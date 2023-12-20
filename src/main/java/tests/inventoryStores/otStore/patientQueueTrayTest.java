package tests.inventoryStores.otStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.ipd.Page_IPD;
import pages.ipd.forms.intraOperative.*;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;

import pages.store.OtStore.Page_OtStore;
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_PurchaseReturn;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static tests.inventoryStores.pharmacyStore.Transaction.PurchaseGRNTest.convertStringToDouble;

public class patientQueueTrayTest extends TestBase {
    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    public static String dateAndTime = "";
    public static String dateAndTimeLot = "";
    String concatPatientFullName = "";
    String currentCreateTrayStock = "";
    String currentEditTrayStock = "";
    String currentCreateItemRate = "";
    String currentEditItemRate = "";
    String createTrayItemName = "CreateTrayTestItem";
    String editTrayItemName = "EditTrayTestItem";
    String itemQuantityUI = "10.0";
    String editItemQuantityUI = "15";
    String sCONSUMED_QUANTITY = "5";
    String totalNetPriceValue = "";
    String otStoreName = "OT Store- IPD";


    @Test(enabled = true, description = "Validate Create Tray Functionality In OT Store")
    public void validateCreateTrayFunctionalityInOTStore() {

        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

        boolean bPatientNameFound = false;
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            try {

                CommonActions.selectStoreOnApp(otStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                bPatientNameFound = selectPatientNameInPatientQueue(concatPatientFullName);
                if (bPatientNameFound) {

                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mmaa");
                    dateAndTime = dateFormat.format(new Date()).toString();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_createTray),
                            "Create Tray Button Clicked In Patient Queue For Patient name :" + concatPatientFullName);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_descriptionInTrayTemplate, 4);
                    Cls_Generic_Methods.clickElement(oPage_OtStore.button_descriptionInTrayTemplate);
                    Cls_Generic_Methods.customWait(2);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_lotSearchBoxInTrayTemplate, createTrayItemName);
                    Cls_Generic_Methods.customWait();

                    int indexOfItemDescription = -1;
                    boolean bItemStockStatus = false;

