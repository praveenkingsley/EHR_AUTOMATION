package pages.Sprint71;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_HEAL6023 extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_HEAL6023(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//div[@class='btn-group']//button[contains(text(),'1 D')]")
    public WebElement button_1DayOption;

    @FindBy(xpath = "//div[@id='advice']/div[3]/div/div[2]")
    public WebElement text_1DayTextDisplayingUnderFollowupDetailsInTempPreview;

    @FindBy(xpath = "//button[text()='Retry']")
    public WebElement button_RetrySaveTemplate;

}
