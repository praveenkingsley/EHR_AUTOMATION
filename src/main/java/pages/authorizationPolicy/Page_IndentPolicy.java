package pages.authorizationPolicy;


import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_IndentPolicy extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_IndentPolicy(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),'New')]//i[@class='fa fa-plus']")
    public WebElement button_indentNew;
    @FindBy(xpath = "//input[@id='inventory_order_indent_indent_date']")
    public WebElement input_indentOrderDateField;
    @FindBy(xpath = "//input[@id='inventory_order_indent_indent_time_picker']")
    public WebElement input_indentOrderTimeField;
    @FindBy(xpath = "//a[text()=' New Order']")
    public WebElement button_newIndentOrder;


}
