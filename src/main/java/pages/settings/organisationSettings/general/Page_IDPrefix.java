package pages.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_IDPrefix extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_IDPrefix(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//ul[@id='generalSettingSubmenu']//a[contains(text(),'ID Prefix')]")
    public WebElement button_idPrefix;

    //IDPrefix Input Box
    @FindBy(xpath = "//input[@id='org_id_prefix']")
    public WebElement input_idPrefixTextBox;

    //Save ID Prefix Button
    @FindBy(xpath = "//input[@data-disable-with='Save ID Prefix']")
    public WebElement button_saveIdPrefix;

    //Verify Saved Successfully Message
    @FindBy(xpath = "//div[@id='org-id-alert-messages']")
    public WebElement alert_saveSuccessfullyMessage;

    //***** CIN Functionality *****//

    @FindBy(xpath = "//input[@id='org_cin']")
    public WebElement input_cinTextBox;

    @FindBy(xpath = "//input[@data-disable-with='Save CIN']")
    public WebElement button_saveCin;

    @FindBy(xpath = "//div[@id='cin-alert-messages']//strong")
    public WebElement alert_saveCinMessage;


}