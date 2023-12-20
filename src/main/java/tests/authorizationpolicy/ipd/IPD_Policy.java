package tests.authorizationpolicy.ipd;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Recordset;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import com.healthgraph.SeleniumFramework.Util.SuiteUtil.SuiteUtil;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import pages.commonElements.CommonActions;
import pages.commonElements.bills.Page_Bills;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.opd.Page_OPD;
import pages.ot.Page_OT;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import tests.departments.opd.AddPatientsTest;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class IPD_Policy extends TestBase {

    String sAdminUser = EHR_Data.user_PRAkashTest;
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    boolean defaultUserEnabled;
    Page_Navbar oPage_Navbar;
    SoftAssert oSoftAssert;
    Page_OrganisationSetup oPage_OrganisationSetup;

    WebDriver dDriver;
    WebDriver iDriver;
    String adminTab = "";
    String ipdTab = "";
    static HashMap<String, String> policyData;
    String sPolicyDescription;

    boolean timeSlotDisabled = false;
    boolean timeSlotEnabled = false;

    final String sTabScheduled = getScheduledTabName();
    final String sTabMyQueue = "MY QUEUE";
    final String sTabDischargedToday = "DISCHARGED TODAY";

    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    String concatPatientFullName = "";

    @BeforeTest
    public void checkConditions() {

        dDriver = TestBase.driver;

        //If Policy User is diff , open new incognito window
        if (!sPolicyUser.equals(sAdminUser)) {
            openIncognitoWindow();
            TestBase.driver = iDriver;
        }

        //GET DESCRIPTION
        policyData = getPolicyDescriptionFromExcel();
        System.out.println(policyData);
    }

    @BeforeMethod
    public void initElements() {

        if (!sPolicyUser.equals(sAdminUser)) {
            TestBase.driver = iDriver;
        }

        defaultUserEnabled = false;
    }

    public void setPolicy(String policyName, boolean enable) {

        if (iDriver != null) {
            switchToAdmin();
        }

        String sPolicyType = "IPD";
        sPolicyDescription = policyData.get(policyName);
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

        try {

            boolean allUsersClicked = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 5);

            if (!allUsersClicked) {
                String expectedLoggedInUser = sAdminUser;
                Cls_Generic_Methods.customWait(3);
                CommonActions.loginFunctionality(expectedLoggedInUser);
                boolean notifyMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.text_notifyWorkingFrom, 3);

                if (notifyMsg) {
                    Cls_Generic_Methods.customWait(10);
                }

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 10);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUsers);
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            if (enable) {
                CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyType, policyName, sPolicyDescription);
            } else {
                CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyType, policyName, sPolicyDescription);
            }
            Cls_Generic_Methods.customWait(3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToAdmin() {
        if (iDriver != null) {
            TestBase.driver = dDriver;
//            SoftAssert.driver = dDriver;
        } else {
            Cls_Generic_Methods.switchToWindowHandle(adminTab);
        }
    }

    public void switchToPolicyUser() {
        if (iDriver != null) {
            TestBase.driver = iDriver;
//            SoftAssert.driver = iDriver;
        } else {
            Cls_Generic_Methods.switchToWindowHandle(ipdTab);
        }
    }

    public void openIncognitoWindow() {

        try {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "Resources"
                    + File.separator + "Drivers" + File.separator + "chromedriver.exe");
            ChromeOptions options = new ChromeOptions();

            options.addArguments("--remote-allow-origins=*");
            options.addArguments("start-maximized");
            options.addArguments("--incognito");
            iDriver = new ChromeDriver(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getPolicyDescriptionFromExcel() {
        String patientsExcelFileName = "Policy_Descriptions.xlsx";
        String dataModelsPath = System.getProperty("user.dir") + File.separator + "Resources"
                + File.separator + "Data_Files" + File.separator;
        String filePath = dataModelsPath + patientsExcelFileName;

        Connection connection = null;
        HashMap<String, String> userData;
        ArrayList<HashMap<String, String>> allUserData = new ArrayList<>();
        HashMap<String, String> policyComponentDescription = new HashMap<>();
        boolean addPolicy = false;

        try {
            connection = SuiteUtil._connectToExcel(filePath);
            String query = "SELECT * FROM IPD";
            Recordset recordset = connection.executeQuery(query);
            ArrayList<String> columnHeader = recordset.getFieldNames();


            while (recordset.next()) {
                userData = new HashMap<>();

                for (String columnValue : columnHeader) {
                    try {
                        if (!recordset.getField("component_name").isEmpty()) {
                            userData.put(columnValue.trim(), recordset.getField(columnValue.trim()));
                            addPolicy = true;
                        }
                    } catch (Exception ignored) {
                    }
                }
                if (addPolicy) {
                    allUserData.add(userData);
                    addPolicy = false;
                }
            }

            for (HashMap<String, String> extractData : allUserData) {
                String componentName = extractData.get("component_name").toUpperCase().trim();
                String featureName = extractData.get("feature_name").toUpperCase().trim();
                String description = extractData.get("description").trim();

                policyComponentDescription.put(componentName + " (" + featureName + ")", description);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return policyComponentDescription;
    }

    public void createPatient() {
        try {

            CommonActions.loginFunctionality(sPolicyUser);
            CommonActions.selectFacility("TST");
            CommonActions.selectDepartmentOnApp("OPD");
            Cls_Generic_Methods.customWait(4);
            boolean patientCreated = selectPatientFromAllTab();

            if (!patientCreated) {
                createPatientWithEssentialPatientDetails(sPolicyUser);
                new AddPatientsTest().clickCreateAppointment();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.waitForPageLoad(driver, 10);
                selectPatientFromAllTab();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPatientWithEssentialPatientDetails(String expectedLoggedInUser) {


        try {
            Model_Patient myPatient = map_PatientsInExcel.get(patientKey);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");

            try {

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
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_NewPatientRegisteration.tabs_PatientRegForm, 20);

                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION());
                }

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                        "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()),
                        "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()),
                        "Last Name is entered as - " + myPatient.getsLAST_NAME());

                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                                myPatient.getsMOBILE_NUMBER()),
                        "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

                Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_specialityAvailable, "Ophthalmology");
                Cls_Generic_Methods.customWait();


            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //If you want to select random patient , then pass TabName and PatientName in params
    public boolean selectPatientFromAllTab(String... tab) {

        String tabToSelect;
        String selectName = "";

        if (tab.length == 0) {
            tabToSelect = "ALL";
        } else {
            tabToSelect = tab[0];
            selectName = tab[1];
        }

        boolean bPatientNameFound = false;


        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
            Model_Patient myPatient = map_PatientsInExcel.get(patientKey);
            String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            if (!selectName.isBlank()) {
                concatPatientFullName = selectName;
            }

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage, 20);
            CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect);

            Cls_Generic_Methods.customWait();

            for (WebElement eTabElement : oPage_OPD.rows_patientAppointments) {

                if (eTabElement.isDisplayed()) {
                    List<WebElement> patientDetailsOnRow = eTabElement.findElements(By.xpath("./child::*"));
                    String patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");

                    if (concatPatientFullName.equals(patientName.trim())) {
                        bPatientNameFound = Cls_Generic_Methods.clickElement(driver, eTabElement);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                                16);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bPatientNameFound;
    }

    public boolean selectPatientFromIpd(String... tabToSelect) {
        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        if (concatPatientFullName.isBlank()) {
            Model_Patient myPatient = map_PatientsInExcel.get(patientKey);
            concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        }

        String tab = sTabScheduled;
        List<WebElement> patientRowElement;

        if (tabToSelect.length > 0) {
            tab = tabToSelect[0];
        }

        switch (tab) {
            case sTabMyQueue -> patientRowElement = oPage_IPD.rows_patientsOnMyQueueTab;
            case sTabDischargedToday -> patientRowElement = oPage_IPD.rows_patientsOnDischargedTodayTab;
            default -> patientRowElement = oPage_IPD.rows_patientsOnScheduledTodayTab;
        }

        boolean patientFound = false;

        try {

            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_IPD.tabs_TabsOnIPD, 20);
            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, tab);

            Cls_Generic_Methods.customWait(5);

            for (WebElement eTabElement : patientRowElement) {
                if (eTabElement.isDisplayed()) {
                    List<WebElement> patientDetailsOnRow = eTabElement.findElements(By.xpath("./child::*"));
                    String patientName = Cls_Generic_Methods.getTextInElement(patientDetailsOnRow.get(0));

                    if (patientName.contains(concatPatientFullName)) {

                        patientFound = Cls_Generic_Methods.clickElement(driver, eTabElement);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 16);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patientFound;
    }
    @AfterMethod
    public void closeTabAndSwitchToOther() {
        if (iDriver == null) {
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } else {
            Cls_Generic_Methods.driverRefresh();
            TestBase.driver = dDriver;
        }

        if (defaultUserEnabled) {
            iDriver.close();
            TestBase.driver = dDriver;
        }
    }
    public boolean enableOrDisableTimeSlot(boolean timeSlot) {
        boolean flag = false;
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

        try {

            //Checking whether script already enabled or disabled timeslot
            if (!timeSlotDisabled && !timeSlot || !timeSlotEnabled && timeSlot) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_orgSettings, 16);

                Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_orgSettings);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.header_orgSettings, 10);

                boolean isTimeSlotEnabled = Cls_Generic_Methods.radioButtonIsSelected(oPage_OrganisationSetup.checkbox_yesEnableTimeSlot);

                if (timeSlot) {
                    if (!isTimeSlotEnabled) {
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.checkbox_yesEnableTimeSlot);
                        flag = true;
                        timeSlotEnabled = true;
                    }
                } else {
                    if (isTimeSlotEnabled) {
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.checkbox_noEnableTimeSlot);
                        flag = true;
                        timeSlotDisabled = true;
                    }
                }

                if (flag) {
                    Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_saveOrgSetting);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_closeEditModal, 5);
                    Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_closeEditModal);
                } else {
                    Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_close);
                    flag = true;
                }

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
            } else {
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean enableOrDisableBillingClearanceInIpd(boolean billingClearance) {
        boolean flag = false;
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_orgSettings, 16);

            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_orgSettings);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.header_orgSettings, 5);
            CommonActions.selectOptionUnderOrgSettings("Finance Settings");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.radBtn_billClearanceYes, 5);

            boolean isBillingClearanceEnabled = Cls_Generic_Methods.radioButtonIsSelected(oPage_OrganisationSetup.radBtn_billClearanceNo);

            if (billingClearance) {
                if (!isBillingClearanceEnabled) {
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.radBtn_billClearanceYes);
                    flag = true;
                    timeSlotEnabled = true;
                }
            } else {
                if (isBillingClearanceEnabled) {
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.radBtn_billClearanceNo);
                    flag = true;
                    timeSlotDisabled = true;
                }
            }

            if (flag) {
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_saveOrgSetting);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_closeEditModal, 5);
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_closeEditModal);
            } else {
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_close);
                flag = true;
            }

            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForPageLoad(driver, 20);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean isButtonEnabled(WebElement element) {
        boolean status = true;
        try {
            if (element.getAttribute("class").contains("disabled") || element.getAttribute("title").contains("Not Authorized")) {
                status = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    
    public boolean scheduleAdmission(boolean... createMultiple) {
        boolean flag=false;
        boolean multipleAdmission = false;
        String admissionBookingTime ="";

        if (createMultiple.length > 0) {
            multipleAdmission = true;
        }

        Page_ScheduleAdmission oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        boolean patientFound = false;
        try {

            if (!multipleAdmission) {
                CommonActions.loginFunctionality(sPolicyUser);
                CommonActions.selectFacility("TST");
                CommonActions.selectDepartmentOnApp("IPD");
                Cls_Generic_Methods.customWait(4);
                patientFound = selectPatientFromIpd();

                if (!patientFound) {
                    patientFound = selectPatientFromIpd(sTabMyQueue);
                }
                if (!patientFound) {
                    boolean foundPatientInDischargedToday = selectPatientFromIpd(sTabDischargedToday);

                    if (foundPatientInDischargedToday) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_clickToReadmit, 10);
                        Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_clickToReadmit);
                        Cls_Generic_Methods.customWait(4);
                        patientFound = selectPatientFromIpd(sTabMyQueue);
                    }
                }
            }
            if (!patientFound) {
                createPatient();
                patientFound = selectPatientFromAllTab();

                if (patientFound) {

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
                    Cls_Generic_Methods.scrollToElementByAction(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission);
                    Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.header_ScheduleAdmissionForm, 20);

                    WebElement radioAdmissionType = oPage_ScheduleAdmission.list_radioAdmissionType.get(2);
                    Cls_Generic_Methods.clickElementByJS(driver, radioAdmissionType);
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.button_viewCaseDetails);

                    Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_createAdmission);

                    // Get the current date and time
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a,dd MMM yy");
                    admissionBookingTime = currentDateTime.format(formatter);

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduleAdmission.header_assignBed, 15);
                    Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectWard);
                    Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectWard, 1);
                    Cls_Generic_Methods.customWait(1);
                    Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectRoom);
                    Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectRoom, 1);
                    Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_closeAdmissionForm);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);

                    CommonActions.selectDepartmentOnApp("IPD");
                    Cls_Generic_Methods.customWait(4);
                    patientFound=selectPatientFromIpd(sTabScheduled);

                    if(patientFound){
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_bookingTime,10);
                        String actualBookingTime= Cls_Generic_Methods.getTextInElement(oPage_IPD.text_bookingTime);
                        flag=actualBookingTime.equals(admissionBookingTime);
                    }
                } else {
                    m_assert.assertFalse("searched patient is not present in patient list");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }
        return flag;
    }

    public boolean selectPatientFromOt() {

        boolean nameIsSelected = false;
        Page_OT oPage_OT = new Page_OT(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            CommonActions.selectDepartmentOnApp("OT");
            Cls_Generic_Methods.customWait(5);

            for (WebElement eTabElement : oPage_OT.rows_patientNamesOnOT) {

                if (eTabElement.isDisplayed()) {
                    String patientName = Cls_Generic_Methods.getTextInElement(eTabElement);
                    if (concatPatientFullName.equals(patientName.trim())) {
                        Cls_Generic_Methods.clickElement(driver, eTabElement);
                        nameIsSelected = true;
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 16);
                        break;
                    }
                }
            }

            if (!nameIsSelected) {

                scheduleAdmission();
                boolean patientFound = selectPatientFromIpd(sTabScheduled);

                if (patientFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_readyForAdmission, 6);
                    Cls_Generic_Methods.clickElement(oPage_IPD.button_readyForAdmission);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 10);
                } else {
                    //If Patient not found in Schedule , Search in MyQueue
                    boolean patientFoundInMyQueue = selectPatientFromIpd(sTabMyQueue);
                    if (patientFoundInMyQueue) {
                        boolean admitBtn = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.button_admitPatient, 6);
                        if (admitBtn) {
                            Cls_Generic_Methods.clickElement(oPage_IPD.button_admitPatient);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.header_admissionForm, 10);
                            Cls_Generic_Methods.clickElement(driver, oPage_IPD.button_saveAdmissionForm);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
                        }
                    }
                }

                selectPatientFromIpd(sTabMyQueue);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_scheduleOt, 6);
                Cls_Generic_Methods.clickElement(driver, oPage_OT.button_scheduleOt);
                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OT.header_OTForm, 15);

                Cls_Generic_Methods.clickElementByJS(driver, oPage_OT.checkbox_timeSlotOverlap);
                Cls_Generic_Methods.customWait(4);
                Cls_Generic_Methods.clickElement(driver, oPage_OT.button_viewCaseDetails);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OT.button_saveOtForm, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_OT.button_saveOtForm);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 16);

                CommonActions.selectDepartmentOnApp("OT");
                Cls_Generic_Methods.customWait(5);

                for (WebElement eTabElement : oPage_OT.rows_patientNamesOnOT) {

                    if (eTabElement.isDisplayed()) {
                        String patientName = Cls_Generic_Methods.getTextInElement(eTabElement);
                        if (concatPatientFullName.equals(patientName.trim())) {
                            Cls_Generic_Methods.clickElement(driver, eTabElement);
                            nameIsSelected = true;
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 16);
                            break;
                        }
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return nameIsSelected;
    }

    public static String getScheduledTabName() {
        LocalDate today = LocalDate.now();
        LocalDate threeDaysAgo = today.minusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM''yy", Locale.ENGLISH);
        String formattedDate = threeDaysAgo.format(formatter);

        System.out.println("TAB ----------> " + "SCHEDULED " + formattedDate.toUpperCase() + " - TODAY");
        return "SCHEDULED " + formattedDate.toUpperCase() + " - TODAY";
    }

    public void createNewBill(boolean... draft) {

        boolean createDraftBill = false;
        if (draft.length > 0) {
            createDraftBill = draft[0];
        }

        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_Bills oPage_Bills = new Page_Bills(driver);

        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_clickBills, 10);
            Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.button_cashBill, 10);

            if (oPage_Bills.list_previousBills.size() == 0 || createDraftBill) {

                if (createDraftBill) {
                    Cls_Generic_Methods.clickElement(oPage_Bills.button_newDraftBill);
                } else {
                    Cls_Generic_Methods.clickElement(oPage_Bills.button_cashBill);
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 10);
                Cls_Generic_Methods.selectElementByIndex(driver.findElement(By.xpath("//select[@id='invoice_ipd_services_0_description']")), 1);
                Cls_Generic_Methods.customWait();

                if (!createDraftBill) {
                    Cls_Generic_Methods.scrollToElementByJS(oPage_Bills.select_modeOfPayment);
                    Cls_Generic_Methods.selectElementByValue(oPage_Bills.select_modeOfPayment, "Cash");
                    Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_clickSaveFinalBills);

                } else {
                    String netAmount = Cls_Generic_Methods.getElementAttribute(oPage_Bills.text_netBillTotal, "value");
                    Cls_Generic_Methods.clearValuesInElement(oPage_Bills.input_amountPending);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_Bills.input_amountPending, netAmount);
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_saveDraftBillButton);
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Bills.text_billHeading, 20);
                Cls_Generic_Methods.clickElement(driver, oPage_Bills.button_closeBill);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
            } else {
                Cls_Generic_Methods.clickElement(oPage_Bills.button_clickBills);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
