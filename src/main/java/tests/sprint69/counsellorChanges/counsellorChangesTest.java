package tests.sprint69.counsellorChanges;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.opd.Page_OPD;
import pages.sprint69.counsellorChanges.Page_CounsellorChanges;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedTransferQueue;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import static pages.commonElements.CommonActions.oEHR_Data;

public class counsellorChangesTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    String concatPatientFullName = "";

    List<String> advisedProceduresList = new ArrayList<String>();
    List<String> advisedOphthalmologyList = new ArrayList<String>();
    List<String> advisedLaboratoryList = new ArrayList<String>();
    List<String> advisedRadiologyList = new ArrayList<String>();
    List<String> currentStatusList = new ArrayList<String>();

    List<String> followedProceduresList = new ArrayList<String>();
    List<String> followedProceduresStatusList = new ArrayList<String>();
    String counsellorOutcomeOptions[] = {"No Action Taken","Declined","Agreed","Payment Taken"};
    String followupActualHeaderList[] = {"Procedure - Order","Advised by","Current Status","Existing Upcoming Followups"};

    String expectedFollowDateInPatientUIString = "";


    @Test(enabled = true, description = "Validate Create Counselling Record Functionality")
    public void validateCreateCounsellingRecord(){

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_CounsellorChanges oPage_CounsellorChanges = new Page_CounsellorChanges(driver);

        boolean bPatientNameFound = false;
        try{
            CommonActions.loginFunctionality(EHR_Data.user_HGCounsellor);

            try{

                // Searching Patient in My Queue

                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                Cls_Generic_Methods.customWait();
                bPatientNameFound = selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);

                if(bPatientNameFound){

                    // Opening New Counselling Record Template
                    m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_CounsellorChanges.button_counsellingDropdownButton),
                            "Counselling Dropdown button is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CounsellorChanges.button_counsellingDropdownButton),
                            "Clicked on Counselling Dropdown button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.button_newCounsellingRecordButton,2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_newCounsellingRecordButton),
                            " New Counselling Record Button Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.header_counsellingRecordTemplateHeader,5);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.header_counsellingRecordTemplateHeader),
                            " New Counselling Record Template Opened");
                    String counselledByUI = Cls_Generic_Methods.getSelectedValue(oPage_CounsellorChanges.select_orderCounselledBy);
                    m_assert.assertTrue(counselledByUI.equalsIgnoreCase(EHR_Data.user_HGCounsellor),
                            "Counselled By is Displaying correctly");
                    String procedureTabStatus = Cls_Generic_Methods.getElementAttribute(
                                                    oPage_CounsellorChanges.tab_proceduresTabInTemplate,"class");
                    String investigationTabStatus = Cls_Generic_Methods.getElementAttribute(
                            oPage_CounsellorChanges.tab_investigationsTabInTemplate,"class");
                    m_assert.assertTrue(procedureTabStatus.equalsIgnoreCase("active") && investigationTabStatus.equalsIgnoreCase(""),
                            "Procedure Tab And Investigation Tab is Displayed and Procedure Tab is Selected By Default");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.input_searchProcedureInputBox),
                            " Procedure search box is displayed");

                    // Creating New Record , Starting Counselling With Advised Procedures

                    int indexOfProcedure = -1 ;
                    for(WebElement procedures : oPage_CounsellorChanges.list_advisedProceduresList){

                        indexOfProcedure = oPage_CounsellorChanges.list_advisedProceduresList.indexOf(procedures);
                        String defaultCounsellingOutcome = Cls_Generic_Methods.getSelectedValue(oPage_CounsellorChanges.list_counsellingOutcomeBoxList.get(indexOfProcedure));
                        m_assert.assertTrue(defaultCounsellingOutcome.equalsIgnoreCase("No Action Taken"),
                                " By Default For Advised Procedure "+Cls_Generic_Methods.getTextInElement(procedures)+
                                        " Counselling outcome displaying correctly as :"+defaultCounsellingOutcome);
                        String currentStatus = Cls_Generic_Methods.getTextInElement(
                                oPage_CounsellorChanges.list_currentStatusList.get(indexOfProcedure)).replaceAll("[\r\n]+", " ");
                        currentStatusList.add(currentStatus);
                        m_assert.assertTrue(currentStatus.contains("Advised - "+EHR_Data.user_PRAkashTest),
                                " Current Status for procedure "+ Cls_Generic_Methods.getTextInElement(procedures) +
                                " is displaying correctly "+currentStatus);

                    }

                    for(WebElement procedures : oPage_CounsellorChanges.list_advisedProceduresList){

                        indexOfProcedure = oPage_CounsellorChanges.list_advisedProceduresList.indexOf(procedures);
                        advisedProceduresList.add(Cls_Generic_Methods.getTextInElement(procedures));
                        if(counsellorOutcomeOptions.length > indexOfProcedure) {

                            String procedureName = advisedProceduresList.get(indexOfProcedure);
                            Cls_Generic_Methods.selectElementByVisibleText(oPage_CounsellorChanges.list_counsellingOutcomeBoxList.get(indexOfProcedure)
                                    ,counsellorOutcomeOptions[indexOfProcedure]);
                            m_assert.assertInfo("Selecting Counselling Outcome for procedure name : " + procedureName +
                                            " as :" + counsellorOutcomeOptions[indexOfProcedure]);

                            Cls_Generic_Methods.sendKeysIntoElement(oPage_CounsellorChanges.list_patientCommentBoxList.get(indexOfProcedure),
                                    "Patient Procedure Comment"+indexOfProcedure);
                            Cls_Generic_Methods.sendKeysIntoElement(oPage_CounsellorChanges.list_counsellorCommentBoxList.get(indexOfProcedure),
                                    "Counsellor Procedure Comment"+indexOfProcedure);

                        }
                    }

                    // Move To Investigation Tab , Started with ophthalmology Investigation

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.tab_investigationsTabInTemplate),
                            "Investigation Tab Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.tab_ophthalmologyInvestigationTab,3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.tab_ophthalmologyInvestigationTab),
                            " Ophthalmology Investigation Tab Clicked in Counselling Template");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.tab_ophthalmologyInvestigationTab,2);

                    int indexOfOphthalmology = -1 ;
                    for(WebElement ophthalmology : oPage_CounsellorChanges.list_advisedOphthalmologyInvestigationList){

                        indexOfOphthalmology = oPage_CounsellorChanges.list_advisedOphthalmologyInvestigationList.indexOf(ophthalmology);
                        String defaultCounsellingOutcome = Cls_Generic_Methods.getSelectedValue(
                                oPage_CounsellorChanges.list_ophthalmologyCounsellingOutcomeBoxList.get(indexOfOphthalmology));
                        m_assert.assertTrue(defaultCounsellingOutcome.equalsIgnoreCase("No Action Taken"),
                                " By Default For Advised Ophthalmology Investigation "+Cls_Generic_Methods.getTextInElement(ophthalmology)+
                                        " Counselling outcome displaying correctly as :"+defaultCounsellingOutcome);
                        String currentStatus = Cls_Generic_Methods.getTextInElement(
                                oPage_CounsellorChanges.list_ophthalmologyCurrentStatusList.get(indexOfOphthalmology));
                        currentStatusList.add(currentStatus);
                        m_assert.assertTrue(currentStatus.contains("Advised - "+EHR_Data.user_PRAkashTest),
                                " Current Status for Ophthalmology Investigation "+ Cls_Generic_Methods.getTextInElement(ophthalmology) +
                                        " is displaying correctly "+currentStatus);

                    }

                    for(WebElement ophthalmology : oPage_CounsellorChanges.list_advisedOphthalmologyInvestigationList){

                        indexOfOphthalmology = oPage_CounsellorChanges.list_advisedOphthalmologyInvestigationList.indexOf(ophthalmology);
                        advisedOphthalmologyList.add(Cls_Generic_Methods.getTextInElement(ophthalmology));
                        if(counsellorOutcomeOptions.length > indexOfOphthalmology) {

                            String ophthalmologyName = advisedOphthalmologyList.get(indexOfOphthalmology);
                            Cls_Generic_Methods.selectElementByVisibleText(oPage_CounsellorChanges.list_ophthalmologyCounsellingOutcomeBoxList.get(indexOfOphthalmology)
                                    ,counsellorOutcomeOptions[indexOfOphthalmology]);
                            m_assert.assertInfo("Selecting Counselling Outcome for Ophthalmology name : " + ophthalmologyName +
                                    " as :" + counsellorOutcomeOptions[indexOfOphthalmology]);

                            Cls_Generic_Methods.sendKeysIntoElement(oPage_CounsellorChanges.list_ophthalmologyPatientCommentBoxList.get(indexOfOphthalmology),
                                    "Patient Ophthalmology Comment"+indexOfOphthalmology);
                            Cls_Generic_Methods.sendKeysIntoElement(oPage_CounsellorChanges.list_ophthalmologyCounsellorCommentBoxList.get(indexOfOphthalmology),
                                    "Counsellor Ophthalmology Comment"+indexOfOphthalmology);

                        }
                    }

                    // Counselling Laboratory investigations

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.tab_laboratoryInvestigationTab,3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.tab_laboratoryInvestigationTab),
                            "Laboratory Tab Clicked In Counselling Investigation");
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_CounsellorChanges.list_advisedLaboratoryInvestigationList,2);

                    int indexOfLaboratory = -1 ;
                    for(WebElement laboratory : oPage_CounsellorChanges.list_advisedLaboratoryInvestigationList){

                        indexOfLaboratory = oPage_CounsellorChanges.list_advisedLaboratoryInvestigationList.indexOf(laboratory);
                        String defaultCounsellingOutcome = Cls_Generic_Methods.getSelectedValue(
                                oPage_CounsellorChanges.list_laboratoryCounsellingOutcomeBoxList.get(indexOfLaboratory));
                        m_assert.assertTrue(defaultCounsellingOutcome.equalsIgnoreCase("No Action Taken"),
                                " By Default For Advised Laboratory Investigation "+Cls_Generic_Methods.getTextInElement(laboratory)+
                                        " Counselling outcome displaying correctly as :"+defaultCounsellingOutcome);
                        String currentStatus = Cls_Generic_Methods.getTextInElement(
                                oPage_CounsellorChanges.list_laboratoryCurrentStatusList.get(indexOfLaboratory));
                        currentStatusList.add(currentStatus);
                        m_assert.assertTrue(currentStatus.contains("Advised - "+EHR_Data.user_PRAkashTest),
                                " Current Status for Laboratory Investigation "+ Cls_Generic_Methods.getTextInElement(laboratory) +
                                        " is displaying correctly "+currentStatus);

                    }

                    for(WebElement laboratory : oPage_CounsellorChanges.list_advisedLaboratoryInvestigationList){

                        indexOfLaboratory = oPage_CounsellorChanges.list_advisedLaboratoryInvestigationList.indexOf(laboratory);
                        advisedLaboratoryList.add(Cls_Generic_Methods.getTextInElement(laboratory));
                        if(counsellorOutcomeOptions.length > indexOfLaboratory) {

                            String laboratoryName = advisedLaboratoryList.get(indexOfLaboratory);
                            Cls_Generic_Methods.selectElementByVisibleText(oPage_CounsellorChanges.list_laboratoryCounsellingOutcomeBoxList.get(indexOfLaboratory)
                                    ,counsellorOutcomeOptions[indexOfLaboratory]);
                            m_assert.assertInfo("Selecting Counselling Outcome for Laboratory name : " + laboratoryName +
                                    " as :" + counsellorOutcomeOptions[indexOfLaboratory]);

                            Cls_Generic_Methods.sendKeysIntoElement(oPage_CounsellorChanges.list_laboratoryPatientCommentBoxList.get(indexOfLaboratory),
                                    " Patient Laboratory Comment : "+indexOfLaboratory);
                            Cls_Generic_Methods.sendKeysIntoElement(oPage_CounsellorChanges.list_laboratoryCounsellorCommentBoxList.get(indexOfLaboratory),
                                    "Counsellor Laboratory Comment : "+indexOfLaboratory);

                        }
                    }


                    // Counselling Radiology Investigation

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.tab_radiologyInvestigationTab,3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.tab_radiologyInvestigationTab),
                            "Radiology Tab Clicked in Counselling Investigation ");
                    Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_CounsellorChanges.list_advisedRadiologyInvestigationList,2);

                    int indexOfRadiology = -1 ;
                    for(WebElement radiology : oPage_CounsellorChanges.list_advisedRadiologyInvestigationList){

                        indexOfRadiology = oPage_CounsellorChanges.list_advisedRadiologyInvestigationList.indexOf(radiology);
                        String defaultCounsellingOutcome = Cls_Generic_Methods.getSelectedValue(
                                oPage_CounsellorChanges.list_radiologyCounsellingOutcomeBoxList.get(indexOfRadiology));
                        m_assert.assertTrue(defaultCounsellingOutcome.equalsIgnoreCase("No Action Taken"),
                                " By Default For Advised Radiology Investigation "+Cls_Generic_Methods.getTextInElement(radiology)+
                                        " Counselling outcome displaying correctly as : "+defaultCounsellingOutcome);
                        String currentStatus = Cls_Generic_Methods.getTextInElement(
                                oPage_CounsellorChanges.list_radiologyCurrentStatusList.get(indexOfRadiology));
                        currentStatusList.add(currentStatus);
                        m_assert.assertTrue(currentStatus.contains("Advised - "+EHR_Data.user_PRAkashTest),
                                " Current Status for Radiology Investigation "+ Cls_Generic_Methods.getTextInElement(radiology) +
                                        " is displaying correctly "+currentStatus);

                    }

                    for(WebElement radiology : oPage_CounsellorChanges.list_advisedRadiologyInvestigationList){

                        indexOfRadiology = oPage_CounsellorChanges.list_advisedRadiologyInvestigationList.indexOf(radiology);
                        advisedRadiologyList.add(Cls_Generic_Methods.getTextInElement(radiology));
                        if(counsellorOutcomeOptions.length > indexOfRadiology) {

                            String radiologyName = advisedRadiologyList.get(indexOfRadiology);
                            Cls_Generic_Methods.selectElementByVisibleText(oPage_CounsellorChanges.list_radiologyCounsellingOutcomeBoxList.get(indexOfRadiology)
                                    ,counsellorOutcomeOptions[indexOfRadiology]);
                            m_assert.assertInfo("Selecting Counselling Outcome for Radiology name : " + radiologyName +
                                    " as : " + counsellorOutcomeOptions[indexOfRadiology]);

                            Cls_Generic_Methods.sendKeysIntoElement(oPage_CounsellorChanges.list_radiologyPatientCommentBoxList.get(indexOfRadiology),
                                    "Patient Radiology Comment "+indexOfRadiology);
                            Cls_Generic_Methods.sendKeysIntoElement(oPage_CounsellorChanges.list_radiologyCounsellorCommentBoxList.get(indexOfRadiology),
                                    "Counsellor Radiology Comment "+indexOfRadiology);

                        }
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.input_saveCounsellingRecord),
                            " Save Button Clicked In Counselling Records");
                    Cls_Generic_Methods.customWait(10);

                    // Verifying Creating Counselling Record

                    Cls_Generic_Methods.scrollToElementByJS(oPage_CounsellorChanges.button_counsellingDropdownButton);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,oPage_CounsellorChanges.button_counsellingDropdownButton),
                            "Counselling Button Clicked");
                    Cls_Generic_Methods.customWait(5);
                    m_assert.assertInfo("Counselling Record Created and Displayed In Counselling Dropdown correctly as : "+
                                    Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.list_counsellingDropdownTodayRecords));

                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,oPage_CounsellorChanges.list_counsellingDropdownTodayRecords),
                            "Counselling Created Record Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.header_counsellingRecordTemplateHeader,10);

                    for(WebElement procedures : oPage_CounsellorChanges.list_advisedProceduresList){

                        indexOfProcedure = oPage_CounsellorChanges.list_advisedProceduresList.indexOf(procedures);
                        String procedureName = advisedProceduresList.get(indexOfProcedure);

                        if(procedureName.equalsIgnoreCase(advisedProceduresList.get(indexOfProcedure))) {

                            String currentCounsellingOutcome = Cls_Generic_Methods.getSelectedValue(oPage_CounsellorChanges.list_counsellingOutcomeBoxList.get(indexOfProcedure));

                            m_assert.assertTrue(currentCounsellingOutcome.equalsIgnoreCase(counsellorOutcomeOptions[indexOfProcedure]),
                                    " Counsellor Outcome displaying correctly as " + counsellorOutcomeOptions[indexOfProcedure]);


                        }else{
                                if(indexOfProcedure > counsellorOutcomeOptions.length){
                                    m_assert.assertInfo(" Procedures is more than 4  , its not counselled");
                                    break;
                                }
                                else{
                                    m_assert.assertTrue(false,"Counselled Status is not updated or saved");
                                }
                        }
                    }


                }
                else{
                    m_assert.assertTrue(bPatientNameFound,"Patient Not Found In Mu Queue List , please create new");
                }

                Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_closeCounsellingRecordTemplate);
                Cls_Generic_Methods.customWait();


            }catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Application not loaded in the browser" + e);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }
    }

    @Test(enabled = true, description = "Validate Edit Counselling Record Functionality")
    public void validateEditCounsellingRecord(){

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_CounsellorChanges oPage_CounsellorChanges = new Page_CounsellorChanges(driver);

        boolean bPatientNameFound = false;
        boolean bEditCounsellingRecordFound = false;

        try{
            CommonActions.loginFunctionality(EHR_Data.user_HGCounsellor);

            try{

                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                Cls_Generic_Methods.customWait();
                bPatientNameFound = selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);

                if(bPatientNameFound){

                    // Opening Existing Counselling Record
                    m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_CounsellorChanges.button_counsellingDropdownButton),
                            "Counselling Dropdown button is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CounsellorChanges.button_counsellingDropdownButton),
                            "Clicked on Counselling Dropdown button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.list_counsellingDropdownTodayRecords,2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.list_counsellingDropdownTodayRecords),
                            "Today Created Counselling Record Clicked In Dropdown");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.header_counsellingRecordTemplateHeader,5);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.header_counsellingRecordTemplateHeader),
                            " Existing Counselling Record Template Opened ");


                    // Editing Counselling Record with new updates

                    String procedureName = advisedProceduresList.get(0);
                    Cls_Generic_Methods.selectElementByVisibleText(oPage_CounsellorChanges.list_counsellingOutcomeBoxList.get(0)
                                    ,counsellorOutcomeOptions[2]);
                    m_assert.assertInfo("Editing Counselling Outcome for procedure name : " + procedureName +
                                    " as :" + counsellorOutcomeOptions[2]);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_CounsellorChanges.list_patientCommentBoxList.get(0),
                                    "Edit Patient Procedure Comment ");
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_CounsellorChanges.list_counsellorCommentBoxList.get(0),
                                    "Edit Counsellor Procedure Comment ");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.input_saveCounsellingRecord),
                            "Save Counselling Button Clicked");
                    Cls_Generic_Methods.customWait(10);

                    // Verifying Edited counselling Records

                    Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_counsellingDropdownButton);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.list_counsellingDropdownTodayRecords,2);
                    Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.list_counsellingDropdownTodayRecords);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.header_counsellingRecordTemplateHeader,10);

                    int indexOfProcedure = -1 ;
                    for(WebElement procedures : oPage_CounsellorChanges.list_advisedProceduresList){

                        indexOfProcedure = oPage_CounsellorChanges.list_advisedProceduresList.indexOf(procedures);
                        String procedureNameText =  Cls_Generic_Methods.getTextInElement(
                                oPage_CounsellorChanges.list_advisedProceduresList.get(indexOfProcedure));

                        if(procedureNameText.equalsIgnoreCase(procedureName)) {

                            String currentCounsellingOutcome = Cls_Generic_Methods.getSelectedValue(
                                    oPage_CounsellorChanges.list_counsellingOutcomeBoxList.get(indexOfProcedure));

                            m_assert.assertTrue(currentCounsellingOutcome.equalsIgnoreCase(counsellorOutcomeOptions[2]),
                                    " Counsellor Outcome displaying correctly as " + counsellorOutcomeOptions[2]);
                            bEditCounsellingRecordFound = true;
                            break ;

                        }

                    }

                    m_assert.assertTrue(bEditCounsellingRecordFound, " Counselling Records Updated Successfully");

                }else{
                    m_assert.assertTrue(bPatientNameFound, "Patient not found in My queue list , please create new");
                }

                Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_closeCounsellingRecordTemplate);
                Cls_Generic_Methods.customWait();


            }catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Application not loaded in the browser" + e);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }
    }

    @Test(enabled = true, description = "Validate Create FollowUp Record Functionality")
    public void validateCreateFollowUpRecordFunctionality(){

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_CounsellorChanges oPage_CounsellorChanges = new Page_CounsellorChanges(driver);


        boolean bPatientNameFound = false;
        boolean bPatientNameFoundInFollowup = false;


        try{
            CommonActions.loginFunctionality(EHR_Data.user_HGCounsellor);

            try{

                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                Cls_Generic_Methods.customWait();
                bPatientNameFound = selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);

                Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_appointmentForwardDateButton);
                Cls_Generic_Methods.customWait();
                int totalFollowUpBeforeCreate = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.button_followUpButtonInAppointmentPatientList));

                Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_appointmentTodayButton);
                Cls_Generic_Methods.customWait();

                if(bPatientNameFound){

                    // Creating New Followup

                    m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_CounsellorChanges.button_followupDropdownButton),
                            "Followup Dropdown button is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CounsellorChanges.button_followupDropdownButton),
                            "Clicked on Followup Dropdown button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.button_newCounsellingFollowupButton,2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_newCounsellingFollowupButton),
                            "New Followup Counselling Record Button Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.header_counsellingFollowupTemplateHeader,5);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.header_counsellingFollowupTemplateHeader),
                            " New Followup Record Template Opened and header displayed as :"+
                            Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.header_counsellingFollowupTemplateHeader));

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.text_counselledByInFollowUp),
                            "Counselled By Text Displayed In Templated");
                    m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_CounsellorChanges.select_followupCounselledBy).equalsIgnoreCase(EHR_Data.user_HGCounsellor),
                            " Counselled by displayed correctly in create followup as : "+EHR_Data.user_HGCounsellor);

                    int indexOfFollowupHeader  = -1;
                    boolean headerPresent = false ;


                    for(WebElement followupHeader : oPage_CounsellorChanges.list_followupProcedureTableHeaderList){

                        indexOfFollowupHeader = oPage_CounsellorChanges.list_followupProcedureTableHeaderList.indexOf(followupHeader);
                        if(Cls_Generic_Methods.getTextInElement(followupHeader).equalsIgnoreCase(followupActualHeaderList[indexOfFollowupHeader])){
                            headerPresent = true ;
                            m_assert.assertTrue(headerPresent," Header Present In Follow up procedure table as :"+
                                    Cls_Generic_Methods.getTextInElement(followupHeader));
                        }else{
                            m_assert.assertTrue(headerPresent," Header Not Present In Follow up procedure table as : "+
                                    Cls_Generic_Methods.getTextInElement(followupHeader));

                        }

                    }

                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.text_totalOrderCountText),
                            "No Order text displayed");
                    String noOrderSelectedText = Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.text_totalOrderCountText);
                    m_assert.assertTrue(noOrderSelectedText.equalsIgnoreCase("No Orders Selected."),
                            " No order Selected text present when no order selected");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.input_saveFollowupButton),
                            "Save Button Clicked In Followup");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.text_followupErrorMessage),
                            "Procedure Required Error Message Displayed in follow up as : "+
                            Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.text_followupErrorMessage));
                    Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.text_followupErrorMessage);
                    Cls_Generic_Methods.customWait(1);

                    int indexOfProcedure = -1 ;

                    for(WebElement procedureRow : oPage_CounsellorChanges.list_followupProceduresList){

                        indexOfProcedure = oPage_CounsellorChanges.list_followupProceduresList.indexOf(procedureRow);
                        if(Cls_Generic_Methods.isElementDisplayed(procedureRow) && indexOfProcedure > 0){

                            List<WebElement> procedureDetailsOnRow = procedureRow.findElements(By.xpath("./child::*"));

                            WebElement procedureCheckbox = procedureDetailsOnRow.get(0).findElement(By.xpath("./input"));
                            WebElement procedureNameElement = procedureDetailsOnRow.get(1).findElement(By.xpath("./strong"));

                            String procedureNameInFollowup = Cls_Generic_Methods.getTextInElement(procedureNameElement);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(procedureCheckbox),
                                    "Procedure selection check box present and clicked in followup");
                            m_assert.assertTrue(advisedProceduresList.get(indexOfProcedure-1).contains(procedureNameInFollowup),
                                    "Procedure Name present in followup are correctly ");
                            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(procedureDetailsOnRow.get(2)).contains(EHR_Data.user_PRAkashTest),
                                    " Advised By displayed correctly as : "+Cls_Generic_Methods.getTextInElement(procedureDetailsOnRow.get(2)));

                            followedProceduresList.add(procedureNameInFollowup+" "+Cls_Generic_Methods.getTextInElement(procedureDetailsOnRow.get(2)));
                            followedProceduresStatusList.add(Cls_Generic_Methods.getTextInElement(procedureDetailsOnRow.get(3)));

                           if(indexOfProcedure == 1 || indexOfProcedure == 3) {
                               m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(procedureDetailsOnRow.get(3)).equalsIgnoreCase(counsellorOutcomeOptions[2]),
                                       " Current Status of Procedure " + procedureNameInFollowup + " displayed correctly as : " + counsellorOutcomeOptions[2]);
                           }
                           else{
                               m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(procedureDetailsOnRow.get(3)).equalsIgnoreCase(counsellorOutcomeOptions[indexOfProcedure-1]),
                                       " Current Status of Procedure " + procedureNameInFollowup + " displayed correctly as : " + counsellorOutcomeOptions[indexOfProcedure-1]);
                           }
                            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(procedureDetailsOnRow.get(4)).equalsIgnoreCase(""),
                                    "Existing Followup displayed correctly ");
                            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.text_totalOrderCountText).
                                    equalsIgnoreCase("Orders Selected: " + indexOfProcedure + " Procedures"),  "Orders Selected: " + indexOfProcedure + " Procedures");

                        }
                        else{
                            if(indexOfProcedure != 0)
                                        break;
                        }

                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.input_saveFollowupButton),
                            "Save Follow Up Button Clicked");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.text_followupErrorMessage),
                            "Date Required Error Message Displayed in follow up as : "+
                                    Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.text_followupErrorMessage));

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.input_followupOrderDate),
                            "Clicked on Followup Date Input Box");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.date_nextDateToCurrentDate,2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,oPage_CounsellorChanges.date_nextDateToCurrentDate),
                            "Next Date Selected as Follow Up Date");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.input_followupTypeInPerson),
                            "Follow Up Type Selected as In Person");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.select_followupCounsellor,1);
                    m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_CounsellorChanges.select_followupCounsellor).
                                    equalsIgnoreCase(EHR_Data.user_HGCounsellor), " Counsellor Displayed as current user as :"+EHR_Data.user_HGCounsellor);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.input_saveFollowupButton),
                            "Save Follow Up Button clicked");
                    Cls_Generic_Methods.customWait(10);

                    // Verifying Newly Created Followup  In Followup Dropdown, Patient Details Section and In Followup Tab

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CounsellorChanges.button_followupDropdownButton),
                            "Clicked on Followup  Dropdown button");
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.list_followupDropdownTodayFirstRecords),
                            " Newly Created Followup Displayed In Followup Button Dropdown List correctly");
                    String followupCreateAt = Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.list_followupDropdownTodayFirstRecords);
                    Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.text_followupDetailsTextInPatientRHS);
                    Cls_Generic_Methods.customWait(1);
                    expectedFollowDateInPatientUIString =  followupCreateAt.substring(0,2)+" "+getMonthStringForNumber(Integer.parseInt(followupCreateAt.substring(4,5)))+","
                                                              +followupCreateAt.substring(8,10)+" "+"at"+""+followupCreateAt.substring(10,18);

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.text_followupDetailsTextInPatientRHS).contains(expectedFollowDateInPatientUIString),
                            " Follow Up details displayed on patient rhs ui side correctly as : "+expectedFollowDateInPatientUIString);

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_appointmentForwardDateButton),
                            "Next date Button Clicked In appointment list");
                    Cls_Generic_Methods.customWait();
                    int totalFollowUpAfterCreate = Integer.parseInt(Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.button_followUpButtonInAppointmentPatientList));

                   m_assert.assertTrue(totalFollowUpAfterCreate == (totalFollowUpBeforeCreate+1)," Follow Up present in followup tab");

                   m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_followUpButtonInAppointmentPatientList),
                           "Follow Up Tab Clicked In Patient List LHS");
                    Cls_Generic_Methods.customWait();

                    bPatientNameFoundInFollowup = selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);
                    m_assert.assertTrue(bPatientNameFoundInFollowup ," Follow Up created successfully and Verified In Follow Up Tab");
                    Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_appointmentTodayButton);
                    Cls_Generic_Methods.customWait();


                }
                else {
                    m_assert.assertTrue(bPatientNameFound, "patient not found please create new or something is wrong");
                }



            }catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Application not loaded in the browser" + e);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }
    }

    @Test(enabled = true, description = "Validate Edit FollowUp Record Functionality")
    public void validateEditFollowUpRecordFunctionality(){

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_CounsellorChanges oPage_CounsellorChanges = new Page_CounsellorChanges(driver);


        boolean bPatientNameFound = false;
        try{
            CommonActions.loginFunctionality(EHR_Data.user_HGCounsellor);

            try{

                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                Cls_Generic_Methods.customWait();
                bPatientNameFound = selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);

                if(bPatientNameFound){

                    m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_CounsellorChanges.button_followupDropdownButton),
                            "Followup Dropdown button is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CounsellorChanges.button_followupDropdownButton),
                            "Clicked on Followup Dropdown button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.button_newCounsellingFollowupButton,2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.list_followupDropdownTodayFirstRecords),
                            " Previously Created Follow Up Record Clicked In Dropdown");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.header_counsellingFollowupTemplateHeader,5);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.header_counsellingFollowupTemplateHeader),
                            " Edit Followup Record Template Opened ");

                    int totalProcedure = oPage_CounsellorChanges.list_followupProceduresList.size()-1 ;
                    int  indexOfProcedure = -1 ;

                    for(WebElement procedureRow : oPage_CounsellorChanges.list_followupProceduresList){

                        indexOfProcedure = oPage_CounsellorChanges.list_followupProceduresList.indexOf(procedureRow);
                        if(Cls_Generic_Methods.isElementDisplayed(procedureRow) && indexOfProcedure > 0){

                            List<WebElement> procedureDetailsOnRow = procedureRow.findElements(By.xpath("./child::*"));

                            WebElement procedureCheckbox = procedureDetailsOnRow.get(0).findElement(By.xpath("./input"));
                            WebElement procedureName = procedureDetailsOnRow.get(1).findElement(By.xpath("./strong"));

                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(procedureCheckbox),
                                    "Procedure selection check box present and clicked in followup");

                            m_assert.assertTrue(advisedProceduresList.get(indexOfProcedure-1).contains(Cls_Generic_Methods.getTextInElement(procedureName)),
                                    "Procedure Name present in followup are correctly ");

                            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(procedureDetailsOnRow.get(4)),
                                    " Expected Follow Up date display correctly");
                            break;


                        }


                    }

                    System.out.println(Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.text_totalOrderCountText));
                    System.out.println("Orders Selected: "+(totalProcedure-1)+" Procedures");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.text_totalOrderCountText).
                            equalsIgnoreCase("Orders Selected: "+(totalProcedure-1)+" Procedures")," No order Selected text present when no order selected");

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.input_updateFollowupButton),
                            " Update Followup Button Clicked");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.button_followupDropdownButton,5);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CounsellorChanges.button_followupDropdownButton),
                            "Clicked on Followup Dropdown button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.button_newCounsellingFollowupButton,2);
                    Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.list_followupDropdownTodayFirstRecords);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.header_counsellingFollowupTemplateHeader,5);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.header_counsellingFollowupTemplateHeader),
                            " Edit Followup Record Template Opened ");

                    m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_CounsellorChanges.text_totalOrderCountText).
                            equalsIgnoreCase("Orders Selected: "+(totalProcedure-1)+" Procedures")," No order Selected text present when no order selected");


                    Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_closeCounsellingRecordTemplate);
                    Cls_Generic_Methods.customWait();

                }
                else{
                    m_assert.assertTrue(bPatientNameFound, "Patient Not found");
                }



            }catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Application not loaded in the browser" + e);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }
    }

    @Test(enabled = true, description = "Validate All Order Record Functionality")
    public void validateAllOrderFunctionality(){

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_CounsellorChanges oPage_CounsellorChanges = new Page_CounsellorChanges(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        List<String> counselledOphthalmologyList = new ArrayList<String>();
        List<String> counselledOphthalmologyStatusList = new ArrayList<String>();
        List<String> counselledLaboratoryList = new ArrayList<String>();
        List<String> counselledLaboratoryStatusList = new ArrayList<String>();

        List<String> counselledRadiologyList = new ArrayList<String>();
        List<String> counselledRadiologyStatusList = new ArrayList<String>();


        boolean bPatientNameFound = false;
        try{
            CommonActions.loginFunctionality(EHR_Data.user_HGCounsellor);

            try{

                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                Cls_Generic_Methods.customWait();
                bPatientNameFound = selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);

                if(bPatientNameFound){

                    m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_CounsellorChanges.button_counsellingDropdownButton),
                            "Counselling Dropdown button is displayed");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_CounsellorChanges.button_counsellingDropdownButton),
                            "Clicked on Counselling Dropdown button");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.list_counsellingDropdownTodayRecords,2);
                    Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.list_counsellingDropdownTodayRecords);
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.header_counsellingRecordTemplateHeader,5);
                    m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_CounsellorChanges.header_counsellingRecordTemplateHeader),
                            " Counselling Record Template Opened");

                    int indexOfProcedure = -1 ;
                    int indexOfOphthalmology = -1 ;
                    int indexOfLaboratory = -1 ;
                    int indexOfRadiology = -1 ;


                    Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.tab_investigationsTabInTemplate);
                    Cls_Generic_Methods.customWait();

                    for(WebElement ophthalmology : oPage_CounsellorChanges.list_advisedOphthalmologyInvestigationList){

                        indexOfOphthalmology = oPage_CounsellorChanges.list_advisedOphthalmologyInvestigationList.indexOf(ophthalmology);
                        counselledOphthalmologyList.add(Cls_Generic_Methods.getTextInElement(ophthalmology));
                        String currentStatus = Cls_Generic_Methods.getTextInElement(
                                oPage_CounsellorChanges.list_ophthalmologyCurrentStatusList.get(indexOfOphthalmology)).replaceAll("[\r\n]+", " ");
                        counselledOphthalmologyStatusList.add(currentStatus);

                    }

                    Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.tab_laboratoryInvestigationTab);
                    Cls_Generic_Methods.customWait();

                    for(WebElement laboratory : oPage_CounsellorChanges.list_advisedLaboratoryInvestigationList){

                        indexOfLaboratory = oPage_CounsellorChanges.list_advisedLaboratoryInvestigationList.indexOf(laboratory);
                        counselledLaboratoryList.add(Cls_Generic_Methods.getTextInElement(laboratory));

                        String currentStatus = Cls_Generic_Methods.getTextInElement(
                                oPage_CounsellorChanges.list_laboratoryCurrentStatusList.get(indexOfLaboratory)).replaceAll("[\r\n]+", " ");
                        counselledLaboratoryStatusList.add(currentStatus);
                    }

                    Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.tab_radiologyInvestigationTab);
                    Cls_Generic_Methods.customWait();

                    for(WebElement radiology : oPage_CounsellorChanges.list_advisedRadiologyInvestigationList){

                        indexOfRadiology = oPage_CounsellorChanges.list_advisedRadiologyInvestigationList.indexOf(radiology);
                        counselledRadiologyList.add(Cls_Generic_Methods.getTextInElement(radiology));

                        String currentStatus = Cls_Generic_Methods.getTextInElement(
                                oPage_CounsellorChanges.list_radiologyCurrentStatusList.get(indexOfRadiology)).replaceAll("[\r\n]+", " ");
                        counselledRadiologyStatusList.add(currentStatus);

                    }
                     Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_closeCounsellingRecordTemplate);
                     Cls_Generic_Methods.customWait();

                     m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_allOrderButton),
                             "All Order Button Clicked");
                     Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CounsellorChanges.header_allOrderTemplateHeader,2);

                     boolean procedureAndStatusFound = false ;
                     for(WebElement procedures : oPage_CounsellorChanges.list_allOrderProceduresOrderList){

                        indexOfProcedure = oPage_CounsellorChanges.list_allOrderProceduresOrderList.indexOf(procedures);
                        String allOrderProcedureName = Cls_Generic_Methods.getTextInElement(procedures).replaceAll("[\r\n]+", " ");

                        String allOrderProcedureStatus = Cls_Generic_Methods.getTextInElement(
                                                             oPage_CounsellorChanges.list_allOrderProceduresStatusList.get(indexOfProcedure));


                        String counselledProcedureName = followedProceduresList.get(indexOfProcedure);
                        String counselledProcedureStatus = followedProceduresStatusList.get(indexOfProcedure);

                         if(allOrderProcedureStatus.equalsIgnoreCase(counselledProcedureStatus) &&
                                 counselledProcedureName.contains(allOrderProcedureName)) {

                            procedureAndStatusFound = true ;
                            m_assert.assertTrue("Procedure name : "+allOrderProcedureName+" and Its Status :"+allOrderProcedureStatus+"  matched in all order");
                        }
                        else{
                            m_assert.assertTrue(procedureAndStatusFound,
                                    "Procedure name "+allOrderProcedureName+" and Its Status "+allOrderProcedureStatus+" Not matched");
                        }
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.tab_investigationsTabInTemplate),
                            " Investigation Tab Clicked");
                    Cls_Generic_Methods.customWait(2);

                    boolean ophthalmologyAndStatusFound = false ;
                    for(WebElement ophthalmology : oPage_CounsellorChanges.list_allOrderOphthalmologyOrderList){

                        indexOfOphthalmology = oPage_CounsellorChanges.list_allOrderOphthalmologyOrderList.indexOf(ophthalmology);

                        String allOrderOphthalmologyOrder[] = Cls_Generic_Methods.getTextInElement(ophthalmology).split("\\r?\\n|\\r");
                        String allOrderOphthalmologyName = allOrderOphthalmologyOrder[0];

                        String allOrderOphthalmologyStatus = Cls_Generic_Methods.getTextInElement(
                                oPage_CounsellorChanges.list_allOrderOphthalmologyStatusList.get(indexOfOphthalmology));

                        String counselledOphthalmologyStatus = counselledOphthalmologyStatusList.get(indexOfOphthalmology).substring(0,7);
                        
                        if(allOrderOphthalmologyStatus.equalsIgnoreCase(counselledOphthalmologyStatus) &&
                                counselledOphthalmologyList.get(indexOfOphthalmology).contains(allOrderOphthalmologyName)) {

                            ophthalmologyAndStatusFound = true ;
                            m_assert.assertTrue("Ophthalmology Investigation name "+allOrderOphthalmologyName+" and Its Status "+allOrderOphthalmologyStatus+"  matched in all order");

                        }
                        else{
                            m_assert.assertTrue(ophthalmologyAndStatusFound,
                                    "Ophthalmology Investigation name "+allOrderOphthalmologyName+" and Its Status "+allOrderOphthalmologyStatus+" Not matched");
                        }
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.tab_laboratoryInvestigationTab),
                            "Laboratory investigation Tab Clicked");
                    Cls_Generic_Methods.customWait();

                    boolean laboratoryAndStatusFound = false ;
                    for(WebElement laboratory : oPage_CounsellorChanges.list_allOrderLaboratoryOrderList){

                        indexOfLaboratory = oPage_CounsellorChanges.list_allOrderLaboratoryOrderList.indexOf(laboratory);

                        String allOrderLaboratoryOrder[] = Cls_Generic_Methods.getTextInElement(laboratory).split("\\r?\\n|\\r");
                        String allOrderLaboratoryName = allOrderLaboratoryOrder[0];

                        String allOrderLaboratoryStatus = Cls_Generic_Methods.getTextInElement(
                                oPage_CounsellorChanges.list_allOrderLaboratoryStatusList.get(indexOfLaboratory));

                        String counselledLaboratoryStatus = counselledLaboratoryStatusList.get(indexOfLaboratory).substring(0,7);

                        if(allOrderLaboratoryStatus.equalsIgnoreCase(counselledLaboratoryStatus) &&
                                counselledLaboratoryList.get(indexOfLaboratory).contains(allOrderLaboratoryName)) {

                            laboratoryAndStatusFound = true ;
                            m_assert.assertTrue("Laboratory name "+allOrderLaboratoryName+" and Its Status "+allOrderLaboratoryStatus+"  matched in all order");

                        }
                        else{
                            m_assert.assertTrue(laboratoryAndStatusFound,
                                    "Laboratory name "+allOrderLaboratoryName+" and Its Status "+allOrderLaboratoryStatus+" Not matched");
                        }
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.tab_radiologyInvestigationTab),
                            "Radiology Investigation tab Clicked");
                    Cls_Generic_Methods.customWait();

                    boolean radiologyAndStatusFound = false ;
                    for(WebElement radiology : oPage_CounsellorChanges.list_allOrderRadiologyOrderList){

                        indexOfRadiology = oPage_CounsellorChanges.list_allOrderRadiologyOrderList.indexOf(radiology);
                        String allOrderRadiologyOrder[] = Cls_Generic_Methods.getTextInElement(radiology).split("\\r?\\n|\\r");
                        String allOrderRadiologyName = allOrderRadiologyOrder[0];

                        String allOrderRadiologyStatus = Cls_Generic_Methods.getTextInElement(
                                oPage_CounsellorChanges.list_allOrderRadiologyStatusList.get(indexOfRadiology));

                        String counselledRadiologyStatus = counselledRadiologyStatusList.get(indexOfRadiology).substring(0,7);

                        if(allOrderRadiologyStatus.equalsIgnoreCase(counselledRadiologyStatus) &&
                                counselledRadiologyList.get(indexOfRadiology).contains(allOrderRadiologyName)) {

                            radiologyAndStatusFound = true ;
                            m_assert.assertTrue("Radiology name "+allOrderRadiologyName+" and Its Status "+allOrderRadiologyStatus+"  matched in all order");

                        }
                        else{
                            m_assert.assertTrue(radiologyAndStatusFound,
                                    "Radiology name "+allOrderRadiologyName+" and Its Status "+allOrderRadiologyStatus+" Not matched");
                        }
                    }

                    Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
                    Cls_Generic_Methods.customWait();


                }
                else{
                    m_assert.assertTrue(bPatientNameFound,"Patient Not found in my queue");
                }





            }catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Application not loaded in the browser" + e);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }
    }

    @Test(enabled = true, description = "Validate Add Patient Functionality")
    public void validateAddAppointmentFunctionality(){

        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_CounsellorChanges oPage_CounsellorChanges = new Page_CounsellorChanges(driver);
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);

        boolean bPatientNameFound = false;
        boolean bPatientNameInDoctor = false;

        try{
            CommonActions.loginFunctionality(EHR_Data.user_HGCounsellor);

            try{

                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                Cls_Generic_Methods.customWait();
                bPatientNameFound = selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);

                if(bPatientNameFound){

                     Cls_Generic_Methods.scrollToElementByJS(oPage_CounsellorChanges.button_plusAppointmentButtonInPatientRHS);
                     Cls_Generic_Methods.clickElement(oPage_CounsellorChanges.button_plusAppointmentButtonInPatientRHS);
                     Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_addAppointment,5);
                     Cls_Generic_Methods.clickElement(oPage_Navbar.button_addAppointment);
                     Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm,15);

                    // Appointment Type
                    int requiredIndex = -1;
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

                    // Appointment Date & Time
                    try {
                        // Date
                        Cls_Generic_Methods.clickElement(driver,
                                oPage_NewPatientRegisteration.inputButton_appointmentDateForAppointDetails);

                        String[] separatedDateValue = CommonActions.formatInputToRequiredDate(Cls_Generic_Methods.getTodayDate()).split(":");
                        String dateOnCalendar = separatedDateValue[0];
                        String monthOnCalendar = separatedDateValue[1];
                        String yearOnCalendar = separatedDateValue[2];

                        // Separate out month and year handling

                        if (!oPage_NewPatientRegisteration.text_yearOnCalendarForAppointDetails.getText().trim()
                                .equals(yearOnCalendar)) {
                            int forwardYearsCounter = CommonActions.getDifferenceInYearsForFutureDate(oEHR_Data.sAPPOINTMENT_DATE);
                            forwardYearsCounter = forwardYearsCounter * 12;

                            for (int i = 0; i < forwardYearsCounter; i++) {
                                Thread.sleep(100);
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_NewPatientRegisteration.button_nextMonthForAppointmentOnPatientRegForm);
                                m_assert.assertInfo("Clicked on Next Month button in Calendar for Appointment Creation - "
                                        + oPage_NewPatientRegisteration.text_monthOnCalendarForAppointDetails.getText()
                                        + " "
                                        + oPage_NewPatientRegisteration.text_yearOnCalendarForAppointDetails.getText());
                            }

                        }

                        if (!oPage_NewPatientRegisteration.text_monthOnCalendarForAppointDetails.getText().trim()
                                .equals(monthOnCalendar)) {
                            int forwardMonthsCounter = CommonActions.getDifferenceInMonthsForFutureDate(oEHR_Data.sAPPOINTMENT_DATE);

                            for (int i = 0; i < forwardMonthsCounter; i++) {
                                Thread.sleep(100);
                                Cls_Generic_Methods.clickElement(driver,
                                        oPage_NewPatientRegisteration.button_nextMonthForAppointmentOnPatientRegForm);
                                m_assert.assertInfo("Clicked on Next Month button in Calendar for Appointment Creation - "
                                        + oPage_NewPatientRegisteration.text_monthOnCalendarForAppointDetails.getText()
                                        + " "
                                        + oPage_NewPatientRegisteration.text_yearOnCalendarForAppointDetails.getText());
                            }

                        }

                        if (oPage_NewPatientRegisteration.text_monthOnCalendarForAppointDetails.getText().trim()
                                .equals(monthOnCalendar)
                                && oPage_NewPatientRegisteration.text_yearOnCalendarForAppointDetails.getText().trim()
                                .equals(yearOnCalendar)) {

                            for (WebElement eDate : oPage_NewPatientRegisteration.td_datesOnCalendarOnPatientRegForm) {
                                if (eDate.getText().toString().trim().equals(dateOnCalendar)) {
                                    Cls_Generic_Methods.clickElement(driver, eDate);
                                    m_assert.assertTrue(true,
                                            "Entered Appointment Date as - " + oEHR_Data.sAPPOINTMENT_DATE);
                                    break;
                                }
                            }
                        } else {
                            m_assert.assertTrue(false, "Expected Month and Year = " + dateOnCalendar + "/" + monthOnCalendar
                                    + "/" + yearOnCalendar + "\t<br>" + "Actual Month and Year = "
                                    + oPage_NewPatientRegisteration.text_monthOnCalendarForAppointDetails.getText() + "/"
                                    + oPage_NewPatientRegisteration.text_yearOnCalendarForAppointDetails.getText());
                        }


                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        m_assert.assertTrue(false, "Error occurred while entering Appointment Date - " + e1);
                    }

                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm),
                            "Validate that Create Appointment button is clicked");

                    Cls_Generic_Methods.customWait(10);

                    CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
                    String notArrived = "Not Arrived";
                    concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                    concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                    m_assert.assertTrue(
                            CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, notArrived),
                            "Validate " + notArrived + " tab is selected");
                    Cls_Generic_Methods.customWait();
                    bPatientNameInDoctor = selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);

                    m_assert.assertTrue(bPatientNameInDoctor, " New Appointment Found In All Tab");


                }
                else {
                    m_assert.assertTrue(bPatientNameFound, "patient not found please create new or something is wrong");
                }



            }catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Application not loaded in the browser" + e);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }
    }

    @Test(enabled = true, description = "Validate schedule admission for patient")
    public void scheduleAdmissionFromOPD() throws Exception {

        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_ScheduleAdmission oPage_ScheduleAdmission = new Page_ScheduleAdmission(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);

        String concatPatientFullName = "";
        boolean bPatientNameFound = false;
        try {

            //Currently, patient is in OPD

            CommonActions.loginFunctionality(EHR_Data.user_HGCounsellor);
            try{
                String MyQueueTab = "My Queue";
                concatPatientFullName = CommonActions.getFullPatientName(myPatient);
                concatPatientFullName = concatPatientFullName.toUpperCase().trim();
                m_assert.assertTrue(
                        CommonActions.selectTabOnDepartmentPage(oPage_OPD.tabs_appointmentTabsOnHomepage, MyQueueTab),
                        "Validate " + MyQueueTab + " tab is selected");
                Thread.sleep(1000);
                bPatientNameFound = selectPatientNameInOpd(oPage_OPD.rows_patientAppointments,concatPatientFullName);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Exception while getting patient" + e);
            }
            if (bPatientNameFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 20);
                m_assert.assertTrue(Cls_Generic_Methods.scrollToElementByAction(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
                        "Scheduled admission button is displayed");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission),
                        "Clicked on scheduled admission button");

                //Fill Schedule Admission Form
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_ScheduleAdmissionForm, 20),
                        "Scheduled admission form is displayed");

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduleAdmission.radioBtn_emergency),
                        "Select emergency radio button");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_viewCaseDetails),
                        "View case details button is clicked");
                Cls_Generic_Methods.customWait(3);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_createAdmission),
                        "Create admission button is clicked");

                //Assign Bed
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_assignBed, 15),
                        "Assigned bed Form is displayed");

                try {
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_ScheduleAdmission.header_assignBed)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectWard),
                                "Ward dropdown Clicked");
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectWard, 1),
                                "Ward Value Selected");

                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.input_selectRoom),
                                "SelectRoom dropdown Clicked");
                        Cls_Generic_Methods.customWait(5);
                        m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_ScheduleAdmission.input_selectRoom, 1),
                                "SelectRoom value Selected");
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_saveBed),
                                "Clicked on Save bed");
                        Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,10);

                    }else{
                        m_assert.assertTrue(false,"Assign Bed Form Not displayed. ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

            } else {
                m_assert.assertTrue(false, "searched patient is not present in patient list");
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Application not loaded in the browser" + e);
        }

    }



    public static boolean selectPatientNameInOpd(List<WebElement> listOfPatientNames, String expectedPatientName) {
        boolean nameIsSelected = false;
        String patientName = null;
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            for (WebElement eTabElement : listOfPatientNames) {

                if (Cls_Generic_Methods.isElementDisplayed(eTabElement)) {
                    List<WebElement> patientDetailsOnRow = eTabElement.findElements(By.xpath("./child::*"));
                    patientName = Cls_Generic_Methods.getTextInElement(patientDetailsOnRow.get(0).findElement(By.xpath("./span/b")));

                    if (expectedPatientName.equals(patientName.trim())) {
                        Cls_Generic_Methods.clickElement(driver, eTabElement);
                        nameIsSelected = true;
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                                30);
                        break;
                    }
                }
            }
            m_assert.assertTrue(nameIsSelected, "Patient name found in OPD: <b> " + expectedPatientName + "</b>");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nameIsSelected;
    }

    public static String getMonthStringForNumber(int number){

        String monthString = "";

        try{
            switch (number) {
                case 1:  monthString = "Jan";
                    break;
                case 2:  monthString = "Feb";
                    break;
                case 3:  monthString = "Mar";
                    break;
                case 4:  monthString = "Apr";
                    break;
                case 5:  monthString = "May";
                    break;
                case 6:  monthString = "Jun";
                    break;
                case 7:  monthString = "Jul";
                    break;
                case 8:  monthString = "Aug";
                    break;
                case 9:  monthString = "Sep";
                    break;
                case 10: monthString = "Oct";
                    break;
                case 11: monthString = "Nov";
                    break;
                case 12: monthString = "Dec";
                    break;
                default: monthString = "Invalid month";
                    break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return  monthString;

    }




}
