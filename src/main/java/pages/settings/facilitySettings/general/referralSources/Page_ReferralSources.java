package pages.settings.facilitySettings.general.referralSources;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Page_ReferralSources extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_ReferralSources(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@id='add-sub-referral']")
    public WebElement button_AddSubReferral;

    @FindBy(xpath = "//select[@id='sub_referral_type_referral_type_id']")
    public WebElement select_ReferralTypeOnModal;

    @FindBy(xpath = "//select[@id='sub_referral_type_facility_ids']")
    public WebElement select_FacilityOnModal;

    @FindBy(xpath = "//input[@id='sub_referral_type_name']")
    public WebElement input_SubReferral_Name;

    @FindBy(xpath = "//input[@id='sub_referral_type_mobile_number']")
    public WebElement input_SubReferral_Mobile;

    @FindBy(xpath = "//input[@id='sub_referral_type_email']")
    public WebElement input_SubReferral_Email;

    @FindBy(xpath = "//input[@id='sub_referral_type_designation']")
    public WebElement input_SubReferral_Designation;

    @FindBy(xpath = "//input[@id='sub_referral_type_speciality']")
    public WebElement input_SubReferral_Speciality;

    @FindBy(xpath = "//input[@id='sub_referral_type_location']")
    public WebElement input_SubReferral_Location;

    @FindBy(xpath = "//input[@id='sub_referral_type_city']")
    public WebElement input_SubReferral_City;

    @FindBy(xpath = "//input[@id='sub_referral_type_state']")
    public WebElement input_SubReferral_State;

    @FindBy(xpath = "//span[@title='Campaign Type']")
    public WebElement selectButton_CampaignOnModal;

    @FindBy(xpath = "//ul[@class='select2-results__options']/li")
    public List<WebElement> list_selectOptionsForCampaignOnModal;

    @FindBy(xpath = "//input[@id='sub_referral_type_comment']")
    public WebElement input_SubReferral_Comment;

    @FindBy(xpath = "//div[@class='modal-footer']/input[@value='Save']")
    public WebElement button_SaveOnModal;

    @FindBy(xpath = "(//div[@class='modal-footer']/button[text()='Confirm'])")
    public List<WebElement> button_DeleteOnModal;

    @FindBy(xpath = "//div[@role='tabpanel']//select[@id='referral_type']")
    public WebElement select_ReferralType;

    @FindBy(xpath = "//tbody[@id='sub-referral-list']/tr/td[2]/b")
    public List<WebElement> list_SubSourceValuesOnTable;

    @FindBy(xpath = "//tbody[@id='sub-referral-list']/tr/td[3]/a[text()='Edit']")
    public List<WebElement> list_EditButtonsOnTable;

    @FindBy(xpath = "//tbody[@id='sub-referral-list']/tr/td[3]/a[text()='Delete']")
    public List<WebElement> list_DeleteButtonsOnTable;
}
