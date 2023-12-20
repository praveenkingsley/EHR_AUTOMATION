package pages.store.PharmacyStore.Order;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Requisition extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Requisition(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_newRequisition;

    @FindBy(xpath = "//span[contains(@aria-labelledby,'inventory_order_requisition_receive_store')]")
    public WebElement dropdown_receivingStoreInRequisition;
    @FindBy(xpath = "//select[@id='inventory_order_requisition_receive_store_id']")
    public WebElement select_receivingStore;
    @FindBy(xpath = "//input[@id='variants_search']")
    public WebElement input_searchMedicineName;
    @FindBy(xpath = "//tbody[@class='items-variants-body']//td[2]/b")
    public List<WebElement> list_namesOfMedicinesOnLeftInSearchResultPO;
    @FindBy(xpath = "//ul[@id='select2-inventory_order_requisition_receive_store_id-results']/li[@class='select2-results__option']")
    public List<WebElement> list_storesListInReceivingStoresRequisition;

    @FindBy(xpath = "//span[contains(@aria-labelledby,'_requisition_type')]")
    public WebElement dropdown_requisitionType;

    @FindBy(xpath = "//select[@id='inventory_order_requisition_requisition_type']")
    public WebElement select_reqType;
    @FindBy(xpath = "//ul[contains(@id,'_requisition_type')]/li[@class='select2-results__option']")
    public List<WebElement> list_requisitionType;

    @FindBy(xpath = "//input[@id='inventory_order_requisition_requisition_time']")
    public WebElement input_requisitionOrderTime;

    @FindBy(xpath = "//input[@id='inventory_order_requisition_requisition_date']")
    public WebElement input_requisitionOrderDate;

    @FindBy(xpath = "//input[@placeholder='Qty']")
    public WebElement input_quantityForRequisition;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[1]")
    public List<WebElement> list_dateTimeOfRequisition;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[2]")
    public List<WebElement> list_statusOfRequisition;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveRequisition;

    @FindBy(xpath = "//div[@class='inventory-show-panel']/div/a[text()='View Order']")
    public WebElement button_viewOrderRequisition;
    @FindBy(xpath = "//div[@class='modal-body window-body']/div/a[1]")
    public WebElement button_cancelRequisition;
    @FindBy(xpath = "//div[@class='modal-body window-body']/div/a[2]")
    public WebElement button_approveRequisition;
    @FindBy(xpath = "//div[@class='modal-body window-body']/div/a[3]")
    public WebElement button_editRequisition;

    @FindBy(xpath = "//div[@class='modal-footer']/button[text()='Yes']")
    public WebElement button_confirmRequisition;

    @FindBy(xpath = "//table[@class='table items-variants-table']//tr/td[2]")
    public List<WebElement> list_itemNameInPurchaseStore;

    @FindBy(xpath = "//input[@id='variants_search']")
    public WebElement input_itemSearchBox;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[4]")
    public List<WebElement> list_requisitionNumberOnRequisition;
    @FindBy(xpath = "//input[@placeholder='Qty']")
    public List<WebElement>  input_quantityForRequisitionList;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//tr/td[2]")
    public List<WebElement> list_itemNameOnRequisition;

    @FindBy(xpath = "//tbody[@class='items-variants-body']//td[1]")
    public List<WebElement> list_itemCodeOnLeftInSearchResultPO;
    @FindBy(xpath = "//tbody[@class='items-variants-body']//td[3]")
    public List<WebElement> list_stockOnLeftInSearchResultPO;
    @FindBy(xpath = "//tbody[@class='items-variants-body']//td[4]")
    public List<WebElement> list_itemCategoryOnLeftInSearchResultPO;

    @FindBy(xpath = "//input[@id='inventory_order_requisition_remarks']")
    public WebElement input_orderNote;
    @FindBy(xpath = "//button[contains(@class,'delete_empty_item')]")
    public List<WebElement> button_removeItemFromList;
    @FindBy(xpath = "//table[@id='medicationTable']/thead/tr/td")
    public List<WebElement> list_tableHeadCreateRequisition;
    @FindBy(xpath = "//table[@id='medicationTable']/tbody/tr")
    public List<WebElement> list_tableRowCreateRequisition;
    @FindBy(xpath = "//label[@class='error']")
    public List<WebElement> label_quantityError;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[3]/b")
    public List<WebElement> list_receivingStoreRequisition;
    @FindBy(xpath = "//div[@class='logopanel']")
    public WebElement logo_store;
    @FindBy(xpath = "//b[text()='Requisition']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_requisitionNo;
    @FindBy(xpath = "//b[text()='Created By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_requisitionCreatedByUser;
    @FindBy(xpath = "//b[text()='Created By']/parent::div/following-sibling::div/span[5]")
    public WebElement text_rhs_requisitionCreatedStore;
    @FindBy(xpath = "//b[text()='Created By']/parent::div/following-sibling::div/span[3]")
    public WebElement text_rhs_requisitionCreatedAt;
    @FindBy(xpath = "//b[text()='Status']/parent::div/following-sibling::div/span")
    public WebElement text_rhs_requisitionStatus;
    @FindBy(xpath = "//b[text()='Requisition']/parent::div/following-sibling::div/span[3]")
    public WebElement text_rhs_requisitionType;
    @FindBy(xpath = "//b[text()='To']/parent::div/following-sibling::div")
    public WebElement text_rhs_requisitionToStore;
    @FindBy(xpath = "//b[text()='Approved By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_rhs_requisitionApprovedByUser;
    @FindBy(xpath = "//b[text()='Approved By']/parent::div/following-sibling::div/span[3]")
    public WebElement text_rhs_requisitionApprovedAt;
    @FindBy(xpath = "//*[text()='Item Details:']/parent::div/following-sibling::div//table//th")
    public List<WebElement> list_textRHSItemDetailsTableHeader;
    @FindBy(xpath = "//*[text()='Item Details:']/parent::div/following-sibling::div//table//tbody/tr")
    public List<WebElement> list_textRHSItemDetailsTableRow;
    @FindBy(xpath = "//div[@class='modal-content']//*[text()='Item Details:']/parent::div/following-sibling::div//table//th")
    public List<WebElement> list_textViewOrderItemDetailsTableHeader;
    @FindBy(xpath = "//div[@class='modal-content']//*[text()='Item Details:']/parent::div/following-sibling::div//table//tbody/tr")
    public List<WebElement> list_textViewOrderItemDetailsTableRow;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//h3")
    public WebElement text_RHSOrderNote;
    @FindBy(xpath = "//h4[text()='Requisition Order']")
    public WebElement text_headerViewOrder;
    @FindBy(xpath = "//b[text()='Requisition']/parent::div/following-sibling::div/span[1]")
    public WebElement text_viewOrder_requisitionNo;
    @FindBy(xpath = "//div[@class='modal-content']//b[text()='Created By']/parent::div/following-sibling::div/span[1]")
    public WebElement text_viewOrder_requisitionCreatedByUser;
    @FindBy(xpath = "//div[@class='modal-content']//b[text()='Created By']/parent::div/following-sibling::div/span[5]")
    public WebElement text_viewOrder_requisitionCreatedStore;
    @FindBy(xpath = "//div[@class='modal-content']//b[text()='Created By']/parent::div/following-sibling::div/span[3]")
    public WebElement text_viewOrder_requisitionCreatedAt;
    @FindBy(xpath = "//div[@class='modal-content']//b[text()='Status']/parent::div/following-sibling::div/span")
    public WebElement text_viewOrder_requisitionStatus;
    @FindBy(xpath = "//div[@class='modal-content']//b[text()='Requisition']/parent::div/following-sibling::div/span[3]")
    public WebElement text_viewOrder_requisitionType;
    @FindBy(xpath = "//div[@class='modal-content']//b[text()='To']/parent::div/following-sibling::div")
    public WebElement text_viewOrder_requisitionToStore;
    @FindBy(xpath = "//div[@class='modal-content']//h3")
    public WebElement text_viewOrderWindowNote;
    @FindBy(xpath = "//button[normalize-space()='Close']")
    public WebElement button_close;
    @FindBy(xpath = "//div[@class='modal-footer']/button[text()='Cancel']")
    public WebElement button_cancelApprove;
    @FindBy(xpath = "//input[@value='Update Changes']")
    public WebElement button_updateRequisition;
    @FindAll({@FindBy(xpath = "//div[@class='col-xs-4']//button[@type='button']"),
            @FindBy(xpath = "//button[@class='btn btn-primary btn-sm dropdown-toggle ']")})
    public WebElement button_dropDownTimeFilter;

    @FindBy(xpath = "//table[@class='table inventory_table_center']/thead//td")
    public List<WebElement> list_headerRequisitionList;
    @FindBy(xpath = "//table[@class='table inventory_table_center']//tbody")
    public List<WebElement> list_requisitionDataList;
    @FindBy(xpath = "//b[text()='Fulfillment Status']/parent::div/following-sibling::div/span")
    public WebElement text_fullfilmentStatus;

    @FindBy(xpath = "//input[@id='cancelled_reason']")
    public WebElement input_cancelReason;
    @FindBy(xpath = "//input[@value='Cancel Requisition']")
    public WebElement input_cancelRequisition;






}
