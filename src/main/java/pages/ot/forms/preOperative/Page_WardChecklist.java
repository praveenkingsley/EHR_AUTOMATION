package pages.ot.forms.preOperative;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_WardChecklist extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_WardChecklist(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//div[text()='Pre-Operative']/following-sibling::div//a[contains(text(),'Ward Checklist')]")
    public WebElement button_wardChecklistInPreOperative;

}