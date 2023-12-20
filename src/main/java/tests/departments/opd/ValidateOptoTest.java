package tests.departments.opd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Optometrist_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.Page_HistoryTab;
import pages.commonElements.common_tabs.Page_RefractionTab;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.optometrist.Pages_Optometrist;

import java.util.List;

public class ValidateOptoTest extends TestBase {

    Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    Page_OPD oPage_OPD;
    Pages_Optometrist oPages_Optometrist;

    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    Page_Navbar oPage_Navbar;
    //    EHR_Data oEHR_Data = new EHR_Data();
    Optometrist_Data oOptometrist_Data = new Optometrist_Data();
    EHR_Data oEHR_Data = new EHR_Data();
    static Model_Patient myPatient;
    Page_HistoryTab oPage_HistoryTab;
    Page_CommonElements oPage_CommonElements;
    Page_OPD opage_opd;
    Page_RefractionTab oPage_RefractionTab;
    boolean bScriptDebugMode = false;

    @Test(enabled = true, description = "Validate Optometrist Module")
    public void validatePatientArrivedInOptometrist() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        oPage_OPD = new Page_OPD(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

//        String status = null;
        String patientName = null;
        boolean bPatientNameFound = false;
        String expectedLoggedInUser = EHR_Data.user_HGOptom1;
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
                m_assert.assertTrue(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab), "Validate " + MyQueueTab + " tab is selected");
                Thread.sleep(1000);

                for (WebElement patient : oPage_OPD.rows_patientAppointments) {
                    if (patient.isDisplayed()) {
                        List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                        patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");
//                        status = Cls_Generic_Methods.getTextInElement(patientDetailsOnRow.get(4));
                        if (concatPatientFullName.equals(patientName.trim())) {

                            m_assert.assertTrue(true, "Patient Name Matched in Appointment Details Section");
                            bPatientNameFound = true;
                            Cls_Generic_Methods.clickElement(driver, patient);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
                            break;
                        }
                    }
                }

                m_assert.assertTrue(bPatientNameFound, "Validate Patient  " + concatPatientFullName + " is clicked in " + MyQueueTab + " of Optometrist");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient in Optometrist module " + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Fill Optometrist Template Data")
    public void createOptometristTemplate() {

        oPages_Optometrist = new Pages_Optometrist(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oPage_RefractionTab = new Page_RefractionTab(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        opage_opd = new Page_OPD(driver);
        String OptometristTemplateOption = "Optometrist";
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String expectedLoggedInUser = EHR_Data.user_HGOptom1;
        try {

            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait(5);
            CommonActions.selectFacility("TST");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_searchCriteriaSelector, 20);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, opage_opd.button_clickNewTemplate), "New Template button clicked");
            m_assert.assertTrue(CommonActions.selectTemplateOption(opage_opd.listButtons_selectOptionsOnTemplate, OptometristTemplateOption), "Validate " + OptometristTemplateOption + " template  is selected");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_HistoryTab.tab_HistoryTab, 20);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.tab_HistoryTab), "Validate clicked on History tab");

            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_Chief_Complaints),
                    "Clicked on NIL Checkbox for Chief Complaints");
            Cls_Generic_Methods.customWait(1);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_Ophthalmic_History),
                    "Clicked on NIL Checkbox for Ophthalmic History");
            Cls_Generic_Methods.customWait(1);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_Systemic_History),
                    "Clicked on NIL Checkbox for Systemic History");
            Cls_Generic_Methods.customWait(1);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_HistoryTab.checkbox_NIL_All_Allergies),
                    "Clicked on NIL Checkbox for All Allergies");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 8);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_RefractionTab.tab_RefractionTab), "Validate clicked on Refraction tab");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.button_editRefractionButton, 8);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_RefractionTab.button_editRefractionButton), "Validate clicked on Refraction tab");
            Cls_Generic_Methods.customWait(5);
            if (Cls_Generic_Methods.isElementDisplayed(oPage_RefractionTab.tab_RedColourInVisionTab)) {
                // Validating the R/OD Visual Acuity

                try {
                    for (WebElement buttonElement : oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityRight) {

                        int index = oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityRight.indexOf(buttonElement);
                        // ***********kept it for regression****************//
//						if (Cls_Generic_Methods.getTextInElement(buttonElement)
//								.equals(oOptometrist_Data.list_UCVA_ABSENT.get(index))) {
//							buttonsCounter++;
//							m_assert.assertInfo(true, "Validate " + oOptometrist_Data.list_UCVA_ABSENT.get(index)
//									+ " is present under Visual Acuity section");
//						} else {
//							m_assert.assertInfo(false,
//									"Validate " + oOptometrist_Data.list_UCVA_ABSENT.get(index)
//											+ " is present under Nutritional Assessment section. Actual = "
//											+ Cls_Generic_Methods.getTextInElement(buttonElement));
//						}

                        boolean validateButtonFunctionality = false;
                        validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);
                        m_assert.assertInfo(validateButtonFunctionality, "Validate "
                                + oOptometrist_Data.list_UCVA_ABSENT.get(index) + " R/OD Button is Clickable");

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement), "Validate "
                                + oOptometrist_Data.list_UCVA_ABSENT.get(index) + " R/OD Button is Clicked");
                        break;
                    }
                    // ***********kept it for regression****************//
