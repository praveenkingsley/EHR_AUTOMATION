package pages.ot.forms.intraOperative;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class Page_BillOfMaterialUpdate extends TestBase {
    private WebDriver driver;

    public Page_BillOfMaterialUpdate(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //This xpath need to be updated
    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//li[@style='text-align: center']")
    public WebElement button_viewBillOfMaterial;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_closeTemplate;

    @FindBy(xpath = "//a[text()='Edit']")
    public WebElement button_editBillOfMaterial;

    @FindBy(xpath = "//input[@value='Update Changes']")
    public WebElement button_updateChangesInBillOfMaterial;


}
