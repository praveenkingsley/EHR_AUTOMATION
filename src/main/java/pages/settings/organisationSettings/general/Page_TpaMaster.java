package pages.settings.organisationSettings.general;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_TpaMaster {
    public Page_TpaMaster(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),'Add Tpa')]")
    public WebElement button_addTPA;

    @FindBy(xpath = "//h4[contains(text(),'Add Tpa')]")
    public WebElement text_addTpaLabel;

    @FindBy(xpath = "//input[@id='finance_tpa_master_name']")
    public WebElement inputField_tpaName;

    @FindBy(xpath = "//input[@id='finance_tpa_master_description']")
    public WebElement inputField_tpaDescription;

    @FindBy(xpath = "//input[@placeholder='Search By Tpa Name']")
    public WebElement search_tpaName;

    @FindBy(xpath = "//tbody[@id='tpa_master-list']/tr/td[1]/b")
    public List<WebElement> list_tpaName;

    @FindBy(xpath = "//tbody[@id='tpa_master-list']/tr/td[2]/b")
    public List<WebElement> list_tpaDescription;

    @FindBy(xpath = "//tbody[@id='tpa_master-list']//td[contains(@style,'visible')]//a[text()='Edit']")
    public List<WebElement> list_editTpaButton;

    @FindBy(xpath = "//tbody[@id='tpa_master-list']//div[text()='Disable']")
    public List<WebElement> list_disableTpaButton;
    @FindBy(xpath = "//tbody[@id='tpa_master-list']//a[text()='Active']")
    public List<WebElement> list_activeTpaButton;

}
