package pages.settings.facilitySettings.general.otSetup;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_OtSetup extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_OtSetup(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//span[@id='select2-ot_rooms_facility_dropdown-container']")
    public WebElement select_facilityForOtSetup;
    @FindBy(xpath = "//ul[@class='select2-results__options']//li")
    public List<WebElement> list_facilityNameFromDropdownForOtSetup;
    @FindBy(xpath = "//span[@id='select2-ot_rooms_facility_id-container']")
    public WebElement select_facilityForAddOtRoom;

    @FindBy(xpath = "//a[contains(text(),' Add OT')]//i")
    public WebElement button_addOt;
    @FindBy(xpath = "//input[@name='ot_rooms[0][name]']")
    public WebElement input_oTName1;

    @FindBy(xpath = "//input[@name='ot_rooms[1][name]']")
    public WebElement input_oTName2;
    @FindBy(xpath = "//input[@name='ot_room[name]']")
    public WebElement input_oTNameUpdate;
    @FindBy(xpath = "//span[@id='select2-ot_rooms_0_specialty_id-container']")
    public WebElement select_specialityForOtSetup;
    @FindBy(xpath = "//input[@id='ot_rooms_0_capacity']")
    public WebElement input_capacity1;
    @FindBy(xpath = "//input[@id='ot_rooms_1_capacity']")
    public WebElement input_capacity2;

    @FindBy(xpath = "//button[@id='btn-add-ot-room']")
    public WebElement button_addRoom;

    @FindBy(xpath = "//button[@id='ot_rooms_1_remove']")
    public WebElement button_deleteOtRoom;

    @FindBy(xpath = "//input[@type='submit']")
    public WebElement button_saveOtRoom;

    @FindBy(xpath = "//div[@id='ot-room-table']//tr/td[1]")
    public List<WebElement> list_OtName;

    @FindBy(xpath = "//div[@id='ot-room-table']//tr/td[4]/a[1]")
    public List<WebElement> list_editButtonInOtSetUpPage;

    @FindBy(xpath = "//div[@id='ot-room-table']//tr/td[4]/a[2]")
    public List<WebElement> list_deleteButtonInOtSetUpPage;

    @FindBy(xpath = "//button[contains(text(),'Confirm')]")
    public WebElement button_cancelOtName;

    @FindBy(xpath = "//a[text()=' Schedule OT']")
    public WebElement button_scheduleOt;

    @FindBy(xpath = "//span[@id='select2-ot_schedule_theatreno-container']")
    public WebElement select_operationTheaterInNewOtForm;

    @FindBy(xpath = "//button[contains(text(),'Close')]")
    public WebElement button_closeNewOtForm;

    @FindBy(xpath = "//a[@title='Cancel Appointment']")
    public WebElement button_cancelAppointment;


}