                    for (WebElement itemName : oPage_OtStore.list_itemNameListInTrayTemplate) {
                        if (Cls_Generic_Methods.getTextInElement(itemName).equalsIgnoreCase(createTrayItemName)) {
                            indexOfItemDescription = oPage_OtStore.list_itemNameListInTrayTemplate.indexOf(itemName);
                            currentCreateItemRate = Cls_Generic_Methods.getTextInElement(oPage_OtStore.list_itemRateListInTrayTemplate.get(indexOfItemDescription));
                            currentCreateTrayStock = Cls_Generic_Methods.getTextInElement(oPage_OtStore.list_itemStockListInTrayTemplate.get(indexOfItemDescription));
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemName),
                                    " Item Selected From Left Panel In Create Tray As :" + createTrayItemName);
                            bItemStockStatus = true;
                            Cls_Generic_Methods.customWait(5);
                            break;
                        }
                    }

                    m_assert.assertTrue(bItemStockStatus,
                            " Purchase Item Selected From Left Panel in Create Tray Template: " + createTrayItemName);

                    for (WebElement itemQuantity : oPage_OtStore.list_itemQuantityListInTrayTemplate) {
                        Cls_Generic_Methods.sendKeysIntoElement(itemQuantity, itemQuantityUI);
                    }

                    String netAmountPerItem = "";
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    for (WebElement itemQuantity : oPage_OtStore.list_itemAmountListInTrayTemplate) {
                        netAmountPerItem = Cls_Generic_Methods.getElementAttribute(itemQuantity, "value");
                    }

                    String mrpPerItem = "";
                    for (WebElement itemMrp : oPage_OtStore.list_itemMRPListInTrayTemplate) {
                        mrpPerItem = Cls_Generic_Methods.getTextInElement(itemMrp);
                    }

                    m_assert.assertTrue(("₹" + mrpPerItem).equalsIgnoreCase(currentCreateItemRate),
                            "MRP in View Item is same as Item Rate Price In Left Panel" + currentCreateItemRate);

                    String netAmountCalculated = decimalFormat.format(convertStringToDouble(mrpPerItem) * convertStringToDouble(itemQuantityUI));
                    m_assert.assertTrue(netAmountPerItem.equals(netAmountCalculated),
                            "Net Amount Calculated Correctly for Each Item" + netAmountCalculated);
                    totalNetPriceValue = Cls_Generic_Methods.getElementAttribute(oPage_OtStore.input_trayTotalCostValue, "value");
                    m_assert.assertTrue(totalNetPriceValue.equalsIgnoreCase(netAmountPerItem),
                            "Total Net Price calculated correctly and same as sum of all item amount" + totalNetPriceValue);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                            "Save Changes Button Clicked In Create Tray Template");
                    dateAndTimeLot = dateFormat.format(new Date()).toString();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 10);

                    boolean trayFound = selectViewTrayAction("", "Open");
                    m_assert.assertTrue(trayFound, " Tray Created Successfully and Found In patient Queue");


                } else {
                    m_assert.assertTrue(bPatientNameFound + "Patient Not Found In Patient Queue List Of OT Store");
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }

    }

    @Test(enabled = true, description = "Validate Create Tray Functionality In Items Lot")
    public void ValidateCreateTrayInItemsLotFunctionality() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);

        boolean bItemFoundInLot = false;
        boolean bCreateTrayFoundInLot = false;
        double stockAfterCalculated = 0.0;
        String stockBeforeReturnUI = "";

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(otStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {

                // Validating Return Purchase In Items Lots
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, createTrayItemName);
                Cls_Generic_Methods.customWait(5);
                oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(5);

                int indexOfItemInLot = 0;

                for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
                    List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
                    String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((0)));
                    if (itemDescriptionText.contains(createTrayItemName)) {
                        Cls_Generic_Methods.clickElement(items);
                        Cls_Generic_Methods.waitForElementToBeDisplayed
                                (oPage_Lot.text_transactionDetailsSectionText, 5);
                        bItemFoundInLot = true;
                        break;
                    }
                }
                m_assert.assertTrue(bItemFoundInLot, createTrayItemName + " Clicked in Items Lot at index " + indexOfItemInLot);

                if (bItemFoundInLot) {
                    stockBeforeReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockBeforeInViewTemplate);
                    String stockAfterReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockAfterInViewTemplate);
                    String flowTextUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsFlowInViewTemplate);
                    String dateAndTimeUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsDateAndTimeInViewTemplate);

                    String date = dateAndTimeUI.split("\\|")[0].trim();
                    String time = dateAndTimeUI.split("\\|")[1].trim();
                    date = CommonActions.getRequiredFormattedDateTime("yyyy-MM-dd", "dd-MM-yyyy", date);

                    String purchaseReturnDateAndTime = date + " | " + time;

                    stockAfterCalculated = convertStringToDouble(stockBeforeReturnUI) - convertStringToDouble(itemQuantityUI);

                    String availableStockUI = Cls_Generic_Methods.getTextInElement(oPage_OtStore.text_stockAvailableInItemDetailsInViewTemplate);

                    if (flowTextUI.equalsIgnoreCase("Out (Tray)") &&
                            Double.toString(stockAfterCalculated).equals(stockAfterReturnUI) &&
                            purchaseReturnDateAndTime.equals(dateAndTimeLot) &&
                            availableStockUI.equalsIgnoreCase(Double.toString(stockAfterCalculated))) {
                        bCreateTrayFoundInLot = true;
                    }
                }
                m_assert.assertTrue(bCreateTrayFoundInLot,
                        " <b> Create Tray Functionality Working Fine, as initial stock was: " + stockBeforeReturnUI +
                                " after create tray stock is: " + stockAfterCalculated + "</b> ");

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

    @Test(enabled = true, description = "Validate Edit Tray Functionality In OT Store")
    public void validateEditTrayFunctionalityInOTStore() {

        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        List<String> itemDetailsHeaderListUI = new ArrayList<String>();

        boolean bPatientNameFound = false;
        boolean bTrayDetailsFound = false;
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            try {

                CommonActions.selectStoreOnApp(otStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                bPatientNameFound = selectPatientNameInPatientQueue(concatPatientFullName);
                if (bPatientNameFound) {

                    boolean clickOnViewTray = selectViewTrayAction("View Tray", "Open");

                    m_assert.assertTrue(clickOnViewTray, "View Tray is clicked for patient " + concatPatientFullName);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.header_trayDetailsHeaderInViewTray, 5);

                    for (WebElement itemDetailHeader : oPage_OtStore.list_itemDetailsHeaderListInViewTray) {
                        itemDetailsHeaderListUI.add(Cls_Generic_Methods.getTextInElement(itemDetailHeader));
                    }

                    for (WebElement itemDetails : oPage_OtStore.list_itemDetailsDataListInViewTray) {

                        List<WebElement> itemDetailsRow = itemDetails.findElements(By.xpath("./child::*"));
                        String itemName = Cls_Generic_Methods.getTextInElement(itemDetailsRow.get(itemDetailsHeaderListUI.indexOf("Description")));
                        String initialQty = Cls_Generic_Methods.getTextInElement(itemDetailsRow.get(itemDetailsHeaderListUI.indexOf("Initial Qty")));
                        String availableQty = Cls_Generic_Methods.getTextInElement(itemDetailsRow.get(itemDetailsHeaderListUI.indexOf("Available Qty")));
                        String totalPrice = Cls_Generic_Methods.getTextInElement(itemDetailsRow.get(itemDetailsHeaderListUI.indexOf("Total Price")));

                        if (itemName.contains(createTrayItemName) &&
                                convertStringToDouble(initialQty) == (convertStringToDouble(itemQuantityUI)) &&
                                convertStringToDouble(availableQty) == (convertStringToDouble(itemQuantityUI)) &&
                                totalPrice.equalsIgnoreCase(totalNetPriceValue)) {

                            bTrayDetailsFound = true;
                            break;
                        }

                    }

                    m_assert.assertTrue(bTrayDetailsFound, " Create Tray Details Found Correctly In View Tray");

                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mmaa");
                    dateAndTime = dateFormat.format(new Date()).toString();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_EditTrayButtonInViewTray),
                            " Edit Tray Button Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_descriptionInTrayTemplate, 4);
                    Cls_Generic_Methods.clickElement(oPage_OtStore.button_descriptionInTrayTemplate);
                    Cls_Generic_Methods.customWait(2);
                    Cls_Generic_Methods.clickElement(oPage_OtStore.input_lotSearchBoxInTrayTemplate);
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_lotSearchBoxInTrayTemplate, editTrayItemName);
                    Cls_Generic_Methods.customWait();

                    int indexOfItemDescription = -1;
                    boolean bItemStockStatus = false;

                    for (WebElement itemName : oPage_OtStore.list_itemNameListInTrayTemplate) {
                        if (Cls_Generic_Methods.getTextInElement(itemName).equalsIgnoreCase(editTrayItemName)) {
                            indexOfItemDescription = oPage_OtStore.list_itemNameListInTrayTemplate.indexOf(itemName);
                            currentEditItemRate = Cls_Generic_Methods.getTextInElement(oPage_OtStore.list_itemRateListInTrayTemplate.get(indexOfItemDescription));
                            currentEditTrayStock = Cls_Generic_Methods.getTextInElement(oPage_OtStore.list_itemStockListInTrayTemplate.get(indexOfItemDescription));
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemName),
                                    " Item Selected From Left Panel In Edit Tray As :" + editTrayItemName);
                            bItemStockStatus = true;
                            Cls_Generic_Methods.customWait(5);
                            break;
                        }
                    }

                    m_assert.assertTrue(bItemStockStatus,
                            " Purchase Item Selected From Left Panel in Edit Tray Template: " + editTrayItemName);

                    for (WebElement itemQuantity : oPage_OtStore.list_itemQuantityListInTrayTemplate) {
                        Cls_Generic_Methods.clearValuesInElement(itemQuantity);
                        Cls_Generic_Methods.customWait(1);
                        Cls_Generic_Methods.sendKeysIntoElement(itemQuantity, editItemQuantityUI);
                    }

                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    List<String> netAmountList = new ArrayList<String>();
                    for (WebElement itemQuantity : oPage_OtStore.list_itemAmountListInTrayTemplate) {
                        netAmountList.add(Cls_Generic_Methods.getElementAttribute(itemQuantity, "value"));
                    }

                    List<String> itemMrpList = new ArrayList<String>();
                    for (WebElement itemMrp : oPage_OtStore.list_itemMRPListInTrayTemplate) {
                        itemMrpList.add(Cls_Generic_Methods.getTextInElement(itemMrp));
                    }

                    m_assert.assertTrue(("₹" + itemMrpList.get(0)).equalsIgnoreCase(currentCreateItemRate),
                            "MRP in View Item is same as Item Rate Price In Left Panel for " + currentCreateItemRate);

                    m_assert.assertTrue(("₹" + itemMrpList.get(1)).equalsIgnoreCase(currentEditItemRate),
                            "MRP in View Item is same as Item Rate Price In Left Panel for " + currentEditItemRate);

                    double totalNetAmountCalculated = 0.00;
                    for (int i = 0; i < netAmountList.size(); i++) {

                        double netAmountPerItemCalculated = convertStringToDouble(itemMrpList.get(i)) * convertStringToDouble(editItemQuantityUI);

                        m_assert.assertTrue(convertStringToDouble(netAmountList.get(i)) == netAmountPerItemCalculated,
                                " Net Amount Calculated per Item Correctly " + netAmountPerItemCalculated);

                        totalNetAmountCalculated = totalNetAmountCalculated + netAmountPerItemCalculated;
                    }

                    String totalNetPriceValue = Cls_Generic_Methods.getElementAttribute(oPage_OtStore.input_trayTotalCostValue, "value");
                    m_assert.assertTrue(totalNetPriceValue.equalsIgnoreCase(decimalFormat.format(totalNetAmountCalculated)),
                            "Total Net Price calculated correctly and same as sum of all item amount" + totalNetPriceValue);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.input_updateChangesInTray),
                            "Update Changes Button Clicked In Edit Tray Template");

                    dateAndTimeLot = dateFormat.format(new Date()).toString();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 5);

                    boolean trayFound = selectViewTrayAction("", "Open");
                    m_assert.assertTrue(trayFound, " Tray Edited Successfully and Found In patient Queue");

                } else {
                    m_assert.assertTrue(bPatientNameFound + "Patient Not Found In Patient Queue List Of OT Store");
                }


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }

    }

    @Test(enabled = true, description = "Validate Edit Tray Functionality In Items Lot ")
    public void ValidateEditTrayInItemsLotFunctionality() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);


        boolean bItemFoundInLot = false;
        boolean bEditTrayFoundInLot = false;
        double stockAfterCalculated = 0.0;
        String stockBeforeReturnUI = "";

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(otStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {

                // Validating Return Purchase In Items Lots
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, editTrayItemName);
                Cls_Generic_Methods.customWait(5);
                oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(5);

                int indexOfItemInLot = 0;

                for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
                    List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
                    String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((0)));
                    if (itemDescriptionText.contains(editTrayItemName)) {
                        Cls_Generic_Methods.clickElement(items);
                        Cls_Generic_Methods.waitForElementToBeDisplayed
                                (oPage_Lot.text_transactionDetailsSectionText, 5);
                        bItemFoundInLot = true;
                        break;
                    }
                }
                m_assert.assertTrue(bItemFoundInLot, editTrayItemName + " Clicked in Items Lot at index " + indexOfItemInLot);

                if (bItemFoundInLot) {
                    stockBeforeReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockBeforeInViewTemplate);
                    String stockAfterReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockAfterInViewTemplate);
                    String flowTextUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsFlowInViewTemplate);
                    String dateAndTimeUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsDateAndTimeInViewTemplate);

                    String date = dateAndTimeUI.split("\\|")[0].trim();
                    String time = dateAndTimeUI.split("\\|")[1].trim();

                    date = CommonActions.getRequiredFormattedDateTime("yyyy-MM-dd", "dd-MM-yyyy", date);
                    String purchaseReturnDateAndTime = date + " | " + time;
                    stockAfterCalculated = convertStringToDouble(stockBeforeReturnUI) - convertStringToDouble(editItemQuantityUI);
                    String availableStockUI = Cls_Generic_Methods.getTextInElement(oPage_OtStore.text_stockAvailableInItemDetailsInViewTemplate);

                    if (flowTextUI.equalsIgnoreCase("Out (Tray)") &&
                            Double.toString(stockAfterCalculated).equals(stockAfterReturnUI) &&
                            purchaseReturnDateAndTime.equalsIgnoreCase(dateAndTimeLot) &&
                            availableStockUI.equalsIgnoreCase(Double.toString(stockAfterCalculated))) {
                        bEditTrayFoundInLot = true;
                    }
                }
                m_assert.assertTrue(bEditTrayFoundInLot,
                        " <b> Edit Tray Functionality Working Fine, as initial stock was: " + stockBeforeReturnUI +
                                " after Edit Tray  stock is: " + stockAfterCalculated + "</b> ");


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

    @Test(enabled = true, description = "Validate Tray Functionality In IPD Bill Of Materials ")
    public void validatePartiallyTrayInBillOfMaterialsForm() {


        Page_BillOfMaterial oPage_BillOfMaterial = new Page_BillOfMaterial(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        boolean bValidatePatientFound = false;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("IPD");
            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today);
            Cls_Generic_Methods.customWait();
            bValidatePatientFound = selectPatientNameInIPD(concatPatientFullName);
            m_assert.assertInfo(bValidatePatientFound, "Validate patient found");

            if (bValidatePatientFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_intraOperativeSection)) {

                    // bill of material
                    try {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_billOfMaterialTemplate, 4);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_billOfMaterialTemplate),
                                "Clicked on bill of material template ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_addBillOfMaterial, 4);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_addBillOfMaterial),
                                "Clicked on + bill of material to fill the template ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_saveBom, 8);

                        for (WebElement consumedQty : oPage_BillOfMaterial.list_inputConsumedQuantityListInBom) {
                            if (Cls_Generic_Methods.isElementDisplayed(consumedQty)) {
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(consumedQty, sCONSUMED_QUANTITY),
                                        "Consumed quantity entered as " + sCONSUMED_QUANTITY);
                            }
                        }

                        List<String> billableUnitPriceValueList = new ArrayList<String>();

                        for (WebElement billableUnitPrice : oPage_BillOfMaterial.list_inputBillableUnitPriceListInBom) {
                            billableUnitPriceValueList.add(Cls_Generic_Methods.getElementAttribute(billableUnitPrice, "value"));
                        }

                        double totalPriceCalculated = 0.00;
                        int indexOfItem = -1;
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        for (WebElement totalItem : oPage_BillOfMaterial.list_totalPerItemListInBom) {

                            indexOfItem = oPage_BillOfMaterial.list_totalPerItemListInBom.indexOf(totalItem);
                            String totalValuePerTrayItem = Cls_Generic_Methods.getElementAttribute(totalItem, "value");
                            double totalPerItemCalculated = convertStringToDouble(billableUnitPriceValueList.get(indexOfItem)) *
                                    convertStringToDouble(sCONSUMED_QUANTITY);

                            m_assert.assertTrue(totalValuePerTrayItem.equalsIgnoreCase(decimalFormat.format(totalPerItemCalculated)),
                                    " Total Calculated Correctly For Each Item In Bills Of Materials as : " + totalValuePerTrayItem);
                            totalPriceCalculated = totalPriceCalculated + totalPerItemCalculated;

                        }

                        String totalPriceBomBeforeBillable = Cls_Generic_Methods.getElementAttribute(oPage_BillOfMaterial.input_totalPriceBom, "value");
                        m_assert.assertTrue(totalPriceBomBeforeBillable.equalsIgnoreCase(""),
                                " Total Price Displaying correctly as " + totalPriceBomBeforeBillable + " without Billable checkbox selection");

                        for (WebElement billableCheckbox : oPage_BillOfMaterial.list_inputBillableCheckboxListInBom) {
                            if (Cls_Generic_Methods.isElementDisplayed(billableCheckbox)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(billableCheckbox),
                                        "Billable Checkbox Clicked at index " + oPage_BillOfMaterial.list_inputBillableCheckboxListInBom.indexOf(billableCheckbox));
                            }
                        }

                        String totalPriceBomAfterBillable = Cls_Generic_Methods.getElementAttribute(oPage_BillOfMaterial.input_totalPriceBom, "value");

                        m_assert.assertTrue(totalPriceBomAfterBillable.equalsIgnoreCase(decimalFormat.format(totalPriceCalculated)),
                                " Total Price Calculated correctly as " + totalPriceBomAfterBillable + " with Billable checkbox selection");

                        Cls_Generic_Methods.sendKeysIntoElement(oPage_BillOfMaterial.input_discountBom, "10");

                        String totalBillingPrice = Cls_Generic_Methods.getElementAttribute(oPage_BillOfMaterial.input_totalBillingPriceBom, "value");

                        double totalBillingPriceCalculated = convertStringToDouble(totalPriceBomAfterBillable) - convertStringToDouble("10");

                        m_assert.assertTrue(totalBillingPrice.equalsIgnoreCase(decimalFormat.format(totalBillingPriceCalculated)),
                                "Total Billing Price Calculated Correctly as : " + totalBillingPriceCalculated);

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_saveBom),
                                " Bills of Material saved");


                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Exception while filling bill of material chart Template " + e);
                    }

                } else {
                    m_assert.assertInfo(false,
                            "Admission form in Intra operative section is not filled, please fill admission form");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validate Partially Consumed Tray In OT Store")
    public void validatePartiallyConsumedTrayFunctionalityInOTStore() {

        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PurchaseReturn oPage_PurchaseReturn = new Page_PurchaseReturn(driver);

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

        boolean bPatientNameFound = false;
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            try {

                CommonActions.selectStoreOnApp(otStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                bPatientNameFound = selectPatientNameInPatientQueue(concatPatientFullName);
                if (bPatientNameFound) {

                    boolean clickOnViewTray = selectViewTrayAction("View Tray", "Partially Consumed");
                    m_assert.assertTrue(clickOnViewTray, "Partially Consumed Status is displayed for tray in patient :" + concatPatientFullName);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.header_trayDetailsHeaderInViewTray, 5);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_CloseTrayButtonInViewTray),
                            " Close Button is displayed and clicked in view tray for closing tray");
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mmaa");
                    dateAndTimeLot = dateFormat.format(new Date()).toString();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseReturn.button_confirmYesTemplate, 5);

                    try {
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseReturn.button_confirmYesTemplate)) {
                            Cls_Generic_Methods.clickElement(oPage_PurchaseReturn.button_confirmYesTemplate);
                            Cls_Generic_Methods.customWait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal(" Confirmation Popup is Not coming" + e);
                    }

                    clickOnViewTray = selectViewTrayAction("", "Closed");
                    m_assert.assertTrue(clickOnViewTray, " Tray status is changed to closed ");

                } else {
                    m_assert.assertTrue(bPatientNameFound + "Patient Not Found In Patient Queue List Of OT Store");
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }

    }

    @Test(enabled = true, description = "Validate Partially Consumed Tray In Items Lot")
    public void ValidatePartiallyConsumedCreateTrayInItemsLotFunctionality() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);


        boolean bItemFoundInLot = false;
        boolean bCreateTrayPartiallyConsumedFoundInLot = false;
        double stockAfterCalculated = 0.0;
        String stockBeforeReturnUI = "";

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(otStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {

                // Validating Return Purchase In Items Lots
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, createTrayItemName);
                Cls_Generic_Methods.customWait(5);
                oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(5);

                int indexOfItemInLot = 0;

                for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
                    List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
                    String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((0)));
                    if (itemDescriptionText.contains(createTrayItemName)) {
                        Cls_Generic_Methods.clickElement(items);
                        Cls_Generic_Methods.waitForElementToBeDisplayed
                                (oPage_Lot.text_transactionDetailsSectionText, 5);
                        bItemFoundInLot = true;
                        break;
                    }
                }
                m_assert.assertTrue(bItemFoundInLot, createTrayItemName + " Clicked in Items Lot at index " + indexOfItemInLot);

                if (bItemFoundInLot) {
                    stockBeforeReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockBeforeInViewTemplate);
                    String stockAfterReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockAfterInViewTemplate);
                    String flowTextUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsFlowInViewTemplate);
                    String dateAndTimeUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsDateAndTimeInViewTemplate);

                    String date = dateAndTimeUI.split("\\|")[0].trim();
                    String time = dateAndTimeUI.split("\\|")[1].trim();
                    date = CommonActions.getRequiredFormattedDateTime("yyyy-MM-dd", "dd-MM-yyyy", date);
                    String purchaseReturnDateAndTime = date + " | " + time;

                    double remainingQty = convertStringToDouble(editItemQuantityUI) - convertStringToDouble(sCONSUMED_QUANTITY);
                    stockAfterCalculated = convertStringToDouble(stockBeforeReturnUI) + remainingQty;

                    String availableStockUI = Cls_Generic_Methods.getTextInElement(oPage_OtStore.text_stockAvailableInItemDetailsInViewTemplate);

                    double availableStockCalculated = convertStringToDouble(currentCreateTrayStock) - convertStringToDouble(sCONSUMED_QUANTITY);


                    if (flowTextUI.equalsIgnoreCase("In (Close Tray)") &&
                            Double.toString(stockAfterCalculated).equals(stockAfterReturnUI) &&
                            purchaseReturnDateAndTime.equalsIgnoreCase(dateAndTimeLot) &&
                            availableStockUI.equalsIgnoreCase(Double.toString(stockAfterCalculated)) &&
                            availableStockUI.equalsIgnoreCase(Double.toString(availableStockCalculated))
                    ) {
                        bCreateTrayPartiallyConsumedFoundInLot = true;
                    }
                }
                m_assert.assertTrue(bCreateTrayPartiallyConsumedFoundInLot,
                        " <b> Create Tray Partially Consumed and Remaining stock added to available stock, as initial stock was: " + stockBeforeReturnUI +
                                " after partially Consumed stock is: " + stockAfterCalculated + "</b> ");


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

    @Test(enabled = true, description = "Validate Partially Consumed Tray In Items Lot")
    public void ValidatePartiallyConsumedEditTrayInItemsLotFunctionality() {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        Page_Lot oPage_Lot = new Page_Lot(driver);


        boolean bItemFoundInLot = false;
        boolean bCreateTrayPartiallyConsumedFoundInLot = false;
        double stockAfterCalculated = 0.0;
        String stockBeforeReturnUI = "";

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(otStoreName);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            try {

                // Validating Return Purchase In Items Lots
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, editTrayItemName);
                Cls_Generic_Methods.customWait(5);
                oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(5);

                int indexOfItemInLot = 0;

                for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
                    List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
                    String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((0)));
                    if (itemDescriptionText.contains(editTrayItemName)) {
                        Cls_Generic_Methods.clickElement(items);
                        Cls_Generic_Methods.waitForElementToBeDisplayed
                                (oPage_Lot.text_transactionDetailsSectionText, 5);
                        bItemFoundInLot = true;
                        break;
                    }
                }
                m_assert.assertTrue(bItemFoundInLot, editTrayItemName + " Clicked in Items Lot at index " + indexOfItemInLot);

                if (bItemFoundInLot) {
                    stockBeforeReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockBeforeInViewTemplate);
                    String stockAfterReturnUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsStockAfterInViewTemplate);
                    String flowTextUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsFlowInViewTemplate);
                    String dateAndTimeUI = Cls_Generic_Methods.getTextInElement(oPage_Purchase.table_transactionDetailsDateAndTimeInViewTemplate);

                    String date = dateAndTimeUI.split("\\|")[0].trim();
                    String time = dateAndTimeUI.split("\\|")[1].trim();
                    date = CommonActions.getRequiredFormattedDateTime("yyyy-MM-dd", "dd-MM-yyyy", date);
                    String purchaseReturnDateAndTime = date + " | " + time;

                    double remainingQty = convertStringToDouble(editItemQuantityUI) - convertStringToDouble(sCONSUMED_QUANTITY);
                    stockAfterCalculated = convertStringToDouble(stockBeforeReturnUI) + remainingQty;

                    String availableStockUI = Cls_Generic_Methods.getTextInElement(oPage_OtStore.text_stockAvailableInItemDetailsInViewTemplate);

                    double availableStockCalculated = convertStringToDouble(currentCreateTrayStock) - convertStringToDouble(sCONSUMED_QUANTITY);


                    if (flowTextUI.equalsIgnoreCase("In (Close Tray)") &&
                            Double.toString(stockAfterCalculated).equals(stockAfterReturnUI) &&
                            purchaseReturnDateAndTime.equalsIgnoreCase(dateAndTimeLot) &&
                            availableStockUI.equalsIgnoreCase(Double.toString(stockAfterCalculated)) &&
                            availableStockUI.equalsIgnoreCase(Double.toString(availableStockCalculated))
                    ) {
                        bCreateTrayPartiallyConsumedFoundInLot = true;
                    }
                }
                m_assert.assertTrue(bCreateTrayPartiallyConsumedFoundInLot,
                        " <b> Edit Tray Partially Consumed and Remaining stock added to available stock, as initial stock was: " + stockBeforeReturnUI +
                                " after partially Consumed stock is: " + stockAfterCalculated + "</b> ");


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            } finally {
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = " Creating Tray For Fully Consumed Tray In BOM")
    public void validateCreateTrayForFullyConsumedInOTStore() {

        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

        boolean bPatientNameFound = false;
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            try {

                CommonActions.selectStoreOnApp(otStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                bPatientNameFound = selectPatientNameInPatientQueue(concatPatientFullName);
                if (bPatientNameFound) {

                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mmaa");
                    dateAndTime = dateFormat.format(new Date()).toString();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_createTray),
                            "Create Tray Button Clicked In Patient Queue For Patient name :" + concatPatientFullName);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_descriptionInTrayTemplate, 4);
                    Cls_Generic_Methods.clickElement(oPage_OtStore.button_descriptionInTrayTemplate);
                    Cls_Generic_Methods.customWait(2);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_lotSearchBoxInTrayTemplate, createTrayItemName);
                    Cls_Generic_Methods.customWait();

                    int indexOfItemDescription = -1;
                    boolean bItemStockStatus = false;

                    for (WebElement itemName : oPage_OtStore.list_itemNameListInTrayTemplate) {
                        if (Cls_Generic_Methods.getTextInElement(itemName).equalsIgnoreCase(createTrayItemName)) {
                            indexOfItemDescription = oPage_OtStore.list_itemNameListInTrayTemplate.indexOf(itemName);
                            currentCreateItemRate = Cls_Generic_Methods.getTextInElement(oPage_OtStore.list_itemRateListInTrayTemplate.get(indexOfItemDescription));
                            currentCreateTrayStock = Cls_Generic_Methods.getTextInElement(oPage_OtStore.list_itemStockListInTrayTemplate.get(indexOfItemDescription));
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemName),
                                    " Item Selected From Left Panel In Create Tray As :" + createTrayItemName);
                            bItemStockStatus = true;
                            Cls_Generic_Methods.customWait(5);
                            break;
                        }
                    }

                    m_assert.assertTrue(bItemStockStatus,
                            " Purchase Item Selected From Left Panel in Create Tray Template: " + createTrayItemName);

                    for (WebElement itemQuantity : oPage_OtStore.list_itemQuantityListInTrayTemplate) {
                        Cls_Generic_Methods.sendKeysIntoElement(itemQuantity, itemQuantityUI);
                    }

                    String netAmountPerItem = "";
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    for (WebElement itemQuantity : oPage_OtStore.list_itemAmountListInTrayTemplate) {
                        netAmountPerItem = Cls_Generic_Methods.getElementAttribute(itemQuantity, "value");
                    }

                    String mrpPerItem = "";
                    for (WebElement itemMrp : oPage_OtStore.list_itemMRPListInTrayTemplate) {
                        mrpPerItem = Cls_Generic_Methods.getTextInElement(itemMrp);
                    }

                    m_assert.assertTrue(("₹" + mrpPerItem).equalsIgnoreCase(currentCreateItemRate),
                            "MRP in View Item is same as Item Rate Price In Left Panel" + currentCreateItemRate);

                    String netAmountCalculated = decimalFormat.format(convertStringToDouble(mrpPerItem) * convertStringToDouble(itemQuantityUI));
                    m_assert.assertTrue(netAmountPerItem.equals(netAmountCalculated),
                            "Net Amount Calculated Correctly for Each Item" + netAmountCalculated);
                    totalNetPriceValue = Cls_Generic_Methods.getElementAttribute(oPage_OtStore.input_trayTotalCostValue, "value");
                    m_assert.assertTrue(totalNetPriceValue.equalsIgnoreCase(netAmountPerItem),
                            "Total Net Price calculated correctly and same as sum of all item amount" + totalNetPriceValue);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                            "Save Changes Button Clicked In A Lot Inventory Template");
                    dateAndTimeLot = dateFormat.format(new Date()).toString();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 10);

                    boolean trayFound = selectViewTrayAction("", "Open");
                    m_assert.assertTrue(trayFound, " Tray Created Successfully and Found In patient Queue");


                } else {
                    m_assert.assertTrue(bPatientNameFound + "Patient Not Found In Patient Queue List Of OT Store");
                }


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }

    }

    @Test(enabled = true, description = "Validate Full Tray In Bills Of Materials")
    public void validateFullyTrayInBillOfMaterialsForm() {


        Page_BillOfMaterial oPage_BillOfMaterial = new Page_BillOfMaterial(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        boolean bValidatePatientFound = false;

        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("IPD");

            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today);
            Cls_Generic_Methods.customWait();

            bValidatePatientFound = selectPatientNameInIPD(concatPatientFullName);
            m_assert.assertInfo(bValidatePatientFound, "Validate patient found");

            if (bValidatePatientFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_intraOperativeSection)) {

                    // bill of material
                    try {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_billOfMaterialTemplate, 4);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_billOfMaterialTemplate),
                                "Clicked on bill of material template ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_addBillOfMaterial, 4);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_addBillOfMaterial),
                                "Clicked on + bill of material to fill the template ");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_saveBom, 8);
                        for (WebElement consumedQty : oPage_BillOfMaterial.list_inputConsumedQuantityListInBom) {
                            if (Cls_Generic_Methods.isElementDisplayed(consumedQty)) {
                                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(consumedQty, itemQuantityUI),
                                        "Consumed quantity entered as " + itemQuantityUI);
                            }
                        }

                        List<String> billableUnitPriceValueList = new ArrayList<String>();

                        for (WebElement billableUnitPrice : oPage_BillOfMaterial.list_inputBillableUnitPriceListInBom) {
                            billableUnitPriceValueList.add(Cls_Generic_Methods.getElementAttribute(billableUnitPrice, "value"));
                        }

                        double totalPriceCalculated = 0.00;
                        int indexOfItem = -1;
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        for (WebElement totalItem : oPage_BillOfMaterial.list_totalPerItemListInBom) {

                            indexOfItem = oPage_BillOfMaterial.list_totalPerItemListInBom.indexOf(totalItem);
                            String totalValuePerTrayItem = Cls_Generic_Methods.getElementAttribute(totalItem, "value");
                            double totalPerItemCalculated = convertStringToDouble(billableUnitPriceValueList.get(indexOfItem)) *
                                    convertStringToDouble(itemQuantityUI);

                            m_assert.assertTrue(totalValuePerTrayItem.equalsIgnoreCase(decimalFormat.format(totalPerItemCalculated)),
                                    " Total Calculated Correctly For Each Item In Bills Of Materials as : " + totalValuePerTrayItem);
                            totalPriceCalculated = totalPriceCalculated + totalPerItemCalculated;

                        }

                        String totalPriceBomBeforeBillable = Cls_Generic_Methods.getElementAttribute(oPage_BillOfMaterial.input_totalPriceBom, "value");
                        m_assert.assertTrue(totalPriceBomBeforeBillable.equalsIgnoreCase(""),
                                " Total Price Displaying correctly as empty without Billable checkbox selection");

                        for (WebElement billableCheckbox : oPage_BillOfMaterial.list_inputBillableCheckboxListInBom) {
                            if (Cls_Generic_Methods.isElementDisplayed(billableCheckbox)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(billableCheckbox),
                                        "Billable Checkbox Clicked at index " + oPage_BillOfMaterial.list_inputBillableCheckboxListInBom.indexOf(billableCheckbox));
                            }
                        }

                        String totalPriceBomAfterBillable = Cls_Generic_Methods.getElementAttribute(oPage_BillOfMaterial.input_totalPriceBom, "value");

                        m_assert.assertTrue(totalPriceBomAfterBillable.equalsIgnoreCase(decimalFormat.format(totalPriceCalculated)),
                                " Total Price Calculated correctly as " + totalPriceBomAfterBillable + " with Billable checkbox selection");

                        Cls_Generic_Methods.sendKeysIntoElement(oPage_BillOfMaterial.input_discountBom, "10");

                        String totalBillingPrice = Cls_Generic_Methods.getElementAttribute(oPage_BillOfMaterial.input_totalBillingPriceBom, "value");

                        double totalBillingPriceCalculated = convertStringToDouble(totalPriceBomAfterBillable) - convertStringToDouble("10");

                        m_assert.assertTrue(totalBillingPrice.equalsIgnoreCase(decimalFormat.format(totalBillingPriceCalculated)),
                                "Total Billing Price Calculated Correctly as : " + totalBillingPriceCalculated);

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_saveBom),
                                " Bills of Material saved");


                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Exception while filling bill of material chart Template " + e);
                    }

                } else {
                    m_assert.assertInfo(false,
                            "Admission form in Intra operative section is not filled, please fill admission form");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate Fully Consumed Tray In OT Store")
    public void validateFullyConsumedTrayFunctionalityInOTStore() {

        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

        boolean bPatientNameFound = false;
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            try {

                CommonActions.selectStoreOnApp(otStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                bPatientNameFound = selectPatientNameInPatientQueue(concatPatientFullName);
                if (bPatientNameFound) {

                    boolean clickOnViewTray = selectViewTrayAction("View Tray", "Consumed");
                    m_assert.assertTrue(clickOnViewTray, "Tray Status Changes to Consumed for patient :" + concatPatientFullName);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_CloseTrayButtonInViewTray, 5);
                    for (WebElement eItemDetails : oPage_OtStore.list_itemDetailsDataListInViewTray) {
                        List<WebElement> trayRow = eItemDetails.findElements(By.xpath("./child::*"));
                        String initialQty = Cls_Generic_Methods.getTextInElement(trayRow.get(4));
                        String availableQty = Cls_Generic_Methods.getTextInElement(trayRow.get(5));

                        m_assert.assertTrue(initialQty.equalsIgnoreCase(itemQuantityUI),
                                " Initial Quantity is Displaying correctly as  " + initialQty);
                        m_assert.assertTrue(availableQty.equalsIgnoreCase("0.0"),
                                " Available Quantity is Displaying correctly as  " + availableQty);
                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 5);

                    }

                } else {
                    m_assert.assertTrue(bPatientNameFound + "Patient Not Found In Patient Queue List Of OT Store");
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }

    }

    @Test(enabled = true, description = "Validate Delete Tray In OT Store")
    public void validateDeleteTrayInOTStore() {

        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        Page_PurchaseReturn oPage_PurchaseReturn = new Page_PurchaseReturn(driver);

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

        boolean bPatientNameFound = false;
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            try {

                CommonActions.selectStoreOnApp(otStoreName);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                bPatientNameFound = selectPatientNameInPatientQueue(concatPatientFullName);
                if (bPatientNameFound) {

                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mmaa");
                    dateAndTime = dateFormat.format(new Date()).toString();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_createTray),
                            "Create Tray Button Clicked In Patient Queue For Patient name :" + concatPatientFullName);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_descriptionInTrayTemplate, 4);
                    Cls_Generic_Methods.clickElement(oPage_OtStore.button_descriptionInTrayTemplate);
                    Cls_Generic_Methods.customWait(2);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_OtStore.input_lotSearchBoxInTrayTemplate, createTrayItemName);
                    Cls_Generic_Methods.customWait();

                    int indexOfItemDescription = -1;
                    boolean bItemStockStatus = false;

                    for (WebElement itemName : oPage_OtStore.list_itemNameListInTrayTemplate) {
                        if (Cls_Generic_Methods.getTextInElement(itemName).equalsIgnoreCase(createTrayItemName)) {
                            indexOfItemDescription = oPage_OtStore.list_itemNameListInTrayTemplate.indexOf(itemName);
                            currentCreateItemRate = Cls_Generic_Methods.getTextInElement(oPage_OtStore.list_itemRateListInTrayTemplate.get(indexOfItemDescription));
                            currentCreateTrayStock = Cls_Generic_Methods.getTextInElement(oPage_OtStore.list_itemStockListInTrayTemplate.get(indexOfItemDescription));
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemName),
                                    " Item Selected From Left Panel In Create Tray As :" + createTrayItemName);
                            bItemStockStatus = true;
                            Cls_Generic_Methods.customWait(5);
                            break;
                        }
                    }

                    m_assert.assertTrue(bItemStockStatus,
                            " Purchase Item Selected From Left Panel in Create Tray Template: " + createTrayItemName);

                    for (WebElement itemQuantity : oPage_OtStore.list_itemQuantityListInTrayTemplate) {
                        Cls_Generic_Methods.sendKeysIntoElement(itemQuantity, itemQuantityUI);
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot),
                            "Save Changes Button Clicked In A Lot Inventory Template");
                    dateAndTimeLot = dateFormat.format(new Date()).toString();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 10);

                    int createTrayListSizeAfterCreate = oPage_OtStore.list_trayItemListInViewPatient.size();
                    boolean trayFound = selectViewTrayAction("View Tray", "Open");
                    m_assert.assertTrue(trayFound, " Tray Found and Clicked In patient Queue");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_deleteButtonInViewTray, 5);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OtStore.button_deleteButtonInViewTray),
                            "Delete Button Clicked in View Tray");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseReturn.button_confirmYesTemplate, 5);

                    try {
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseReturn.button_confirmYesTemplate)) {
                            Cls_Generic_Methods.clickElement(oPage_PurchaseReturn.button_confirmYesTemplate);
                            Cls_Generic_Methods.customWait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal(" Confirmation Popup is Not coming" + e);
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 2);
                    int createTrayListSizeAfterDelete = oPage_OtStore.list_trayItemListInViewPatient.size();

                    m_assert.assertTrue((createTrayListSizeAfterCreate - 1) == createTrayListSizeAfterDelete,
                            " Delete Tray Working In Ot Store");

                } else {
                    m_assert.assertInfo(bPatientNameFound + "Patient Not Found In Patient Queue List Of OT Store");
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }

    }

    public static boolean selectPatientNameInPatientQueue(String expectedPatientName) {

        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        boolean bPatientNameFoundAndClicked = false;
        String patientName = "";
        try {
            for (WebElement patientNameElement : oPage_OtStore.list_patientNameListInQueue) {
                if (Cls_Generic_Methods.isElementDisplayed(patientNameElement)) {
                    patientName = Cls_Generic_Methods.getElementAttribute(patientNameElement, "title");
                    if (expectedPatientName.equals(patientName.trim())) {
                        Cls_Generic_Methods.clickElement(driver, patientNameElement);
                        Cls_Generic_Methods.customWait(5);
                        bPatientNameFoundAndClicked = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Patient Is not present or not clicked" + e);
        }

        return bPatientNameFoundAndClicked;
    }

    public static boolean selectPatientNameInIPD(String expectedPatientName) {

        Page_IPD oPage_IPD = new Page_IPD(driver);
        boolean bPatientNameFoundAndClicked = false;

        try {

            for (WebElement patient : oPage_IPD.rows_patientNamesOnIPD) {
                if (Cls_Generic_Methods.isElementDisplayed(patient)) {
                    String patientNameOnUI = Cls_Generic_Methods.getTextInElement(patient);
                    if (expectedPatientName.equals(patientNameOnUI.trim())) {
                        Cls_Generic_Methods.clickElement(driver, patient);
                        bPatientNameFoundAndClicked = true;
                        Cls_Generic_Methods.customWait(5);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Patient Is not present or not clicked" + e);

        }

        return bPatientNameFoundAndClicked;
    }

    public static boolean selectViewTrayAction(String actionToPerform, String status) {

        boolean bTrayFound = false;
        boolean bViewTrayClicked = false;
        boolean bTrayFoundOrCLicked = false;


        Page_OtStore oPage_OtStore = new Page_OtStore(driver);

        List<String> trayHeaderList = new ArrayList<String>();
        try {

            for (WebElement trayHeader : oPage_OtStore.list_trayHeaderListInViewPatient) {
                trayHeaderList.add(Cls_Generic_Methods.getTextInElement(trayHeader));
            }
            for (WebElement trayItem : oPage_OtStore.list_trayItemListInViewPatient) {

                List<WebElement> trayRow = trayItem.findElements(By.xpath("./child::*"));
                String createdOn = Cls_Generic_Methods.getTextInElement(trayRow.get(trayHeaderList.indexOf("Created On")));
                String trayStatus = Cls_Generic_Methods.getTextInElement(trayRow.get(trayHeaderList.indexOf("Tray Status")));
                String trayAction = Cls_Generic_Methods.getTextInElement(trayRow.get(trayHeaderList.indexOf("Action")));

                WebElement viewTray = trayRow.get(trayHeaderList.indexOf("Action")).findElement(By.xpath("./a"));

                if (createdOn.equalsIgnoreCase(dateAndTime) &&
                        trayStatus.equalsIgnoreCase(status) &&
                        trayAction.equalsIgnoreCase("View Tray")) {
                    if (actionToPerform.equalsIgnoreCase("View Tray")) {
                        Cls_Generic_Methods.clickElement(viewTray);
                        bViewTrayClicked = true;
                    }
                    bTrayFound = true;
                    break;
                }
            }

            if (actionToPerform.equalsIgnoreCase("View Tray")) {
                bTrayFoundOrCLicked = bViewTrayClicked;
            } else {
                bTrayFoundOrCLicked = bTrayFound;
            }


        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return bTrayFoundOrCLicked;
    }


}
