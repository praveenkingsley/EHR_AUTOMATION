package tests.inventoryStores.pharmacyStore.Order;


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
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_StoreSetUp;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_TermsAndConditions;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorMaster;
import pages.store.PharmacyStore.Items.Page_Lot;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Order.Page_Indent;
import pages.store.PharmacyStore.Order.Page_PurchaseOrder;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Order.Page_RequisitionReceived;
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import pages.store.PharmacyStore.Transaction.*;
import tests.inventoryStores.pharmacyStore.TaxInvoiceTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RequisitionTest extends TestBase {

    String sStore = "Pharmacy Automation 2- Pharmacy";
    String sReceivingStore = "OpticalStore- Optical";
    String vendorName = "Supplier ABC";
    String sRequisitionId = "";
    String sIssueTransactionId = "";
    String sIndentOrderId = "";
    String sPurchaseOrderId = "";
    String sPurchaseGrnId = "";
    boolean enabledTaxInvoice;
    String currentIssueStatus = "open";
    String getRequisitionStatus = "";
    String getRequisitionReceivedStatus = "";
    String getRequisitionCreatedAt = "";
    String getReceivingStore = "";
    String getRequestedStore = "";
    String getRequisitionType = "";
    String sRequisitionCreatedAt = "";
    String sItem1Description = "Xiidra";
    String sItem2Description = "Warfarin";
    String sOrderNote = "AUTO TEST";
    String sRequisitionType = "Urgent";
    String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
    Page_CommonElements oPage_CommonElements;
    Page_Master oPage_Master;
    Page_ItemMaster oPage_ItemMaster;
    Page_Lot oPage_Lot;
    Page_Requisition oPage_Requisition;
    Page_RequisitionReceived oPage_RequisitionReceived;
    Page_Indent oPage_Indent;
    Page_PurchaseOrder oPage_PurchaseOrder;
    Page_StoreSetUp oPage_StoreSetUp;
    Page_Navbar oPage_Navbar;
    Page_Purchase oPage_Purchase;
    Page_Transfer oPage_Transfer;
    double dTotalAmount = 0;

    String receivedAt;
    HashMap<String, String> itemDetails1;
    HashMap<String, String> itemDetails2;

    //PO
    String storeDefaultShippingAddress = "";
    String storeDefaultBillingAddress = "";
    String storeGSTno = "";
    String vendor_address = "";
    String vendorGSTno = "";
    String vendorCreditDays = "";
    String storeEntityGroup = "";
    String vendorPOExpiry = "";

    @Test(description = "Validate Linked Requisition Stores")
    public void validateLinkedRequisitionStores() {
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Requisition = new Page_Requisition(driver);
        oPage_StoreSetUp = new Page_StoreSetUp(driver);
        oPage_Navbar = new Page_Navbar(driver);
        ArrayList<String> linkedStoresList = new ArrayList<>();
        String linkStoreName = "Optical Store 1";

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);

            int storeNo = 0;
            for (WebElement txtStoreName : oPage_StoreSetUp.list_text_storeName) {
                String storeName = Cls_Generic_Methods.getTextInElement(txtStoreName);
                if (storeName.contains(sStore.split("-")[0])) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.list_linkButton.get(storeNo)), "Clicked Link Button in Store Setup");
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.list_btn_linkRequisitionStores.get(storeNo));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, 2);
                    selectByOptions(oPage_StoreSetUp.dropdown_storeTypeFilter, "All");

                    Cls_Generic_Methods.customWait();
                    for (WebElement category : oPage_StoreSetUp.list_currentlyLinkedStores) {
                        linkedStoresList.add(Cls_Generic_Methods.getTextInElement(category));
                    }

                    m_assert.assertInfo(selectByOptions(oPage_StoreSetUp.select_storeLink, linkStoreName), "Selected Store as <b>" + linkStoreName + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_saveLinkStore, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_saveLinkStore), "Clicked on save for linking store");
                    linkedStoresList.add(linkStoreName);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);

                    break;
                }
                storeNo++;
            }

            openRequisitionPage();
            Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.input_orderNote, 10);

            List<WebElement> options = oPage_Requisition.select_receivingStore.findElements(By.xpath("./option"));
            int optionsSize = options.size();

            for (WebElement option : options) {
                String optionValue = Cls_Generic_Methods.getTextInElement(option);
                if (optionValue.isEmpty()) {
                    optionsSize--;
                }
            }

            if (optionsSize == (linkedStoresList.size()+1)) {
                for (WebElement option : options) {
                    int index = options.indexOf(option);
                    String optionValue = Cls_Generic_Methods.getTextInElement(option);
                    boolean status = false;

                    if (optionValue.isEmpty() || index == 1) {
                        continue;
                    }

                    for (String linkedStore : linkedStoresList) {
                        if (linkedStore.equalsIgnoreCase(optionValue)) {
                            status = true;
                            break;
                        }
                    }

                    m_assert.assertTrue(status, "Linked Store<b>(" + optionValue + ")</b> is present in Requisition Receiving store options");
                }
            } else {
                m_assert.assertFatal("Unable To validate linked requisition stores");
            }
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            //Unlink Store and check
            Cls_Generic_Methods.driverRefresh();
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);

            storeNo = 0;
            for (WebElement txtStoreName : oPage_StoreSetUp.list_text_storeName) {
                String storeName = Cls_Generic_Methods.getTextInElement(txtStoreName);
                if (storeName.contains(sStore.split("-")[0])) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.list_unlinkButton.get(storeNo)), "Clicked Un-Link Button in Store Setup");
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.list_btn_unlinkRequisitionStores.get(storeNo));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.input_selectStoreInLinkExistingStore, 2);
                    selectByOptions(oPage_StoreSetUp.dropdown_storeTypeFilter, "All");

                    Cls_Generic_Methods.customWait();
                    m_assert.assertInfo(selectByOptions(oPage_StoreSetUp.select_storeLink, linkStoreName), "Selected Store as <b>" + linkStoreName + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_saveLinkStore, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.button_saveLinkStore), "Clicked on save for Un-Linking store");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_selectedFacilityName, 2);
                    linkedStoresList.remove(linkStoreName);
                    break;
                }
                storeNo++;
            }

            openRequisitionPage();
            Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.input_orderNote, 10);

            options = oPage_Requisition.select_receivingStore.findElements(By.xpath("./option"));
            optionsSize = options.size();

            for (WebElement option : options) {
                String optionValue = Cls_Generic_Methods.getTextInElement(option);
                if (optionValue.isEmpty()) {
                    optionsSize--;
                }
            }

            boolean status = true;
            if (optionsSize == (linkedStoresList.size()+1)) {
                for (WebElement option : options) {
                    String optionValue = Cls_Generic_Methods.getTextInElement(option);
                    if (linkStoreName.equalsIgnoreCase(optionValue)) {
                        status = false;
                        break;
                    }
                }
            } else {
                status = false;
                m_assert.assertFatal("Unable To validate unlinked requisition stores");
            }

            m_assert.assertTrue(status, "Un-Linked Store<b>(" + linkStoreName + ")</b> is not present in Requisition Receiving store options");

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(description = "Validate Create Requisition")
    public void validateCreateRequisition() {
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Requisition = new Page_Requisition(driver);

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            itemDetails1 = getItemDetails(sItem1Description);
            itemDetails2 = getItemDetails(sItem2Description);
            openRequisitionPage();

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition), "Clicked <b>New</b> Requisition button");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.select_receivingStore, 10), "Create Requisition Page Opened");

            String orderDate = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate, "value");
            String orderTime = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "value");
            sRequisitionCreatedAt = orderDate + "|" + CommonActions.getRequiredFormattedDateTime("h:mm a", "hh:mm a", orderTime);
            m_assert.assertTrue(compareDateTimesWithTolerance(getCurrentDateTime(), sRequisitionCreatedAt, 1), "Validated default Order Date <b>(" + orderDate + ")</b> and Time <b>(" + orderTime + ")</b>");

            //Header Elements
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_orderNote, sOrderNote), "Entered " + sOrderNote + " in Order Note");
            m_assert.assertInfo(selectByOptions(oPage_Requisition.select_receivingStore, sReceivingStore.split("-")[0]), "Selected <b>" + sReceivingStore.split("-")[0] + "</b> in Receiving Store");
            m_assert.assertInfo(selectByOptions(oPage_Requisition.select_reqType, sRequisitionType), "Selected <b>" + sRequisitionType + "</b> in Receiving Type");

            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_searchMedicineName, sItem1Description), "Entered " + sItem1Description + " in Item Search");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_Requisition.input_searchMedicineName);
            oPage_Requisition.input_searchMedicineName.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(5);

            for (WebElement row : oPage_Requisition.list_namesOfMedicinesOnLeftInSearchResultPO) {
                String itemValue = Cls_Generic_Methods.getTextInElement(row);

                if (itemValue.equalsIgnoreCase(sItem1Description)) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(row), "Item found : <b>" + sItem1Description + "</b> and selected");
                    break;
                }
            }

            //Validate Remove Functionality
            Cls_Generic_Methods.customWait(5);
            m_assert.assertTrue(oPage_Requisition.button_removeItemFromList.size() == 1, "Validated Requisition -> Item Selected ");
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Requisition.button_removeItemFromList.get(0)), "Clicked <b>Remove</b> Item from list Button");
            Cls_Generic_Methods.customWait();
            m_assert.assertFalse(Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Requisition.button_removeItemFromList, 2), "Validated Remove Selected Item Functionality");

            selectItemFromLeftPanelCreateRequisition(itemDetails1);
            selectItemFromLeftPanelCreateRequisition(itemDetails2);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Requisition.button_removeItemFromList, 10);

            validateAndFillItemDetails(itemDetails1);
            Cls_Generic_Methods.customWait();
            validateAndFillItemDetails(itemDetails2);

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Requisition.button_saveRequisition), "Clicked <b>Save</b> Requisition");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 10);
            openRequisitionPage();
            selectRequisitionOrder();

            m_assert.assertTrue(compareDateTimesWithTolerance(getRequisitionCreatedAt, sRequisitionCreatedAt, 0), "Validated Requisition Created List -> Order Created At " + getRequisitionCreatedAt);
          //  m_assert.assertTrue(getReceivingStore.replaceAll(" ", "").equalsIgnoreCase(sReceivingStore.replaceAll(" ", "")), "Validated Requisition Created List -> Order Receiving Store : <b>" + getReceivingStore + "</b>");
            m_assert.assertTrue(getRequisitionType.contains(sRequisitionType), "Validated Requisition Created List -> Requisition Type :<b>" + sRequisitionType + "</b>");
            m_assert.assertTrue(getRequisitionStatus.contains("Open"), "Validated Requisition Created List -> Requisition Status :<b>Open</b>");

             String headerList[] = {"Req. Info","Type | Status | Through","Fulfillment"};
             for(WebElement header : oPage_Requisition.list_headerRequisitionList){
                 int index = oPage_Requisition.list_headerRequisitionList.indexOf(header);
                 String headerText = Cls_Generic_Methods.getTextInElement(header);
                 if(headerText.equalsIgnoreCase(headerList[index])){
                     m_assert.assertTrue(headerText+" header Present in the table");
                 }else {
                     m_assert.assertWarn("Either Header Changed or not present");
                 }
             }

            for(WebElement header : oPage_Requisition.list_dateTimeOfRequisition){
                String headerText = Cls_Generic_Methods.getTextInElement(header);
                if(headerText.contains(sReceivingStore)){
                    m_assert.assertTrue(headerText+" Store Present in the table");
                }else {
                    m_assert.assertWarn("Store Not Present");
                }
            }

            for(WebElement header : oPage_Requisition.list_requisitionDataList){
                String typeAndStatus = Cls_Generic_Methods.getTextInElement(header.findElement(By.xpath("./td[2]")));
                String fulfillmentText = Cls_Generic_Methods.getTextInElement(header.findElement(By.xpath("./td[3]")));

                if(typeAndStatus.contains("Open | Urgent | Manual") && fulfillmentText.contains("None")){
                    m_assert.assertTrue(" Status and Fullfillment data displayed in the table as "+typeAndStatus + " "+fulfillmentText);
                }else {
                    m_assert.assertWarn("Status or Fullfilment Status  Not Present correctl");
                }
            }


            String fullfilmentStatus = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_fullfilmentStatus);
            m_assert.assertTrue(fullfilmentStatus.contains("None"),"Fullfilment Status DIsplayed as None correctly");
            validateRequisitionRHS();
            validateTimePeriod();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(description = "Validate Edit Requisition")
    public void validateEditRequisition() {
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Requisition = new Page_Requisition(driver);

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            String newItem = "Restasis";
            HashMap<String, String> newItemDetails = getItemDetails(newItem);

            openRequisitionPage();
            selectRequisitionOrder(sRequisitionId);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition), "Clicked <b>View Order</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_editRequisition, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_editRequisition), "Clicked <b>Edit</b> Requisition");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.input_orderNote, 9), "Validated Edit Requisition Page is displayed");

            String orderDate = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate, "value");
            String orderTime = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "value");

            sRequisitionCreatedAt = orderDate + "|" + CommonActions.getRequiredFormattedDateTime("h:mm a", "hh:mm a", orderTime);

            m_assert.assertTrue(getRequisitionCreatedAt.contains(CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy","dd-MM-yyyy",orderDate)) && getRequisitionCreatedAt.contains(orderTime.split(" ")[0]),"Validated Edit Order Date <b>(" + orderDate + ")</b> and Time <b>(" + orderTime + ")</b>");

            //validate previously Filled Details
            String orderNoteEdit = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_orderNote, "value");
            String receivingStoreEdit = Cls_Generic_Methods.getSelectedValue(oPage_Requisition.select_receivingStore);
            String requisitionTypeEdit = Cls_Generic_Methods.getSelectedValue(oPage_Requisition.select_reqType);

            m_assert.assertTrue(orderNoteEdit.equalsIgnoreCase(sOrderNote), "Validated Edit -> Order Note : <b>" + orderNoteEdit + "</b>");
            m_assert.assertTrue(receivingStoreEdit.equalsIgnoreCase(sReceivingStore.split("-")[0]), "Validated Edit -> Receiving Store : <b>" + receivingStoreEdit + "</b>");
            m_assert.assertTrue(requisitionTypeEdit.equalsIgnoreCase(sRequisitionType), "Validated Edit -> Requisition Type : <b>" + requisitionTypeEdit + "</b>");

            validateFilledItemDetails(itemDetails1);
            validateFilledItemDetails(itemDetails2);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Requisition.button_removeItemFromList, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Requisition.button_removeItemFromList.get(1)), "Clicked <b>Remove</b> Item from list Button");
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(oPage_Requisition.button_removeItemFromList.size() == 1, "Validated Edit -> Item (" + sItem2Description + ") Removed");

            itemDetails2 = newItemDetails;

            selectItemFromLeftPanelCreateRequisition(itemDetails2);
            Cls_Generic_Methods.customWait();

            validateAndFillItemDetails(itemDetails2);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Requisition.button_updateRequisition), "Clicked <b>Update Changes</b> ");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 10);
            validateRequisitionRHS();

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(description = "Validate Requisition Approve")
    public void validateApproveRequisition() {
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Requisition = new Page_Requisition(driver);

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            openRequisitionPage();
            selectRequisitionOrder(sRequisitionId);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition), "Clicked <b>View Order</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_editRequisition, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_approveRequisition), "Clicked <b>Approve</b> Requisition");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_confirmRequisition, 10), "Validated -> Approve Confirmation pop up displayed");

            if (Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_confirmRequisition)) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_cancelApprove),
                        "In Approve Confirmation Pop-up window <b> Cancel </b>button is clicked ");
            }
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_approveRequisition, 10), "Validated Approve cancel functionality");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_approveRequisition), "Clicked <b>Approve</b> Requisition");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_confirmRequisition, 10);
            if (Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_confirmRequisition)) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_Requisition.button_confirmRequisition),
                        "In Approve Confirmation Pop-up window <b> Yes </b>button is clicked ");
            }

            selectRequisitionOrder(sRequisitionId);
            validateRequisitionRHS();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(description = "Validate Requisition Received")
    public void validateRequisitionReceivedDetails() {
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Requisition = new Page_Requisition(driver);
        oPage_RequisitionReceived = new Page_RequisitionReceived(driver);


        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            validateRequisitionReceivedRHS();

            Cls_Generic_Methods.customWait();
            validateTimePeriod();

            //Blocking all the available lot
            blockLot(sItem1Description);
            blockLot(sItem2Description);

            //Creating grn for one Item
            createPurchaseGrn(itemDetails1);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(description = "Validate Requisition Received Pending")
    public void validateRequisitionReceivedPendingTransaction() {
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Requisition = new Page_Requisition(driver);
        oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        String item1issueQuantity = "4";

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            openRequisitionReceivedPage();
            selectRequisitionReceivedOrder(sRequisitionId);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 10);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_newTransactionRequisition), "Clicked <b>New Transaction</b>");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.text_headerIssueItems, 15), "Issue Items page displayed");


            String receivingStore = Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_receivingStore, "value");
            m_assert.assertTrue(receivingStore.contains(sStore.split("-")[0]), "Validated Displayed Receiving Store : " + receivingStore);

            String orderDate = CommonActions.getRequiredFormattedDateTime("dd/mm/yyyy", "dd-mm-yyy", Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_transactionDate, "value"));
            m_assert.assertTrue(getCurrentDateTime().contains(orderDate), "Validated Displayed Transaction Date : " + orderDate);


            validateItemInRequisitionReceived(itemDetails1);
            validateLotRequisitionReceived(itemDetails1, item1issueQuantity);

            validateItemInRequisitionReceived(itemDetails2);
            validateLotRequisitionReceived(itemDetails2);

            //Create Page
            validateIssueItemTable(itemDetails1, item1issueQuantity);

            String actualTotalAmount = Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_totalAmtIncludingTax, "value");
            m_assert.assertTrue(CommonActions.convertStringToDouble(actualTotalAmount) == dTotalAmount, "Validated Issue Items -> Total Amount Inc Tax = <b>" + actualTotalAmount + "</b>");

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_saveTransfer), "Clicked <b>Save</b>");
            receivedAt = getCurrentDateTime();
            validateRequisitionReceivedRHS();
            Cls_Generic_Methods.customWait();
            validateIssueHistory();
            validateIssueHistoryTable(itemDetails1, item1issueQuantity);

            boolean newTransactionBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 3);
            m_assert.assertFalse(newTransactionBtn, "Validated Requisition Received -> <b>New Transaction</b> button is not displayed , if order has Open Transaction ");

            approveIssueTransaction();

            openRequisitionReceivedPage();
            selectRequisitionReceivedOrder(sRequisitionId);

            newTransactionBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 6);
            m_assert.assertTrue(newTransactionBtn, "Validated Requisition Received -> <b>New Transaction</b> button is displayed , if transaction is approved ");

            //To check the status
            validateIssueHistory();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            enabledTaxInvoice = setupTaxInvoiceDeliveryChallanSetting(false);

            if (enabledTaxInvoice) {
                //TAX INVOICE
                CommonActions.selectStoreOnApp(sReceivingStore);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.customWait();
                createTaxInvoice();
            }

            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            //Partially Receiving
            receiveItem(itemDetails1, item1issueQuantity, true);

            //Check the remaining Quantity after partially receiving
            validateRequisitionReceivedRHS();
            validateIssueHistory();

            //Restock balance
            reStockIssueTransaction();


            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(description = "Validate Requisition Received Closed Transaction")
    public void validateRequisitionReceivedClosedTransaction() {
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Requisition = new Page_Requisition(driver);
        oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        String item1issueQuantity = itemDetails1.get("REMAINING QUANTITY");
        String item2issueQuantity = itemDetails2.get("REMAINING QUANTITY");
        dTotalAmount = 0;

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectStoreOnApp(sReceivingStore);
            Cls_Generic_Methods.switchToOtherTab();

            //Creating grn for one Item
            createPurchaseGrn(itemDetails2);

            openRequisitionReceivedPage();
            selectRequisitionReceivedOrder(sRequisitionId);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 10);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_newTransactionRequisition), "Clicked <b>New Transaction</b>");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.text_headerIssueItems, 15), "Issue Items page displayed");


            String receivingStore = Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_receivingStore, "value");
            m_assert.assertTrue(receivingStore.contains(sStore.split("-")[0]), "Validated Displayed Receiving Store : " + receivingStore);

            String orderDate = CommonActions.getRequiredFormattedDateTime("dd/mm/yyyy", "dd-mm-yyy", Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_transactionDate, "value"));
            m_assert.assertTrue(getCurrentDateTime().contains(orderDate), "Validated Displayed Transaction Date : " + orderDate);


            validateItemInRequisitionReceived(itemDetails1);
            validateLotRequisitionReceived(itemDetails1, item1issueQuantity);

            validateItemInRequisitionReceived(itemDetails2);
            validateLotRequisitionReceived(itemDetails2, item2issueQuantity);

            //Create Page
            validateIssueItemTable(itemDetails1, item1issueQuantity);
            validateIssueItemTable(itemDetails2, item2issueQuantity);

            String actualTotalAmount = Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_totalAmtIncludingTax, "value");
            m_assert.assertTrue(CommonActions.convertStringToDouble(actualTotalAmount) == dTotalAmount, "Validated Issue Items -> Total Amount Inc Tax = <b>" + actualTotalAmount + "</b>");

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_saveTransfer), "Clicked <b>Save</b>");
            currentIssueStatus = "open";

            receivedAt = getCurrentDateTime();
            validateRequisitionReceivedRHS();

            validateIssueHistory();
            validateIssueHistoryTable(itemDetails1, item1issueQuantity);

            boolean newTransactionBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 3);
            m_assert.assertFalse(newTransactionBtn, "Validated Requisition Received -> <b>New Transaction</b> button is not displayed , if order has Open Transaction ");

            approveIssueTransaction();

            openRequisitionReceivedPage();
            selectRequisitionReceivedOrder(sRequisitionId);

            //To check the status
            validateIssueHistory();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            if (enabledTaxInvoice) {
                //TAX INVOICE
                CommonActions.selectStoreOnApp(sReceivingStore);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.customWait();
                createTaxInvoice();
            }

            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            //Partially Receiving
            receiveItem(itemDetails1, item1issueQuantity, false);
            receiveItem(itemDetails2, item2issueQuantity, false);


            validateRequisitionReceivedRHS();
            currentIssueStatus = "Closed";
            validateIssueHistory();

            newTransactionBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 6);
            m_assert.assertFalse(newTransactionBtn, "Validated Requisition Received -> <b>New Transaction</b> button is not displayed , if order is completed ");

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            openRequisitionPage();
            selectRequisitionOrder(sRequisitionId);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.text_rhs_requisitionStatus, 10);
            String status = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionStatus);

            m_assert.assertTrue(getRequisitionStatus.equalsIgnoreCase(currentIssueStatus), "Validated Requisition Status : <b>" + getRequisitionStatus + "</b>");
            m_assert.assertTrue(status.equalsIgnoreCase(currentIssueStatus), "Validated Requisition RHS status : <b>" + status + "</b>");

            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();


            HashMap<String, String> itemDetails1AfterRequisition = getItemDetails(itemDetails1.get("DESCRIPTION"));
            HashMap<String, String> itemDetails2AfterRequisition = getItemDetails(itemDetails2.get("DESCRIPTION"));

            double stockAfterRequisition = CommonActions.convertStringToDouble(itemDetails1AfterRequisition.get("STOCK"));
            double stockBeforeRequisition = CommonActions.convertStringToDouble(itemDetails1.get("STOCK"));
            m_assert.assertTrue(stockAfterRequisition == stockBeforeRequisition + (CommonActions.convertStringToDouble(itemDetails1.get("QUANTITY"))), "Validated Item (" + itemDetails1.get("DESCRIPTION") + ") Stock increased from <b>" + stockBeforeRequisition + "</b> to <b>" + stockAfterRequisition + "</b>");

            stockAfterRequisition = CommonActions.convertStringToDouble(itemDetails2AfterRequisition.get("STOCK"));
            stockBeforeRequisition = CommonActions.convertStringToDouble(itemDetails2.get("STOCK"));
            m_assert.assertTrue(stockAfterRequisition == stockBeforeRequisition + (CommonActions.convertStringToDouble(itemDetails2.get("QUANTITY"))), "Validated Item (" + itemDetails2.get("DESCRIPTION") + ") Stock increased from <b>" + stockBeforeRequisition + "</b> to <b>" + stockAfterRequisition + "</b>");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(description = "Validate Requisition Received by using indent")
    public void validateCreateRequisitionThroughIndentTest() {
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Requisition = new Page_Requisition(driver);
        oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        oPage_Indent = new Page_Indent(driver);
        oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        oPage_Purchase=new Page_Purchase(driver);

        itemDetails1=new HashMap<>();
        String sRatePerUnit = "300";
        String mrp = "400";
        dTotalAmount=0;

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            getStoreAndVendorDetails(sReceivingStore);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            itemDetails1 = getItemDetails(sItem1Description);

            openRequisitionPage();

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition), "Clicked <b>New</b> Requisition button");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.select_receivingStore, 10), "Create Requisition Page Opened");

            String orderDate = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate, "value");
            String orderTime = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "value");
            sRequisitionCreatedAt = orderDate + "|" + CommonActions.getRequiredFormattedDateTime("h:mm a", "hh:mm a", orderTime);
            m_assert.assertTrue(compareDateTimesWithTolerance(getCurrentDateTime(), sRequisitionCreatedAt, 1), "Validated default Order Date <b>(" + orderDate + ")</b> and Time <b>(" + orderTime + ")</b>");

            //Header Elements
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_orderNote, sOrderNote), "Entered " + sOrderNote + " in Order Note");
            m_assert.assertInfo(selectByOptions(oPage_Requisition.select_receivingStore, sReceivingStore.split("-")[0]), "Selected <b>" + sReceivingStore.split("-")[0] + "</b> in Receiving Store");
            m_assert.assertInfo(selectByOptions(oPage_Requisition.select_reqType, sRequisitionType), "Selected <b>" + sRequisitionType + "</b> in Receiving Type");

            selectItemFromLeftPanelCreateRequisition(itemDetails1);

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Requisition.button_removeItemFromList, 10);

            validateAndFillItemDetails(itemDetails1);
            Cls_Generic_Methods.customWait();


            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Requisition.button_saveRequisition), "Clicked <b>Save</b> Requisition");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 10);
            openRequisitionPage();
            selectRequisitionOrder();

            m_assert.assertTrue(compareDateTimesWithTolerance(getRequisitionCreatedAt, sRequisitionCreatedAt, 0), "Validated Requisition Created List -> Order Created At " + getRequisitionCreatedAt);
            m_assert.assertTrue(getReceivingStore.replaceAll(" ", "").equalsIgnoreCase(sReceivingStore.replaceAll(" ", "")), "Validated Requisition Created List -> Order Receiving Store : <b>" + getReceivingStore + "</b>");
            m_assert.assertTrue(getRequisitionType.contains(sRequisitionType), "Validated Requisition Created List -> Requisition Type :<b>" + sRequisitionType + "</b>");
            m_assert.assertTrue(getRequisitionStatus.contains("Open"), "Validated Requisition Created List -> Requisition Status :<b>Open</b>");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_viewOrderRequisition, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition), "Clicked <b>View Order</b>");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_editRequisition, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_approveRequisition), "Clicked <b>Approve</b> Requisition");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_confirmRequisition, 10);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_Requisition.button_confirmRequisition);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();


            openRequisitionReceivedPage();
            boolean bRequisitionReceivedFound = selectRequisitionReceivedOrder(sRequisitionId);

            if (bRequisitionReceivedFound) {
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait();

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_addNewIndent, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Indent.button_addNewIndent),
                        "New Button clicked in Indent Order");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_variantOrRequisitionSelected, 10);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.select_vendorField, 10);
                m_assert.assertTrue(selectByOptions(oPage_Indent.select_StoreInIndent, sReceivingStore.split("-")[0]), "Selected Store as : " + sReceivingStore.split("-")[0]);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_requisitionToSelect), "Requisition button clicked under Indent ");
                Cls_Generic_Methods.customWait();

                Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.select_indentType,sRequisitionType);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_noteUnderIndentForPurchase, sOrderNote), "Entered Order Note  <b>" + sOrderNote + "</b> ");
                Cls_Generic_Methods.customWait();

                boolean requisitionIndentFound = false;
                for (WebElement eRequisitionNumberIndent : oPage_Indent.list_reqInfoUnderIndentPurchase) {
                    if (Cls_Generic_Methods.getTextInElement(eRequisitionNumberIndent).contains(sRequisitionId)) {
                        Cls_Generic_Methods.clickElement(eRequisitionNumberIndent);
                        requisitionIndentFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(requisitionIndentFound, "<b> Requisition under indent is found " + sRequisitionId + "</b>");

                if (Cls_Generic_Methods.isElementDisplayed(oPage_Indent.select_substoreFromIndentPurchasePage)) {
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.select_substoreFromIndentPurchasePage, itemDetails1.get("SUB STORE"));
                    Cls_Generic_Methods.customWait();
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder), " Indent order saved");
                String indentCreatedAt = getCurrentDateTime();
                Cls_Generic_Methods.customWait(3);

                boolean bIndentOrderFound = false;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String indentOrderId = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[5]")));

                    if (compareDateTimesWithTolerance(indentCreatedAt, dateTimeFull, 1)) {
                        bIndentOrderFound = true;
                        sIndentOrderId = indentOrderId;
                        m_assert.assertTrue("Indent Order Created for Requisition -> ID :<b> " + sIndentOrderId + "</b>");
                        Cls_Generic_Methods.clickElement(eDate);
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }

                if (bIndentOrderFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_newOrder, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder), "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);
                    String sIndentNoInPurchaseOrderPopUp = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp).split(":")[1].trim();

                    if (sIndentOrderId.equalsIgnoreCase(sIndentNoInPurchaseOrderPopUp)) {
                        m_assert.assertTrue("Validated Indent Number while creating new purchase order =  <b>" + sIndentNoInPurchaseOrderPopUp + "</b>");
                    } else {
                        m_assert.assertTrue("displaying wrong Indent Number ");
                    }

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_OrderNote, 10);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_OrderNote, sOrderNote);

                    String storeName=Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_purchaseStoreName,"value");
                    m_assert.assertTrue(storeName.equals(sReceivingStore.split("-")[0]), "Selected store : <b>" + storeName + " </b>");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_Vendor_search, 4);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, (vendorName + " " + vendor_address));
                    Cls_Generic_Methods.customWait();

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_Vendor_search, "" + Keys.DOWN + Keys.ENTER),
                            "Vendor selected : <b>" + vendorName + "</b>");
                    Cls_Generic_Methods.customWait();

                    //Validating UI Header Elements
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.input_orderNote, 7);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PurchaseOrder.input_orderNote, sOrderNote), "Entered " + sOrderNote + " in Order Note");
                    String storeGstNoInPo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_storeGstNo, "value");
                    m_assert.assertTrue(storeGstNoInPo.equals(storeGSTno), "Validated displayed Store GST No : <b>" + storeGstNoInPo + "</b>");

                    //PO EXPIRY
                    LocalDate date = LocalDate.now().plusDays(Integer.parseInt(vendorPOExpiry));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String expectedPOExpiryDay = date.format(formatter);

                    String poExpiryDateInPo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_expiryDatePO, "value");
                    m_assert.assertTrue(poExpiryDateInPo.equals(expectedPOExpiryDay), "Validated displayed PO expiry Date : <b>" + poExpiryDateInPo + "</b>");

                    //CREDIT DAYS
                    String creditDaysInPo = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_creditDays, "value");
                    m_assert.assertTrue(creditDaysInPo.equals(vendorCreditDays), "Validated displayed Vendor Credit Days : <b>" + creditDaysInPo + "</b>");

                    //ADDRESS
                    String selectedBillAddressInPo = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_billToStore);
                    m_assert.assertTrue(selectedBillAddressInPo.contains(storeDefaultBillingAddress), "Validated displayed Bill To Address : <b>" + selectedBillAddressInPo + "</b>");

                    String selectedShipAddressInPo = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_shipToStore);
                    m_assert.assertTrue(selectedShipAddressInPo.contains(storeDefaultShippingAddress), "Validated displayed Ship To Address : <b>" + selectedShipAddressInPo + "</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_RatePerUnit, sRatePerUnit), "Rate per unit =  <b>" + sRatePerUnit + " </b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_cancelOtherCharges, 4);
                    Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_cancelOtherCharges);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_saveIndentPurchaseOrder), "Indent purchase order Save Button clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.option_purchaseOrder, 10);

                    //PURCHASE ORDER
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                    Cls_Generic_Methods.customWait();
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_newOrder, 3);

                    boolean purchaseOrderFound = false;
                    double actualTotalNetAmount = 0;
                    for (WebElement row : oPage_PurchaseOrder.list_purchaseOrdertransactions) {
                        String value = Cls_Generic_Methods.getTextInElement(row);

                        purchaseOrderFound = true;
                        Cls_Generic_Methods.clickElement(row);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.text_purchaseOrderId,10);
                        sPurchaseOrderId=Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_purchaseOrderId);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_approveOrder, 10);
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_approveOrder), "Clicked <b>Approve</b> button");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_Yes, 10);
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_PurchaseOrder.button_Yes);
                        m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.label_approved, 5), "Validated Purchase Order Approved Notify Msg displayed");
                        Cls_Generic_Methods.customWait();

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_NewTransaction, 15);

                        m_assert.assertTrue("Validated -> Purchase Order created -> PO No : <b>" + sPurchaseOrderId + "</b>");
                        break;
                    }

                    //GRN
                    String billType = "Bill";
                    String billNumber = "BILL_NO_" + getRandomNumber();
                    String batchNo = getRandomNumber();
                    String subStore="Default";

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.button_NewTransaction, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.button_NewTransaction), "Clicked New transaction button to create GRN");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_GRN, 10);

                    Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType), "Selected Bill Type as <b>" + billType + "</b>");

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber), "Entered<b> " + billNumber + "</b> in Bill Number");
                    Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                    Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate);

                    String rateInGrn = Cls_Generic_Methods.getTextInElement(oPage_Purchase.list_rate.get(0));

                    m_assert.assertTrue(CommonActions.convertStringToDouble(rateInGrn) == CommonActions.convertStringToDouble(sRatePerUnit), "Validated GRN -> Entered Item Rate :<b>" + rateInGrn + " </b>");
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_batchNumber.get(0), batchNo), "Entered  batch number : <b>" + batchNo + " </b>");
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.list_MRPPrice.get(0), mrp), "Entered item Mrp : <b>" + mrp + " </b>");


                    String paidQtyInGrn = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_poPaidQty, "value");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(paidQtyInGrn) == CommonActions.convertStringToDouble(itemDetails1.get("QUANTITY")), "Validated GRN -> Entered Item Quantity :<b>" + paidQtyInGrn + " </b>");
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_subStore, subStore);
                    Cls_Generic_Methods.customWait();
                    String netAmount=Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_totalNetAmount,"value");
                    double unitCostWithTax=CommonActions.convertStringToDouble(netAmount)/CommonActions.convertStringToDouble(itemDetails1.get("QUANTITY"));
                    Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges);
                    String purchaseGrnSavedAt = getCurrentDateTime();

                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                    Cls_Generic_Methods.customWait();
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
                    List<String> purchaseTransactionHeaderList = new ArrayList<String>();

                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_purchaseTransactionHeaderList, 10);
                    for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                        purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
                    }

                    String grnCreatedAt = "";
                    for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                        if (Cls_Generic_Methods.isElementDisplayed(row)) {
                            List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                            String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));
                            if (purchaseStatus.equalsIgnoreCase("open")) {
                                Cls_Generic_Methods.clickElement(row);
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 10);

                                grnCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnCreatedAt);

                                if (!compareDateTimesWithTolerance(grnCreatedAt, purchaseGrnSavedAt, 1)) {
                                    continue;
                                }

                                sPurchaseGrnId = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_transactionID);
                                m_assert.assertInfo("Purchase Grn no =<b>" + sPurchaseGrnId + "</b>");
                                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_Yes, 6);
                                Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_Yes);
                                m_assert.assertInfo("Purchase Grn created and approved");
                                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_assignBarcodePurchaseTransaction, 15);

                                m_assert.assertInfo("Purchase Grn Created At = <b>" + grnCreatedAt + "</b>");
                                break;
                            }
                        }
                    }

                    itemDetails1.put("BATCH", batchNo);
                    itemDetails1.put("GRN NO", sPurchaseGrnId);
                    itemDetails1.put("GRN TIME", grnCreatedAt);
                    itemDetails1.put("SUB STORE",subStore );
                    itemDetails1.put("MRP", mrp);
                    itemDetails1.put("RATE", sRatePerUnit);
                    itemDetails1.put("EXPIRY", "");
                    itemDetails1.put("TOTAL AMOUNT", String.valueOf(unitCostWithTax));

                    if (!purchaseOrderFound) {
                        throw new Exception("PURCHASE ORDER NOT GENERATED");
                    }

                } else {
                    throw new RuntimeException("INDENT NOT FOUND");
                }

            } else {
                m_assert.assertFatal("Requisition not found in requisition Received");
            }



            openRequisitionReceivedPage();
            selectRequisitionReceivedOrder(sRequisitionId);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 10);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_newTransactionRequisition), "Clicked <b>New Transaction</b>");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.text_headerIssueItems, 15), "Issue Items page displayed");


            String receivingStore = Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_receivingStore, "value");
            m_assert.assertTrue(receivingStore.contains(sStore.split("-")[0]), "Validated Displayed Receiving Store : " + receivingStore);


            String item1issueQuantity=itemDetails1.get("QUANTITY");
            validateItemInRequisitionReceived(itemDetails1);
            validateLotRequisitionReceived(itemDetails1, item1issueQuantity);

            //Create Page
            validateIssueItemTable(itemDetails1, item1issueQuantity);

            String actualTotalAmount = Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_totalAmtIncludingTax, "value");
            m_assert.assertTrue(CommonActions.convertStringToDouble(actualTotalAmount) == dTotalAmount, "Validated Issue Items -> Total Amount Inc Tax = <b>" + actualTotalAmount + "</b>");

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_saveTransfer), "Clicked <b>Save</b>");
            currentIssueStatus = "open";
            receivedAt = getCurrentDateTime();

            openRequisitionReceivedPage();
            selectRequisitionReceivedOrder(sRequisitionId);

            validateIssueHistory();
            validateIssueHistoryTable(itemDetails1, item1issueQuantity);

            boolean newTransactionBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 3);
            m_assert.assertFalse(newTransactionBtn, "Validated Requisition Received -> <b>New Transaction</b> button is not displayed , if order has Open Transaction ");

            approveIssueTransaction();

            openRequisitionReceivedPage();
            selectRequisitionReceivedOrder(sRequisitionId);

            newTransactionBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 6);
            m_assert.assertFalse(newTransactionBtn, "Validated Requisition Received -> <b>New Transaction</b> button is displayed , if transaction is closed ");

            //To check the status
            validateIssueHistory();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            if (enabledTaxInvoice) {
                //TAX INVOICE
                CommonActions.selectStoreOnApp(sReceivingStore);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.customWait();
                createTaxInvoice();
            }

            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            receiveItem(itemDetails1, item1issueQuantity, false);

            //Checking RHS Status After closed Transaction
            openRequisitionReceivedPage();
            selectRequisitionReceivedOrder(sRequisitionId);
            currentIssueStatus = "Closed";
            validateIssueHistory();

            newTransactionBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_newTransactionRequisition, 6);
            m_assert.assertFalse(newTransactionBtn, "Validated Requisition Received -> <b>New Transaction</b> button is not displayed , if order is completed ");

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

            openRequisitionPage();
            selectRequisitionOrder(sRequisitionId);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.text_rhs_requisitionStatus, 10);
            String status = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionStatus);

            m_assert.assertTrue(getRequisitionStatus.equalsIgnoreCase(currentIssueStatus), "Validated Requisition Status : <b>" + getRequisitionStatus + "</b>");
            m_assert.assertTrue(status.equalsIgnoreCase(currentIssueStatus), "Validated Requisition RHS status : <b>" + status + "</b>");

            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();


            HashMap<String, String> itemDetails1AfterRequisition = getItemDetails(itemDetails1.get("DESCRIPTION"));

            double stockAfterRequisition = CommonActions.convertStringToDouble(itemDetails1AfterRequisition.get("STOCK"));
            double stockBeforeRequisition = CommonActions.convertStringToDouble(itemDetails1.get("STOCK"));
            m_assert.assertTrue(stockAfterRequisition == stockBeforeRequisition + (CommonActions.convertStringToDouble(itemDetails1.get("QUANTITY"))), "Validated Item (" + itemDetails1.get("DESCRIPTION") + ") Stock increased from <b>" + stockBeforeRequisition + "</b> to <b>" + stockAfterRequisition + "</b>");

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createTaxInvoice() {
        try {
            TaxInvoiceTest taxInvoice = new TaxInvoiceTest();
            Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);

            taxInvoice.sTransferId = sIssueTransactionId;
            taxInvoice.sReceivingStore = sStore;
            taxInvoice.createTaxInvoiceDeliveryChallanWithMandatoryField("TAX INVOICE", true);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_approve, 10);
            Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_approve);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            m_assert.assertFatal("Unable to create Tax Invoice " + e);
            e.printStackTrace();
        }
    }

    private void receiveItem(HashMap<String, String> itemDetails, String issueQuantity, boolean receivePartial) {
        Page_Receive oPage_Receive = new Page_Receive(driver);

        try {
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Receive");
            Cls_Generic_Methods.customWait(4);


            for (WebElement row : oPage_Receive.list_text_transactionIdRow) {
                Cls_Generic_Methods.clickElement(row);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.button_receiveStock, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Receive.button_receiveStock), "Clicked <b>Receive</b>");

                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.text_receiveQtyUnderReceiveTransactionPopUp, 10);

                String quantityInReceive = Cls_Generic_Methods.getElementAttribute(oPage_Receive.text_receiveQtyUnderReceiveTransactionPopUp, "value");
                String itemDescriptionInReceive = Cls_Generic_Methods.getTextInElement(oPage_Receive.text_itemDescriptionUnderReceiveTransactionPopUp);
                String itemBatchNoInReceive = Cls_Generic_Methods.getTextInElement(oPage_Receive.text_itemBatchNoUnderReceiveTransactionPopUp);
                String itemMrpInReceive = Cls_Generic_Methods.getTextInElement(oPage_Receive.text_totalCostUnderReceiveTransactionPopUp);

                m_assert.assertTrue(itemDescriptionInReceive.equals(itemDetails.get("DESCRIPTION")), "Validated Receive Transaction -> Received Item Description : <b>" + itemDescriptionInReceive + "</b>");
                m_assert.assertTrue(itemBatchNoInReceive.equals(itemDetails.get("BATCH")), "Validated Receive Transaction -> Item Batch No : <b>" + itemBatchNoInReceive + "</b>");
                m_assert.assertTrue(CommonActions.convertStringToDouble(quantityInReceive) == CommonActions.convertStringToDouble(issueQuantity), "Validated Receive Transaction -> Received Item Quantity : <b>" + quantityInReceive + "</b>");
                m_assert.assertTrue(CommonActions.convertStringToDouble(itemMrpInReceive) == CommonActions.convertStringToDouble(itemDetails.get("MRP")), "Validated Receive Transaction -> Item MRP : <b>" + itemMrpInReceive + "</b>");

                if (receivePartial) {
                    double partialQuantity = CommonActions.convertStringToDouble(issueQuantity) - 1;
                    Cls_Generic_Methods.clearValuesInElement(oPage_Receive.text_receiveQtyUnderReceiveTransactionPopUp);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Receive.text_receiveQtyUnderReceiveTransactionPopUp, String.valueOf(partialQuantity)), "Entered <b>" + partialQuantity + "</b> in Receive Quantity [<i>Entered Partial Quantity</i>]");
                    double remainingQuantity = CommonActions.convertStringToDouble(itemDetails.get("REMAINING QUANTITY")) + 1;

                    itemDetails.put("REMAINING QUANTITY", String.valueOf(remainingQuantity));
                    currentIssueStatus = "PENDING";
                } else {
                    currentIssueStatus = "CLOSED";
                    itemDetails.put("REMAINING QUANTITY", "0");
                }

                boolean subStore = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Receive.select_subStore, 1);

                if (subStore) {
                    selectByOptions(oPage_Receive.select_subStore, itemDetails.get("SUB STORE"));
                }
                Cls_Generic_Methods.customWait();
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Receive.button_saveChanges), "Clicked <b>Save Changes</b>");
                Cls_Generic_Methods.customWait(4);
                break;
            }

            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            m_assert.assertFatal("Unable To Receive Transaction " + e);
            e.printStackTrace();
        }
    }

    private boolean setupTaxInvoiceDeliveryChallanSetting(boolean enable) {
        boolean flag = false;

        oPage_StoreSetUp = new Page_StoreSetUp(driver);

        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Store Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_StoreSetUp.button_addStore, 3);

            int storeNo = 0;
            for (WebElement txtStoreName : oPage_StoreSetUp.list_text_storeName) {
                String storeName = Cls_Generic_Methods.getTextInElement(txtStoreName);
                if (storeName.contains(sReceivingStore.split("-")[0])) {
                    Cls_Generic_Methods.clickElement(driver, oPage_StoreSetUp.list_btn_editStore.get(storeNo));
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_StoreSetUp.list_btn_editStoreBillingAddress, 10);

                    Cls_Generic_Methods.clickElementByJS(driver, oPage_StoreSetUp.radio_enableTransferOrIssue);

                    if (enable) {
                        m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_StoreSetUp.radio_enableTaxInvoiceDeliveryChallan), "Enabled Tax Invoice and Challan in Store Setup");
                    } else {
                        m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_StoreSetUp.radio_disableTaxInvoiceDeliveryChallan), "Disabled Tax Invoice and Challan in Store Setup");
                    }
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElement(oPage_StoreSetUp.button_updateStore);
                    break;
                }
                storeNo++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    private void validateIssueItemTable(HashMap<String, String> itemDetails, String issueQuantity) {

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.input_totalAmtIncludingTax, 10);
            String itemDescription = itemDetails.get("DESCRIPTION");
            ArrayList<String> headerValue = new ArrayList<>();
            for (WebElement header : oPage_RequisitionReceived.list_headerIssueItems) {
                headerValue.add(Cls_Generic_Methods.getTextInElement(header));
            }

            for (WebElement row : oPage_RequisitionReceived.list_rowIssueItems) {
                int rowNo = oPage_RequisitionReceived.list_rowIssueItems.indexOf(row);

                String description = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Description")));
                String grnNo = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("GRN No.")));
                String grnDate = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("GRN Date")));
                String batchNo = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Batch")));
                String expiry = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Expiry")));
                String mrp = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("MRP")));
                String rate = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Rate")));
                String quantity = Cls_Generic_Methods.getElementAttribute(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Qty")).findElement(By.xpath(".//input")), "value");
                String amount = Cls_Generic_Methods.getElementAttribute(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Amount")).findElement(By.xpath(".//input")), "value");
                String textTotal = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.list_textTotalIssueItems.get(rowNo));

                if (description.contains(itemDescription)) {
                    m_assert.assertInfo("Validated Requisition Received Issue Items -> Item found : " + description);
                   // m_assert.assertTrue(grnNo.equals(itemDetails.get("GRN NO")), "Validated Requisition Received Issue Items -> Item(" + description + ") GRN No : " + grnNo);
                    m_assert.assertTrue(itemDetails.get("GRN TIME").contains(CommonActions.getRequiredFormattedDateTime("dd/mm/yyy", "dd-mm-yyyy", grnDate)), "Validated Requisition Received Issue Items -> Item(" + description + ") GRN Date : " + grnDate);
                    m_assert.assertTrue(batchNo.equals(itemDetails.get("BATCH")), "Validated Requisition Received Issue Items -> Item(" + description + ") Batch No : " + batchNo);

                    if (!itemDetails.get("EXPIRY").isEmpty()) {
                        m_assert.assertTrue(CommonActions.getRequiredFormattedDateTime("yyyy-mm-dd", "dd/mm/yyyy", expiry).equals(itemDetails.get("EXPIRY")), "Validated Requisition Received Issue Items -> Item(" + description + ") Expiry Date : " + expiry);
                    }

                    m_assert.assertTrue(CommonActions.convertStringToDouble(mrp) == CommonActions.convertStringToDouble(itemDetails.get("MRP")), "Validated Requisition Received Issue Items -> Item(" + description + ") MRP : " + mrp);
                    m_assert.assertTrue(CommonActions.convertStringToDouble(rate) == CommonActions.convertStringToDouble(itemDetails.get("RATE")), "Validated Requisition Received Issue Items -> Item(" + description + ") Rate : " + rate);
                    m_assert.assertTrue(CommonActions.convertStringToDouble(quantity) == CommonActions.convertStringToDouble(issueQuantity), "Validated Requisition Received Issue Items -> Item(" + description + ") Issue Quantity : " + quantity);

                    double dAmount = CommonActions.convertStringToDouble(itemDetails.get("TOTAL AMOUNT")) * CommonActions.convertStringToDouble(issueQuantity);
                    m_assert.assertTrue(CommonActions.convertStringToDouble(amount) == dAmount, "Validated Requisition Received Issue Items -> Item(" + description + ") Amount : " + amount);
                    dTotalAmount = dTotalAmount + dAmount;

                    m_assert.assertTrue(CommonActions.convertStringToDouble(textTotal.split(":")[1].trim()) == CommonActions.convertStringToDouble(issueQuantity), "Validated Requisition Received Issue Items -> Displayed Total for Item(" + description + ") " + textTotal);

                    //Validate Edit
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_RequisitionReceived.list_editLotIssueItems.get(rowNo)), "Clicked <b>Edit Lot</b> button");
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.input_issueQuantity, 10), "Validated Edit Lot -> Displayed Lot Page");

                    String actualIssueQuantity = Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_issueQuantityLot, "value");
                    String actualRemainingQuantity = Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_remainingQuantityLot, "value");

                    m_assert.assertTrue(CommonActions.convertStringToDouble(actualIssueQuantity) == CommonActions.convertStringToDouble(issueQuantity), "Validated Requisition Received Lot Details -> Item(" + itemDescription + ") Issue Quantity : <b>" + actualIssueQuantity + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(actualRemainingQuantity) == CommonActions.convertStringToDouble(itemDetails.get("REMAINING QUANTITY")), "Validated Requisition Received Lot Details -> Item(" + itemDescription + ") Remaining Quantity : <b>" + actualRemainingQuantity + "</b>");
                    Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_closeWithoutSavingLot);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Note : If Issue quantity is passed . Validation part ll be skipped
    private void validateLotRequisitionReceived(HashMap<String, String> itemDetails, String... issueQuantity) {
        try {
            String sIssueQuantity = "0";
            String itemDescription = itemDetails.get("DESCRIPTION");
            String sAvailableQuantity = itemDetails.get("REMAINING QUANTITY");

            if (issueQuantity.length > 0) {
                sIssueQuantity = issueQuantity[0];
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.input_balanceQuantityLot, 10);
            for (WebElement eDescription : oPage_RequisitionReceived.list_textDescriptionLots) {

                if (sAvailableQuantity.isEmpty()) {
                    m_assert.assertFatal("Unable To Validate Requisition Received Lot Details for Item (" + itemDescription + ") -> Incorrect Lot Detail");
                    break;
                }

                int rowNo = oPage_RequisitionReceived.list_textDescriptionLots.indexOf(eDescription);
                String sDescriptionInTable = Cls_Generic_Methods.getTextInElement(eDescription);
                String sGrnNo = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.list_textGrnNoLots.get(rowNo));

                if (sGrnNo.equals(itemDetails.get("GRN NO"))) {
                    if (CommonActions.convertStringToDouble(sIssueQuantity) == 0) {
                        String sBatch = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.list_textBatchNoLots.get(rowNo));
                        String sExpiry = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.list_textExpiryLots.get(rowNo));
                        String sGrnTimeInTable = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.list_textGrnCreatedAtLots.get(rowNo));
                        String sMrpInTable = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.list_textMrpLots.get(rowNo));
                        String sRateInTable = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.list_textRateLots.get(rowNo));
                        String sAvailableQtyInTable = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.list_textAvailableQuantityLots.get(rowNo));

                        m_assert.assertTrue("Validated Requisition Received Lot Details -> Item Description : <b>" + sDescriptionInTable + "</b>");
                        m_assert.assertTrue(sBatch.equals(itemDetails.get("BATCH")), "Validated Requisition Received Lot Details -> Item(" + itemDescription + ") Batch No : <b>" + sBatch + "</b>");
                        m_assert.assertTrue(sExpiry.equals(itemDetails.get("EXPIRY")), "Validated Requisition Received Lot Details -> Item(" + itemDescription + ") Expiry Date : <b>" + sExpiry + "</b>");
                        m_assert.assertTrue(sGrnNo.equals(itemDetails.get("GRN NO")), "Validated Requisition Received Lot Details -> Item(" + itemDescription + ") GRN No : <b>" + sGrnNo + "</b>");
                        m_assert.assertTrue(compareDateTimesWithTolerance(sGrnTimeInTable, itemDetails.get("GRN TIME"), 0), "Validated Requisition Received Lot Details -> GRN Created At : <b>" + sGrnTimeInTable + "</b>");
                        m_assert.assertTrue(CommonActions.convertStringToDouble(sMrpInTable) == CommonActions.convertStringToDouble(itemDetails1.get("MRP")), "Validated Requisition Received Lot Details -> Item(" + itemDescription + ") MRP : <b>" + sMrpInTable + "</b>");
                        m_assert.assertTrue(CommonActions.convertStringToDouble(sRateInTable) == CommonActions.convertStringToDouble(itemDetails1.get("RATE")), "Validated Requisition Received Lot Details -> Item(" + itemDescription + ") Rate : <b>" + sRateInTable + "</b>");
                        m_assert.assertTrue(CommonActions.convertStringToDouble(sAvailableQtyInTable) == CommonActions.convertStringToDouble(sAvailableQtyInTable), "Validated Requisition Received Lot Details -> Item(" + itemDescription + ") Available Quantity : <b>" + sAvailableQtyInTable + "</b>");

                    } else {
                        Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.list_inputLotStock.get(rowNo));
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RequisitionReceived.list_inputLotStock.get(rowNo), sIssueQuantity), "Entered <b>" + sIssueQuantity + "</b> in Issue Quantity");
                        double remainingQuantity = CommonActions.convertStringToDouble(sAvailableQuantity) - CommonActions.convertStringToDouble(sIssueQuantity);
                        itemDetails.put("REMAINING QUANTITY", String.valueOf(remainingQuantity));

                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.text_headerIssueItems, 10);
                    break;
                }
                else{
                    Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.list_inputLotStock.get(rowNo));
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RequisitionReceived.list_inputLotStock.get(rowNo), sIssueQuantity), "Entered <b>" + sIssueQuantity + "</b> in Issue Quantity");
                    double remainingQuantity = CommonActions.convertStringToDouble(sAvailableQuantity) - CommonActions.convertStringToDouble(sIssueQuantity);
                    itemDetails.put("REMAINING QUANTITY", String.valueOf(remainingQuantity));

                }
            }

            String actualBalanceQuantity = Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_balanceQuantityLot, "value");
            String actualIssueQuantity = Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_issueQuantityLot, "value");
            String actualRemainingQuantity = Cls_Generic_Methods.getElementAttribute(oPage_RequisitionReceived.input_remainingQuantityLot, "value");

            double dBalanceQuantity = CommonActions.convertStringToDouble(itemDetails.get("BALANCE QUANTITY"));

            m_assert.assertTrue(CommonActions.convertStringToDouble(actualBalanceQuantity) == dBalanceQuantity, "Validated Requisition Received Lot Details -> Item(" + itemDescription + ") Balance Quantity : <b>" + actualBalanceQuantity + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(actualIssueQuantity) == CommonActions.convertStringToDouble(sIssueQuantity), "Validated Requisition Received Lot Details -> Item(" + itemDescription + ") Issue Quantity : <b>" + actualIssueQuantity + "</b>");
            m_assert.assertTrue(CommonActions.convertStringToDouble(actualRemainingQuantity) == CommonActions.convertStringToDouble(itemDetails.get("REMAINING QUANTITY")), "Validated Requisition Received Lot Details -> Item(" + itemDescription + ") Remaining Quantity : <b>" + actualRemainingQuantity + "</b>");

            if (CommonActions.convertStringToDouble(sIssueQuantity) == 0) {
                Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_closeWithoutSavingLot);

            } else {
                double balance = CommonActions.convertStringToDouble(itemDetails.get("BALANCE QUANTITY")) - CommonActions.convertStringToDouble(sIssueQuantity);
                itemDetails.put("BALANCE QUANTITY", String.valueOf(balance));
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_confirmTransfer), "Clicked <b>Confirm</b> Button");
            }


        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate lot requisition received" + e);
            e.printStackTrace();
        }
    }

    public void validateItemInRequisitionReceived(HashMap<String, String> itemDetails) {

        try {
            String itemDescription = itemDetails.get("DESCRIPTION");
            String remainingQuantity = itemDetails.get("REMAINING QUANTITY");

            ArrayList<String> headerValue = new ArrayList<>();
            for (WebElement header : oPage_RequisitionReceived.list_requisitionItemsHeader) {
                headerValue.add(Cls_Generic_Methods.getTextInElement(header));
            }

            for (WebElement row : oPage_RequisitionReceived.list_requisitionItems) {
                String description = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Description")));
                String requestedQuantity = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Requested Quantity")));
                String remQuantity = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Rem.Quantity")));
                String remark = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Remark")));

                if (description.contains(itemDescription)) {
                    m_assert.assertInfo("Validated Requisition Received -> Item found : " + description);
                    m_assert.assertTrue(requestedQuantity.equals(itemDetails.get("QUANTITY")), "Validated Requisition Received -> Item(" + description + ") Requested Quantity : " + requestedQuantity);
                    m_assert.assertTrue(remQuantity.equals(remainingQuantity), "Validated Requisition Received -> Item(" + description + ") Remaining Quantity : " + remainingQuantity);
                    m_assert.assertTrue(remark.equals("TEST"), "Validated Requisition Received -> Item(" + description + ") Remark : " + remark);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(row), "Selected Item : <b>" + description + "</b> in Issue Items");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void validateRequisitionRHS() {
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Requisition = new Page_Requisition(driver);

        try {
            openRequisitionPage();
            boolean selectRequisition = selectRequisitionOrder(sRequisitionId);

            if (selectRequisition) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.text_rhs_requisitionNo, 10);

                String requisitionNo = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionNo);
                String requisitionCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionCreatedByUser);
                String requisitionCreatedStore = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionCreatedStore);
                String requisitionCreatedAtDateAndTime = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionCreatedAt);
                String requisitionStatus = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionStatus);
                String requisitionToStore = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionToStore);
                String requisitionType = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionType);
                String requisitionOrderNote = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_RHSOrderNote);

                m_assert.assertTrue(requisitionNo.equalsIgnoreCase(sRequisitionId), "Validated Requisition RHS --> Requisition ID <b>: " + requisitionNo + "</b>");
                m_assert.assertTrue(requisitionType.equalsIgnoreCase("Manual"), "Validated Requisition RHS --> Type <b>: " + requisitionType + "</b>");
                m_assert.assertTrue(requisitionCreatedBy.equals(expectedLoggedInUser), "Validated Requisition RHS --> Requisition Created By User <b>: " + requisitionCreatedBy + "</b>");
                m_assert.assertTrue(requisitionCreatedStore.contains(sStore.split("-")[0]), "Validated Requisition RHS --> Requisition Created Store <b>: " + requisitionCreatedStore + "</b>");
                m_assert.assertTrue(compareDateTimesWithTolerance(requisitionCreatedAtDateAndTime, sRequisitionCreatedAt, 0), "Validated Requisition RHS --> Requisition Created At <b>: " + requisitionCreatedAtDateAndTime + "</b>");
                m_assert.assertTrue(requisitionStatus.equalsIgnoreCase(getRequisitionStatus), "Validated Requisition RHS --> Status <b>: " + requisitionStatus + " </b>");
                m_assert.assertTrue(requisitionToStore.replaceAll(" ", "").equalsIgnoreCase(sReceivingStore.replaceAll(" ", "")), "Validated Requisition RHS --> Requisition To Store <b>: " + requisitionToStore + "</b>");
                m_assert.assertTrue(requisitionOrderNote.equalsIgnoreCase(sOrderNote), "Validated Requisition RHS --> Order Note <b>: " + requisitionOrderNote + " </b>");

                //Validate Item Details Table

                validateRequisitionRHSItemDetails(itemDetails1);
                validateRequisitionRHSItemDetails(itemDetails2);

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Requisition.button_viewOrderRequisition), "Clicked <b>View Order</b> button");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.text_headerViewOrder, 10), "View Order Pop-up window is displayed");

                if (getRequisitionStatus.equalsIgnoreCase("open")) {
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_editRequisition), "Edit Button is Displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_cancelRequisition), "Cancel Button is Displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_approveRequisition), "Approve Button is Displayed");
                } else {
                    m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_editRequisition), "Edit Button is not Displayed");
                    m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_cancelRequisition), "Cancel Button is not Displayed");
                    m_assert.assertFalse(Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.button_approveRequisition), "Approve Button is not Displayed");
                }
                requisitionNo = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_viewOrder_requisitionNo);
                requisitionCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_viewOrder_requisitionCreatedByUser);
                requisitionCreatedStore = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_viewOrder_requisitionCreatedStore);
                requisitionCreatedAtDateAndTime = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_viewOrder_requisitionCreatedAt);
                requisitionStatus = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_viewOrder_requisitionStatus);
                requisitionToStore = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_viewOrder_requisitionToStore);
                requisitionType = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_viewOrder_requisitionType);
                requisitionOrderNote = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_viewOrderWindowNote);

                m_assert.assertTrue(requisitionNo.equalsIgnoreCase(sRequisitionId), "Validated Requisition View Order --> Requisition ID <b>: " + requisitionNo + "</b>");
                m_assert.assertTrue(requisitionType.equalsIgnoreCase("Manual"), "Validated Requisition View Order --> Type <b>: " + requisitionType + "</b>");
                m_assert.assertTrue(requisitionCreatedBy.equals(expectedLoggedInUser), "Validated Requisition View Order --> Requisition Created By User <b>: " + requisitionCreatedBy + "</b>");
                m_assert.assertTrue(requisitionCreatedStore.contains(sStore.split("-")[0]), "Validated Requisition View Order --> Requisition Created Store <b>: " + requisitionCreatedStore + "</b>");
                m_assert.assertTrue(compareDateTimesWithTolerance(requisitionCreatedAtDateAndTime, sRequisitionCreatedAt, 0), "Validated Requisition View Order --> Requisition Created At <b>: " + requisitionCreatedAtDateAndTime + "</b>");
                m_assert.assertTrue(requisitionStatus.equalsIgnoreCase(getRequisitionStatus), "Validated Requisition View Order --> Status <b>: " + requisitionStatus + " </b>");
                m_assert.assertTrue(requisitionToStore.replaceAll(" ", "").equalsIgnoreCase(sReceivingStore.replaceAll(" ", "")), "Validated Requisition View Order --> Requisition To Store <b>: " + requisitionToStore + "</b>");
                m_assert.assertTrue(requisitionOrderNote.equalsIgnoreCase(sOrderNote), "Validated Requisition View Order --> Order Note <b>: " + requisitionOrderNote + " </b>");

                validateRequisitionViewOrderItemDetails(itemDetails1);
                validateRequisitionViewOrderItemDetails(itemDetails2);

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Requisition.button_close), "Clicked <b>Close</b> Button");

            } else {
                m_assert.assertFatal("Unable to select requisition -> " + sRequisitionId);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void validateRequisitionReceivedRHS() {
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Requisition = new Page_Requisition(driver);
        oPage_RequisitionReceived = new Page_RequisitionReceived(driver);

        try {
            openRequisitionReceivedPage();
            boolean selectRequisition = selectRequisitionReceivedOrder(sRequisitionId);

            if (selectRequisition) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.text_rhs_requisitionNo, 10);

                String requisitionNo = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionNo);
                String requisitionCreatedBy = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionCreatedByUser);
                String requisitionCreatedStore = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionCreatedStore);
                String requisitionCreatedAtDateAndTime = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionCreatedAt);
                String requisitionStatus = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionStatus);
                String requisitionToStore = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionToStore);
                String requisitionType = Cls_Generic_Methods.getTextInElement(oPage_Requisition.text_rhs_requisitionType);

                m_assert.assertTrue(requisitionNo.equalsIgnoreCase(sRequisitionId), "Validated Requisition Received RHS --> Requisition ID <b>: " + requisitionNo + "</b>");
                m_assert.assertTrue(requisitionType.equalsIgnoreCase("Manual"), "Validated Requisition Received RHS --> Type <b>: " + requisitionType + "</b>");
                m_assert.assertTrue(requisitionCreatedBy.equals(expectedLoggedInUser), "Validated Requisition Received RHS --> Requisition Created By User <b>: " + requisitionCreatedBy + "</b>");
                m_assert.assertTrue(requisitionCreatedStore.contains(sStore.split("-")[0]), "Validated Requisition Received RHS --> Requisition Created Store <b>: " + requisitionCreatedStore + "</b>");
                m_assert.assertTrue(compareDateTimesWithTolerance(requisitionCreatedAtDateAndTime, sRequisitionCreatedAt, 0), "Validated Requisition Received RHS --> Requisition Created At <b>: " + requisitionCreatedAtDateAndTime + "</b>");
                m_assert.assertTrue(requisitionStatus.equalsIgnoreCase(getRequisitionReceivedStatus), "Validated Requisition Received RHS --> Status <b>: " + requisitionStatus + " </b>");
                m_assert.assertTrue(requisitionToStore.replaceAll(" ", "").equalsIgnoreCase(sReceivingStore.replaceAll(" ", "")), "Validated Requisition Received RHS --> Requisition To Store <b>: " + requisitionToStore + "</b>");

                //Validate Item Details Table
                validateRequisitionReceivedRHSItemDetails(itemDetails1);
                validateRequisitionReceivedRHSItemDetails(itemDetails2);

            } else {
                m_assert.assertFatal("Unable to select requisition received -> " + sRequisitionId);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Checking available stock and fetching other item details from item master
    private HashMap<String, String> getItemDetails(String itemDescription) {
        String sItemTotalStock = "";
        String itemCode = "";
        String subCategory = "";
        String category = "";
        String unit = "";

        boolean itemFound = false;

        oPage_Master = new Page_Master(driver);
        oPage_ItemMaster = new Page_ItemMaster(driver);
        HashMap<String, String> itemDetails = new HashMap<>();


        try {
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Master");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.input_itemNameSearchInItemMaster, 8);

            //Entering Item Description in search
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


                    if (itemDescriptionName.equals(itemDescription)) {
                        itemFound = true;
                        sItemTotalStock = itemStock;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemData),
                                "<b> " + itemDescription + " </b> is present in Item Master List");
                        m_assert.assertInfo("Available stock of item : " + itemDescription + " --> <b>" + itemStock + "</b>");

                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.text_itemCode, 10);
                        itemCode = Cls_Generic_Methods.getTextInElement(oPage_Master.text_itemCode);
                        try {
                            subCategory = Cls_Generic_Methods.getTextInElement(oPage_Master.text_subCategory);
                        } catch (Exception ignored) {
                        }

                        Cls_Generic_Methods.clickElement(oPage_Master.button_editItem);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_inventoryItemSubPackageItemUnit, 10);
                        unit = Cls_Generic_Methods.getElementAttribute(oPage_ItemMaster.input_inventoryItemSubPackageItemUnit, "value");
                        category = Cls_Generic_Methods.getSelectedValue(oPage_ItemMaster.select_itemCategory);

                        Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_closeItemMasterTemplate);
                        break;
                    }
                }
            }

            if (!itemFound) {
                m_assert.assertFatal("Unable to find item ->" + itemDescription);
            } else {
                itemDetails.put("DESCRIPTION", itemDescription);
                itemDetails.put("CODE", itemCode);
                itemDetails.put("CATEGORY TYPE", category);
                itemDetails.put("SUB CATEGORY", subCategory);
                itemDetails.put("STOCK", sItemTotalStock);
                itemDetails.put("UNIT", unit);
                itemDetails.put("BATCH", "");
                itemDetails.put("GRN NO", "");
                itemDetails.put("GRN TIME", "");
                itemDetails.put("SUB STORE", "");
                itemDetails.put("MRP", "");
                itemDetails.put("RATE", "");
                itemDetails.put("EXPIRY", "");
                itemDetails.put("BALANCE QUANTITY", "");
                itemDetails.put("TOTAL AMOUNT", "");

            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable to get Item Details -" + itemDescription + "  -->" + e);
            e.printStackTrace();
        }

        return itemDetails;
    }

    private void blockLot(String itemDescription) {
        try {
            oPage_Lot = new Page_Lot(driver);
            oPage_Master = new Page_Master(driver);
            oPage_ItemMaster = new Page_ItemMaster(driver);

            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Items", "Lot");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Master.button_refreshItemMasterButton, 10);
            Cls_Generic_Methods.clearValuesInElement(oPage_Master.input_itemNameSearchInItemMaster);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Master.input_itemNameSearchInItemMaster, itemDescription);
            Cls_Generic_Methods.customWait(3);
            oPage_Master.input_itemNameSearchInItemMaster.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(2);

            int rowNo = 0;
            for (WebElement itemData : oPage_ItemMaster.list_itemListInStoreInventory) {

                WebElement itemDetailsInRow = null;
                try {
                    itemDetailsInRow = itemData.findElement(By.xpath("./td/div[1]"));
                } catch (Exception s) {
                    itemDetailsInRow = driver.findElements(By.xpath("//tbody[@id='inventory_table_body']/tr")).get(rowNo).findElement(By.xpath("./td/div[1]"));
                    itemData = driver.findElements(By.xpath("//tbody[@id='inventory_table_body']/tr")).get(rowNo);
                }

                String itemDescriptionNameAndVariantCode = Cls_Generic_Methods.getTextInElement(itemDetailsInRow);
                if (itemDescriptionNameAndVariantCode.contains(itemDescription)) {
                    Cls_Generic_Methods.clickElement(itemData);
                    boolean blockStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.button_blockLot, 10);
                    if (blockStatus) {
                        Cls_Generic_Methods.clickElement(oPage_Lot.button_blockLot);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.header_blockLotHeader, 5);

                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Lot.input_blockLotComment, "AUTO-BLOCK");
                        Cls_Generic_Methods.clickElement(oPage_Lot.button_blockLotConfirmation);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Lot.button_unblockLot, 5);
                    }
                }
                rowNo++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRandomNumber() {
        Random random = new Random();
        String id = String.format("%06d", random.nextInt(1000000));
        return id;
    }

    private void createPurchaseGrn(HashMap<String, String> itemDetails) {
        oPage_Purchase = new Page_Purchase(driver);
        String billNumber = "REQ-TEST-" + getRandomNumber();
        String billType = "Bill";
        String subStore = "Default";
        String purchaseItem = itemDetails.get("DESCRIPTION");
        String expiryDate = "";
        String unitCostWOTax = "150";
        String unitCostWithTax = "";
        String sellingPrice = "400";
        String packageQuantity = itemDetails.get("QUANTITY");
        String grnCreatedAt = "";
        String grn_no = "";
        String batchNo = getRandomNumber();
        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 5);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
            Cls_Generic_Methods.clickElement(oPage_Purchase.button_purchaseNew);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_Vendor_search, 4);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_Vendor_search, vendorName);
            Cls_Generic_Methods.customWait(4);
            oPage_Purchase.input_Vendor_search.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(5);
            for (WebElement eVendor : oPage_Purchase.list_select_vendor) {
                Cls_Generic_Methods.clickElementByJS(driver, eVendor);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean itemClicked = false;
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_searchItem, 20);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_searchItem, purchaseItem);

            oPage_Purchase.input_searchItem.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_itemNameInPurchaseStore, 10);
            for (WebElement eItemName : oPage_Purchase.list_itemNameInPurchaseStore) {
                if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(purchaseItem)) {
                    Cls_Generic_Methods.clickElementByJS(driver, eItemName);
                    itemClicked = true;
                    break;
                }
            }

            if (itemClicked) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewLot, 15);

                Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_batchNumber, batchNo);

                if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.select_subStore)) {
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_subStore, subStore);
                }

                if (Cls_Generic_Methods.isElementDisplayed(oPage_Purchase.input_expiryDate) && Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_expiryDate, "value").isEmpty()) {
                    Cls_Generic_Methods.clickElement(driver, oPage_Purchase.input_expiryDate);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.select_expiryDateYear, 1);
                    String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_Purchase.select_expiryDateYear);
                    int year = Integer.parseInt(currentYear);
                    int newYear = year + 3;
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.select_expiryDateYear, Integer.toString(newYear));
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.select_expiryDateDay);
                    expiryDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_expiryDate, "value");
                }

                Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_unitCostWOTax);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_unitCostWOTax, unitCostWOTax);
                Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_packageQuantity);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_packageQuantity, packageQuantity);
                Cls_Generic_Methods.clearValuesInElement(oPage_Purchase.input_sellingPrice);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_sellingPrice, sellingPrice);
                Cls_Generic_Methods.selectElementByValue(oPage_Purchase.select_unitPriceType, "Unit");
                Cls_Generic_Methods.customWait();
                unitCostWithTax = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_unitCostWithTax, "value");

                Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveLot);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.header_addNewStock, 15);

                Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_transactionNotes, "AUTO");
                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType);

                Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber);
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_deleteOtherCharges);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_saveAddNewLot);
            } else {
                m_assert.assertFatal("Item not selected");
            }


            List<String> purchaseTransactionHeaderList = new ArrayList<String>();

            Cls_Generic_Methods.customWait(15);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Purchase.list_purchaseTransactionHeaderList, 10);
            for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }
            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));
                    if (purchaseStatus.equalsIgnoreCase("open")) {
                        Cls_Generic_Methods.clickElement(row);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_approvePurchaseTransaction, 10);
                        Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_Yes, 6);
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_Yes);
                        m_assert.assertInfo("Purchase Grn created and approved");
                        Cls_Generic_Methods.customWait(4);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_assignBarcodePurchaseTransaction, 15);
                        grn_no = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_transactionID);
                        grnCreatedAt = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_grnCreatedAt);
                        break;
                    }
                }
            }

            itemDetails.put("BATCH", batchNo);
            itemDetails.put("GRN NO", grn_no);
            itemDetails.put("GRN TIME", grnCreatedAt);
            itemDetails.put("SUB STORE", subStore);
            itemDetails.put("MRP", sellingPrice);
            itemDetails.put("RATE", unitCostWOTax);
            itemDetails.put("EXPIRY", expiryDate);
            itemDetails.put("TOTAL AMOUNT", unitCostWithTax);


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to create Purchase GRN" + e);
        }


    }


    private void validateIssueHistory() {


        try {
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_RequisitionReceived.list_issueHistory, 10);

            boolean issueFound = false;
            for (WebElement eIssueHistory : oPage_RequisitionReceived.list_issueHistory) {
                String value = Cls_Generic_Methods.getTextInElement(eIssueHistory);
                String timeInIssueHistory = value.split("\\|")[1].trim();

                if (compareDateTimesWithTolerance(timeInIssueHistory.replaceFirst(" ", "|"), receivedAt, 1)) {
                    issueFound = true;
                    sIssueTransactionId = value.split("\\|")[0].trim();
                    m_assert.assertTrue("Issue Transaction Found -> ID : <b>" + sIssueTransactionId + "</b>");
                    String status = value.split("\\|")[2].trim();
                    m_assert.assertTrue(status.equalsIgnoreCase(currentIssueStatus), "Validated Issue History -> Transaction Status -> " + status);
                    break;
                }
            }
            if (!issueFound) {
                m_assert.assertFatal("Transaction not found in Issue History");
            }

        } catch (Exception e) {
            m_assert.assertFatal("  -->" + e);
            e.printStackTrace();
        }


    }

    private void validateIssueHistoryTable(HashMap<String, String> itemDetails, String issueQuantity) {

        try {
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_RequisitionReceived.list_issueHistory, 10);
            for (WebElement eIssueHistory : oPage_RequisitionReceived.list_issueHistory) {
                String value = Cls_Generic_Methods.getTextInElement(eIssueHistory);
                String timeInIssueHistory = value.split("\\|")[1].trim();

                if (compareDateTimesWithTolerance(timeInIssueHistory.replaceFirst(" ", "|"), receivedAt, 1)) {
                    sIssueTransactionId = value.split("\\|")[0].trim();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(eIssueHistory), "Clicked Issue Transaction : " + sIssueTransactionId);

                    WebElement table = eIssueHistory.findElement(By.xpath("./ancestor::div[@class='panel-heading']/following-sibling::div//table"));
                    String itemDescription = itemDetails.get("DESCRIPTION");

                    ArrayList<String> headerValue = new ArrayList<>();
                    for (WebElement header : table.findElements(By.xpath(".//th"))) {
                        headerValue.add(Cls_Generic_Methods.getTextInElement(header));
                    }

                    for (WebElement row : table.findElements(By.xpath(".//tbody/tr"))) {

                        String description = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Description")));
                        String batchNo = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Batch No.")));
                        String expiry = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Expiry")));
                        String quantity = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Quantity")));
                        String amount = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Total Price")));

                        if (description.contains(itemDescription)) {
                            m_assert.assertInfo("Validated Issue History -> Item found : " + description);
                            m_assert.assertTrue(batchNo.equals(itemDetails.get("BATCH")), "Validated Issue History -> Item(" + description + ") Batch No : " + batchNo);
                            if (!itemDetails.get("EXPIRY").isEmpty()) {
                                m_assert.assertTrue(CommonActions.getRequiredFormattedDateTime("yyyy-mm-dd", "dd/mm/yyyy", expiry).equals(itemDetails.get("EXPIRY")), "Validated Issue History -> Item(" + description + ") Expiry Date : " + expiry);
                            }
                            m_assert.assertTrue(CommonActions.convertStringToDouble(quantity) == CommonActions.convertStringToDouble(issueQuantity), "Validated Issue History -> Item(" + description + ") Issue Quantity : " + quantity);

                            double dAmount = CommonActions.convertStringToDouble(itemDetails.get("TOTAL AMOUNT")) * CommonActions.convertStringToDouble(issueQuantity);
                            m_assert.assertTrue(CommonActions.convertStringToDouble(amount) == dAmount, "Validated Requisition Received Issue Items -> Item(" + description + ") Amount : " + amount);
                            Cls_Generic_Methods.clickElement(eIssueHistory);
                            break;
                        }
                    }

                    break;
                }
            }


        } catch (Exception e) {
            m_assert.assertFatal("  -->" + e);
            e.printStackTrace();
        }


    }

    private void openRequisitionPage() {
        try {
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.logo_store)) {
                CommonActions.selectStoreOnApp(sStore);
                Cls_Generic_Methods.switchToOtherTab();
            } else {
                Cls_Generic_Methods.driverRefresh();
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openRequisitionReceivedPage() {
        try {
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_Requisition.logo_store)) {
                CommonActions.selectStoreOnApp(sReceivingStore);
                Cls_Generic_Methods.switchToOtherTab();
            } else {
                Cls_Generic_Methods.driverRefresh();
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 8);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();

            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_RequisitionReceived.list_filterPeriodType, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean selectByOptions(WebElement selectElement, String optionToSelect) {
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

    private static boolean compareDateTimesWithTolerance(String dateTime1, String dateTime2, int minuteTolerance) {
        dateTime1 = dateTime1.replaceAll(" ", "").replaceAll("/", "-");
        dateTime2 = dateTime2.replaceAll(" ", "").replaceAll("/", "-");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy|hh:mma");

        LocalDateTime dt1 = LocalDateTime.parse(dateTime1, formatter);
        LocalDateTime dt2 = LocalDateTime.parse(dateTime2, formatter);

        long differenceInMinutes = Math.abs(ChronoUnit.MINUTES.between(dt1, dt2));

        return differenceInMinutes <= minuteTolerance;
    }

    private String getCurrentDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mm a");
        Date date = new Date();
        //  07-06-2023 | 01:09 PM
        return dateFormat.format(date);
    }

    private void selectItemFromLeftPanelCreateRequisition(HashMap<String, String> itemDetails) {
        try {
            String itemDescription = itemDetails.get("DESCRIPTION");

            Cls_Generic_Methods.clearValuesInElement(oPage_Requisition.input_searchMedicineName);
            Cls_Generic_Methods.clickElement(oPage_Requisition.input_searchMedicineName);
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_searchMedicineName, itemDescription), "Entered " + itemDescription + " in Item Search");
            Cls_Generic_Methods.customWait(5);

            for (WebElement row : oPage_Requisition.list_namesOfMedicinesOnLeftInSearchResultPO) {
                int rowNo = oPage_Requisition.list_namesOfMedicinesOnLeftInSearchResultPO.indexOf(row);
                String itemValue = Cls_Generic_Methods.getTextInElement(row);

                if (itemValue.equalsIgnoreCase(itemDescription)) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, row), "Item found : <b>" + itemDescription + "</b> and selected");
                    String itemCodeInLeftRow = Cls_Generic_Methods.getTextInElement(oPage_Requisition.list_itemCodeOnLeftInSearchResultPO.get(rowNo));
                    String categoryInLeftRow = Cls_Generic_Methods.getTextInElement(oPage_Requisition.list_itemCategoryOnLeftInSearchResultPO.get(rowNo));
                    String stockInLeftRow = Cls_Generic_Methods.getTextInElement(oPage_Requisition.list_stockOnLeftInSearchResultPO.get(rowNo));

                    m_assert.assertTrue(itemCodeInLeftRow.contains(itemDetails.get("CODE")), "Validate Item Search List -> Item Code : " + itemCodeInLeftRow);
                    m_assert.assertTrue(categoryInLeftRow.equalsIgnoreCase(itemDetails.get("CATEGORY TYPE")), "Validate Item Search List -> Category : " + categoryInLeftRow);
                    m_assert.assertTrue(CommonActions.convertStringToDouble(stockInLeftRow) == CommonActions.convertStringToDouble(itemDetails.get("STOCK")), "Validate Item Search List -> Item Available Stock : " + stockInLeftRow);
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select item from left panel " + e);
            e.printStackTrace();
        }
    }

    private void validateRequisitionRHSItemDetails(HashMap<String, String> itemDetails) {
        try {
            ArrayList<String> headerValue = new ArrayList<>();
            String itemDescription = itemDetails.get("DESCRIPTION");

            for (WebElement eHeader : oPage_Requisition.list_textRHSItemDetailsTableHeader) {
                headerValue.add(Cls_Generic_Methods.getTextInElement(eHeader));
            }

            boolean status = false;
            for (WebElement row : oPage_Requisition.list_textRHSItemDetailsTableRow) {
                String descriptionTable = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Description")));
                String categoryTable = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Category")));
                String quantityTable = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Quantity")));
                String remarkTable = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Remark")));

                if (descriptionTable.contains(itemDescription)) {
                    status = true;
                    m_assert.assertTrue(categoryTable.equalsIgnoreCase(itemDetails.get("CATEGORY TYPE")), "Validated RHS Item Details -> Item(" + itemDescription + ") Category : <b>" + categoryTable + "</b>");
                    m_assert.assertTrue(quantityTable.equals(itemDetails.get("QUANTITY")), "Validated RHS Item Details -> Item(" + itemDescription + ") Quantity : <b>" + quantityTable + "</b>");
                    m_assert.assertTrue(remarkTable.equals("TEST"), "Validated RHS Item Details -> Item(" + itemDescription + ") Remark : <b>" + remarkTable + "</b>");
                    break;
                }
            }

            m_assert.assertTrue(status, "Validated RHS Item Details -> Item (" + itemDescription + ") found");

        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate rhs item details " + e);
            e.printStackTrace();
        }
    }

    private void validateRequisitionReceivedRHSItemDetails(HashMap<String, String> itemDetails) {
        try {
            ArrayList<String> headerValue = new ArrayList<>();
            String itemDescription = itemDetails.get("DESCRIPTION");

            for (WebElement eHeader : oPage_Requisition.list_textRHSItemDetailsTableHeader) {
                headerValue.add(Cls_Generic_Methods.getTextInElement(eHeader));
            }

            boolean status = false;
            for (WebElement row : oPage_Requisition.list_textRHSItemDetailsTableRow) {
                String descriptionTable = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Description")));
                String categoryTable = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Category")));
                String quantityTable = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Quantity")));
                String remQuantity = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Rem.Quantity")));

                if (descriptionTable.contains(itemDescription)) {
                    status = true;
                    m_assert.assertTrue(categoryTable.equalsIgnoreCase(itemDetails.get("CATEGORY TYPE")), "Validated RHS Item Details -> Item(" + itemDescription + ") Category : <b>" + categoryTable + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(quantityTable) == CommonActions.convertStringToDouble(itemDetails.get("QUANTITY")), "Validated RHS Item Details -> Item(" + itemDescription + ") Quantity : <b>" + quantityTable + "</b>");
                    m_assert.assertTrue(CommonActions.convertStringToDouble(remQuantity) == CommonActions.convertStringToDouble(itemDetails.get("REMAINING QUANTITY")), "Validated RHS Item Details -> Item(" + itemDescription + ") Remaining Quantity : <b>" + remQuantity + "</b>");
                    break;
                }
            }

            m_assert.assertTrue(status, "Validated RHS Item Details -> Item (" + itemDescription + ") found");

        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate rhs item details " + e);
            e.printStackTrace();
        }
    }

    private void validateRequisitionViewOrderItemDetails(HashMap<String, String> itemDetails) {
        try {
            ArrayList<String> headerValue = new ArrayList<>();
            String itemDescription = itemDetails.get("DESCRIPTION");

            for (WebElement eHeader : oPage_Requisition.list_textViewOrderItemDetailsTableHeader) {
                headerValue.add(Cls_Generic_Methods.getTextInElement(eHeader));
            }

            boolean status = false;
            for (WebElement row : oPage_Requisition.list_textViewOrderItemDetailsTableRow) {
                String descriptionTable = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Description")));
                String categoryTable = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Category")));
                String quantityTable = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Quantity")));
                String remarkTable = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Remark")));

                if (descriptionTable.contains(itemDescription)) {
                    status = true;
                    m_assert.assertTrue(categoryTable.equalsIgnoreCase(itemDetails.get("CATEGORY TYPE")), "Validated View Order Item Details -> Item(" + itemDescription + ") Category : <b>" + categoryTable + "</b>");
                    m_assert.assertTrue(quantityTable.equals(itemDetails.get("QUANTITY")), "Validated View Order Item Details -> Item(" + itemDescription + ") Quantity : <b>" + quantityTable + "</b>");
                    m_assert.assertTrue(remarkTable.equals("TEST"), "Validated View Order Item Details -> Item(" + itemDescription + ") Remark : <b>" + remarkTable + "</b>");
                    break;
                }
            }

            m_assert.assertTrue(status, "Validated View Order Item Details -> Item (" + itemDescription + ") found");

        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate View Order item details " + e);
            e.printStackTrace();
        }
    }

    private void validateAndFillItemDetails(HashMap<String, String> itemDetails) {

        try {
            String itemDescription = itemDetails.get("DESCRIPTION");
            double unit = CommonActions.convertStringToDouble(itemDetails.get("UNIT"));
            String qty = String.valueOf(unit * 5);

            ArrayList<String> headerValue = new ArrayList<>();
            for (WebElement eHeader : oPage_Requisition.list_tableHeadCreateRequisition) {
                String value = Cls_Generic_Methods.getTextInElement(eHeader);
                headerValue.add(value);
            }

            for (WebElement row : oPage_Requisition.list_tableRowCreateRequisition) {
                int rowNo = oPage_Requisition.list_tableRowCreateRequisition.indexOf(row);
                String description = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Description")));
                String category = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Category")));
                String subCategory = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Sub-Category")));
                String code = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Code")));

                if (description.equals(itemDescription)) {
                    m_assert.assertTrue("Validated selected item -> Description : </b>" + description + "</b>");
                    m_assert.assertTrue(category.equalsIgnoreCase(itemDetails.get("CATEGORY TYPE")), "Validated displayed Category : </b>" + category + "</b>");
                    m_assert.assertTrue(subCategory.equalsIgnoreCase(itemDetails.get("SUB CATEGORY")), "Validated displayed Sub Category : </b>" + subCategory + "</b>");
                    m_assert.assertTrue(code.contains(itemDetails.get("CODE")), "Validated displayed Code : </b>" + code + "</b>");

                    if (unit > 2) {
                        //Enter Lesser that unit value in qty
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Quantity")).findElement(By.xpath(".//input")), "1"), "Entered lesser than unit value in Quantity");
                        Cls_Generic_Methods.clickElement(oPage_Requisition.input_orderNote);
                        m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.label_quantityError.get(rowNo), 10), "Validated Displayed Error Message -> " + Cls_Generic_Methods.getTextInElement(oPage_Requisition.label_quantityError.get(rowNo)));
                    }

                    Cls_Generic_Methods.clearValuesInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Quantity")).findElement(By.xpath(".//input")));
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Quantity")).findElement(By.xpath(".//input")), qty), "Entered <b>" + qty + "</b> in Quantity");
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Remark")).findElement(By.xpath(".//input")), "TEST"), "Entered <b>TEST</b> in Remark");
                    itemDetails.put("QUANTITY", qty);
                    itemDetails.put("REMAINING QUANTITY", qty);
                    itemDetails.put("BALANCE QUANTITY", qty);
                    break;
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate selected item ->" + e);
            e.printStackTrace();
        }

    }

    private void validateFilledItemDetails(HashMap<String, String> itemDetails) {

        try {
            String itemDescription = itemDetails.get("DESCRIPTION");


            ArrayList<String> headerValue = new ArrayList<>();
            for (WebElement eHeader : oPage_Requisition.list_tableHeadCreateRequisition) {
                String value = Cls_Generic_Methods.getTextInElement(eHeader);
                headerValue.add(value);
            }

            for (WebElement row : oPage_Requisition.list_tableRowCreateRequisition) {

                String description = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Description")));
                String category = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Category")));
                String subCategory = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Sub-Category")));
                String code = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Code")));

                if (description.equals(itemDescription)) {
                    m_assert.assertTrue("Validated selected item -> Description : </b>" + description + "</b>");
                    m_assert.assertTrue(category.equalsIgnoreCase(itemDetails.get("CATEGORY TYPE")), "Validated displayed Category : </b>" + category + "</b>");
                    m_assert.assertTrue(subCategory.equalsIgnoreCase(itemDetails.get("SUB CATEGORY")), "Validated displayed Sub Category : </b>" + subCategory + "</b>");
                    m_assert.assertTrue(code.contains(itemDetails.get("CODE")), "Validated displayed Code : </b>" + code + "</b>");

                    String quantity = Cls_Generic_Methods.getElementAttribute(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Quantity")).findElement(By.xpath(".//input")), "value");
                    m_assert.assertInfo(quantity.equals(itemDetails.get("QUANTITY")), "Displayed Quantity = <b>" + quantity + "</b>");
                    String remark = Cls_Generic_Methods.getElementAttribute(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Remark")).findElement(By.xpath(".//input")), "value");
                    m_assert.assertInfo(remark.equalsIgnoreCase("TEST"), "Displayed Remark <b>" + remark + "</b>");
                    if (remark.isEmpty()) {
                        Cls_Generic_Methods.sendKeysIntoElement(row.findElements(By.xpath("./td")).get(headerValue.indexOf("Remark")).findElement(By.xpath(".//input")), "TEST");
                    }
                    break;
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate filled item ->" + e);
            e.printStackTrace();
        }

    }

    private boolean selectRequisitionOrder(String... requisitionNo) {

        boolean flag = false;
        String selectPORow = null;
        String requisitionCreatedAtInRow = "";
        String requisitionNoInRow = "";
        String requisitionStatus = "";
        String requisitionStore = "";
        String requisitionType = "";
        try {

            if (requisitionNo.length > 0) {
                selectPORow = requisitionNo[0];
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 10);


            for (WebElement row : oPage_Requisition.list_dateTimeOfRequisition) {
                int rowNo = oPage_Requisition.list_dateTimeOfRequisition.indexOf(row);
                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    requisitionCreatedAtInRow = Cls_Generic_Methods.getTextInElement(row).split("\n")[0];
                    requisitionNoInRow = Cls_Generic_Methods.getTextInElement(row).split("\n")[1];
                    requisitionStatus = Cls_Generic_Methods.getTextInElement(oPage_Requisition.list_statusOfRequisition.get(rowNo)).split("\\|")[0].trim();
                    requisitionType = Cls_Generic_Methods.getTextInElement(oPage_Requisition.list_statusOfRequisition.get(rowNo)).split("\\|")[1].trim();

                    requisitionStore = Cls_Generic_Methods.getTextInElement(oPage_Requisition.list_receivingStoreRequisition.get(rowNo));

                    if (requisitionNo.length > 0) {
                        if (requisitionNoInRow.equals(selectPORow)) {
                            Cls_Generic_Methods.clickElement(row);
                            flag = true;
                            sRequisitionId = requisitionNoInRow;
                            m_assert.assertInfo("Selected Requisition Order -> <b>Requisition No :</b> " + requisitionNoInRow + " | <b>Status :</b> " + requisitionStatus);
                            break;
                        }
                    } else {
                        Cls_Generic_Methods.clickElement(row);
                        flag = true;
                        sRequisitionId = requisitionNoInRow;
                        m_assert.assertInfo("Selected Requisition Order -> <b>Requisition No :</b> " + requisitionNoInRow + " | <b>Status :</b> " + requisitionStatus);
                        break;
                    }
                }
            }

            getRequisitionCreatedAt = requisitionCreatedAtInRow;
            getRequisitionStatus = requisitionStatus;
            getReceivingStore = requisitionStore;
            getRequisitionType = requisitionType;

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find Requisition Order " + e);
        }
        return flag;
    }

    public boolean selectRequisitionReceivedOrder(String... requisitionNo) {

        boolean flag = false;
        String selectPORow = null;
        String requisitionCreatedAtInRow = "";
        String requisitionNoInRow = "";
        String requisitionReceivedStatus = "";
        String requestedStore = "";
        String requisitionType = "";
        try {

            if (requisitionNo.length > 0) {
                selectPORow = requisitionNo[0];
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.button_newRequisition, 10);


            for (WebElement row : oPage_Requisition.list_dateTimeOfRequisition) {
                int rowNo = oPage_Requisition.list_dateTimeOfRequisition.indexOf(row);
                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    requisitionCreatedAtInRow = Cls_Generic_Methods.getTextInElement(row).split("\n")[0];
                    requisitionNoInRow = Cls_Generic_Methods.getTextInElement(row).split("\n")[1];
                    requisitionReceivedStatus = Cls_Generic_Methods.getTextInElement(oPage_Requisition.list_statusOfRequisition.get(rowNo)).split("\\|")[0].trim();
                    requisitionType = Cls_Generic_Methods.getTextInElement(oPage_Requisition.list_statusOfRequisition.get(rowNo)).split("\\|")[1].trim();
                    requestedStore = Cls_Generic_Methods.getTextInElement(oPage_Requisition.list_receivingStoreRequisition.get(rowNo));

                    if (requisitionNo.length > 0) {
                        if (requisitionNoInRow.equals(selectPORow)) {
                            Cls_Generic_Methods.clickElement(row);
                            flag = true;
                            sRequisitionId = requisitionNoInRow;
                            m_assert.assertInfo("Selected Requisition Order -> <b>Requisition No :</b> " + requisitionNoInRow + " | <b>Status :</b> " + requisitionReceivedStatus);
                            break;
                        }
                    } else {
                        Cls_Generic_Methods.clickElement(row);
                        flag = true;
                        sRequisitionId = requisitionNoInRow;
                        m_assert.assertInfo("Selected Requisition Order -> <b>Requisition No :</b> " + requisitionNoInRow + " | <b>Status :</b> " + requisitionReceivedStatus);
                    }
                }
            }

            getRequisitionCreatedAt = requisitionCreatedAtInRow;
            getRequisitionReceivedStatus = requisitionReceivedStatus;
            getRequestedStore = requestedStore;
            getRequisitionType = requisitionType;

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find Requisition Received Order " + e);
        }
        return flag;
    }

    private void validateTimePeriod() {
        try {
            m_assert.assertFalse(selectTimePeriod("Yesterday"), "Validated ->Time Filter - Yesterday");
            m_assert.assertTrue(selectTimePeriod("This Week"), "Validated -->Time Filter - This Week");
            m_assert.assertTrue(selectTimePeriod("This Month"), "Validated -->Time Filter - This Month");
            m_assert.assertTrue(selectTimePeriod("This Quarter"), "Validated -->Time Filter - This Quarter");
            m_assert.assertTrue(selectTimePeriod("This Year"), "Validated -->Time Filter - This Year");
            m_assert.assertFalse(selectTimePeriod("Previous Week"), "Validated -->Time Filter - Previous Week");
            m_assert.assertFalse(selectTimePeriod("Previous Month"), "Validated -->Time Filter - Previous Month");
            m_assert.assertFalse(selectTimePeriod("Previous Quarter"), "Validated -->Time Filter - Previous Quarter");
            m_assert.assertFalse(selectTimePeriod("Previous Year"), "Validated -->Time Filter - Previous Year");
            m_assert.assertTrue(selectTimePeriod("Today"), "Validated -->Time Filter - Today");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean selectTimePeriod(String periodToSelect) {
        boolean status = false;
        try {

            Cls_Generic_Methods.clickElement(driver, oPage_Requisition.button_dropDownTimeFilter);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(driver.findElement(By.xpath("//li/a[contains(text(),'" + periodToSelect + "')]")));
            Cls_Generic_Methods.customWait(3);

            for (WebElement eReqNo : oPage_Requisition.list_dateTimeOfRequisition) {
                String requisitionNoInRow = Cls_Generic_Methods.getTextInElement(eReqNo);

                if (requisitionNoInRow.contains(sRequisitionId)) {
                    status = true;
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select " + periodToSelect);
            e.printStackTrace();
        }
        return status;
    }

    private void approveIssueTransaction() {
        oPage_Transfer = new Page_Transfer(driver);
        boolean status = false;
        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.customWait(4);

            for (WebElement e : oPage_Transfer.list_transferTransactionRow) {
                String transferId = Cls_Generic_Methods.getTextInElement(e.findElement(By.xpath("./child::div[1]")));

                if (transferId.equals(sIssueTransactionId)) {
                    status = true;
                    Cls_Generic_Methods.clickElement(e);
                    m_assert.assertInfo("Issue Transaction Found -> ID : <b>" + transferId + "</b> ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransferTransaction, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransferTransaction), "Approved Transfer Transaction");
                    Cls_Generic_Methods.customWait();
                    currentIssueStatus = "Approved";
                    break;
                }
            }

            if (!status) {
                m_assert.assertFatal("Issue Transaction Not Found ->" + sIssueTransactionId);
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to Approve Issue Transaction");
            e.printStackTrace();
        }
    }

    private void reStockIssueTransaction() {
        oPage_Transfer = new Page_Transfer(driver);
        boolean status = false;
        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
            Cls_Generic_Methods.customWait(4);

            for (WebElement e : oPage_Transfer.list_transferTransactionRow) {
                String transferId = Cls_Generic_Methods.getTextInElement(e.findElement(By.xpath("./child::div[1]")));

                if (transferId.equals(sIssueTransactionId)) {
                    status = true;
                    Cls_Generic_Methods.clickElement(e);
                    m_assert.assertInfo("Issue Transaction Found -> ID : <b>" + transferId + "</b> ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_restock, 10);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Transfer.button_restock), "Clicked <b>Re Stock</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_confirmRestock, 5);
                    Cls_Generic_Methods.clickElement(oPage_Transfer.button_confirmRestock);
                    break;
                }
            }

            if (!status) {
                m_assert.assertFatal("Issue Transaction Not Found ->" + sIssueTransactionId);
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to Restock Issue Transaction");
            e.printStackTrace();
        }
    }

    private void getStoreAndVendorDetails(String sStoreName) {
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
                        } catch (org.openqa.selenium.NoSuchElementException e) {
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

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to get Store and Vendor Details from Organisation Setting " + e);
        }
    }
}
