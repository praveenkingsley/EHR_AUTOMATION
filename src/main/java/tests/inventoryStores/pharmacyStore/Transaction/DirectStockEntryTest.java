package tests.inventoryStores.pharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Transaction.Page_DirectStockEntry;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_SON;
import tests.inventoryStores.pharmacyStore.InventorySearchTest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static pages.commonElements.CommonActions.*;

public class DirectStockEntryTest extends TestBase {

    static String sStoreName = "Pharmacy automation- Pharmacy";
    static String sTotalStockBeforeTxn = "";
    static String myMedicationName = "Automation Test SON";
    static String subStore = "Default";
    static String sUnitCostWithTax = "";
    static String sTaxPercentage = "5%";
    static double dTaxPercentage = 0.00;
    static String packageQuantity = "10";
    static String packageQuantityEdit = "5";
    static String subPackageQuantity = "5";
    static String unitQuantity = "5";
    static String freeUnitQuantity = "5";
    static double dTotalCost = 0.0;
    static String sTxnDate = "";
    static String sTxnTime = "";
    String dseTableHeaderList[] = {"Date | Time","Note","Vendor Name","Amount","Status"};
    String[] viewAddLotKeyList = {"Description:","HSN No:","Item Code:",
            "Category:","Dosage:","Dispensing Unit:","Package Type:","Sub package/Package:","Unit/Sub package:"};
    List<String> viewAddLotValueList = new ArrayList<>();
    List <String> viewAddLotLabelList = new ArrayList<>();
    String sEditBatchNumber = "BT_Edit_"+CommonActions.getRandomString(5);
    static String batchNo = "BT"+CommonActions.getRandomString(5);
    static String totalCost = "";
    List<String> batchNumberList = new ArrayList<String>();
    List<String> expiryDateList = new ArrayList<String>();
    List<String> unitCostList = new ArrayList<String>();
    List<String> totalCostList = new ArrayList<String>();
    List<String> totalQuantityList = new ArrayList<String>();
    List<String> transactionDetailsDataList = new ArrayList<String>();
    String transactionNotes = "Transaction_notes" + getRandomString(4);
    String documentNumber = "Document_Number" + getRandomString(4);
    Double totalCostCalculatedForAllItem = 0.0;
    String totalCostIncludingTax = "";
    String sDiscount = "10.0";
    String sEditDiscount = "20.0";

    String sUnitCostWOList[] = {"100","100.40"};
    String sSellingPriceList[] = {"200","250.40"};
    String netAmountAfterDiscount = "";
    public static final String itemDescription = "TransactionPurchase_" + getRandomString(4);
    String vendorName = "Supplier ABC";
    String billType = "Bill";
    String billNumber = "BILL_" + getRandomNumber();
    String challanType = "Challan";
    String challanNumber = "Challan_" + getRandomNumber();
    String storeGSTno;
    String vendor_address;
    String vendorGSTno;
    String vendorFullNameAndAddress,billDate,transactionId;
    boolean gstTypeIGST;



