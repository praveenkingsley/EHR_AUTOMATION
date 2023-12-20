package pages.store.OtStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_OtStore extends TestBase {

    private WebDriver driver;

    public Page_OtStore(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }


    @FindBy(xpath = "//div[@class='ipd_content tab-pane active']/div/div[1]")
    public List<WebElement> list_patientNameListInQueue;

    @FindBy(xpath = "//a[contains(text(),'Create Tray')]")
    public WebElement button_createTray;

    @FindBy(xpath = "//button[text()='Description']")
    public WebElement button_descriptionInTrayTemplate;

    @FindBy(xpath = "//input[@id='lots_search']")
    public WebElement input_lotSearchBoxInTrayTemplate;

    @FindBy(xpath = "//tbody[@class='items-lots-body']//tr/td/div[1]/span[1]")
    public List<WebElement> list_itemNameListInTrayTemplate;

    @FindBy(xpath = "//tbody[@class='items-lots-body']//tr/td/div[2]/span[2]/b")
    public List<WebElement> list_itemStockListInTrayTemplate;

    @FindBy(xpath = "//tbody[@class='items-lots-body']//tr/td/div[2]/span[3]/b")
    public List<WebElement> list_itemRateListInTrayTemplate;

    @FindBy(xpath = "//table[@class='inventory-tray-log-item']//td[5]")
    public List<WebElement> list_itemMRPListInTrayTemplate;

    @FindBy(xpath = "//table[@class='inventory-tray-log-item']//td[7]/input[1]")
    public List<WebElement> list_itemQuantityListInTrayTemplate;

    @FindBy(xpath = "//table[@class='inventory-tray-log-item']//td[8]/input")
    public List<WebElement> list_itemAmountListInTrayTemplate;

    @FindBy(xpath = "//input[@id='inventory_transaction_tray_total_cost']")
    public WebElement input_trayTotalCostValue;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered no_padding']/thead/tr/th")
    public List<WebElement> list_trayHeaderListInViewPatient;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered no_padding']/tbody/tr")
    public List<WebElement> list_trayItemListInViewPatient;

    @FindBy(xpath = "//div[text()='Available Stock:']/following-sibling::div/strong")
    public WebElement text_stockAvailableInItemDetailsInViewTemplate;

    @FindBy(xpath = "//h4[text()='Tray Details']")
    public WebElement header_trayDetailsHeaderInViewTray;

    @FindBy(xpath = "//a[text()='Delete']")
    public WebElement button_deleteButtonInViewTray;

    @FindBy(xpath = "//a[text()='Edit Tray']")
    public WebElement button_EditTrayButtonInViewTray;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']//th")
    public List<WebElement> list_itemDetailsHeaderListInViewTray;

    @FindBy(xpath = "//table[@class='table table-striped table-bordered']/tbody/tr")
    public List<WebElement> list_itemDetailsDataListInViewTray;

    @FindBy(xpath = "//a[contains(text(),'Close Tray')]")
    public WebElement button_CloseTrayButtonInViewTray;

    @FindBy(xpath = "//input[@value='Update Changes']")
    public WebElement input_updateChangesInTray;

    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_new;

    @FindBy(xpath = "//h4[text()='Store Consumption']")
    public WebElement header_storeConsumption;

    @FindBy(xpath = "//button[text()='Description']")
    public WebElement button_descriptionInStoreConsumption;

    @FindBy(xpath = "//input[@id='lots_search']")
    public WebElement input_lotSearchBoxInStoreConsumption;

    @FindBy(xpath = "//tbody[@class='items-lots-body']/tr/td[1]/div[1]/span[1]/b[1]")
    public List<WebElement> list_itemNameListInStoreConsumption;

    @FindBy(xpath = "//table[contains(@class,'lot-table')]/tbody/tr/td/strong")
    public List<WebElement> list_itemNameListInRHSTable;

    @FindBy(xpath = "//table[contains(@class,'lot-table')]/tbody/tr/td[8]/button/i")
    public List<WebElement> list_deleteiIemInRHSTable;

    @FindBy(xpath = "//select[contains(@class,'form-control user-name select2-hidden-accessible')]")
    public WebElement select_userNameInStoreConsumption;

    @FindBy(xpath = "//input[contains(@class,'item_quantity modalRequest_input_style')]")
    public WebElement input_qtyForItem;

    @FindBy(xpath = "//label[contains(@id,'id-error')]")
    public WebElement label_employeeFieldValidationError;

    @FindBy(xpath = "//label[contains(@id,'stock-error')]")
    public WebElement label_qtyFieldValidationError;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveChanges;

    @FindBy(xpath = "//input[@placeholder='Transaction Note']")
    public WebElement input_transactionNote;

    @FindBy(xpath = "//input[@id='inventory_transaction_store_consumption_transaction_date']")
    public WebElement text_transactionDate;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']/tr")
    public List<WebElement> list_storeConsumptionTransactions;

    @FindBy(xpath = "//button[contains(@class,'cancel-store_consumption')]")
    public WebElement button_cancelStoreConsumption;

    @FindBy(xpath = "//i[contains(@class,'edit')]")
    public WebElement button_editStoreConsumption;

    @FindBy(xpath = "//a[text()='Approve']")
    public WebElement button_approveStoreConsumption;

    @FindBy(xpath = "//table[contains(@class,'table-bordered')]/tbody/tr/td[1]")
    public WebElement text_itemDescriptionOnUI;

    @FindBy(xpath = "//table[contains(@class,'table-bordered')]/tbody/tr/td[5]")
    public WebElement text_itemQuantityOnUI;

    @FindBy(xpath = "//table[contains(@class,'table-bordered')]/tbody/tr/td[6]")
    public WebElement text_EmployeeNameOnUI;

    @FindBy(xpath = "//a[contains(text(),'Cancel')]")
    public WebElement button_confirmCancel;

    @FindBy(xpath = "//div[@class='inventory-show-panel']/div/div[1]/div[1]")
    public WebElement text_statusOfTransaction;





}
