package pages.settings.facilitySettings.general.wardSetup;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_WardSetup extends TestBase {

    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_WardSetup(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//a[text()='Ward Setup']")
    public WebElement link_wardSetup;

    @FindBy(xpath = "//input[@id='ward_name']")
    public WebElement input_wardName;

    @FindBy(xpath = "//input[@id='ward_code']")
    public WebElement input_wardCode;

    @FindBy(xpath = "//input[contains(@class,'room-name')]")
    public WebElement input_roomName;

    @FindBy(xpath = "//select[contains(@class,'room-type')]")
    public WebElement select_roomType;

    @FindBy(xpath = "//option[text()='General Ward']")
    public WebElement option_generalWard;

    @FindBy(xpath = "//input[contains(@class,'room-code')]")
    public WebElement input_roomCode;

    @FindBy(xpath = "//input[contains(@name,'bed_name')]")
    public WebElement input_bedName;

    @FindBy(xpath = "//input[contains(@name,'bed_code')]")
    public WebElement input_bedCode;

    @FindBy(xpath = "//input[contains(@class,'bed-total_beds')]")
    public WebElement input_totalBed;

    @FindBy(xpath = "//input[contains(@class,'bed-bed_price')]")
    public WebElement input_bedPrice;

    @FindBy(xpath = "//input[@name='commit']")
    public WebElement button_saveWard;

    @FindBy(xpath = "//div[@id='manage_wards']//h5")
    public List<WebElement> list_wardNames;

    @FindBy(xpath = "//h4[text()='Add Ward']")
    public WebElement form_addWard;

    @FindBy(xpath = "//a[text() = ' Add Ward']")
    public WebElement button_addWard;

    @FindBy(xpath = "//a[text()=' Schedule Admission']")
    public WebElement button_scheduledAdmission;

    @FindBy(xpath = "//h4[text()='Schedule Admission']")
    public WebElement form_scheduledAdmission;

    @FindBy(xpath = "//label[@for='admission_same_day_hospitalization']")
    public WebElement radioButton_sameDay;

    @FindBy(xpath = "//a[text()='Case Details']")
    public WebElement tab_caseDetails;

    // click plus sign
    @FindBy(xpath = "//input[@value='Schedule Admission']")
    public WebElement button_createAdmission;

    // click on new package
    @FindBy(xpath = "//h4[text()='Assign Bed']")
    public WebElement window_assignBed;

    @FindBy(xpath = "//select[@name='admission[ward_id]']")
    public WebElement dropdown_ward;

    @FindBy(xpath = "//select[@id='admission_ward_id']/option")
    public List<WebElement> list_dropdownWardNames;

    @FindBy(xpath = "//select[@name='admission[room_id]']")
    public WebElement dropdown_room;

    @FindBy(xpath = "//select[@id='admission_room_id']/option")
    public List<WebElement> list_dropdownRoomNames;

    @FindBy(xpath = "//div[contains(@class,'show-beds')]//input[@name='admission[bed_id]']")
    public List<WebElement> list_radioBeds;

    @FindBy(xpath = "//div[contains(@class,'show-beds')]//label[text()=' BE1']")
    public WebElement text_bedName;
    @FindBy(xpath = "//input[@id='save_bed']")
    public WebElement button_saveBed;

    @FindBy(xpath = "//h4[text()='Appointment Details']")
    public WebElement tab_appointmentDetails;

    @FindBy(xpath = "//div[contains(text(),'Bed Assigned')]")
    public WebElement text_assignedBed;

    @FindBy(xpath = "//div[contains(text(),'Bed Assigned')]/parent::div/div[3]")
    public WebElement text_ofAssignedBed;

    @FindBy(xpath = "//a[@title='Remove Bed?']")
    public WebElement icon_removeBed;

    @FindBy(xpath = "//button[text()='Remove Bed']")
    public WebElement button_removeBed;

    @FindBy(xpath = "//div[contains(text(),'Daycare')]")
    public WebElement text_dayCare;

    @FindBy(xpath = "//a[contains(@title,'Delete this admission')]")
    public WebElement link_deleteAdmission;

    @FindBy(xpath = "//button[text()='Delete Admission']")
    public WebElement button_deleteAdmission;

    @FindBy(xpath = "//a[contains(@class,'delete-ward')]")
    public WebElement button_deleteWard;

    @FindBy(xpath = "//h5[text()='Are you sure?']")
    public WebElement header_deleteWard;

    @FindBy(xpath = "//button[text()='Confirm']")
    public WebElement button_confirmDeleteWard;
}
