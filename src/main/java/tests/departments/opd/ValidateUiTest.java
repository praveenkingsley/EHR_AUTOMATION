package tests.departments.opd;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import data.EHR_Data;
import data.IPD_Data;
import data.OPD_Data;
import data.OT_Data;
import org.dom4j.rule.Action;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;

import pages.commonElements.CommonActions;
import pages.commonElements.dashboard.Page_Dashboard;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.opd.Page_OPD;
import pages.ot.Page_OT;
import pages.settings.organisationSettings.general.Page_PatientSettings;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_CategoryMaster;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;

public class ValidateUiTest extends TestBase {

    Page_Navbar oPage_Navbar;
    Page_Dashboard oPage_Dashboard;
    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_OPD oPage_OPD;
    EHR_Data oEHR_Data = new EHR_Data();
    static Model_Patient myPatient;
    Page_IPD oPage_IPD;
    Page_OT oPage_OT;
    Page_ScheduleAdmission oPage_ScheduleAdmission;
    Page_CategoryMaster oPage_CategoryMaster;
    Page_ItemMaster oPage_ItemMaster;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");

    @Test(enabled = true, description = "Validate Top Navbar on Application")
    public void validatePrimaryNavbar() {

        oPage_Navbar = new Page_Navbar(driver);
        oPage_Dashboard = new Page_Dashboard(driver);
        String currentLoggedUserName = null;

        String expectedLoggedInUser = "PR.Akash test";
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);

            // Get current logged in user
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
            currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser);
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
            currentLoggedUserName = "<b>" + currentLoggedUserName + "</b>";

            // Validate Logo is displayed
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 20),
                    "Validate EHS Logo is dislpayed");

            try {
                // Validate Dashboard Opened & displayed
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.logo_FF_EHS);
                m_assert.assertTrue(
                        Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Dashboard.panels_DashboardPanels, 20),
                        "Validate panels on Dashboard are displayed.");

                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while validating Dashboard Opened & displayed. " + e);
            }

            try {
                // Validate Departments
                boolean deptsFound = false;
                int departmentsCounter = 0;

                try {
                    // Works in case the Departments are in a dropdown
                    Cls_Generic_Methods.customWait(3);
                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_storesSelector);
                    Cls_Generic_Methods.customWait(5);
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_departmentFromDropdownSelector);
                    Cls_Generic_Methods.customWait(3);
                    for (WebElement eDepartment : oPage_Navbar.list_departmentSelector) {
                        m_assert.assertInfo(eDepartment.getText().trim() + " department is visible.");
                        departmentsCounter++;
                    }
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_departmentFromDropdownSelector);

                } catch (Exception e) {
                    //	System.out.println(e);

                    //  Works in case the Departments are NOT in a dropdown but visible upfront

                    for (WebElement eDepartment : oPage_Navbar.list_departmentsInOneLine) {
                        m_assert.assertInfo(eDepartment.getText().trim() + " department is visible.");
                        departmentsCounter++;
                    }
                }

                if (departmentsCounter > 1) {
                    deptsFound = true;
                }

                m_assert.assertTrue(deptsFound, "Validate " + departmentsCounter + " Departments are found.");

            } catch (Exception e1) {
                System.out.println(e1);
                m_assert.assertFatal("Exception while validating Departments on Webpage. " + e1);
            }

            try {
                // Validate Stores
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_storesSelector);

                int storesCounter = 0;
                boolean storesFound = false;

                for (WebElement eStore : oPage_Navbar.list_storesSelector) {
                    m_assert.assertInfo(eStore.getText().trim() + " store is present.");
                    storesCounter++;
                }
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_departmentFromDropdownSelector);

                if (storesCounter > 1) {
                    storesFound = true;
                }

                m_assert.assertTrue(storesFound, "Validate " + storesCounter + " Stores are found.");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_storesSelector, 20);
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_storesSelector);
            } catch (Exception e1) {
                e1.printStackTrace();
                m_assert.assertWarn("Stores not present for the current user " + currentLoggedUserName);
            }

            // Validate Search Options
            try {
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_searchCriteriaSelector);

                int searchCriteriaCounter = 0;
                boolean expectedSearchCriteriasFound = false;

                for (WebElement eSearchCriteria : oPage_Navbar.list_searchCriteriaSelector) {
                    m_assert.assertInfo(eSearchCriteria.getText().trim() + " search criteria is present.");
                    searchCriteriaCounter++;
                }

                // 8 is hard coded as we have at least 8 search types - MR No, Mobile No, Name,
                // Patient Identifier,my diagnosis, all diagnosis, my procedure and all procedure
                if (searchCriteriaCounter >= 8) {
                    expectedSearchCriteriasFound = true;
                }

                m_assert.assertTrue(expectedSearchCriteriasFound,
                        "Validate " + searchCriteriaCounter + " filters are found for Search Criteria.");

                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_searchCriteriaSelector);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Validate search criterias are found " + e);
            }

            // Validate Search Dialog box is opened
            try {
                boolean searchResultsFound = false;
                oPage_Navbar = new Page_Navbar(driver);
                String searchKeyword = "Patient";

                Cls_Generic_Methods.sendKeysIntoElement(oPage_Navbar.input_search, searchKeyword);
                Cls_Generic_Methods.clickElement(oPage_Navbar.button_search);

                // Cls_Generic_Methods.sendKeysByAction(oPage_Navbar.input_search, searchKeyword);
                Thread.sleep(1000);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.modal_patientSearch, 8);

                Cls_Generic_Methods.selectElementByVisibleText(oPage_Navbar.select_modalPatientSearchCriteria, "Name");

                Cls_Generic_Methods.sendKeysIntoElement(oPage_Navbar.input_modalPatientSearch, searchKeyword);

                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_modalPatientSearch);

                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_Navbar.list_modalSearchResults, 8);

                int searchResultsCount = oPage_Navbar.list_modalSearchResults.size();
                if (searchResultsCount > 0) {
                    searchResultsFound = true;
                }

                m_assert.assertTrue(searchResultsFound,
                        "Validate " + searchResultsCount + " results are found search keyword = " + searchKeyword);

                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_modalSearchResultsClose);

                //       Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_modalClose);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Validate search results are found." + e);
            }

            // Validate Set State
            try {
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_setState);

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.modal_ChangeState, 8),
                        "Validate Set State modal is displayed");

                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_modalClose);
            } catch (Exception e1) {
//				System.out.println(e1);
                m_assert.assertWarn("Set State option is not found for the current user " + currentLoggedUserName);
            }

            // Validate Facility Selection
            try {
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_facilitySelector);

                int facilityCounter = 0;
                boolean facilitiesFound = false;

                for (WebElement eFacility : oPage_Navbar.list_facilitySelector) {
                    System.out.println(eFacility.getText().trim());
                    facilityCounter++;
                }

                if (facilityCounter >= 1) {
                    facilitiesFound = true;
                }

                m_assert.assertTrue(facilitiesFound,
                        "Validate " + facilityCounter + " facilities are found under Facility options.");

                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_facilitySelector);
            } catch (Exception e) {
                System.out.println(e);
                m_assert.assertWarn("Facilities selection is not found for current user " + currentLoggedUserName);
            }

            // Validate Feedback options
            //Commenting Feedback Code as we have removed feedback option from Navbar
