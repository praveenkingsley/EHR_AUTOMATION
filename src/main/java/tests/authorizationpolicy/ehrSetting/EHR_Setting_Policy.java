package tests.authorizationpolicy.ehrSetting;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Recordset;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import com.healthgraph.SeleniumFramework.Util.SuiteUtil.SuiteUtil;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class EHR_Setting_Policy extends TestBase {
    String sAdminUser = EHR_Data.user_PRAkashTest;
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    boolean defaultUserEnabled;
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;

    WebDriver dDriver;
    WebDriver iDriver;
    String adminTab = "";
    String otherTab = "";
    static HashMap<String, String> policyData;
    String sPolicyDescription;


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

        String sPolicyType = "EHR SETTINGS";
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
            SoftAssert.driver = dDriver;
        } else {
            Cls_Generic_Methods.switchToWindowHandle(adminTab);
        }
    }

    public void switchToPolicyUser() {
        if (iDriver != null) {
            TestBase.driver = iDriver;
            SoftAssert.driver = iDriver;
        } else {
            Cls_Generic_Methods.switchToWindowHandle(otherTab);
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
            String query = "SELECT * FROM EHR_SETTING";
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

    public void openOrganisationSetting() throws Exception {
        CommonActions.loginFunctionality(sPolicyUser);
        Cls_Generic_Methods.waitForPageLoad(driver,20);
        boolean organisationSettingAccess=CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
        if(!organisationSettingAccess){
            throw new Exception("Policy User Don't have access to Organisation Setting");
        }
    }
    public void openNewTab(){
        try {
            if (iDriver == null) {
                Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
                otherTab = driver.getWindowHandle();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isButtonEnabled(WebElement element) {
        boolean status = true;
        try {
            if (element.getAttribute("class").contains("disabled") || element.getAttribute("title").contains("Not Authorized")) {
                status = false;
            }
        } catch (Exception e) {
            status=false;
            e.printStackTrace();
        }
        return status;
    }

}