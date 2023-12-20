package tests.inventoryStores.pharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.InventoryCommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.templates.Page_InventorySearchCommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorMaster;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_PurchaseReturn;
import pages.store.PharmacyStore.Transaction.Page_Sale;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static pages.commonElements.CommonActions.getRandomString;
import static tests.inventoryStores.pharmacyStore.Transaction.PurchaseGRNTest.*;

public class PurchaseReturnTest extends TestBase {

    String pharmacyStoreName = "Pharmacy automation- Pharmacy";
    public static final String itemDescription = "TransactionPurchase_" + getRandomString(4);
    String oldDescription = "";
    static String vendorName = "Supplier ABC";
    static String billType = "Bill";
    static String billNumber = "1111"+CommonActions.getRandomString(5);
    public static final String subStore = "Default";
    static String purchaseTransactionTime = "";
    String purchaseTransactionDate = "";

    String otherChargesAmount = "10.0";
    String otherChargesName = "Item_other";
    static String transactionNotes = "Transaction_notes" + getRandomString(4);
    String returnNotes = "Return_notes" + getRandomString(3);
    String totalNetAmountAfterOtherChargesAddition = "";
    String returnQuantityValue = "1";
    String otherCharges = "10";
    Double discount = 0.0;
    String storeGSTno;
    String vendor_address;
    String vendorGSTno;
    String vendorFullNameAndAddress , transactionIdInSetting;
    boolean gstTypeIGST;
    List<String > itemDetailsValue = new ArrayList<>();
    String itemPrice = "";
    String returnTransactionId, grnNo,oldTransactionId,oldGrnNo;
    String searchTypeList [] = {"Transaction ID","Item Description","GRN No."};




