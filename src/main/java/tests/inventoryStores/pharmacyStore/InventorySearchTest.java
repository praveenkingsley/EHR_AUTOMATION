package tests.inventoryStores.pharmacyStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.python.antlr.ast.Str;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.templates.Page_InventorySearchCommonElements;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.PharmacyStore.Order.Page_Indent;
import pages.store.PharmacyStore.Order.Page_PurchaseOrder;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Order.Page_RequisitionReceived;
import pages.store.PharmacyStore.Page_TaxInvoiceDeliveryChallan;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_PurchaseReturn;
import pages.store.PharmacyStore.Transaction.Page_Sale;
import pages.store.PharmacyStore.Transaction.Page_Transfer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static pages.commonElements.CommonActions.selectOptionAndSearch;

public class InventorySearchTest extends TestBase {
    public static String sSTORE = "Pharmacy automation- Pharmacy";
    public static String sCENTRAL_HUB = "CENTRAL HUB 01- Central Hub";
    public String sReceivingStore = "OpticalStore- Optical";
    String sRequisitionType = "Normal";
    String sItemName = "Transfer Item 2";
    String requisitionOrderTime = "",requisitionOrderDate="";
    String reqNum = "",oldReceiveNo;
    String incorrectReqNumber = "Req-1234563569";
    String requisitionSearchTypeList [] = {"Requisition No","Item Description"};
    String requisitionReceivedSearchTypeList [] = {"Requisition No","Item Description","Issue No."};
    String purchaseSearchTypeList [] = {"PO Number","Indent Number","GRN No","Item Description"};
    String indentSearchTypeList [] = {"Indent No.","Item Description","PO No."};
    String sIndentNoOnUI = null;
    String grnNumber ="",sTransferId,oldTransferId ,taxInvoiceId,deliveryChallanId;
    String poNumber = "";
    String oldPO,oldIndent,oldGrn,oldDescription,issueNumber,oldReq,oldIssueNo;
    String issueQty ="3";


    @Test(enabled = true, description = "Desc")
    public void validateSearchFunctionalityInRequisition(){

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        boolean bRequisitionOrderFound = false;


        try{
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sSTORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try{

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                createRequisitionInStore();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase("Requisition No."),
                        " By Default Search Type Selection Dropdown Displayed correctly as : Requisition No.");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox),
                        " Search Button Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"placeholder")
                                .equalsIgnoreCase("Search By Requisition No."),
                        " Input Search Box Place holder for selected search type Displayed correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");

                boolean searchResultByNumber = CommonActions.selectOptionAndSearch("Requisition No",reqNum);
                Cls_Generic_Methods.customWait();
                if(searchResultByNumber) {
                    bRequisitionOrderFound = getSearchedRecord(oPage_Requisition.list_dateTimeOfRequisition,reqNum);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Requisition No Worked correctly as Requisition order found in the requisition page for number: "+reqNum);
                }

                boolean searchResultByDescription = CommonActions.selectOptionAndSearch("Item Description",sItemName);
                Cls_Generic_Methods.customWait();
                if(searchResultByDescription) {
                    bRequisitionOrderFound = getSearchedRecordAndValidate(oPage_Requisition.list_dateTimeOfRequisition,sItemName);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Item Description Worked correctly as Requisition order found in the requisition page");
                }

