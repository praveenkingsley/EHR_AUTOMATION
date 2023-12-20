package pages.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_DispensaryMaster extends TestBase {

    public Page_DispensaryMaster(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),' Add Dispensary')]")
    public WebElement button_addDispensary;

    @FindBy(xpath = "//h4[contains(text(),'Add Dispensary')]")
    public WebElement text_addDispensaryLabel;

    @FindBy(xpath = "//input[@id='finance_dispensary_master_name']")
    public WebElement inputField_dispensaryName;

    @FindBy(xpath = "//input[@id='finance_dispensary_master_description']")
    public WebElement inputField_dispensaryDescription;

    @FindBy(xpath = "//input[@placeholder='Search By Dispensary Name']")
    public WebElement search_dispensaryName;

    @FindBy(xpath = "//tbody[@id='dispensary_master-list']/tr/td[1]/b")
    public List<WebElement> list_dispensaryName;

    @FindBy(xpath = "//tbody[@id='dispensary_master-list']/tr/td[2]/b")
    public List<WebElement> list_dispensaryDescription;

    @FindBy(xpath = "//tbody[@id='dispensary_master-list']//td[contains(@style,'visible')]//a[text()='Edit']")
    public List<WebElement> list_editButton;
    @FindBy(xpath = "//tbody[@id='dispensary_master-list']//div[text()='Disable']")
    public List<WebElement> list_disableButton;
    @FindBy(xpath = "//tbody[@id='dispensary_master-list']//a[text()='Active']")
    public List<WebElement> list_activeButton;

}
