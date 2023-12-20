package tests.inventoryStores.pharmacyStore.Order;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.templates.Page_InventorySearchCommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_CategoryMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorMaster;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Order.Page_WorkOrder;
import tests.inventoryStores.pharmacyStore.InventorySearchTest;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static pages.commonElements.CommonActions.getRandomString;
import static pages.commonElements.CommonActions.selectOptionAndSearch;

public class WorkOrderTest extends TestBase {
    public static String workOrderNo,oldWorkOrderNo;
    String workOrderCreatedAt;
    public String sVendorName = "Supplier ABC";
    public String vendor_address;
    public String vendorGstNo;
    public String vendorCreditDays;
    public String store = "Pharmacy automation- Pharmacy";
    public String sItemCategoryName ;
    public String sItem1Description ,oldItem;
    public String sItem2Description ;
    public String sItem1Code;
    public String sItem2Code;
    public String sInstruction = "AUTO-TEST";
    public double grossAmount = 0;
    public double totalDiscount = 0;
    public double itemTotalNetAmount = 0;
    public double expectedTotalOtherCharges = 0;
    public double totalTaxGST5 = 0;
    public double totalTaxGST12 = 0;

    public String otherCharges = "100";
    public String orderType = "Urgent";
    String cancelledAt;
    public String otherChargesPercent = "50";
    String globalDiscount = "20.2";
    public HashMap<String, String> item1Details;
    public HashMap<String, String> item2Details;

    public Page_CategoryMaster oPage_CategoryMaster;
    public Page_StoreSetUp oPage_StoreSetUp;
    public Page_Navbar oPage_Navbar;
    public Page_CommonElements oPage_CommonElements;
    public Page_ItemMaster oPage_ItemMaster;
    public Page_VendorMaster oPage_VendorMaster;
    public Page_WorkOrder oPage_WorkOrder;
    public String expectedLoggedInUser = EHR_Data.user_PRAkashTest;

    @Test(description = "Validating Create Work Order")
    public void validateCreateWorkOrder() {
        oPage_WorkOrder = new Page_WorkOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);

        grossAmount = 0;
        totalDiscount = 0;
        totalTaxGST5 = 0;
        totalTaxGST12 = 0;
        itemTotalNetAmount = 0;
        expectedTotalOtherCharges = 0;

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            boolean categoryLinked =  createNewCategoryAndLinkItToStore();

