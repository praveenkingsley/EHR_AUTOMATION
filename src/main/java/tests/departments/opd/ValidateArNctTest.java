package tests.departments.opd;

import java.util.List;

import data.OPD_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;

import data.EHR_Data;
import pages.commonElements.CommonActions;

import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.Page_RefractionTab;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;

public class ValidateArNctTest extends TestBase {

    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_OPD oPage_OPD;
    Page_CommonElements oPage_CommonElements;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    Page_Navbar oPage_Navbar;
    Page_RefractionTab oPage_RefractionTab;
    EHR_Data oEHR_Data = new EHR_Data();
    static Model_Patient myPatient;

    @Test(enabled = true, description = "Validate Patient is sent to AR NCT")
    public void validateSendPatientToArNct() {

        myPatient = map_PatientsInExcel.get(patientKey);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
        String tabToSelect = OPD_Data.tab_ALL;
        String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
        concatPatientFullName = concatPatientFullName.toUpperCase().trim();
        String destinationArNct = EHR_Data.user_ARNCT2;
        int requiredArNctIndex = -1;
        boolean requiredArNctFound = false;

        try {

            oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            oPage_OPD = new Page_OPD(driver);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectFacility("TST");
            m_assert.assertInfo(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                    "Validate " + tabToSelect + " tab is selected");

            String myQueueTab = "My Queue";
            int myQueueInitialCount = -1, updatedMyQueueCount = -1;

            myQueueInitialCount = CommonActions.getCountOfPatientsInTab(oPage_OPD.tabs_appointmentTabsOnHomepage,
                    myQueueTab);

            for (WebElement patient : oPage_OPD.rows_patientAppointments) {

                if (patient.isDisplayed()) {
                    try {
                        List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                        String patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");
                        if (concatPatientFullName.equals(patientName.trim())) {
                            Cls_Generic_Methods.clickElement(driver, patient);
                            m_assert.assertInfo(true, "Validate " + concatPatientFullName + " patient is selected");
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertFatal("" + e);
                    }
                }

            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 16);

            m_assert.assertTrue(
                    Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_sendToArNct),
                    "Button for sending patient to AR NCT - <b>" + destinationArNct + "</b> is clicked");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.listButtons_sendToArNct.get(0), 4);


            for (WebElement option_buttonArNctUser : oPage_PatientAppointmentDetails.listButtons_sendToArNct) {
                if (option_buttonArNctUser.isDisplayed()) {

//					System.out.println(option_buttonArNctUser);

                    List<WebElement> arNctDetails = option_buttonArNctUser.findElements(By.xpath("./child::*"));

                    // Children Elements of Each Button
//					List<WebElement> eUserAvailibility = arNctDetails.get(0).findElements(By.xpath("./child::*"));
                    WebElement eUserName = arNctDetails.get(1);
//					WebElement eUserQueueCount = arNctDetails.get(2);

//					System.out.println(Cls_Generic_Methods.getElementAttribute(eUserAvailibility.get(0), "class"));
//					System.out.println(eUserName.getText());
//					System.out.println(eUserQueueCount.getText());
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_PatientAppointmentDetails.listButtons_sendToArNct, 30);
                    if (Cls_Generic_Methods.getTextInElement(eUserName).equals(destinationArNct)) {
                        requiredArNctIndex = oPage_PatientAppointmentDetails.listButtons_sendToArNct.indexOf(option_buttonArNctUser);
                        requiredArNctFound = true;
                        break;
                    }
                }
            }

            m_assert.assertInfo(requiredArNctFound, "Validate " + destinationArNct + " User is found.");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.listButtons_sendToArNct.get(requiredArNctIndex)),
                    "<b>" + destinationArNct + "</b> is clicked");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_OPD.rows_patientAppointments, 20);


            updatedMyQueueCount = CommonActions
                    .getCountOfPatientsInTab(oPage_OPD.tabs_appointmentTabsOnHomepage, myQueueTab);

            if (updatedMyQueueCount == (myQueueInitialCount - 1)) {
                m_assert.assertTrue("Validate Patient Count has been decreased from <b>" + myQueueInitialCount
                        + "</b> to <b>" + updatedMyQueueCount + "</b> in MY QUEUE tab for current user.");
            } else if (updatedMyQueueCount < myQueueInitialCount) {
                m_assert.assertWarn("Validate Patient Count has been decreased from <b>" + myQueueInitialCount
                        + "</b> to <b>" + updatedMyQueueCount + "</b> in MY QUEUE tab for current user.");
            } else {
                m_assert.assertTrue(false, "Validate Patient Count has been decreased from <b>" + myQueueInitialCount
                        + "</b> to <b>" + (myQueueInitialCount - 1) + "</b> in MY QUEUE tab for current user. Actual = " + updatedMyQueueCount);
            }
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_OPD.tabs_appointmentTabsOnHomepage, 20);
            m_assert.assertInfo(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                    "Validate " + tabToSelect + " tab is selected");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validate AR NCT Module")
    public void validatePatientArrivedInArNct() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

        String patientName = null;
