package pages.settings.organisationSettings.inventoryAndSupplyChain;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_TermsAndConditions extends TestBase {
    public Page_TermsAndConditions(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//a[@class='btn btn-primary'][normalize-space()='Terms & Conditions']")
    public WebElement button_addTermsAndConditions;
    @FindBy(xpath = "//b[text()='Delivery Terms']/parent::td/preceding-sibling::td")
    public List<WebElement> text_descriptionsDeliveryTerms;
    @FindBy(xpath = "//b[text()='Payment Terms']/parent::td/preceding-sibling::td")
    public List<WebElement> text_descriptionsPaymentTerms;
}
