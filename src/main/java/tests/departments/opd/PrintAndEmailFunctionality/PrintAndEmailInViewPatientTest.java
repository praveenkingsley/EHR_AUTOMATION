package tests.departments.opd.PrintAndEmailFunctionality;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import com.mongodb.gridfs.CLI;
import data.EHR_Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.opd.Page_OPD;
import pages.store.Page_Store;

public class PrintAndEmailInViewPatientTest extends TestBase {

    static Model_Patient myPatient;

    public static String myMedicationName = "SalesOrderTest1";
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();
    boolean patientSelectedOPD = false;
    String concatPatientFullName = "";

    @Test(enabled = true, description = "Validating Print Functionality For Appointment List")
    public void validateAppointmentListPrintFunctionality() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {
                 createPatientToPrint();
                 Cls_Generic_Methods.customWait();
                 boolean printButtonCheck = CommonActions.validatePrintButtonFunctionality(oPage_Navbar.button_printAppointmentList,"Print Appointment");
                 m_assert.assertTrue(printButtonCheck,"Print Functionality Working For Navbar Appointment list");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating My Draft Bill Print Functionality In Report")
    public void validateMyDraftBillReportPrintFunctionality() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_reports),
                        " Reports Button Clicked In Nav Bar");
                Cls_Generic_Methods.customWait();
                for (WebElement eReportOption : oPage_Navbar.options_reportOptions) {
                    if(Cls_Generic_Methods.getTextInElement(eReportOption).contains("My Bills"))
                    {
                        Cls_Generic_Methods.clickElement(eReportOption);
                        Cls_Generic_Methods.customWait();
                        boolean draftBillsPrintButton = CommonActions.validatePrintButtonFunctionality(oPage_Navbar.button_draftBills,"Draft Bills");
                        m_assert.assertTrue(draftBillsPrintButton,"Print Functionality Working For Draft Bill In My Bills Report");

                    }
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

    @Test(enabled = true, description = "Validating My Final Bill Print Functionality In Report")
    public void validateMyFinalBillReportPrintFunctionality() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_reports),
                        " Reports Button Clicked In Nav Bar");
                Cls_Generic_Methods.customWait();
                for (WebElement eReportOption : oPage_Navbar.options_reportOptions) {
                    if(Cls_Generic_Methods.getTextInElement(eReportOption).contains("My Bills"))
                    {
                        Cls_Generic_Methods.clickElement(eReportOption);
                        Cls_Generic_Methods.customWait();
                        boolean draftBillsPrintButton = CommonActions.validatePrintButtonFunctionality(oPage_Navbar.button_finalBills,"Final Bills");
                        m_assert.assertTrue(draftBillsPrintButton,"Print Functionality Working For Final Bill In My Bills Report");

                    }
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

    @Test(enabled = true, description = "Validating All Draft Bill Print Functionality In Report")
    public void validateAllDraftBillReportPrintFunctionality() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_reports),
                        " Reports Button Clicked In Nav Bar");
                Cls_Generic_Methods.customWait();
                for (WebElement eReportOption : oPage_Navbar.options_reportOptions) {
                    if(Cls_Generic_Methods.getTextInElement(eReportOption).contains("All Bills"))
                    {
                        Cls_Generic_Methods.clickElement(eReportOption);
                        Cls_Generic_Methods.customWait();
                        boolean draftBillsPrintButton = CommonActions.validatePrintButtonFunctionality(oPage_Navbar.button_draftBillsInAllBill,"Draft Bills");
                        m_assert.assertTrue(draftBillsPrintButton,"Print Functionality Working For Draft Bill In All Bills Report");

                    }
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

    @Test(enabled = true, description = "Validating All Final Bill Print Functionality In Report")
    public void validateAllFinalBillReportPrintFunctionality() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_reports),
                        " Reports Button Clicked In Nav Bar");
                Cls_Generic_Methods.customWait();
                for (WebElement eReportOption : oPage_Navbar.options_reportOptions) {
                    if(Cls_Generic_Methods.getTextInElement(eReportOption).contains("All Bills"))
                    {
                        Cls_Generic_Methods.clickElement(eReportOption);
                        Cls_Generic_Methods.customWait();
                        boolean draftBillsPrintButton = CommonActions.validatePrintButtonFunctionality(oPage_Navbar.button_finalBillsInAllBill,"Final Bills");
                        m_assert.assertTrue(draftBillsPrintButton,"Print Functionality Working For Final Bill In All Bills Report");

                    }
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

    @Test(enabled = true, description = "Validating Advance Receipt Print Functionality In Report")
    public void validateAdvanceReceiptReportPrintFunctionality() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_reports),
                        " Reports Button Clicked In Nav Bar");
                Cls_Generic_Methods.customWait();
                for (WebElement eReportOption : oPage_Navbar.options_reportOptions) {
                    if(Cls_Generic_Methods.getTextInElement(eReportOption).contains("Advance Receipts"))
                    {
                        boolean PrintButton = CommonActions.validatePrintButtonFunctionality(eReportOption,"Advance Receipts");
                        m_assert.assertTrue(PrintButton,"Print Functionality Working For Advance Receipts Report");

                    }
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

    @Test(enabled = true, description = "Validating Refund Receipt Print Functionality In Report")
    public void validateRefundReceiptReportPrintFunctionality() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_reports),
                        " Reports Button Clicked In Nav Bar");
                Cls_Generic_Methods.customWait();
                for (WebElement eReportOption : oPage_Navbar.options_reportOptions) {
                    if(Cls_Generic_Methods.getTextInElement(eReportOption).contains("Refund Receipts"))
                    {
                        boolean PrintButton = CommonActions.validatePrintButtonFunctionality(eReportOption,"Refund Receipts");
                        m_assert.assertTrue(PrintButton,"Print Functionality Working For Refund Receipts Report");

                    }
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

    @Test(enabled = true, description = "Validating My Collection Print Functionality In Report")
    public void validateMyCollectionReceiptReportPrintFunctionality() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_reports),
                        " Reports Button Clicked In Nav Bar");
                Cls_Generic_Methods.customWait();
                for (WebElement eReportOption : oPage_Navbar.options_reportOptions) {
                    if(Cls_Generic_Methods.getTextInElement(eReportOption).contains("My Collection Receipts"))
                    {
                        boolean PrintButton = CommonActions.validatePrintButtonFunctionality(eReportOption,"My Collection Receipts");
                        m_assert.assertTrue(PrintButton,"Print Functionality Working For My Collection Receipts Report");

                    }
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

    @Test(enabled = true, description = "Validating All Collection Print Functionality In Report")
    public void validateAllCollectionReceiptReportPrintFunctionality() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Navbar.button_reports),
                        " Reports Button Clicked In Nav Bar");
                Cls_Generic_Methods.customWait();
                for (WebElement eReportOption : oPage_Navbar.options_reportOptions) {
                    if(Cls_Generic_Methods.getTextInElement(eReportOption).contains("All Collection Receipts"))
                    {
                        boolean PrintButton = CommonActions.validatePrintButtonFunctionality(eReportOption,"All Collection Receipts");
                        m_assert.assertTrue(PrintButton,"Print Functionality Working For All Collection Receipts Report");

                    }
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

    @Test(enabled = true, description = "Validating Print In Patient Details and Summary Section")
    public void validateViewPatientDetailsSectionAndInSummaryPrintFunctionality() {
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {

                createPatientToPrint();
                Cls_Generic_Methods.customWait();
                boolean printButtonCheck = CommonActions.validatePrintButtonFunctionality(oPage_PatientAppointmentDetails.button_printVisitSummary,"Print Appointment");
                m_assert.assertTrue(printButtonCheck,"Print Functionality Working In Patient Details Section");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.button_visitSummary),
                        " Summary Button Clicked In Patient Details Section");
                Cls_Generic_Methods.customWait();
                printButtonCheck = CommonActions.validatePrintButtonFunctionality(oPage_PatientAppointmentDetails.button_printVisitSummary,"Print Appointment");
                m_assert.assertTrue(printButtonCheck,"Print Functionality Working In Patient Summary Details Section");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validating Email and Print For Patient Referral")
    public void validateViewPatientReferralEmailAndPrintFunctionality() {
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        try {
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

            try {

                myPatient = map_PatientsInExcel.get(patientKey);
                CommonActions.selectDepartmentOnApp("OPD");
                Cls_Generic_Methods.customWait(5);
                concatPatientFullName = CommonActions.getFullPatientName(myPatient).toUpperCase().trim();
                patientSelectedOPD = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                m_assert.assertTrue(patientSelectedOPD, " Patient Created Successfully In OPD");

                if(!patientSelectedOPD) {
                    createPatientToPrint();
                }

                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.icon_emailBoxIcon),
                        " Email Button Clicked for Patient Referral");
                Cls_Generic_Methods.customWait(2);
                for(WebElement eEmailButton : oPage_PatientAppointmentDetails.list_emailReceiptsReferralList){
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(eEmailButton),
                            Cls_Generic_Methods.getTextInElement(eEmailButton)+" Selected as Patient Referral");
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.input_saveReferralMessageButton,5);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PatientAppointmentDetails.input_saveReferralMessageButton),
                            "Save Referral Button Clicked");
                    Cls_Generic_Methods.customWait(5);
                    break;

                }

                boolean printButtonCheck = CommonActions.validatePrintButtonFunctionality(oPage_PatientAppointmentDetails.button_printA4Button,"PrintA4");
                m_assert.assertTrue(printButtonCheck,"PrintA4 Functionality Working In Referral Section");
                Cls_Generic_Methods.customWait();
                printButtonCheck = CommonActions.validatePrintButtonFunctionality(oPage_PatientAppointmentDetails.button_printA5Button,"PrintA5");
                m_assert.assertTrue(printButtonCheck,"PrintA5 Functionality Working In Patient Referral Details Section");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }


    public void createPatientToPrint() {
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);



        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
            Cls_Generic_Methods.customWait();


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

                // Entering Essential Form Data
                if (!myPatient.getsSALUTATION().isEmpty()) {
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_NewPatientRegisteration.select_salutationForPatient, myPatient.getsSALUTATION()), "Salutation for Patient is selected as - " + myPatient.getsSALUTATION());
                }

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, myPatient.getsFIRST_NAME()), "First Name is entered as - " + myPatient.getsFIRST_NAME());
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm, myPatient.getsMOBILE_NUMBER()), "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());


                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Validate that Create Appointment button is clicked");

                Cls_Generic_Methods.customWait(10);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 20);

                concatPatientFullName = CommonActions.getFullPatientName(myPatient).toUpperCase().trim();
                patientSelectedOPD = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, concatPatientFullName);
                m_assert.assertTrue(patientSelectedOPD, " Patient Created Successfully In OPD");


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }
}
