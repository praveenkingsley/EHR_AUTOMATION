package tests.authorizationpolicy.opd;

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
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import tests.departments.opd.AddPatientsTest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OPD_Policy extends TestBase {
    String sAdminUser = EHR_Data.user_PRAkashTest;
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    boolean defaultUserEnabled;
    Page_Navbar oPage_Navbar;
    SoftAssert oSoftAssert;
    Page_OrganisationSetup oPage_OrganisationSetup;

    WebDriver dDriver;
    WebDriver iDriver;
    String adminTab = "";
    String opdTab = "";
    static HashMap<String, String> policyData;
    String sPolicyDescription;

    boolean timeSlotDisabled = false;
    boolean timeSlotEnabled = false;


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

        String sPolicyType = "OPD";
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
            Cls_Generic_Methods.switchToWindowHandle(opdTab);
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
            String query = "SELECT * FROM OPD";
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
    public void updateExcel(String sheetName, String columnName, String searchValue, String updateColumnName, String newValue) {


        String patientsExcelFileName = "OPD_Policy.xlsx";
        String dataModelsPath = System.getProperty("user.dir") + File.separator + "Resources"
                + File.separator + "Data_Files" + File.separator;
        String filePath = dataModelsPath + patientsExcelFileName;

        try {
            Connection connection = SuiteUtil._connectToExcel(filePath);
            String query = "UPDATE " + sheetName + " SET " + updateColumnName + "='" + newValue + "' WHERE " + columnName + "='" + searchValue + "'";
            int update = connection.executeUpdate(query);
            System.out.println("Updated " + update + " record(s).");
            connection.close();
        } catch (FilloException e) {
            e.printStackTrace();
        }
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

        String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");

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
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.header_orgSettings, 5);

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
    public boolean sendPatientToOtherUser(String userRole, String userName) {
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        boolean bPatientSent = false;
        WebElement button_sendTo = null;
        List<WebElement> list_users = null;

        switch (userRole) {
            case "DOCTOR" -> {
                button_sendTo = oPage_PatientAppointmentDetails.button_sendToDoctor;
                list_users = oPage_PatientAppointmentDetails.listButtons_sendToDoctor;
            }
            case "OPTOMETRIST" -> {
                button_sendTo = oPage_PatientAppointmentDetails.button_sendToOptometrist;
                list_users = oPage_PatientAppointmentDetails.listButtons_sendToOptometrist;
            }
            case "ARNCT" -> {
                button_sendTo = oPage_PatientAppointmentDetails.button_sendToArNct;
                list_users = oPage_PatientAppointmentDetails.listButtons_sendToArNct;
            }
        }

        try {

            Cls_Generic_Methods.clickElement(driver, button_sendTo);
            Cls_Generic_Methods.waitForElementToBeDisplayed(list_users.get(0), 8);

            for (WebElement userRow : list_users) {

                if (userRow.isDisplayed()) {

                    List<WebElement> userDetailsOnRow = userRow.findElements(By.xpath("./child::*"));
                    for (WebElement itemOnRow : userDetailsOnRow) {
                        if (Cls_Generic_Methods.getTextInElement(itemOnRow).equals(userName)) {
                            Cls_Generic_Methods.clickElement(driver, userRow);
                            bPatientSent = true;
                            break;
                        }
                    }

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return bPatientSent;
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
    public void setDefaultUser(String sPolicy) {


        defaultUserEnabled = true;
        timeSlotEnabled = false;
        timeSlotDisabled = false;
        switch (sPolicy) {
            case "CONFIRM APPOINTMENT (APPOINTMENT DETAILS)" -> {
                sPolicyUser = EHR_Data.user_mansa1;
                sAdminUser = EHR_Data.user_PRAkashTest;
            }
            case "MARK AS ARRIVE-MYQUEUE (PATIENT FLOW)", "MARK AS ARRIVE-MYSTATION (PATIENT FLOW)" -> {
                EHR_Data.list_userName.put("Karthik", "hgind_qmsadmin,HGraph@2$2$");
                EHR_Data.list_userName.put("Swapna", "hgind_qmsrec,HGraph@3$3$");

                sPolicyUser = "Swapna";
                sAdminUser = "Karthik";

            }
        }
        openIncognitoWindow();
        TestBase.driver = iDriver;
    }
    public void searchOldPatientAndCreateAppointment(String patientName){

        try{
            CommonActions.loginFunctionality(sPolicyUser);
            Cls_Generic_Methods.customWait(4);
            boolean patientAdded = selectPatientFromAllTab("ALL",patientName);

            if (!patientAdded) {
                Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
                Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

                String selectType = "Name";
                CommonActions.loginFunctionality(sPolicyUser);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_addAppointment);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.select_searchPatientOrAddNewPatient, 5);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_searchPatientOrAddNewPatient, selectType);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_searchPatient, patientName);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_searchPatient);

                do {
                    Cls_Generic_Methods.customWait(2);
                } while (oPage_NewPatientRegisteration.list_textSearchResults.size() < 1);

                for (WebElement searchResult : oPage_NewPatientRegisteration.list_textSearchResults) {
                    String value = Cls_Generic_Methods.getTextInElement(searchResult).toUpperCase();
                    if (value.contains(patientName)) {
                        Cls_Generic_Methods.clickElementByJS(driver, searchResult);
                        break;
                    }
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.rows_appointmentDetails, 20);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm);
                Cls_Generic_Methods.customWait(7);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

