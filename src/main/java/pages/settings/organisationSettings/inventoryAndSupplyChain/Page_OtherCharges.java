package pages.settings.organisationSettings.inventoryAndSupplyChain;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_OtherCharges extends TestBase{
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_OtherCharges(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//div[@id='manage_inventory_other_charges']//div/a")
    public WebElement button_addOtherCharges;

    @FindBy(xpath = "//input[@id='inventory_other_charge_name']")
    public WebElement input_otherChargesName;

    @FindBy(xpath = "//textarea[@id='inventory_other_charge_description']")
    public WebElement input_otherChargesDescription;

    @FindBy(xpath = "//input[@onclick='validate_inventory_other_charge_form()']")
    public WebElement button_saveChanges;

    @FindBy(xpath = "//table[@id='manage_inventory_other_charges_table']/tbody/tr/td[1]")
    public List<WebElement> list_chargesNameInTable;

    @FindBy(xpath = "//table[@id='manage_inventory_other_charges_table']/tbody/tr/td[2]")
    public List<WebElement> list_chargesDescriptionInTable;

    @FindBy(xpath = "//table[@id='manage_inventory_other_charges_table']/tbody/tr/td[3]")
    public List<WebElement> list_chargesActionsInTable;

    // store elements
    @FindBy(xpath = "//div[contains(@class,'inventory-item-toolbar')]/nav/div/div[2]/div/a")
    public WebElement button_salesOrderNew;

    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_saveNewPatientForm;

    @FindBy(xpath = "//button[@input-value='NonStockable']")
    public WebElement button_nonStockable;

    @FindBy(xpath = "//table[@class='table items-table']/tbody/tr")
    public List<WebElement> list_nonStockableItems;

    @FindBy(xpath = "//input[contains(@class,'non_stockable_item_quantity')]")
    public WebElement input_nonStockableQuantity;

    @FindBy(xpath = "//input[contains(@class,'unit_item_list_price')]")
    public WebElement input_nonStockableMRP;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveSalesOrder;

    @FindBy(xpath ="//select[contains(@id,'inventory_transaction_stock_receive_note_other_charges_attributes_0_other_charge_id')]" )
    public WebElement dropdown_otherCharges;

    @FindBy(xpath = "//div[@class='modal-header transaction-header']/button[@class='close']")
    public WebElement button_closeSRNTemplate;

    @FindBy(xpath ="//select[contains(@id,'stock_receive_note_other_charges___id')]/option" )
    public List<WebElement> list_otherCharges;

}
