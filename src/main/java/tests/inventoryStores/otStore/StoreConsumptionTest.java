package tests.inventoryStores.otStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.store.OtStore.Page_OtStore;
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import java.util.Random;
import static pages.commonElements.CommonActions.getRandomString;

public class StoreConsumptionTest extends TestBase {
    String sStoreName = "123store- IPD";
    public static final String itemDescription = "TransactionPurchase_" + getRandomString(4);
    static String  vendorName = "Supplier ABC";

    static String purchaseTransactionTime="";
    static String purchaseTransactionDate ="";
    static String sTransactionDate ="";
    static String billType = "Bill";
    static String billNumber = "BILL_" + getRandomNumber();
    public static final String subStore = "Default1";
    public static String unitCostWOTax = "100";
    public static String sItemQuantity = "5";
    public static String sStoreConsumptionQuantity = "2";
    public static String sUpdatedQuantity = "5";
    public static String sellingPrice = "120";
    public static String taxPercentage = "5%";
    public static String discountAmount = "10";
    static String otherChargesName = "Item_other";
    static String transactionNotes = "Transaction_notes" + getRandomString(4);
    static String sTransactionNotes = "Transaction_notes" + getRandomString(4);

    static String otherCharges = "10";

    public static String hsnCode, expiryDate;

    public String sEmployee = "PR.Akash test";
    public String sUpdatedEmployee = "Arpit Singh";
    public String sStockOnLot="";
    public String sBlockedStockONLot="";