            if (categoryLinked) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectStoreOnApp(store);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);

                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_new), "Clicked New Button");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.text_headerCreteWorkOrder, 10), "Validated --> Create New Order Page Displayed");
                m_assert.assertTrue(selectByOptions(oPage_WorkOrder.select_vendor, sVendorName), "Selected Vendor : <b>" + sVendorName + "</b>");
                Cls_Generic_Methods.customWait(5);
                int totalRow = oPage_WorkOrder.list_row_createWorkOrder.size();
                if (totalRow > 0) {
                    int rowNo = 0;
                    while (rowNo < totalRow) {
                        String itemCode = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_column_itemCode_createWorkOrder.get(rowNo));
                        String itemDescription = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_column_itemDescription_createWorkOrder.get(rowNo));
                        String itemCategory = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_column_itemCategory_createWorkOrder.get(rowNo));

                        if (itemDescription.equals(sItem1Description)) {
                            m_assert.assertTrue("Validated Work Order --> Item Description : <b>" + sItem1Description + "</b>");
                            m_assert.assertTrue(itemCategory.equalsIgnoreCase(sItemCategoryName), "Validated Work Order --> Item Category : <b>" + itemCategory + "</b>");
                            m_assert.assertTrue(itemCode.equals(sItem1Code+"-100"), "Validated Work Order --> Item Code : <b>" + itemCode + "</b>");
                            break;
                        }
                        rowNo++;
                    }
                }

                //Validate Search
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_searchItemCreateWO, sItem1Description), "Entered <b>" + sItem1Description + "</b> in item search");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_searchItemCreateWO, " ");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_searchItemCreateWO, "" + Keys.BACK_SPACE);
                Cls_Generic_Methods.customWait(5);
                if (oPage_WorkOrder.list_row_createWorkOrder.size() == 0) {
                    String itemDescription = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_column_itemDescription_createWorkOrder.get(0));
                    m_assert.assertTrue(itemDescription.equals(sItem1Description), "Validated Search Functionality --> Found Item = " + sItem1Description);
                } else {
                    m_assert.assertFatal("Unable to validate search functionality");
                }

                //

                boolean item2Selected = selectItemInCreateWorkOrder(sItem2Description);
                Cls_Generic_Methods.customWait();
                boolean item1Selected = selectItemInCreateWorkOrder(sItem1Description);
                Cls_Generic_Methods.customWait(5);

                Cls_Generic_Methods.customWait();
                if (item1Selected && item2Selected) {
                    int initialRowSize = oPage_WorkOrder.list_tableBody_createWorkOrder.size();
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_removeFromList_createWorkOrder), "Clicked <b>Remove From List</b> Button");
                    Cls_Generic_Methods.customWait();
                    int finalRowSize = oPage_WorkOrder.list_tableBody_createWorkOrder.size();
                    m_assert.assertTrue(initialRowSize == (finalRowSize + 1), "Validated Remove From List Functionality --> Item Removed");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_removeFromList_createWorkOrder), "Clicked <b>Remove From List</b> Button");
                    Cls_Generic_Methods.customWait();
                    int final2RowSize = oPage_WorkOrder.list_tableBody_createWorkOrder.size();
                    m_assert.assertTrue(finalRowSize == final2RowSize, "Validated Remove From List Functionality --> Item not removed if only one is selected");
                }
                selectItemInCreateWorkOrder(sItem2Description);
                Cls_Generic_Methods.customWait();
                //Validate alert msg
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_saveChanges_createWorkOrder), "Clicked Save Changes Button without filling anything");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.select_orderTypeCreateWO.findElement(By.xpath("./following-sibling::label"))), "Validated Alert message on Order Type");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.select_otherCharges_createWorkOrder.get(0).findElement(By.xpath("./following-sibling::label"))), "Validated Alert message on Other Charges Type");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.input_otherCharges_createWorkOrder.get(0).findElement(By.xpath("./following-sibling::label"))), "Validated Alert message on Other Charges input text box");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.text_displayOtherCharges_createWorkOrder.findElement(By.xpath("./following-sibling::label"))), "Validated Alert message on Other Charges Display box");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.list_inputTableInstructions_createWorkOrder.get(0).findElement(By.xpath("./following-sibling::label"))), "Validated Alert message on Instructions");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.list_inputTableRate_createWorkOrder.get(0).findElement(By.xpath("./following-sibling::label"))), "Validated Alert message on Rate");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.list_inputTableQuantity_createWorkOrder.get(0).findElement(By.xpath("./following-sibling::label"))), "Validated Alert message on Quantity");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.list_selectTableTax_createWorkOrder.get(0).findElement(By.xpath("./following-sibling::label"))), "Validated Alert message on Tax Type");

                //Order Notes And Order Type
                String orderNotes = "Test Notes";
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_orderNotesCreateWO, orderNotes), "Entered <i>" + orderNotes + "</i> in Order Notes");
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_WorkOrder.select_orderTypeCreateWO, orderType), "Selected <i>" + orderType + "</i> in Order Type");

                //Set Date and Time
                Cls_Generic_Methods.clickElement(oPage_WorkOrder.input_orderDateCreateWO);
                boolean datePickerFound = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.input_todayBillDate, 3);
                if (datePickerFound) {
                    Cls_Generic_Methods.clickElement(oPage_WorkOrder.input_todayBillDate);
                    String date = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.input_orderDateCreateWO, "value").trim();
                    m_assert.assertInfo("Selected Work Order Date as : <b>" + date + "</b>");
                } else {
                    String date = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.input_orderDateCreateWO, "value").trim();
                    m_assert.assertFatal("Date Picker not displayed to select Order Date - Found Date = " + date);
                }
                m_assert.assertInfo(setTime(oPage_WorkOrder.input_orderTimeCreateWO, "10:00:PM"), "Selected Work Order Time as : <b>10:00:PM</b>");

                //Credit Days
                String creditDays = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.input_creditDaysCreateWO, "value");
                m_assert.assertTrue(creditDays.equals(vendorCreditDays), "Validated Credit Days in Work Order --> Credit Days : <b>" + creditDays + "</b>");

                String actualExpiryDate = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.input_expiryDateCreateWO, "value");
                String expectedExpiryDate = getTomorrowDate();
                m_assert.assertTrue(actualExpiryDate.equals(expectedExpiryDate), "Validated Work Order Expiry Date --> Expiry Day : <b>" + actualExpiryDate + "</b>");

                //Validate Item Table

                Cls_Generic_Methods.customWait();
                item1Details = fillItemTable(sItem1Description, "GST5");
                item2Details = fillItemTable(sItem2Description, "GST12");


                m_assert.assertInfo(selectByOptions(oPage_WorkOrder.select_otherCharges_createWorkOrder.get(0), "Fitting Charges"), "Selected <b>Fitting Charges</b> on Other Charges");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_otherCharges_createWorkOrder.get(0), otherCharges), "Entered <b>" + otherCharges + "</b> on Other Charges");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_otherChargesPercent_createWorkOrder, otherChargesPercent), "Entered <b>" + otherChargesPercent + "</b> on Other Charges Percentage");
                Cls_Generic_Methods.customWait();
                double expectedOtherCharges = Double.parseDouble(otherCharges) + ((Double.parseDouble(otherCharges) * Double.parseDouble(otherChargesPercent)) / 100);
                double actualOtherCharges = Double.parseDouble(Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_displayOtherCharges_createWorkOrder, "value"));
                m_assert.assertTrue(actualOtherCharges == expectedOtherCharges, "Validated Displayed Other Charges");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_addOtherCharges_createWorkOrder), "Clicked Add new Other Charges button");
                Cls_Generic_Methods.customWait();
                int noOfOtherCharges = oPage_WorkOrder.input_otherCharges_createWorkOrder.size();
                m_assert.assertTrue(noOfOtherCharges == 2, "Validated --> Add Other Charges");
                m_assert.assertInfo(selectByOptions(oPage_WorkOrder.select_otherCharges_createWorkOrder.get(1), "Item_other"), "Selected <b>Item_other</b> on Newly added Other Charges");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_otherCharges_createWorkOrder.get(1), otherCharges), "Entered <b>" + otherCharges + "</b> on Newly added Other Charges");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_minusOtherCharges_createWorkOrder.get(1)), "Clicked <b>-</b> in other charge");
                Cls_Generic_Methods.customWait();
                String actualTotalOtherCharges = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_finalOtherCharges_createWorkOrder, "value").trim();
                expectedTotalOtherCharges = expectedOtherCharges - Double.parseDouble(otherCharges);
                m_assert.assertTrue(Double.parseDouble(actualTotalOtherCharges) == expectedTotalOtherCharges, "Validated Total Other Charges : <b>" + expectedTotalOtherCharges + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_removeOtherCharges_createWorkOrder.get(1)), "Clicked Remove Other Charges button");
                Cls_Generic_Methods.customWait();
                int noOfOtherChargesAfterDelete = oPage_WorkOrder.input_otherCharges_createWorkOrder.size();
                m_assert.assertTrue(noOfOtherChargesAfterDelete == 1, "Validated --> Delete Other Charges");
                Cls_Generic_Methods.customWait();
                actualTotalOtherCharges = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_finalOtherCharges_createWorkOrder, "value").trim();
                expectedTotalOtherCharges = expectedTotalOtherCharges + Double.parseDouble(otherCharges);
                m_assert.assertTrue(Double.parseDouble(actualTotalOtherCharges) == expectedTotalOtherCharges, "Validated Total Other Charges after deleting other charge : <b>" + expectedTotalOtherCharges + "</b>");

                //Global Discount
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_globalDiscount_createWorkOrder, globalDiscount), "Entered <b>" + globalDiscount + "</b> in Global Discount");
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(oPage_WorkOrder.select_globalDiscountType_createWorkOrder, 1), "Selected <b>%</b> in Global Discount Type");

                //Validate Net Amount
                String actualNetAmount = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_finalNetAmount_createWorkOrder, "value").trim();
                m_assert.assertTrue(Double.parseDouble(actualNetAmount) == (formatDecimalNumber(itemTotalNetAmount) + expectedTotalOtherCharges), "Validated Total Net Amount : <b>" + actualNetAmount + "</b>");

                //TERMS
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(oPage_WorkOrder.select_paymentTerms, 1), "Selected Payment Terms");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_deliveryTerms), "Clicked Delivery Terms");
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(oPage_WorkOrder.select_deliveryTerms, 1), "Selected Delivery Terms");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_saveChanges_createWorkOrder), "Clicked <b>Save Changes</b>");
                workOrderCreatedAt = getCurrentDateTime();

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 15);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);

                selectWorkOrder("pending");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.text_rhs_workOrderNo, 10);
                workOrderNo = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderNo);
                m_assert.assertTrue("Work Order Created successfully --> Work Order No : <b>" + workOrderNo + "</b>");

            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFalse("Unable to Validate Create Work Order --> " + e);
        }
    }

    @Test(description = "Validating Work Order RHS")
    public void validateWorkOrderRHS() {
        oPage_WorkOrder = new Page_WorkOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);
            boolean bSelectWorkOrder = selectWorkOrder("pending", workOrderNo);

            if (bSelectWorkOrder) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.text_rhs_workOrderNo, 10);
                String woCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderCreatedByUser);
                String woCreatedStore = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderCreatedStore);
                String woCreatedAtDateAndTime = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderCreatedAt);
                String woStatus = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderStatus);
                String woType = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderType);
                String vendorDetails = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderVendor);
                String vendorGst = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderVendorGST);

                m_assert.assertTrue(woCreatedBy.equals(expectedLoggedInUser), "Validated Work Order RHS --> Work Order Created By User <b>: " + woCreatedBy + "</b>");
                m_assert.assertTrue(woCreatedStore.contains(store.split("-")[0]), "Validated Work Order RHS --> Work Order Created Store <b>: " + woCreatedStore + "</b>");
                m_assert.assertTrue(woCreatedAtDateAndTime.contains(workOrderCreatedAt.split(":")[0]), "Validated Work Order RHS --> Work Order Created At <b>: " + woCreatedAtDateAndTime + "</b>");
                m_assert.assertTrue(woStatus.equals("pending"), "Validated Work Order RHS --> Status <b>: open </b>");
                m_assert.assertTrue(woType.equals(orderType), "Validated Work Order RHS --> Type <b>: " + woType + "</b>");
                m_assert.assertTrue(vendorDetails.contains(sVendorName + " - " + vendor_address), "Validated Work Order RHS --> Vendor Details <b>: " + vendorDetails + "</b>");
                m_assert.assertTrue(vendorGst.equals(vendorGstNo), "Validated Work Order RHS --> Vendor GST No <b>: " + vendorGst + "</b>");

                //Table Validation
                validateWorkOrderRHS_TableValue(sItem1Description, item1Details);
                validateWorkOrderRHS_TableValue(sItem2Description, item2Details);

                //Item info
                HashMap<String, String> itemInfo = new HashMap<>();
                int infoNo = 0;
                double gstValue = 0;
                for (WebElement columnValue : oPage_WorkOrder.list_rhsItemInfoHeader) {
                    String key = Cls_Generic_Methods.getTextInElement(columnValue);
                    String value = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_rhsItemInfoValue.get(infoNo));
                    itemInfo.put(key, value);
                    infoNo++;
                }
                itemInfo.put("GST", String.valueOf(formatDecimalNumber(gstValue)));

                System.out.println(itemInfo);
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("Gross Amount")) == formatDecimalNumber(grossAmount), "Validated RHS Item Information --> Gross Amount = <b>" + itemInfo.get("Gross Amount") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("Total Discount")) == formatDecimalNumber(totalDiscount), "Validated RHS Item Information --> Total Discount = <b>" + itemInfo.get("Total Discount") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("GST5")) == formatDecimalNumber(totalTaxGST5), "Validated RHS Item Information --> Total Tax Amount GST5= <b>" + itemInfo.get("GST5") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("GST12")) == formatDecimalNumber(totalTaxGST12), "Validated RHS Item Information --> Total Tax Amount GST12= <b>" + itemInfo.get("GST12") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("Other Charges")) == formatDecimalNumber(expectedTotalOtherCharges), "Validated RHS Item Information --> Other Charges = <b>" + itemInfo.get("Other Charges") + "</b>");
                m_assert.assertTrue(Double.parseDouble(itemInfo.get("Net Amount")) == (formatDecimalNumber(itemTotalNetAmount) + expectedTotalOtherCharges), "Validated RHS Item Information --> Net Amount = <b>" + itemInfo.get("Net Amount") + "</b>");
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Work Order Rhs " + e);
        }
    }

    @Test(description = "Validating Edit Work Order")
    public void validateEditWorkOrder() {
        oPage_WorkOrder = new Page_WorkOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);
            boolean bSelectWorkOrder = selectWorkOrder("pending", workOrderNo);

            if (bSelectWorkOrder) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_edit, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_edit), "Clicked <b>Edit</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.text_headerCreteWorkOrder, 10);
                validateFillItemTable(sItem1Description, item1Details);
                validateFillItemTable(sItem2Description, item2Details);

                String otherChargeType = Cls_Generic_Methods.getSelectedValue(oPage_WorkOrder.select_otherCharges_createWorkOrder.get(0));
                m_assert.assertTrue(otherChargeType.equals("Fitting Charges"), "Validated -->Selected Other Charges");

                String displayedOtherCharges = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_displayOtherCharges_createWorkOrder, "value");
                m_assert.assertTrue(Double.parseDouble(displayedOtherCharges) == expectedTotalOtherCharges, "Validated -->Displayed Other Charges");

                String totalOtherCharge = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_finalTotalDiscount_createWorkOrder, "value");
                m_assert.assertTrue(Double.parseDouble(totalOtherCharge) == expectedTotalOtherCharges, "Validated -->Total Other Charges");

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_updateReason, "TO"), "Entered lesser than minimum character in update reason");
                Cls_Generic_Methods.customWait();
                m_assert.assertFalse(Cls_Generic_Methods.isElementEnabled(oPage_WorkOrder.button_updateChanges), "Validated Update Reason --> <b>Update Changes</b> button is disabled");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_updateReason, "TEST"), "Entered <b>TEST</B> in update reason");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_WorkOrder.button_updateChanges), "Validated Update Reason --> <b>Update Changes</b> button is enabled");

                String netAmountValue = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_finalNetAmount_createWorkOrder, "value");
                m_assert.assertTrue(Double.parseDouble(netAmountValue) == formatDecimalNumber(itemTotalNetAmount), "Validated Net Amount : <b>" + netAmountValue + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_updateChanges), "Clicked <b>Update Changes</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 15);

                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            } else {
                m_assert.assertFatal("Unable to select work order");
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate Edit functionality " + e);
            e.printStackTrace();
        }
    }

    @Test(description = "Validating Approve Work Order")
    public void validateApproveWorkOrder() {
        oPage_WorkOrder = new Page_WorkOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);
            boolean bSelectWorkOrder = selectWorkOrder("pending", workOrderNo);

            if (bSelectWorkOrder) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_approve, 15);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_approve), "Clicked <b>Approve</b> button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_yes, 6);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_WorkOrder.button_yes);

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.label_approved, 10), "Validated -->Notify message --> Work Order is approved ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.text_rhs_workOrderStatus, 10);
                String status = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderStatus);
                m_assert.assertTrue(status.equals("approved"), "Validated RHS Work Order Status : <b>" + status + "</b>");


                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_newTransaction), "Validated Approve --> New Transaction option is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_print, 10), "Validated Approve --> Print option is displayed");
                m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_edit), "Validated Approve --> Edit option is removed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.button_close), "Validated Approve --> Close option is displayed");


                int preWindowsCount = driver.getWindowHandles().size();
                String initialWindowHandle = driver.getWindowHandle();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_print), "Clicked Print button");
                Cls_Generic_Methods.customWait(8);
                int postWindowsCount = driver.getWindowHandles().size();

                m_assert.assertTrue(postWindowsCount > preWindowsCount, "Validated Print --> Work Order Print page opened");

                for (String currentWindowHandle : driver.getWindowHandles()) {
                    if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                        driver.switchTo().window(currentWindowHandle);
                    }
                }
                driver.close();
                driver.switchTo().window(initialWindowHandle);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            } else {
                m_assert.assertFatal("Unable to select work order");
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate approve Work Order " + e);
        }
    }

    @Test(description = "Validating Cancel Work Order")
    public void validateCancelWorkOrder() {
        oPage_WorkOrder = new Page_WorkOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);
            boolean bSelectWorkOrder = selectWorkOrder("pending", workOrderNo);
            if (!bSelectWorkOrder) {
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                Cls_Generic_Methods.customWait();
                createWorkOrderWithMandatoryFields(false);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectStoreOnApp(store);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);
                bSelectWorkOrder = selectWorkOrder("pending", workOrderNo);
            }

            if (bSelectWorkOrder) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_cancel, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_WorkOrder.button_cancel), "Clicked Cancel Work Order--> Work Order No=" + workOrderNo);
                String cancellationReason = "AUTO TESTING CANCELLATION";
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.input_cancellationReason, 10);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_cancellationReason, cancellationReason);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_dontCancel), "Clicked Don't Cancel button");
                Cls_Generic_Methods.clickElement(driver, oPage_WorkOrder.button_cancel);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.input_cancellationReason, 10);
                Cls_Generic_Methods.clearValuesInElement(oPage_WorkOrder.input_cancellationReason);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_cancellationReason, cancellationReason), "Entered <b>" + cancellationReason + "</b> in cancellation reason");
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_confirmCancel), "Clicked <b>Cancel Work Order</b> button");
                cancelledAt = getCurrentDateTime();

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");

                boolean rowSelected = selectWorkOrder("cancelled", workOrderNo);
                Cls_Generic_Methods.customWait(4);
                if (rowSelected) {
                    String rhsStatus = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderStatus);
                    m_assert.assertTrue(rhsStatus.equalsIgnoreCase("cancelled"), "Validated Cancel --> Status = <b>" + rhsStatus + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderCancelledByUser).equals(expectedLoggedInUser), "Validated RHS -->Cancelled By User  = <b>" + expectedLoggedInUser + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderCancelledAt).contains(cancelledAt.split(":")[0]), "Validated RHS -->Cancelled At = <b>" + cancelledAt + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderCancelledReason).equalsIgnoreCase(cancellationReason), "Validated RHS -->Cancelled Reason = <b>" + cancellationReason + "</b>");
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                } else {
                    m_assert.assertFatal("Unable To validate RHS --> WO not Selected");
                }

            } else {
                m_assert.assertFatal("Unable to select work order");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Desc")
    public void validateSearchFunctionalityInWorkOrder(){

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        InventorySearchTest oInventorySearchTest = new InventorySearchTest();
        boolean bRequisitionOrderFound = false;
        String workOrderSearchTypeList[] = {"Work Order No.","Item Description"};

        try{
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try{

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(workOrderSearchTypeList[0]),
                        " By Default Search Type Selection Dropdown Displayed correctly as : "+workOrderSearchTypeList[0]);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox),
                        " Search Button Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"placeholder")
                                .equalsIgnoreCase("Search By "+workOrderSearchTypeList[0]),
                        " Input Search Box Place holder for selected search type Displayed correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                for (WebElement row : oPage_WorkOrder.list_woCreatedList) {
                    if (Cls_Generic_Methods.isElementDisplayed(row)) {
                        String workOrderNoInRow = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_workOrderNoList.get(1));
                        if (!workOrderNo.equalsIgnoreCase(workOrderNoInRow)) {
                            Cls_Generic_Methods.clickElement(row);
                            Cls_Generic_Methods.customWait(5);
                            oldWorkOrderNo = workOrderNoInRow;
                            oldItem = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_rhsTableRow.get(0).findElement(By.xpath(".//td[2]")));
                             break;
                        }
                    }
                }

                String searchValueList[] = {workOrderNo,oldWorkOrderNo,sItem1Description,oldItem};
                for(int i =0;i<2;i++) {
                    boolean searchResultByNumber = CommonActions.selectOptionAndSearch(workOrderSearchTypeList[0], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByNumber) {
                        bRequisitionOrderFound = oInventorySearchTest.getSearchedRecord(oPage_WorkOrder.list_woCreatedList, searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound && oPage_WorkOrder.list_woCreatedList.size() == 1 ,
                                "Search By Work Order No Worked correctly as order found in the page for number: " + searchValueList[i]);
                    }
                }

                for(int i =2 ;i<4;i++) {
                    boolean searchResultByDescription = CommonActions.selectOptionAndSearch(workOrderSearchTypeList[1], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByDescription) {
                        bRequisitionOrderFound = oInventorySearchTest.getSearchedRecordAndValidate(oPage_Requisition.list_dateTimeOfRequisition, searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Item Description Worked correctly as Work order found in the Work Order page");
                    }
                }

                for(int i =0 ;i<2;i++) {

                    boolean searchResultByWrongNumber = selectOptionAndSearch(workOrderSearchTypeList[i],"incorrectReqNumber");
                    m_assert.assertFalse(searchResultByWrongNumber," WOrk Order Search With Incorrect Number Working Correct");
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
                        " Input Search Box is empty");


                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty");
                boolean selectOption = CommonActions.selectOption(workOrderSearchTypeList[0]);
                m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(workOrderSearchTypeList[0]),
                        " Search Type Selected  as : "+workOrderSearchTypeList[0]);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(workOrderSearchTypeList[index])){
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


    private void validateWorkOrderRHS_TableValue(String itemDescription, HashMap<String, String> itemDetails) {
        try {
            ArrayList<String> headerValue = new ArrayList<>();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.text_rhs_workOrderNo, 10);
            for (WebElement tableColumn : oPage_WorkOrder.list_rhsTableHeader) {
                headerValue.add(Cls_Generic_Methods.getTextInElement(tableColumn));
            }

            for (WebElement tableRow : oPage_WorkOrder.list_rhsTableRow) {
                String description = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Description")));
                String instruction = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Instruction")));
                String rate = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Rate")));
                String quantity = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Qty.")));
                String discount = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Discount")));
                String netAmount = Cls_Generic_Methods.getTextInElement(tableRow.findElements(By.xpath("./child::td")).get(headerValue.indexOf("Net Amount")));

                if (description.contains(itemDescription)) {
                    m_assert.assertTrue("Validate Work Order RHS Table --> Item Description : <b>" + description + "</b>");
                    m_assert.assertTrue(instruction.equals(sInstruction), "Validate Work Order RHS Table --> Instruction : <b>" + instruction + "</b>");
                    m_assert.assertTrue(Double.parseDouble(rate) == formatDecimalNumber(Double.parseDouble(itemDetails.get("RATE"))), "Validate Work Order RHS Table --> Rate : <b>" + rate + "</b>");
                    m_assert.assertTrue(Double.parseDouble(quantity) == formatDecimalNumber(Double.parseDouble(itemDetails.get("QUANTITY"))), "Validate Work Order RHS Table --> Quantity : <b>" + quantity + "</b>");
                    m_assert.assertTrue(Double.parseDouble(discount) == formatDecimalNumber(Double.parseDouble(itemDetails.get("DISCOUNT"))), "Validate Work Order RHS Table --> Discount : <b>" + discount + "</b>");
                    m_assert.assertTrue(Double.parseDouble(netAmount) == formatDecimalNumber(Double.parseDouble(itemDetails.get("NET AMOUNT"))), "Validate Work Order RHS Table --> Net Amount : <b>" + netAmount + "</b>");
                    break;
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable To validate Work Order RHS table -->" + e);
        }
    }

    private boolean createNewCategoryAndLinkItToStore() {

        oPage_CategoryMaster = new Page_CategoryMaster(driver);
        oPage_StoreSetUp = new Page_StoreSetUp(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_ItemMaster = new Page_ItemMaster(driver);
        oPage_VendorMaster = new Page_VendorMaster(driver);

        boolean bItemCreated = false;
        boolean bCategoryLinkedToVendor = false;
        boolean bCategoryLinkedToStore = false;
        sItemCategoryName = "TEST ASSET " + getRandomNumber();
        String categoryDescription = "AUTO-TEST ITEM";
        String categoryPrefix = getRandomString(6);
        String categoryType = "Asset";

        try {
            // Creating New Category in Category Master
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Category Master");

            // Adding New Category to Category Master
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_addCategory, 15);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_CategoryMaster.button_addCategory);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.header_titleAddCategory, 10);
            Cls_Generic_Methods.isElementDisplayed(oPage_CategoryMaster.text_labelCategoryInformation);

            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_categoryName, sItemCategoryName), "Category Name Entered as : <b>" + sItemCategoryName + "</b>");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_categoryDescription, categoryDescription), "Category Description Entered as : <b>" + categoryDescription + "</b>");
            Cls_Generic_Methods.sendKeysIntoElement(oPage_CategoryMaster.input_categoryPrefix, categoryPrefix);
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_CategoryMaster.select_categoryType, categoryType), "Category Type selected as : <b>" + categoryType + "</b>");
            Cls_Generic_Methods.clickElement(oPage_CategoryMaster.button_saveChanges);
            Cls_Generic_Methods.customWait(5);
            boolean createdCategory = !Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CategoryMaster.button_saveChanges, 1);

            if (createdCategory) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Item Master");
                sItem1Description = "AUTO_TEST_ITEM_1_" + getRandomNumber();
                sItem1Code = createNewItem(sItem1Description);
                Cls_Generic_Methods.customWait(5);
                sItem2Description = "AUTO_TEST_ITEM_2_" + getRandomNumber();
                sItem2Code = createNewItem(sItem2Description);

                Cls_Generic_Methods.customWait();
                if (!sItem1Code.isEmpty() && !sItem2Code.isEmpty()) {
                    bItemCreated = true;
                }

                if (bItemCreated) {
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.customWait();
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_vendorMasterTitle, 10);

                    int vendorNo = 0;
                    for (WebElement txtStoreName : oPage_VendorMaster.list_text_vendorName) {

                        String vendorNameInList = Cls_Generic_Methods.getTextInElement(txtStoreName);
                        if (vendorNameInList.equalsIgnoreCase(sVendorName)) {
                            Cls_Generic_Methods.clickElement(driver, oPage_VendorMaster.list_btn_linkCategory.get(vendorNo));
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_linkCategoryTemplateTitle, 10);
                            Cls_Generic_Methods.clickElement(oPage_VendorMaster.input_selectCategorySearchBox);
                            Cls_Generic_Methods.customWait();
                            Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_selectCategorySearchBox, sItemCategoryName);
                            for (WebElement categoryName : oPage_VendorMaster.list_selectCategoryValuesList) {
                                if (Cls_Generic_Methods.getTextInElement(categoryName).contains(sItemCategoryName)) {
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(categoryName),
                                            "Selected Category as : <b>" + sItemCategoryName + "</b> in Vendor Master");
                                    break;
                                }
                            }
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_saveLinkCategoryInVendorMaster, 1);
                            Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_saveLinkCategoryInVendorMaster);
                            Cls_Generic_Methods.customWait(5);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.list_btn_vendorLocation.get(vendorNo), 15);
                            Cls_Generic_Methods.clickElement(oPage_VendorMaster.list_btn_vendorLocation.get(vendorNo));
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

                                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.input_inventoryVendorCreditDays, 10);
                                    vendorCreditDays = Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_inventoryVendorCreditDays, "value");
                                    vendorGstNo = Cls_Generic_Methods.getElementAttribute(oPage_VendorMaster.input_gstNo, "value");
                                    m_assert.assertInfo("Vendor Credit days present in " + sVendorName + " is <b>" + vendorCreditDays + "</b>");
                                    m_assert.assertInfo("Vendor GST No present in " + sVendorName + " is <b>" + vendorGstNo + "</b>");

                                    bCategoryLinkedToVendor = true;
                                }
                                headerColumn++;

                            }


                        }
                        vendorNo++;
                    }
                } else {
                    m_assert.assertFatal("New Item not created ");
                }
                if (bCategoryLinkedToVendor) {
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.scrollToTop();
                    Cls_Generic_Methods.customWait();
                    CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);

                    int storeNo = 0;
                    for (WebElement txtStoreName : oPage_StoreSetUp.list_text_storeName) {
                        String storeName = Cls_Generic_Methods.getTextInElement(txtStoreName);
                        if (storeName.contains(store.split("-")[0])) {
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.list_linkButton.get(storeNo));
                            Cls_Generic_Methods.customWait();
                            Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.list_btn_linkCategory.get(storeNo));
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, 2);
                            Cls_Generic_Methods.clickElement(oPage_StoreSetUp.input_selectStoreInLinkExistingStore);

                            for (WebElement category : oPage_StoreSetUp.list_categoriesInDropdown) {
                                if (Cls_Generic_Methods.getTextInElement(category).equalsIgnoreCase(sItemCategoryName)) {
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(category), "Linked Category <b>" + sItemCategoryName + "</b> to the " + storeName);
                                    Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_save);
                                    bCategoryLinkedToStore = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 10);
                                    break;
                                }
                            }
                            break;
                        }
                        storeNo++;
                    }
                } else {
                    m_assert.assertFatal("Category not linked to vendor ");
                }
            } else {
                m_assert.assertFatal("New Category not created ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return bCategoryLinkedToStore;
    }

    public static boolean setTime(WebElement timeElement, String time) {

        boolean flag = false;
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        try {
            Cls_Generic_Methods.clickElement(driver, timeElement);
            String[] seperatedTimeValue = time.split(":");
            Cls_Generic_Methods.clearValuesInElement(oPage_NewPatientRegisteration.input_appointmentHourForAppointDetails);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_appointmentHourForAppointDetails, seperatedTimeValue[0]);
            Cls_Generic_Methods.clearValuesInElement(oPage_NewPatientRegisteration.input_appointmentMinuteForAppointDetails);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_appointmentMinuteForAppointDetails, seperatedTimeValue[1]);

            Cls_Generic_Methods.clearValuesInElement(oPage_NewPatientRegisteration.input_appointmentMeridianForAppointDetails);

            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_appointmentMeridianForAppointDetails, seperatedTimeValue[2]);
            flag = true;

        } catch (Exception e) {
            m_assert.assertFatal("Unable to set Time");
            e.printStackTrace();
        }
        return flag;

    }

    private String getRandomNumber() {
        Random random = new Random();
        String id = String.format("%04d", random.nextInt(10000));
        return id;
    }

    public boolean selectByOptions(WebElement selectElement, String optionToSelect) {
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

    public boolean selectItemInCreateWorkOrder(String itemDescription) {
        boolean flag = false;
        try {
            int rowNo = 0;
            for (WebElement row : oPage_WorkOrder.list_row_createWorkOrder) {
                String itemDescriptionInRow = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_column_itemDescription_createWorkOrder.get(rowNo));
                if (itemDescriptionInRow.equals(itemDescription)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(row), "Clicked Item " + itemDescription);
                    flag = true;
                    break;
                }
                rowNo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find Item : " + itemDescription);
        }
        return flag;
    }

    public String createNewItem(String itemDescription) {
        String itemCodeValue = null;

        oPage_ItemMaster = new Page_ItemMaster(driver);
        boolean bItemCategorySelected = false;
        String hsnCode = "HSN_CODE_" + getRandomNumber();
        String itemPropertiesTax = "GST5 - 5.0%";
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_addItem, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_ItemMaster.button_addItem);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_addItemMasterTemplateTitle, 10);
            Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, sItemCategoryName);
            Cls_Generic_Methods.customWait();

            for (WebElement itemCategory : oPage_ItemMaster.list_inventoryItemCategoryList) {
                if (Cls_Generic_Methods.getTextInElement(itemCategory).contains(sItemCategoryName)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(itemCategory), "Category selected: <b> " + sItemCategoryName + "</b>");
                    bItemCategorySelected = true;
                    break;
                }
            }
            if (bItemCategorySelected) {
                Cls_Generic_Methods.customWait(3);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemDescription, itemDescription), "Item Description Entered as : <b>" + itemDescription + "</b>");
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemHsnCode, hsnCode);
                Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_itemPropertiesTaxList);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemPropertiesTaxList, itemPropertiesTax),
                        "Item Tax Entered as : <b>" + itemPropertiesTax + "</b>");

               /* Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_removePossibleVariant, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_ItemMaster.button_removePossibleVariant);*/
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_saveAddItemTemplate);
                do {
                    Cls_Generic_Methods.customWait(2);
                } while (Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.header_addItemMasterTemplateTitle));

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_inventoryItemSearch, 15);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_inventoryItemSearch, itemDescription);
                Cls_Generic_Methods.customWait(3);

                for (WebElement itemRow : oPage_ItemMaster.list_itemRowListInItemMaster) {
                    if (Cls_Generic_Methods.isElementDisplayed(itemRow)) {
                        List<WebElement> itemDetailsInRow = itemRow.findElements(By.xpath("./child::*"));
                        String itemDescriptionInRow = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get(1));
                        String itemCategory = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get(2));
                        String itemCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow.get(0));

                        if (itemDescriptionInRow.equalsIgnoreCase(itemDescription)
                                && itemCategory.equalsIgnoreCase(sItemCategoryName)) {
                            itemCodeValue = itemCode;
                            m_assert.assertInfo("Item code for " + itemDescription + " = <b>" + itemCode + "</b>");
                            break;
                        }
                    }
                }
            } else {
                m_assert.assertFatal("Item Category not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to add Item --->" + e);
        }

        return itemCodeValue;
    }

    public HashMap<String, String> fillItemTable(String itemDescription, String gstTaxType) {
        HashMap<String, String> itemDetails = new HashMap<>();
        try {
            boolean itemFound = false;
            String itemRate = getRandomDecimal(3);//;="200";
            String quantity = getRandomDecimal(2);//;="10";
            String discount = getRandomDecimal(2);//="50";


            int totalItemSelected = oPage_WorkOrder.list_tableBody_createWorkOrder.size();

            for (int i = 0; i < totalItemSelected; i++) {
                String itemDescriptionInRow = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_textTableItemDescription_createWorkOrder.get(i));
                if (itemDescriptionInRow.equals(itemDescription)) {
                    itemFound = true;
                    m_assert.assertTrue("Validated Item Description --> <b>" + itemDescription + "</b>");
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.list_inputTableInstructions_createWorkOrder.get(i), "TEST"), "Entered minimum length of characters in Instruction Text Box");
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_WorkOrder.list_inputTableInstructions_createWorkOrder.get(i).findElement(By.xpath("./following-sibling::label"))), "Validated Alert message is displayed ,if user enters minimum characters in Instruction");
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clearValuesInElement(oPage_WorkOrder.list_inputTableInstructions_createWorkOrder.get(i));
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.list_inputTableInstructions_createWorkOrder.get(i), sInstruction), "Entered <b>" + sInstruction + "</b> in Instructions");
                    Cls_Generic_Methods.clearValuesInElement(oPage_WorkOrder.list_inputTableRate_createWorkOrder.get(i));
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.list_inputTableRate_createWorkOrder.get(i), itemRate), "Entered <b>" + itemRate + "</b> in Item Rate");

                    //Validate Quantity
                    Cls_Generic_Methods.clickElement(oPage_WorkOrder.list_inputTableQuantity_createWorkOrder.get(i));
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.list_inputTableQuantity_createWorkOrder.get(i), "" + Keys.ARROW_UP + Keys.ARROW_UP), "Clicked Up arrow Twice in Item Quantity");
                    Cls_Generic_Methods.customWait();
                    String quantityPresent = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.list_inputTableQuantity_createWorkOrder.get(i), "value");
                    m_assert.assertTrue(quantityPresent.equals("2"), "Validated --> Quantity Increment Functionality");
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.list_inputTableQuantity_createWorkOrder.get(i), "" + Keys.ARROW_DOWN), "Clicked Down arrow once in Item Quantity");
                    Cls_Generic_Methods.customWait();
                    quantityPresent = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.list_inputTableQuantity_createWorkOrder.get(i), "value");
                    m_assert.assertTrue(quantityPresent.equals("1"), "Validated --> Quantity Decrement Functionality");

                    Cls_Generic_Methods.clearValuesInElement(oPage_WorkOrder.list_inputTableQuantity_createWorkOrder.get(i));
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.list_inputTableQuantity_createWorkOrder.get(i), quantity), "Entered <b>" + quantity + "</b> in Item Quantity");

                    //Validate Gross Amount
                    String actualGrossAmount = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_finalGrossAmount_createWorkOrder, "value");
                    double expectedGrossAmount = Double.parseDouble(itemRate) * Double.parseDouble(quantity);
                    grossAmount = grossAmount + expectedGrossAmount;
                    m_assert.assertTrue(Double.parseDouble(actualGrossAmount) == formatDecimalNumber(grossAmount), "Validated --> Gross Amount = <b>" + actualGrossAmount + "</b>");

                    //Discount
                    Cls_Generic_Methods.clearValuesInElement(oPage_WorkOrder.list_inputTableDiscount_createWorkOrder.get(i));
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.list_inputTableDiscount_createWorkOrder.get(i), discount), "Entered <b>" + discount + "</b> in Discount Value");
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(oPage_WorkOrder.list_selectTableDiscountType_createWorkOrder.get(i), i),
                            "Selected <b>" + Cls_Generic_Methods.getSelectedValue(oPage_WorkOrder.list_selectTableDiscountType_createWorkOrder.get(i)) + "</b> in Discount Type");
                    Cls_Generic_Methods.customWait();
                    String discountValue = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_textTableDiscountValue_createWorkOrder.get(i)).replaceAll("-", "").replaceAll(" ", "");
                    double expectedDiscount;
                    if (i == 0) {
                        expectedDiscount = Double.parseDouble(discount);
                    } else {
                        expectedDiscount = formatDecimalNumber(expectedGrossAmount * (Double.parseDouble(discount) / 100));
                    }

                    m_assert.assertTrue(Double.parseDouble(discountValue) == formatDecimalNumber(expectedDiscount), "Validated Discount Label --> Discount = <b>" + discountValue + "</b>");

                    //Total Discount
                    String actualTotalDiscount = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_finalTotalDiscount_createWorkOrder, "value");
                    double expectedTotalDiscount = totalDiscount + expectedDiscount;
                    totalDiscount = expectedTotalDiscount;
                    m_assert.assertTrue(Double.parseDouble(actualTotalDiscount) == formatDecimalNumber(expectedTotalDiscount), "Validated --> Total Discount = <b>" + actualTotalDiscount + "</b>");

                    //GST TAX
                    String actualGST;
                    m_assert.assertInfo(selectByOptions(oPage_WorkOrder.list_selectTableTax_createWorkOrder.get(i), gstTaxType), "Selected <b>" + gstTaxType + "</b> in Item Tax type");
                    String selectedTax = Cls_Generic_Methods.getSelectedValue(oPage_WorkOrder.list_selectTableTax_createWorkOrder.get(i)).split("-")[1].replaceAll("%", "").trim();
                    double expectedTax = (expectedGrossAmount - expectedDiscount) * (Double.parseDouble(selectedTax) / 100);
                    Cls_Generic_Methods.customWait();
                    if (gstTaxType.equals("GST5")) {
                        actualGST = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_finalGST5_createWorkOrder, "value");
                        totalTaxGST5 = totalTaxGST5 + expectedTax;
                    } else {
                        actualGST = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_finalGST12_createWorkOrder, "value");
                        totalTaxGST12 = totalTaxGST12 + expectedTax;
                    }
                    m_assert.assertTrue(Double.parseDouble(actualGST) == formatDecimalNumber(expectedTax), "Validated --> GST " + selectedTax + " Tax Amount = <b>" + actualGST + "</b>");

                    //Item Net Amount
                    String actualNetAmount = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_textTableNetAmount_createWorkOrder.get(i));
                    double expectedNetAmount = expectedGrossAmount - expectedDiscount + expectedTax;
                    itemTotalNetAmount = itemTotalNetAmount + expectedNetAmount;
                    m_assert.assertTrue(Double.parseDouble(actualNetAmount) == formatDecimalNumber(expectedNetAmount), "Validated --> Item Net Amount = <b>" + actualNetAmount + "</b>");

                    System.out.println("---------------------------BREAK UP----------------------------------");
                    System.out.println("DESCRIPTION -->" + itemDescription);
                    System.out.println("RATE --------->" + itemRate);
                    System.out.println("QUANTITY ----->" + quantity);
                    System.out.println("TAX ---------->" + expectedTax);
                    System.out.println("DISCOUNT ----->" + expectedDiscount);
                    System.out.println("NET AMOUNT --->" + expectedNetAmount);
                    System.out.println("GROSS AMOUNT ++ ------->" + expectedGrossAmount);
                    System.out.println("TOTAL DISCOUNT ++ ----->" + expectedTotalDiscount);
                    System.out.println("TOTAL TAX AMOUNT 5.0 ++ ----->" + totalTaxGST5);
                    System.out.println("TOTAL TAX AMOUNT 12.0 ++ ----->" + totalTaxGST12);
                    System.out.println("TOTAL NET AMOUNT ++ ----->" + itemTotalNetAmount);

                    itemDetails.put("RATE", itemRate);
                    itemDetails.put("QUANTITY", quantity);
                    itemDetails.put("TAX", String.valueOf(expectedTax));
                    itemDetails.put("DISCOUNT", String.valueOf(expectedDiscount));
                    itemDetails.put("NET AMOUNT", String.valueOf(formatDecimalNumber(expectedNetAmount)));
                    break;
                }
            }


            if (!itemFound) {
                m_assert.assertFatal("Unable to find item : " + itemDescription);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return itemDetails;
    }

    public void validateFillItemTable(String itemDescription, HashMap<String, String> itemDetails) {
        try {
            boolean itemFound = false;

            int totalItemSelected = oPage_WorkOrder.list_tableBody_createWorkOrder.size();
            for (int i = 0; i < totalItemSelected; i++) {
                String itemDescriptionInRow = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_textTableItemDescription_createWorkOrder.get(i));
                if (itemDescriptionInRow.equals(itemDescription)) {
                    itemFound = true;
                    m_assert.assertTrue("Validated Edit --> Item Description --> <b>" + itemDescription + "</b>");

                    m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.list_inputTableInstructions_createWorkOrder.get(i), "value").equals(sInstruction), "Validated Edit --> Entered Instructions : <b>" + sInstruction + "</b>");
                    m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.list_inputTableRate_createWorkOrder.get(i), "value").equals(itemDetails.get("RATE")), "Validated Edit --> Entered Item Rate : <b>" + itemDetails.get("RATE") + "</b>");

                    m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.list_inputTableQuantity_createWorkOrder.get(i), "value").equals(itemDetails.get("QUANTITY")), "Validated Edit --> Entered Item Quantity : <b>" + itemDetails.get("QUANTITY") + "</b>");

                    String discountLabel = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_textTableDiscountValue_createWorkOrder.get(i)).replaceAll("-", "").replaceAll(" ", "");
                    m_assert.assertTrue(Double.parseDouble(discountLabel) == formatDecimalNumber(Double.parseDouble(itemDetails.get("DISCOUNT"))), "Validated Discount Label --> Discount = <b>" + discountLabel + "</b>");

                    //Item Net Amount
                    String actualNetAmount = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_textTableNetAmount_createWorkOrder.get(i));
                    m_assert.assertTrue(Double.parseDouble(actualNetAmount) == formatDecimalNumber(Double.parseDouble(itemDetails.get("NET AMOUNT"))), "Validated --> Item Net Amount = <b>" + actualNetAmount + "</b>");
                    break;
                }
            }
            if (!itemFound) {
                m_assert.assertFatal("Unable to find item : " + itemDescription);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCurrentDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mm a");
        Date date = new Date();
        //  07-06-2023 | 01:09 PM
        String date1 = dateFormat.format(date);
        return date1;
    }

    public String getRandomDecimal(int numDigits) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        Random random = new Random();
        double randomValue = random.nextDouble();
        double scaledValue = randomValue * Math.pow(10, numDigits);
        String formattedValue = decimalFormat.format(scaledValue);
        return formattedValue;
    }

    public double formatDecimalNumber(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(java.math.RoundingMode.HALF_UP);
        return Double.parseDouble(decimalFormat.format(number));
    }

    public double formatAndRemoveDecimalNumber(double inputNumber) {
        BigDecimal bd = new BigDecimal(String.valueOf(inputNumber));
        BigDecimal no = bd.setScale(2, BigDecimal.ROUND_DOWN);
        return no.doubleValue();
    }

    private String getTomorrowDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String tomorrowDate = dateFormat.format(calendar.getTime());

        return tomorrowDate;
    }

    public boolean selectWorkOrder(String status, String... workOrderNo) {
        boolean flag = false;
        oPage_WorkOrder = new Page_WorkOrder(driver);
        String selectWORow = null;

        try {
            if (workOrderNo.length > 0) {
                selectWORow = workOrderNo[0];
            }
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_WorkOrder.list_woHeaderList, 10);
            List<String> workOrderHeaderList = new ArrayList<String>();

            for (WebElement purchaseHeaderList : oPage_WorkOrder.list_woHeaderList) {
                workOrderHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }

            int rowNo = 0;
            for (WebElement row : oPage_WorkOrder.list_woCreatedList) {
                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> workOrderRow = row.findElements(By.xpath("./child::*"));
                    String woStatus = Cls_Generic_Methods.getTextInElement(workOrderRow.get(workOrderHeaderList.indexOf("Status")));
                    String workOrderNoInRow = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.list_workOrderNoList.get(rowNo));

                    if (workOrderNo.length == 0) {
                        if (woStatus.equalsIgnoreCase(status)) {
                            Cls_Generic_Methods.clickElement(row);
                            flag = true;
                            break;
                        }

                    } else {
                        if (workOrderNoInRow.equals(selectWORow) && woStatus.equalsIgnoreCase(status)) {
                            Cls_Generic_Methods.clickElement(row);
                            flag = true;
                            break;
                        }
                    }
                }
                rowNo++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find Work Order " + e);
        }

        return flag;

    }

    public void createWorkOrderWithMandatoryFields(boolean createNewCategory) {
        oPage_WorkOrder = new Page_WorkOrder(driver);
        oPage_CommonElements = new Page_CommonElements(driver);

        grossAmount = 0;
        totalDiscount = 0;
        totalTaxGST5 = 0;
        totalTaxGST12 = 0;
        itemTotalNetAmount = 0;
        expectedTotalOtherCharges = 0;

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            if (createNewCategory) {
                createNewCategoryAndLinkItToStore();
            }
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(store);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);

            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);
            Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_new);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.text_headerCreteWorkOrder, 10);
            m_assert.assertTrue(selectByOptions(oPage_WorkOrder.select_vendor, sVendorName), "Selected Vendor : <b>" + sVendorName + "</b>");
            Cls_Generic_Methods.customWait(5);

            selectItemInCreateWorkOrder(sItem1Description);
            Cls_Generic_Methods.customWait();
            selectItemInCreateWorkOrder(sItem2Description);
            Cls_Generic_Methods.customWait(5);
            //Order Notes And Order Type
            String orderNotes = "Test Notes";
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_orderNotesCreateWO, orderNotes), "Entered <i>" + orderNotes + "</i> in Order Notes");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_WorkOrder.select_orderTypeCreateWO, orderType), "Selected <i>" + orderType + "</i> in Order Type");


            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_saveChanges_createWorkOrder);
            Cls_Generic_Methods.customWait();
            item1Details = fillItemTable(sItem1Description, "GST5");
            item2Details = fillItemTable(sItem2Description, "GST12");


            m_assert.assertInfo(selectByOptions(oPage_WorkOrder.select_otherCharges_createWorkOrder.get(0), "Fitting Charges"), "Selected <b>Fitting Charges</b> on Other Charges");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_otherCharges_createWorkOrder.get(0), otherCharges), "Entered <b>" + otherCharges + "</b> on Other Charges");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_WorkOrder.input_otherChargesPercent_createWorkOrder, otherChargesPercent), "Entered <b>" + otherChargesPercent + "</b> on Other Charges Percentage");
            Cls_Generic_Methods.customWait();
            expectedTotalOtherCharges = Double.parseDouble(otherCharges) + ((Double.parseDouble(otherCharges) * Double.parseDouble(otherChargesPercent)) / 100);

            String actualTotalOtherCharges = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_finalOtherCharges_createWorkOrder, "value").trim();
            m_assert.assertInfo(Double.parseDouble(actualTotalOtherCharges) == expectedTotalOtherCharges, "Total Other Charges : <b>" + expectedTotalOtherCharges + "</b>");

            //Validate Net Amount
            String actualNetAmount = Cls_Generic_Methods.getElementAttribute(oPage_WorkOrder.text_finalNetAmount_createWorkOrder, "value").trim();
            m_assert.assertTrue(Double.parseDouble(actualNetAmount) == (formatDecimalNumber(itemTotalNetAmount) + expectedTotalOtherCharges), "Total Net Amount : <b>" + actualNetAmount + "</b>");
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_WorkOrder.button_saveChanges_createWorkOrder), "Clicked <b>Save Changes</b>");
            workOrderCreatedAt = getCurrentDateTime();

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 15);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Work Order");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.button_new, 10);

            selectWorkOrder("pending");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_WorkOrder.text_rhs_workOrderNo, 10);
            workOrderNo = Cls_Generic_Methods.getTextInElement(oPage_WorkOrder.text_rhs_workOrderNo);
            m_assert.assertTrue("Work Order Created successfully --> Work Order No : <b>" + workOrderNo + "</b>");
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFalse("Unable to validate Create Work Order --> " + e);
        }
    }

}


