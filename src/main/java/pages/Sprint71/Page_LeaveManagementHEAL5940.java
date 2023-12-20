package pages.Sprint71;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_LeaveManagementHEAL5940 extends TestBase {
    private WebDriver driver;

    public Page_LeaveManagementHEAL5940(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//h5[contains(text(),'Upcoming Plan')]")
    public WebElement header_upcomingPlanHeader;
    @FindBy(xpath = "//span[text()='No Plans Added']")
    public WebElement text_upcomingNoPlanText;
    @FindBy(xpath = "//div[@class='row toggle-view holiday-rows']")
    public WebElement text_upcomingPlansDataRow;

    @FindBy(xpath = "//div[@class='row toggle-view holiday-rows']/div/div[1]//b")
    public List<WebElement> list_upcomingPlanTableHeaderList;
    @FindBy(xpath = "//div[@class='row toggle-view holiday-rows']/div/div")
    public List<WebElement> list_upcomingPlansList;
    @FindBy(xpath = "//h5[contains(text(),'Past Plans : ')]")
    public WebElement header_pastPlanHeader;

    @FindBy(xpath = "//h5[contains(text(),'Past Plans : ')]/following-sibling::div[1]//b")
    public List<WebElement> list_pastPlanTableHeaderList;
    @FindBy(xpath = "//h5[contains(text(),'Past Plans : ')]/following-sibling::div")
    public List<WebElement> list_pastPlansList;
    @FindBy(xpath = "//a[@class='btn btn-link btn-minimize-view pull-right']/b")
    public WebElement button_addOrEditUpcomingPlan;
    @FindBy(xpath = "//h4[text()='Plan Holidays/Leave/Meetings']")
    public WebElement header_addOrEditPlanTemplate;
    @FindBy(xpath = "//div[contains(text(),'No Plans Added')]")
    public WebElement text_noPlanAddedInAddEditPlanTemplate;
    @FindBy(xpath = "//b[text()='Upcoming Leave & Meetings']")
    public WebElement text_upcomingPlanTextInAddEditPlanTemplate;
    @FindBy(xpath = "//form[@class='new_holiday_plan']/div/div/b")
    public List<WebElement> list_addPlanHeaderListInAddPlanTemplate;
    @FindBy(xpath = "//select[@name='holiday_plan[plan]']")
    public WebElement select_selectPlanTypeInAddPlan;
    @FindBy(xpath = "//input[@name='holiday_plan[start_date]']")
    public WebElement input_startDateInAddPlan;
    @FindBy(xpath = "//input[@name='holiday_plan[start_time]']")
    public WebElement input_startTimeInAddPlan;
    @FindBy(xpath = "//input[@name='holiday_plan[end_date]']")
    public WebElement input_endDateInAddPlan;
    @FindBy(xpath = "//input[@name='holiday_plan[end_time]']")
    public WebElement input_endTimeInAddPlan;
    @FindBy(xpath = "//input[@name='holiday_plan[reason]']")
    public WebElement input_reasonTextBoxInAddPlan;
    @FindBy(xpath = "//input[@value='+ Plan']")
    public WebElement input_savePlanInAddPlan;
    @FindBy(xpath = "//button[text()='Clear']")
    public WebElement input_clearAddPlanInAddPlan;
    @FindBy(xpath = "//label[text()='Plan cannot be Blank']")
    public WebElement label_planTypeRequiredErrorMessage;
    @FindBy(xpath = "//label[text()='Start Date cannot be Blank']")
    public WebElement label_startDateRequiredErrorMessage;
    @FindBy(xpath = "//label[text()='Start Time cannot be Blank']")
    public WebElement label_startTimeRequiredErrorMessage;
    @FindBy(xpath = "//label[text()='End Date cannot be Blank']")
    public WebElement label_endDateRequiredErrorMessage;
    @FindBy(xpath = "//label[text()='End Time cannot be Blank']")
    public WebElement label_endTimeRequiredErrorMessage;
    @FindBy(xpath = "//td[contains(@class,' ui-datepicker-days-cell-over')]/a")
    public WebElement date_currentDateHighlighted;
    @FindBy(xpath = "//td[contains(@class,' ui-datepicker-days-cell-over')]/following-sibling::td[1]")
    public WebElement date_nextDaytoCurrentDate;

    @FindBy(xpath = "(//td[@data-handler='selectDay'])[1]/a")
    public WebElement date_nextMonthFirstDay;

    @FindBy(xpath = "//span[text()='Next']")
    public WebElement button_nextMonthButton;

    @FindBy(xpath = "//span[@class='ui-datepicker-month']")
    public WebElement month_currentMonthNameInDatePicker;
    @FindBy(xpath = "//span[@class='ui-datepicker-year']")
    public WebElement year_currentYearNameInDatePicker;

    @FindBy(xpath = "//td[@data-handler='selectDay']/a")
    public List<WebElement> list_activeDateInCurrentMonth;
    @FindBy(xpath = "//input[@class='bootstrap-timepicker-hour']")
    public WebElement input_hourFieldForTime;
    @FindBy(xpath = "//input[@class='bootstrap-timepicker-minute']")
    public WebElement input_minuteFieldForTime;
    @FindBy(xpath = "//input[@class='bootstrap-timepicker-meridian']")
    public WebElement input_meridianFieldForTime;
    @FindBy(xpath = "//b[text()='Upcoming Leave & Meetings']/parent::div/following-sibling::div[contains(@class,'row')]")
    public List<WebElement> list_activeUpcomingPlanListInAddTemplate;
    @FindBy(xpath = "//b[text()='Upcoming Leave & Meetings']/parent::div/following-sibling::div[1]//b")
    public List<WebElement> list_activeUpcomingPlanHeaderListInAddTemplate;
    @FindBy(xpath = "//a[@title='Break']")
    public WebElement leave_breakTypeLeaveSlot;
    @FindBy(xpath = "//div[text()='Doctor not available on selected slot, Please select available time slot.']")
    public WebElement message_slotInformationFlashMessage;
    @FindBy(xpath = "//form[@class='edit_holiday_plan']")
    public WebElement form_editHolidayPlanForm;
    @FindBy(xpath = "//form[@class='edit_holiday_plan']/div[1]//b")
    public List<WebElement> list_editPlanHeaderListInEditPlanForm;
    @FindBy(xpath = "//form[@class='edit_holiday_plan']//select[@name='holiday_plan[plan]']")
    public WebElement select_selectEditPlanTypeInEditPlan;
    @FindBy(xpath = "//form[@class='edit_holiday_plan']//input[@name='holiday_plan[start_date]']")
    public WebElement input_editStartDateInEditPlan;
    @FindBy(xpath = "//form[@class='edit_holiday_plan']//input[@name='holiday_plan[start_time]']")
    public WebElement input_editStartTimeInEditPlan;
    @FindBy(xpath = "//form[@class='edit_holiday_plan']//input[@name='holiday_plan[end_date]']")
    public WebElement input_editEndDateInEditPlan;
    @FindBy(xpath = "//form[@class='edit_holiday_plan']//input[@name='holiday_plan[end_time]']")
    public WebElement input_editEndTimeInEditPlan;
    @FindBy(xpath = "//form[@class='edit_holiday_plan']//input[@name='holiday_plan[reason]']")
    public WebElement input_editReasonTextBoxInEditPlan;
    @FindBy(xpath = "//input[@value='Update']")
    public WebElement input_updatePlanButtonInEditPlan;
    @FindBy(xpath = "//td[@class='  ui-datepicker-current-day']/following-sibling::td[1]")
    public WebElement date_editDateSelection;

    @FindBy(xpath = "//td[@class='  ui-datepicker-current-day']/following-sibling::td[1]")
    public WebElement date_editEndDateSelection;
}
