package pages.store.PharmacyStore.Transaction;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Adjustment extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Adjustment(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }


    @FindBy(xpath = "//b[text()='Adjustment Item1']")
    public WebElement text_ItemName;

    @FindBy(xpath = "//a[@id='edit_inventory_lot']")
    public WebElement link_Edit;


    @FindBy(xpath = "//i[@class='fa fa-plus']")
    public WebElement button_New;

    @FindBy(xpath = "//h4[text()='Adjustment']")
    public WebElement header_Adjustment;

    @FindBy(xpath = "//button[text()='Description']")
    public WebElement button_Description;

    @FindBy(xpath = "//input[@id='lots_search']")
    public WebElement input_LotSearch;

    @FindBy(xpath = "//span[@title='Adjustment Item1']")
    public WebElement title_Item;

    @FindBy(xpath = "//input[@placeholder='Qty']")
    public List<WebElement> list_inputQtyList;
    @FindBy(xpath = "//label[contains(@id,'inventory_transaction_adjustment')]")
    public WebElement text_adjustmentError;
    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement input_SaveChanges;

    @FindBy(xpath = "(//tr[@class='inventory-list-row'])[1]")
    public WebElement row_AdjustmentTransaction;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li//li")
    public List<WebElement> list_ChildOptionsOnLeft;
    @FindBy(xpath = "//tbody[@class='items-lots-body']//tr")
    public List<WebElement> getList_namesOfMedicinesOnLeftInSearchResult;

    @FindBy(xpath = "//tbody[@class='items-lots-body']/tr//div[1]/span[1]/b")
    public List<WebElement> list_namesOfMedicinesOnLeftInSearchResult;

    @FindBy(xpath = "//input[@id='inventory_transaction_adjustment_transaction_date']")
    public WebElement input_adjustmentTransactionDate;
    @FindBy(xpath = "//a[text()='Today']/following-sibling::button[1]")
    public WebElement button_todayFilterButton;
    @FindBy(xpath = "//tbody[@class='inventory-table-body']//tr//td[1]")
    public List<WebElement> list_transactionDateColumnList;
    @FindBy(xpath = "//table[@class='table transaction-lots-table']/thead/tr/td")
    public List<WebElement> list_adjustmentTableHeaderList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td/strong")
    public List<WebElement> list_adjustmentItemNameList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[2]/label")
    public List<WebElement> list_adjustmentBatchNoList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[3]/label")
    public List<WebElement> list_adjustmentBarcodeList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[4]/label")
    public List<WebElement> list_adjustmentModelNoList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[5]")
    public List<WebElement> list_adjustmentExpiryList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[9]/button")
    public List<WebElement> list_adjustmentDeleteButtonList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[6]//select[contains(@id,'inventory_transaction_adjustment')]")
    public List<WebElement> list_adjustmentAddDeductSelectList;
    @FindBy(xpath = "//div[@class='transaction-table-body']/table/tbody/tr/td[8]/input[1]")
    public List<WebElement> list_adjustmentTotalCostList;
    @FindBy(xpath = "//input[@id='inventory_transaction_adjustment_total_cost']")
    public WebElement input_totalAmountIncludingTax;
    @FindBy(xpath = "//input[@id='inventory_transaction_adjustment_note']")
    public WebElement input_transactionNote;
    @FindBy(xpath = "//input[@id='inventory_transaction_adjustment_transaction_date']")
    public WebElement input_transactionDate;
    @FindBy(xpath = "//div[@class='inventory-show-panel']//h3")
    public WebElement header_viewAdjustmentHeader;
    @FindBy(xpath = "//td[text()='Stock Adjustment']/parent::tr/following-sibling::tr[1]/td[4]")
    public WebElement text_transactionIdInSetting;
    @FindBy(xpath = "//td[text()='Stock Adjustment']/parent::tr/td[4]")
    public WebElement text_opticalTransactionIdInSetting;

    @FindBy(xpath = "//b[contains(text(),'Transaction ID')]/parent::span")
    public WebElement text_transactionIdInAdjustment;
    @FindBy(xpath = "//h4[contains(text(),'Transaction Details')]")
    public WebElement text_transactionDetails;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/thead/tr/th")
    public List<WebElement> list_transactionDetailsHeaderList;
    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr/td")
    public List<WebElement> list_transactionDetailsValueList;














}
