package tests.inventoryStores.opticalStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.storeData.InventoryStore_Data;
import org.dom4j.rule.Mode;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.InventoryCommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.OpticalStore.Page_SalesOrder;
import pages.store.Page_Store;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_SRN;

import javax.sound.midi.Soundbank;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static pages.commonElements.CommonActions.*;
import static pages.commonElements.CommonActions.convertStringToDouble;

public class SRNTest extends TestBase {

    public static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");

    String itemDescription[] = {"NONSTOCKTESTITEM", "Xion"};
    String opticalStoreName = "OpticalStore- Optical";
    String salesOrderTime = "";
    String salesOrderDate = "";
    String otherChargesName = "Item_other";
    String otherCharges = "10.0";
    String billType = "Bill";
    String billNumber = "1111";
    String salesOrderItemQuantity[] = {"2.0", "3.0"};
    String salesOrderItemMRPValue[] = {"20", "25"};
    String vendorName = "Supplier ABC";
    String srnUnitCost[] = {"30", "40"};
    String salesOrderTaxRate[] = {"5", "18"};
    String currentDate = Cls_Generic_Methods.getTodayDate("dd-MM-yyyy");
    String dateTimeFull = "";
    String orderDeliveryDate = "";
    String orderStatus = "";
    String orderRecipientInfo = "";
    String orderAmount = "";
    String netAmountValue = "";
    String availableStock = "0.0";
    List<String> salesOrderTableHeaderList = new ArrayList<String>();
    String srnTransactionNotes = "SRN_Notes" + CommonActions.getRandomString(5);
    String srnDate, srnTime, netAmountCalculatedAfterOtherCharges, totalNetAmountAfterOtherChargesAddition;
    List<String> transactionDetailsDataList = new ArrayList<>();
    List<String> taxableAmountListSrnTable = new ArrayList<>();
    List<String> taxAmountListSrnTable = new ArrayList<>();
    List<String> totalCostListSrnTable = new ArrayList<>();
    List<String> sgstListSrnTable = new ArrayList<>();

    List<String> cgstListSrnTable = new ArrayList<>();
    List<String> vendorRateMrpList = new ArrayList<>();

    String taxableAmountInView, cgstAmountInView, totalAmtIncludingTaxInView, netAmontInView;


    @Test(enabled = true, description = "Creating Sales Order For SRN In Optical Store")
    public void createSalesOrderForSRNTransaction() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);

        myPatient = map_PatientsInExcel.get(patientKey);
        boolean bSalesOrderFound = false;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {

                // Open Optical Store

                CommonActions.selectStoreOnApp(opticalStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");

                // Creating New Item In Item Master

                boolean addFirstItemStatus = addItemForSRNTransaction(itemDescription[0]);
                boolean addSecondItemStatus = addItemForSRNTransaction(itemDescription[1]);

                if (addFirstItemStatus && addSecondItemStatus) {

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

                    boolean firstItem = validatingSearchedItemInList(oPage_SalesOrder.list_rawOfNonStockableItemOnLeftInSearchResult, itemDescription[0]);
                    enteringValueItemInList(oPage_SalesOrder.list_inputQty.get(0), salesOrderItemQuantity[0],
                            oPage_SalesOrder.input_itemMRP.get(0), salesOrderItemMRPValue[0]);

                    boolean secondItem = validatingSearchedItemInList(oPage_SalesOrder.list_rawOfNonStockableItemOnLeftInSearchResult, itemDescription[1]);
                    enteringValueItemInList(oPage_SalesOrder.list_inputQty.get(1), salesOrderItemQuantity[1],
                            oPage_SalesOrder.input_itemMRP.get(1), salesOrderItemMRPValue[1]);

                    m_assert.assertTrue(firstItem, "Non Stockable Item Found and clicked as " + itemDescription[0]);
                    m_assert.assertTrue(secondItem, "Non Stockable Item Found and clicked as " + itemDescription[1]);

                    salesOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_orderTime, "value");
                    salesOrderTime = salesOrderTime.replaceAll("\\s+", "");
                    salesOrderTime = CommonActions.getRequiredFormattedDateTime("h:mma", "hh:mma", salesOrderTime);

                    m_assert.assertTrue("Sales order time:<b> " + salesOrderTime + "</b>");

                    salesOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_TxnDate, "value");
                    salesOrderDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", salesOrderDate);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_estimatedOrderDeliveryDate),
                            "Estimated Delivery Date Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_currentEstimatedOrderDeliveryDate, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_currentEstimatedOrderDeliveryDate),
                            "Estimated Delivery Date entered as :" + Cls_Generic_Methods.getTextInElement(oPage_SalesOrder.input_currentEstimatedOrderDeliveryDate));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_netAmount, 1);
                    netAmountValue = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.input_netAmount, "value");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                            "Save Changes Button Clicked In Sales Order Template");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 10);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 4);

                    for (WebElement salesOrderHeader : oPage_SalesOrder.list_salesOrderTableHeaderList) {
                        salesOrderTableHeaderList.add(Cls_Generic_Methods.getTextInElement(salesOrderHeader));
                    }

                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    for (WebElement salesOrderItem : oPage_SalesOrder.list_salesOrderTableItemDataList) {
                        if (Cls_Generic_Methods.isElementDisplayed(salesOrderItem)) {
                            List<WebElement> salesOrderRow = salesOrderItem.findElements(By.xpath("./child::*"));

                            dateTimeFull = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(salesOrderTableHeaderList.indexOf("Txn.Info")));
                            orderDeliveryDate = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(salesOrderTableHeaderList.indexOf("Delivery Date")));
                            orderStatus = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(salesOrderTableHeaderList.indexOf("Order Status")));
                            orderRecipientInfo = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(salesOrderTableHeaderList.indexOf("Recipient Info")));
                            orderAmount = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(salesOrderTableHeaderList.indexOf("Amount")));
                            String totalAmount[] = orderAmount.split("[\r\n]+");

                            String orderAmountUI = decimalFormat.format(convertStringToDouble(totalAmount[0]));
                            String[] dateTimeAndId = dateTimeFull.split("[\r\n]+");

                            String fullName = myPatient.getsSALUTATION() + " " + myPatient.getsFIRST_NAME() +
                                    " " + myPatient.getsMOBILE_NUMBER();

