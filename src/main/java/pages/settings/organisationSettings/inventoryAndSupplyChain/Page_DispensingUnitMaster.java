package pages.settings.organisationSettings.inventoryAndSupplyChain;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_DispensingUnitMaster extends TestBase{
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_DispensingUnitMaster(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
    @FindBy(xpath = "//h4[text()='MANAGE INVENTORY DISPENSING UNIT']")
    public WebElement header_manageInventoryDispensingUnit;

    @FindBy(xpath = "//a[text()=' Add Dispensing Unit']")
    public WebElement button_addDispensingUnit;

    @FindBy(xpath = "//h4[text()='Add Dispensing Unit']")
    public WebElement header_addDispensingUnitTemplateHeader;

    @FindBy(xpath = "//strong[text()='Dispensing Unit Information']")
    public WebElement label_dispensingUnitInformationLabel;

    @FindBy(xpath = "//input[@id='inventory_dispensing_unit_name']")
    public WebElement input_dispensingUnitName;

    @FindBy(xpath = "//label[@id='inventory_dispensing_unit_name-error']")
    public WebElement label_dispensingUnitNameRequiredError;

    @FindBy(xpath = "//input[@placeholder='Search Dispensory Unit']")
    public WebElement input_searchDispensingUnitName;

    @FindBy(xpath = "//tbody[@id='dispensory_unit-list']/tr/td[1]")
    public List<WebElement> list_dispensingUnitNameInDispensingUnitMaster;

    @FindBy(xpath = "//tbody[@id='dispensory_unit-list']/tr/td[2]")
    public List<WebElement> list_dispensingUnitActionsForDispensingUnitName;

    @FindBy(xpath = "//h4[text()='Update Dispensing Unit']")
    public WebElement header_editDispensingUnitTemplateHeader;

}