//		String status = null;
        boolean bPatientNameFound = false;
        String expectedLoggedInUser = EHR_Data.user_ARNCT2;
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait(5);
            CommonActions.selectFacility("TST");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_searchCriteriaSelector, 20);
            // Assuming that the opened page is OPD
            try {
                String MyQueueTab = "My Queue";
                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                Cls_Generic_Methods.customWait(5);

                for (WebElement patient : oPage_OPD.rows_patientAppointments) {
                    if (patient.isDisplayed()) {
                        List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                        patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");
//						status = Cls_Generic_Methods.getTextInElement(patientDetailsOnRow.get(4));

                        if (concatPatientFullName.equals(patientName.trim())) {
                            Cls_Generic_Methods.clickElement(driver, patient);
                            bPatientNameFound = true;
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
                            break;
                        }
                    }
                }

                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + MyQueueTab + " of Ar Nct");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient in ArNct module " + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }

    }

    @Test(enabled = true, description = "Fill Ar Nct Template Data")
    public void createArNctTemplate() throws Exception {

        oPage_OPD = new Page_OPD(driver);
        oPage_RefractionTab = new Page_RefractionTab(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        String sArNctTemplateOption = "AR/NCT";
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String expectedLoggedInUser = oEHR_Data.user_ARNCT2;
        CommonActions.loginFunctionality(expectedLoggedInUser);
        Cls_Generic_Methods.customWait(3);
        CommonActions.selectFacility("TST");

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_searchCriteriaSelector, 20);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template button clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, sArNctTemplateOption), "Validate " + sArNctTemplateOption + " template  is selected");

            Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightIop);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightIop, myPatient.getsIOP_RIGHT()), "Right Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

            Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftIop);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftIop, myPatient.getsIOP_LEFT()), "Left Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());


            if (CommonActions.templateBadgeIsGreen(oPage_RefractionTab.status_iopBadge)) {
                m_assert.assertTrue(true, "IOP fields are filled successfully, button is GREEN");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Template Saved. ");
            } else if (CommonActions.templateBadgeIsRed(oPage_RefractionTab.status_iopBadge)) {
                m_assert.assertTrue(false, "IOP fields are not filled, button is RED");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 12);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate), "Template Closed.");
            Cls_Generic_Methods.customWait();

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertTrue(false, "Error occurred while filling Arnct Template - " + e);
        }

    }

    @Test(enabled = true, description = "Send Patient to Optometrist")
    public void validateSendPatientFromArNctToOptometrist() throws Exception {

        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String expectedLoggedInUser = oEHR_Data.user_ARNCT2;
        CommonActions.loginFunctionality(expectedLoggedInUser);
        Cls_Generic_Methods.customWait(3);
        CommonActions.selectFacility("TST");
        try {
            String myQueueTab = "My Queue";
            int myQueueInitialCount = -1;
            int updatedMyQueueCount = -1;
            boolean bPatientSent = false;
            String sOptometristName = EHR_Data.user_HGOptom1;

            myQueueInitialCount = CommonActions.getCountOfPatientsInTab(oPage_OPD.tabs_appointmentTabsOnHomepage, myQueueTab);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_sendToOptometrist), "Send to Optometrist clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.listButtons_sendToOptometrist.get(0), 8);

            for (WebElement optometristRow : oPage_PatientAppointmentDetails.listButtons_sendToOptometrist) {
                if (optometristRow.isDisplayed()) {
//					System.out.println(Cls_Generic_Methods.getTextInElement(optometristRow));
                    try {
                        List<WebElement> optometristDetailsOnRow = optometristRow.findElements(By.xpath("./child::*"));
                        for (WebElement itemOnRow : optometristDetailsOnRow) {
                            if (Cls_Generic_Methods.getTextInElement(itemOnRow).equals(sOptometristName)) {
                                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, optometristRow), sOptometristName + "Optometrist selected for the patient.");
                                bPatientSent = true;
                                Thread.sleep(2000);
                                break;
                            }
                        }
                    } catch (ElementNotInteractableException e) {
                        m_assert.assertTrue(false, "<b>Optometrist not selected for the patient</b> " + e);
                        e.printStackTrace();
                    }

                }
                if (bPatientSent == true) {
                    break;
                }
            }
            oPage_OPD = new Page_OPD(driver);
            updatedMyQueueCount = CommonActions.getCountOfPatientsInTab(oPage_OPD.tabs_appointmentTabsOnHomepage,
                    myQueueTab);

            m_assert.assertTrue((updatedMyQueueCount < myQueueInitialCount),
                    "Validate Patient Count has been decreased from <b>" + myQueueInitialCount + "</b> to <b>"
                            + updatedMyQueueCount + "</b> in MY QUEUE tab. So Patient is with optometrist.");

        } catch (InterruptedException e) {
            m_assert.assertTrue(false, "<b>Patient has been not send to optometrist</b> " + e);
            e.printStackTrace();
        }

    }

}
