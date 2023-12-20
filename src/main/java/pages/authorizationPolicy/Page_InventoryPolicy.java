package pages.authorizationPolicy;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_InventoryPolicy extends TestBase {

    public Page_InventoryPolicy(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//div[@class='user_policy_name']")
    public WebElement text_userNameOnPolicy;

    @FindBy(xpath = "//div[@id='UserPolicyWizard']/ul/li/a")
    public List<WebElement> list_typeOfPolicies;

    @FindBy(xpath = "//div[@class='tab-content']//div[@class='tab-pane active']//div[@class='policy user-policy']/div[@class='policy-header']/span")
    public List<WebElement> list_inventoryPolicyComponentHeader;
    @FindBy(xpath = "//div[@class='tab-content']//div[@class='tab-pane  active']//div[@class='policy user-policy']/div[@class='policy-header']/span")
    public List<WebElement> list_otherPolicyComponentHeader;

    @FindBy(xpath = "//div[@id='UserPolicyWizard']/div/div[@class='tab-pane active']//div[@class='policy user-policy']/div[2]")
    public List<WebElement> list_InventoryPolicyComponent;

    @FindBy(xpath = "//div[@id='UserPolicyWizard']/div/div[@class='tab-pane  active']//div[@class='policy user-policy']/div[2]")
    public List<WebElement> list_otherPolicyComponent;

    @FindBy(xpath = "//div[@id='UserPolicyWizard']/div/div[@class='tab-pane active']//div[@class='policy user-policy']/div[2]/div/div/div")
    public List<WebElement> list_policyDescription;

    @FindBy(xpath = "//li/a[text()='INVENTORY']")
    public WebElement button_InventoryPolicy;

    @FindBy(xpath = "//input[@onclick='validate_user_policy_form()']")
    public WebElement button_SaveChanges;

}
