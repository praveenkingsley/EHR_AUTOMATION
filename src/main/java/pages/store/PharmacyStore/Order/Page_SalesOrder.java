package pages.store.PharmacyStore.Order;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_SalesOrder extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_SalesOrder(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }


    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_addNewButtonInOrder;

    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_saveOnNewPatientFormInSalesOrderOnOrder;

    @FindBy(xpath = "//h5/strong[text()='Sales Order']")
    public WebElement text_salesOrderTitleOnModal;

    @FindBy(xpath = "//input[@id='invoice_inventory_order_order_date']")
    public WebElement text_orderDateOnInvoice;

    @FindBy(xpath = "//input[@id='invoice_inventory_order_order_time']")
    public WebElement text_orderTimeOnInvoice;

    @FindBy(xpath = "//button[text()='Description']")
    public WebElement button_description;

    @FindBy(xpath = "//button[text()='Non-Stockable']")
    public WebElement button_nonStockable;

    @FindBy(xpath = "//input[@id='lots_search']")
    public WebElement input_searchMedicineNameInDescription;

    @FindBy(xpath = "//tbody[@class='items-lots-body']/tr//div[1]/span[1]/b")
    public List<WebElement> list_namesOfMedicinesOnLeftInSearchResult;

    @FindBy(xpath = "//input[@placeholder='Qty']")
    public WebElement input_quantityOfMedicine;

    @FindBy(xpath = "//input[@name='invoice_total_payment_remaining']")
    public WebElement text_balancePendingAmount;

    @FindBy(xpath = "//select[@name='invoice_inventory_order[payment_received_breakups_attributes][0][mode_of_payment]']")
    public WebElement select_modeOfPayment;

    @FindBy(xpath = "//input[@name='invoice_inventory_order[payment_received_breakups_attributes][0][amount_received]']")
    public WebElement input_amountPaidInCash;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveChangesOnSalesOrder;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_closeModalOfSalesOrder;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']/tr/td[2]")
    public List<WebElement> list_transactionsInfoOnSalesOrderTable;

    @FindBy(xpath = "//select[@id='patient_search']")
    public WebElement list_PatientSearch;

    @FindBy(xpath = "//input[@id='search-patient']")
    public WebElement input_patientdetails;
    @FindBy(xpath = "(//button[contains(@class,'search')])[3]")
    public WebElement button_Search;

    @FindBy (xpath = "//b[contains(text(),'9879879870')]")
    public WebElement selectPatient;

    @FindBy(xpath = "//tbody[@class='items-lots-body']//tr")
    public List<WebElement> getList_namesOfMedicinesOnLeftInSearchResult;

    @FindBy(xpath = "//table[contains(@class,'inventory-')]//td[6]")
    public WebElement priceOfMedication;

    @FindBy(xpath = "//input[contains(@class,'fi_input_style text-right non_taxable_amount')]")
    public WebElement taxableAmount;

    @FindBy(xpath = "//input[contains(@class,'tax_group_amount_60352163517aa33deaa68b86')]")
    public WebElement taxAmount;
    @FindBy(xpath = "//input[contains(@name,'[total_list_price]')]")
    public WebElement totalAmount;

    @FindBy(xpath = "//table[contains(@class,'inventory')]//tr")
    public List<WebElement> list_namesofSalesOrder;

    @FindBy(xpath = "(//div[@class='col-lg-2 font_size'])[6]")
    public WebElement text_Patient;

    @FindBy(xpath = "(//div[contains(@class,'col-sm-5 font_size')])[1]")
    public  WebElement text_TaxableAmount;

    @FindBy(xpath = "(//div[contains(@class,'col-sm-5 font_size')])[2]")
    public  WebElement text_TaxAmount;

    @FindBy(xpath = "(//div[contains(@class,'col-sm-5 font_size')])[3]")
    public  WebElement text_TotalAmount;

    @FindBy(xpath = "(//div[contains(@class,'col-sm-5 font_size')])[4]")
    public  WebElement text_PaymentReceived;

    @FindBy(xpath = "//table[@class='table table table-striped table-bordered']//td[2]")
    public WebElement text_Quantity;

    @FindBy(xpath = "//table[@class='table table table-striped table-bordered']//td[5]")
    public WebElement text_SellingPrice;

    @FindBy(xpath = "//a[contains(text(),'More')]")
    public WebElement button_MoreAction;

    @FindBy(xpath = "(//input[contains(@placeholder,'Date of transition')])[1]")
    public WebElement input_TransitionDate;

    @FindBy (xpath = "//input[contains(@placeholder,'Time of Order')]")
    public WebElement input_OrderTime;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']/tr[1]/td[2]")
    public WebElement text_TransactionInfo;

    @FindBy(xpath = "//tbody[@class='items-body']//tr/td[2]/b")
    public List<WebElement> getList_namesOfItemOnLeftInNonStockable;

    @FindBy(xpath = "//input[@class='non_stockable_item_quantity modalRequest_input_style']")
    public WebElement input_itemQuantity;

    @FindBy(xpath = "//input[@id='invoice_inventory_order_order_date']")
    public WebElement input_orderDate;

    @FindBy(xpath = "//input[@id='invoice_inventory_order_order_time']")
    public WebElement input_orderTime;


    @FindBy(xpath = "//input[@id='grand_total_inc_discount']")
    public WebElement input_netAmount;


    @FindBy(xpath = "//tbody[@class='inventory-table-body']//tr")
    public List<WebElement> list_salesOrderTableItemDataList;

    @FindBy(xpath = "//a[text()=' Create Bill']")
    public WebElement button_salesOrderCreateBill;

    @FindBy(xpath = "//h5[text()='Review Order']")
    public WebElement header_createBillReviewOrderTemplate;

    @FindBy(xpath = "//select[@name='invoice_inventory_invoice[payment_received_breakups_attributes][0][mode_of_payment]']")
    public WebElement select_modeOfPaymentInvoice;

    @FindBy(xpath = "//input[@name='invoice_inventory_invoice[payment_received_breakups_attributes][0][amount_received]']")
    public WebElement input_amountPaidInCashInvoice;



    @FindBy(xpath = "//input[@value='Create Bill']")
    public WebElement input_createBillReviewOrderTemplate;



}