                String oldRequisition = getOldRequisition(oPage_Requisition.list_dateTimeOfRequisition);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);
                boolean searchResultByOldNumber = selectOptionAndSearch("Requisition No",oldRequisition);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldNumber) {
                    bRequisitionOrderFound = getSearchedRecord(oPage_Requisition.list_dateTimeOfRequisition,oldRequisition);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Old Requisition Worked correctly as Requisition order found in the requisition page for number: "+reqNum);
                }

                boolean searchResultByWrongNumber = selectOptionAndSearch("Requisition No",incorrectReqNumber);
                m_assert.assertFalse(searchResultByWrongNumber," Requisition Search With Incorrect Number Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(incorrectReqNumber),
                        " Input Search Box is not empty by for selected search type Displayed correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty");


                boolean searchResultByWrongDescription = selectOptionAndSearch("Item Description","incorrectReqNumber");
                m_assert.assertFalse(searchResultByWrongDescription," Requisition Search With Incorrect Number Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase("incorrectReqNumber"),
                        " Input Search Box is not empty by for selected search type Displayed correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty");
                boolean selectOption = CommonActions.selectOption("Requisition No");
                m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase("Requisition No."),
                        " Search Type Selected  as : Requisition No.");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(requisitionSearchTypeList[index])){
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
    @Test(enabled = true, description = "Desc")
    public void validateSearchFunctionalityInPurchaseOrder(){

        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);

        boolean bPurchaseOrderFound = false;


        try{
            createPOUsingIndentInStore();

            try{

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Purchase");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(purchaseSearchTypeList[0]),
                        " By Default Search Type Selection Dropdown Displayed correctly as :"+purchaseSearchTypeList[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"placeholder")
                                .contains("Search By "+purchaseSearchTypeList[0]),
                        " Input Search Box Place holder for selected search type Displayed correctly");

                boolean searchResultByPONumber = CommonActions.selectOptionAndSearch(purchaseSearchTypeList[0],poNumber);
                Cls_Generic_Methods.customWait();
                if(searchResultByPONumber) {
                    bPurchaseOrderFound = getSearchedRecord(oPage_Indent.list_dateTimeOfIndentOrder,poNumber);
                    m_assert.assertTrue(bPurchaseOrderFound,
                            "Search By PO Number Worked correctly as Purchase Order found " +
                                    "in the page for PO Number: "+poNumber);
                }
                boolean searchResultByIndent = CommonActions.selectOptionAndSearch(purchaseSearchTypeList[1],sIndentNoOnUI);
                Cls_Generic_Methods.customWait();
                if(searchResultByIndent) {
                    bPurchaseOrderFound = getSearchedRecordAndValidateInView(oPage_Indent.list_dateTimeOfIndentOrder,oPage_PurchaseOrder.text_rhsIndentNumber,sIndentNoOnUI);
                    m_assert.assertTrue(bPurchaseOrderFound,
                            "Search By Indent No Worked correctly as Purchase Order found " +
                                    "in the Order page for Indent Number: "+sIndentNoOnUI);
                }
                boolean searchResultByGrnNo = CommonActions.selectOptionAndSearch(purchaseSearchTypeList[2],grnNumber);
                Cls_Generic_Methods.customWait();
                if(searchResultByGrnNo) {
                    bPurchaseOrderFound = getSearchedRecordAndValidateInView(oPage_Indent.list_dateTimeOfIndentOrder,
                            oPage_PurchaseOrder.list_transactionAgainstOrder.get(0),grnNumber);
                    m_assert.assertTrue(bPurchaseOrderFound,
                            "Search By GRN No Worked correctly as Purchase Order found " +
                                    "in the Order page for Number: "+grnNumber);
                }
                boolean searchResultByDescription = CommonActions.selectOptionAndSearch(purchaseSearchTypeList[3],sItemName);
                Cls_Generic_Methods.customWait();
                if(searchResultByDescription) {
                    bPurchaseOrderFound = getSearchedRecordAndValidateInView(oPage_Indent.list_dateTimeOfIndentOrder,oPage_PurchaseOrder.list_itemNameOnRequisition.get(0),sItemName);
                    m_assert.assertTrue(bPurchaseOrderFound,
                            "Search By Item Description Worked correctly as Order found in the Purchase page");
                }

                getOldRecord(oPage_Indent.list_dateTimeOfIndentOrder);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                boolean searchResultByOldPONumber = CommonActions.selectOptionAndSearch(purchaseSearchTypeList[0],oldPO);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldPONumber) {
                    bPurchaseOrderFound = getSearchedRecord(oPage_Indent.list_dateTimeOfIndentOrder,oldPO);
                    m_assert.assertTrue(bPurchaseOrderFound,
                            "Search By Old PO Number Worked correctly as Purchase Order found " +
                                    "in the page for PO Number: "+oldPO);
                }

                boolean searchResultByOldIndent = CommonActions.selectOptionAndSearch(purchaseSearchTypeList[1],oldIndent);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldIndent) {
                    bPurchaseOrderFound = getSearchedRecordAndValidateInView(oPage_Indent.list_dateTimeOfIndentOrder,oPage_PurchaseOrder.text_rhsIndentNumber,oldIndent);
                    m_assert.assertTrue(bPurchaseOrderFound,
                            "Search By Old Indent No Worked correctly as Purchase Order found " +
                                    "in the Order page for Indent Number: "+oldIndent);
                }

                boolean searchResultByOldGrnNo = CommonActions.selectOptionAndSearch(purchaseSearchTypeList[2],oldGrn);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldGrnNo) {
                    bPurchaseOrderFound = getSearchedRecordAndValidateInView(oPage_Indent.list_dateTimeOfIndentOrder,
                            oPage_PurchaseOrder.list_transactionAgainstOrder.get(0),oldGrn);
                    m_assert.assertTrue(bPurchaseOrderFound,
                            "Search By Old GRN No Worked correctly as Purchase Order found " +
                                    "in the Order page for Number: "+oldGrn);
                }

                boolean searchResultByOldDescription = CommonActions.selectOptionAndSearch(purchaseSearchTypeList[3],oldDescription);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldDescription) {
                    bPurchaseOrderFound = getSearchedRecordAndValidateInView(oPage_Indent.list_dateTimeOfIndentOrder,oPage_PurchaseOrder.list_itemNameOnRequisition.get(0),oldDescription);
                    m_assert.assertTrue(bPurchaseOrderFound,
                            "Search By Old Item Description Worked correctly as Order found in the Purchase page");
                }


                boolean searchResultByWrongPO = CommonActions.selectOptionAndSearch(purchaseSearchTypeList[0],oldPO+"23");
                m_assert.assertFalse(searchResultByWrongPO," Transaction Search With Incorrect PO Number Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");

                boolean searchResultByWrongIndent = CommonActions.selectOptionAndSearch(purchaseSearchTypeList[1],oldIndent);
                m_assert.assertFalse(searchResultByWrongIndent," Transaction Search With Incorrect Indent Number Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");

                boolean searchResultByWrongDescription = CommonActions.selectOptionAndSearch(purchaseSearchTypeList[3],oldDescription);
                m_assert.assertFalse(searchResultByWrongDescription,"  Search With Incorrect Description Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");

                boolean searchResultByWrongGrn = CommonActions.selectOptionAndSearch(purchaseSearchTypeList[2],oldGrn);
                m_assert.assertFalse(searchResultByWrongGrn,"  Search With Incorrect GRN Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty as clear button is working correctly");
                boolean selectOption = CommonActions.selectOption(purchaseSearchTypeList[0]);
                m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(purchaseSearchTypeList[0]),
                        " Search Type Selected  as : "+purchaseSearchTypeList[0]);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(purchaseSearchTypeList[index])){
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
    @Test(enabled = true, description = "Desc")
    public void validateSearchFunctionalityInRequisitionReceived(){

        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        boolean bRequisitionOrderFound = false;


        try{
            validateRequisitionReceivedAndTransferFromHub();
            try{


                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                createRequisitionInStore();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase("Requisition No."),
                        " By Default Search Type Selection Dropdown Displayed correctly as : Requisition No.");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox),
                        " Search Button Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"placeholder")
                                .equalsIgnoreCase("Search By Requisition No."),
                        " Input Search Box Place holder for selected search type Displayed correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");

                boolean searchResultByNumber = CommonActions.selectOptionAndSearch("Requisition No",reqNum);
                Cls_Generic_Methods.customWait();
                if(searchResultByNumber) {
                    bRequisitionOrderFound = getSearchedRecord(oPage_Requisition.list_dateTimeOfRequisition,reqNum);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Requisition No Worked correctly as Requisition order found in the requisition page for number: "+reqNum);
                }

                boolean searchResultByDescription = CommonActions.selectOptionAndSearch("Item Description",sItemName);
                Cls_Generic_Methods.customWait();
                if(searchResultByDescription) {
                    bRequisitionOrderFound = getSearchedRecordAndValidate(oPage_Requisition.list_dateTimeOfRequisition,sItemName);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Item Description Worked correctly as Requisition order found in the requisition page");
                }

                boolean searchResultByIssueNumber = CommonActions.selectOptionAndSearch("Issue No.",issueNumber);
                Cls_Generic_Methods.customWait();
                if(searchResultByIssueNumber) {
                    bRequisitionOrderFound = getSearchedRecordAndValidateInView(oPage_Requisition.list_dateTimeOfRequisition,
                            oPage_PurchaseOrder.list_transactionAgainstOrder.get(0),issueNumber);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Issue No Worked correctly as Requisition order found in the requisition page for number: "+reqNum);
                }

                getOldRecordRequisitionReceived(oPage_Requisition.list_dateTimeOfRequisition);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                boolean searchResultByOldNumber = CommonActions.selectOptionAndSearch("Requisition No",oldReq);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldNumber) {
                    bRequisitionOrderFound = getSearchedRecord(oPage_Requisition.list_dateTimeOfRequisition,oldReq);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Old Requisition No Worked correctly as Requisition order found in the requisition page for number: "+reqNum);
                }

                boolean searchResultByOldDescription = CommonActions.selectOptionAndSearch("Item Description",oldDescription);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldDescription) {
                    bRequisitionOrderFound = getSearchedRecordAndValidate(oPage_Requisition.list_dateTimeOfRequisition,oldDescription);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Old Item Description Worked correctly as Requisition order found in the requisition page");
                }

                boolean searchResultByOldIssueNumber = CommonActions.selectOptionAndSearch("Issue No.",oldIssueNo);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldIssueNumber) {
                    bRequisitionOrderFound = getSearchedRecordAndValidateInView(oPage_Requisition.list_dateTimeOfRequisition,
                            oPage_PurchaseOrder.list_transactionAgainstOrder.get(0),oldIssueNo);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Old issue No Worked correctly as Requisition order found in the requisition page for number: "+reqNum);
                }

                boolean searchResultByWrongNumber = selectOptionAndSearch("Requisition No",incorrectReqNumber);
                m_assert.assertFalse(searchResultByWrongNumber," Requisition Search With Incorrect Number Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(incorrectReqNumber),
                        " Input Search Box is not empty by for selected search type Displayed correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty");


                boolean searchResultByWrongDescription = selectOptionAndSearch("Item Description","incorrectReqNumber");
                m_assert.assertFalse(searchResultByWrongDescription," Requisition Search With Incorrect Number Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");


                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase("incorrectReqNumber"),
                        " Input Search Box is not empty by for selected search type Displayed correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty");

                boolean searchResultByWrongIssue = CommonActions.selectOptionAndSearch("Issue No.",oldPO+"23");
                m_assert.assertFalse(searchResultByWrongIssue," Transaction Search With Incorrect Issue Number Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");


                boolean selectOption = CommonActions.selectOption("Requisition No");
                m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase("Requisition No."),
                        " Search Type Selected  as : Requisition No.");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(requisitionReceivedSearchTypeList[index])){
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
    @Test(enabled = true, description = "Desc")
    public void validateSearchFunctionalityInIndent(){

        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);

        boolean bRequisitionOrderFound = false;


        try{
            try{

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Indent");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                createRequisitionInStore();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase("Indent No."),
                        " By Default Search Type Selection Dropdown Displayed correctly as : Indent No.");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox),
                        " Search Button Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"placeholder")
                                .equalsIgnoreCase("Search By Indent No."),
                        " Input Search Box Place holder for selected search type Displayed correctly");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");

                boolean searchResultByNumber = CommonActions.selectOptionAndSearch("Indent No.",sIndentNoOnUI);
                Cls_Generic_Methods.customWait();
                if(searchResultByNumber) {
                    bRequisitionOrderFound = getSearchedRecord(oPage_Indent.list_indentNumberOfIndentOrder,sIndentNoOnUI);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Indent No Worked correctly as order found in the Indent page for number: "+sIndentNoOnUI);
                }

                boolean searchResultByDescription = CommonActions.selectOptionAndSearch("Item Description",sItemName);
                Cls_Generic_Methods.customWait();
                if(searchResultByDescription) {
                    bRequisitionOrderFound = getSearchedRecordAndValidate(oPage_Requisition.list_dateTimeOfRequisition,sItemName);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Item Description Worked correctly as order found in the Indent page");
                }

                boolean searchResultByPONumber = CommonActions.selectOptionAndSearch("PO No.",poNumber);
                Cls_Generic_Methods.customWait();
                if(searchResultByPONumber) {
                    bRequisitionOrderFound = getSearchedRecordAndValidateInView(oPage_Requisition.list_dateTimeOfRequisition,
                            oPage_Indent.text_poNumberOrderAgainstIndentRHS,poNumber);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Indent No Worked correctly as order found in the Indent page for number: "+poNumber);
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                boolean searchResultByOldNumber = CommonActions.selectOptionAndSearch("Indent No.",oldIndent);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldNumber) {
                    bRequisitionOrderFound = getSearchedRecord(oPage_Indent.list_indentNumberOfIndentOrder,oldIndent);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Old Indent No Worked correctly as order found in the Indent page for number: "+oldIndent);
                }

                boolean searchResultByOldDescription = CommonActions.selectOptionAndSearch("Item Description",oldDescription);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldDescription) {
                    bRequisitionOrderFound = getSearchedRecordAndValidate(oPage_Requisition.list_dateTimeOfRequisition,oldDescription);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Old Item Description Worked correctly as order found in the Indent page");
                }

                boolean searchResultByOldIssueNumber = CommonActions.selectOptionAndSearch("PO No.",oldPO);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldIssueNumber) {
                    bRequisitionOrderFound = getSearchedRecordAndValidateInView(oPage_Requisition.list_dateTimeOfRequisition,
                            oPage_Indent.text_poNumberOrderAgainstIndentRHS,oldPO);
                    m_assert.assertTrue(bRequisitionOrderFound,
                            "Search By Old PO No Worked correctly as  order found in the Indent page for number: "+oldPO);
                }

                boolean searchResultByWrongNumber = selectOptionAndSearch("Indent No.",incorrectReqNumber);
                m_assert.assertFalse(searchResultByWrongNumber," Indent Search With Incorrect Number Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(incorrectReqNumber),
                        " Input Search Box is not empty by for selected search type Displayed correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty");


                boolean searchResultByWrongDescription = selectOptionAndSearch("Item Description","incorrectReqNumber");
                m_assert.assertFalse(searchResultByWrongDescription," Indent Search With Incorrect Number Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");


                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase("incorrectReqNumber"),
                        " Input Search Box is not empty by for selected search type Displayed correctly");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty");

                boolean searchResultByWrongPO = CommonActions.selectOptionAndSearch("PO No.",oldPO+"23");
                m_assert.assertFalse(searchResultByWrongPO," Transaction Search With Incorrect PO Number Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");


                boolean selectOption = CommonActions.selectOption("Indent No.");
                m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase("Indent No."),
                        " Search Type Selected  as : Indent No.");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(indentSearchTypeList[index])){
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
    @Test(enabled = true, description = "Desc")
    public void validateSearchFunctionalityInTaxInvoiceAndDeliveryChallan(){

        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_TaxInvoiceDeliveryChallan oPage_TaxInvoiceDeliveryChallan = new Page_TaxInvoiceDeliveryChallan(driver);
        String taxInvoiceSearchTypeList [] = {"Transaction ID","Transfer No.","Issue No."};

        try{
            createDirectTransfer();
            getDataFromSetting();
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sSTORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();


            try{
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.tab_taxInvoiceDeliveryChallan), "Selected Option in the Left Panel = Tax Invoice / Delivery Challan");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TaxInvoiceDeliveryChallan.button_new, 5);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase("Transaction ID"),
                        " By Default Search Type Selection Dropdown Displayed correctly as : Transaction ID");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"placeholder")
                                .contains("Search By Transaction ID"),
                        " Input Search Box Place holder for selected search type Displayed correctly");

                boolean searchResultByTaxInvoiceId = CommonActions.selectOptionAndSearch("Transaction ID",taxInvoiceId);
                Cls_Generic_Methods.customWait();
                if(searchResultByTaxInvoiceId) {

                    String txnTypeUI = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_transactionDetailsCreatedList.get(0));
                    String txnIdUI = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedIdList.get(0));
                    m_assert.assertTrue(txnTypeUI.equalsIgnoreCase("Tax Invoice") && txnIdUI.equalsIgnoreCase(taxInvoiceId),
                            "Search By Tax Invoice Number Worked correctly as Order found " +
                                    "in the page for Tax Invoice Number : "+taxInvoiceId);
                }

                boolean searchResultByDeliveryChallanId = CommonActions.selectOptionAndSearch("Transaction ID",deliveryChallanId);
                Cls_Generic_Methods.customWait();
                if(searchResultByDeliveryChallanId) {

                    String txnTypeUI = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_transactionDetailsCreatedList.get(0));
                    String txnIdUI = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedIdList.get(0));
                    m_assert.assertTrue(txnTypeUI.equalsIgnoreCase("Delivery Challan") && txnIdUI.equalsIgnoreCase(deliveryChallanId),
                            "Search By Delivery Challan Number Worked correctly as Order found " +
                                    "in the page for Tax Invoice Number : "+deliveryChallanId);
                }

                boolean searchResultByTransferNo = CommonActions.selectOptionAndSearch("Transfer No.",sTransferId);
                Cls_Generic_Methods.customWait();
                if(searchResultByTransferNo) {
                    m_assert.assertTrue(oPage_Indent.list_dateTimeOfIndentOrder.size()==1,
                            "Search By Transfer Number Worked correctly as Order found " +
                                    "in the Order page for Transfer Number: "+sTransferId);
                }

                boolean searchResultByIssueNo = CommonActions.selectOptionAndSearch("Issue No.",issueNumber);
                Cls_Generic_Methods.customWait();
                if(searchResultByIssueNo) {
                    m_assert.assertTrue(oPage_Indent.list_dateTimeOfIndentOrder.size()==1,
                            "Search By Issue Number Worked correctly as Order found " +
                                    "in the Order page for Transfer Number: "+issueNumber);
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_sortTaxInvoiceAndDeliveryChallan);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_sortByTaxInvoice),"Clicked sort by Tax Invoice");
                Cls_Generic_Methods.customWait();

                String oldTaxInvoiceId =  Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedIdList.get(2));
                Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_sortTaxInvoiceAndDeliveryChallan);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_TaxInvoiceDeliveryChallan.button_sortByDeliveryChallan),"Clicked sort by Delivery Challan");
                Cls_Generic_Methods.customWait();

                String oldDeliveryId =  Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedIdList.get(2));

                boolean searchResultByOldTaxInvoiceId = CommonActions.selectOptionAndSearch("Transaction ID",oldTaxInvoiceId);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldTaxInvoiceId) {

                    String txnTypeUI = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_transactionDetailsCreatedList.get(0));
                    String txnIdUI = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedIdList.get(0));
                    m_assert.assertTrue(txnTypeUI.equalsIgnoreCase("Tax Invoice") && txnIdUI.equalsIgnoreCase(oldTaxInvoiceId),
                            "Search By Old Tax Invoice Number Worked correctly as Order found " +
                                    "in the page for Tax Invoice Number : "+oldTaxInvoiceId);
                }

                boolean searchResultByOldDeliveryChallanId = CommonActions.selectOptionAndSearch("Transaction ID",oldDeliveryId);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldDeliveryChallanId) {

                    String txnTypeUI = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_transactionDetailsCreatedList.get(0));
                    String txnIdUI = Cls_Generic_Methods.getTextInElement(oPage_TaxInvoiceDeliveryChallan.list_transactionCreatedIdList.get(0));
                    m_assert.assertTrue(txnTypeUI.equalsIgnoreCase("Delivery Challan") && txnIdUI.equalsIgnoreCase(oldDeliveryId),
                            "Search By Old Delivery Challan Number Worked correctly as Order found " +
                                    "in the page for Tax Invoice Number : "+oldDeliveryId);
                }

                boolean searchResultByOldTransferNo = CommonActions.selectOptionAndSearch("Transfer No.",oldTransferId);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldTransferNo) {
                    m_assert.assertTrue(oPage_Indent.list_dateTimeOfIndentOrder.size()==1,
                            "Search By Old Transfer Number Worked correctly as Order found " +
                                    "in the Order page for Transfer Number: "+oldTransferId);
                }

                boolean searchResultByOldIssueNo = CommonActions.selectOptionAndSearch("Issue No.",oldIssueNo);
                Cls_Generic_Methods.customWait();
                if(searchResultByOldIssueNo) {
                    m_assert.assertTrue(oPage_Indent.list_dateTimeOfIndentOrder.size()==1,
                            "Search By Old Issue Number Worked correctly as Order found " +
                                    "in the Order page for Transfer Number: "+oldIssueNo);
                }


                boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch("Transaction ID",oldPO+"23");
                m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect Transaction ID Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");

                boolean searchResultByWrongTransferID = CommonActions.selectOptionAndSearch("Transfer No.",sTransferId+"233");
                m_assert.assertFalse(searchResultByWrongTransferID," Transaction Search With Incorrect Transfer Number Working Correct");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                        " Nothing To Display Text Displayed as Result");

                boolean searchResultByWrongIssue = CommonActions.selectOptionAndSearch("Issue No.",issueNumber+"432");
                m_assert.assertFalse(searchResultByWrongIssue,"  Search With Incorrect Issue Number Working Correct");
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
                        " Search Type Selected  as : "+"Transaction ID");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(taxInvoiceSearchTypeList[index])){
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

    @Test(enabled = true, description = "Desc")
    public void validateSearchFunctionalityInTransferIssue(){

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        String transferSearchTypeList [] = {"Transfer","Item Description","Issue No","Requisition No","Receive No"};


        try{
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sSTORE);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(transferSearchTypeList[0]),
                        " By Default Search Type Selection Dropdown Displayed correctly as :" + transferSearchTypeList[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "placeholder")
                                .contains("Search By " + transferSearchTypeList[0]),
                        " Input Search Box Place holder for selected search type Displayed correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                String searchValueList[] = {sTransferId,oldTransferId,sItemName,oldDescription,issueNumber,oldIssueNo,reqNum,oldReq};

                for(int i = 0;i<2;i++) {
                    boolean searchResultByTransferNumber = CommonActions.selectOptionAndSearch(transferSearchTypeList[0], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByTransferNumber) {
                      boolean  bRequisitionOrderFound = getSearchedRecord(oPage_Transfer.list_transferTransactionRow, searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Transfer No Worked correctly as order found in the Transfer page for number: " + searchValueList[i]);
                    }else{
                        boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch(transferSearchTypeList[0],searchValueList[i]+"23");
                        m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect "+ transferSearchTypeList[0] +"Working Correct");
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                                " Nothing To Display Text Displayed as Result");
                    }
                }

                for(int i = 2;i<4;i++) {
                    boolean searchResultByDescription = CommonActions.selectOptionAndSearch(transferSearchTypeList[1],searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if(searchResultByDescription) {
                        boolean bRequisitionOrderFound = getSearchedRecordAndValidate(oPage_Transfer.list_transferTransactionRow,searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Item Description Worked correctly as order found in the Transfer page");
                    }else{
                        boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch(transferSearchTypeList[1],searchValueList[i]+"23");
                        m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect "+ transferSearchTypeList[1] +"Working Correct");
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                                " Nothing To Display Text Displayed as Result");
                    }

                }

                for(int i = 4;i<6;i++) {
                    boolean searchResultByIssueNumber = CommonActions.selectOptionAndSearch(transferSearchTypeList[2], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByIssueNumber) {
                        boolean  bRequisitionOrderFound = getSearchedRecord(oPage_Transfer.list_transferTransactionRow, searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Issue No Worked correctly as order found in the Transfer page for number: " + searchValueList[i]);
                    }else{
                        boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch(transferSearchTypeList[2],searchValueList[i]+"23");
                        m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect "+ transferSearchTypeList[0] +"Working Correct");
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                                " Nothing To Display Text Displayed as Result");
                    }

                }


                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab() ;
                Cls_Generic_Methods.customWait();
                CommonActions.selectStoreOnApp(sReceivingStore);
                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);

                for(int i = 6;i<8;i++) {
                    boolean searchResultByReqNumber = CommonActions.selectOptionAndSearch(transferSearchTypeList[3], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByReqNumber) {
                        boolean  bRequisitionOrderFound = getSearchedRecordAndValidateInView(oPage_Transfer.list_transferTransactionRow, oPage_Transfer.text_requisitionNumber,searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Requisition No Worked correctly as order found in the Transfer page for number: " + searchValueList[i]);
                    }else{
                        boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch(transferSearchTypeList[3],searchValueList[i]+"23");
                        m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect "+ transferSearchTypeList +"Working Correct");
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                                " Nothing To Display Text Displayed as Result");
                    }
                }

                for(int i = 4;i<6;i++) {
                    boolean searchResultByIssueNumber = CommonActions.selectOptionAndSearch(transferSearchTypeList[2], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByIssueNumber) {
                        boolean  bRequisitionOrderFound = getSearchedRecord(oPage_Transfer.list_transferTransactionRow, searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Issue No Worked correctly as order found in the Transfer page for number: " + searchValueList[i]);
                    }else{
                        boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch(transferSearchTypeList[2],searchValueList[i]+"23");
                        m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect "+ transferSearchTypeList[0] +"Working Correct");
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                                " Nothing To Display Text Displayed as Result");
                    }

                }


                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty as clear button is working correctly");
                boolean selectOption = CommonActions.selectOption(transferSearchTypeList[0]);
                m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(transferSearchTypeList[0]),
                        " Search Type Selected  as : "+transferSearchTypeList[0]);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(transferSearchTypeList[index])){
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

    @Test(enabled = true, description = "Desc")
    public void validateSearchFunctionalityInReceive(){

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        String transferSearchTypeList [] = {"Receive No","Item Description","Issue No","Requisition No","Transfer No"};


        try{
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();
            CommonActions.selectStoreOnApp(sReceivingStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving), "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {

                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox, 10);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type  Selection Dropdown Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.input_searchBoxInput),
                        " Input Search Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(transferSearchTypeList[0]+"."),
                        " By Default Search Type Selection Dropdown Displayed correctly as :" + transferSearchTypeList[0]);
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty by default for selected search type Displayed correctly");
                m_assert.assertTrue(!Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Not Displayed Correctly as Default");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput, "placeholder")
                                .contains("Search By " + transferSearchTypeList[0]),
                        " Input Search Box Place holder for selected search type Displayed correctly");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                        " Filter Dropdown Button Clicked");
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_thisYearFilter),
                        " This Year Selected as Filter");
                Cls_Generic_Methods.customWait(2);

                String searchValueList[] = {sItemName,oldDescription,issueNumber,oldIssueNo,reqNum,oldReq,sTransferId,oldTransferId};



                for(int i = 0;i<2;i++) {
                    boolean searchResultByDescription = CommonActions.selectOptionAndSearch(transferSearchTypeList[1],searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if(searchResultByDescription) {
                        boolean bRequisitionOrderFound = getSearchedRecordAndValidate(oPage_Transfer.list_transferTransactionRow,searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Item Description Worked correctly as order found in the Transfer page");
                    }else{
                        boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch(transferSearchTypeList[1],searchValueList[i]+"23");
                        m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect "+ transferSearchTypeList[1] +"Working Correct");
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                                " Nothing To Display Text Displayed as Result");
                    }

                }

                for(int i = 2;i<4;i++) {
                    boolean searchResultByIssueNumber = CommonActions.selectOptionAndSearch(transferSearchTypeList[2], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByIssueNumber) {
                        boolean  bRequisitionOrderFound = getSearchedRecord(oPage_Transfer.list_transactionNoteToGetItemsToBeReceived, searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Issue No Worked correctly as order found in the Transfer page for number: " + searchValueList[i]);
                    }else{
                        boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch(transferSearchTypeList[2],searchValueList[i]+"23");
                        m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect "+ transferSearchTypeList[0] +"Working Correct");
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                                " Nothing To Display Text Displayed as Result");
                    }

                }

                for(int i = 4;i<6;i++) {
                    boolean searchResultByReqNumber = CommonActions.selectOptionAndSearch(transferSearchTypeList[3], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByReqNumber) {
                        boolean  bRequisitionOrderFound = oPage_Transfer.list_transferTransactionRow.size() ==1;
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Requisition No Worked correctly as order found in the Transfer page for number: " + searchValueList[i]);
                    }else{
                        boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch(transferSearchTypeList[3],searchValueList[i]+"23");
                        m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect "+ transferSearchTypeList +"Working Correct");
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                                " Nothing To Display Text Displayed as Result");
                    }
                }


                for(int i = 6;i<8;i++) {
                    boolean searchResultByTransferNumber = CommonActions.selectOptionAndSearch(transferSearchTypeList[0], searchValueList[i]);
                    Cls_Generic_Methods.customWait();
                    if (searchResultByTransferNumber) {
                        boolean  bRequisitionOrderFound = getSearchedRecord(oPage_Transfer.list_transferTransactionRow, searchValueList[i]);
                        m_assert.assertTrue(bRequisitionOrderFound,
                                "Search By Transfer No Worked correctly as order found in the Transfer page for number: " + searchValueList[i]);
                    }else{
                        boolean searchResultByWrongTransactionId = CommonActions.selectOptionAndSearch(transferSearchTypeList[0],searchValueList[i]+"23");
                        m_assert.assertFalse(searchResultByWrongTransactionId," Transaction Search With Incorrect "+ transferSearchTypeList[0] +"Working Correct");
                        m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay),
                                " Nothing To Display Text Displayed as Result");
                    }
                }


                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_clearButtonInSearchBx),
                        " Clear Button Displayed and clicked");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                                .equalsIgnoreCase(""),
                        " Input Search Box is empty as clear button is working correctly");
                boolean selectOption = CommonActions.selectOption(transferSearchTypeList[0]);
                m_assert.assertTrue(selectOption," Able to selected Search type Again to Default");
                m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.button_searchTypeSelectedText).
                                equalsIgnoreCase(transferSearchTypeList[0]),
                        " Search Type Selected  as : "+transferSearchTypeList[0]);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                        " Search Type Dropdown Clicked");
                Cls_Generic_Methods.customWait();
                for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                    String typeText = Cls_Generic_Methods.getTextInElement(type);
                    int index = oPage_InventorySearchCommonElements.list_searchTypeList.indexOf(type);
                    if(typeText.equalsIgnoreCase(transferSearchTypeList[index])){
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





    public void createRequisitionInStore() {

        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        boolean itemFoundInRequisition = false;
        boolean bRequisitionOrderFound = false;
        boolean receivingStoreFound = false;
        String requisitionDateAndTime = "";

            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_newRequisition),
                        "New Button clicked in Order Requisition");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Requisition.dropdown_receivingStoreInRequisition, 3);

                //Select receiving store
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.dropdown_receivingStoreInRequisition),
                        "Receiving Store Dropdown clicked");
                for (WebElement receivingStore : oPage_Requisition.list_storesListInReceivingStoresRequisition) {
                    if (sCENTRAL_HUB.contains(Cls_Generic_Methods.getTextInElement(receivingStore))) {
                        Cls_Generic_Methods.clickElement(receivingStore);
                        receivingStoreFound = true;
                        break;
                    }
                }
                m_assert.assertTrue(receivingStoreFound, "Receiving store selected: <b>" + sCENTRAL_HUB + " </b>");
                Cls_Generic_Methods.customWait();

                // Select requisition type
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
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_itemSearchBox, sItemName);
                Cls_Generic_Methods.customWait(3);
                oPage_Requisition.input_itemSearchBox.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);

                    for (WebElement eItemName : oPage_Requisition.list_itemNameInPurchaseStore) {
                        if (Cls_Generic_Methods.getTextInElement(eItemName).equalsIgnoreCase(sItemName)) {
                            Cls_Generic_Methods.clickElement(eItemName);
                            Cls_Generic_Methods.customWait();
                            itemFoundInRequisition = true;
                            break;
                        }
                    }

                    m_assert.assertTrue(itemFoundInRequisition, "Item found in requisition: <b> " + sItemName + "</b>");


                if (itemFoundInRequisition) {
                    //getting rol_date and rol_time value
                    requisitionOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderTime, "value");
                    requisitionOrderTime = requisitionOrderTime.replaceAll("\\s+", "");
                    requisitionOrderTime = requisitionOrderTime.replace("pm", "PM").replace("am", "AM");
                    if(requisitionOrderTime.length() == 6){
                        requisitionOrderTime  = "0"+requisitionOrderTime;
                    }
                    m_assert.assertTrue("Requisition order time:<b> " + requisitionOrderTime + "</b>");

                    requisitionOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Requisition.input_requisitionOrderDate, "value");
                    requisitionOrderDate = requisitionOrderDate.replaceAll("/", "-");
                    m_assert.assertTrue("Requisition order date:<b> " + requisitionOrderDate + "</b>");

                    requisitionDateAndTime =  requisitionOrderDate + "  |  " + requisitionOrderTime;
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Requisition.input_quantityForRequisition, issueQty);
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Requisition.button_saveRequisition),
                            "Requisition saved");

                    Cls_Generic_Methods.customWait(4);
                }

                for (WebElement eDate : oPage_Requisition.list_dateTimeOfRequisition) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate);
                    String dateAndTime = dateTimeFull.split("\n")[0].trim();
                    reqNum = dateTimeFull.split("\n")[1].trim();

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
    }
    public void validateRequisitionReceivedAndTransferFromHub() {
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        boolean bRequisitionReceivedFound = false;
        boolean bTransferEntryFound = false;
        String dateAndTime ="";

        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectStoreOnApp(sCENTRAL_HUB);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();

            try {
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Requisition Received");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.button_today, 8);
                Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_downArrowForToday);
                CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_RequisitionReceived.list_filterPeriodType, "This Month");
                Cls_Generic_Methods.customWait(5);

                //find created requisition in requisition received
                for (WebElement e : oPage_RequisitionReceived.list_dateTimeOfRequisitionReceived) {
                    String dataTimeAndReqNo = Cls_Generic_Methods.getTextInElement(e);
                    String reqNumber = dataTimeAndReqNo.split("\n")[1].trim();
                    if (reqNum.equals(reqNumber)) {
                        Cls_Generic_Methods.clickElement(e);
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
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RequisitionReceived.input_issueQuantity, issueQty),
                            "Transfer quantity: <b> " + issueQty + "</b>");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_confirmTransfer),
                            "Item Transfer confirmed from requisition");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RequisitionReceived.list_transferItem, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_RequisitionReceived.button_saveTransfer),
                            "Transfer saved");
                    Cls_Generic_Methods.customWait(10);

                    for (WebElement eTransaction : oPage_RequisitionReceived.list_transferTransactions) {
                        String requisitionTransferDateTime = Cls_Generic_Methods.getTextInElement(eTransaction);
                        String dateNdTime = requisitionTransferDateTime.split("\\|")[1].trim();
                        List<String> sArray = List.of(dateNdTime.split("\\s"));
                        String date = sArray.get(0);
                        String time = sArray.get(sArray.size() - 1);
                        date = CommonActions.getRequiredFormattedDateTime("dd/MM/yyyy", "dd-MM-yyyy", date);

                        dateAndTime = date + "  |  " + time;
                    }
                }
                // Approving transfer from central hub

                try {

                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Central hub popup closed");
                    Cls_Generic_Methods.customWait();
                    CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Transfer/Issue");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_newTransaction, 8);

                    for (WebElement eTransferredOn : oPage_Transfer.list_dateTimeOfRequisitionReceived) {
                        String dateTimeIssue = Cls_Generic_Methods.getTextInElement(eTransferredOn);
                        String dateTimeOnUI = dateTimeIssue.split("\n")[1];
                        String date = dateTimeOnUI.split(" ")[0];
                        String time = dateTimeOnUI.split(" ")[2];
                        if (dateAndTime.contains(date)
                                &&dateAndTime.contains(time) ) {
                            bTransferEntryFound = true;
                            Cls_Generic_Methods.clickElement(eTransferredOn);
                            Cls_Generic_Methods.customWait();
                            break;
                        }
                    }

                    m_assert.assertTrue(bTransferEntryFound, "Transfer Transaction Found");

                    if (bTransferEntryFound) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransfer, 10);

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransfer),
                                "Transfer approved Clicked");
                        Cls_Generic_Methods.customWait(5);
                        issueNumber = Cls_Generic_Methods.getTextInElement(oPage_Transfer.text_issueNo);
                        Cls_Generic_Methods.customWait();
                        TaxInvoiceTest taxInvoice = new TaxInvoiceTest();
                        taxInvoice.createTaxInvoiceDeliveryChallanWithMandatoryField("TAX INVOICE", true);


                    }


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
        } finally {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        }
    }
    public void createPOUsingIndentInStore() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        Page_Indent oPage_Indent = new Page_Indent(driver);
        String sINDENT_STORE = "Pharmacy automation- Pharmacy";
        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        String sItem = "Indent item";
        String indentOrderTime,indentOrderDate,indentOrderDateAndTime ;
        String sIndentType = "Normal";
        String sSubStore = "Default";
        String sRatePerUnit = "10.0";
        String sVendorName = "Supplier ABC";
        String OrderTime,OrderDate,OrderDateAndTime ;
        String billType = "Bill";
        String billNumber = "PO_BILL_NO_" + CommonActions.getRandomString(4);

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
                boolean storeFound = false ;
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox,sINDENT_STORE.split("-")[0]);
                Cls_Generic_Methods.customWait();
                for (WebElement eStoreName : oPage_Indent.list_stores) {
                    if (Cls_Generic_Methods.getTextInElement(eStoreName).contains(sINDENT_STORE.split("-")[0])) {
                        Cls_Generic_Methods.clickElement(eStoreName);
                        storeFound = true;
                        break;
                    }
                }

                m_assert.assertTrue(storeFound, "Store found to do indent purchase : <b> " + sINDENT_STORE + "</b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_noteUnderIndentForPurchase, 10);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_variantSearch, sItem),
                        "<b> " + sItem + "</b> Entered in search item field");
                Cls_Generic_Methods.customWait(2);
                oPage_Indent.input_variantSearch.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);
                for (WebElement eItem : oPage_Indent.list_ItemDescriptionsUnderIndentPurchase) {
                    oPage_Indent = new Page_Indent(driver);
                    if (Cls_Generic_Methods.getTextInElement(eItem).contains(sItem)) {
                        Cls_Generic_Methods.clickElement(eItem);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.input_indentOrderDate, 10);
                indentOrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderTime, "value");
                indentOrderTime = indentOrderTime.replaceAll("\\s+", "");
                indentOrderTime = indentOrderTime.replace("am", "AM").replace("pm", "PM");
                if(indentOrderTime.length() == 6){
                    indentOrderTime = "0"+indentOrderTime;
                }
                m_assert.assertTrue("Indent order time:<b> " + indentOrderTime + "</b>");
                indentOrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_indentOrderDate, "value");
                indentOrderDate = indentOrderDate.replaceAll("/", "-");
                m_assert.assertTrue("Indent order date:<b> " + indentOrderDate + "</b>");

                indentOrderDateAndTime = indentOrderDate + "  |  " + indentOrderTime;
                for (WebElement eItem : oPage_Indent.list_itemNameSelectedToCreateIndentPurchase) {
                    if (Cls_Generic_Methods.getTextInElement(eItem).contains(sItem)) {
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_quantityField, "2");
                    }
                }

                Cls_Generic_Methods.customWait();
                if (Cls_Generic_Methods.selectElementByVisibleText(oPage_Indent.select_substoreFromIndentPurchasePage, sSubStore)) {
                    m_assert.assertTrue("Selected sub store = <b> " + sSubStore + "</b>");
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.dropdown_IndentType),
                        "Clicked on Indent Type Dropdown");

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

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.button_saveIndentPurchaseOrder, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder),
                        " Indent order saved");
                Cls_Generic_Methods.customWait(3);
                boolean bIndentOrderFound = false;
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

                    sIndentNoOnUI = Cls_Generic_Methods.getTextInElement(oPage_Indent.text_IndentNumber);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Indent.button_newOrder),
                            "Clicked on New Order button to create PO");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_IndentNumberInPurchaseOrderPopUp, 10);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_searchVendorAddress,sVendorName);
                    Cls_Generic_Methods.customWait();
                    String sSelectedVendor = Cls_Generic_Methods.getTextInElement(oPage_Indent.vendorAddressName);
                    if (sSelectedVendor.contains(sVendorName)) {
                        Cls_Generic_Methods.clickElement(oPage_Indent.vendorAddressName);
                        m_assert.assertTrue("Selected Vendor:  <b>" + sVendorName + "</b>");
                    } else {
                        m_assert.assertTrue("Required vendor <b>" + sVendorName + "</b> is NOT selected");
                    }
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_RatePerUnit, sRatePerUnit),
                            "Rate per unit =  <b>" + sRatePerUnit + " </b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.value_PendingQty, 10);
                    Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_cancelOtherCharges);
                    Cls_Generic_Methods.customWait();

                }

                OrderTime = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderTime, "value");
                OrderTime = OrderTime.replaceAll("\\s+", "");
                OrderTime = CommonActions.getRequiredFormattedDateTime("K:mma", "hh:mma", OrderTime);
                OrderTime = OrderTime.replace("am", "AM").replace("pm", "PM");
                m_assert.assertTrue(" order time:<b> " + OrderTime + "</b>");

                OrderDate = Cls_Generic_Methods.getElementAttribute(oPage_Indent.input_OrderDate, "value");
                OrderDate = OrderDate.replaceAll("/", "-");
                m_assert.assertTrue(" order date:<b> " + OrderDate + "</b>");
                OrderDateAndTime = OrderDate + "  |  " + OrderTime;

                Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_saveIndentPurchaseOrder);
                Cls_Generic_Methods.customWait(5);
                bIndentOrderFound = false ;
                for (WebElement eDate : oPage_Indent.list_dateTimeOfIndentOrder) {
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    poNumber = dateTimeFull.split("\\|")[2].trim();
                    if (OrderDateAndTime.contains(date) &&
                            OrderDateAndTime.contains(time)) {
                        bIndentOrderFound = true;
                        Cls_Generic_Methods.clickElement(eDate);
                        break;
                    }
                }

                Cls_Generic_Methods.customWait();
                for (WebElement eYesButton : oPage_Purchase.button_yesButtonList) {
                    if (Cls_Generic_Methods.isElementDisplayed(eYesButton)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(eYesButton),
                                "Yes Button Clicked In Approved Confirmation");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(bIndentOrderFound, "Order found in the Indent order page");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_Indent.button_newTransaction),
                        "Clicked on new transaction");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Indent.text_itemNameInPoTable, 10);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_mrpInNewTransaction,"100");
                m_assert.assertTrue("MRP Entered while Adding New Stock to Inventory =  <b>" + "100" + "</b>");

                Cls_Generic_Methods.sendKeysIntoElement(oPage_Indent.input_batchNOInNewTransaction,"BT1234");
                m_assert.assertTrue("Batch No Entered while Adding New Stock to Inventory =  <b>" + "BT1234" + "</b>");

                Cls_Generic_Methods.clickElement(oPage_Purchase.dropdown_selectBillType);
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_Purchase.dropdown_selectBillType, billType), "Selected Bill Type as <b>" + billType + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Purchase.input_billNumber, billNumber), "Entered<b> " + billNumber + "</b> in Bill Number");
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_billDate);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.input_todayBillDate, 3);
                Cls_Generic_Methods.clickElement(oPage_Purchase.input_todayBillDate);
                String billDate = Cls_Generic_Methods.getElementAttribute(oPage_Purchase.input_billDate, "value");
                m_assert.assertInfo("Selected Bill Date as " + billDate);

                if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseOrder.input_expiryDateCreatePO)) {
                    Cls_Generic_Methods.clickElement(oPage_PurchaseOrder.input_expiryDateCreatePO);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseOrder.select_expiryDateYear, 1);
                    String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_PurchaseOrder.select_expiryDateYear);
                    int year = Integer.parseInt(currentYear);
                    int newYear = year + 3;

                    Cls_Generic_Methods.selectElementByVisibleText(oPage_PurchaseOrder.select_expiryDateYear, Integer.toString(newYear));
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_PurchaseOrder.select_expiryDateDay);
                    String expiryDate = Cls_Generic_Methods.getElementAttribute(oPage_PurchaseOrder.input_expiryDateCreatePO, "value");

                    m_assert.assertInfo("Selected Expiry Date as <b>" + expiryDate + "</b>");
                }

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Purchase.button_SaveChanges), "Clicked Save Changes");
                Cls_Generic_Methods.customWait(5);
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInInventoryStorePanel("Transaction", "Purchase/Grn");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_purchaseNew, 10);
                Cls_Generic_Methods.clickElement(oPage_Purchase.button_approvePurchaseTransaction);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_Yes, 6);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Purchase.button_Yes);
                m_assert.assertInfo("Purchase Grn created and approved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Purchase.button_assignBarcodePurchaseTransaction, 15);
                grnNumber = Cls_Generic_Methods.getTextInElement(oPage_Purchase.text_transactionID);

            }
            catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }
    public boolean createDirectTransfer() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Transfer oPage_Transfer = new Page_Transfer(driver);
        boolean status = false;
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
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, sItemName);
            Cls_Generic_Methods.customWait(1);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.input_searchItemToBeTransferred, " ");
            Cls_Generic_Methods.customWait(3);
            for (WebElement eItemVariantCode : oPage_Transfer.list_itemDescriptionRow
            ) {
                if (Cls_Generic_Methods.getTextInElement(eItemVariantCode).equalsIgnoreCase(sItemName)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(eItemVariantCode), "Selected Item : <b>" + sItemName + "</b>");
                    break;
                }
            }

            Cls_Generic_Methods.customWait(3);
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_Transfer.list_quantityFieldForItemsToTransfer.get(0), issueQty),
                    "Entered <b>" + issueQty + "</b> in Quantity");

            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_saveChanges), "Transfer Transaction saved");
            String currentTime = getCurrentDateTime().replaceAll("\\|", "");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Transfer.list_transferTransactionRow,5);


            for (WebElement ele : oPage_Transfer.list_transferTransactionRow) {
                    sTransferId = Cls_Generic_Methods.getTextInElement(ele.findElement(By.xpath("./child::div[1]")));
                    String sTransferredAt = Cls_Generic_Methods.getTextInElement(ele.findElement(By.xpath("./child::div[2]")));

                    if (sTransferredAt.contains(currentTime.split(":")[0])) {
                        Cls_Generic_Methods.clickElement(ele);
                        m_assert.assertInfo("Transfer ID : <b>" + sTransferId + "</b> || Transfer Date : <b>" + sTransferredAt + "</b>");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Transfer.button_approveTransferTransaction, 10);
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransferTransaction), "Approved Transfer Transaction");
                        status = true;
                        break;
                    }
                }


           oldTransferId =  Cls_Generic_Methods.getTextInElement(oPage_Transfer.list_transferTransactionRow.get(2).findElement(By.xpath("./child::div[1]")));

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Transfer.button_approveTransfer),
                    "Cancel Button Clicked");
            Cls_Generic_Methods.customWait(3);
            TaxInvoiceTest taxInvoice = new TaxInvoiceTest();
            taxInvoice.createTaxInvoiceDeliveryChallanWithMandatoryField("DELIVERY CHALLAN",false);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            Cls_Generic_Methods.customWait();


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertInfo("Unable to create Transfer " + e);
        }
        return status;
    }
    public String getOldRequisition(List<WebElement> listOfElement){

        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        String oldReqNumber = "";
        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_filterDropdownButton),
                    " Filter Dropdown Button Clicked");
            Cls_Generic_Methods.customWait(2);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.text_previousMonthFilter),
                    " Previous Month Selected as Filter");
            Cls_Generic_Methods.customWait(2);
            for (WebElement eDate : listOfElement) {
                String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate);
                String reqNumOnUI = dateTimeFull.split("\n")[1].trim();
                oldReqNumber = reqNumOnUI ;
            }


        }catch (Exception e){
            e.printStackTrace();
            m_assert.assertFatal(e.toString());
        }

        return oldReqNumber;
    }
    public boolean getSearchedRecord(List<WebElement> listOfElement , String expectedString){

        boolean recordFound = false ;
        try {
            for (WebElement eDate : listOfElement) {
                String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate);
                String reqNumOnUI = dateTimeFull.split("\n")[1].trim();
                if (expectedString.contains(reqNumOnUI)) {
                    recordFound = true;
                    Cls_Generic_Methods.clickElement(eDate);
                    Cls_Generic_Methods.customWait( 5);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
         return  recordFound ;
    }
    public boolean getSearchedRecordAndValidate(List<WebElement> listOfElement,String expectedString){

        Page_Requisition oPageRequisition = new Page_Requisition(driver);
        boolean recordFound = false ;
        try {
            for (WebElement eDate : listOfElement) {
                recordFound = false;
                Cls_Generic_Methods.clickElement(eDate);
                Cls_Generic_Methods.customWait( 5);
                for(WebElement item : oPageRequisition.list_itemNameOnRequisition){
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
    public boolean getSearchedRecordAndValidateInView(List<WebElement> listOfElement,WebElement element,String expectedString){

        boolean recordFound = false ;
        try {
            for (WebElement eDate : listOfElement) {
                recordFound = false;
                Cls_Generic_Methods.clickElement(eDate);
                Cls_Generic_Methods.customWait( 5);
                String actualUIText = Cls_Generic_Methods.getTextInElement(element);
                if(actualUIText.contains(expectedString)){
                    recordFound = true;
                    break;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  recordFound ;
    }
    public void getOldRecord(List<WebElement> listOfElement){

        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);

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
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    oldPO = dateTimeFull.split("\\|")[2].trim();
                    oldIndent= Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.text_rhsIndentNumber);
                    oldGrn = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.list_transactionAgainstOrder.get(0));
                    oldDescription = Cls_Generic_Methods.getTextInElement(oPage_PurchaseOrder.list_itemNameOnRequisition.get(0));
                    if(!oldGrn.isEmpty() && !oldIndent.isEmpty()){
                        break;
                    }
                }

            }


        }catch (Exception e){
            e.printStackTrace();
            m_assert.assertFatal(e.toString());
        }

    }
    public void getOldRecordRequisitionReceived(List<WebElement> listOfElement){

        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);
        Page_PurchaseOrder oPage_PurchaseOrder = new Page_PurchaseOrder(driver);
        Page_RequisitionReceived oPage_RequisitionReceived = new Page_RequisitionReceived(driver);

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
                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(eDate.findElement(By.xpath("./td[1]")));
                    oldReq = dateTimeFull.split("\\|")[2].trim();
                    oldIssueNo = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.text_rhsIssueNo);
                    oldDescription = Cls_Generic_Methods.getTextInElement(oPage_RequisitionReceived.list_textDescriptionRhs.get(0));
                    if(!oldDescription.isEmpty() && !oldIssueNo.isEmpty()){
                        break;
                    }
                }

            }


        }catch (Exception e){
            e.printStackTrace();
            m_assert.assertFatal(e.toString());
        }

    }
    public String getCurrentDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | hh:mm a");
        Date date = new Date();
        //  07-06-2023 | 01:09 PM
        String date1 = dateFormat.format(date);
        return date1;
    }

    public void getDataFromSetting(){

        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new Page_InventorySearchCommonElements(driver);


        try{
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait();
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Sequence Manager");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.text_taxInvoiceId,10);
            taxInvoiceId = Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.text_taxInvoiceId).trim();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.text_deliveryChallanId,10);
            deliveryChallanId = Cls_Generic_Methods.getTextInElement(oPage_InventorySearchCommonElements.text_deliveryChallanId).trim();

        }catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

}
