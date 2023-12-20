package tests.departments.opd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.Page_RefractionTab;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.settings.facilitySettings.general.facilitySetup.Page_FacilitySetup;

import java.text.DecimalFormat;
import java.text.ParseException;

public class lasikTest extends TestBase {
    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");

    public static String sPatientAge = "";
    public static int iPatientAge = 22;

    @Test(enabled = true, description = "Create patient for lasik eligibility")
    public void createPatientForLasik() {

        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        boolean bPatientNameFound = false;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);

            try {

                // Open the Search/Add patient dialog box
                try {
                    if (!oPage_NewPatientRegisteration.modalHeader_PatientRegForm.isDisplayed()) {
                        CommonActions.openPatientRegisterationAndAppointmentForm();
                    } else {
                        CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_NewPatientRegisteration.tabs_PatientRegForm, "Patient Details");
                        Cls_Generic_Methods.customWait();
                    }
                } catch (NoSuchElementException e1) {
                    CommonActions.openPatientRegisterationAndAppointmentForm();
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.select_salutationForPatient, 12);
                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()), "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()),
                        "First Name is entered as - " + myPatient.getsFIRST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_middleNameOnPatientRegForm, myPatient.getsMIDDLE_NAME()),
                        "Middle Name is entered as - " + myPatient.getsMIDDLE_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_lastNameOnPatientRegForm, myPatient.getsLAST_NAME()),
                        "Last Name is entered as - " + myPatient.getsLAST_NAME());

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()),
                        "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.input_patientDob),
                        "Dob calendar clicked");
                String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_NewPatientRegisteration.select_dobYear);

                int year = Integer.parseInt(currentYear);
                int newYear = year - iPatientAge;

                Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_dobYear, Integer.toString(newYear));
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.select_dobDateDay),
                        "Date selected for DOB as date: <b>" + Cls_Generic_Methods.getTextInElement(oPage_NewPatientRegisteration.select_dobDateDay) +
                                " Year: " + newYear + " </b>");

                sPatientAge = Cls_Generic_Methods.getElementAttribute(oPage_NewPatientRegisteration.text_patientAge, "value");
                m_assert.assertInfo("Age is: " + sPatientAge);

                if (oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm.isDisplayed()) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Validate that Create Appointment button is clicked");

                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10), "Patient details displayed ");
                }

                String concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                //select patient
                bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

                if (bPatientNameFound) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
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

    @Test(enabled = true, description = "Create template with mandatory fields")
    public void createEmptyTemplateForLasik() {

        Page_OPD oPage_OPD = new Page_OPD(driver);
        String PostOpTemplate = "Post OP";

        boolean bPatientNameFound = false;

        Page_FacilitySetup oPage_FacilitySetup = new Page_FacilitySetup(driver);
        Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String concatPatientFullName = "";

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

            if (bPatientNameFound) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate), "New Template Details button is clicked");
                m_assert.assertTrue(CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, PostOpTemplate), "Validate " + PostOpTemplate + " template  is selected");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 20);

                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilitySetup.checkbox_rightVision),
                        "VA not examined for right clicked ");
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FacilitySetup.checkbox_leftVision),
                        "VA not examined for left clicked ");

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightIop);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightIop, myPatient.getsIOP_RIGHT()), "Right Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftIop);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftIop, myPatient.getsIOP_LEFT()), "Left Eye intraocularPressure is " + myPatient.getsIOP_RIGHT());

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate),
                        PostOpTemplate + " Template Saved. ");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate),
                        PostOpTemplate + " Template closed.");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 5);
            }


        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Enter PGP data")
    public void enterPgpDataForLasik() {

        Page_OPD oPage_OPD = new Page_OPD(driver);
        String PostOpTemplate = "Post OP";
        String sRPgpSph = "8.3";
        String sRPgpCyl = "";
        String sLPgpSph = "1.2";
        String sLPgpCyl = "8.7";

        boolean bPatientNameFound = false;

        Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String concatPatientFullName = "";

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);


            if (bPatientNameFound) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_openTemplate), "Open template button is clicked");

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.text_headerOPDSummary, 10),
                        "Templated Opened, Text Visible: <b> " + oPage_OPD.text_headerOPDSummary.getText() + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_enabledEditInOpdSummary),
                        "Edit template button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 20);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_RefractionTab.button_editPgpData), "Edit PGP data button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.input_rightPgpDistantCyl, 10);

                //Fill pgp sph and cyl data
                Cls_Generic_Methods.scrollToElementByAction(driver, oPage_RefractionTab.input_rightPgpDistantCyl);
                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightPgpDistantSph);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightPgpDistantSph, sRPgpSph), "PGP Right Distant Sph entered = <b>" + sRPgpSph + "</b> ");

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightPgpDistantCyl);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightPgpDistantCyl, sRPgpCyl), "PGP Right Distant Cyl entered = <b>" + sRPgpCyl + "</b> ");

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftPgpDistantSph);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftPgpDistantSph, sLPgpSph), "PGP Left Distant Sph entered = <b>" + sLPgpSph + "</b> ");

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftPgpDistantCyl);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftPgpDistantCyl, sLPgpCyl), "PGP Left Distant Cyl entered = <b>" + sLPgpCyl + "</b> ");
                Cls_Generic_Methods.customWait(3);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_updateTemplate),
                        PostOpTemplate + " Template Updated. ");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate),
                        PostOpTemplate + " Template closed.");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);

            }

        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Enter glass data")
    public void enterGlassDataForLasik() {

        Page_OPD oPage_OPD = new Page_OPD(driver);
        String PostOpTemplate = "Post OP";

        String sRGlassSph = "+0.3";
        String sRGlassCyl = "-0.4";
        String sLGlassSph = "-0.23";
        String sLGlassCyl = "+0.4";
        boolean bPatientNameFound = false;

        Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String concatPatientFullName = "";

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

            if (bPatientNameFound) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_openTemplate), "Open template button is clicked");

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.text_headerOPDSummary, 5),
                        "Templated Opened, Text Visible: <b> " + oPage_OPD.text_headerOPDSummary.getText() + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_enabledEditInOpdSummary),
                        "Edit template button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 20);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_RefractionTab.button_editGlassPrescriptionData), "Edit PGP data button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.input_leftGlassDistantCyl, 3);

                //Fill glass prescription sph and cyl data
                Cls_Generic_Methods.scrollToElementByAction(driver, oPage_RefractionTab.input_rightGlassDistantSph);
                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightGlassDistantSph);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightGlassDistantSph, sRGlassSph), "Glass Right Distant Sph entered = <b>" + sRGlassSph + "</b> ");

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightGlassDistantCyl);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightGlassDistantCyl, sRGlassCyl), "Glass Right Distant Cyl entered = <b>" + sRGlassCyl + "</b> ");

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftGlassDistantSph);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftGlassDistantSph, sLGlassSph), "Glass Left Distant Sph entered = <b>" + sLGlassSph + "</b> ");

                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftGlassDistantCyl);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftGlassDistantCyl, sLGlassCyl), "Glass Left Distant Cyl entered = <b>" + sLGlassCyl + "</b> ");
                Cls_Generic_Methods.customWait(3);

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_updateTemplate),
                        PostOpTemplate + " Template Updated. ");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate),
                        PostOpTemplate + " Template closed.");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10);


            }

        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate lasik eligibility")
    public void validateLasikEligibility() {
        /**
         gets age of user and L (and/or) R eye Glass/PGP readings from ui (PGP = Previous Glass Prescription)
         decides lasik eligibility on below points:

         * If Glass Prescription available, ignore PGP readings.
         *
         * if age <18 --> NOT ELIGIBLE

         * if age >=18 and right eye glass/PGP sum or left eye glass/PGP sum <= 8 --> ELIGIBLE
         * if age >=18 and right eye glass/PGP sum or left eye glass/PGP sum > 8 --> NOT ELIGIBLE
         * if age >=18 and both glass/PGP section present then eligibility decided on glass prescription readings
         */

        Page_OPD oPage_OPD = new Page_OPD(driver);
        String PostOpTemplate = "Post OP";
        String sRGlassSph = "";
        String sRGlassCyl = "";
        String sLGlassSph = "";
        String sLGlassCyl = "";
        String sRPgpSph = "";
        String sRPgpCyl = "";
        String sLPgpSph = "";
        String sLPgpCyl = "";

        double rGlassSum = -1;
        double lGlassSum = -1;
        double rPgpSum = -1;
        double lPgpSum = -1;

        boolean eligibilityStatus = false;
        boolean rightGlassStatus = false;
        boolean leftGlassStatus = false;
        boolean rightPGPStatus = false;
        boolean leftPGPStatus = false;

        boolean pgpSectionFound = false;
        boolean glassSectionFound = false;

        boolean bPatientNameFound = false;
        boolean notEligibleStatus = false;

        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String concatPatientFullName = "";

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();
            bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

            if (bPatientNameFound) {
                DecimalFormat decimalFormat = new DecimalFormat("0.00");

                String age = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_patientAgeInOPD);
                String intValueAge = age.replaceAll("[^0-9]", "");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_openTemplate, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_openTemplate), "Open template button is clicked");

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.text_headerOPDSummary, 10),
                        "Templated Opened, Text Visible: <b> " + oPage_OPD.text_headerOPDSummary.getText() + "</b>");


                if (Cls_Generic_Methods.isElementDisplayed(oPage_OPD.header_glassPrescription)) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.header_glassPrescription, 5);

                    glassSectionFound = true;
                    m_assert.assertInfo("Glass section status present");

                    //Glass reading validation
                    sRGlassSph = Cls_Generic_Methods.getTextInElement(oPage_OPD.list_glassSph.get(0));
                    sLGlassSph = Cls_Generic_Methods.getTextInElement(oPage_OPD.list_glassSph.get(1));
                    sRGlassCyl = Cls_Generic_Methods.getTextInElement(oPage_OPD.list_glassCyl.get(0));
                    sLGlassCyl = Cls_Generic_Methods.getTextInElement(oPage_OPD.list_glassCyl.get(1));

                    if (sRGlassSph.equals("--") && sRGlassCyl.equals("--") && sLGlassSph.equals("--") && sLGlassCyl.equals("--")) {
                        m_assert.assertFatal("Not lasik eligible");
                    }
                    if (sRGlassSph.equals("--")) {
                        sRGlassSph = "0";
                    }
                    if (sLGlassSph.equals("--")) {
                        sLGlassSph = "0";
                    }
                    if (sRGlassCyl.equals("--")) {
                        sRGlassCyl = "0";
                    }
                    if (sLGlassCyl.equals("--")) {
                        sLGlassCyl = "0";
                    }

                    rGlassSum = Math.abs(Double.parseDouble(sRGlassSph)) + Math.abs(Double.parseDouble(sRGlassCyl));
                    lGlassSum = Math.abs(Double.parseDouble(sLGlassSph)) + Math.abs(Double.parseDouble(sLGlassCyl));

                    // If Any one is Eligible, then patient is Eligible
                    if (rGlassSum <= 8) {
                        rightGlassStatus = calcGlassEligibility(intValueAge, rGlassSum);
                    } else if (lGlassSum <= 8) {
                        leftGlassStatus = calcGlassEligibility(intValueAge, lGlassSum);
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate),
                            PostOpTemplate + " Template closed.");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.text_lasikStatus, 10);
                    String status = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_lasikStatus);

                    if (rGlassSum > 8 && lGlassSum > 8) {
                        if (status.equalsIgnoreCase("Not Eligible")) {
                            notEligibleStatus = true;
                            m_assert.assertTrue(notEligibleStatus, "<b> Lasik text displayed on UI: " + status +
                                    " as age: " + intValueAge + " R glass sum: " + decimalFormat.format(rGlassSum) + " L glass sum: " + decimalFormat.format(lGlassSum) + " </b> ");

                        }
                    }

                    if ((rightGlassStatus || leftGlassStatus)) {
                        if (status.equalsIgnoreCase("Eligible")) {
                            eligibilityStatus = true;
                            m_assert.assertTrue(eligibilityStatus, "<b> Lasik text displayed on UI: " + status + "</b> ");
                        }
                    }

                } else if (Cls_Generic_Methods.isElementDisplayed(oPage_OPD.header_pgp)) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.header_pgp, 5);

                    pgpSectionFound = true;
                    m_assert.assertInfo(pgpSectionFound, "Pgp section status present. ");

                    sRPgpSph = Cls_Generic_Methods.getTextInElement(oPage_OPD.list_PgpSph.get(0));
                    sLPgpSph = Cls_Generic_Methods.getTextInElement(oPage_OPD.list_PgpSph.get(1));
                    sRPgpCyl = Cls_Generic_Methods.getTextInElement(oPage_OPD.list_PgpCyl.get(0));
                    sLPgpCyl = Cls_Generic_Methods.getTextInElement(oPage_OPD.list_PgpCyl.get(1));

                    Cls_Generic_Methods.customWait(3);
                    if (sRPgpSph.equals("--") && sRPgpCyl.equals("--") && sLPgpSph.equals("--") && sLPgpCyl.equals("--")) {
                        m_assert.assertFatal("Not lasik eligible");
                    }
                    if (sRPgpSph.equals("--")) {
                        sRPgpSph = "0";
                    }
                    if (sLPgpSph.equals("--")) {
                        sLPgpSph = "0";
                    }
                    if (sRPgpCyl.equals("--")) {
                        sRPgpCyl = "0";
                    }
                    if (sLPgpCyl.equals("--")) {
                        sLPgpCyl = "0";
                    }

                    rPgpSum = Math.abs(Double.parseDouble(sRPgpSph)) + Math.abs(Double.parseDouble(sRPgpCyl));
                    lPgpSum = Math.abs(Double.parseDouble(sLPgpSph)) + Math.abs(Double.parseDouble(sLPgpCyl));

                    // If Any one is Eligible, then patient is Eligible
                    if (rPgpSum <= 8) {
                        rightPGPStatus = calcPGPEligibility(intValueAge, rPgpSum);
                    } else if (lPgpSum <= 8) {
                        leftPGPStatus = calcPGPEligibility(intValueAge, lPgpSum);
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate),
                            PostOpTemplate + " Template closed.");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.text_lasikStatus, 10);
                    String status = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_lasikStatus);

                    if (rPgpSum > 8 && lPgpSum > 8) {
                        if (status.equalsIgnoreCase("Not Eligible")) {
                            notEligibleStatus = true;
                            m_assert.assertTrue(notEligibleStatus, "<b> Lasik text displayed on UI: " + status +
                                    " as age: " + intValueAge + " R pgp sum: " + decimalFormat.format(rPgpSum) + " L pgp sum: " + decimalFormat.format(lPgpSum) + " </b> ");

                        }
                    }
                    if (rightPGPStatus || leftPGPStatus) {
                        if (status.equalsIgnoreCase("Eligible")) {
                            eligibilityStatus = true;
                            m_assert.assertTrue(eligibilityStatus, "<b> Lasik text displayed on UI: " + status + "</b> ");

                        }
                    }
                } else {
                    m_assert.assertTrue(false, "Both Glass Prescription & PGP Sections <b>NOT</b> found.");
                }

            }

        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate minor patient")
    public void validateMinorPatientForLasik() {

        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        String PostOpTemplate = "Post OP";
        boolean eligibilityStatus = false;
        boolean bPatientNameFound = false;
        String concatPatientFullName = "";


        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            concatPatientFullName = CommonActions.getFullPatientName(myPatient);
            concatPatientFullName = concatPatientFullName.toUpperCase().trim();

            bPatientNameFound = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);

            if (bPatientNameFound) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_editPatientInfo),
                        "Edit patient details button is clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.input_patientDob, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.input_patientDob),
                        "Dob calendar clicked");
                String currentYear = Cls_Generic_Methods.getSelectedValue(oPage_NewPatientRegisteration.select_dobYear);
                int year = Integer.parseInt(currentYear);
                int newYear = year + 19;

                Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_dobYear, Integer.toString(newYear));
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.select_dobDateDay),
                        "Date selected for DOB date: <b>" + Cls_Generic_Methods.getTextInElement(oPage_NewPatientRegisteration.select_dobDateDay) +
                                "Year: " + newYear + " </b>");

                sPatientAge = Cls_Generic_Methods.getElementAttribute(oPage_NewPatientRegisteration.text_patientAge, "value");
                m_assert.assertInfo("Age is: " + sPatientAge);

                if (oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm.isDisplayed()) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Validate that Create Appointment button is clicked");

                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 10), "Patient details displayed ");
                }
                oPage_OPD = new Page_OPD(driver);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_openTemplate), "Open template button is clicked");

                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.text_headerOPDSummary, 5),
                        "Templated Opened, Text Visible: <b> " + oPage_OPD.text_headerOPDSummary.getText() + "</b>");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_enabledEditInOpdSummary),
                        "Edit template button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_updateTemplate),
                        PostOpTemplate + " Template Updated. ");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 20);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate),
                        PostOpTemplate + " Template closed.");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.text_lasikStatus, 10);
                String status = Cls_Generic_Methods.getTextInElement(oPage_PatientAppointmentDetails.text_lasikStatus);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_markPatientAsCompleted),
                        "Mark As Completed Clicked ");
                if (status.equalsIgnoreCase("Not Eligible")) {
                    eligibilityStatus = true;
                }
                m_assert.assertTrue(eligibilityStatus, "<b> Lasik text displayed on UI: " + status + "</b> ");
            }

        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
    }

    private boolean calcGlassEligibility(String age, double sum) {
        boolean bGlassEligible = false;

        try {

            if ((Integer.parseInt(age) >= 18) && (sum <= 8)) {
                bGlassEligible = true;
            }
            m_assert.assertTrue(bGlassEligible, "<b> Eligible for lasik as age : " + age + " and glass sum is: " + sum + "</b>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }
        return bGlassEligible;
    }

    private boolean calcPGPEligibility(String age, double pgpSum) {
        boolean bPGPEligible = false;
        try {
            if ((Integer.parseInt(age) >= 18) && (pgpSum <= 8)) {
                bPGPEligible = true;
            }
            m_assert.assertTrue(bPGPEligible, "<b> Eligible for lasik as age : " + age + " and pgp sum is: " + pgpSum + "</b>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal(" " + e);
        }
        return bPGPEligible;
    }

}
