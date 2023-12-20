package pages.ipd.forms.intraOperative;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_BillOfMaterial extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_BillOfMaterial(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// bill of material
	@FindBy(xpath = "//button[@id='bill_of_material_form']")
	public WebElement button_billOfMaterialTemplate;

	@FindBy(xpath = "//a[@id='bom-note-btn']")
	public WebElement button_addBillOfMaterial;
	
	@FindBy(xpath = "//button[@input-value='Description']")
	public WebElement button_descriptionInBom;
	
	@FindBy(xpath = "//tr[@class='inventory-list-row']")
	public List<WebElement> tr_itemInBom;
	
	@FindBy(xpath = "//button[@name='anesthesia_planned']")
	public List<WebElement> listButtons_selectAnesthesiaPlanned;

	@FindBy(xpath = "//tr[@class='inventory-list-row']")
	public WebElement select_itemForBom;

	@FindBy(xpath = "//input[@class='item_bom_list_price modalRequest_input_style']")
	public WebElement input_billableUnitPrice;

	@FindBy(xpath = "//input[@class='item_bom_quantity modalRequest_input_style']")
	public WebElement input_consumedQuantityInBom;

	@FindBy(xpath = "//input[@class='btn btn-success']")
	public WebElement button_saveBom;

	@FindBy(xpath = "//input[@class='item_bom_quantity modalRequest_input_style']")
	public List<WebElement> list_inputConsumedQuantityListInBom;

	@FindBy(xpath = "//input[@class='billable-price']")
	public List<WebElement> list_inputBillableCheckboxListInBom;

	@FindBy(xpath = "//input[@class='modalRequest_input_style item_bom_list_price']")
	public List<WebElement> list_inputBillableUnitPriceListInBom;

	@FindBy(xpath = "//input[contains(@class,'modalRequest_input_style item_total_price')]")
	public List<WebElement> list_totalPerItemListInBom;

	@FindBy(xpath = "//input[@id='inpatient_bill_of_material_total_cost']")
	public WebElement input_totalPriceBom;

	@FindBy(xpath = "//input[@id='inpatient_bill_of_material_discount']")
	public WebElement input_discountBom;

	@FindBy(xpath = "//input[@id='inpatient_bill_of_material_total_billing_price']")
	public WebElement input_totalBillingPriceBom;

	@FindBy(xpath = "//div[@class='tray-table-body']/div/div[2]/strong")
	public WebElement text_dateAndTimeBom;


}
