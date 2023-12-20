package pages.settings.facilitySettings.clinical.facilityAdviceSet;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Page_FacilityAdviceSet extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_FacilityAdviceSet(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//div[@id='advice_set_organisation']//a[contains(text(),'Advice Set')]")
    public WebElement button_adviceSet;

    //search
    @FindBy(xpath = "//div[@id='advice-set-list_filter']//input")
    public WebElement input_searchAdviceSet;

    //Find the created advice set in the table
    @FindBy(xpath = "//table[@id='advice-set-list']/tbody/tr/td[@class='sorting_1']")
    public List<WebElement> list_adviceSetsName;

    @FindBy(xpath = "//table[@id='advice-set-list']/tbody/tr/td[3]//i[@class='fa fa-trash-alt']")
    public List<WebElement> list_adviceSetDeleteButton;

    @FindBy(xpath = "//button[text()='Confirm']")
    public WebElement button_confirmDeleteAdviceSet;

    @FindBy(xpath = "//table[@id='advice-set-list']/tbody/tr/td[3]//i[@class='fa fa-pencil-square-o']")
    public List<WebElement> list_adviceSetEditButton;

    @FindBy(xpath = "//input[@value='Update Advice Set']")
    public WebElement button_updateAdviceSet;

    //fill the details for new advice set
    @FindBy(xpath = "//input[@id='advice_set_name']")
    public WebElement input_adviceSetName;

    @FindBy(xpath = "//h4[text()='Advice Set']")
    public WebElement header_adviceSet;

    @FindBy(xpath = "//select[@id='advice_set_specialty_id']")
    public WebElement dropdown_selectSpeciality;

    @FindBy(xpath = "//select[@id='advice_set_specialty_id']/option")
    public List<WebElement> list_dropdownSpeciality;

    @FindBy(xpath = "//select[@id='advice_set_lcid_code']/option")
    public List<WebElement> list_LanguageSet;

    @FindBy(xpath = "//div[@class='note-editing-area']//div[contains(@class,'note-editable')]")
    public WebElement input_adviceSetContent;

    @FindBy(xpath = "//input[@id='create-advice-set']")
    public WebElement button_createAdviceSet;


}