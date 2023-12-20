package tests.Sprint71;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.Sprint71.Page_LeaveManagementHEAL5940;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.dashboard.Page_Dashboard;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.templates.eye.Page_EyeTemplate;
import pages.ipd.Page_IPD;
import pages.ipd.forms.postOperative.Page_DischargeForm;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_IDPrefix;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Transaction.Page_PurchaseReturn;
import tests.inventoryStores.otStore.patientQueueTrayTest;

import java.util.ArrayList;
import java.util.List;

import static pages.commonElements.CommonActions.oEHR_Data;

public class leaveManagementHEAL5940Test extends TestBase {
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    boolean enableTimeSlot = false ;
    static Model_Patient myPatient;

    public int totalUpcomingPlan = -1;
    int totalPastPlan = -1;

    String[] actualAddEditPlanHeaderTextList = {"Plan Type" ,"Start Date","Start Time","End Date","End Time","Reason","Actions"};
    String month = "";
    String year = "";
    String date = "";

    String startHour = "02";
    String startMinute = "40";
    String meridian = "PM";
    String endMinute = "45";
    String reason = "Break Type";
    String editStartMinute = "50";
    String editEndMinute = "55";
    String editReason = " Edit Break Type Reason";
    String expectedDate ="";
    String expectedTime ="";
    String upcomingPlanActionInDashboardAndTemplate = "delete";
    String upcomingPlanActionInAddTemplate = "Edit";

    String[] patientTemplatesList = {"Eye","Post OP"};
    String[] actualPlanTypeSelectList = {"Planned Leave" ,"Meeting","Sick Leave","Emergency Leave","Personal Leave","Break"};

    String nextDateDisabledText = " ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled";