    @Test(enabled = true, description = " Validating Create Direct Stock Entry Transaction Functionality")
    public void validatingCreateDirectStockEntryTransaction() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        Page_SON oPage_SON = new Page_SON(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_DirectStockEntry oPage_DirectStockEntry = new Page_DirectStockEntry(driver);

        boolean addItemStatus = false ;
        boolean vendorFound = false;


        try {

            //Opening Pharmacy Store

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            getGST_valueFromSetting();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait(5);
            CommonActions.selectStoreOnApp(sStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            //Getting total stock of the item from Master
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

            boolean existingItemPresent = selectItemFromItemMasterList(myMedicationName);
            if(!existingItemPresent){

                addItemStatus = CommonActions.addItemInInventory(myMedicationName);
                m_assert.assertTrue(addItemStatus, " New Item Added successfully as :<b> "+myMedicationName);
                for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
                    if (Cls_Generic_Methods.isElementDisplayed(itemData)) {

                        List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));

                        String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));

                        if (itemDescriptionName.contains(myMedicationName)) {
                            existingItemPresent = true ;
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
                                    "<b> "+myMedicationName + " </b> Item Clicked from Item Master List");
                            Cls_Generic_Methods.customWait(5);
                            break;
                        }
                    }
                }
            }
            m_assert.assertTrue(existingItemPresent ," Item Found In Item Master as : <b> "+myMedicationName+"</b>");


            sTotalStockBeforeTxn = Cls_Generic_Methods.getTextInElement(oPage_Master.list_itemStockOnItemMaster.get(0)).trim();
            addListDataForItemValuesOnAddLot();

            //Opening the DSE Transaction Page and validate page

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");

            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_refreshPurchaseTransaction),
                    " <b> Refresh Button </b> Displayed In Direct Stock Entry Table");
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.button_todayFilterButton),
                    " <b> Today Filter Button </b> Displayed In Direct Stock Entry Table");
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.input_vendorSearchBox),
                    " <b> Vendor Name </b> Search Text Box Displayed In Purchase GRN Table");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_vendorSearchBox,"placeholder").
                    equalsIgnoreCase("Search by Vendor Name")," Place Holder Showing Correct In Search Box as Search by Vendor Name");

            for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                int indexOfHeader = oPage_Purchase.list_purchaseTransactionHeaderList.indexOf(purchaseHeaderList);
                String sValueOfHeader = Cls_Generic_Methods.getTextInElement(purchaseHeaderList);
                if(sValueOfHeader.equalsIgnoreCase(dseTableHeaderList[indexOfHeader])){
                    m_assert.assertTrue( "<b>"+sValueOfHeader + " </b> Header Present In Direct Stock Entry Data Table List");
                }else{
                    m_assert.assertWarn(sValueOfHeader + " Header Not Present In Direct Stock Entry Data Table List");
                }
            }

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SON.button_addNewButton),
                    "New Button Displayed and Clicked");

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


            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.header_DirectStockEntry, 8),
                    " Add Direct Stock Entry Template Opened and Header Displayed as : <b> "+
                            Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.header_DirectStockEntry)+"</b>");

            sTxnDate = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionDate, "value");
            sTxnTime = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionTime, "value");
            sTxnTime = sTxnTime.replaceAll("\\s+", "");
            sTxnTime = CommonActions.getRequiredFormattedDateTime("K:mma","hh:mma",sTxnTime);
            sTxnTime = sTxnTime.replace("am", "AM").replace("pm","PM");
            sTxnDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", sTxnDate);
            m_assert.assertTrue("Direct Stock Entry Transaction date:<b> " + sTxnDate + "|" +sTxnTime+"</b>");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.button_saveChanges, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_saveChanges), "Clicked save changes button to validate required fields");

            //Validating Required fields
            m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.label_billTypeRequired, "class").contains("error"),
                    "Validated the Bill Type is required");

            //Adding medication for DSE transaction

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

            validatingDataInViewItemRHSSide(oPage_SON.list_keysAndValuesInAddLot,viewAddLotKeyList,viewAddLotValueList);
            addListDataForLabelsOnAddLot();
            validatingDataTableHeadersInItemMaster(oPage_SON.list_labelsInStockAddLot,viewAddLotLabelList);
            batchNumberList.add(batchNo);
            boolean bLotCreated = createLot(sUnitCostWOList[0],batchNo, sTaxPercentage,packageQuantity,subPackageQuantity,unitQuantity,freeUnitQuantity,sSellingPriceList[0]);
            if(bLotCreated){

                getAddedItemStockCalculation();

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_totalAmtIncludingTaxLabel),
                        " Total Amt Including Tax Label is Displayed ");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_discountLabel),
                        " Discount Label is Displayed ");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_netAmountLabel),
                        " Net Amount Label is Displayed ");

                totalCostIncludingTax = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_totalAmountIncludingTax,"value");
                m_assert.assertTrue(totalCostCalculatedForAllItem == Double.parseDouble(totalCostIncludingTax),
                        " Total Cost Including Tax Calculated Correctly and Displayed as : <b> "+totalCostIncludingTax+"</b>");

                String initialDiscountValue = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_discountBox,"value");

                m_assert.assertTrue(initialDiscountValue.isEmpty()," Discount is Initial Empty as no value entered as : <b> Empty </b>");

                String netAmountBeforeDiscount = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_netAmount,"value");

                m_assert.assertTrue(netAmountBeforeDiscount.equalsIgnoreCase(totalCostIncludingTax),
                        " Net Amount Calculated Correctly Before Discount and Displayed as : <b> "+netAmountBeforeDiscount+"</b>");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_discountBox,"10"),
                        " Discount Entered as : <b> 10 </b> ");
                Cls_Generic_Methods.customWait();
                netAmountAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_netAmount,"value");
                Double netAmountAfterDiscountCalculated = totalCostCalculatedForAllItem - Double.parseDouble("10");

                m_assert.assertTrue(Double.parseDouble(netAmountAfterDiscount) == netAmountAfterDiscountCalculated,
                        " Net Amount Calculated Correctly After Discount and Displayed as : <b> "+netAmountAfterDiscount+"</b>");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_transactionNote, transactionNotes),
                        " Transaction Notes is Displayed and value Entered as :<b> " +transactionNotes+ "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.select_billType),
                        " Bill Type Select Box Displayed");
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_DirectStockEntry.select_billType, billType),
                        "Selected Bill Type as <b>" + billType + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_billNumber, billNumber),
                        "Entered<b> " + billNumber + "</b> in Bill Number");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.input_billDate),
                        " Bill Date Input Box Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        " Bill Date Entered as : "+ Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_billDate,"value"));

                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_vendorNameField,"value").contains(vendorFullNameAndAddress),
                        " Vendor Name Dropdown Displayed and and Value Show correctly as :  "+vendorFullNameAndAddress);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_saveChanges),
                        " Save Changes Button Clicked In Direct Stock Entry Template ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.button_addNewButton, 15);

                boolean bDirectStockTransactionFound = getDirectStockTransactionFromTransactionList(sTxnDate + "|" +sTxnTime,
                        transactionNotes,vendorFullNameAndAddress,totalCostIncludingTax,"Open");

                m_assert.assertTrue(bDirectStockTransactionFound, "Direct Stock Entry Transaction Found, Created Successfully for date and time :<b> "
                        +sTxnDate + "|" +sTxnTime +"</b>" );


            }else{
                m_assert.assertTrue(bLotCreated,"Lot Not added In DSE Template");
            }

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            Cls_Generic_Methods.customWait();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating View Direct Stock Entry Transaction")
    public void validateViewDirectStockEntryFunctionality() {

        Page_SON oPage_SON = new Page_SON(driver);
        Page_Purchase oPage_Purchase =  new Page_Purchase(driver);
        Page_DirectStockEntry oPage_DirectStockEntry = new Page_DirectStockEntry(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {

                // Open Pharmacy Store

                CommonActions.selectStoreOnApp(sStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                // Dynamic Wait
                Cls_Generic_Methods.customWait();

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);

                boolean bDirectStockTransactionFound = getDirectStockTransactionFromTransactionList(sTxnDate + "|" +sTxnTime,
                        transactionNotes,vendorFullNameAndAddress,totalCostIncludingTax,"Open");

                m_assert.assertTrue(bDirectStockTransactionFound," Direct Stock Entry Transaction Found And Clicked In List");

                if(bDirectStockTransactionFound){

                    int preWindowsCount = driver.getWindowHandles().size();
                    String initialWindowHandle = driver.getWindowHandle();
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SON.button_printA4SizeButton), "Displayed and Clicked PrintA4 button");
                    Cls_Generic_Methods.customWait(8);
                    int postWindowsCount = driver.getWindowHandles().size();

                    m_assert.assertTrue(postWindowsCount > preWindowsCount, "Validated Print --> DSE Print page opened");

                    for (String currentWindowHandle : driver.getWindowHandles()) {
                        if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                            driver.switchTo().window(currentWindowHandle);
                        }
                    }
                    driver.close();
                    driver.switchTo().window(initialWindowHandle);
                    Cls_Generic_Methods.customWait();


                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SON.button_printA5SizeButton), "Displayed and Clicked PrintA5 button");
                    Cls_Generic_Methods.customWait(8);
                    postWindowsCount = driver.getWindowHandles().size();

                    m_assert.assertTrue(postWindowsCount > preWindowsCount, "Validated Print --> DSE Print page opened");

                    for (String currentWindowHandle : driver.getWindowHandles()) {
                        if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                            driver.switchTo().window(currentWindowHandle);
                        }
                    }
                    driver.close();
                    driver.switchTo().window(initialWindowHandle);
                    Cls_Generic_Methods.customWait();

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_Approve),
                            " Approve Button Displayed In View Direct Stock Entry");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_editSONButton),
                            " Edit Button Displayed In View Direct Stock Entry");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_cancelSONButton),
                            " Cancel Button Displayed In View Direct Stock Entry");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.header_viewDirectStockEntryNoteHeader).equalsIgnoreCase(transactionNotes),
                            transactionNotes+" Notes as header displayed in view Direct Stock Entry");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_statusKeyAndValue).
                            equalsIgnoreCase("Status:    Open"),"<b> Status:    Open  </b> displayed in view DSE");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_transactionIDKeyAndValue).contains("Transaction ID"),
                            "<b>"+Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_transactionIDKeyAndValue)+"  </b> displayed in view DSE");
                    transactionId = Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_transactionIDKeyAndValue).split(" ")[2].trim();
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_billNumberAndDateKeyAndValue).
                            equalsIgnoreCase("Bill Number:     "+billNumber+"    |   Bill Date:     "+Cls_Generic_Methods.getTodayDate()),
                            "<b>"+ Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_billNumberAndDateKeyAndValue) +" </b> displayed in view DSE");

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_transactionDetailsSectionText),
                            " Transaction Details Section Displayed");


                    String expiryDateInTable = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", expiryDateList.get(0));

                    transactionDetailsDataList.add(myMedicationName+"-500mg");
                    transactionDetailsDataList.add(batchNo);
                    transactionDetailsDataList.add("");
                    transactionDetailsDataList.add(expiryDateInTable);
                    transactionDetailsDataList.add(String.valueOf(Double.parseDouble(totalQuantityList.get(0))));
                    transactionDetailsDataList.add(String.valueOf(Double.parseDouble(totalCostIncludingTax)));


                    for(WebElement eTransactionTable : oPage_Purchase.list_valuesInTransactionDetailsTable){

                        int index = oPage_Purchase.list_valuesInTransactionDetailsTable.indexOf(eTransactionTable);
                        String eTransactionValue = Cls_Generic_Methods.getTextInElement(eTransactionTable);
                        if(eTransactionValue.equalsIgnoreCase(transactionDetailsDataList.get(index))){
                            m_assert.assertTrue("<b>"+eTransactionValue + " </b> Value present in Transaction Details table ");
                        }else{
                            m_assert.assertFalse(eTransactionValue + " Value not present in table ");
                        }

                    }


                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_totalAmtIncludingTaxLabel),
                            " Total Amt Including Tax Label is Displayed In View Direct Stock Entry ");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_discountLabel),
                            " Discount Label is Displayed In View Direct Stock Entry ");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_netAmountLabel),
                            " Net Amount Label is Displayed In View Direct Stock Entry ");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_amountPaidLabel),
                            " Amount Paid Label is Displayed In View Direct Stock Entry ");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_amountRemainingLabel),
                            " Amount Remaining Label is Displayed In View Direct Stock Entry ");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_totalAmtIncTaxLabelValue).trim().
                                    equalsIgnoreCase(String.valueOf(Double.parseDouble(totalCostIncludingTax))),
                            " Total Amt Including Tax Label Value Correctly as : <b> "+ totalCostIncludingTax+"</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_discountLabelValue).trim().equalsIgnoreCase(sDiscount),
                            " Discount Label Value Correctly as : <b> "+ sDiscount+"</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_netAmountLabelValue).trim().
                                    equalsIgnoreCase(String.valueOf(Double.parseDouble(netAmountAfterDiscount))),
                            " Net Amount Label Value Correctly as : <b> "+ netAmountAfterDiscount+"</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_amountPaidLabelValue).trim().equalsIgnoreCase("0.0"),
                            " Amount Paid Label Value Correctly as : <b> "+ "0.0"+"</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_amountRemainingLabelValue).
                                    equalsIgnoreCase(String.valueOf(Double.parseDouble(netAmountAfterDiscount))),
                            " Amount Remaining Label Value Correctly as : <b> "+ netAmountAfterDiscount+"</b>");

                }else{
                    m_assert.assertFalse(bDirectStockTransactionFound," Direct Stock Entry Transaction Not Found In List");

                }

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

    @Test(enabled = true, description = "Validating Edit Direct Stock Entry In Transaction")
    public void validateEditDirectStockEntryFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_SON oPage_SON = new Page_SON(driver);
        Page_DirectStockEntry oPage_DirectStockEntry = new Page_DirectStockEntry(driver);


        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {

                CommonActions.selectStoreOnApp(sStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");

                boolean bDirectStockTransactionFound = getDirectStockTransactionFromTransactionList(sTxnDate + "|" +sTxnTime,
                        transactionNotes,vendorFullNameAndAddress,totalCostIncludingTax,"Open");

                m_assert.assertTrue(bDirectStockTransactionFound ," Direct Stock Entry Transaction Found And Clicked");

                if(bDirectStockTransactionFound) {

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_editSONButton),
                            "Edit Button Clicked In Direct Stock Entry Transaction");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.button_addNewButton, 15);

                    sTxnDate = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionDate, "value");
                    sTxnTime = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionTime, "value");
                    sTxnTime = sTxnTime.replaceAll("\\s+", "");
                    sTxnTime = CommonActions.getRequiredFormattedDateTime("K:mma","hh:mma",sTxnTime);
                    sTxnTime = sTxnTime.replace("am", "AM").replace("pm","PM");
                    sTxnDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", sTxnDate);
                    m_assert.assertTrue("Direct Stock Entry Transaction date:<b> " + sTxnDate + "|" +sTxnTime+"</b>");

                    m_assert.assertTrue(" Edit Direct Stock Entry Transaction date:<b> " + sTxnDate + "|" +sTxnTime+"</b>");

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

                    m_assert.assertInfo(myMedicationFound, "Required medication found in search");
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.header_addNewLot, 15),
                            "Add Lot Template Opened and Displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_SON.header_addNewLot));

                    batchNumberList.add(sEditBatchNumber);
                    boolean bLotCreated = createLot(sUnitCostWOList[1],sEditBatchNumber, sTaxPercentage,packageQuantityEdit,subPackageQuantity,unitQuantity,freeUnitQuantity,sSellingPriceList[1]);

                    if(bLotCreated){
                        getAddedItemStockCalculation();

                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_totalAmtIncludingTaxLabel),
                                " Total Amt Including Tax Label is Displayed ");
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_discountLabel),
                                " Discount Label is Displayed ");
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_netAmountLabel),
                                " Net Amount Label is Displayed ");

                        totalCostIncludingTax = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_totalAmountIncludingTax,"value");

                        m_assert.assertTrue(totalCostCalculatedForAllItem == Double.parseDouble(totalCostIncludingTax),
                                " Total Cost Including Tax Calculated Correctly and Displayed as : <b> "+totalCostIncludingTax+"</b>");
                        String initialDiscountValue = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_discountBox,"value");

                        m_assert.assertTrue(initialDiscountValue.equalsIgnoreCase("10.0")," Discount value displayed as <b> 10.0 </b> ");

                        String netAmountBeforeDiscount = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_netAmount,"value");

                        m_assert.assertTrue((Double.parseDouble(netAmountBeforeDiscount)+Double.parseDouble("10")) == totalCostCalculatedForAllItem,
                                " Net Amount Calculated Correctly Before Discount and Displayed as : <b> "+netAmountBeforeDiscount+"</b>");

                        Cls_Generic_Methods.clearValuesInElement(oPage_DirectStockEntry.input_discountBox);
                        m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_discountBox,"20"),
                                " Discount Entered as : <b> 20 </b>");
                        Cls_Generic_Methods.customWait();
                        netAmountAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_netAmount,"value");
                        Double netAmountAfterDiscountCalculated = totalCostCalculatedForAllItem - Double.parseDouble("20");

                        m_assert.assertTrue(Double.parseDouble(netAmountAfterDiscount) == netAmountAfterDiscountCalculated,
                                " Net Amount Calculated Correctly After Discount and Displayed as : <b> "+netAmountAfterDiscount+"</b>");

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_saveChanges),
                                " Save Changes Button Clicked In Direct Stock Entry Template ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.button_addNewButton, 15);

                        boolean bEditDSETransactionFound = getDirectStockTransactionFromTransactionList(sTxnDate + "|" +sTxnTime,
                                transactionNotes,vendorFullNameAndAddress,totalCostIncludingTax,"Open");

                        m_assert.assertTrue(bEditDSETransactionFound, "Edit Direct Stock Entry Transaction Found, Created Successfully for date and time <b> "
                                +sTxnDate + "|" +sTxnTime +"</b>");

                    }

                }
                else{
                    m_assert.assertTrue("Transaction Not found or not clicked");
                }
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

    @Test(enabled = true, description = "Validate The Approve Direct Stock Entry Transaction")
    public void validateApprovedDirectStockEntryTransaction() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        Page_SON oPage_SON = new Page_SON(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_DirectStockEntry oPage_DirectStockEntry = new Page_DirectStockEntry(driver);


        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            //Selecting created SON transaction
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");

            boolean bDSETransactionFound = getDirectStockTransactionFromTransactionList(sTxnDate + "|" +sTxnTime,
                    transactionNotes,vendorFullNameAndAddress,totalCostIncludingTax,"Open");

            m_assert.assertTrue(bDSETransactionFound, " Direct Stock Entry Transaction Found And Clicked");

            if (bDSETransactionFound) {


                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_Approve),
                        "Clicked Approve button In RHS Side");
                Cls_Generic_Methods.customWait(4);

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_printA4SizeButton),
                        " Print A4 Button Displayed In View Direct Stock Entry");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_printA5SizeButton),
                        " Print A5 Button Displayed In View Direct Stock Entry");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.header_viewDirectStockEntryNoteHeader).equalsIgnoreCase(transactionNotes),
                        transactionNotes+" Notes as header displayed in view Direct Stock Entry");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_statusKeyAndValue).
                        equalsIgnoreCase("Status:    Approved"),"<b> Status:    Approved  </b> displayed in view DSE");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_transactionIDKeyAndValue).contains("Transaction ID"),
                        "<b>"+Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_transactionIDKeyAndValue)+"  </b> displayed in view DSE");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_billNumberAndDateKeyAndValue).
                                equalsIgnoreCase("Bill Number:     "+billNumber+"    |   Bill Date:     "+Cls_Generic_Methods.getTodayDate()),
                        "<b>"+ Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_billNumberAndDateKeyAndValue) +" </b> displayed in view DSE");

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_transactionDetailsSectionText),
                        " Transaction Details Section Displayed");

                String expiryDateInTable = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", expiryDateList.get(0));


                transactionDetailsDataList.add(myMedicationName+"-500mg");
                transactionDetailsDataList.add(sEditBatchNumber);
                transactionDetailsDataList.add("");
                transactionDetailsDataList.add(expiryDateInTable);
                transactionDetailsDataList.add(String.valueOf(Double.parseDouble(totalQuantityList.get(1))));
                transactionDetailsDataList.add(String.valueOf(Double.parseDouble(totalCostList.get(1))));

                for(WebElement eTransactionTable : oPage_Purchase.list_valuesInTransactionDetailsTable){

                    int index = oPage_Purchase.list_valuesInTransactionDetailsTable.indexOf(eTransactionTable);
                    String eTransactionValue = Cls_Generic_Methods.getTextInElement(eTransactionTable);
                    if(eTransactionValue.equalsIgnoreCase(transactionDetailsDataList.get(index))){
                        m_assert.assertTrue("<b>"+eTransactionValue + " </b> Value present in Transaction Detailstable ");
                    }else{
                        m_assert.assertFalse(eTransactionValue + " Value not present in table ");
                    }

                }


                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_totalAmtIncludingTaxLabel),
                        " Total Amt Including Tax Label is Displayed In View Direct Stock Entry ");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_discountLabel),
                        " Discount Label is Displayed In View Direct Stock Entry ");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_netAmountLabel),
                        " Net Amount Label is Displayed In View Direct Stock Entry ");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_amountPaidLabel),
                        " Amount Paid Label is Displayed In View Direct Stock Entry ");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_amountRemainingLabel),
                        " Amount Remaining Label is Displayed In View Direct Stock Entry ");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_totalAmtIncTaxLabelValue).trim().
                                equalsIgnoreCase(String.valueOf(Double.parseDouble(totalCostIncludingTax))),
                        " Total Amt Including Tax Label Value Correctly as : <b> "+ totalCostIncludingTax+"</b>");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_discountLabelValue).trim().equalsIgnoreCase(sEditDiscount),
                        " Discount Label Value Correctly as : <b> "+ sEditDiscount+"</b>");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_netAmountLabelValue).trim().
                                equalsIgnoreCase(String.valueOf(Double.parseDouble(netAmountAfterDiscount))),
                        " Net Amount Label Value Correctly as : <b> "+ netAmountAfterDiscount+"</b>");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_amountPaidLabelValue).trim().equalsIgnoreCase("0.0"),
                        " Amount Paid Label Value Correctly as : <b> 0.0 </B> ");

                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_amountRemainingLabelValue).
                                equalsIgnoreCase(String.valueOf(Double.parseDouble(netAmountAfterDiscount))),
                        " Amount Remaining Label Value Correctly as : <b> "+ netAmountAfterDiscount+"</b>");

                bDSETransactionFound = getDirectStockTransactionFromTransactionList(sTxnDate + "|" +sTxnTime,
                        transactionNotes,vendorFullNameAndAddress,totalCostIncludingTax,"Approved");

                m_assert.assertTrue(bDSETransactionFound , " Approved Status Changes and Transaction Found In List ");

                //Opening and Validating the transaction in Master Page

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, myMedicationName);
                Cls_Generic_Methods.customWait(4);
                oPage_Master.input_itemNameSearchInItemMaster.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);
                m_assert.assertInfo("Total stock of the variant <b> " + myMedicationName + "</b> before txn is <b>" + sTotalStockBeforeTxn + " </b>");
                String sTotalStockAfterTxnOnUI = Cls_Generic_Methods.getTextInElement(oPage_Master.list_itemStockOnItemMaster.get(0)).trim();
                double totalQuantity = Double.parseDouble(totalQuantityList.get(0)) + Double.parseDouble(totalQuantityList.get(1));
                double dTotalStockAfterTxn = Double.parseDouble(sTotalStockBeforeTxn) +totalQuantity;

                m_assert.assertTrue(dTotalStockAfterTxn == Double.parseDouble(sTotalStockAfterTxnOnUI),
                        "Validated the total stock of variant <b>" + myMedicationName + " </b>after txn is <b>" + sTotalStockAfterTxnOnUI);
                Cls_Generic_Methods.customWait();


            } else {
                m_assert.assertInfo("Required DSE Transaction Not Found on Direct Stock Entry Transaction Page");
            }

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            Cls_Generic_Methods.customWait();


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Cancel Direct Stock Entry Transaction")
    public void cancelDirectStockEntryTransaction() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Master oPage_Master = new Page_Master(driver);
        Page_SON oPage_SON = new Page_SON(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_DirectStockEntry oPage_DirectStockEntry = new Page_DirectStockEntry(driver);


        boolean addItemStatus = false ;
        boolean  vendorFound = false ;


        try {

            //Opening Pharmacy Store

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            //Getting total stock of the item from Master
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

            boolean existingItemPresent = selectItemFromItemMasterList(myMedicationName);
            if(!existingItemPresent){

                addItemStatus = CommonActions.addItemInInventory(myMedicationName);
                m_assert.assertTrue(addItemStatus, " New Item Added successfully as :<b> "+myMedicationName);
                for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {
                    if (Cls_Generic_Methods.isElementDisplayed(itemData)) {

                        List<WebElement> itemDetailsInRow = itemData.findElements(By.xpath("./child::*"));

                        String itemDescriptionName = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get((1)));

                        if (itemDescriptionName.contains(myMedicationName)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
                                    "<b> "+myMedicationName + " </b> Item Clicked from Item Master List");
                            Cls_Generic_Methods.customWait(5);
                            break;
                        }
                    }
                }
            }

            sTotalStockBeforeTxn = Cls_Generic_Methods.getTextInElement(oPage_Master.list_itemStockOnItemMaster.get(0)).trim();
            addListDataForItemValuesOnAddLot();
            //Opening the SON Transaction Page and validate page

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SON.button_addNewButton),
                    "New Button Displayed and Clicked");

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

            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.header_DirectStockEntry, 8),
                    " Add Direct Stock Entry Template Opened and Header Displayed as : "+ Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.header_DirectStockEntry));

            sTxnDate = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionDate, "value");
            sTxnTime = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_transactionTime, "value");
            sTxnTime = sTxnTime.replaceAll("\\s+", "");
            sTxnTime = CommonActions.getRequiredFormattedDateTime("K:mma","hh:mma",sTxnTime);
            sTxnTime = sTxnTime.replace("am", "AM").replace("pm","PM");
            sTxnDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", sTxnDate);
            m_assert.assertTrue("Direct Stock Entry Transaction date:<b> " + sTxnDate + "|" +sTxnTime+"</b>");


            //Adding medication for Direct Stock Entry transaction

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

            m_assert.assertInfo(myMedicationFound, "Required medication found in search");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SON.header_addNewLot, 15),
                    "Add Lot Template Opened and Displayed as : "+Cls_Generic_Methods.getTextInElement(oPage_SON.header_addNewLot));

            boolean bLotCreated = createLot(sUnitCostWOList[0],batchNo, sTaxPercentage,packageQuantity,subPackageQuantity,unitQuantity,freeUnitQuantity,sSellingPriceList[0]);
            if(bLotCreated){


                Cls_Generic_Methods.customWait();
                netAmountAfterDiscount = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_netAmount,"value");

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_transactionNote, transactionNotes),
                        " Transaction Notes Entered as :<b> " +transactionNotes+ "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.select_billType),
                        " Bill Type Select Box Displayed");
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_DirectStockEntry.select_billType, challanType),
                        "Selected Challan Type as <b>" + challanType + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_DirectStockEntry.input_challanNumber, challanNumber),
                        "Entered<b> " + challanNumber + "</b> in Bill Number");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.input_challanDate),
                        " Challan Date Input Box Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                        " Challan Date Entered as : "+ Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_billDate,"value"));

                totalCostIncludingTax = Cls_Generic_Methods.getElementAttribute(oPage_DirectStockEntry.input_totalAmountIncludingTax,"value");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_saveChanges),
                        " Save Changes Button Clicked In Direct Stock Entry Template ");
                Cls_Generic_Methods.customWait(5);
                
                boolean bDSETransactionFound = getDirectStockTransactionFromTransactionList(sTxnDate + "|" +sTxnTime,
                        transactionNotes,vendorFullNameAndAddress,totalCostIncludingTax,"Open");

                m_assert.assertTrue(bDSETransactionFound, "Direct Stock Entry Transaction Found, Created Successfully for date "
                        +sTxnDate + "|" +sTxnTime );

                if(bDSETransactionFound){

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_cancelSONButton),
                            "Clicked Cancel button In RHS Side");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DirectStockEntry.button_cancelDSEButton,4);

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_DirectStockEntry.text_confirmationMessage),
                            "Confirmation Message Display In Cancel Direct Stock Entry Template as "
                                    +Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_confirmationMessage));

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DirectStockEntry.button_cancelDSEButton),
                            "Clicked Cancel button In Cancel Direct Stock Entry Template");

                    Cls_Generic_Methods.customWait(4);

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_printA4SizeButton),
                            " Print A4 Button Displayed In View Direct Stock Entry");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.button_printA5SizeButton),
                            " Print A5 Button Displayed In View Direct Stock Entry");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.header_viewDirectStockEntryNoteHeader).equalsIgnoreCase(transactionNotes),
                            transactionNotes+" Notes as header displayed in view Direct Stock Entry");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_statusKeyAndValue).
                            equalsIgnoreCase("Status:    Cancelled"),"<b> Status:    Cancelled  </b> displayed in view DSE");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_transactionIDKeyAndValue).contains("Transaction ID"),
                            "<b>"+Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_transactionIDKeyAndValue)+"  </b> displayed in view DSE");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_billNumberAndDateKeyAndValue).
                                    equalsIgnoreCase("Bill Number:     "+billNumber+"    |   Bill Date:     "+Cls_Generic_Methods.getTodayDate()),
                            "<b>"+ Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_billNumberAndDateKeyAndValue) +" </b> displayed in view DSE");

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_transactionDetailsSectionText),
                            " Transaction Details Section Displayed");

                    String expiryDateInTable = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", expiryDateList.get(0));


                    transactionDetailsDataList.add(myMedicationName+"-500mg");
                    transactionDetailsDataList.add(sEditBatchNumber);
                    transactionDetailsDataList.add("");
                    transactionDetailsDataList.add(expiryDateInTable);
                    transactionDetailsDataList.add(String.valueOf(Double.parseDouble(totalQuantityList.get(1))));
                    transactionDetailsDataList.add(String.valueOf(Double.parseDouble(totalCostList.get(1))));

                    for(WebElement eTransactionTable : oPage_Purchase.list_valuesInTransactionDetailsTable){

                        int index = oPage_Purchase.list_valuesInTransactionDetailsTable.indexOf(eTransactionTable);
                        String eTransactionValue = Cls_Generic_Methods.getTextInElement(eTransactionTable);
                        if(eTransactionValue.equalsIgnoreCase(transactionDetailsDataList.get(index))){
                            m_assert.assertTrue("<b>"+eTransactionValue + " </b> Value present in Transaction Details table ");
                        }else{
                            m_assert.assertFalse(eTransactionValue + " Value not present in table ");
                        }

                    }


                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_totalAmtIncludingTaxLabel),
                            " Total Amt Including Tax Label is Displayed In View Direct Stock Entry ");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_discountLabel),
                            " Discount Label is Displayed In View Direct Stock Entry ");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_netAmountLabel),
                            " Net Amount Label is Displayed In View Direct Stock Entry ");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_amountPaidLabel),
                            " Amount Paid Label is Displayed In View Direct Stock Entry ");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_SON.text_amountRemainingLabel),
                            " Amount Remaining Label is Displayed In View Direct Stock Entry ");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_totalAmtIncTaxLabelValue).trim().
                                    equalsIgnoreCase(String.valueOf(Double.parseDouble(totalCostIncludingTax))),
                            " Total Amt Including Tax Label Value Correctly as : <b> "+ totalCostIncludingTax+"</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_netAmountLabelValue).trim().
                                    equalsIgnoreCase(String.valueOf(Double.parseDouble(netAmountAfterDiscount))),
                            " Net Amount Label Value Correctly as : <b> "+ netAmountAfterDiscount+"</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_amountPaidLabelValue).trim().equalsIgnoreCase("0.0"),
                            " Amount Paid Label Value Correctly as : <b> 0.0 </B> ");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_SON.text_amountRemainingLabelValue).
                                    equalsIgnoreCase(String.valueOf(Double.parseDouble(netAmountAfterDiscount))),
                            " Amount Remaining Label Value Correctly as : <b> "+ netAmountAfterDiscount+"</b>");

                    bDSETransactionFound = getDirectStockTransactionFromTransactionList(sTxnDate + "|" +sTxnTime,
                            transactionNotes,vendorFullNameAndAddress,totalCostIncludingTax,"Cancelled");

                    m_assert.assertTrue(bDSETransactionFound , " Cancelled Status Changes and Transaction Found In List ");

                }


            }else{
                m_assert.assertTrue(bLotCreated,"Lot Not added In Direct Stock Entry Template");
            }

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            Cls_Generic_Methods.customWait();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Search Functionality In DSE Transaction")
    public void validateSearchFunctionalityInDSE(){

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        InventorySearchTest oInventorySearchTest = new InventorySearchTest();
        Page_SON oPage_SON = new Page_SON(driver);
        Page_DirectStockEntry oPage_DirectStockEntry = new Page_DirectStockEntry(driver);



        String dseSearchTypeList [] = {"Transaction ID","Item Description","Bill No.","Challan No.","Batch No."};
        String oldTransactionId="" ,oldItem="",oldBillNo="",oldBatchNo="",oldChallanNo ="";

        try{

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Direct Stock Entry");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(dseSearchTypeList[0]),
                        " By Default Search Type Selection Dropdown Displayed correctly as :" + dseSearchTypeList[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "placeholder")
                                .contains("Search By " + dseSearchTypeList[0]),
                        " Input Search Box Place holder for selected search type Displayed correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                for (WebElement row : oPage_SON.list_SONTransactions) {
                    int index = oPage_SON.list_SONTransactions.indexOf(row);
                    if (index>1) {

                        Cls_Generic_Methods.clickElement(row);
                        Cls_Generic_Methods.customWait(5);
                        oldTransactionId = Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_transactionIDKeyAndValue).split(" ")[2].trim();
                        oldItem = Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_variantDescription);
                        oldBillNo = Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_billNumberAndDateKeyAndValue).substring(17,29);
                        oldChallanNo = Cls_Generic_Methods.getTextInElement(oPage_DirectStockEntry.text_billNumberAndDateKeyAndValue).substring(17,35);
                        oldBatchNo = Cls_Generic_Methods.getTextInElement(oPage_SON.text_batchNoText);

                        break;

                    }
                }


                String searchValueList[] = {transactionId,oldTransactionId,myMedicationName,oldItem,billNumber,oldBillNo,challanNumber,oldChallanNo,batchNo,oldBatchNo};

                for(int i = 0;i<2;i++) {
                    boolean searchResultByTransactionId = CommonActions.selectOptionAndSearch(dseSearchTypeList[0], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByTransactionId) {
                        boolean  bRequisitionOrderFound = oInventorySearchTest.getSearchedRecordAndValidateInView(oPage_SON.list_SONTransactions,oPage_SON.text_transactionIDKeyAndValue,searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound && oPage_SON.list_SONTransactions.size() == 1,
                                "Search By Transaction Id Worked correctly as order found in the  page for number: " + searchValueList[i]);
                    }
                }

                for(int i = 2;i<4;i++) {
                    boolean searchResultByDescription = CommonActions.selectOptionAndSearch(dseSearchTypeList[1], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByDescription) {
                        boolean  bRequisitionOrderFound = oInventorySearchTest.getSearchedRecordAndValidateInView(oPage_SON.list_SONTransactions,oPage_SON.text_variantDescription,searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound && oPage_SON.list_SONTransactions.size() == 1,
                                "Search By Item Description Worked correctly as order found in the  page for number: " + searchValueList[i]);
                    }

                }

                for(int i = 4;i<6;i++) {
                    boolean searchResultByBillNo = CommonActions.selectOptionAndSearch(dseSearchTypeList[2], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByBillNo) {
                        boolean  bRequisitionOrderFound = oInventorySearchTest.getSearchedRecordAndValidateInView(oPage_SON.list_SONTransactions,oPage_DirectStockEntry.text_billNumberAndDateKeyAndValue,searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound && oPage_SON.list_SONTransactions.size() == 1,
                                "Search By Bill No Worked correctly as order found in the  page for number: " + searchValueList[i]);
                    }

                }

                for(int i = 6;i<8;i++) {
                    boolean searchResultByChallanNo = CommonActions.selectOptionAndSearch(dseSearchTypeList[3], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByChallanNo) {
                        boolean  bRequisitionOrderFound = oInventorySearchTest.getSearchedRecordAndValidateInView(oPage_SON.list_SONTransactions,oPage_DirectStockEntry.text_billNumberAndDateKeyAndValue,searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound && oPage_SON.list_SONTransactions.size() == 1,
                                "Search By Challan No Worked correctly as order found in the  page for number: " + searchValueList[i]);
                    }

                }

                for(int i = 8;i<10;i++) {
                    boolean searchResultByBatchNo = CommonActions.selectOptionAndSearch(dseSearchTypeList[4], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByBatchNo) {
                        boolean  bRequisitionOrderFound = oInventorySearchTest.getSearchedRecordAndValidateInView(oPage_SON.list_SONTransactions,oPage_SON.text_batchNoText,searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound && oPage_SON.list_SONTransactions.size() == 1,
                                "Search By Batch No Worked correctly as order found in the  page for number: " + searchValueList[i]);
                    }

                }


                for(int i =0 ;i<5;i++) {

                    boolean searchResultByWrongNumber = selectOptionAndSearch(dseSearchTypeList[i],"incorrectReqNumber");
                    m_assert.assertFalse(searchResultByWrongNumber," SON Search With Incorrect Number Working Correct");
                    m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                    .equalsIgnoreCase("incorrectReqNumber"),
                            " Input Search Box is not empty by for selected search type Displayed correctly");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                            " Nothing To Display Text Displayed as Result");
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty as clear button is working correctly");
                boolean selectOption = CommonActions.selectOption(dseSearchTypeList[0]);
                m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(dseSearchTypeList[0]),
                        " Search Type Selected  as : "+dseSearchTypeList[0]);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(dseSearchTypeList[index])){
                        m_assert.assertTrue( typeText+" Search Type Present In Dropdown List");
                        Cls_Generic_Methods.customWait();
                    }else{
                        m_assert.assertFalse( typeText+" Search Type Not Present In Dropdown List");

                    }
                }

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();



            }catch (Exception e){
                e.printStackTrace();
                m_assert.assertFatal(e.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
            m_assert.assertFalse(e.toString());
        }
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

    public  boolean createLot(String unitCostWOTax,String sBatchNo ,String taxPercent,String packageUnit,String subPackageUnit,String unit,
                              String freeUnit,String sSellingPrice){

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
                expiryDateList.add(sExpiryDate);
            }

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_unitCostWOTax, unitCostWOTax),
                    "Unit cost without tax entered as : <b> " + unitCostWOTax + "</b>");
            Cls_Generic_Methods.customWait();

            dTaxPercentage = Double.parseDouble(InventoryCommonActions.convertTaxPercentageToAmount(taxPercent));
            double dUnitCostWithTax = (Double.parseDouble(unitCostWOTax) * dTaxPercentage) + Double.parseDouble(unitCostWOTax);

            sUnitCostWithTax = Cls_Generic_Methods.getElementAttribute(oPage_SON.text_unitCostWithTax, "value");
            unitCostList.add(sUnitCostWithTax);
            double dUnitCostWithTaxOnUI = Double.parseDouble(sUnitCostWithTax);
            m_assert.assertTrue(dUnitCostWithTax == dUnitCostWithTaxOnUI, "validated the unit cost with tax amount <b> " + dUnitCostWithTaxOnUI + "</b> on add Lot page");

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

            String totalStockOnUI = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_totalStockField,"value");
            double totalStockCalculated = (Double.parseDouble(packageUnit)*4)+(Double.parseDouble(subPackageUnit)*2)+
                    Double.parseDouble(unit)+Double.parseDouble(freeUnit);

            totalQuantityList.add(totalStockOnUI);
            m_assert.assertTrue(totalStockCalculated == convertStringToDouble(totalStockOnUI),
                    "validating the total stock working correctly and displayed as  <b> " + totalStockOnUI + "</b> on add Lot page");

            Cls_Generic_Methods.clearValuesInElement(oPage_SON.input_sellingPrice);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SON.input_sellingPrice, sSellingPrice),
                    "Selling Price entered as : <b> " + sSellingPrice + "</b>");

            validatingSellingPriceUnitValueOnSellingTypeBasis("Package","4",sSellingPrice);
            Cls_Generic_Methods.customWait(1);
            validatingSellingPriceUnitValueOnSellingTypeBasis("Subpackage","2",sSellingPrice);
            Cls_Generic_Methods.customWait(1);
            validatingSellingPriceUnitValueOnSellingTypeBasis("Unit","",sSellingPrice);


            //TotalCost = Units*UnitCostWithTax
            dTotalCost = totalStockCalculated * dUnitCostWithTax;
            totalCost = Cls_Generic_Methods.getElementAttribute(oPage_SON.input_totalCost, "value");
            totalCostList.add(totalCost);
            double dTotalCostOnUI = Double.parseDouble(totalCost);
            m_assert.assertTrue(dTotalCost == dTotalCostOnUI, "Validated the total cost <b>" + dTotalCostOnUI + "</b> on add Lot Page");

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_SON.button_saveLot), "Saving the Lot details");
            Cls_Generic_Methods.customWait( 5);
            if(oPage_SON.list_itemStockNameListInStockInventoryTemplate.size()>0){
                bLotCreated = true ;
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return bLotCreated;
    }

    private void addListDataForItemValuesOnAddLot() {

        viewAddLotValueList.add(myMedicationName);
        viewAddLotValueList.add("Deep12345");
        viewAddLotValueList.add("INV-MED-1057");
        viewAddLotValueList.add("Medication");
        viewAddLotValueList.add("");
        viewAddLotValueList.add("Tablets");
        viewAddLotValueList.add("Box");
        viewAddLotValueList.add("2 Number");
        viewAddLotValueList.add("2 Number");

    }
    private void addListDataForLabelsOnAddLot() {

        viewAddLotLabelList.add("Power:");
        viewAddLotLabelList.add("Batch No:");
        viewAddLotLabelList.add("Sub-Store:");
        viewAddLotLabelList.add("Expiry:");
        viewAddLotLabelList.add("Total Cost:");
        viewAddLotLabelList.add("Unit Cost With Tax:");
        viewAddLotLabelList.add("5.0%  tax");
        viewAddLotLabelList.add("Unit Cost W/O Tax:");
        viewAddLotLabelList.add("Stock (Package + Subpackage + Units + Free Units):");
        viewAddLotLabelList.add("Package/s (x 2 x 2)");
        viewAddLotLabelList.add("Subpackage/s (x 2)");
        viewAddLotLabelList.add("Unit/s");
        viewAddLotLabelList.add("Free Unit/s");
        viewAddLotLabelList.add("Total Unit/s");
        viewAddLotLabelList.add("Selling Price :");
        viewAddLotLabelList.add("5.0% tax - Inclusive");
        viewAddLotLabelList.add("Price w/o Tax :");


    }
    public void validatingDataTableHeadersInItemMaster(List<WebElement> eHeaderList , List<String> labelList){
        try{
            for(WebElement itemTableKeys : eHeaderList){

                if(Cls_Generic_Methods.isElementDisplayed(itemTableKeys)){

                    int indexOfLotDetailsKey = eHeaderList.indexOf(itemTableKeys);
                    String sItemKeyText = Cls_Generic_Methods.getTextInElement(itemTableKeys);

                    if(sItemKeyText.equalsIgnoreCase(labelList.get(indexOfLotDetailsKey))){

                        m_assert.assertTrue( "<b> " +sItemKeyText + " </b> Label Present In Add Lot </b>");

                    }else{
                        m_assert.assertWarn(sItemKeyText+"Either Not added While Item Creation or Not Present In Lot Please Verify");
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean validatingSellingPriceUnitValueOnSellingTypeBasis(String type ,String typeUnit,String sSellingPrice) {

        Page_SON oPage_SON = new Page_SON(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        double sellingPriceByUnit = 0 ;
        boolean bSellingPriceFound = false ;

        try{

            if(type.equalsIgnoreCase("Package")){

                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SON.select_selectSellingPriceUnitTypeInAddLot,"Package"),
                        " Package Unit Type Selected In Selling Price");
                Cls_Generic_Methods.customWait();
                sellingPriceByUnit = Double.parseDouble(sSellingPrice)/Double.parseDouble(typeUnit);


            }else if(type.equalsIgnoreCase("Subpackage")){
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SON.select_selectSellingPriceUnitTypeInAddLot,"Subpackage"),
                        " Subpackage Unit Type Selected In Selling Price");
                Cls_Generic_Methods.customWait();
                sellingPriceByUnit = Double.parseDouble(sSellingPrice)/Double.parseDouble(typeUnit);

            }else if(type.equalsIgnoreCase("Unit")){
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_SON.select_selectSellingPriceUnitTypeInAddLot,"Unit"),
                        " Package Unit Type Selected In Selling Price");
                Cls_Generic_Methods.customWait();
                sellingPriceByUnit = Double.parseDouble(sSellingPrice);
            }

            String sellingPricePerUnitOnUI = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_sellingPriceByUnit,"value");

            if(sellingPriceByUnit == Double.parseDouble(sellingPricePerUnitOnUI)){
                m_assert.assertTrue(" Selling Price Per Unit Calculated Correctly on Unit Basis and Displayed as :"+sellingPricePerUnitOnUI);
                bSellingPriceFound = true ;
            }

            String sellingPriceWOTaxCalculated = InventoryCommonActions.getSellingPriceAmountWithoutTax(sellingPricePerUnitOnUI, sTaxPercentage);
            String sellingPriceWOTaxUI = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_sellingPriceWOTax,"value");
            m_assert.assertTrue(sellingPriceWOTaxCalculated.equalsIgnoreCase(sellingPriceWOTaxUI),
                    "Selling Price W/O Tax calculated by correctly  ,present in add lot as :  "+sellingPriceWOTaxUI);


        }catch (Exception e) {
            e.printStackTrace();
        }

        return bSellingPriceFound;

    }

    public void getAddedItemStockCalculation() {

        Page_SON oPage_SON = new Page_SON(driver);

        List<String> taxableAmountList = new ArrayList<String>();
        List<String> taxRateList = new ArrayList<String>();

        try {

            // Storing  All item Stocks Cost , Rate , Quantity , NetAmount

            for (WebElement eItemDescription : oPage_SON.list_itemStockNameListInStockInventoryTemplate) {
                int index = oPage_SON.list_itemStockNameListInStockInventoryTemplate.indexOf(eItemDescription);
                String itemDescriptionName = Cls_Generic_Methods.getTextInElement(eItemDescription);
                if(itemDescriptionName.equalsIgnoreCase(myMedicationName+"  500mg")){
                    m_assert.assertTrue(" Item Description Name Displaying Correctly in Add/Edit Template as : <b>  "+itemDescriptionName+" </b> at index <b> "+index);
                }else{
                    m_assert.assertWarn(" Item Description Name is not coming correctly");
                }
            }

            for (WebElement eQuantity : oPage_SON.list_totalQuantityListInStockInventoryTemplate) {

                int index = oPage_SON.list_totalQuantityListInStockInventoryTemplate.indexOf(eQuantity);
                String itemQuantity = Cls_Generic_Methods.getElementAttribute(eQuantity,"value");

                if(Double.parseDouble(itemQuantity)==(Double.parseDouble(totalQuantityList.get(index)))){
                    m_assert.assertTrue(" Item Stock Displaying Correctly in Add/Edit Template as : <b> "+itemQuantity+" </b> at index <b> "+index+"</b>");
                }else{
                    m_assert.assertWarn(" Item Stock is not coming correctly");
                }
            }

            for (WebElement eBatch : oPage_SON.list_batchNoListInStockInventoryTemplate) {

                int index = oPage_SON.list_batchNoListInStockInventoryTemplate.indexOf(eBatch);
                String itemBatchNo = Cls_Generic_Methods.getElementAttribute(eBatch,"value");

                if(itemBatchNo.equalsIgnoreCase(batchNumberList.get(index))){
                    m_assert.assertTrue(" Item Batch No Displaying Correctly in Add/Edit Template as : <b> "+itemBatchNo+" </b> at index </b> "+index+"</b>");
                }else{
                    m_assert.assertWarn(" Item Batch is not coming correctly");
                }
            }

            for (WebElement eExpiryDate : oPage_SON.list_expiryDateListInStockInventoryTemplate) {

                int index = oPage_SON.list_expiryDateListInStockInventoryTemplate.indexOf(eExpiryDate);
                String itemExpiryDate = Cls_Generic_Methods.getElementAttribute(eExpiryDate,"value");
                String expiryDateText = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy","yyyy-MM-dd",expiryDateList.get(index));
                if(itemExpiryDate.equalsIgnoreCase(expiryDateList.get(index))){
                    m_assert.assertTrue(" Item Expiry Date Displaying Correctly in Add/Edit Template as : <b> "+itemExpiryDate+" </b> at index <b>"+index+"</b>");
                }else if(itemExpiryDate.equalsIgnoreCase(expiryDateText)){
                    m_assert.assertTrue(" Item Expiry Date Displaying Correctly in Add/Edit Template as : <b> "+itemExpiryDate+" </b> at index <b> "+index+"</b>");
                }else{
                    m_assert.assertTrue(" Item Expiry Date Not Displaying Correctly in Add/Edit Template as : "+itemExpiryDate+" at index "+index);
                }
            }

            for (WebElement eTaxableAmount : oPage_SON.list_taxableAmountListInStockInventoryTemplate) {

                int index = oPage_SON.list_taxableAmountListInStockInventoryTemplate.indexOf(eTaxableAmount);
                String itemTaxableAmount = Cls_Generic_Methods.getElementAttribute(eTaxableAmount,"value");
                Double taxableAmountCalculated = Double.parseDouble(sUnitCostWOList[index]) * Double.parseDouble(totalQuantityList.get(index));
                taxableAmountList.add(itemTaxableAmount);

                if(Double.parseDouble(itemTaxableAmount) == taxableAmountCalculated){
                    m_assert.assertTrue(" Item Taxable Amount Displaying Correctly in Add/Edit Template as : <b> "+itemTaxableAmount+"</b>  at index <b>"+index+"</b>");
                }else{
                    m_assert.assertWarn(" Item Taxable Amount or Calculation is not coming correctly");
                }

            }

            for (WebElement eTax : oPage_SON.list_taxRateListInStockInventoryTemplate) {

                int index = oPage_SON.list_taxRateListInStockInventoryTemplate.indexOf(eTax);
                String itemTaxRate = Cls_Generic_Methods.getElementAttribute(eTax,"value");
                taxRateList.add(itemTaxRate);
                String  sTaxRateCalculatedString = sTaxPercentage.replaceAll(" ", "").replace("tax", "").replace("%", "");
                double sTaxRateCalculated = Double.parseDouble(sTaxRateCalculatedString);
                if(itemTaxRate.equalsIgnoreCase(String.valueOf(sTaxRateCalculated))){
                    m_assert.assertTrue(" Item Tax Rate Displaying Correctly in Add/Edit Template as : <b> "+itemTaxRate+"</b> at index <b>"+index+"</b>");
                }else{
                    m_assert.assertWarn(" Item Tax Rate or Calculation is not coming correctly");
                }

            }

            for (WebElement eTaxInc : oPage_SON.list_taxIncListInStockInventoryTemplate) {

                int index = oPage_SON.list_taxIncListInStockInventoryTemplate.indexOf(eTaxInc);
                String itemTaxInc = Cls_Generic_Methods.getTextInElement(eTaxInc);
                if(itemTaxInc.equalsIgnoreCase("YES")){
                    m_assert.assertTrue(" Item Tax Inc Displaying Correctly in Add/Edit Template as : <b> "+itemTaxInc+"</b> at index <b>"+index+"</b>");
                }else{
                    m_assert.assertWarn(" Item Tax Inc is not coming correctly");
                }

            }

            for (WebElement eUnitCost : oPage_SON.list_unitCostListInStockInventoryTemplate) {

                int index = oPage_SON.list_unitCostListInStockInventoryTemplate.indexOf(eUnitCost);

                String itemUnitCost = Cls_Generic_Methods.getElementAttribute(eUnitCost,"value");

                if(Double.parseDouble(itemUnitCost)==(Double.parseDouble(unitCostList.get(index)))){
                    m_assert.assertTrue(" Item Unit Cost Displaying Correctly in Add/Edit Template as : <b>"+itemUnitCost+"</b> at index <b>"+index+"</b>");
                }else{
                    m_assert.assertWarn(" Item Unit Cost or Calculation is not coming correctly");
                }

            }

            Double totalCostCalculatedForPerItem = 0.0;
            for (WebElement eTotalCost : oPage_SON.list_totalCostListInStockInventoryTemplate) {

                int index = oPage_SON.list_totalCostListInStockInventoryTemplate.indexOf(eTotalCost);

                String itemTotalCost = Cls_Generic_Methods.getElementAttribute(eTotalCost,"value");
                totalCostCalculatedForPerItem = totalCostCalculatedForPerItem + Double.parseDouble(itemTotalCost);
                totalCostCalculatedForAllItem = totalCostCalculatedForPerItem;
                if(Double.parseDouble(itemTotalCost)==(Double.parseDouble(totalCostList.get(index)))){
                    m_assert.assertTrue(" Item Total Cost Displaying Correctly in Add/Edit Template as : <b>"+itemTotalCost+"</b> at index <b>"+index+"</b>");
                }else{
                    m_assert.assertWarn(" Item Total Cost or Calculation is not coming correctly");
                }

            }

          /*  for (WebElement eDeleteButton : oPage_SON.list_deleteListInStockInventoryTemplate) {

                int index = oPage_SON.list_deleteListInStockInventoryTemplate.indexOf(eDeleteButton);

                int itemCount =  oPage_SON.list_deleteListInStockInventoryTemplate.size();
                if(Cls_Generic_Methods.isElementDisplayed(eDeleteButton)){
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(eDeleteButton),
                            " Delete Button Displayed and Clicked In SON Template at index "+index);
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(itemCount == (itemCount-index),
                            " Delete Button Working as its Delete Row ,If its one Item then its not delete but button is displaying ");
                }

            }*/

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }
    public static boolean getDirectStockTransactionFromTransactionList(String dateAndTime, String transactionNotes, String sVendorName,
                                                                       String totalNetAmount, String status) {

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
                    String vendorNameUI = Cls_Generic_Methods.getTextInElement(purchaseRow.get(sonTransactionHeaderList.indexOf("Vendor Name")));
                    String purchaseNote = Cls_Generic_Methods.getTextInElement(purchaseRow.get(sonTransactionHeaderList.indexOf("Note")));
                    String purchaseAmount = Cls_Generic_Methods.getTextInElement(purchaseRow.get(sonTransactionHeaderList.indexOf("Amount")));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(sonTransactionHeaderList.indexOf("Status")));

                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    String purchaseDateAndTime = date + "|" + time;

                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    String purchaseNetAmountUI = decimalFormat.format(Double.parseDouble(purchaseAmount));
                    vendorNameUI = vendorNameUI.substring(0,8) + vendorNameUI.substring(8,12).toUpperCase();

                    if (purchaseDateAndTime.equals(dateAndTime) &&
                            purchaseStatus.equalsIgnoreCase(status) &&
                            purchaseNetAmountUI.equalsIgnoreCase(totalNetAmount) &&
                            sVendorName.contains(vendorNameUI) &&
                            purchaseNote.equalsIgnoreCase(transactionNotes)
                    ) {
                        bSONTransactionFound = true;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(row),
                                "Direct Stock Entry Transaction Clicked  In Transaction List");
                        Cls_Generic_Methods.customWait(2);
                        break;
                    }

                }
            }

            // If Purchase Not Found In Purchase Transaction List on basis of Date and Time and Vendor
            if (!bSONTransactionFound) {
                m_assert.assertTrue(" Direct Stock Entry Order Is not found for date : " + dateAndTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return bSONTransactionFound;

    }

    public static void validatingDataInViewItemRHSSide(List<WebElement> eHeaderList , String [] actualHeaderList,List<String> actualValueList){
        try{

            int indexOfKey = 0,indexOfValue = 0 ;
            for(WebElement eViewItem : eHeaderList){

                if(Cls_Generic_Methods.isElementDisplayed(eViewItem)){
                    int indexOfViewItem = eHeaderList.indexOf(eViewItem);
                    String sViewItemText = Cls_Generic_Methods.getTextInElement(eViewItem);
                    if(indexOfViewItem % 2 == 0) {
                        m_assert.assertTrue(sViewItemText.equalsIgnoreCase(actualHeaderList[indexOfKey]),
                                "<b> "+sViewItemText+ " </b> Label Present In View Item Lot RHS side ");
                        indexOfKey ++ ;
                    }else{
                        if(indexOfViewItem  == 5){
                            m_assert.assertTrue(!(sViewItemText.isEmpty()),
                                    "<b>"+sViewItemText+ " </b>  Value Present In View Item Lot RHS against label <b> "+actualHeaderList[indexOfValue]+"</b>");
                        }else {
                            m_assert.assertTrue(sViewItemText.equalsIgnoreCase(actualValueList.get(indexOfValue)),
                                    "<b> "+sViewItemText + "  </b> Value Present In View Item RHS against label <b> " + actualHeaderList[indexOfValue]+" </b>");
                        }
                        indexOfValue ++ ;
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getGST_valueFromSetting() {
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

    private String getRandomNumber() {
        Random random = new Random();
        String id = String.format("%06d", random.nextInt(1000000));
        return id;
    }

}
