package pages.ot.forms.consent;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_AdmissionInConsent extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_AdmissionInConsent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }


    // Admission -  Consent Section
    @FindBy(xpath = "//div[text()='Consents']/following-sibling::div//a[contains(text(),'Admission')]")
    public WebElement button_admissionInConsent;


}