//					if ((oOptometrist_Data.list_UCVA_ABSENT.size() == buttonsCounter)) {
//						m_assert.assertTrue(true, "Validate " + buttonsCounter
//								+ " UCVA absent options are present under visual acuity section");
//					} else {
//						m_assert.assertTrue(false, "Validate " + oOptometrist_Data.list_UCVA_ABSENT.size()
//								+ " disorders are present under visual acuity section. Actual = " + buttonsCounter);
//					}

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_RefractionTab.input_ucvaCommentUnderVisualAcuityRightSide,
                                    oOptometrist_Data.sUCVA_COMMENT),
                            "Validate Visual Acuity for right eye Comment is entered as "
                                    + oOptometrist_Data.sUCVA_COMMENT);

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_RefractionTab.input_CommentUnderVisualAcuityRightSide,
                                    oOptometrist_Data.sUCVA_COMMENT),
                            "Validate R/OD Visual Acuity Comment is entered as "
                                    + oOptometrist_Data.sVISUAL_ACUITY_COMMENT);

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate R/OD Section under visual acuity - \n" + e);
                }
                // Validating the L/OS Visual Acuity
                try {
                    for (WebElement buttonElement : oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityLeft) {

                        int index = oPage_RefractionTab.buttons_ucvaAbsentUnderVisualAcuityLeft.indexOf(buttonElement);
                        // ***********kept it for regression****************//
//						if (Cls_Generic_Methods.getTextInElement(buttonElement).equals(oOptometrist_Data.list_UCVA_ABSENT.get(index))) {
//							buttonsCounter++;
//							m_assert.assertInfo(true, "Validate " + oOptometrist_Data.list_UCVA_ABSENT.get(index)
//									+ " is present under Visual Acuity section");
//						} else {
//							m_assert.assertInfo(false,
//									"Validate " + oOptometrist_Data.list_UCVA_ABSENT.get(index)
//											+ " is present under Nutritional Assessment section. Actual = "
//											+ buttonElement.getText());
//						}

                        boolean validateButtonFunctionality = CommonActions.validateIf_EHR_ButtonIsClickable(buttonElement);

                        m_assert.assertTrue(validateButtonFunctionality, "Validate "
                                + oOptometrist_Data.list_UCVA_ABSENT.get(index) + " L/OS Button is Clickable");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, buttonElement), "Validate "
                                + oOptometrist_Data.list_UCVA_ABSENT.get(index) + " L/OS Button is Clicked");
                        break;
                    }
                    // ***********kept it for regression****************//
