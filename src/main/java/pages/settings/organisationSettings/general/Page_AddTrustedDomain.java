package pages.settings.organisationSettings.general;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_AddTrustedDomain extends TestBase{

    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_AddTrustedDomain(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//div[@id='trusted_domains']//a[contains(@data-target,'trusted-domain')]")
    public WebElement button_addNewDomain;

    @FindBy(xpath = "//input[@id='trusted_domain_name']")
    public WebElement input_domainName;

    @FindBy(xpath = "//input[@value='Add Domain']")
    public WebElement button_addDomainModal;

    @FindBy(xpath = "//div[@id='trusted-domain-table']/table//tr/td[2]")
    public List<WebElement> list_domainNamesTable;

    @FindBy(xpath = "//div[@id='trusted-domain-table']/table//tr/td[3]")
    public List<WebElement> list_actionsStatus;

    @FindBy(xpath = "//div[@id='trusted-domain-table']/table//tr//a[text()='Disable']")
    public List<WebElement> list_button_disableDomain;

    @FindBy(xpath = "//div[@id='trusted-domain-table']/table//tr//a[text()='Remove']")
    public List<WebElement> list_button_removeDomain;

    @FindBy(xpath = "//div[@id='trusted-domain-table']/table//tr//a[text()='Enable']")
    public List<WebElement> button_enableDomain;


}