//            try {
//
//                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_feedback);
//
//                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.iframe_feedback, 16);
//
//                Cls_Generic_Methods.switchToFrame(driver, oPage_Navbar.iframe_feedback);
//
//                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Navbar.input_feedbackSummary),
//                        "Validate Summary under Feedback is displayed.");
//
//                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Navbar.input_feedbackDescription),
//                        "Validate Description under Feedback is displayed.");
//
//                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Navbar.input_feedbackFullName),
//                        "Validate Full Name under Feedback is displayed.");
//
//                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Navbar.input_feedbackEmail),
//                        "Validate Email under Feedback is displayed.");
//
//                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.input_feedbackEmail);
//               try {
//                   Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_feedbackClose);
//               }catch(Exception e){
//                   Cls_Generic_Methods.customWait();
//               }
//                Cls_Generic_Methods.switchToDefaultFrame(driver);
//                Cls_Generic_Methods.customWait();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                m_assert.assertFatal("Validate Feedback options." + e);
//            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        } finally {

        }
    }

    @Test(enabled = true, description = "Validate Secondary Navbar on Application")
    public void validateSecondaryNavbar() {

        oPage_Navbar = new Page_Navbar(driver);
        oPage_Dashboard = new Page_Dashboard(driver);

        //String expectedLoggedInUser = "AR NCT 2";
        try {
            //CommonActions.loginFunctionality(expectedLoggedInUser);

            // Validate Calendar View is displayed
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Navbar.button_showCalendarView),
                    "Validate Calendar View button is dislpayed");
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_showCalendarView);

            m_assert.assertTrue(
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.section_calendarView_Summary, 8),
                    "Validate Calendar View is displayed");

            // Validate List View is displayed
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_showListView);
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_today, 8),
                    "Validate List View is displayed");

            // Validate Refresh button is displayed
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Navbar.button_refreshView),
                    "Validate Refresh button is displayed");

            // Validate Search Patient input area is displayed
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Navbar.input_searchPatientOnView),
                    "Validate Search Patient input area is displayed");

            try {
                // Validate Today button is displayed and functional
                m_assert.assertTrue(!Cls_Generic_Methods.isElementEnabled(oPage_Navbar.button_today),
                        "Validate Today button is Disabled");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElementByAction(driver, oPage_Navbar.button_datePrevious);
                Cls_Generic_Methods.customWait(5);
                m_assert.assertTrue(Cls_Generic_Methods.isElementEnabled(oPage_Navbar.button_today),
                        "Validate Today button is Enabled after date change from Today");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_today, 30);
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_today);
                Thread.sleep(2000);

                // Validate Date section is displayed
                String todaySystemDate = new SimpleDateFormat("MMM dd, yyyy").format(Calendar.getInstance().getTime());
                String dateOnUI = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_dateOnNav);

                m_assert.assertTrue(todaySystemDate.equals(dateOnUI),
                        "Validate Date from Today button is same as that from system");

                // Validate Calendar button is displayed
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Navbar.button_calendarOpen),
                        "Validate Calendar Open button is displayed");

                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_calendarOpen);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_monthOnCalendar, 4);

                String currentMonthOnSystem = new SimpleDateFormat("MMMM").format(Calendar.getInstance().getTime());
                String currentMonthOnUI = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_monthOnCalendar);

                m_assert.assertTrue(currentMonthOnSystem.equals(currentMonthOnUI),
                        "Validate correct Month is displayed on opened Calendar");
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.input_searchPatientOnView);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertTrue(false, "Validate Date Functionality " + e);
            }

            // Validate Add Appointment button is displayed
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Navbar.button_addAppointment),
                    "Validate Add Patient button is displayed");

            // Validate Print Appointments button is displayed
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Navbar.button_printAppointment),
                    "Validate Print Appointments button is displayed");

            try {
                // Validate Reports button is displayed
                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Navbar.button_reports),
                        "Validate Reports button is displayed");

                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_reports);

                int reportOptionsCount = 0;
                int minimumExpectedOptionsCount = 3;
                for (WebElement eReportOption : oPage_Navbar.options_reportOptions) {
                    System.out.println(eReportOption.getText().trim());
                    reportOptionsCount++;
                }

                m_assert.assertTrue(reportOptionsCount > minimumExpectedOptionsCount,
                        "Validate more than " + minimumExpectedOptionsCount + " options are displayed under Reports");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertTrue(false, "Validate Report options " + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        } finally {

        }
    }

    @Test(enabled = true, description = "Validate Adverse Event & Send To Departments")
    public void validatePatientDetailsSection() {

        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

        String expectedLoggedInUser = EHR_Data.user_mansa1;
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);

            // Assuming that the opened page is OPD
            try {

                String tabToSelect = OPD_Data.tab_ALL;
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                        "Validate " + tabToSelect + " tab is selected");

                CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

                m_assert.assertTrue(
                        Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_adverseEvent),
                        "Validate Adverse Event button is displayed");

                Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_adverseEvent);

                m_assert.assertTrue(
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_reportAdverseEvent,
                                8),
                        "Validate Adverse Event button is displayed");

                Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_reportAdverseEvent);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.modal_adverseEvent, 8);

                m_assert.assertInfo(oPage_PatientAppointmentDetails.text_adverseEventPatientName.getText()
                        + " patient is displayed.");

                int criticalEventsCounter = 0, majorEventsCounter = 0, minorEventsCounter = 0;

                oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

                try {
                    Cls_Generic_Methods.clickElementByAction(driver,
                            oPage_PatientAppointmentDetails.listRadioButtons_criticalEvents.get(0));
                    criticalEventsCounter = oPage_PatientAppointmentDetails.listRadioButtons_criticalEvents.size();
                } catch (ElementClickInterceptedException e) {
                    e.printStackTrace();
                }

                try {
                    Cls_Generic_Methods.clickElementByAction(driver,
                            oPage_PatientAppointmentDetails.radioButton_majorEventsSelector);
                    Thread.sleep(1000);
                    Cls_Generic_Methods.clickElementByAction(driver,
                            oPage_PatientAppointmentDetails.listRadioButtons_majorEvents.get(0));
                    majorEventsCounter = oPage_PatientAppointmentDetails.listRadioButtons_majorEvents.size();
                } catch (ElementClickInterceptedException e) {
                    e.printStackTrace();
                }

                try {
                    Cls_Generic_Methods.clickElementByAction(driver,
                            oPage_PatientAppointmentDetails.radioButton_minorEventsSelector);
                    Thread.sleep(1000);
                    Cls_Generic_Methods.clickElementByAction(driver,
                            oPage_PatientAppointmentDetails.listRadioButtons_minorEvents.get(0));
                    minorEventsCounter = oPage_PatientAppointmentDetails.listRadioButtons_minorEvents.size();
                } catch (ElementClickInterceptedException e) {
                    e.printStackTrace();
                }

                Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_modalAdverseEventClose);
                Thread.sleep(1000);

                int expectedCriticalSelectorsCount = 16;
                int expectedMajorSelectorsCount = 15;
                int expectedMinorSelectorsCount = 7;

                m_assert.assertTrue(criticalEventsCounter == expectedCriticalSelectorsCount, "Validate "
                        + criticalEventsCounter + " Critical Events options are displayed under Adverse Events");
                m_assert.assertTrue(majorEventsCounter == expectedMajorSelectorsCount,
                        "Validate " + majorEventsCounter + " Major Events options are displayed under Adverse Events");
                m_assert.assertTrue(minorEventsCounter == expectedMinorSelectorsCount,
                        "Validate " + minorEventsCounter + " Minor Events options are displayed under Adverse Events");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertTrue(false, "" + e);
            }

            // Validate Send To different departments buttons
            // Go to the ALL tab