//                            currentDate = CommonActions.getRequiredFormattedDateTime("dd-MM-yyyy", "dd-MM-yyyy", currentDate);

                            if (dateTimeAndId[0].equalsIgnoreCase(salesOrderDate + "  |  " + salesOrderTime) &&
                                    orderDeliveryDate.replaceAll("[\r\n]+", " ").equalsIgnoreCase(currentDate + " Estimated") &&
                                    //orderStatus.equalsIgnoreCase("Placed") &&
                                    orderRecipientInfo.replaceAll("[\r\n]+", " ").equalsIgnoreCase(fullName) &&
                                    orderAmountUI.equals(decimalFormat.format(convertStringToDouble(netAmountValue)))
                            ) {
                                bSalesOrderFound = true;
                                Cls_Generic_Methods.clickElement(salesOrderItem);
                                Cls_Generic_Methods.customWait(5);

                                break;
                            }
                        }
                    }

                    m_assert.assertTrue(bSalesOrderFound, " Sale Order Created and Found In List");

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_createRequisition),
                            " Create Requisition Button Displayed");
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                    Cls_Generic_Methods.customWait();

                } else {
                    m_assert.assertInfo(addFirstItemStatus, "Item is not Added in Item Master Description as: <b>" + itemDescription[0] + "</b>");
                    m_assert.assertInfo(addSecondItemStatus, "Item is not Added in Item Master Description as: <b>" + itemDescription[1] + "</b>");

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

    @Test(enabled = true, description = "Validating Create SRN Transaction Functionality In Optical Store")
    public void validateCreateSRNTransactionFunctionality() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_SRN oPage_SRN = new Page_SRN(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        boolean bSrnSalesOrderFound = false;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {
                // Creating SRN Transaction For Sales Order
                CommonActions.selectStoreOnApp(opticalStoreName);
                Cls_Generic_Methods.switchToOtherTab();
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

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.input_srnNotes, srnTransactionNotes),
                        "SRN Notes Entered as : " + srnTransactionNotes);


                for (WebElement srnOrderItem : oPage_SRN.list_salesOrderListInSRNTemplate) {
                    if (Cls_Generic_Methods.isElementDisplayed(srnOrderItem)) {

                        List<WebElement> srnOrderRow = srnOrderItem.findElements(By.xpath("./child::td"));

                        String srnDateTimeFull = Cls_Generic_Methods.getTextInElement(srnOrderRow.get(1));
                        String srnOrderDeliveryDate = Cls_Generic_Methods.getTextInElement(srnOrderRow.get(2));
                        String srnOrderStatus = Cls_Generic_Methods.getTextInElement(srnOrderRow.get(3));
                        String srnOrderRecipientInfo = Cls_Generic_Methods.getTextInElement(srnOrderRow.get(4));
                        String srnOrderAmount = Cls_Generic_Methods.getTextInElement(srnOrderRow.get(5));

                        if (srnDateTimeFull.equalsIgnoreCase(dateTimeFull) &&
                                srnOrderDeliveryDate.equalsIgnoreCase(orderDeliveryDate) &&
                                srnOrderStatus.equalsIgnoreCase(orderStatus) &&
                                srnOrderRecipientInfo.equalsIgnoreCase(orderRecipientInfo) &&
                                srnOrderAmount.equalsIgnoreCase(orderAmount)) {

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

                    String itemName = Cls_Generic_Methods.getTextInElement(eDescription);
                    String srnMrpValue = Cls_Generic_Methods.getElementAttribute(oPage_SRN.list_mrpListInSrn.get(index), "value");
                    String srnQuantity = Cls_Generic_Methods.getElementAttribute(oPage_SRN.list_quantityListInSrn.get(index), "value");

                    m_assert.assertTrue(itemName.equalsIgnoreCase(itemDescription[index]), itemDescription[index] + " Item Name Displaying Correctly In SRN ");

                    m_assert.assertTrue(convertStringToDouble(srnMrpValue) == (convertStringToDouble(salesOrderItemMRPValue[index])) ||
                                    convertStringToDouble(srnMrpValue) == (convertStringToDouble(vendorRateMrpList.get(index))) ,
                            "MRP for Sale order is Same in Sales Order As SRN as :<b>" + srnMrpValue + "</b> for item  " + itemDescription[index]);

                    m_assert.assertTrue(srnQuantity.equalsIgnoreCase(salesOrderItemQuantity[index]),
                            "Quantity for Sale order is Same in Sales Order As SRN as : <b>" + salesOrderItemQuantity[index] + "</b> for item " + itemDescription[index]);

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.list_unitCostListInSrn.get(index), srnUnitCost[index]),
                            "Unit cost entered as : <b>" + srnUnitCost[index] + "</b> for item " + itemDescription[index]);

                    Cls_Generic_Methods.customWait();

                    String srnGSTTaxValue = Cls_Generic_Methods.getTextInElement(oPage_SRN.list_taxRateListInSrn.get(index));
                    String srnTaxIncValue = Cls_Generic_Methods.getTextInElement(oPage_SRN.list_taxInclusiveListInSrn.get(index));

                    String srnTaxableAmountValue = Cls_Generic_Methods.getElementAttribute(oPage_SRN.list_taxableAmountListInSrn.get(index), "value");
                    String srnTaxAmountValue = Cls_Generic_Methods.getElementAttribute(oPage_SRN.list_taxAmountListInSrn.get(index), "value");
                    String srnTotalCostValue = Cls_Generic_Methods.getElementAttribute(oPage_SRN.list_totalCostListInSrn.get(index), "value");

                    taxableAmountListSrnTable.add(srnTaxableAmountValue);
                    totalCostListSrnTable.add(srnTotalCostValue);

                    Double taxableAmountCalculation = (convertStringToDouble(srnTotalCostValue) * (100 /
                            (100 + convertStringToDouble(salesOrderTaxRate[index]))));
                    String taxableAmountCalculated = decimalFormat.format(taxableAmountCalculation);

                    if(taxableAmountCalculated.equalsIgnoreCase(srnTaxableAmountValue)){
                        m_assert.assertTrue("Taxable Amount Calculated correctly : <b>" + taxableAmountCalculated + "</b> for item " + itemDescription[index]);
                    }else{
                        DecimalFormat decimalFormat1 = new DecimalFormat("0.00");

                        decimalFormat1.setRoundingMode(RoundingMode.UP);
                        m_assert.assertTrue(decimalFormat1.format(taxableAmountCalculation).equalsIgnoreCase(srnTaxableAmountValue),"Taxable Amount Calculated correctly : <b>" + taxableAmountCalculated + "</b> for item " + itemDescription[index]);

                    }

                    String taxAmountCalculated = decimalFormat.format(Double.parseDouble(srnTotalCostValue) - Double.parseDouble(srnTaxableAmountValue));
                    taxAmountListSrnTable.add(taxAmountCalculated);

                    Double srnTotalCostValueCalculated = convertStringToDouble(srnUnitCost[index]) * convertStringToDouble(salesOrderItemQuantity[index]);

                    m_assert.assertTrue(srnGSTTaxValue.equalsIgnoreCase(String.valueOf(Double.parseDouble(salesOrderTaxRate[index]))),
                            "Tax rate is displaying correctly as :<b>" + srnGSTTaxValue + "</b> for item " + itemDescription[index]);
                    m_assert.assertTrue(srnTaxIncValue.equalsIgnoreCase("true"),
                            " Tax Inc Displaying correctly : <b>" + srnTaxIncValue + "</b> for item  " + itemDescription[index]);

                    m_assert.assertTrue(convertStringToDouble(srnTotalCostValue) == srnTotalCostValueCalculated,
                            " Total Cost Calculated Correctly as :<b>" + srnTotalCostValueCalculated + "</b> for item " + itemDescription[index]);

                    m_assert.assertTrue(srnTaxAmountValue.equalsIgnoreCase(taxAmountCalculated) ,
                            "Tax Amount Calculated Correctly : <b>" + taxAmountCalculated + "</b> for item " + itemDescription[index]);

                }


                String srnSgstTaxValueFirstItem = Cls_Generic_Methods.getElementAttribute(oPage_SRN.list_gstValuesList.get(0), "value");
                String srnCgstTaxValueFirstItem = Cls_Generic_Methods.getElementAttribute(oPage_SRN.list_gstValuesList.get(1), "value");
                String srnSgstTaxValueSecondItem = Cls_Generic_Methods.getElementAttribute(oPage_SRN.list_gstValuesList.get(2), "value");
                String srnCgstTaxValueSecondItem = Cls_Generic_Methods.getElementAttribute(oPage_SRN.list_gstValuesList.get(3), "value");

                sgstListSrnTable.add(srnSgstTaxValueFirstItem);
                sgstListSrnTable.add(srnSgstTaxValueSecondItem);

                cgstListSrnTable.add(srnCgstTaxValueFirstItem);
                cgstListSrnTable.add(srnCgstTaxValueSecondItem);


                String sgstAmountCalculatedFirstItem = decimalFormat.format(
                        convertStringToDouble(taxAmountListSrnTable.get(0)) / 2);
                String cgstAmountCalculatedFirstItem = decimalFormat.format(
                        convertStringToDouble(taxAmountListSrnTable.get(0)) / 2);
                String sgstAmountCalculatedSecondItem = String.valueOf(
                        convertStringToDouble(taxAmountListSrnTable.get(1)) / 2);
                String cgstAmountCalculatedSecondItem = String.valueOf(
                        convertStringToDouble(taxAmountListSrnTable.get(1)) / 2);

                m_assert.assertTrue(sgstAmountCalculatedFirstItem.equals(srnSgstTaxValueFirstItem),
                        "SGST2.5 Amount Calculated Correctly as :<b>" + srnSgstTaxValueFirstItem + "</b>");
                m_assert.assertTrue(cgstAmountCalculatedFirstItem.equals(srnCgstTaxValueFirstItem),
                        "CGST2.5 Amount Calculated Correctly as :<b>" + srnCgstTaxValueFirstItem + "</b>");
                m_assert.assertTrue(sgstAmountCalculatedSecondItem.contains(srnSgstTaxValueSecondItem),
                        "SGST9 Amount Calculated Correctly as :<b>" + srnSgstTaxValueSecondItem + "</b>");
                m_assert.assertTrue(cgstAmountCalculatedSecondItem.contains(srnCgstTaxValueSecondItem),
                        "CGST9 Amount Calculated Correctly as :<b>" + srnCgstTaxValueSecondItem + "</b>");

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


                Double totalTaxableAmountCalculatedInTaxCage = 0.0;
                for (String sTaxableAmount : taxableAmountListSrnTable) {
                    totalTaxableAmountCalculatedInTaxCage = totalTaxableAmountCalculatedInTaxCage + Double.parseDouble(sTaxableAmount);
                }

                Double totalCostCalculatedForAllItem = 0.0;
                for (String sTotalCost : totalCostListSrnTable) {
                    totalCostCalculatedForAllItem = totalCostCalculatedForAllItem + Double.parseDouble(sTotalCost);
                }

                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTaxableAmountInTaxCage, "value").equalsIgnoreCase(String.valueOf(totalTaxableAmountCalculatedInTaxCage)),
                        " Taxable Amount Showing Correctly In Tax Cage Before Applying other Charges or Discount ");

                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTotalAmountIncTax, "value").equalsIgnoreCase(String.format("%.2f", totalCostCalculatedForAllItem)),
                        " Total Amount Including Tax Showing Correctly Before adding other charges or discount");


                String netAmountBeforeOtherCharges = Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnNetAmountValue, "value");

                m_assert.assertTrue(netAmountBeforeOtherCharges.equalsIgnoreCase(String.format("%.2f", totalCostCalculatedForAllItem)),
                        " Net Amount Showing Correctly Before Other Charges and Discount");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.input_srnDiscount, "10"),
                        " Discount Entered as 10");
                Cls_Generic_Methods.customWait(2);

                String netAmountAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnNetAmountValue, "value");
                String netAmountCalculatedAfterDiscount = String.format("%.2f", Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTotalAmountIncTax, "value")) - Double.parseDouble("10"));

                m_assert.assertTrue(netAmountAfterDiscount.equalsIgnoreCase(netAmountCalculatedAfterDiscount),
                        " Net Amount Showing Correctly After Applying Discount");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTotalAmountIncTax, "value").equalsIgnoreCase(String.format("%.2f", totalCostCalculatedForAllItem)),
                        " Total Amount Including Tax Showing Correctly adding Discount ");

                Cls_Generic_Methods.customWait(4);
                String discountPercentageCal = validatingDiscountCalculation(Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTotalAmountIncTax, "value"), "10");

                DecimalFormat decimalFormat1 = new DecimalFormat("0.00");

                decimalFormat1.setRoundingMode(RoundingMode.UP);

                String TaxableAmountInCageAfterDiscountOnUI = Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTaxableAmountInTaxCage, "value");

                String taxableAmountFirstItemAfterDiscount = decimalFormat1.format(Double.parseDouble(taxableAmountListSrnTable.get(0)) -
                        (Double.parseDouble(taxableAmountListSrnTable.get(0)) * Double.parseDouble(discountPercentageCal) / 100));

                String taxableAmountSecondItemAfterDiscount = decimalFormat1.format(Double.parseDouble(taxableAmountListSrnTable.get(1)) -
                        (Double.parseDouble(taxableAmountListSrnTable.get(1)) * Double.parseDouble(discountPercentageCal) / 100));

                Double totalTaxableAmountAfterDiscount = Double.parseDouble(taxableAmountFirstItemAfterDiscount) + Double.parseDouble(taxableAmountSecondItemAfterDiscount);

                m_assert.assertTrue(TaxableAmountInCageAfterDiscountOnUI.equalsIgnoreCase(decimalFormat.format(totalTaxableAmountAfterDiscount)),
                        " Taxable Amount in Tax Cage Calculated Correctly after Discount"+decimalFormat.format(totalTaxableAmountAfterDiscount));

                taxableAmountInView = TaxableAmountInCageAfterDiscountOnUI;

                String srnSgstTaxValueAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnSgstTaxValue, "value");
                String srnCgstTaxValueAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnCgstTaxValue, "value");

                cgstAmountInView = srnSgstTaxValueAfterDiscount;

                String sgstFirstItemAfterDiscount = decimalFormat.format(Double.parseDouble(sgstListSrnTable.get(0)) -
                        (Double.parseDouble(sgstListSrnTable.get(0)) * Double.parseDouble(discountPercentageCal) / 100));

                String sgstAmountSecondItemAfterDiscount = decimalFormat.format(Double.parseDouble(sgstListSrnTable.get(1)) -
                        (Double.parseDouble(sgstListSrnTable.get(1)) * Double.parseDouble(discountPercentageCal) / 100));

                String cgstFirstItemAfterDiscount = decimalFormat.format(Double.parseDouble(cgstListSrnTable.get(0)) -
                        (Double.parseDouble(cgstListSrnTable.get(0)) * Double.parseDouble(discountPercentageCal) / 100));

                String cgstAmountSecondItemAfterDiscount = decimalFormat.format(Double.parseDouble(cgstListSrnTable.get(1)) -
                        (Double.parseDouble(cgstListSrnTable.get(1)) * Double.parseDouble(discountPercentageCal) / 100));

                m_assert.assertTrue(srnSgstTaxValueAfterDiscount.equalsIgnoreCase(sgstFirstItemAfterDiscount),
                        " Sgst2.5 in Tax Cage Calculated Correctly after Discount");
                m_assert.assertTrue(srnCgstTaxValueAfterDiscount.equalsIgnoreCase(cgstFirstItemAfterDiscount),
                        " cgst2.5 in Tax Cage Calculated Correctly after Discount");
                m_assert.assertTrue(srnSgstTaxValueAfterDiscount.equalsIgnoreCase(sgstAmountSecondItemAfterDiscount),
                        " Sgst9 in Tax Cage Calculated Correctly after Discount");
                m_assert.assertTrue(srnCgstTaxValueAfterDiscount.equalsIgnoreCase(cgstAmountSecondItemAfterDiscount),
                        " cgst9 in Tax Cage Calculated Correctly after Discount");


                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SRN.select_otherCharges, otherChargesName),
                        "Other Charges: <b> " + otherChargesName + " </b>");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.input_otherChargesAmount, otherCharges);

                totalAmtIncludingTaxInView = Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTotalAmountIncTax, "value");
                m_assert.assertTrue(totalAmtIncludingTaxInView.equalsIgnoreCase(String.format("%.2f", totalCostCalculatedForAllItem)),
                        " Total Amount Including Tax Showing Correctly adding other charges ");

                netAmountCalculatedAfterOtherCharges = InventoryCommonActions.getOtherChargesNetAmount(otherCharges, netAmountAfterDiscount, "plus");
                totalNetAmountAfterOtherChargesAddition = Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnNetAmountValue, "value");

                m_assert.assertTrue(netAmountCalculatedAfterOtherCharges.equalsIgnoreCase(totalNetAmountAfterOtherChargesAddition),
                        "Other Charges and Discount addition working correctly and adding successfully in net amount");


                Cls_Generic_Methods.clickElement(oPage_SRN.dropdown_selectBillType);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SRN.dropdown_selectBillType, billType),
                        "Bill Type Selected:<b> " + billType + " </b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SRN.input_billNumber, billNumber),
                        "Bill Number: <b> " + billNumber + " </b>");
                Cls_Generic_Methods.clickElement(oPage_SRN.input_billDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SRN.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SRN.input_todayBillDate),
                        "Date of bill selected:<b> " + Cls_Generic_Methods.getTextInElement(oPage_Purchase.input_todayBillDate) + " </b>");

                srnDate = Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTransactionDateValue, "value");
                srnTime = Cls_Generic_Methods.getElementAttribute(oPage_SRN.input_srnTransactionTimeValue, "value");

                if (srnTime.length() == 7) {
                    srnTime = "0" + srnTime;
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "Save Changes Button Clicked In A Lot Inventory Template");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 15);

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating View SRN Transaction Functionality In Optical Store")
    public void validateViewSRNTransactionFunctionality() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_SRN oPage_SRN = new Page_SRN(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        String srnHeaderList[] = {"Date | Time", "Note", "SRN No.", "Amount"};
        String srnTransactionDetailsHeaderList[] = {"Description", "Batch No.", "Model No.", "Expiry", "Quantity", "Total Price"};

        myPatient = map_PatientsInExcel.get(patientKey);
        boolean bSrnFound = false;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {
                // Creating SRN Transaction For Sales Order
                CommonActions.selectStoreOnApp(opticalStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "SRN");

                for (WebElement eHeader : oPage_Purchase.list_purchaseTransactionHeaderList) {

                    int index = oPage_Purchase.list_purchaseTransactionHeaderList.indexOf(eHeader);
                    String headerValue = Cls_Generic_Methods.getTextInElement(eHeader);
                    if (headerValue.equalsIgnoreCase(srnHeaderList[index])) {
                        m_assert.assertTrue(headerValue + " Header Present In SRN Transaction Table");
                    }

                }


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
                                note.equalsIgnoreCase(srnTransactionNotes) &&
                                (!srnNo.isEmpty()) &&
                                amount.equalsIgnoreCase(String.valueOf(Double.parseDouble(totalAmtIncludingTaxInView)))
                        ) {
                            bSrnFound = true;
                            Cls_Generic_Methods.clickElement(eValue);
                            Cls_Generic_Methods.customWait(5);

                            break;
                        }
                    }
                }

                m_assert.assertTrue(bSrnFound, " SRN Transaction Found And Clicked");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SRN.text_statusRhsSide).equalsIgnoreCase("Status:    Open"),
                        " Status Present In RHS Side as : Status:    Open");
                m_assert.assertTrue(!Cls_Generic_Methods.getTextInElement(oPage_SRN.text_transactionIdRhs).isEmpty(),
                        " Transaction ID Present In RHS Side as : " + Cls_Generic_Methods.getTextInElement(oPage_SRN.text_transactionIdRhs));

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SRN.header_transactionDetailsSection),
                        " Transaction Details Section Displayed");

                for (WebElement eHeader : oPage_SRN.list_transactionDetailsHeaderList) {

                    int index = oPage_SRN.list_transactionDetailsHeaderList.indexOf(eHeader);
                    String headerValue = Cls_Generic_Methods.getTextInElement(eHeader);
                    if (headerValue.equalsIgnoreCase(srnTransactionDetailsHeaderList[index])) {
                        m_assert.assertTrue(headerValue + " Header Present In SRN Transaction Details Table");
                    }

                }

                addDataForTransactionDetails();
                for (WebElement eHeader : oPage_SRN.list_transactionDetailsValueList) {

                    int index = oPage_SRN.list_transactionDetailsValueList.indexOf(eHeader);
                    String headerValue = Cls_Generic_Methods.getTextInElement(eHeader);
                    if (headerValue.equalsIgnoreCase(transactionDetailsDataList.get(index))) {
                        m_assert.assertTrue(headerValue + " Value Present In SRN Transaction Details Table");
                    }

                }

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SRN.text_totalAmtIncTax).equalsIgnoreCase(String.valueOf(Double.parseDouble(totalAmtIncludingTaxInView))),
                        " Total Amt Including Tax Label and Its Value as :" + totalAmtIncludingTaxInView);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SRN.text_netAmount).equalsIgnoreCase(String.valueOf(Double.parseDouble(totalNetAmountAfterOtherChargesAddition))),
                        " Net Amount Label and Its Value as :" + totalNetAmountAfterOtherChargesAddition);

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SRN.text_taxableAmt).equalsIgnoreCase(String.valueOf(Double.parseDouble(taxableAmountInView))),
                        " Taxable Amount Label and Its Value as :" + taxableAmountInView);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SRN.text_sgst).equalsIgnoreCase(cgstAmountInView),
                        " SGST Amount Label and Its Value as :" + cgstAmountInView);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SRN.text_cgst).equalsIgnoreCase(cgstAmountInView),
                        " CGST Amount Label and Its Value as :" + cgstAmountInView);


                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating SRN Transaction Functionality In Item Lot In Optical Store")
    public void validateSRNTransactionInLotFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SRN oPage_SRN = new Page_SRN(driver);


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {

                //Validating Created SRN In Items Lot Section
                CommonActions.selectStoreOnApp(opticalStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");

                m_assert.assertTrue(validateInLot(itemDescription[0]), " Validated In Lot For Item : " + itemDescription[0]);

                String lotBlockedStockAmountAfterPayment = Cls_Generic_Methods.getTextInElement(oPage_SRN.text_lotBlockedStockAmount);
                String lotAvailableStockAmountAfterPayment = Cls_Generic_Methods.getTextInElement(oPage_SRN.text_lotAvailableStockAmount);
                m_assert.assertTrue(lotBlockedStockAmountAfterPayment.equalsIgnoreCase(salesOrderItemQuantity[0]), "Blocked Stock Displaying correctly as :<b>" + salesOrderItemQuantity[0] + "</b>");
                m_assert.assertTrue(lotAvailableStockAmountAfterPayment.equalsIgnoreCase(availableStock), "Available Stock Displaying correctly as :<b>" + availableStock + "</b>");

                m_assert.assertTrue(validateInLot(itemDescription[1]), " Validated In Lot FOr Item : " + itemDescription[1]);

                lotBlockedStockAmountAfterPayment = Cls_Generic_Methods.getTextInElement(oPage_SRN.text_lotBlockedStockAmount);
                lotAvailableStockAmountAfterPayment = Cls_Generic_Methods.getTextInElement(oPage_SRN.text_lotAvailableStockAmount);
                m_assert.assertTrue(lotBlockedStockAmountAfterPayment.equalsIgnoreCase(salesOrderItemQuantity[1]), "Blocked Stock Displaying correctly as :<b>" + salesOrderItemQuantity[1] + "</b>");
                m_assert.assertTrue(lotAvailableStockAmountAfterPayment.equalsIgnoreCase(availableStock), "Available Stock Displaying correctly as :<b>" + availableStock + "</b>");

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating Create Bill In Sales Order In Optical Store")
    public void validateCreateBillAndValidateInLotFunctionalityForSalesOrder() {


        Page_SalesOrder oPage_SalesOrder = new Page_SalesOrder(driver);
        Page_SRN oPage_SRN = new Page_SRN(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        myPatient = map_PatientsInExcel.get(patientKey);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {

                // Creating Bill In SRN Transaction For Same Sale Order
                CommonActions.selectStoreOnApp(opticalStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SRN.button_addNew, 4);

                boolean bSalesOrderFoundAfterSRN = false;
                for (WebElement salesOrderItem : oPage_SalesOrder.list_salesOrderTableItemDataList) {
                    if (Cls_Generic_Methods.isElementDisplayed(salesOrderItem)) {
                        List<WebElement> salesOrderRow = salesOrderItem.findElements(By.xpath("./child::*"));

                        dateTimeFull = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(salesOrderTableHeaderList.indexOf("Txn.Info")));
                        orderDeliveryDate = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(salesOrderTableHeaderList.indexOf("Delivery Date")));
                        orderStatus = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(salesOrderTableHeaderList.indexOf("Order Status")));
                        orderRecipientInfo = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(salesOrderTableHeaderList.indexOf("Recipient Info")));
                        orderAmount = Cls_Generic_Methods.getTextInElement(salesOrderRow.get(salesOrderTableHeaderList.indexOf("Amount")));
                        String totalAmount[] = orderAmount.split("[\r\n]+");
                        String orderAmountUI = decimalFormat.format(convertStringToDouble(totalAmount[0]));
                        String[] dateTimeAndId = dateTimeFull.split("[\r\n]+");

                        String fullName = myPatient.getsSALUTATION() + " " + myPatient.getsFIRST_NAME() +
                                " " + myPatient.getsMOBILE_NUMBER();


                        if (dateTimeAndId[0].equalsIgnoreCase(salesOrderDate + "  |  " + salesOrderTime) &&
                                orderDeliveryDate.replaceAll("[\r\n]+", " ").equalsIgnoreCase(currentDate + " Estimated") &&
                               // orderStatus.equalsIgnoreCase("Placed") &&
                                orderRecipientInfo.replaceAll("[\r\n]+", " ").equalsIgnoreCase(fullName) &&
                                orderAmountUI.equals(decimalFormat.format(convertStringToDouble(netAmountValue)))
                        ) {
                            bSalesOrderFoundAfterSRN = true;
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(salesOrderItem),
                                    "Sale Order Selected From List ");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_CreateBill, 10);
                            break;
                        }
                    }
                }

                m_assert.assertTrue(bSalesOrderFoundAfterSRN, " Sales Order Found In List After SRN Create");

                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.button_createRequisition),
                        " Create Requisition Is Not Displayed Correctly ");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_CreateBill),
                        "Create Bill Button CLicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.header_reviewOrder, 5);
                String sPendingBalance = Cls_Generic_Methods.getElementAttribute(oPage_SalesOrder.text_balancePendingAmountPaymentReceived, "value");

                Cls_Generic_Methods.selectElementByVisibleText(oPage_SalesOrder.select_modeOfPaymentReceived.get(0), "Cash");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.input_amountPaymentReceived.get(0), 1);

                Cls_Generic_Methods.clickElement(oPage_SalesOrder.input_amountPaymentReceived.get(0));
                Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_amountPaymentReceived.get(0), sPendingBalance);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_CreateBillAfterPayment, 1);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_CreateBillAfterPayment),
                        "Create Bill Button Clicked in Review Order For Payment");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SalesOrder.button_addNewButtonInOrder, 10);

                // Validating Same Order In Lot Again After Bill Payment
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.customWait();

                m_assert.assertTrue(validateInLot(itemDescription[0]), " Validated In Lot For Item : " + itemDescription[0]);

                String lotBlockedStockAmountAfterPayment = Cls_Generic_Methods.getTextInElement(oPage_SRN.text_lotBlockedStockAmount);
                String lotAvailableStockAmountAfterPayment = Cls_Generic_Methods.getTextInElement(oPage_SRN.text_lotAvailableStockAmount);
                m_assert.assertTrue(lotBlockedStockAmountAfterPayment.equalsIgnoreCase(availableStock), "Blocked Stock Displaying correctly as :<b>" + availableStock + "</b>");
                m_assert.assertTrue(lotAvailableStockAmountAfterPayment.equalsIgnoreCase(availableStock), "Available Stock Displaying correctly as :<b>" + availableStock + "</b>");

                m_assert.assertTrue(validateInLot(itemDescription[1]), " Validated In Lot FOr Item : " + itemDescription[1]);

                lotBlockedStockAmountAfterPayment = Cls_Generic_Methods.getTextInElement(oPage_SRN.text_lotBlockedStockAmount);
                lotAvailableStockAmountAfterPayment = Cls_Generic_Methods.getTextInElement(oPage_SRN.text_lotAvailableStockAmount);
                m_assert.assertTrue(lotBlockedStockAmountAfterPayment.equalsIgnoreCase(availableStock), "Blocked Stock Displaying correctly as :<b>" + availableStock + "</b>");
                m_assert.assertTrue(lotAvailableStockAmountAfterPayment.equalsIgnoreCase(availableStock), "Available Stock Displaying correctly as :<b>" + availableStock + "</b>");

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait(2);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

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

    public void addDataForTransactionDetails() {

        transactionDetailsDataList.add(itemDescription[0] + "-");
        transactionDetailsDataList.add("");
        transactionDetailsDataList.add("");
        transactionDetailsDataList.add("");
        transactionDetailsDataList.add(salesOrderItemQuantity[0]);
        transactionDetailsDataList.add(String.valueOf(Double.parseDouble(totalNetAmountAfterOtherChargesAddition)));

        transactionDetailsDataList.add(itemDescription[1] + "-");
        transactionDetailsDataList.add("");
        transactionDetailsDataList.add("");
        transactionDetailsDataList.add("");
        transactionDetailsDataList.add(salesOrderItemQuantity[1]);
        transactionDetailsDataList.add(String.valueOf(Double.parseDouble(totalNetAmountAfterOtherChargesAddition)));


    }

    public String validatingDiscountCalculation(String totalAmtIncTax, String discount) {

        String bDiscountCalculation = "";

        try {

            Double discountPercentage = (Double.parseDouble(discount) * 100) / (Double.parseDouble(totalAmtIncTax));
            bDiscountCalculation = String.format("%.2f", discountPercentage);

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return bDiscountCalculation;
    }

    public boolean validateInLot(String sDescription) {

        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_SRN oPage_SRN = new Page_SRN(driver);
        boolean sValidated = false;

        try {
            Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchInventoryInStoreInventory);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, sDescription);
            Cls_Generic_Methods.customWait(3);
            oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
                List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
                String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((0)));
                if (itemDescriptionText.contains(sDescription)) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(items),
                            itemDescriptionText + " Clicked in Items Lot  ");
                    sValidated = true;
                    Cls_Generic_Methods.customWait(5);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return sValidated;
    }

    public boolean validatingSearchedItemInList(List<WebElement> elementList, String sDescription) {

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

    public void enteringValueItemInList(WebElement eQty, String sQty, WebElement eMrp, String sMrp) {

        try {
            Cls_Generic_Methods.clearValuesInElement(eQty);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(eQty, sQty), "Quantity Entered as : " + sQty + " for item ");
            String vendorRateCheck = Cls_Generic_Methods.getElementAttribute(eMrp,"value");
            if(vendorRateCheck.isEmpty()) {
                Cls_Generic_Methods.clearValuesInElement(eMrp);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(eMrp, sMrp), "MRP Entered as : " + sMrp + " for item ");
            }else{
                vendorRateMrpList.add(vendorRateCheck);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

}