//					if ((oOptometrist_Data.list_UCVA_ABSENT.size() == buttonsCounter)) {
//						m_assert.assertTrue(true, "Validate " + buttonsCounter
//								+ " UCVA absent options are present under visual acuity section");
//					} else {
//						m_assert.assertTrue(false, "Validate " + oOptometrist_Data.list_UCVA_ABSENT.size()
//								+ " disorders are present under visual acuity section. Actual = " + buttonsCounter);
//					}

                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_RefractionTab.input_ucvaCommentUnderVisualAcuityLeftSide,
                                    oOptometrist_Data.sUCVA_COMMENT),
                            "Validate Visual Acuity for left eye Comment is entered as "
                                    + oOptometrist_Data.sUCVA_COMMENT);
                    m_assert.assertTrue(
                            Cls_Generic_Methods.sendKeysIntoElement(
                                    oPage_RefractionTab.input_CommentUnderVisualAcuityLefttSide,
                                    oOptometrist_Data.sUCVA_COMMENT),
                            "Validate L/OD Visual Acuity Comment is entered as "
                                    + oOptometrist_Data.sVISUAL_ACUITY_COMMENT);
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to validate L/OS side absent Section under visual acuity - \n" + e);
                }
            } else {
                m_assert.assertTrue(
                        Cls_Generic_Methods.isElementDisplayed(oPage_RefractionTab.tab_GreenColourInVisioTab),
                        "vision Tab is in Green colour");
            }

            //IOPs
            try {
//                m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(opage_refractionTab.tab_RedColourInIopTab), "IOP Tab is in Red colour");

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightIop);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightIop,
                        myPatient.getsIOP_RIGHT()), "IOP Value filled for R/OD");

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftIop);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftIop,
                        myPatient.getsIOP_LEFT()), "IOP Value filled for L/OS");

                //  m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(opage_refractionTab.tab_GreenColourInIopTab),
                //      "vision Tab is in Green colour");

                if (CommonActions.templateBadgeIsGreen(oPage_RefractionTab.status_iopBadge)) {
                    m_assert.assertTrue(true, "IOP fields are filled successfully, button is GREEN");
//                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate), "Template Saved. ");
                } else if (CommonActions.templateBadgeIsRed(oPage_RefractionTab.status_iopBadge)) {
                    m_assert.assertTrue(false, "IOP fields are not filled, button is RED");
                }

            } catch (Exception e) {
                m_assert.assertTrue(false, "Unable to validate IOP Section Under Refraction \n" + e);
                e.printStackTrace();
            }

            if (bScriptDebugMode) {
                try {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.tab_HistoryTab),
                            "clicked on History tab");
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_HistoryTab.tab_RedColourInHistoryTab)) {
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_HistoryTab.button_OneChiefComplaints,
                                10);
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_OneChiefComplaints),
                                " selected one of the chief complaints");
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_HistoryTab.button_OneOphthalmicHistory),
                                " selected one of the ophthalmic history");
                        m_assert.assertTrue(
                                Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_SystemmicHistory),
                                " selected one of the systemic history");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_HistoryTab.button_Allergy),
                                " selected one of the allergy");
                    } else {
                        m_assert.assertTrue(
                                Cls_Generic_Methods.isElementDisplayed(oPage_HistoryTab.tab_GreenColourInHistoryTab),
                                "vision Tab is in Green colour");
                    }
                } catch (Exception e) {
                    m_assert.assertTrue(false, "Unable to validate History Section Under Refraction " + e);
                    e.printStackTrace();
                }
            }

            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_SaveTemplate, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate),
                        OptometristTemplateOption + " Template Saved. ");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertTrue(false, OptometristTemplateOption + " Template Saved. \n" + e);
            }

            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate),
                        OptometristTemplateOption + " Template closed.");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertTrue(false, OptometristTemplateOption + " Template closed. \n" + e);
            }

        } catch (Exception e) {
            m_assert.assertTrue(false, "<b>Optometrist Template is not selected. </b> " + e);
            e.printStackTrace();
        }

    }

    @Test(enabled = true, description = "Send Patient to Doctor")
    public void validateSendPatientFromOptometristToDoctor() {
        oPages_Optometrist = new Pages_Optometrist(driver);
        oPage_OPD = new Page_OPD(driver);
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String destinationDoctorName = "PR.Akash test";
        int requiredDoctorIndex = -1;
        boolean requiredDoctorFound = false;
        int myQueueInitialCount = -1, updatedMyQueueCount = -1;
        String tabToSelect = "All";
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        String expectedLoggedInUser = EHR_Data.user_HGOptom1;
        try {
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.customWait(5);
            CommonActions.selectFacility("TST");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_searchCriteriaSelector, 20);
            try {
                String myQueueTab = "My Queue";
                myQueueInitialCount = CommonActions.getCountOfPatientsInTab(oPage_OPD.tabs_appointmentTabsOnHomepage, myQueueTab);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_sendToDoctor, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_sendToDoctor), "Send to Doctor button clicked");

                for (WebElement doctorRow : oPage_PatientAppointmentDetails.listButtons_sendToDoctor) {
                    if (doctorRow.isDisplayed()) {

                        List<WebElement> doctorDetails = doctorRow.findElements(By.xpath("./child::*"));
                        WebElement eDoctorNameName = doctorDetails.get(1);

                        if (Cls_Generic_Methods.getTextInElement(eDoctorNameName).equals(destinationDoctorName)) {
                            requiredDoctorIndex = oPage_PatientAppointmentDetails.listButtons_sendToDoctor.indexOf(doctorRow);
                            requiredDoctorFound = true;
                            break;
                        }
                    }
                }

                m_assert.assertInfo(requiredDoctorFound, "Validate " + destinationDoctorName + " User is found.");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.listButtons_sendToDoctor.get(requiredDoctorIndex)),
                        "<b>" + destinationDoctorName + "</b> is clicked");

                Thread.sleep(2000);
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_OPD.rows_patientAppointments, 4);

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

                m_assert.assertInfo(CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, tabToSelect),
                        "Validate " + tabToSelect + " tab is selected");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient in Optometrist module " + e);
            }

        } catch (Exception e) {
            m_assert.assertFatal("<b>Not</b> able to send the patient to Doctor. \n" + e);
            e.printStackTrace();
        }
    }

}
