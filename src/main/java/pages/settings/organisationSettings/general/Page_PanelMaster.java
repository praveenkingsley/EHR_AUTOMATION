package pages.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_PanelMaster extends TestBase {
    public Page_PanelMaster(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),'Add Panel')]")
    public WebElement button_addPanel;

    @FindBy(xpath = "//h4[contains(text(),'Add Panel')]")
    public WebElement text_addPanelLabel;

    @FindBy(xpath = "//input[@id='finance_panel_master_name']")
    public WebElement inputField_panelName;

    @FindBy(xpath = "//input[@id='finance_panel_master_description']")
    public WebElement inputField_panelDescription;

    @FindBy(xpath = "//input[@placeholder='Search By Panel Name']")
    public WebElement search_panelName;

    @FindBy(xpath = "//tbody[@id='panel_master-list']/tr/td[1]/b")
    public List<WebElement> list_panelName;

    @FindBy(xpath = "//tbody[@id='panel_master-list']/tr/td[2]/b")
    public List<WebElement> list_panelDescription;
    @FindBy(xpath = "//tbody[@id='panel_master-list']//td[contains(@style,'visible')]//a[text()='Edit']")
    public List<WebElement> list_editPanelButton;
    @FindBy(xpath = "//tbody[@id='panel_master-list']//div[text()='Disable']")
    public List<WebElement> list_disablePanelButton;
    @FindBy(xpath = "//tbody[@id='panel_master-list']//a[text()='Active']")
    public List<WebElement> list_activePanelButton;

    @FindBy(xpath = "//select[@id='payer_master_contact_group_id']")
    public WebElement select_contactGroup;

    @FindBy(xpath = "//span[@id='select2-payer_master_payer_type_id-container']")
    public WebElement field_payerTypeMaster;

    @FindBy(xpath = "//ul[@id='select2-payer_master_payer_type_id-results']/li")
    public List<WebElement> list_payerTypeMaster;

}