    @Test(enabled = true, description = "Validating Enable Slot Setting In Org Setting")
    public void validatingEnableSlotSettingInOrgSetting() {

        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_IDPrefix oPage_IDPrefix = new Page_IDPrefix(driver);


        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_orgSettingsButton, 10);
                Cls_Generic_Methods.scrollToElementByAction(driver, oPage_OrganisationSetup.button_orgSettingsButton);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_orgSettingsButton),
                        " Org.Settings Button clicked");
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.header_editOrganisationSettingHeader, 12),
                        " Edit Organisation Setting Template Opened");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_OrganisationSetup.label_enableTimeSlotsLabel),
                        "Enable Time Slot Label Displayed");
                // Getting  Slot is Enabled Or Disabled
                enableTimeSlot = Cls_Generic_Methods.radioButtonIsSelected(oPage_OrganisationSetup.input_timeSlotYesRadioButton);
                if(enableTimeSlot){
                    m_assert.assertTrue(" Time slot is Enabled , Yes Radio Button is Selected In Enable Time Slot  ");
                }
                else{
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,oPage_OrganisationSetup.input_timeSlotYesRadioButton),
                            "Yes Button Clicked , Time Slot is Enabled ");
                    Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_saveUser);
                    enableTimeSlot = true;
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_closeTemplateWithoutSaving,5);
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        " Close button clicked , Org.Setting Template closed ");
                Cls_Generic_Methods.customWait();

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Add Leave Template")
    public void validatingAddNewLeaveTemplate() {

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_Dashboard oPage_Dashboard = new Page_Dashboard(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {

                try {
                    // Validate Dashboard Opened & displayed
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.logo_FF_EHS);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Dashboard.panels_DashboardPanels, 5),
                            "Validate panels on Dashboard are displayed.");

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Exception while validating Dashboard Opened & displayed. " + e);
                }

                Cls_Generic_Methods.scrollToElementByJS(oPage_LeaveManagementHEAL5940.button_addOrEditUpcomingPlan);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.header_upcomingPlanHeader),
                        " Upcoming Holiday Header Displayed");

                String upcomingPlanFullText = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.header_upcomingPlanHeader);
                String[] arrOfUpcomingPlanText = upcomingPlanFullText.split("[\r\n]+");
                String[] upcomingPlanText = arrOfUpcomingPlanText[0].split(" : ");
                totalUpcomingPlan = Integer.parseInt(upcomingPlanText[1]);
                if(arrOfUpcomingPlanText[0].equalsIgnoreCase("UPCOMING PLANS : 0")){
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_upcomingNoPlanText),
                            "No Plan Added Text is Displayed , No Upcoming Plan Available");

                }
                else{
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_upcomingPlansDataRow),
                            " Upcoming Plan Displayed , Upcoming plan are present in list");
                    m_assert.assertTrue(oPage_LeaveManagementHEAL5940.list_upcomingPlansList.size()-1 == totalUpcomingPlan,
                            " Upcoming Plan Count at heading and Upcoming Plan raw are same and display correctly as :<b>"+totalUpcomingPlan+"</b>");
                }

                String pastPlanFullText = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.header_pastPlanHeader);
                String[] arrOfPastPlanText = pastPlanFullText.split("[\r\n]+");
                if(arrOfPastPlanText[0].equalsIgnoreCase("PAST PLANS : 0")){

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_upcomingNoPlanText),
                            "No Plan Added Text is Displayed , No Past Plan Available");
                }
                else{
                    String[] pastPlanText = arrOfPastPlanText[0].split(" : ");
                    totalPastPlan = Integer.parseInt(pastPlanText[1]);
                    m_assert.assertTrue(oPage_LeaveManagementHEAL5940.list_pastPlansList.size()-1 == totalPastPlan,
                            " Past Plan Count at heading and Past Plan raw are same and display correctly as : <b>"+totalPastPlan+"</b>");
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.button_addOrEditUpcomingPlan),
                        "Add/Edit Leave Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_LeaveManagementHEAL5940.header_addOrEditPlanTemplate,5);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.header_addOrEditPlanTemplate),
                        " Add/Edit Plan Template Opened , Heading displayed as <b> : Plan Holidays/Leave/Meetings</b>");

                if(totalUpcomingPlan == 0){
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_noPlanAddedInAddEditPlanTemplate),
                            " No Plan Added Message Displayed correctly as there is no upcoming plan");
                }else {
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_upcomingPlanTextInAddEditPlanTemplate),
                            " Existing Plans Displayed , Plan Text Heading as <b>Upcoming Leave & Meetings</b>");
                }

                int indexOfAddPlanHeaders = -1;

                for(WebElement addPlanHeaders : oPage_LeaveManagementHEAL5940.list_addPlanHeaderListInAddPlanTemplate){
                    indexOfAddPlanHeaders = oPage_LeaveManagementHEAL5940.list_addPlanHeaderListInAddPlanTemplate.indexOf(addPlanHeaders);
                    if(Cls_Generic_Methods.getTextInElement(addPlanHeaders).equalsIgnoreCase(actualAddEditPlanHeaderTextList[indexOfAddPlanHeaders])){
                        m_assert.assertTrue(actualAddEditPlanHeaderTextList[indexOfAddPlanHeaders] +"  Header Present in Add Plan Template");
                    }
                    else{
                        m_assert.assertFalse(actualAddEditPlanHeaderTextList[indexOfAddPlanHeaders] +"  Header Present in not Add Plan Template");

                    }

                }

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.select_selectPlanTypeInAddPlan),
                        " Select Plan Type Text box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.input_startDateInAddPlan),
                        " Start Date Text Box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.input_startTimeInAddPlan),
                        " Start Time Text box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.input_endDateInAddPlan),
                        " End Date Text box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.input_endTimeInAddPlan),
                        " End Time Text box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.input_reasonTextBoxInAddPlan),
                        " Reason Text box Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.input_savePlanInAddPlan),
                        " +Plan Button Displayed to create plan");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.input_clearAddPlanInAddPlan),
                        " Clear Button Displayed to clear add plan field");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.input_clearAddPlanInAddPlan),
                        "Clear button clicked in add plan");

                //Validating Mandatory Field Error Message

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_LeaveManagementHEAL5940.input_savePlanInAddPlan,2);
                Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.input_savePlanInAddPlan);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_LeaveManagementHEAL5940.label_planTypeRequiredErrorMessage,2);
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.label_planTypeRequiredErrorMessage),
                        "Plan Type Required Error Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.label_startDateRequiredErrorMessage),
                        "Start Date Required Error Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.label_startTimeRequiredErrorMessage),
                        "Start Time Required Error Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.label_endDateRequiredErrorMessage),
                        "End Date Required Error Displayed");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.label_endTimeRequiredErrorMessage),
                        "End Time Required Error Displayed");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        " Close button clicked , Org.Setting Template closed ");
                Cls_Generic_Methods.customWait();


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Creating New Leave Functionality")
    public void validatingCreateNewLeaveOrHolidayFunctionality() {

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_Dashboard oPage_Dashboard = new Page_Dashboard(driver);
        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        boolean addLeaveStatus = false;

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {

                try {
                    // Validate Dashboard Opened & displayed
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.logo_FF_EHS);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Dashboard.panels_DashboardPanels, 5),
                            "Validate panels on Dashboard are displayed.");

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Exception while validating Dashboard Opened & displayed. " + e);
                }

                Cls_Generic_Methods.scrollToElementByJS(oPage_LeaveManagementHEAL5940.button_addOrEditUpcomingPlan);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.header_upcomingPlanHeader),
                        " Upcoming Holiday Header Displayed");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.button_addOrEditUpcomingPlan),
                        " + Add/Edit Upcoming Plan Button CLicked in Dashboard ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_LeaveManagementHEAL5940.header_addOrEditPlanTemplate,5);
                int totalUpcomingPlan = 0 ;
                int totalUpcomingPlanAfterSave = 0 ;
                if(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_noPlanAddedInAddEditPlanTemplate)) {
                    m_assert.assertTrue(" No Plan Added Text is Displayed , No Past Plan Available ");
                }
                else {

                    totalUpcomingPlan = oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanListInAddTemplate.size();
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_upcomingPlanTextInAddEditPlanTemplate),
                            " Upcoming Leave & Meetings Text Heading Displayed ");

                }

                // Adding New Leave Plan

                addLeaveStatus = createOrEditLeaveOrHolidayPlan(actualPlanTypeSelectList[0],actualPlanTypeSelectList[0],startHour,startMinute,startHour,endMinute,meridian,meridian);

                if(addLeaveStatus) {

                    // Validating Count of Total Plan After Adding New plan Leave

                    Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_upcomingPlanTextInAddEditPlanTemplate);

                    totalUpcomingPlanAfterSave = oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanListInAddTemplate.size();

                    m_assert.assertTrue((totalUpcomingPlanAfterSave - 1) == (totalUpcomingPlan),
                            " New Leave Added successfully and count increased correctly as : <b> " + totalUpcomingPlan + "</b>");

                    Cls_Generic_Methods.customWait();

                    if (Integer.parseInt(date) < 10) {
                        date = "0" + date;
                    }

                    expectedDate = date + " " + month.substring(0, 3) + "'" + year.substring(2, 4);
                    expectedTime = startHour + ":" + startMinute + " " + "-" + " " + startHour + ":" + endMinute + " " + meridian;

                    // Validating Added Leave Plan In List Of Upcoming Plan In Add Template

                    m_assert.assertTrue(getSavedPlanDetailsInAddTemplate
                                    (expectedDate, expectedTime, actualPlanTypeSelectList[0], actualPlanTypeSelectList[0]),
                            " Upcoming plan added successfully and Verified added data showing correctly in list");
                }else {
                    m_assert.assertTrue(addLeaveStatus," Leave Plan is not created successfully");
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                        "Close Button Clicked in Add Leave Template");
                Cls_Generic_Methods.customWait();



            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Added Leave In Dashboard Page Functionality")
    public void validatingNewlyAddedLeaveInDashboard() {

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_Dashboard oPage_Dashboard = new Page_Dashboard(driver);
        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {

                try {
                    // Validate Dashboard Opened & displayed
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.logo_FF_EHS);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Dashboard.panels_DashboardPanels, 5),
                            "Validate panels on Dashboard are displayed.");

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Exception while validating Dashboard Opened & displayed. " + e);
                }

                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.header_upcomingPlanHeader),
                        " Upcoming Holiday Header Displayed");

                String upcomingPlanFullText = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.header_upcomingPlanHeader);
                String[] arrOfUpcomingPlanText = upcomingPlanFullText.split("[\r\n]+");
                if(arrOfUpcomingPlanText[0].equalsIgnoreCase("UPCOMING PLANS : 0")){
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_upcomingNoPlanText),
                            "No Plan Added Text is Displayed , No Upcoming Plan Available");
                    m_assert.assertTrue(false,"New Plan Added Is not displaying on Dashboard");

                }
                else{

                    String[] upcomingPlanText = arrOfUpcomingPlanText[0].split(" : ");
                    totalUpcomingPlan = Integer.parseInt(upcomingPlanText[1]);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_upcomingPlansDataRow),
                            " Upcoming Plan Displayed , Newly Added Upcoming plan are present in list ");
                    m_assert.assertTrue(oPage_LeaveManagementHEAL5940.list_upcomingPlansList.size()-1 == totalUpcomingPlan,
                            " Upcoming Plan Count at heading and In Upcoming Plan List is increased correctly bu one and total count is : <b>"+totalUpcomingPlan+"</b>");
                }

                m_assert.assertTrue(getUpcomingPlanDetailsInDashboard(expectedDate,expectedTime,upcomingPlanActionInDashboardAndTemplate),
                        " Upcoming plan added successfully and Verified added data showing correctly in upcoming plan list in dashboard ");
                Cls_Generic_Methods.customWait();


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Newly Added Leave In Add Appointment")
    public void validatingNewlyAddedLeaveInAddAppointment() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);


        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectFacility("OPTHA1");

            try {

                CommonActions.selectDepartmentOnApp("OPD");
                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                                "Patient Details");
                        Thread.sleep(2000);
                    }
                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                }

                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByJS(oPage_LeaveManagementHEAL5940.leave_breakTypeLeaveSlot),
                        " Added Leave Plan Displayed in Calendar");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,oPage_LeaveManagementHEAL5940.leave_breakTypeLeaveSlot),
                        "Clicked on Added Leave Plan in Add Appointment Calendar");
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.message_slotInformationFlashMessage),
                        "Error Message Displayed, Slot is not selected");
                String leaveInformationText = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.message_slotInformationFlashMessage);
                m_assert.assertTrue(leaveInformationText.equalsIgnoreCase("Doctor not available on selected slot, Please select available time slot."),
                        " Error Message Displayed For Leave Slot Selection , Message Displayed as :"+leaveInformationText);
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait();

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Newly Added Leave In Patient Template")
    public void validatingNewlyAddedLeaveInTemplateFollowUpForPatient() {

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);
        Page_EyeTemplate oPage_EyeTemplate = new Page_EyeTemplate(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectFacility("OPTHA1");

            try {

                CommonActions.selectDepartmentOnApp("OPD");

                String MyQueueTab = "My Queue";
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab), "Validate " + MyQueueTab + " tab is selected");
                Cls_Generic_Methods.customWait();
                CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);

                for(int i=0 ; i<=patientTemplatesList.length;i++){


                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_OPD.listButtons_selectOptionsOnTemplate, 1);
                    m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, patientTemplatesList[i]),
                            "Validate "+patientTemplatesList[i]+" template  is selected");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_EyeTemplate.button_followUpButtonInTemplate, 20);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_EyeTemplate.button_followUpButtonInTemplate),
                            "Follow Up Button Clicked in template "+patientTemplatesList[i]);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_EyeTemplate.input_dateFieldInFollowUpTab, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_EyeTemplate.input_dateFieldInFollowUpTab),
                            "Follow Up Date Field Displayed and Clicked");
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.scrollToElementByJS(oPage_LeaveManagementHEAL5940.leave_breakTypeLeaveSlot);
                    Cls_Generic_Methods.customWait();
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_LeaveManagementHEAL5940.leave_breakTypeLeaveSlot),
                            " Added Leave Slot Clicked In Template Follow Up");
                    Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.message_slotInformationFlashMessage);
                    String leaveInformationText = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.message_slotInformationFlashMessage);
                    m_assert.assertTrue(leaveInformationText.equalsIgnoreCase("Doctor not available on selected slot, Please select available time slot."),
                            " Error Message Displayed For Leave Slot Selection , Message Displayed as :" + leaveInformationText);

                    Cls_Generic_Methods.clickElementByJS(driver, oPage_EyeTemplate.button_CloseModalWith_X);
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                    Cls_Generic_Methods.customWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Newly Added Leave In Discharge Template")
    public void validateNewlyAddedLeaveInDischargeFormIPD() {


        Page_DischargeForm oPage_DischargeForm = new Page_DischargeForm(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);
        Page_EyeTemplate oPage_EyeTemplate = new Page_EyeTemplate(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        boolean bValidatePatientFound = false;


        try {

            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            CommonActions.selectFacility("OPTHA1");
            CommonActions.selectDepartmentOnApp("IPD");
            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today);
            Cls_Generic_Methods.customWait();
            bValidatePatientFound = patientQueueTrayTest.selectPatientNameInIPD(concatPatientFullName);
            m_assert.assertInfo(bValidatePatientFound, "Validate patient found");

            if (bValidatePatientFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_IPD.text_postOperativeSection)) {

                    try {

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_DischargeForm.button_dischargeTemplate),
                                "clicked on Discharge template ");
                        Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_DischargeForm.list_tabsOnDischargeForm, 20);
                        m_assert.assertTrue(CommonActions.selectListOption(oPage_DischargeForm.list_tabsOnDischargeForm, "Follow Up"),
                                "Follow Up Tab Clicked In Discharged Template");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_EyeTemplate.input_dateFieldInFollowUpTab,2);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_EyeTemplate.input_dateFieldInFollowUpTab),
                                "Follow Up Date Field Clicked");
                        Cls_Generic_Methods.customWait();
                        Cls_Generic_Methods.scrollToElementByJS(oPage_LeaveManagementHEAL5940.leave_breakTypeLeaveSlot);
                        Cls_Generic_Methods.customWait();
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_LeaveManagementHEAL5940.leave_breakTypeLeaveSlot),
                                " Added Leave Slot Clicked In Template Follow Up");
                        Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.message_slotInformationFlashMessage);
                        String leaveInformationText = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.message_slotInformationFlashMessage);
                        m_assert.assertTrue(leaveInformationText.equalsIgnoreCase("Doctor not available on selected slot, Please select available time slot."),
                                " Error Message Displayed For Leave Slot Selection , Message Displayed as :"+leaveInformationText);

                        Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                        Cls_Generic_Methods.customWait();



                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("Exception while filling bill of material chart Template " + e);
                    }

                } else {
                    m_assert.assertInfo(false,
                            "Admission form in Intra operative section is not filled, please fill admission form");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating Edit Existing Leave Functionality")
    public void validatingEditLeaveFunctionality() {

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_Dashboard oPage_Dashboard = new Page_Dashboard(driver);
        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);


        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {

                try {
                    // Validate Dashboard Opened & displayed
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.logo_FF_EHS);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Dashboard.panels_DashboardPanels, 5),
                            "Validate panels on Dashboard are displayed.");

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Exception while validating Dashboard Opened & displayed. " + e);
                }

                Cls_Generic_Methods.scrollToElementByJS(oPage_LeaveManagementHEAL5940.button_addOrEditUpcomingPlan);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.header_upcomingPlanHeader),
                        " Upcoming Holiday Header Displayed");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.button_addOrEditUpcomingPlan),
                        " + Add/Edit Upcoming Plan Button CLicked in Dashboard ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_LeaveManagementHEAL5940.header_addOrEditPlanTemplate,5);
                boolean clickOnEdit =  getActionOnUpcomingHoliday(expectedDate,upcomingPlanActionInAddTemplate);
                if(clickOnEdit) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_LeaveManagementHEAL5940.form_editHolidayPlanForm, 2);
                    int indexOfEditPlanHeaders = -1;
                    for (WebElement editPlanHeaders : oPage_LeaveManagementHEAL5940.list_editPlanHeaderListInEditPlanForm) {
                        indexOfEditPlanHeaders = oPage_LeaveManagementHEAL5940.list_editPlanHeaderListInEditPlanForm.indexOf(editPlanHeaders);
                        if (Cls_Generic_Methods.getTextInElement(editPlanHeaders).equalsIgnoreCase(actualAddEditPlanHeaderTextList[indexOfEditPlanHeaders])) {
                            m_assert.assertTrue(actualAddEditPlanHeaderTextList[indexOfEditPlanHeaders] + "  Header Present in Edit Plan Template");
                        } else {
                            m_assert.assertFalse(actualAddEditPlanHeaderTextList[indexOfEditPlanHeaders] + "  Header Present in not Edit Plan Template");

                        }

                    }

                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                    oPage_LeaveManagementHEAL5940.select_selectEditPlanTypeInEditPlan, actualPlanTypeSelectList[4]),
                            "Plan Type Entered as : " + actualPlanTypeSelectList[4] + " in Edit Plan");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.input_editStartDateInEditPlan),
                            "Start Date Button Clicked in Edit Plan");
                    Cls_Generic_Methods.customWait(1);

                    String nextDaySelection = Cls_Generic_Methods.getElementAttribute(oPage_LeaveManagementHEAL5940.date_editDateSelection, "class");
                    if (nextDaySelection.equalsIgnoreCase(nextDateDisabledText)) {
                        Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.button_nextMonthButton);
                        Cls_Generic_Methods.customWait();
                        date = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.date_nextMonthFirstDay);
                        month = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.month_currentMonthNameInDatePicker);
                        year = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.year_currentYearNameInDatePicker);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.date_nextMonthFirstDay),
                                "Start Date entered as " + date);
                        Cls_Generic_Methods.customWait();
                    } else {

                        date = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.date_editDateSelection);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.date_editDateSelection),
                                "Start Date entered as " + date);
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.input_editEndDateInEditPlan),
                            "End Date Button Clicked");

                    if (nextDaySelection.equalsIgnoreCase(nextDateDisabledText)) {
                        Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.button_nextMonthButton);
                        Cls_Generic_Methods.customWait();
                        date = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.date_nextMonthFirstDay);
                        month = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.month_currentMonthNameInDatePicker);
                        year = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.year_currentYearNameInDatePicker);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.date_nextMonthFirstDay),
                                "Date is clicked in end date as  " + date);
                        Cls_Generic_Methods.customWait();
                    } else {

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_LeaveManagementHEAL5940.date_editEndDateSelection),
                                "Date is clicked in end date as  " + date);

                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.input_editStartTimeInEditPlan),
                            "Start Time Input Button Clicked");

                    Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_hourFieldForTime);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_hourFieldForTime, startHour),
                            "Hour entered in start time as : " + startHour);

                    Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_minuteFieldForTime);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_minuteFieldForTime, editStartMinute),
                            "Minute entered in start time as : " + editStartMinute);

                    Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_meridianFieldForTime);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_meridianFieldForTime, meridian),
                            "Meridian entered in start time as : " + meridian);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.input_editEndTimeInEditPlan),
                            "End Time Button Clicked");

                    Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_hourFieldForTime);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_hourFieldForTime, startHour),
                            "End Hours Enter as "+startHour);;

                    Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_minuteFieldForTime);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_minuteFieldForTime, editEndMinute),
                            "End Minute Enter as "+editEndMinute);

                    Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_meridianFieldForTime);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_meridianFieldForTime, meridian),
                            "End Meridian enter as :"+meridian);

                    Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_editReasonTextBoxInEditPlan);
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_editReasonTextBoxInEditPlan, editReason),
                            " Edit Plan Reason Entered as :" + editReason);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_LeaveManagementHEAL5940.input_updatePlanButtonInEditPlan,1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.input_updatePlanButtonInEditPlan),
                            " Update Button Clicked to Edit plan");
                    Cls_Generic_Methods.customWait();

                    if (Integer.parseInt(date) < 10) {
                        date = "0" + date;
                    }
                    expectedDate = date + " " + month.substring(0, 3) + "'" + year.substring(2, 4);
                    expectedTime = startHour + ":" + editStartMinute + " " + "-" + " " + startHour + ":" + editEndMinute + " " + meridian;

                    m_assert.assertTrue(getSavedPlanDetailsInAddTemplate(expectedDate, expectedTime, actualPlanTypeSelectList[0], actualPlanTypeSelectList[0]),
                            " Upcoming plan updated successfully and Verified added data showing correctly in list");

                }else{
                    m_assert.assertTrue(clickOnEdit,"Either Edit Button is not clicked or Clickable Or Not Displayed");
                }
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait();


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Adding Multiple Leave Functionality")
    public void validatingAddingMultipleLeaveOrHolidayPlan() {

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_Dashboard oPage_Dashboard = new Page_Dashboard(driver);
        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {

                try {
                    // Validate Dashboard Opened & displayed
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.logo_FF_EHS);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Dashboard.panels_DashboardPanels, 5),
                            "Validate panels on Dashboard are displayed.");

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Exception while validating Dashboard Opened & displayed. " + e);
                }

                Cls_Generic_Methods.scrollToElementByJS(oPage_LeaveManagementHEAL5940.button_addOrEditUpcomingPlan);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.header_upcomingPlanHeader),
                        " Upcoming Holiday Header Displayed");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.button_addOrEditUpcomingPlan),
                        " + Add/Edit Upcoming Plan Button CLicked in Dashboard ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_LeaveManagementHEAL5940.header_addOrEditPlanTemplate,5);

                int totalUpcomingPlan = 0 ;
                int totalUpcomingPlanAfterSave = 0 ;
                if(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_noPlanAddedInAddEditPlanTemplate)) {
                    m_assert.assertTrue(" No Plan Added Text is Displayed , No Past Plan Available ");
                }
                else {

                    totalUpcomingPlan = oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanListInAddTemplate.size();
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_upcomingPlanTextInAddEditPlanTemplate),
                            " Upcoming Leave & Meetings Text Heading Displayed ");

                }

                boolean addLeaveStatus = createOrEditLeaveOrHolidayPlan(actualPlanTypeSelectList[0],actualPlanTypeSelectList[0],startHour,startMinute,startHour,endMinute,meridian,meridian);

                if(addLeaveStatus) {
                    Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_upcomingPlanTextInAddEditPlanTemplate);

                    totalUpcomingPlanAfterSave = oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanListInAddTemplate.size();

                    m_assert.assertTrue((totalUpcomingPlanAfterSave - 1) == (totalUpcomingPlan),
                            " New Leave Added successfully and count increased correctly");

                    Cls_Generic_Methods.customWait();
                    if (Integer.parseInt(date) < 10) {
                        date = "0" + date;
                    }
                    expectedDate = date + " " + month.substring(0, 3) + "'" + year.substring(2, 4);
                    expectedTime = startHour + ":" + startMinute + " " + "-" + " " + startHour + ":" + endMinute + " " + meridian;
                    m_assert.assertTrue(getSavedPlanDetailsInAddTemplate
                                    (expectedDate, expectedTime, actualPlanTypeSelectList[0], actualPlanTypeSelectList[0]),
                            " Upcoming plan added successfully and Verified added data showing correctly in list");

                }else{
                    m_assert.assertTrue(addLeaveStatus,"Leave is not added , Something Wrong");
                }
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait();



            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Delete Existing Leave Functionality")
    public void validatingDeleteLeaveFunctionality() {

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_Dashboard oPage_Dashboard = new Page_Dashboard(driver);
        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PurchaseReturn oPage_PurchaseReturn = new Page_PurchaseReturn(driver);


        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {

                try {
                    // Validate Dashboard Opened & displayed
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.logo_FF_EHS);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Dashboard.panels_DashboardPanels, 5),
                            "Validate panels on Dashboard are displayed.");

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Exception while validating Dashboard Opened & displayed. " + e);
                }

                Cls_Generic_Methods.scrollToElementByJS(oPage_LeaveManagementHEAL5940.button_addOrEditUpcomingPlan);
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.header_upcomingPlanHeader),
                        " Upcoming Holiday Header Displayed");
                int totalUpcomingPlan = 0 ;
                if(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_noPlanAddedInAddEditPlanTemplate)) {
                    m_assert.assertTrue(" No Plan Added Text is Displayed , No Past Plan Available ");
                }
                else {

                    totalUpcomingPlan = oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanListInAddTemplate.size();
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_LeaveManagementHEAL5940.text_upcomingPlanTextInAddEditPlanTemplate),
                            " Upcoming Leave & Meetings Text Heading Displayed ");

                }
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.button_addOrEditUpcomingPlan),
                        " + Add/Edit Upcoming Plan Button CLicked in Dashboard ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_LeaveManagementHEAL5940.header_addOrEditPlanTemplate,5);
                boolean clickOnDelete =  getActionOnUpcomingHoliday(expectedDate,upcomingPlanActionInDashboardAndTemplate);
                if(clickOnDelete) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_LeaveManagementHEAL5940.form_editHolidayPlanForm, 2);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PurchaseReturn.button_confirmYesTemplate, 5);

                    try {
                        if (Cls_Generic_Methods.isElementDisplayed(oPage_PurchaseReturn.button_confirmYesTemplate)) {
                            Cls_Generic_Methods.clickElement(oPage_PurchaseReturn.button_confirmYesTemplate);
                            Cls_Generic_Methods.customWait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal(" Confirmation Popup is Not coming" + e);
                    }


                    int totalUpcomingPlanAfterDelete = oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanListInAddTemplate.size();
                    m_assert.assertTrue(totalUpcomingPlanAfterDelete ==( totalUpcomingPlan -1),
                            " Upcoming Leave & Meetings Text Deleted Successfully ");

                }else{
                    m_assert.assertTrue(clickOnDelete,"Either Delete Button is not clicked or Clickable Or Not Displayed");
                }
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                Cls_Generic_Methods.customWait();


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }









    public  boolean getSavedPlanDetailsInAddTemplate(String expectedDate ,String expectedTime,String planType,String planReason) {

        boolean bPlanFound = false;

        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);

        try {

            int indexOfHeader = -1 ;
            String[] actualAddPlanHeaderTextList = {"Plan Type" ,"Date","Time","Reason","Actions"};
            List<String> savedUpcomingPlansHeaderList = new ArrayList<String>();


            for(WebElement planHeader : oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanHeaderListInAddTemplate){
                String headerName = Cls_Generic_Methods.getTextInElement(planHeader);
                indexOfHeader = oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanHeaderListInAddTemplate.indexOf(planHeader);
                m_assert.assertTrue(headerName.equalsIgnoreCase(actualAddPlanHeaderTextList[indexOfHeader]),
                        " Header Names for Created plan in Plan List are displaying correctly as :"+headerName);
                savedUpcomingPlansHeaderList.add(headerName);

            }

            int indexOfSavedPlan = -1 ;
            for(WebElement plans : oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanListInAddTemplate){
                indexOfSavedPlan = oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanListInAddTemplate.indexOf(plans);
                if(indexOfSavedPlan>0){

                    List<WebElement> plansRow = plans.findElements(By.xpath("./child::*"));
                    String savedUpcomingPlanType = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Plan Type")));
                    String savedUpcomingPlanDate = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Date")));
                    String savedUpcomingPlanTime = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Time")));
                    String savedUpcomingPlanReason = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Reason")));
                    String savedUpcomingPlanAction = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Actions")));

                    if (savedUpcomingPlanType.equalsIgnoreCase(planType) &&
                            savedUpcomingPlanDate.equalsIgnoreCase(expectedDate) &&
                            savedUpcomingPlanTime.equalsIgnoreCase(expectedTime) &&
                            savedUpcomingPlanReason.equalsIgnoreCase(planReason) &&
                            savedUpcomingPlanAction.equalsIgnoreCase("Edit Delete")
                    ){
                        bPlanFound = true;
                        break;
                    }

                }

            }


        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return bPlanFound;
    }

    public  boolean getActionOnUpcomingHoliday(String expectedDate, String actionToPerform) {

        boolean bActionStatus = false;

        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);

        try {

            int indexOfHeader = -1 ;
            String[] actualAddPlanHeaderTextList = {"Plan Type" ,"Date","Time","Reason","Actions"};
            List<String> savedUpcomingPlansHeaderList = new ArrayList<String>();


            for(WebElement planHeader : oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanHeaderListInAddTemplate){
                String headerName = Cls_Generic_Methods.getTextInElement(planHeader);
                indexOfHeader = oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanHeaderListInAddTemplate.indexOf(planHeader);
                m_assert.assertTrue(headerName.equalsIgnoreCase(actualAddPlanHeaderTextList[indexOfHeader]),
                        " Header Names for Created plan in Plan List are displaying correctly as :"+headerName);
                savedUpcomingPlansHeaderList.add(headerName);

            }

            int indexOfSavedPlan = -1 ;
            for(WebElement plans : oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanListInAddTemplate){
                indexOfSavedPlan = oPage_LeaveManagementHEAL5940.list_activeUpcomingPlanListInAddTemplate.indexOf(plans);
                if(indexOfSavedPlan>0){

                    List<WebElement> plansRow = plans.findElements(By.xpath("./child::*"));
                    String savedUpcomingPlanType = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Plan Type")));
                    String savedUpcomingPlanDate = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Date")));
                    String savedUpcomingPlanTime = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Time")));
                    String savedUpcomingPlanReason = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Reason")));
                    String savedUpcomingPlanAction = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Actions")));


                    List<WebElement> listOfButtons = plansRow.get(savedUpcomingPlansHeaderList.indexOf("Actions")).findElements(By.xpath(".//a"));

                    if (savedUpcomingPlanType.equalsIgnoreCase(actualPlanTypeSelectList[5]) &&
                            savedUpcomingPlanDate.equalsIgnoreCase(expectedDate) &&
                            savedUpcomingPlanTime.equalsIgnoreCase("02:40 PM - 02:45 PM") &&
                            savedUpcomingPlanReason.equalsIgnoreCase(reason) &&
                            savedUpcomingPlanAction.equalsIgnoreCase("Edit Delete")
                    ){

                        for (WebElement eButton : listOfButtons) {
                            if (Cls_Generic_Methods.isElementDisplayed(eButton)) {
                                if (actionToPerform.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(eButton))) {
                                    bActionStatus = true;
                                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(eButton),
                                            actionToPerform + " button clicked");

                                    break;
                                }
                            }
                        }
                    }

                }

            }


        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return bActionStatus;
    }
    public  boolean getUpcomingPlanDetailsInDashboard(String expectedDate,String expectedTime,String expectedAction) {

        boolean bPlanFound = false;

        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);

        try {

            int indexOfHeader = -1 ;
            String[] actualAddPlanHeaderTextList = {"Plan Type" ,"Date","Time","Action"};
            List<String> savedUpcomingPlansHeaderList = new ArrayList<String>();


            for(WebElement planHeader : oPage_LeaveManagementHEAL5940.list_upcomingPlanTableHeaderList){
                String headerName = Cls_Generic_Methods.getTextInElement(planHeader);
                indexOfHeader = oPage_LeaveManagementHEAL5940.list_upcomingPlanTableHeaderList.indexOf(planHeader);
                m_assert.assertTrue(headerName.equalsIgnoreCase(actualAddPlanHeaderTextList[indexOfHeader]),
                        " Header Names for Upcoming plan in Plan List are displaying correctly as :"+headerName);
                savedUpcomingPlansHeaderList.add(headerName);
            }

            int indexOfUpcomingPlan = -1 ;
            for(WebElement plans : oPage_LeaveManagementHEAL5940.list_upcomingPlansList){
                indexOfUpcomingPlan = oPage_LeaveManagementHEAL5940.list_upcomingPlansList.indexOf(plans);
                if(indexOfUpcomingPlan>0){

                    List<WebElement> plansRow = plans.findElements(By.xpath("./child::*"));
                    String savedUpcomingPlanType = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Plan Type")));
                    String savedUpcomingPlanDate = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Date")));
                    String savedUpcomingPlanTime = Cls_Generic_Methods.getTextInElement(plansRow.get(savedUpcomingPlansHeaderList.indexOf("Time")));
                    WebElement savedUpcomingPlanActionElement = plansRow.get(savedUpcomingPlansHeaderList.indexOf("Action")).findElement(By.xpath(".//a[@data-method='delete']"));

                    String savedUpcomingPlanAction = Cls_Generic_Methods.getElementAttribute(savedUpcomingPlanActionElement,"data-method");

                    if (savedUpcomingPlanType.equalsIgnoreCase(actualPlanTypeSelectList[5]) &&
                            savedUpcomingPlanDate.equalsIgnoreCase(expectedDate) &&
                            savedUpcomingPlanTime.equalsIgnoreCase(expectedTime) &&
                            savedUpcomingPlanAction.equalsIgnoreCase(expectedAction)
                    ){
                        bPlanFound = true;
                        break;
                    }

                }

            }


        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return bPlanFound;
    }

    public boolean createOrEditLeaveOrHolidayPlan(String planType,String planReason,String planStartHour,String planStartMinute,
                                                  String planEndHour,String planEndMinute,String planStartMeridian,String planEndMeridian) {

        boolean bPlanFound = false;

        Page_LeaveManagementHEAL5940 oPage_LeaveManagementHEAL5940 = new Page_LeaveManagementHEAL5940(driver);

        try {

            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_LeaveManagementHEAL5940.select_selectPlanTypeInAddPlan,planType),
                    "Plan Type Entered as : "+planType+" in Add Plan");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.input_startDateInAddPlan),
                    "Start Date Button Clicked in Add Plan");
            Cls_Generic_Methods.customWait(1);

            date = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.date_currentDateHighlighted);
            month = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.month_currentMonthNameInDatePicker);
            year = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.year_currentYearNameInDatePicker);

            String nextDaySelection = Cls_Generic_Methods.getElementAttribute(oPage_LeaveManagementHEAL5940.date_nextDaytoCurrentDate,"class");
            if(nextDaySelection.equalsIgnoreCase(nextDateDisabledText)){
                Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.button_nextMonthButton);
                Cls_Generic_Methods.customWait();
                date = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.date_nextMonthFirstDay);
                month = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.month_currentMonthNameInDatePicker);
                year = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.year_currentYearNameInDatePicker);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.date_nextMonthFirstDay),
                        "Start Date entered as "+date);
                Cls_Generic_Methods.customWait();
            }
            else{

                date = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.date_nextDaytoCurrentDate);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.date_nextDaytoCurrentDate),
                        "Start Date entered as "+date);
            }

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.input_startTimeInAddPlan),
                    "Start Time Input Button Clicked");

            Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_hourFieldForTime);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_hourFieldForTime,planStartHour),
                    "Hour entered in start time as : "+planStartHour);

            Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_minuteFieldForTime);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_minuteFieldForTime,planStartMinute),
                    "Minute entered in start time as : "+planStartMinute);

            Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_meridianFieldForTime);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_meridianFieldForTime,planStartMeridian),
                    "Meridian entered in start time as : "+planStartMeridian);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.input_endDateInAddPlan),
                    "End Date Button Clicked");

            if(nextDaySelection.equalsIgnoreCase(nextDateDisabledText)){
                Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.button_nextMonthButton);
                Cls_Generic_Methods.customWait();
                date = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.date_nextMonthFirstDay);
                month = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.month_currentMonthNameInDatePicker);
                year = Cls_Generic_Methods.getTextInElement(oPage_LeaveManagementHEAL5940.year_currentYearNameInDatePicker);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.date_nextMonthFirstDay),
                        "Date is clicked in end date as  "+date);
                Cls_Generic_Methods.customWait();
            }
            else{

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.date_nextDaytoCurrentDate),
                        "Date is clicked in end date as  "+date);
            }

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.input_endTimeInAddPlan),
                    "End Time Button Clicked");

            Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_hourFieldForTime);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_hourFieldForTime,planEndHour);

            Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_minuteFieldForTime);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_minuteFieldForTime,planEndMinute);

            Cls_Generic_Methods.clearValuesInElement(oPage_LeaveManagementHEAL5940.input_meridianFieldForTime);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_meridianFieldForTime,planEndMeridian);
            Cls_Generic_Methods.customWait(1);

            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_LeaveManagementHEAL5940.input_reasonTextBoxInAddPlan,planReason),
                    " Add Plan Reason Entered as :"+planReason);
            Cls_Generic_Methods.customWait(1);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_LeaveManagementHEAL5940.input_savePlanInAddPlan),
                    " +Plan Button Clicked to save plan");
            Cls_Generic_Methods.customWait();



        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return bPlanFound;
    }


}