//			String tabToSelect = "All";
//			m_assert.assertTrue(
//					CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
//					"Validate " + tabToSelect + " tab is selected");
//
//			int patientIndex = -1;
//			String patientName = null;
//
//			for (WebElement patient : oPage_OPD.rows_patientAppointments) {
//				if (patient.isDisplayed()) {
//					patientIndex = oPage_OPD.rows_patientAppointments.indexOf(patient);
//					break;
//				}
//			}
//
//			if (patientIndex >= 0) {
//				Cls_Generic_Methods.clickElement(driver, oPage_OPD.rows_patientAppointments.get(patientIndex));
//				Thread.sleep(1000);
//				Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.section_PatientDetails,
//						8);
//
//				List<WebElement> patientDetailsOnRow = oPage_OPD.rows_patientAppointments.get(patientIndex)
//						.findElements(By.xpath("./child::*"));
//
//				patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");
//				m_assert.assertTrue(true, "Patient " + (patientName) + " is selected");
//			} else {
//				m_assert.assertTrue(false, "<b>No patient</b> found on the current page");
//			}


            if (oPage_PatientAppointmentDetails.listButtons_sendToDepartmentsParentButtons.size() == 0) {
                m_assert.assertWarn(false, "Validate send to Departments buttons are displayed");
            } else {
                for (WebElement departmentButton : oPage_PatientAppointmentDetails.listButtons_sendToDepartmentsParentButtons) {
                    String buttonText = Cls_Generic_Methods.getTextInElement(departmentButton);
                    Thread.sleep(1000);
                    CommonActions.validateSendToDepartmentButtons(buttonText);
                }
            }


            m_assert.assertTrue(
                    Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_scheduleOT),
                    "Validate Schedule OT button is displayed");

            m_assert.assertTrue(
                    Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.input_patientNote),
                    "Validate Enter Patient Note area is displayed");

            String inputData = "Demo Patient Note";

            Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientAppointmentDetails.input_patientNote, inputData);

            Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_savePatientNote);
            Thread.sleep(1000);

            Cls_Generic_Methods.waitForElementToBeDisplayed(
                    oPage_PatientAppointmentDetails.listText_patientNotesComment.get(0), 8);
            int index = 0;

            try {
                for (@SuppressWarnings("unused") WebElement ePatientComment : oPage_PatientAppointmentDetails.listText_patientNotesComment) {

                    oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
                    Thread.sleep(1000);
                    String inputCommentOnUI = Cls_Generic_Methods
                            .getTextInElement(oPage_PatientAppointmentDetails.listText_patientNotesComment.get(index));
//				index = oPage_PatientAppointmentDetails.listText_patientNotesComment.indexOf(ePatientComment);
                    m_assert.assertTrue(inputCommentOnUI.equals(inputData),
                            "Validate " + inputData + " is entered as Patient Note");
                    if (inputCommentOnUI.equals(inputData)) {
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_PatientAppointmentDetails.listText_patientNotesDelete.get(index)),
                                "Validate delete is clicked for comment - " + inputCommentOnUI);
                    } else {
                        index++;
                        m_assert.assertTrue(false, "Validate delete is clicked for comment - " + inputCommentOnUI);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertTrue(false, "Exception while validating patient Note " + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate Dilation")
    public void validateDilation() {

        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        int dilationWaitTimeInSecs = 3;

        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);


            try {
                // Assuming that the patient is already created
                // Go to All Tab
                // Select the Patient

                String tabToSelect = "All";
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                        "Validate " + tabToSelect + " tab is selected");

                String patientName = null;
                Cls_Generic_Methods.customWait(5);
                for (WebElement patient : oPage_OPD.rows_patientAppointments) {
                    if (patient.isDisplayed()) {
                        List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                        patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");
                        if (concatPatientFullName.equals(patientName.trim())) {
                            Cls_Generic_Methods.clickElement(driver, patient);
                            Thread.sleep(1500);
                            break;
                        }

                    }
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                        30);

                // Validate that the default tab that's selected is Appointment Details on  RHS
                for (WebElement eTab : oPage_PatientAppointmentDetails.listTabs_tabsOnPatientDetails) {
                    String sTabName = Cls_Generic_Methods.getTextInElement(eTab);
                    if (sTabName.contains("Overview")) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, eTab),
                                "Validate " + sTabName + " tab is selected");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(
                                oPage_PatientAppointmentDetails.button_dilate_OR_dilateAgain, 20);
                    }
                }


