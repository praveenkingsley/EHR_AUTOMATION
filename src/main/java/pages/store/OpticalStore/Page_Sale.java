package pages.store.OpticalStore;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Sale extends TestBase {
    private WebDriver driver;
    public Page_Sale(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = " //div[@class='mainpanel']//h4")
    public WebElement header_panelOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li/a")
    public List<WebElement> list_ParentOptionsOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li/a/span")
    public List<WebElement> list_namesParentOptionsOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li//li")
    public List<WebElement> list_ChildOptionsOnLeft;

    @FindBy(xpath = "//a[contains(text(),'Place')]")
    public WebElement button_PlaceOrder;

    @FindBy(xpath = "(//div[@class='col-lg-2 font_size'])[1]")
    public WebElement text_Patient;

    @FindBy(xpath = "(//div[contains(@class,'col-sm-5 font_size')])[5]")
    public  WebElement text_AdvanceReceived;

    @FindBy(xpath = "(//div[contains(@class,'col-sm-5 font_size')])[6]")
    public  WebElement text_PaymentReceived;

    @FindBy(xpath = "//table[@class='table table table-striped table-bordered']//td[2]")
    public WebElement text_Quantity;

    @FindBy(xpath = "(//div[@class='col-lg-2 font_size'])[8]")
    public WebElement text_ActualDeliveredDate;



}