    @Test(enabled = true, description = "Creating Purchase Transaction in Transaction ")
    public void createPurchaseTransactionForReturn() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_PurchaseReturn oPage_PurchaseReturn = new Page_PurchaseReturn(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getGST_valueFromSetting();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait(5);

            CommonActions.selectStoreOnApp(pharmacyStoreName);
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

                        grnNo = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_transactionID);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_returnPurchaseTransaction),
                                "Return Button Clicked For Selected Transaction In Purchase Transaction");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_returnPurchaseTransactionTemplate, 2);

                        Cls_Generic_Methods.selectElementByIndex(oPage_Purchase.select_vendorList, 1);
                        Cls_Generic_Methods.customWait();
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_returnQuantityListInReturnPurchaseTemplate.get(0), returnQuantityValue),
                                " Return Quantity Enter as <b> " + returnQuantityValue + "</b> for Item No. ");
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

                        for (WebElement discountPerItem : oPage_Purchase.list_discountListInTransactionDetailsTable) {
                            if (Cls_Generic_Methods.isElementDisplayed(discountPerItem)) {
                                discount = convertStringToDouble(Cls_Generic_Methods.getTextInElement(discountPerItem));
                            }
                        }
                        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
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

    }

    @Test(enabled = true, description = "Validating Purchase Return Functionality in Transaction ")
    public void ValidatePurchaseReturnTransactionFunctionality() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_PurchaseReturn oPage_PurchaseReturn = new Page_PurchaseReturn(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        List<String> purchaseTransactionHeaderList = new ArrayList<String>();
        String itemDetailsHeader [] = {"#","Code","Description","QTY","Batch","Model No.","U.Price","Remark","Total"};

        boolean vendorFound = false;
        boolean bPurchaseReturnTransactionFound = false;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(2);
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Sequence Manager");
            Cls_Generic_Methods.customWait(2);

            transactionIdInSetting = Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_transactionIdInSequence).trim();

            CommonActions.selectStoreOnApp(pharmacyStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {

                // Creating Purchase Return For Above Created Transaction
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Return");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 2);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_refreshPurchaseTransaction),
                        "Refresh Button Displayed In Purchase Return Table");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_todayFilterButton),
                        " Today Filter Button Displayed In Purchase Return Table");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_vendorDropdownButtonList),
                        " Vendor Filter Button Displayed In Purchase Return Table");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.input_vendorSearchBox),
                        " Vendor Name Search Text Box Displayed In Purchase Return Table");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_reportButtonInPurchaseGRN),
                        " Report Button Displayed In Purchase Return Table");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew),
                        "New Button Clicked in Return Purchase");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 10);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_Vendor_search, (vendorName + " " + vendor_address));
                Cls_Generic_Methods.customWait(3);
                for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
                    Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                    vendorFound = true;
                    break;
                }

                vendorFullNameAndAddress = vendorName + " - " + vendor_address;
                m_assert.assertTrue(vendorFound, "Vendor present in dropdown: <b> " + vendorFullNameAndAddress + "</b>");

                int indexOfItemStock = -1;
                int indexOfItemDescription = -1;
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseReturn.input_lotSearchBoxInPurchaseReturn, 5);

                for (WebElement purchaseItem : oPage_PurchaseReturn.list_purchaseItemDescriptionList) {
                    if (Cls_Generic_Methods.getTextInElement(purchaseItem).equalsIgnoreCase(itemDescription)) {
                        indexOfItemDescription = oPage_PurchaseReturn.list_purchaseItemDescriptionList.indexOf(purchaseItem);
                        break;
                    }
                }

                String currentStock = "";
                boolean purchaseItemStockStatus = false;
                for (WebElement purchaseItemStock : oPage_PurchaseReturn.list_purchaseItemStockList) {
                    indexOfItemStock = oPage_PurchaseReturn.list_purchaseItemStockList.indexOf(purchaseItemStock);
                    itemPrice = Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.list_purchaseItemPriceList.get(indexOfItemStock));

                    if (indexOfItemDescription == indexOfItemStock) {
                        currentStock = Cls_Generic_Methods.getTextInElement(purchaseItemStock);
                        Cls_Generic_Methods.clickElement(purchaseItemStock);
                        purchaseItemStockStatus = true;
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(purchaseItemStockStatus,
                        " Purchase Item Selected From Left Panel in Return Purchase Template: " + itemDescription);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_purchaseAvailableQtyList).equalsIgnoreCase(currentStock),
                        " Validating Current Stock and Available Stock Are Same as :<b> " + currentStock + "</b>");

                // Validating New Unit Cost in Purchase return template
                String netUnitCostWoTaxValueUI = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseReturn.text_purchaseNetUnitCostWOTaxList, "value");
                String unitCostWithoutTaxUI = Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_purchaseUnitCostWOTaxList);
                String paidQuantityUI = Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_purchaseGrnQtyList);
                String marginAmountUI = Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_purchaseMarginList);

                Double unitCostWOTaxPerItem = convertStringToDouble(unitCostWithoutTaxUI);
                Double totalQuantity = convertStringToDouble(paidQuantityUI) + convertStringToDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_purchaseGrnFreeQtyList));
                Double grossAmount = unitCostWOTaxPerItem * totalQuantity;
                Double netUnitCostWOTaxCalculated = (grossAmount - discount) / totalQuantity;
                m_assert.assertTrue(convertStringToDouble(netUnitCostWoTaxValueUI) == netUnitCostWOTaxCalculated,
                        " Net Unit Cost Without Tax Calculated Correctly as :<b> " + netUnitCostWoTaxValueUI + "</b>");

                // Validating Margin Amount In Purchase Return Template
                Double netUnitCostWithoutTaxUIPerItem = convertStringToDouble(netUnitCostWoTaxValueUI);
                Double marginAmountUICalculated = unitCostWOTaxPerItem - netUnitCostWithoutTaxUIPerItem;
                m_assert.assertTrue(convertStringToDouble(marginAmountUI) == marginAmountUICalculated,
                        "Margin Amount Calculated Correctly as : <b> " + marginAmountUICalculated + "</b>");

                // Validating Returnable Quantity and Total Quantity
                Double returnableQuantityUI = convertStringToDouble(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_purchaseReturnableQtyList).trim());
                m_assert.assertTrue(returnableQuantityUI.equals(totalQuantity),
                        " Returnable Quantity and Total Quantity are same as :<b> " + returnableQuantityUI + "</b>");

                //Entering return qty
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseReturn.text_purchaseReturnQtyList, returnQuantityValue),
                        " Return Quantity Enter as <b> " + returnQuantityValue);
                Cls_Generic_Methods.customWait(4);

                // Validating Amount Before Tax
                String amountBeforeTaxUI = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_amountBeforeTax, "value");
                String netAmountPerItemUI = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseReturn.text_purchaseNetAmountList, "value");
                String taxRateUI = Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_purchaseTaxRateList);
                String netReturnWithTaxUI = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_netReturnWithTaxAmount, "value");

                Double amountBeforeTaxCalculated = convertStringToDouble(netUnitCostWoTaxValueUI) * convertStringToDouble(returnQuantityValue);
                m_assert.assertTrue(amountBeforeTaxCalculated == convertStringToDouble(amountBeforeTaxUI),
                        " Amount Before Tax Calculated Correctly as :<b> " + amountBeforeTaxCalculated + "</b>");

                // Validating Net Amount For Item
                Double gstAmountCalculated = getGStCalculation(netUnitCostWoTaxValueUI, taxRateUI, returnQuantityValue, "0");
                Double netAmountPerItemCalculated = convertStringToDouble(netUnitCostWoTaxValueUI) * convertStringToDouble(returnQuantityValue) + gstAmountCalculated;
                m_assert.assertTrue(convertStringToDouble(netAmountPerItemUI) == netAmountPerItemCalculated,
                        "Net Amount Per Item Calculated Correctly as :<b>" + netAmountPerItemCalculated + "</b>");

                // Validating  GST Tax
                String gstAmountPerItemUi = " ";
                for (WebElement gstAmountText : oPage_Purchase.list_perItemVATListInReturnPurchaseTemplate) {
                    gstAmountPerItemUi = Cls_Generic_Methods.getElementAttribute(gstAmountText, "value");
                }
                m_assert.assertTrue(convertStringToDouble(gstAmountPerItemUi) == gstAmountCalculated,
                        "GST Amount Per Item Calculated Correctly as : <b> " + gstAmountCalculated + "</b>");

                // Validating Net Return With Tax
                Double netReturnWithTaxCalculated = gstAmountCalculated + amountBeforeTaxCalculated;
                m_assert.assertTrue(convertStringToDouble(netReturnWithTaxUI) == netReturnWithTaxCalculated,
                        "Net Return With Tax Calculated Correctly as : <b> " + netReturnWithTaxCalculated + "</b>");

                purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseReturn.input_purchaseTransactionTime, "value");
                purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                m_assert.assertTrue("Purchase Return Transaction time:<b> " + purchaseTransactionTime + "</b>");

                purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseReturn.input_purchaseTransactionDate, "value");
                purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);

                Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseReturn.input_returnNotes, returnNotes);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                        "Save Changes Button CLicked In Return Purchase");


                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving, 5);
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 5);
                for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                    purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
                }

                for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {
                    if (Cls_Generic_Methods.isElementDisplayed(row)) {
                        List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                        String dateTimeFull = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Date | Time")));
                        String purchaseVendorName = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Vendor")));
                        String purchaseNote = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Note")));
                        String purchaseAmount = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Amount")));

                        String date = dateTimeFull.split("\\|")[0].trim();
                        String time = dateTimeFull.split("\\|")[1].trim();
                        String purchaseDateAndTime = date + "|" + time;

                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        String purchaseNetAmountUI = decimalFormat.format(convertStringToDouble(purchaseAmount));

                        if (purchaseDateAndTime.equals(purchaseTransactionDate + "|" + purchaseTransactionTime) &&
                                purchaseNetAmountUI.equalsIgnoreCase(netReturnWithTaxUI) &&
                                purchaseVendorName.equalsIgnoreCase(vendorName) ||
                                purchaseNote.equalsIgnoreCase(returnNotes)) {
                            bPurchaseReturnTransactionFound = true;
                            Cls_Generic_Methods.clickElement(row);
                            Cls_Generic_Methods.customWait(5);
                            break;
                        }
                    }
                }

                m_assert.assertTrue(bPurchaseReturnTransactionFound, " Purchase return transaction found for date : " + purchaseTransactionDate + "|" + purchaseTransactionTime + " and vendor name :" + vendorName);

                boolean printA4InSale = validatePrintButtonFunctionality(oPage_Purchase.button_printA4SizeButton,"Print4 Size In Sale Transaction");
                boolean printA5InSale = validatePrintButtonFunctionality(oPage_Purchase.button_printA5SizeButton,"Print5 Size In Sale Transaction");
                m_assert.assertTrue(printA4InSale," PrintA4 Working In Purchase Return Transaction");
                m_assert.assertTrue(printA5InSale," PrintA5 Working In Purchase Return Transaction");

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseReturn.header_returnInvoiceHeader),
                        " View Return Header Displayed As : Pharmacy  Return Invoice");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_vendorName).contains(vendorFullNameAndAddress),
                        " Vendor Label and value displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_vendorName));

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_mobileNumber).equalsIgnoreCase("Mobile:  7878989098"),
                        " Mobile Label and value displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_mobileNumber));

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_totalCostInView).equalsIgnoreCase("Total Cost:  "+String.valueOf(Double.parseDouble(amountBeforeTaxUI))),
                        " Total Cost Label and value displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_totalCostInView));

                int len = transactionIdInSetting.length();
                String transactionIdInSettingUpdated = String.valueOf(Integer.parseInt(transactionIdInSetting.substring(len-1,len))+1);

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_transactionId).equalsIgnoreCase("Transaction ID:  "+transactionIdInSetting.substring(0,len-1)+transactionIdInSettingUpdated),
                        " Transaction Id Label and value displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_transactionId));


                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_createdBy).equalsIgnoreCase("Created By:  "+EHR_Data.user_PRAkashTest),
                        " Created By Label and value displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_createdBy));

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_DateAndTime).equalsIgnoreCase("Date|Time:  "+purchaseTransactionDate+"  |  "+purchaseTransactionTime),
                        " Date And Time Label and value displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_DateAndTime));


                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_gstNumber).equalsIgnoreCase("GST:  "+vendorGSTno),
                        " GST Label and value displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_gstNumber));

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_returnNoteInView).equalsIgnoreCase("Note:  "+returnNotes),
                        " Note Label and value displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_returnNoteInView));

                for(WebElement eHeader : oPage_PurchaseReturn.list_itemDetailsHeaderList){

                    int index = oPage_PurchaseReturn.list_itemDetailsHeaderList.indexOf(eHeader);
                    String headerValue = Cls_Generic_Methods.getTextInElement(eHeader);
                    if(headerValue.equalsIgnoreCase(itemDetailsHeader[index])){
                        m_assert.assertTrue(headerValue+" Header Present In item Details");
                    }else{
                        m_assert.assertWarn(headerValue+" Header Not Present In item Details");

                    }
                }
                addValueForItemDetails();
                for(WebElement eHeader : oPage_PurchaseReturn.list_itemDetailsValueList){

                    int index = oPage_PurchaseReturn.list_itemDetailsValueList.indexOf(eHeader);
                    String headerValue = Cls_Generic_Methods.getTextInElement(eHeader);
                    if(index == 1){
                        m_assert.assertTrue(!headerValue.isEmpty(),headerValue+" Value Present In item Details");

                    }else{
                    if(headerValue.equalsIgnoreCase(itemDetailsValue.get(index))){
                        m_assert.assertTrue(headerValue+" Value Present In item Details");
                    }else{
                        m_assert.assertWarn(headerValue+" Value Not Present In item Details");

                    }
                    }
                }


                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_amountBeforeTax).equalsIgnoreCase(String.valueOf(Double.parseDouble(amountBeforeTaxUI))),
                        " Amount Before Tax Label and value displayed as : "+Double.parseDouble(amountBeforeTaxUI));
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_gstValueInItemDetails).equalsIgnoreCase(gstAmountPerItemUi),
                        " GST5 Label and value displayed as : "+gstAmountPerItemUi);
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_netReturnWithTaxInView).equalsIgnoreCase(netReturnWithTaxUI),
                        " Net Return With Tax Label and value displayed as : "+netReturnWithTaxUI);

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

    @Test(enabled = true, description = "Validating Purchase Return in Items Lot ")
    public void ValidatePurchaseReturnTransactionInItemLotFunctionality() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        boolean bPurchaseTransactionFoundInLot = false;
        boolean bReturnPurchaseTransactionFoundInLot = false;
        Double stockAfterCalculated = 0.0;
        String stockBeforeReturnUI = "";
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(pharmacyStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {

                // Validating Return Purchase In Items Lots
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, itemDescription);
                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.pressEnter();
                Cls_Generic_Methods.customWait(5);

                int indexOfItemInLot = 0;

                for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
                    List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
                    String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((0)));
                    if (itemDescriptionText.contains(itemDescription)) {
                        Cls_Generic_Methods.clickElement(items);
                        Cls_Generic_Methods.customWait(5);
                        bPurchaseTransactionFoundInLot = true;
                        break;
                    }
                }
                m_assert.assertTrue(bPurchaseTransactionFoundInLot, itemDescription + " Clicked in Items Lot at index " + indexOfItemInLot);

                if (bPurchaseTransactionFoundInLot) {

                    stockBeforeReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockBeforeInViewTemplate);
                    String stockAfterReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockAfterInViewTemplate);
                    String flowTextUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsFlowInViewTemplate);
                    String dateAndTimeUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsDateAndTimeInViewTemplate);

                    String date = dateAndTimeUI.split("\\|")[0].trim();
                    String time = dateAndTimeUI.split("\\|")[1].trim();
                    String purchaseReturnDateAndTime = date + "|" + time;

                    stockAfterCalculated = convertStringToDouble(stockBeforeReturnUI) - convertStringToDouble(returnQuantityValue);

                    if (flowTextUI.equalsIgnoreCase("Out (Purchase Return)") &&
                            Double.toString(stockAfterCalculated).equals(stockAfterReturnUI) &&
                            purchaseReturnDateAndTime.equalsIgnoreCase(purchaseTransactionDate + "|" + purchaseTransactionTime)) {
                        bReturnPurchaseTransactionFoundInLot = true;
                    }
                }
                m_assert.assertTrue(bReturnPurchaseTransactionFoundInLot,
                        " <b> Return Purchase Working Fine, as initial stock was: " + stockBeforeReturnUI +
                                " after return stock is: " + stockAfterCalculated + "</b> ");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Purchase Return in Items Lot ")
    public void ValidatePurchaseReturnSearchFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);

        boolean purchaseReturnRecord = false ;


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(pharmacyStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase Return");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 2);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(searchTypeList[0]),
                        " By Default Search Type Selection Dropdown Displayed correctly as : Transaction ID");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");

                boolean searchResultByTransactionId = CommonActions.selectOptionAndSearch(searchTypeList[0],returnTransactionId);
                Cls_Generic_Methods.customWait();
                if(searchResultByTransactionId) {
                    purchaseReturnRecord = getSearchedRecord(oPage_Purchase.list_transactionPurchaseList,returnTransactionId);
                    m_assert.assertTrue(purchaseReturnRecord,
                            "Search By Transaction Id Worked correctly as Purchase Return found " +
                                    "in the Return page for Id: "+returnTransactionId);
                }

                boolean searchResultByDescription = CommonActions.selectOptionAndSearch(searchTypeList[1],itemDescription);
                Cls_Generic_Methods.customWait();
                if(searchResultByDescription) {
                    purchaseReturnRecord = getSearchedRecordAndValidate(oPage_Purchase.list_transactionPurchaseList,itemDescription);
                    m_assert.assertTrue(purchaseReturnRecord,
                            "Search By Item Description Worked correctly as Return found in the Return page");
                }

                boolean searchResultByGrnNo = CommonActions.selectOptionAndSearch(searchTypeList[2],grnNo);
                Cls_Generic_Methods.customWait();
                if(searchResultByGrnNo) {
                    purchaseReturnRecord = getSearchedRecord(oPage_Purchase.list_transactionPurchaseList,grnNo);
                    m_assert.assertTrue(purchaseReturnRecord,
                            "Search By GRN No Worked correctly as Purchase Return found " +
                                    "in the Return page for Number: "+grnNo);
                }

                getOldRecord(oPage_Purchase.list_transactionPurchaseList);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                boolean searchResultByOldTransactionId = CommonActions.selectOptionAndSearch(searchTypeList[0],oldTransactionId);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldTransactionId) {
                    purchaseReturnRecord = getSearchedRecord(oPage_Purchase.list_transactionPurchaseList,oldTransactionId);
                    m_assert.assertTrue(purchaseReturnRecord,
                            "Search By Old Transaction Id Worked correctly as Purchase Return found " +
                                    "in the Return page for Id: "+oldTransactionId);
                }

                boolean searchResultByOldDescription = CommonActions.selectOptionAndSearch(searchTypeList[1],oldDescription);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldDescription) {
                    purchaseReturnRecord = getSearchedRecordAndValidate(oPage_Purchase.list_transactionPurchaseList,oldDescription);
                    m_assert.assertTrue(purchaseReturnRecord,
                            "Search By Old Item Description Worked correctly as Return found in the Return page");
                }

                boolean searchResultByOldGrnNo = CommonActions.selectOptionAndSearch(searchTypeList[2],oldGrnNo);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldGrnNo) {
                    purchaseReturnRecord = getSearchedRecord(oPage_Purchase.list_transactionPurchaseList,oldGrnNo);
                    m_assert.assertTrue(purchaseReturnRecord,
                            "Search By Old GRN No Worked correctly as Purchase Return found " +
                                    "in the Return page for Number: "+oldGrnNo);
                }

                boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch(searchTypeList[0],oldTransactionId+23);
                m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect Id Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");

                boolean searchResultByWrongDescription = CommonActions.selectOptionAndSearch(searchTypeList[1],oldDescription+"old");
                m_assert.assertFalse(searchResultByWrongDescription,"  Search With Incorrect Description Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");

                boolean searchResultByWrongGrn = CommonActions.selectOptionAndSearch(searchTypeList[2],oldDescription+"old");
                m_assert.assertFalse(searchResultByWrongGrn,"  Search With Incorrect GRN Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty as clear button is working correctly");
                boolean selectOption = CommonActions.selectOption("Transaction ID");
                m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase("Transaction ID"),
                        " Search Type Selected  as : Transaction ID");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(searchTypeList[index])){
                        m_assert.assertTrue( typeText+" Search Type Present In Dropdown List");
                        Cls_Generic_Methods.customWait();
                    }else{
                        m_assert.assertFalse( typeText+" Search Type Not Present In Dropdown List");

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
    }


    public boolean createPurchaseTransaction() {

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
            vendorFullNameAndAddress = vendorName + " - " + vendor_address;

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
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                            "Bill Type Selected:<b> " + billType + " </b>");
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber),
                            "Bill Number: <b> " + billNumber + " </b>");
                    Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                            "Date of bill selected:<b> " + Cls_Generic_Methods.getTextInElement(oPage_Purchase.input_todayBillDate) + " </b>");

                    purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                    purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                    m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                  //  purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                    purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                   if(purchaseTransactionTime.length() == 6){
                       purchaseTransactionTime = "0"+purchaseTransactionTime;
                   }
                    m_assert.assertTrue("Requisition order time:<b> " + purchaseTransactionTime + "</b>");

                    purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                    purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);

                    m_assert.assertTrue("Purchase Transaction date:<b> " + purchaseTransactionDate + "|" + purchaseTransactionTime + "</b>");
                    String totalNetAmountBeforeOtherCharges = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");

                    // Verifying Net Amount Calculation After Adding other Charges

                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_otherCharges, otherChargesName),
                            "Other Charges: <b> " + otherChargesName + " </b>");
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_otherChargesAmount, otherCharges);

                    String netAmountCalculatedAfterOtherCharges = InventoryCommonActions.getOtherChargesNetAmount(otherChargesAmount, totalNetAmountBeforeOtherCharges, "plus");
                    totalNetAmountAfterOtherChargesAddition = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount, "value");

                    m_assert.assertTrue(netAmountCalculatedAfterOtherCharges.equalsIgnoreCase(totalNetAmountAfterOtherChargesAddition),
                            "Other Charges addition working correctly and adding successfully in net amount");

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

    public void getGST_valueFromSetting() {
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
                if (storeName.contains(pharmacyStoreName.split("-")[0])) {
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

            if (vendorGSTno != null && storeGSTno != null) {
                if (vendorGSTno.substring(0, 2).equals(storeGSTno.substring(0, 2))) {
                    gstTypeIGST = false;
                    m_assert.assertInfo("GST type = CGST + SGST");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to get GST no. from Organisation Setting " + e);
        }
    }

    public void addValueForItemDetails(){

        itemDetailsValue.add("1");
        itemDetailsValue.add("");
        itemDetailsValue.add(itemDescription);
        itemDetailsValue.add(String.valueOf(Double.parseDouble(returnQuantityValue)));
        itemDetailsValue.add("");
        itemDetailsValue.add("");
        itemDetailsValue.add(itemPrice);
        itemDetailsValue.add("");
        itemDetailsValue.add(itemPrice);

    }

    public boolean validatePrintButtonFunctionality(WebElement printButton , String printButtonName){

        Page_Sale oPage_Sale = new Page_Sale(driver);
        boolean bPrintWork = false ;

        try {
            int preWindowsCount = driver.getWindowHandles().size();
            String initialWindowHandle = driver.getWindowHandle();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(printButton),
                    printButtonName+" Button Displayed and Clicked ");
            Cls_Generic_Methods.customWait(2);
            if(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_printDropdownFirstOptionInAdvanceReceipt)){
                Cls_Generic_Methods.clickElement(oPage_Sale.list_printDropdownFirstOptionInAdvanceReceipt);
                Cls_Generic_Methods.customWait();
            }else {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_printDropdownFirstOption)) {
                    Cls_Generic_Methods.clickElement(oPage_Sale.list_printDropdownFirstOption);
                    Cls_Generic_Methods.customWait();
                }
            }
            Cls_Generic_Methods.customWait(6);
            int postWindowsCount = driver.getWindowHandles().size();

            bPrintWork = postWindowsCount > preWindowsCount ;
            m_assert.assertTrue(bPrintWork, "Validated Print --> Sale Transaction Print page opened");

            for (String currentWindowHandle : driver.getWindowHandles()) {
                if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                    driver.switchTo().window(currentWindowHandle);
                }
            }
            driver.close();
            driver.switchTo().window(initialWindowHandle);
            Cls_Generic_Methods.customWait(4);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return bPrintWork ;
    }

    public boolean getSearchedRecord(List<WebElement> listOfElement , String expectedString){
        Page_PurchaseReturn oPage_PurchaseReturn = new Page_PurchaseReturn(driver);

        boolean recordFound = false ;
        try {
            for (WebElement eDate : listOfElement) {
                Cls_Generic_Methods.clickElement(eDate);
                Cls_Generic_Methods.customWait( 5);
                String transactionIdUi = Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_transactionId);
                String grnNoUI = Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_grnId);
                if(transactionIdUi.contains(expectedString)){
                    recordFound = true ;
                    break;
                }else if(grnNoUI.contains(expectedString)){
                    recordFound = true ;
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  recordFound ;
    }
    public boolean getSearchedRecordAndValidate(List<WebElement> listOfElement,String expectedString){

        Page_PurchaseReturn oPage_PurchaseReturn = new Page_PurchaseReturn(driver);
        boolean recordFound = false ;
        try {
            for (WebElement eDate : listOfElement) {
                recordFound = false;
                Cls_Generic_Methods.clickElement(eDate);
                Cls_Generic_Methods.customWait( 5);
                for(WebElement item : oPage_PurchaseReturn.list_itemDetailsValueList){
                    String itemText = Cls_Generic_Methods.getTextInElement(item);
                    if (itemText.contains(expectedString)) {
                        recordFound = true;
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  recordFound ;
    }
    public void getOldRecord(List<WebElement> listOfElement){

        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_PurchaseReturn oPage_PurchaseReturn = new Page_PurchaseReturn(driver);

        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                    " Filter Dropdown Button Clicked");
            Cls_Generic_Methods.customWait(2);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_previousMonthFilter),
                    " Previous Month Selected as Filter");
            Cls_Generic_Methods.customWait(2);
            for (WebElement eDate : listOfElement) {

                int index = listOfElement.indexOf(eDate);
                if(index > 0){
                    Cls_Generic_Methods.clickElement(eDate);
                    Cls_Generic_Methods.customWait(3);
                    oldTransactionId= Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_transactionId);
                    oldGrnNo = Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.text_grnId);
                    oldDescription = Cls_Generic_Methods.getTextInElement(oPage_PurchaseReturn.list_itemDetailsValueList.get(2));
                    if(!oldGrnNo.isEmpty()){
                        break;
                    }
                }

            }


        }catch (Exception e){
            e.printStackTrace();
            m_assert.assertFatal(e.toString());
        }

    }


}

