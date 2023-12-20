package pages.store.PharmacyStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Vendors extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Vendors(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//nav/div/div[2]/div")
    public WebElement button_addVendor;
    @FindBy(xpath = "//a[@id='edit_vendor']")
    public WebElement button_editVendor;
    @FindBy(xpath = "//a[@id='delete_vendor']")
    public WebElement button_disableVendor;
    @FindBy(xpath = "//a[@id='enable_vendor']")
    public WebElement button_enableVendor;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[5]")
    public List<WebElement> list_statusOfVendors;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody//td[1]")
    public List<WebElement> list_nameOfVendors;
    @FindBy(xpath = "//table[@class='table inventory_table_center']/tbody/tr")
    public List<WebElement> list_fullRow;

    // add vendor form
    @FindBy(xpath = "//input[@id='inventory_vendor_name']")
    public WebElement input_fullName;
    @FindBy(xpath = "//input[@id='inventory_vendor_mobile_number']")
    public WebElement input_mobileNumber;
    @FindBy(xpath = "//select[@id='inventory_vendor_vendor_group_id']")
    public WebElement dropdown_vendorType;
    @FindBy(xpath = "//select[@id='inventory_vendor_country_id']")
    public WebElement dropdown_country;
    @FindBy(xpath = "//input[@id='inventory_vendor_address']")
    public WebElement input_address;
    @FindBy(xpath = "//input[@onclick='validate_inventory_vendor_form()']")
    public WebElement button_saveVendor;


}
