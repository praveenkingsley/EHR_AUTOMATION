package tests.departments.opd;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import org.openqa.selenium.By;
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

public class AllTabTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();
    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");

    @Test(enabled = true, description = "Validating My Queue Tab")
    public void validateAllTab(){
        Page_OPD oPage_OPD = new Page_OPD(driver);

     try {

         CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

         String allTabCountInitial = Cls_Generic_Methods.getTextInElement(
                 oPage_OPD.tabs_appointmentTabsOnHomepage.get(1).findElement(By.xpath("./a/span")));
         String myOPCountInitial = Cls_Generic_Methods.getTextInElement(
                 oPage_OPD.tabs_appointmentTabsOnHomepage.get(4).findElement(By.xpath("./a/span")));
         String referralCountInitial = Cls_Generic_Methods.getTextInElement(
                 oPage_OPD.tabs_appointmentTabsOnHomepage.get(5).findElement(By.xpath("./a/span")));
            String myQueueTabCountInitial = Cls_Generic_Methods.getTextInElement(
            oPage_OPD.tabs_appointmentTabsOnHomepage.get(0).findElement(By.xpath("./a/span")));
            String patientName = "Deepak" + CommonActions.getRandomString(4);
            createPatient(patientName, "Walkin");
            String myQueueTabCountUpdate = Cls_Generic_Methods.getTextInElement(
                 oPage_OPD.tabs_appointmentTabsOnHomepage.get(0).findElement(By.xpath("./a/span")));
            String fullName = myPatient.getsSALUTATION()+" "+patientName;
            boolean patientSelectedOPD = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, fullName);
            m_assert.assertTrue(patientSelectedOPD,"Created Patient Found In My Queue");
            m_assert.assertTrue(Integer.parseInt(myQueueTabCountInitial) == (Integer.parseInt(myQueueTabCountUpdate)-1),
                   " My Queue Count Increased by one and My Queue Tab Functionality Working Correctly" );

            String notArrivedTabCountInitial = Cls_Generic_Methods.getTextInElement(
                 oPage_OPD.tabs_appointmentTabsOnHomepage.get(3).findElement(By.xpath("./a/span")));
            Cls_Generic_Methods.clickElement(oPage_OPD.button_notArrived);
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(
                 CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, "Not Arrived"),
                 "Validate " + "Not Arrived" + " tab is selected");
            Cls_Generic_Methods.customWait();
           String notArrivedTabCountUpdate = Cls_Generic_Methods.getTextInElement(
                 oPage_OPD.tabs_appointmentTabsOnHomepage.get(3).findElement(By.xpath("./a/span")));
          patientSelectedOPD = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, fullName);
         m_assert.assertTrue(patientSelectedOPD,"Created Patient Found In Not Arrived");
         m_assert.assertTrue(Integer.parseInt(notArrivedTabCountInitial) == (Integer.parseInt(notArrivedTabCountUpdate)-1),
                 " Not Arrived Count Increased by one and Not Arrived Tab Functionality Working Correctly" );


         String allOpTabCountInitial = Cls_Generic_Methods.getTextInElement(
                 oPage_OPD.tabs_appointmentTabsOnHomepage.get(3).findElement(By.xpath("./a/span")));
         Cls_Generic_Methods.clickElement(oPage_OPD.button_markAsCompleted);
         Cls_Generic_Methods.customWait();
         m_assert.assertTrue(
                 CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, "All OP"),
                 "Validate " + "All OP" + " tab is selected");
         Cls_Generic_Methods.customWait();
         String allOpTabCountUpdate = Cls_Generic_Methods.getTextInElement(
                 oPage_OPD.tabs_appointmentTabsOnHomepage.get(2).findElement(By.xpath("./a/span")));
         patientSelectedOPD = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, fullName);
         m_assert.assertTrue(patientSelectedOPD,"Created Patient Found In All Op");
         m_assert.assertTrue(Integer.parseInt(allOpTabCountInitial) == (Integer.parseInt(allOpTabCountUpdate)-1),
                 " All Op Count Increased by one and All Op Tab Functionality Working Correctly" );

         CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);
         String patientName2 = "Deepak" + CommonActions.getRandomString(4);
         String fullName2 = myPatient.getsSALUTATION()+" "+patientName2;

         createPatient(patientName2,"Appointment");
         CommonActions.loginFunctionality(oEHR_Data.user_PRAkashTest);

         m_assert.assertTrue(
                 CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, "Referrals"),
                 "Validate " + "Referrals" + " tab is selected");
         Cls_Generic_Methods.customWait();
         String referralTabCountUpdate = Cls_Generic_Methods.getTextInElement(
                 oPage_OPD.tabs_appointmentTabsOnHomepage.get(5).findElement(By.xpath("./a/span")));
         patientSelectedOPD = CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, fullName2);
         m_assert.assertTrue(patientSelectedOPD,"Created Patient Found In Referrals");
         m_assert.assertTrue(Integer.parseInt(referralCountInitial) == (Integer.parseInt(referralTabCountUpdate)-1),
                 " Referrals Count Increased by one and Referrals Tab Functionality Working Correctly" );

         String allTabCountUpdate = Cls_Generic_Methods.getTextInElement(
                 oPage_OPD.tabs_appointmentTabsOnHomepage.get(1).findElement(By.xpath("./a/span")));
         String myOPCountUpdate = Cls_Generic_Methods.getTextInElement(
                 oPage_OPD.tabs_appointmentTabsOnHomepage.get(4).findElement(By.xpath("./a/span")));

         m_assert.assertTrue(Integer.parseInt(allTabCountInitial) < (Integer.parseInt(allTabCountUpdate)-1),
                 " All Tab Count Increased by one and Referrals Tab Functionality Working Correctly" );
         m_assert.assertTrue(Integer.parseInt(myOPCountInitial) < (Integer.parseInt(myOPCountUpdate)-1),
                 " My OP Count Increased by one and Referrals Tab Functionality Working Correctly" );







     }catch (Exception e){
             e.printStackTrace();
     }


    }


    public void createPatient(String patientName,String type) {
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
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

                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_firstNameOnPatientRegForm, patientName),
                        "First Name is entered as - " + patientName);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_mobileNumberOnPatientRegForm,
                        myPatient.getsMOBILE_NUMBER()), "Mobile Number is entered as - " + myPatient.getsMOBILE_NUMBER());

                if(type.equalsIgnoreCase("Appointment")) {
                    boolean timeSlotStatus = true;
                    try {
                        oPage_NewPatientRegisteration.table_timeSlotPatientRegForm.isDisplayed();
                    } catch (NoSuchElementException e) {
                        timeSlotStatus = false;
                    }

                    int requiredIndex = 0;

                    // Appointment Type
                    requiredIndex = -1;
                    if (!timeSlotStatus) {
                        try {
                            for (WebElement e : oPage_NewPatientRegisteration.radioButtons_appointmentTypeOnPatientRegForm) {

                                if (e.getAttribute("value")
                                        .equals(oEHR_Data.sAPPOINTMENT_TYPE.toLowerCase().replace("-", ""))) {
                                    requiredIndex = oPage_NewPatientRegisteration.radioButtons_appointmentTypeOnPatientRegForm
                                            .indexOf(e);
                                    oPage_NewPatientRegisteration.radioButtonsSelector_appointmentTypeOnPatientRegForm
                                            .get(requiredIndex).click();
                                    m_assert.assertTrue(true,
                                            "Validate " + oEHR_Data.sAPPOINTMENT_TYPE + " is selected for Appointment Type");
                                    break;
                                }

                            }

                            if (requiredIndex == -1) {
                                m_assert.assertTrue(false, oEHR_Data.sAPPOINTMENT_TYPE + " Appointment type is not selected");
                            } else {
                                m_assert.assertTrue(CommonActions.validateOnlyOneRadioButtonIsSelected(
                                                oPage_NewPatientRegisteration.radioButtonsSelector_appointmentTypeOnPatientRegForm),
                                        "Validate only one Appointment Type is selected");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            if (requiredIndex == -1) {
                                m_assert.assertFatal("Unable to select Appointment Type - " + e);
                            }
                        }
                    }


                    try {
                        Cls_Generic_Methods.customWait(2);
                        boolean bConsultantSelected = false;
                        int consultantIndex = -1;
                        Cls_Generic_Methods.clickElement(driver,
                                oPage_NewPatientRegisteration.selectButton_consultantForAppointmentOnPatientRegForm);
                        //      oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
                        for (WebElement e : oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm) {
                            if (e.getText().trim().contains(oEHR_Data.sAPPOINTMENT_CONSULTANT)) {
                                bConsultantSelected = true;
                                consultantIndex = oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm
                                        .indexOf(e);
                                break;
                            }
                        }

                        if (bConsultantSelected) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver,
                                            oPage_NewPatientRegisteration.selectButtonOptions_searchResultsForSelectorsOnPatientRegForm
                                                    .get(consultantIndex)),
                                    oEHR_Data.sAPPOINTMENT_CONSULTANT + " for Consultant is selected");
                        } else {
                            m_assert.assertTrue(false, oEHR_Data.sAPPOINTMENT_CONSULTANT + " is not found");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_assert.assertTrue(false, "Consultant Selection for Facility failed." + e);
                    }
                }


                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm), "Validate that Create Appointment button is clicked");

                Cls_Generic_Methods.customWait(10);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 20);


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