//				String dilationEyeDrops = "";
//				String dilationEyeLaterality = "";
//				String dilationAdvisingDoctor = "";
                String dilationDilatingUser = "asd";
                String dilationDilationComment = "asd";

                boolean bDilationStarted = false, bDilationStopped = false;

                // Click on Dilate button
                // Enter dilation options
                // Start Dilation
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_dilate_OR_dilateAgain, 20);
                String dilateButtonText = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.button_dilate_OR_dilateAgain);
                if (dilateButtonText.equalsIgnoreCase("Dilate")) {
                    Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_dilate_OR_dilateAgain);
                    bDilationStarted = CommonActions.fillDilationDetailsByIndex(1, 1, 1, dilationDilatingUser, dilationDilationComment);
                } else if (dilateButtonText.equalsIgnoreCase("Dilate Again")) {
                    m_assert.assertWarn(false, "Dilation already done. Clicking Dilate Again Button");
                    Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_dilate_OR_dilateAgain);
                    bDilationStarted = CommonActions.fillDilationDetailsByIndex(1, 1, 1, dilationDilatingUser, dilationDilationComment);
                    Cls_Generic_Methods.customWait(4);
                } else {
                    m_assert.assertTrue(false, "Validate Dilate Button is currently as - " + dilateButtonText);
                }

                // Wait n secs (wait time dynamic)
                Thread.sleep(dilationWaitTimeInSecs * 1000);

                // Validate Dilate badge on patient row - Red(badge-danger)
                String sDilationBadgeTextBefore = CommonActions.getDilationBadgeClassForPatient(concatPatientFullName);

                if (sDilationBadgeTextBefore.contains("badge-danger")) {
                    m_assert.assertTrue("Validate Dilation badge on patient Row should be red(In Progress)");
                } else {
                    m_assert.assertTrue(false,
                            "Validate Dilation badge on patient Row should be red(In Progress). Current badge class = "
                                    + sDilationBadgeTextBefore);
                }

                // Stop dilation
                Cls_Generic_Methods.waitForElementToBeDisplayed(
                        oPage_PatientAppointmentDetails.button_stopDilation, 20);
                dilateButtonText = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.button_stopDilation);
                if (dilateButtonText.equalsIgnoreCase("Stop")) {
                    bDilationStopped = Cls_Generic_Methods.clickElement(driver,
                            oPage_PatientAppointmentDetails.button_stopDilation);
                    m_assert.assertTrue(bDilationStopped, "Stop Dilation button is clicked.");
                }

                Thread.sleep(2000);
                // Validate Dilate badge on patient row - Green(badge-success)
                String sDilationBadgeTextAfter = CommonActions.getDilationBadgeClassForPatient(concatPatientFullName);

                if (sDilationBadgeTextAfter.contains("badge-danger")) {
                    m_assert.assertTrue(false, "Validate Dilation badge on patient Row should be green(Completed)");
                } else if (sDilationBadgeTextAfter.contains("badge-success")) {
                    m_assert.assertTrue("Validate Dilation Badge on patient Row should be green(Completed)");
                } else {
                    m_assert.assertTrue(false, "Dilation badge not found");
                }

                // Validate Dilation details messages displayed after dilation process
                for (WebElement eDilationMessageKey : oPage_PatientAppointmentDetails.list_afterDilationMessagesKeys) {

                    String key = Cls_Generic_Methods.getTextInElement(eDilationMessageKey);
                    String value = Cls_Generic_Methods
                            .getTextInElement(oPage_PatientAppointmentDetails.list_afterDilationMessagesValues
                                    .get(oPage_PatientAppointmentDetails.list_afterDilationMessagesKeys
                                            .indexOf(eDilationMessageKey)));

                    System.out.println(key + "\n" + value);
                    m_assert.assertInfo(key + " " + "<br>" + value);

                    key = null;
                    value = null;
                }

                m_assert.assertTrue(bDilationStarted && bDilationStopped, "Validate Dilation for patient " + patientName
                        + " is done in " + dilationWaitTimeInSecs + " secs");


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertTrue(false, "" + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        } finally {

        }
    }

    @Test(enabled = true, description = "Validate Appointment Details on Patient Appointment Details section")
    public void validateAppointmentDetailsTabOnUI() {

        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String expectedLoggedInUser = EHR_Data.user_ARNCT2;

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);

            // Assuming that the patient is already created
            // Select the patient
            String tabToSelect = OPD_Data.tab_ALL;
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            String patientNameOnUI = null;

            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                    "Validate " + tabToSelect + " tab is selected");

            CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

            // Validate all Schedule Admission elements
            m_assert.assertTrue(
                    Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_editAppointment),
                    "Validate Edit Appointment button is displayed");

            m_assert.assertTrue(
                    Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_surgeryAdvised),
                    "Validate Surgery Advised button is displayed");

            m_assert.assertTrue(
                    Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_scheduleAdmission),
                    "Validate Schedule Admission button is displayed");

            m_assert.assertTrue(
                    Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.text_consultantValue),
                    "Validate Consultant Value is displayed = " + Cls_Generic_Methods
                            .getTextInElement(oPage_PatientAppointmentDetails.text_consultantValue));

            m_assert.assertTrue(
                    Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.text_scheduledByValue),
                    "Validate Scheduled By Value is displayed = " + Cls_Generic_Methods
                            .getTextInElement(oPage_PatientAppointmentDetails.text_scheduledByValue));

            m_assert.assertTrue(
                    Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.text_appointmentTypeValue),
                    "Validate Appointment Type Value is displayed = " + Cls_Generic_Methods
                            .getTextInElement(oPage_PatientAppointmentDetails.text_appointmentTypeValue));

            m_assert.assertTrue(
                    Cls_Generic_Methods
                            .isElementDisplayed(oPage_PatientAppointmentDetails.button_rescheduleAppointment),
                    "Validate Reschedule Appointment button is displayed");

            m_assert.assertTrue(
                    Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_cancelAppointment),
                    "Validate Cancel Appointment button is displayed");

            m_assert.assertTrue(
                    Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_uploadPapers),
                    "Validate Upload Papers button is displayed");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validate Investigation Details Panel on Patient Appointment Details section")
    public void validateInvestigationDetailsTabOnUI() {

        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

        String expectedLoggedInUser = EHR_Data.user_ARNCT2;

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);

            // Assuming that the patient is already created
            // Select the patient
            String tabToSelect = OPD_Data.tab_ALL;
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            String patientNameOnUI = null;

            m_assert.assertTrue(
                    CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                    "Validate " + tabToSelect + " tab is selected");

            boolean bPatientFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

            if (bPatientFound) {
                // Validate that the Schedule Admission section is displayed
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.section_appointmentDetails,
                        8);

                // Validate that the default tab selected is Appointment Details
                String sTabName = "Investigations";
                oPage_OPD.selectTabOnAppointmentDetailsInOPD(oPage_PatientAppointmentDetails.listTabs_tabsOnPatientDetails, sTabName);

                Cls_Generic_Methods
                        .waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_refreshInvestigationDetails,
                                8);

                // Validate all Investigation Details elements
                // Refresh
                m_assert.assertTrue(
                        Cls_Generic_Methods
                                .isElementDisplayed(oPage_PatientAppointmentDetails.button_refreshInvestigationDetails),
                        "Validate Refresh button is displayed");

                // All Investigations
                m_assert.assertTrue(
                        Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_allInvestigations),
                        "Validate All Investigations button is displayed");

                m_assert.assertInfo(
                        Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_allInvestigations),
                        "Click All Investigations button");

                int expectedAllInvestigationsOptions = 3;
                m_assert.assertTrue(oPage_PatientAppointmentDetails.list_optionsAllInvestigations.size() == expectedAllInvestigationsOptions,
                        "Validate 3 options under All Investigations are displayed");

                Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_allInvestigations);
                Cls_Generic_Methods.customWait(1);

                // All Reports
                m_assert.assertTrue(
                        Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_allReports),
                        "Validate All Reports button is displayed");

                m_assert.assertInfo(
                        Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_allReports),
                        "Click All Reports button");

                int expectedAllReportsOptions = 3;
                m_assert.assertTrue(oPage_PatientAppointmentDetails.list_optionsAllReports.size() == expectedAllReportsOptions,
                        "Validate 3 options under All Reports are displayed");

                Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_allReports);
                Thread.sleep(1000);

                // Inform Technician
                m_assert.assertWarn(
                        Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_informTechnician),
                        "Validate Inform Technician button is displayed");

                // View Reports
                m_assert.assertTrue(
                        Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_viewReports),
                        "Validate View Reports button is displayed");

                // Add Investigations
                m_assert.assertTrue(
                        Cls_Generic_Methods.isElementDisplayed(oPage_PatientAppointmentDetails.button_addInvestigations),
                        "Validate Add Investigation button is displayed");

                m_assert.assertInfo(
                        Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_addInvestigations),
                        "Click All Reports button");

                int expectedAddInvestigationsOptions = 3;
                m_assert.assertTrue(oPage_PatientAppointmentDetails.list_optionsAddInvestigation.size() == expectedAddInvestigationsOptions,
                        "Validate 3 options under All Reports are displayed");

                Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_addInvestigations);
            } else {
                m_assert.assertTrue(false, "Validate patient Found in OPD");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(enabled = true, description = "Validate allergies in opd and summary")
    public void validateShowAllergiesPoliciesInOPD() throws Exception {
        Page_NewPatientRegisteration oPage_NewPatientRegisteration;
        myPatient = map_PatientsInExcel.get(patientKey);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);

        try {

            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            //creating patient in opd
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
            if (!myPatient.getsSALUTATION().isEmpty()) {
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(
                                oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()),
                        "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
            }
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                    myPatient.getsFIRST_NAME() + " entered for First Name");

            m_assert.assertTrue(
                    Cls_Generic_Methods.sendKeysIntoElement(
                            oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                            myPatient.getsMOBILE_NUMBER()),
                    myPatient.getsMOBILE_NUMBER() + " entered for Mobile Number");

            Cls_Generic_Methods.customWait(3);

            CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm,
                    "Allergies");


            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.button_AntiviralAgents), "select on Antiviral Agents");
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.button_Enfuvirtide), "select on Enfuvirtide");
            Cls_Generic_Methods.customWait(3);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.button_ContactAllergyAdhesiveTape), "select on Adhesive Tape");
            Cls_Generic_Methods.customWait(3);

            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.button_FoodAllergyAllSeaFood), "select on All Sea Food");
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.button_Save), "click on Save");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    15);
//
            //finding patient
            String tabToSelect = OPD_Data.tab_MY_QUEUE;
            myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                    "Validate " + tabToSelect + " tab is selected");
            Cls_Generic_Methods.customWait(10);
            boolean bPatientFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
            m_assert.assertTrue(bPatientFound, "Validate that the patient " + concatPatientFullName + " is created in OPD");

            if (bPatientFound) {
                String DrugAllergisinOpd = (Cls_Generic_Methods.getTextInElement(oPage_OPD.text_DrugAllergiesOPDRHS));
                String ContactAllergisinOpd = (Cls_Generic_Methods.getTextInElement(oPage_OPD.text_contactAllergiesOPDRHS));
                String FoodtAllergisinOpd = (Cls_Generic_Methods.getTextInElement(oPage_OPD.text_FoodAllergiesOPDRHS));

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary), "click on Summary");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);


                String DrugAllergiesSummary = (Cls_Generic_Methods.getTextInElement(oPage_OPD.text_DrugAllergiesSummary));
                m_assert.assertTrue(DrugAllergisinOpd.equalsIgnoreCase(DrugAllergiesSummary),
                        DrugAllergisinOpd + " and " + DrugAllergiesSummary + "both are same");

                String ContactAllergiesSummary = (Cls_Generic_Methods.getTextInElement(oPage_OPD.text_ContactAllergiesSummary));
                m_assert.assertTrue(ContactAllergisinOpd.equalsIgnoreCase(ContactAllergiesSummary),
                        ContactAllergisinOpd + " and " + ContactAllergiesSummary + "both are same");

                String FoodAllergiesSummary = (Cls_Generic_Methods.getTextInElement(oPage_OPD.text_FoodAllergiesSummary));
                m_assert.assertTrue(FoodtAllergisinOpd.equalsIgnoreCase(FoodAllergiesSummary),
                        FoodtAllergisinOpd + " and " + FoodAllergiesSummary + "both are same");

            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validate Patient in ipd and add history, validate in summary")
    public void validateShowAllergiesToIpd() {
        oPage_OPD = new Page_OPD(driver);
        oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oEHR_Data = new EHR_Data();
        oPage_IPD = new Page_IPD(driver);

        String concatPatientFullName = "";
        boolean bPatientFoundInOpd = false;
        boolean bPatientFoundInIpd = false;
        String admType = "Same Day";
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

            try {
                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                bPatientFoundInOpd = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }
            if (bPatientFoundInOpd) {
                m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
                        "Scheduled admission button is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
                        "Clicked on scheduled admission button");

                //Fill Schedule Admission Form
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_ScheduleAdmissionForm, 20),
                        "Scheduled admission form is displayed");

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_EditPatient),
                        "click on Edit Patient");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_History, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_History),
                        "click on History");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.rows_textEyeDisease),
                        "select Eye disease");
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.rows_SystemicAlchoism),
                        "select systemic alcoholism");

                //Admission Type Same day scheduling admission
                for (WebElement radioAdmissionBtn : oPage_ScheduleAdmission.list_radioAdmissionType) {
                    String admissionTypeBtn = Cls_Generic_Methods.getTextInElement(radioAdmissionBtn);
                    if (admissionTypeBtn.equalsIgnoreCase(admType)) {
                        try {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, radioAdmissionBtn), admissionTypeBtn + " Button in Admission Type is Clickable");
                        } catch (Exception e) {
                            m_assert.assertFatal(admissionTypeBtn + " button is not clickable");
                        }
                        break;
                    }
                }
                //Case Detail Tab
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.button_viewCaseDetails),
                        "View case details button is clicked");

                //Create Admission
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_createAdmission),
                        "Create admission button is clicked");

                Cls_Generic_Methods.customWait(4);

                //Assign Bed
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_assignBed, 15),
                        "Assigned bed Form is displayed");
                Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_closeAdmissionForm);
                oPage_IPD = new Page_IPD(driver);
                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
            }

            //finding patient in ipd
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

            m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                    "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
            Cls_Generic_Methods.customWait(3);
            bPatientFoundInIpd = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);
            m_assert.assertTrue(bPatientFoundInIpd, "Validate that the patient " + concatPatientFullName + " is created in IPD");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

            try {
                if (bPatientFoundInIpd) {
                    String HistoryinIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_historyAndAllerigiesRHS));
                    String DrugAllergisinIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_DrugAllergiesInRHSIPD));
                    String ContactAllergisinIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_ContactAllergiesInRHSIPD));
                    String FoodtAllergisinIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_FoodAllergiesInRHSIPD));

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary), "click on Summary");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);

                    String HistorySummaryIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_historyInSummaryIPD));
                    m_assert.assertTrue(HistoryinIPD.equalsIgnoreCase(HistorySummaryIPD),
                            HistoryinIPD + " and " + HistorySummaryIPD + "both are same");

                    String DrugAllergiesSummaryIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_DrugAllergiesInSummaryIPD));
                    m_assert.assertTrue(DrugAllergisinIPD.equalsIgnoreCase(DrugAllergiesSummaryIPD),
                            DrugAllergisinIPD + " and " + DrugAllergiesSummaryIPD + "both are same");

                    String ContactAllergiesSummaryIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_ContactAllergiesInSummaryIPD));
                    m_assert.assertTrue(ContactAllergisinIPD.equalsIgnoreCase(ContactAllergiesSummaryIPD),
                            ContactAllergisinIPD + " and " + ContactAllergiesSummaryIPD + "both are same");

                    String FoodAllergiesSummaryIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_FoodAllerigiesInSummaryIPD));
                    m_assert.assertTrue(FoodtAllergisinIPD.equalsIgnoreCase(FoodAllergiesSummaryIPD),
                            FoodtAllergisinIPD + " and " + FoodAllergiesSummaryIPD + "both are same");

                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Not found in ipd" + e);
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }

    }

    @Test(enabled = true, description = "Schedule in OT and validate in history")
    public void validateShowAllergiesFromOT() throws Exception {

        oPage_IPD = new Page_IPD(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_OT = new Page_OT(driver);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        boolean bPatientFoundIpd = false;
        boolean bPatientFoundOt = false;
        String expectedLoggedInUser = EHR_Data.user_PRAkashTest;

        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                    10);
            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();

                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today),
                        "Validate " + IPD_Data.tab_Scheduled_Today + " tab is selected");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
                bPatientFoundIpd = CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientsOnScheduledTodayTab, concatPatientFullName);

                m_assert.assertTrue(bPatientFoundIpd, "Validate that the patient " + concatPatientFullName + " is created in IPD");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);

                if (bPatientFoundIpd) {

                    Cls_Generic_Methods.scrollToElementByJS(oPage_OT.button_scheduleOt);
                    m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_OT.button_scheduleOt),
                            "Scheduled OT button is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OT.button_scheduleOt),
                            "Schedule OT button clicked");

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_EditPatient,
                            15);


                    //Fill Schedule Admission Form
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_EditPatient),
                            "click on Edit Patient");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.button_History),
                            "click on History");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);


                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.rows_textEyeDisease),
                            "select Eye disease");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_IPD.rows_SystemicAlchoism),
                            "select systemic alchoism");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);

                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OT.header_OTForm, 15),
                            "New OT form is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OT.checkbox_timeSlotOverlap),
                            "Time Slot Overlap Checkbox marked");
                    Cls_Generic_Methods.customWait(4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OT.button_viewCaseDetails),
                            "View case details button clicked");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OT.button_saveOtForm),
                            "Schedule OT button is clicked on OT form");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 25);

                    m_assert.assertInfo(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_OT.link_Ot),
                            "OT link is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OT.link_Ot),
                            "Clicked on OT link");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 16);
                    m_assert.assertInfo(true, "OT page Displayed");

                }

                //finding patient in ot
                String admittedTab = OT_Data.tab_SCHEDULED;
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();

                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OT.tabs_TabsOnOT, admittedTab),
                        "Validate " + admittedTab + " tab is selected");
                Cls_Generic_Methods.customWait();

                bPatientFoundOt = CommonActions.selectPatientNameInOT(oPage_OT.rows_patientNamesOnOT, concatPatientFullName);

                if (bPatientFoundOt) {
                    String HistoryinIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_historyAndAllerigiesRHS));
                    String DrugAllergisinOT = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_DrugAllergiesInRHSIPD));
                    String ContactAllergisinIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_ContactAllergiesInRHSIPD));
                    String FoodtAllergisinIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_FoodAllergiesInRHSIPD));

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_OPD.button_Summary), "click on Summary");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);

                    String HistorySummaryIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_historyInSummaryIPD));
                    m_assert.assertTrue(HistoryinIPD.equalsIgnoreCase(HistorySummaryIPD),
                            HistoryinIPD + " and " + HistorySummaryIPD + "both are same");

                    String DrugAllergiesSummaryIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_DrugAllergiesInSummaryIPD));
                    m_assert.assertTrue(DrugAllergisinOT.equalsIgnoreCase(DrugAllergiesSummaryIPD),
                            DrugAllergisinOT + " and " + DrugAllergiesSummaryIPD + "both are same");

                    String ContactAllergiesSummaryIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_ContactAllergiesInSummaryIPD));
                    m_assert.assertTrue(ContactAllergisinIPD.equalsIgnoreCase(ContactAllergiesSummaryIPD),
                            ContactAllergisinIPD + " and " + ContactAllergiesSummaryIPD + "both are same");

                    String FoodAllergiesSummaryIPD = (Cls_Generic_Methods.getTextInElement(oPage_IPD.text_FoodAllerigiesInSummaryIPD));
                    m_assert.assertTrue(FoodtAllergisinIPD.equalsIgnoreCase(FoodAllergiesSummaryIPD),
                            FoodtAllergisinIPD + " and " + FoodAllergiesSummaryIPD + "both are same");

                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while admitting patient to OT" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


