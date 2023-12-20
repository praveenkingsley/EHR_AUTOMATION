package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.Report.SoftAssert;
import com.healthgraph.SeleniumFramework.dataModels.Model_MedicationSet;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import com.healthgraph.SeleniumFramework.dataModelsUtilities.MedicationSet_Utilities;
import com.healthgraph.SeleniumFramework.dataModelsUtilities.Patient_Utilities;
import data.EHR_Data;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.File;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

public class ParallelTestBase {

    public static String user = EHR_Data.user_PRAkashTest;
    public static final int noOfTabs = 2;

    public String sStore =ParallelTestBase.pharmacy;
    public String sReceivingStore= ParallelTestBase.centralHub1;

    public static CyclicBarrier barrier = new CyclicBarrier(noOfTabs);

    public static WebDriver driver = null;
    public static String timestamp;
    public static String env = "";
    public static String url="";
    public static String currentOs;
    public static String currentMachineName;
    public static String sBrowser;
    public static String className;
    public static String ExtentReportName = "Extent_Report_";
    public static String timeStamp = new SimpleDateFormat("ddMMyyyy_hhmmss_a").format(new Date());
    public static String ExtentReportFileName = ExtentReportName + timeStamp;
    private static final Logger logger = LogManager.getLogger();

    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;
    public static SoftAssert m_assert;
    public static WebDriver sDriver;
    public static Map<String, Model_Patient> map_PatientsInExcel = null;
    public static Map<String, Model_MedicationSet> map_MedicationSetsInExcel = null;

    //STORES
    public static String optical="Optical Store 1- Optical";
    public static String pharmacy="Pharmacy Viet- Pharmacy";
    public static String stationary="AnushkaStationery- Stationery Store";
    public static String ipdStore="OT Store- IPD";
    public static String centralHub1="CENTRAL HUB 01- Central Hub";
    public static String centralHub2="CENTRAL HUB 02- Central Hub";
    public static String houseKeeping="DOPEI HK STORE- House Keeping";
    public static String centralStore="Central Store- Central";

    @BeforeSuite
    public void setUpSuite() {
        try {
            logger.info("BEFORE SUITE");
            currentMachineName = InetAddress.getLocalHost().getHostName();
            currentOs = System.getProperty("os.name");

            try {
                Runtime.getRuntime().exec("taskkill /f /im chrome.exe");
                Thread.sleep(1000);
                Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");

                if (Cls_Generic_Methods.getConfigValues("EnumerateDataExcelFiles").contains("Medication_Sets")) {
                    map_MedicationSetsInExcel = new HashMap<String, Model_MedicationSet>();
                    map_MedicationSetsInExcel = MedicationSet_Utilities.getMedicationSetsFromDataExcelInMap();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                logger.fatal("Error while loading data for Medication_Sets into map_MedicationSetsInExcel. \n" + e1);
            }

            try {
                // Patients Data Load from Excel
                if (Cls_Generic_Methods.getConfigValues("EnumerateDataExcelFiles").contains("Patients")) {
                    map_PatientsInExcel = new HashMap<String, Model_Patient>();
                    map_PatientsInExcel = Patient_Utilities.getPatientsFromDataExcelInMap();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                logger.fatal("Error while loading data for Patients into map_PatientsInExcel. \n" + e1);
            }

            env = Cls_Generic_Methods.environment;
            logger.info("Current Environment = " + env);



            System.out.println();
        } catch (Exception e) {
            logger.fatal("Error occurred in Before Suite. \n" + e);
            e.printStackTrace();
        }

    }

    @BeforeTest
    public void setUp(ITestContext oITestContext) throws Exception {
        htmlReporter = setUpExtentReport(oITestContext);

    }

    @BeforeMethod
    public void setUp1(ITestContext oITestContext) {
        try {

            System.out.println("BEFORE TEST - " + oITestContext.getName());
            logger.info("BEFORE TEST - " + oITestContext.getName());

            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("start-maximized");

            DriverFactory.getInstance().setDriver(new ChromeDriver(options));

            url=Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase());
            WebDriver driver = DriverFactory.getInstance().getDriver();
            driver.navigate().to(url);
            ParallelTestBase.driver=driver;

        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            logger.fatal("Error occurred in Before Test. \n" + e);
        }
    }

    @BeforeMethod
    public void setUp2(Method m) {
        try {

            test = extent.createTest(m.getName());
            ExtentFactory.getInstance().setExtent(test);
            test = ExtentFactory.getInstance().getExtent();

            m_assert = new SoftAssert(driver, extent, test, className);
            Test atTest = m.getAnnotation(Test.class);
            test.assignCategory(atTest.description());

            System.out.println("________________________________________________________________________");
            System.out.println("BEFORE METHOD - " + m.getName());
            System.out.println("________________________________________________________________________");
            System.out.println();
        } catch (Exception e) {
            logger.fatal("Error occurred in Before Method \n" + e);
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void destroyMethod(Method m, ITestResult result) {
        try {


            if (result.getStatus() == ITestResult.FAILURE) {
                test.fail(result.getThrowable());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                try {
                    test.log(Status.PASS, result.getThrowable());
                } catch (NullPointerException e) {
                    System.out.println("Exception from Testbase - " + m.getName() + "\n" + e.toString());
                }
            } else {
                test.skip(result.getThrowable());
            }

            System.out.println();
            System.out.println("________________________________________________________________________");
            System.out.println("AFTER METHOD - " + m.getName());
            System.out.println("________________________________________________________________________");
            System.out.println();

        } catch (Exception e) {
            logger.fatal("Error occurred in After Method \n" + e);
            e.printStackTrace();
        }
    }

    public void tearDown() {
        try {

            DriverFactory.getInstance().getDriver().quit();
            DriverFactory.getInstance().driver.remove();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterTest
    public void destroyTest(ITestContext oITestContext) {
        System.out.println("AFTER TEST - " + oITestContext.getName());
        logger.info("AFTER TEST");

        try {
            System.out.println();

            Thread.sleep(1000);
            driver = null;

        } catch (Exception e) {
            logger.fatal("Error occurred in After Test \n" + e);
            e.printStackTrace();
        } finally {
            extent.flush();
        }
    }

    @AfterSuite
    public void destroySuite() {
        logger.info("AFTER SUITE");
        try {
            System.out.println("AFTER SUITE");
            String reportpath = Cls_Generic_Methods.getConfigValues("ReportPath") + File.separator + ExtentReportFileName + ".html";
            System.out.println();
            System.out.println("Report Path - ");
            System.out.println(reportpath);

        } catch (Exception e) {
            logger.fatal("Error occurred in After Suite \n" + e);
            e.printStackTrace();
        }
    }


    public static ExtentHtmlReporter setUpExtentReport(ITestContext oITestContext) throws Exception {
        htmlReporter = new ExtentHtmlReporter(
                Cls_Generic_Methods.getConfigValues("ReportPath") + File.separator + ExtentReportFileName + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle(oITestContext.getName());
        htmlReporter.config().setReportName(oITestContext.getName());
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setTimeStampFormat("EEEE, dd MM yyyy, hh:mm:ss a");

        extent.setSystemInfo("Browser", sBrowser);
        extent.setSystemInfo("User Name", currentMachineName);
        extent.setSystemInfo("Environment", Cls_Generic_Methods.getEnvironment());

        return htmlReporter;
    }

    public String getDepartment(boolean... receivingStore){

        if(receivingStore.length>0){
            return sReceivingStore.split("-")[1].trim();
        }

        return sStore.split("-")[1].trim();
    }
}