    @Test(enabled = true, description = "Validating Create Store Consumtion In OT Store")
    public void validateCreateStoreConsumptionFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_OtStore oPage_OtStore=new Page_OtStore(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.driverRefresh();
            CommonActions.selectFacility("OPTHA1");
            Cls_Generic_Methods.customWait(5);

            try {

                // Open Pharmacy Store

                CommonActions.selectStoreOnApp(sStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                // Dynamic Wait
                Cls_Generic_Methods.customWait();

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");

                // Creating New Item In Item Master

              boolean addItemStatus = CommonActions.addItemInInventory(itemDescription);
              Cls_Generic_Methods.customWait(3);
              createGRNTransactionToAddLotForTheItem();

                if (addItemStatus) {

                    // Creating store consumption Transaction for Created Item

                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Store pop up closed");
                    Cls_Generic_Methods.customWait(3);
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Store Consumption");
                    Cls_Generic_Methods.waitForPageLoad(driver, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_new),
                            "New Button Displayed and Clicked in Store Consumption Transaction");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.header_storeConsumption,8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_descriptionInStoreConsumption),"Opened store consumption page and clicked description in LHS");
                    Cls_Generic_Methods.customWait(3);
                    int itemSize = 2;
                    if(oPage_OtStore.list_itemNameListInRHSTable.isEmpty())
                    {
                        for(int i=0;i<itemSize;i++) {
                            int j= i+1;
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.list_itemNameListInStoreConsumption.get(i)), "added item " +(j)+" for store consumption");
                        }
                        Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_OtStore.list_deleteiIemInRHSTable,8);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.list_deleteiIemInRHSTable.get(0)),"Delete button clicked");
                        if(!oPage_OtStore.list_itemNameListInRHSTable.isEmpty()){
                            m_assert.assertTrue(oPage_OtStore.list_itemNameListInRHSTable.size()==(itemSize-1),"one item deleted successfully");
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_saveChanges),"Save button clicked without entering required details");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.label_employeeFieldValidationError,8);
                            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_OtStore.label_employeeFieldValidationError,"class").equalsIgnoreCase("error"),"Validated Employee field is required");
                            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_OtStore.label_qtyFieldValidationError,"class").equalsIgnoreCase("error"),"Validated Quantity field is required");
                        }else {
                            m_assert.assertTrue("Item is not added");
                        }
                    }else {
                        m_assert.assertTrue("Item already added");
                    }
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_lotSearchBoxInStoreConsumption,itemDescription);
                    oPage_OtStore.input_lotSearchBoxInStoreConsumption.sendKeys(Keys.ENTER);
                    Cls_Generic_Methods.customWait(3);
                    Cls_Generic_Methods.clearValuesInElement(oPage_OtStore.input_lotSearchBoxInStoreConsumption);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_lotSearchBoxInStoreConsumption,itemDescription);
                    Cls_Generic_Methods.customWait(5);
                    for(WebElement element:oPage_OtStore.list_itemNameListInStoreConsumption)
                    {
                        String item = Cls_Generic_Methods.getTextInElement(element);
                        if(item.equalsIgnoreCase(itemDescription)){
                            Cls_Generic_Methods.clickElement(element);
                            Cls_Generic_Methods.customWait(5);
                            break;
                        }
                    }

                    for(WebElement element: oPage_OtStore.list_itemNameListInRHSTable)
                    {
                        String itemName = Cls_Generic_Methods.getTextInElement(element);
                        if(!(itemName.equalsIgnoreCase(itemDescription))){
                            int index = oPage_OtStore.list_itemNameListInRHSTable.indexOf(element);
                            Cls_Generic_Methods.clickElement(oPage_OtStore.list_deleteiIemInRHSTable.get(index));
                            break;
                        }
                    }
                    Cls_Generic_Methods.customWait(3);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_qtyForItem,sStoreConsumptionQuantity),"Entered quantity <b>"+sStoreConsumptionQuantity+" </b>for the Item");
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OtStore.select_userNameInStoreConsumption,sEmployee),"Selected employee <b>"+sEmployee+" </b>for the Item");
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_transactionNote,transactionNotes);
                    sTransactionDate= Cls_Generic_Methods.getElementAttribute(oPage_OtStore.text_transactionDate,"value");
                    sTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", sTransactionDate);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_saveChanges),"Store Consumptions Transaction saved successfully");
                    Cls_Generic_Methods.customWait(3);
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Store pop up closed");
                    Cls_Generic_Methods.customWait(3);
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch,8);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch,itemDescription);
                    Cls_Generic_Methods.clickElement(oPage_Lot.button_search);
                    Cls_Generic_Methods.customWait(4);
                    for (WebElement element:oPage_Lot.list_LotDetailsOnVariants) {
                        String itemName = Cls_Generic_Methods.getTextInElement(element);
                        if(itemName.equalsIgnoreCase(itemDescription))
                        {
                            Cls_Generic_Methods.clickElement(element);
                            break;
                        }

                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.text_stock,8);
                    sStockOnLot = Cls_Generic_Methods.getTextInElement(oPage_Lot.text_stock);
                    sBlockedStockONLot= Cls_Generic_Methods.getTextInElement(oPage_Lot.text_blockedLot);
                    m_assert.assertTrue(Double.parseDouble(sStockOnLot)==Double.parseDouble(sItemQuantity),"Validated total stock <b>"+sStockOnLot+"</b> for the lot");
                    m_assert.assertTrue(Double.parseDouble(sBlockedStockONLot)==Double.parseDouble(sStoreConsumptionQuantity),"Validated blocked lot <b>"+sBlockedStockONLot+"</b> for the item");
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Store pop up closed");
                    Cls_Generic_Methods.customWait(3);
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Store Consumption");
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_OtStore.list_storeConsumptionTransactions,8);
                    for(WebElement element:oPage_OtStore.list_storeConsumptionTransactions)
                    {
                        String txnDetails = Cls_Generic_Methods.getTextInElement(element);
                        if(txnDetails.contains(transactionNotes)&&txnDetails.contains(sTransactionDate))
                        {
                            Cls_Generic_Methods.clickElement(element);
                            break;
                        }
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_approveStoreConsumption,8);
                    String sItemName= Cls_Generic_Methods.getTextInElement(oPage_OtStore.text_itemDescriptionOnUI).split("-")[0].trim();
                    String sQty = Cls_Generic_Methods.getTextInElement(oPage_OtStore.text_itemQuantityOnUI).trim();
                    String sEmployeeName= Cls_Generic_Methods.getTextInElement(oPage_OtStore.text_EmployeeNameOnUI);
                    m_assert.assertTrue(sItemName.equalsIgnoreCase(itemDescription),"Validated Item name <b>"+sItemName+" </b>on UI ");
                    m_assert.assertTrue(sEmployeeName.equalsIgnoreCase(sEmployee),"Validated Employee name <b>"+sEmployee+" </b>on UI ");
                    m_assert.assertTrue(Double.parseDouble(sStoreConsumptionQuantity)==Double.parseDouble(sQty),"Validated Store consumption Quantity <b>"+sQty+" </b>on UI ");
                    String sStatus = Cls_Generic_Methods.getTextInElement(oPage_OtStore.text_statusOfTransaction).split(" ")[1].trim();
                    m_assert.assertTrue(sStatus.equalsIgnoreCase("Open"),"Transaction is in Open Status");
                    Cls_Generic_Methods.clickElement(oPage_OtStore.button_cancelStoreConsumption);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_confirmCancel,8);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_confirmCancel),"Clicked cancel transaction button");
                    Cls_Generic_Methods.customWait();
                    sStatus = Cls_Generic_Methods.getTextInElement(oPage_OtStore.text_statusOfTransaction).split(" ")[1].trim();
                    m_assert.assertTrue(sStatus.equalsIgnoreCase("Cancelled"),"Store Consumption Transaction is in Cancelled Status");
                    Cls_Generic_Methods.customWait(3);
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                }
            }
            catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }
            }catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
            }
    @Test(enabled = true, description = "Validating edit and approve Store Consumtion In OT Store")
    public void editAndApproveStoreConsumptionFunctionality() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_OtStore oPage_OtStore=new Page_OtStore(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.driverRefresh();
            CommonActions.selectFacility("OPTHA1");
            Cls_Generic_Methods.customWait(5);

            try {

                // Open Pharmacy Store

                CommonActions.selectStoreOnApp(sStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Store Consumption");
                Cls_Generic_Methods.waitForPageLoad(driver, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_new),
                        "New Button Displayed and Clicked in Store Consumption Transaction");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.header_storeConsumption,8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_descriptionInStoreConsumption),"Opened store consumption page and clicked description in LHS");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.input_lotSearchBoxInStoreConsumption,8);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_lotSearchBoxInStoreConsumption,itemDescription);
                oPage_OtStore.input_lotSearchBoxInStoreConsumption.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clearValuesInElement(oPage_OtStore.input_lotSearchBoxInStoreConsumption);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_lotSearchBoxInStoreConsumption,itemDescription);
                Cls_Generic_Methods.customWait(5);
                for(WebElement element:oPage_OtStore.list_itemNameListInStoreConsumption)
                {
                    String item = Cls_Generic_Methods.getTextInElement(element);
                    if(item.equalsIgnoreCase(itemDescription)){
                        Cls_Generic_Methods.clickElement(element);
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_qtyForItem,sStoreConsumptionQuantity),"Entered  quantity <b>"+sStoreConsumptionQuantity+" </b>for the Item");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OtStore.select_userNameInStoreConsumption,sEmployee),"Selected employee <b>"+sEmployee+" </b>for the Item");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_transactionNote,sTransactionNotes);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_saveChanges),"Store Consumptions Transaction saved successfully");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_OtStore.list_storeConsumptionTransactions,8);
                for(WebElement element:oPage_OtStore.list_storeConsumptionTransactions)
                {
                    String txnDetails = Cls_Generic_Methods.getTextInElement(element);
                    if(txnDetails.contains(sTransactionNotes)&&txnDetails.contains(sTransactionDate))
                    {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_editStoreConsumption,8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_editStoreConsumption),"Store Consumption Edit button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.input_qtyForItem,8);
                Cls_Generic_Methods.clearValuesInElement(oPage_OtStore.input_qtyForItem);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_qtyForItem,sUpdatedQuantity),"Entered quantity <b>"+sUpdatedQuantity+" </b>for the Item");
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_OtStore.select_userNameInStoreConsumption,sUpdatedEmployee),"Selected employee <b>"+sUpdatedEmployee+" </b>for the Item");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_saveChanges),"Store Consumptions Transaction saved successfully");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch,8);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch,itemDescription);
                Cls_Generic_Methods.clickElement(oPage_Lot.button_search);
                Cls_Generic_Methods.customWait(4);
                for (WebElement element:oPage_Lot.list_LotDetailsOnVariants) {
                    String itemName = Cls_Generic_Methods.getTextInElement(element);
                    if(itemName.equalsIgnoreCase(itemDescription))
                    {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }

                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.text_stock,8);
                sStockOnLot = Cls_Generic_Methods.getTextInElement(oPage_Lot.text_stock);
                sBlockedStockONLot= Cls_Generic_Methods.getTextInElement(oPage_Lot.text_blockedLot);
                m_assert.assertTrue(Double.parseDouble(sStockOnLot)==Double.parseDouble(sItemQuantity),"Validated total stock <b>"+sStockOnLot+"</b> for the lot");
                m_assert.assertTrue(Double.parseDouble(sBlockedStockONLot)==Double.parseDouble(sUpdatedQuantity),"Validated blocked lot <b>"+sBlockedStockONLot+"</b> for the item");
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Store Consumption");
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_OtStore.list_storeConsumptionTransactions,8);
                for(WebElement element:oPage_OtStore.list_storeConsumptionTransactions)
                {
                    String txnDetails = Cls_Generic_Methods.getTextInElement(element);
                    if(txnDetails.contains(sTransactionNotes)&&txnDetails.contains(sTransactionDate))
                    {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_approveStoreConsumption,8);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_approveStoreConsumption),"Store Consmption Transaction Approved");
                Cls_Generic_Methods.customWait(4);
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.input_InventorySearch,8);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_InventorySearch,itemDescription);
                Cls_Generic_Methods.clickElement(oPage_Lot.button_search);
                Cls_Generic_Methods.customWait(4);
                for (WebElement element:oPage_Lot.list_LotDetailsOnVariants) {
                    String itemName = Cls_Generic_Methods.getTextInElement(element);
                    if(itemName.equalsIgnoreCase(itemDescription))
                    {
                        Cls_Generic_Methods.clickElement(element);
                        break;
                    }

                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.text_stock,8);
                Double dStock = Double.parseDouble(sStockOnLot)-Double.parseDouble(sBlockedStockONLot);
                sStockOnLot = Cls_Generic_Methods.getTextInElement(oPage_Lot.text_stock);
                sBlockedStockONLot= Cls_Generic_Methods.getTextInElement(oPage_Lot.text_blockedLot);
                m_assert.assertTrue(Double.parseDouble(sStockOnLot)==dStock,"Validated total stock <b>"+sStockOnLot+"</b> for the lot");
                m_assert.assertTrue(Double.parseDouble(sBlockedStockONLot)==0,"Validated blocked lot <b>"+sBlockedStockONLot+"</b> for the item");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();


            }catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        }catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }



            public static void createGRNTransactionToAddLotForTheItem(){

                Page_Purchase oPage_Purchase = new Page_Purchase(driver);
                Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

                try{
                    boolean vendorFound = false;

                if (true) {

                    // Creating Purchase Transaction for Created Item

                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                    Cls_Generic_Methods.waitForPageLoad(driver, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew),
                            "New Button Displayed and Clicked in Purchase Transaction");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 10);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_Vendor_search, (vendorName));
                    Cls_Generic_Methods.customWait(3);
                    for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
                        Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                        vendorFound = true;
                        break;
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);

                    // Adding New Item Stock to Inventory

                    boolean bItemStockAdded = addNewItemStockToPurchase(itemDescription);

                    if (bItemStockAdded) {

                        // Verifying Added Item Stock Calculation


                        //Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);

                            // Filling Other Mandatory Fields Like Bill date , Notes Etc

                            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, transactionNotes),
                                    " Transaction Notes Entered as :<b> " + transactionNotes + "</b>");
                            Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType),
                                    "Bill Type Selected:<b> " + billType + " </b>");
                            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber),
                                    "Bill Number: <b> " + billNumber + " </b>");
                            Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                        purchaseTransactionTime = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionTime, "value");
                        purchaseTransactionTime = purchaseTransactionTime.replaceAll("\\s+", "");
                        m_assert.assertTrue("Purchase Transaction time:<b> " + purchaseTransactionTime + "</b>");
                        //purchaseTransactionTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", purchaseTransactionTime);
                        purchaseTransactionTime = purchaseTransactionTime.replace("am", "AM").replace("pm", "PM");
                        if(purchaseTransactionTime.length() == 6){
                            purchaseTransactionTime = "0"+purchaseTransactionTime;
                        }
                        purchaseTransactionDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_purchaseTransactionDate, "value");
                        purchaseTransactionDate = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "yyyy-MM-dd", purchaseTransactionDate);


                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate),
                                    "Date of bill selected:<b> " + oPage_Purchase.input_todayBillDate.getText() + " </b>");

                            // Verifying Net Amount Calculation After Adding other Charges

                            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_otherCharges, otherChargesName),
                                    "Other Charges: <b> " + otherChargesName + " </b>");
                            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_otherChargesAmount, otherCharges);

                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                                    "Save Changes Button Clicked In A Lot Inventory Template");
                            Cls_Generic_Methods.customWait();
                            Cls_Generic_Methods.driverRefresh();
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                                "Store pop up closed");
                        CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/GRN");
                        Cls_Generic_Methods.waitForPageLoad(driver, 4);
                        for(WebElement element: oPage_Purchase.list_purchaseTransactionDateandTimeList){
                            String sDateAndTime = Cls_Generic_Methods.getTextInElement(element);
                            if(sDateAndTime.contains(purchaseTransactionDate)&&sDateAndTime.contains(purchaseTransactionTime)){
                                Cls_Generic_Methods.clickElement(element);
                                break;
                            }
                        }
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction,8);
                        Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approveGRN,8);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_approveGRN),"GRN trsnaction approved successfully");
                        Cls_Generic_Methods.customWait(3);

                    } else {
                        m_assert.assertInfo(bItemStockAdded, " Stock is not Added in Stock Inventory");
                    }
                }
                }catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }
            }

    public static boolean addNewItemStockToPurchase(String itemDescription) {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);

        boolean addStockFound = false;
        boolean itemFoundInPurchase = false;

        try {

            for (WebElement eItemName : oPage_Purchase.list_itemNameInPurchaseStore) {
                if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(itemDescription)) {
                    Cls_Generic_Methods.clickElement(eItemName);
                    itemFoundInPurchase = true;
                    break;
                }
            }

            m_assert.assertTrue(itemFoundInPurchase, "Item found in purchase: <b> " + itemDescription + "</b>");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewLot, 15);

            if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.select_subStore)) {
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_subStore, subStore),
                        "Sub Store: <b> " + subStore + " </b>");
            }
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.input_expiryDate), "Expiry Date Input box clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.activeDateInExpiryDate, 1);
            String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_Purchase.select_expiryDateYear);
            int year = Integer.parseInt(currentYear);
            int newYear = year + 3;

            Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_expiryDateYear, Integer.toString(newYear));

            if (!Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.select_expiryDateDay)) {
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_doneButtonInExpiry);
                Cls_Generic_Methods.customWait(1);
            } else {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.select_expiryDateDay),
                        "Current date selected as expiry date as <b>" + Cls_Generic_Methods.getTextInElement(oPage_Purchase.select_expiryDateDay) + " </b>");
            }
            Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_unitCostWOTax);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_unitCostWOTax, unitCostWOTax),
                    "Unit cost without tax entered as : <b> " + unitCostWOTax + "</b>");

            Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_packageQuantity);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_packageQuantity, sItemQuantity),
                    "package entry entered as : <b> " + sItemQuantity + "</b>");

            Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_discountAmount);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_discountAmount, discountAmount),
                    "Discount entered as : <b> " + discountAmount + "</b>");

            Cls_Generic_Methods.customWait(1);
            Cls_Generic_Methods.customWait(2);

            Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_sellingPrice);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_sellingPrice, sellingPrice),
                    "Selling Price entered as : <b> " + sellingPrice + "</b>");

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveLot),
                    " Save Changes Button CLicked In Inventory Template");

            Cls_Generic_Methods.customWait(2);
            for (WebElement itemName : oPage_Purchase.list_itemStockNameListInStockInventoryTemplate) {
                if (Cls_Generic_Methods.getTextInElement(itemName).contains(itemDescription))
                    addStockFound = true;
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);
            m_assert.assertTrue(addStockFound, "Stock added successfully");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return addStockFound;
    }
    private static String getRandomNumber() {
        Random random = new Random();
        String id = String.format("%06d", random.nextInt(1000000));
        return id;
    }

}
