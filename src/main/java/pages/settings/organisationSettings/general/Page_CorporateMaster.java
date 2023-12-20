package pages.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_CorporateMaster extends TestBase {
    public Page_CorporateMaster(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),'Add Corporate')]")
    public WebElement button_addCorporate;

    @FindBy(xpath = "//h4[contains(text(),'Add Corporate')]")
    public WebElement text_addCorporateLabel;

    @FindBy(xpath = "//input[@id='finance_corporate_master_name']")
    public WebElement inputField_corporateName;

    @FindBy(xpath = "//input[@id='finance_corporate_master_description']")
    public WebElement inputField_corporateDescription;

    @FindBy(xpath = "//input[@placeholder='Search By Corporate Name']")
    public WebElement search_corporateName;

    @FindBy(xpath = "//tbody[@id='corporate_master-list']/tr/td[1]/b")
    public List<WebElement> list_corporateName;

    @FindBy(xpath = "//tbody[@id='corporate_master-list']/tr/td[2]/b")
    public List<WebElement> list_corporateDescription;

    @FindBy(xpath = "//tbody[@id='corporate_master-list']//td[contains(@style,'visible')]//a[text()='Edit']")
    public List<WebElement> list_editButton;
    @FindBy(xpath = "//tbody[@id='corporate_master-list']//div[text()='Disable']")
    public List<WebElement> list_disableButton;
    @FindBy(xpath = "//tbody[@id='corporate_master-list']//a[text()='Active']")
    public List<WebElement> list_activeButton;

}
