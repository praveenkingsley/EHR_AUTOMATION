package pages.commonElements;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_MedicationSet;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.Template_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pages.commonElements.common_tabs.Page_DiagnosisTab;
import pages.commonElements.common_tabs.Page_ExaminationTab;
import pages.commonElements.common_tabs.Page_HistoryTab;
import pages.commonElements.common_tabs.Page_RefractionTab;
import pages.commonElements.common_tabs.advice.Page_AdviceTab;
import pages.commonElements.common_tabs.investigation.Page_ClinicalDetailsAndAssessmentTab;
import pages.commonElements.common_tabs.investigation.Page_InvestigationTab;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.templates.eye.Page_EyeTemplate;
import pages.opd.Page_OPD;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class TemplateCommonActions extends TestBase {
    static Page_HistoryTab oPage_HistoryTab;
    static Page_RefractionTab oPage_RefractionTab;
    static Page_ExaminationTab oPage_ExaminationTab;
    static Page_InvestigationTab oPage_InvestigationTab;
    static Page_ClinicalDetailsAndAssessmentTab oPage_ClinicalDetailsAndAssessmentTab;
    static Page_DiagnosisTab oPage_DiagnosisTab;
    static Page_EyeTemplate oPage_EyeTemplate;
    static Page_AdviceTab oPage_AdviceTab;
    static Page_OPD oPage_OPD;
    static Page_CommonElements oPage_CommonElements;
    static Template_Data oTemplate_Data;
    static Model_Patient myPatient;
    static Model_MedicationSet medicationSet;
    static Page_NewPatientRegisteration oPage_NewPatientRegisteration;
    static String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    static String medicationSetKeyUsed = Cls_Generic_Methods.getConfigValues("medicationSetKeyUsed");
    static ArrayList<String> rod_VisualAcuity = new ArrayList<>();
    static ArrayList<String> rod_comment_VisualAcuity = new ArrayList<>();
    static ArrayList<String> lod_VisualAcuity = new ArrayList<>();
    static ArrayList<String> lod_comment_VisualAcuity = new ArrayList<>();
    static HashMap<String, String> sCONTACT_LENS_PRESCRIPTIONS;
    static String sTEMPLATE_NAME = null;
    public static void validateAndFillHistoryTab() {
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_HistoryTab.tab_HistoryTab, 20);
            if (!oPage_HistoryTab.tab_History.getAttribute("class").equalsIgnoreCase("active")) {
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_HistoryTab.tab_History),
                        "History Tab is clicked");
            }
            sTEMPLATE_NAME = Cls_Generic_Methods.getTextInElement(oPage_HistoryTab.text_templateName).split(" Template ")[0];
            //Selecting User
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(oPage_HistoryTab.select_user_template, 1), Cls_Generic_Methods.getSelectedValue(oPage_HistoryTab.select_user_template) + " is selected as user");
            //Validating Visit Types
            int selectedVisitIndex = -1;
            for (WebElement visitButton : oPage_HistoryTab.list_buttonVisitType) {
                String visitType = Cls_Generic_Methods.getTextInElement(visitButton);
                if (!visitButton.isSelected()) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(visitButton), visitType + " button in Visit is clickable");
                    selectedVisitIndex++;
                } else {
                    m_assert.assertFatal(visitType + " button is selected by default");
                }
            }
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_HistoryTab.button_clearVisitType), "Clear visit type button is clicked");
            m_assert.assertEquals(Cls_Generic_Methods.radioButtonIsSelected(oPage_HistoryTab.list_buttonVisitType.get(selectedVisitIndex)), false, "Validated clear button - Visit Type is deselected");
            for (WebElement visitButton : oPage_HistoryTab.list_buttonVisitType) {
                String visitType = Cls_Generic_Methods.getTextInElement(visitButton);
                if (visitType.equalsIgnoreCase(oTemplate_Data.sVISIT_TYPE)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(visitButton), "<b>" + visitType + "</b> is clicked in Visit Type");
                }
            }
            if (!oTemplate_Data.sVISIT_COMMENTS.isEmpty()) {
                m_assert.assertInfo("Entered " + oTemplate_Data.sVISIT_TYPE + " in Visit Comment");
            }
            //Validate Chief Complaints
            for (WebElement chiefComplaintButton : oPage_HistoryTab.list_buttonChiefComplaints) {
                String sChiefComplaint = Cls_Generic_Methods.getTextInElement(chiefComplaintButton);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(chiefComplaintButton), sChiefComplaint + " in chief complaint is clickable");
                Cls_Generic_Methods.clickElement(chiefComplaintButton);
            }
            int chiefComplaintIndex = 0;
            try {
                if (oTemplate_Data.sCHIEF_COMPLAINTS.length > 0) {
                    for (String chiefComplaint : oTemplate_Data.sCHIEF_COMPLAINTS) {
                        for (WebElement chiefComplaintButton : oPage_HistoryTab.list_buttonChiefComplaints) {
                            String sChiefComplaint = Cls_Generic_Methods.getTextInElement(chiefComplaintButton);
                            String sSide = oTemplate_Data.sSIDE_CHIEF_COMPLAINT[chiefComplaintIndex];
                            String sDuration = oTemplate_Data.sDURATION_CHIEF_COMPLAINT[chiefComplaintIndex];
                            String sDurationUnit = oTemplate_Data.sDURATION_UNIT_CHIEF_COMPLAINTS[chiefComplaintIndex];
                            String sComment = oTemplate_Data.sCOMMENTS_CHIEF_COMPLAINTS;
                            if (sChiefComplaint.equalsIgnoreCase(chiefComplaint)) {
                                if (chiefComplaint.contains("/")) {
                                    chiefComplaint = chiefComplaint.split("/")[0];
                                }
                                m_assert.assertInfo(Cls_Generic_Methods.clickElement(chiefComplaintButton), "Clicked <b>" + chiefComplaint + "</b> in Chief complaints");
                                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_sideChiefComplaints(chiefComplaint), sSide), "<b>" + sSide + "</b> Side is selected for " + chiefComplaint);
                                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationChiefComplaints(chiefComplaint), sDuration), "<b>" + sDuration + "</b> is selected as a duration for " + chiefComplaint);
                                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationUnitChiefComplaints(chiefComplaint), sDurationUnit), "<b>" + sDurationUnit + "</b> is selected as a duration unit for " + chiefComplaint);
                                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_commentChiefComplaint(chiefComplaint), sComment), sComment + "is entered in comment box");
                            }
                        }
                        chiefComplaintIndex++;
                    }
                }
            } catch (NullPointerException e) {
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, oPage_HistoryTab.checkbox_NIL_Chief_Complaints), "Chief Complaint selected as Nil");
            }
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_commentChiefComplaints, oTemplate_Data.sCOMMENTS_CHIEF_COMPLAINTS), "Enter " + oTemplate_Data.sCOMMENTS_CHIEF_COMPLAINTS + " in Chief Complaint Comments");
            if (!Template_Data.sTODAY_FILLED_EYE_TEMPLATE) {
                //Validate Ophthalmic History
                for (WebElement ophthalmicHistoryBtn : oPage_HistoryTab.list_buttonOphthalmicHistory) {
                    String ophthalmicHistory = Cls_Generic_Methods.getTextInElement(ophthalmicHistoryBtn);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(ophthalmicHistoryBtn), ophthalmicHistory + " in Ophthalmic History is clickable");
                    Cls_Generic_Methods.clickElement(ophthalmicHistoryBtn);
                }
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_commentOphthalmicHistory, oTemplate_Data.sCOMMENTS_OPHTHALMIC_HISTORY), "Entered " + oTemplate_Data.sCOMMENTS_OPHTHALMIC_HISTORY + " in Ophthalmic History Comments");
                int ophthalmicHistoryIndex = 0;
                try {
                    if (oTemplate_Data.sOPHTHALMIC_HISTORY.length > 0) {
                        for (String ophthalmicHistory : oTemplate_Data.sOPHTHALMIC_HISTORY) {
                            for (WebElement ophthalmicHistoryBtn : oPage_HistoryTab.list_buttonOphthalmicHistory) {
                                String sOphthalmicHistory = Cls_Generic_Methods.getTextInElement(ophthalmicHistoryBtn);
                                String sRightDuration = oTemplate_Data.sRIGHT_DURATION[ophthalmicHistoryIndex];
                                String sRightDurationUnit = oTemplate_Data.sRIGHT_DURATION_UNIT[ophthalmicHistoryIndex];
                                String sLeftDuration = oTemplate_Data.sLEFT_DURATION[ophthalmicHistoryIndex];
                                String sLeftDurationUnit = oTemplate_Data.sLEFT_DURATION_UNIT[ophthalmicHistoryIndex];
                                String sComment = oTemplate_Data.sCOMMENTS_OPHTHALMIC_HISTORY;
                                if (sOphthalmicHistory.equalsIgnoreCase(ophthalmicHistory)) {
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(ophthalmicHistoryBtn), "Clicked <b>" + ophthalmicHistory + "</b> in Ophthalmic History");
                                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_HistoryTab.select_rightDurationOphthalmicHistory(ophthalmicHistory), 10);
                                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_rightDurationOphthalmicHistory(ophthalmicHistory), sRightDuration),
                                            "<b>" + sRightDuration + "</b> is selected as a Right Duration for" + ophthalmicHistory);
                                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_rightDurationUnitOphthalmicHistory(ophthalmicHistory), sRightDurationUnit),
                                            "<b>" + sRightDurationUnit + "</b> is selected as a Right Duration Unit for " + ophthalmicHistory);
                                    if (!oTemplate_Data.sCOPY_RIGHT_TO_LEFT) {
                                        m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_leftDurationOphthalmicHistory(ophthalmicHistory), sLeftDuration),
                                                "<b>" + sLeftDuration + "</b> is selected as a Left Duration for" + ophthalmicHistory);
                                        m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_leftDurationUnitOphthalmicHistory(ophthalmicHistory), sLeftDurationUnit),
                                                "<b>" + sLeftDurationUnit + "</b> is selected as a Left Duration Unit for " + ophthalmicHistory);
                                    } else {
                                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_HistoryTab.button_copyRightToLeftOphthalmicHistory(ophthalmicHistory)), "Clicked copy right to left button");
                                        System.out.println(Cls_Generic_Methods.getSelectedValue(oPage_HistoryTab.select_leftDurationOphthalmicHistory(ophthalmicHistory)));
                                        m_assert.assertEquals(Cls_Generic_Methods.getSelectedValue(oPage_HistoryTab.select_leftDurationOphthalmicHistory(ophthalmicHistory)), sRightDuration, "Validated copy from left to right in " + ophthalmicHistory);
                                    }
                                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_commentOphthalmicHistory(ophthalmicHistory), sComment), sComment + "is entered in comment box");
                                }
                            }
                            ophthalmicHistoryIndex++;
                        }
                    }
                } catch (NullPointerException e) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver, oPage_HistoryTab.checkbox_NIL_Ophthalmic_History), "Ophthalmic History selected as Nil");
                }
                //Validate Systemic History
                for (WebElement systemicHistoryBtn : oPage_HistoryTab.list_buttonSystemicHistory) {
                    String sSystemicHistory = Cls_Generic_Methods.getTextInElement(systemicHistoryBtn);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(systemicHistoryBtn), sSystemicHistory + " in Systemic History is clickable");
                    Cls_Generic_Methods.clickElement(systemicHistoryBtn);
                }
                int systemicHistoryIndex = 0;
                try {
                    if (oTemplate_Data.sSYSTEMIC_HISTORY.length > 0) {
                        for (String systemicHistory : oTemplate_Data.sSYSTEMIC_HISTORY) {
                            for (WebElement systemicHistoryBtn : oPage_HistoryTab.list_buttonSystemicHistory) {
                                String sSystemicHistory = Cls_Generic_Methods.getTextInElement(systemicHistoryBtn);
                                String sDuration = oTemplate_Data.sDURATION_SYSTEMIC_HISTORY[systemicHistoryIndex];
                                String sDurationUnit = oTemplate_Data.sDURATION_UNIT_SYSTEMIC_HISTORY[systemicHistoryIndex];
                                String sComment = oTemplate_Data.sCOMMENT_SYSTEMIC_HISTORY[systemicHistoryIndex];
                                if (sSystemicHistory.equalsIgnoreCase(systemicHistory)) {
                                    systemicHistory = systemicHistory.toLowerCase();
                                    if (systemicHistory.contains(" ")) {
                                        systemicHistory = systemicHistory.replaceAll(" ", "_");
                                    }
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(systemicHistoryBtn), "Clicked <b>" + systemicHistory + "</b> in Systemic History");
                                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationSystemicHistory(systemicHistory), sDuration), "<b>" + sDuration + "</b> is selected as a duration for " + systemicHistory);
                                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationUnitSystemicHistory(systemicHistory), sDurationUnit), "<b>" + sDurationUnit + "</b> is selected as a duration unit for " + systemicHistory);
                                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_commentSystemicHistory(systemicHistory), sComment), sComment + "is entered in " + systemicHistory + " comment box");
                                }
                            }
                            systemicHistoryIndex++;
                        }
                    }
                } catch (NullPointerException e) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver, oPage_HistoryTab.checkbox_NIL_Systemic_History), "Systemic History selected as Nil");
                }
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_commentSystemicHistory, oTemplate_Data.sCOMMENTS_CHIEF_COMPLAINTS), "Enter " + oTemplate_Data.sCOMMENTS_CHIEF_COMPLAINTS + " in Systemic History Comments");
                //All Allergies History
                boolean validateDrugAllergyBtn = true;
                int agentDrugAllergiesCount = 0;
                boolean nilStatus = false;
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver, oPage_HistoryTab.checkbox_NIL_All_Allergies), "Validate Nil in All Allergies");
                Cls_Generic_Methods.clickElementByAction(driver, oPage_HistoryTab.checkbox_NIL_All_Allergies);
                for (WebElement drugAllergies : oPage_HistoryTab.list_buttonDrugAllergies) {
                    String drugAllergy = Cls_Generic_Methods.getTextInElement(drugAllergies);
                    try {
                        Cls_Generic_Methods.clickElement(drugAllergies);
                        Cls_Generic_Methods.customWait(2);
                        for (WebElement agentsDrugAllergy : oPage_HistoryTab.list_buttonAllDrugAgentAllergies(drugAllergy)) {
                            try {
                                Cls_Generic_Methods.clickElement(agentsDrugAllergy);
                                String agentDrug = Cls_Generic_Methods.getTextInElement(agentsDrugAllergy);
                                if (!oTemplate_Data.sAGENT_DRUG_ALLERGIES().isEmpty()) {
                                    for (String sAgentDrug : oTemplate_Data.sAGENT_DRUG_ALLERGIES()) {
                                        if (agentDrug.equalsIgnoreCase(sAgentDrug)) {
                                            String sDuration = oTemplate_Data.sDURATION_DRUG_ALLERGIES[agentDrugAllergiesCount];
                                            String sDurationUnit = oTemplate_Data.sDURATION_UNIT_DRUG_ALLERGIES[agentDrugAllergiesCount];
                                            m_assert.assertInfo(sAgentDrug + "is clicked");
                                            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationAllergies(sAgentDrug), sDuration), "<b>" + sDuration + "</b> is selected as a duration for " + sAgentDrug);
                                            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationUnitAllergies(sAgentDrug), sDurationUnit), "<b>" + sDurationUnit + "</b> is selected as Duration Unit for " + sAgentDrug);
                                            agentDrugAllergiesCount++;
                                        } else {
                                            Cls_Generic_Methods.clickElement(agentsDrugAllergy);
                                        }
                                    }
                                } else {
                                    nilStatus = true;
                                }
                            } catch (Exception e) {
                                validateDrugAllergyBtn = false;
                                m_assert.assertFatal(agentsDrugAllergy + " in " + drugAllergies + " Drug allergy is not clickable");
                                e.printStackTrace();
                                continue;
                            }
                        }
                    } catch (Exception e) {
                        validateDrugAllergyBtn = false;
                        m_assert.assertFatal(drugAllergy + " in Drug Allergies is not clickable");
                        e.printStackTrace();
                        continue;
                    }
                    m_assert.assertEquals(validateDrugAllergyBtn, true, "Drug Allergies in " + drugAllergy + " Types are clickable");
                    if (oTemplate_Data.sAGENT_DRUG_ALLERGIES().isEmpty()) {
                        Cls_Generic_Methods.clickElement(drugAllergies);
                    }
                }
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_commentDrugAllergies, oTemplate_Data.sCOMMENTS_ALLERGIES), "Entered " + oTemplate_Data + " in Drug Allergies comment");
                // Contact Allergies
                for (WebElement btn_contactAllergy : oPage_HistoryTab.list_buttonContactAllergies) {
                    String sContactAllergies = Cls_Generic_Methods.getTextInElement(btn_contactAllergy);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn_contactAllergy), "<b>" + sContactAllergies + "</b> in contact allergies is clickable");
                    Cls_Generic_Methods.clickElement(btn_contactAllergy);
                }
                try {
                    int contactAllergyIndex = 0;
                    if (oTemplate_Data.sCONTACT_ALLERGIES.length > 0) {
                        for (String sContactAllergies : oTemplate_Data.sCONTACT_ALLERGIES) {
                            for (WebElement btn_contactAllergy : oPage_HistoryTab.list_buttonContactAllergies) {
                                String textContactAllergyBtn = Cls_Generic_Methods.getTextInElement(btn_contactAllergy);
                                String sDuration = oTemplate_Data.sDURATION_CONTACT_ALLERGIES[contactAllergyIndex];
                                String sDurationUnit = oTemplate_Data.sDURATION_UNIT_CONTACT_ALLERGIES[contactAllergyIndex];
                                if (textContactAllergyBtn.equalsIgnoreCase(sContactAllergies)) {
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn_contactAllergy), "Clicked <b>" + sContactAllergies + "</b> in Contact Allergies");
                                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationAllergies(sContactAllergies), sDuration), "<b>" + sDuration + "</b> is selected as a duration for " + sContactAllergies);
                                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationUnitAllergies(sContactAllergies), sDurationUnit), "<b>" + sDurationUnit + "</b> is selected as a duration unit for " + sContactAllergies);
                                }
                            }
                            contactAllergyIndex++;
                        }
                    }
                } catch (NullPointerException e) {
                    nilStatus = true;
                }
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_commentContactAllergies, oTemplate_Data.sCOMMENTS_ALLERGIES), "Entered " + oTemplate_Data + " in Contact Allergies comment ");
                //Food allergies
                for (WebElement btn_foodAllergy : oPage_HistoryTab.list_buttonFoodAllergies) {
                    String sFoodAllergies = Cls_Generic_Methods.getTextInElement(btn_foodAllergy);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn_foodAllergy), "<b>" + sFoodAllergies + "</b> in Food allergies is clickable");
                    Cls_Generic_Methods.clickElement(btn_foodAllergy);
                }
                try {
                    int foodAllergyIndex = 0;
                    if (oTemplate_Data.sFOOD_ALLERGIES.length > 0) {
                        for (String sFoodAllergies : oTemplate_Data.sFOOD_ALLERGIES) {
                            for (WebElement btn_foodAllergy : oPage_HistoryTab.list_buttonFoodAllergies) {
                                String textFoodAllergyBtn = Cls_Generic_Methods.getTextInElement(btn_foodAllergy);
                                String sDuration = oTemplate_Data.sDURATION_FOOD_ALLERGIES[foodAllergyIndex];
                                String sDurationUnit = oTemplate_Data.sDURATION_UNIT_FOOD_ALLERGIES[foodAllergyIndex];
                                if (textFoodAllergyBtn.equalsIgnoreCase(sFoodAllergies)) {
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn_foodAllergy), "Clicked <b>" + sFoodAllergies + "</b> in Food Allergies");
                                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationAllergies(sFoodAllergies), sDuration), "<b>" + sDuration + "</b> is selected as a duration for " + sFoodAllergies);
                                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_HistoryTab.select_durationUnitAllergies(sFoodAllergies), sDurationUnit), "<b>" + sDurationUnit + "</b> is selected as a duration unit for " + sFoodAllergies);
                                }
                            }
                            foodAllergyIndex++;
                        }
                    }
                } catch (NullPointerException e) {
                    nilStatus = true;
                }
                if (nilStatus) {
                    Cls_Generic_Methods.customWait(2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver, oPage_HistoryTab.checkbox_NIL_All_Allergies), "All Allergies selected as Nil");
                }
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_commentFoodAllergies, oTemplate_Data.sCOMMENTS_ALLERGIES), "Entered " + oTemplate_Data + " in Food Allergies comment ");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_otherAllergiesComment, oTemplate_Data.sCOMMENTS_ALLERGIES), "Entered " + oTemplate_Data + " in Food Allergies comment");
            }
            //Vital Signs
            validateVitalSign();
        } catch (Exception e) {
            m_assert.assertFatal("Unable to find History Tab " + e);
            e.printStackTrace();
        }
    }
    public static void validateAndFillRefractionTab() {
        oPage_RefractionTab = new Page_RefractionTab(driver);
        oTemplate_Data = new Template_Data();
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        try {
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.tab_RefractionTab),
                    "Refraction Tab is clicked");
            sTEMPLATE_NAME = Cls_Generic_Methods.getTextInElement(oPage_HistoryTab.text_templateName).split(" Template ")[0];
            if (oPage_RefractionTab.list_showOrHideRefractionField_FreeForm.size() > 0) {
                for (WebElement btn : oPage_RefractionTab.list_showOrHideRefractionField_FreeForm) {
                    Cls_Generic_Methods.clickElementByJS(driver, btn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (sTEMPLATE_NAME.toUpperCase()) {
            case "EYE", "OPTOMETRIST", "PMT", "POST OP", "PAEDIATRICS", "QUICK EYE", "ORTHOPTICS", "TRAUMA", "FREE FORM" -> {
                validateRefraction_VISUAL_ACUITY();
                validateRefraction_IOP();
                validateRefraction_AUTO_REFRACTION();
                validateRefraction_DRY_REFRACTION();
                validateRefraction_DILATED_REFRACTION();
                validateRefraction_PGP();
                validateRefraction_GLASS_PRESCRIPTION();
                validateRefraction_INTERMEDIATE_GLASSES_PRESCRIPTIONS();
                validateRefraction_PMT();
                validateRefraction_RETINOSCOPY();
                validateRefraction_KERATOMETRY();
                validateRefraction_AMSLER();
                validateRefraction_CONTACT_LENS();
                validateRefraction_COLOR_VISION();
                validateRefraction_CONTRAST_SENSITIVITY();
                validateRefraction_ORTHOPTICS();
            }
            case "LENS" -> {
                validateRefraction_VISUAL_ACUITY();
                validateRefraction_OCCULAR_ASSESSMENT();
                validateRefraction_PGP();
                validateRefraction_TRIAL_LENS_DETAILS();
                validateRefraction_FIT_ASSESSMENT();
                validateRefraction_OVER_REACTION_ACCEPTANCE();
                validateRefraction_CONTACT_LENS();
                validateRefraction_VIRTUAL_NEEDS();
                validateRefraction_DISTANCE();
                validateRefraction_FINAL_RECOMMENDATIONS();
                validateRefraction_LVA_PROBLEMS();
                validateRefraction_GLASS_PRESCRIPTION();
                validateRefraction_COLOR_VISION();
                validateRefraction_CONTRAST_SENSITIVITY();
                validateRefraction_IOP();
                validateRefraction_ORTHOPTICS();
            }
        }
    }
    public static void validateAndFillExaminationTab() {
        oTemplate_Data = new Template_Data();
        myPatient = map_PatientsInExcel.get(patientKey);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_ExaminationTab = new Page_ExaminationTab(driver);
        try {
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ExaminationTab.tab_ExaminationTab),
                    "Examination Tab is clicked");
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to click Examination Tab");
        }
        try {
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ExaminationTab.input_commentsExamination, oTemplate_Data.sGENERAL_COMMENT_EXAMINATION), "Entered " + oTemplate_Data.sGENERAL_COMMENT_EXAMINATION + " in Examination comments");
            for (WebElement btnGeneralExamination : oPage_ExaminationTab.list_BtnGeneralExamination) {
                String buttonValue = Cls_Generic_Methods.getTextInElement(btnGeneralExamination);
                if (buttonValue.equalsIgnoreCase("normal")) {
                    m_assert.assertTrue(isButtonSelected(btnGeneralExamination), "In General Examination ,<b>Normal</b> is selected by Default");
                } else {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(btnGeneralExamination), "Abnormal button is clickable");
                    m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ExaminationTab.input_abnormalComment_GeneralExamination, 5), "Validated Abnormal comment is displayed");
                }
            }
            Cls_Generic_Methods.clickElement(oPage_ExaminationTab.list_BtnGeneralExamination.get(0));
            for (WebElement button_OneEyed : oPage_ExaminationTab.list_buttonOneEyed) {
                String value = Cls_Generic_Methods.getTextInElement(button_OneEyed);
                if (value.equalsIgnoreCase("no")) {
                    m_assert.assertTrue(isButtonSelected(button_OneEyed), "In One Eyed ,<b>No</b> is selected by Default");
                }
            }
            for (WebElement button_OneEyed : oPage_ExaminationTab.list_buttonOneEyed) {
                String value = Cls_Generic_Methods.getTextInElement(button_OneEyed);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(button_OneEyed), "Validated <b>" + value + "</b> in One Eyed is Clickable");
            }
            if (oTemplate_Data.sONE_EYED_EXAMINATION) {
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ExaminationTab.list_buttonOneEyed.get(0)), "Clicked <b>Yes</b> in One eyed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sTEMPLATE_NAME.equalsIgnoreCase("EYE")) {
            //SQUINT EXAMINATION
            try {
                for (WebElement button_SquintExamination : oPage_ExaminationTab.list_buttonSquintEvaluation) {
                    String value = Cls_Generic_Methods.getTextInElement(button_SquintExamination);
                    if (value.equalsIgnoreCase("no")) {
                        m_assert.assertTrue(isButtonSelected(button_SquintExamination), "In Squint Examination ,<b>No</b> is selected by Default");
                    }
                }
                for (WebElement button_SquintExamination : oPage_ExaminationTab.list_buttonSquintEvaluation) {
                    String value = Cls_Generic_Methods.getTextInElement(button_SquintExamination);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(button_SquintExamination), "Validated <b>" + value + "</b> in Squint Examination is Clickable");
                }
                if (oTemplate_Data.sSQUINT_EXAMINATION) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ExaminationTab.list_buttonSquintEvaluation.get(0)), "Clicked <b>Yes</b> in One eyed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            //Normal
            for (WebElement normalBtn : oPage_ExaminationTab.list_normalButton) {
                if (!isButtonSelected(normalBtn)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(normalBtn), "Normal button is clickable in " + findLeftOrRightFieldName(normalBtn));
                } else {
                    m_assert.assertFatal("Normal is selected by default in " + findLeftOrRightFieldName(normalBtn));
                }
                if (!oTemplate_Data.sNORMAL_EXAMINATION) {
                    Cls_Generic_Methods.clickElement(normalBtn);
                }
            }
            expandAll_Examination();
            //OCULAR TRAUMA
            if (sTEMPLATE_NAME.equalsIgnoreCase("TRAUMA")) {
                validateBtnExamination(oPage_ExaminationTab.list_btnOcularTrauma);
                for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentOcularTrauma) {
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
                }
            }
            //APPEARANCE
            validateBtnExamination(oPage_ExaminationTab.list_btnAppearance);
            for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentAppearance) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            //INJURY
            validateBtnExamination(oPage_ExaminationTab.list_btnAppearance);
            int injuryIndex = 0;
            while (injuryIndex <= 1) {
                for (WebElement btnNatureOfInjury : oPage_ExaminationTab.list_BtnNatureOfInjury(injuryIndex)) {
                    String value = Cls_Generic_Methods.getTextInElement(btnNatureOfInjury);
                    if (value.equalsIgnoreCase("Open Globe")) {
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(btnNatureOfInjury), "Open Globe button is clickable in " + findLeftOrRightFieldName(btnNatureOfInjury));
                    }
                }
                for (WebElement btnOpenGlobe : oPage_ExaminationTab.list_BtnOpenGlobeInjuries(injuryIndex)) {
                    String value = Cls_Generic_Methods.getTextInElement(btnOpenGlobe);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btnOpenGlobe), "<b>" + value + "</b> button is clickable in " + findLeftOrRightFieldName(btnOpenGlobe));
                }
                for (WebElement btnNatureOfInjury : oPage_ExaminationTab.list_BtnNatureOfInjury(injuryIndex)) {
                    String value = Cls_Generic_Methods.getTextInElement(btnNatureOfInjury);
                    if (value.equalsIgnoreCase("Closed Globe")) {
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(btnNatureOfInjury), "Closed Globe button is clickable in " + findLeftOrRightFieldName(btnNatureOfInjury));
                    }
                }
                for (WebElement btnClosedGlobe : oPage_ExaminationTab.list_BtnClosedGlobeInjuries(injuryIndex)) {
                    String value = Cls_Generic_Methods.getTextInElement(btnClosedGlobe);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btnClosedGlobe), "<b>" + value + "</b> button is clickable in " + findLeftOrRightFieldName(btnClosedGlobe));
                }
                injuryIndex++;
            }
            //APPENDAGES
            for (WebElement btnHeaderAppendages : oPage_ExaminationTab.list_btnHeaderAppendages) {
                String value = Cls_Generic_Methods.getTextInElement(btnHeaderAppendages);
                if (isButtonSelected(btnHeaderAppendages)) {
                    m_assert.assertFatal(value + " button is selected by default in " + findLeftOrRightFieldName(btnHeaderAppendages));
                }
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(btnHeaderAppendages), value + " button is clickable in " + findLeftOrRightFieldName(btnHeaderAppendages));
            }
            validateBtnExamination(oPage_ExaminationTab.list_btnAppendages);
            for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentAppendages) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            for (WebElement btn_diagram : oPage_ExaminationTab.list_BtnAppendagesDiagram) {
                clickAndValidateDiagram(btn_diagram);
            }
            //CONJUNCTIVA
            validateBtnExamination(oPage_ExaminationTab.list_btnConjunctiva);
            for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentConjunctiva) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            //SCLERA
            for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentSclera) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            //CORNEA
            validateBtnExamination(oPage_ExaminationTab.list_btnCornea);
            validateBtnExamination(oPage_ExaminationTab.list_btnFluorescein_StainingCornea);
            for (WebElement inputSchirmer : oPage_ExaminationTab.list_inputSchirmerTestCornea) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputSchirmer, oTemplate_Data.sSCHIRMER_TEST_EXAMINATION), "Entered <b>" + oTemplate_Data.sSCHIRMER_TEST_EXAMINATION + "</b> in " + findLeftOrRightFieldName(inputSchirmer));
            }
            for (WebElement inputCCT : oPage_ExaminationTab.list_inputCCT_Reading("1")) {
                String inputCCT_value = CommonActions.getRandomNumber();
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputCCT, inputCCT_value), "Entered <b>" + inputCCT_value + "</b> in " + findLeftOrRightFieldName(inputCCT));
            }
            for (WebElement inputCCT_time : oPage_ExaminationTab.list_inputCCT_Time("1")) {
                String inputCCT_timeValue = oTemplate_Data.sIOP_TIME;
                m_assert.assertInfo(setTime(inputCCT_time, inputCCT_timeValue), "Entered <b>" + inputCCT_timeValue + "</b> in " + findLeftOrRightFieldName(inputCCT_time));
            }
            int cctCount = 0;
            for (WebElement btnAddCCT : oPage_ExaminationTab.list_btn_addCCT_TestCornea) {
                cctCount = 1;
                while (Cls_Generic_Methods.isElementDisplayed(btnAddCCT)) {
                    cctCount++;
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, btnAddCCT), "Clicked Add CCT" + cctCount + " button");
                }
            }
            m_assert.assertTrue(cctCount == 4, "Validated --> Add CCT functionality");
            cctCount = 2;
            while (cctCount <= 4) {
                Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_ExaminationTab.list_inputCCT_Reading(String.valueOf(cctCount)), 10);
                for (WebElement inputCCT : oPage_ExaminationTab.list_inputCCT_Reading(String.valueOf(cctCount))) {
                    String inputCCT_value = CommonActions.getRandomNumber();
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputCCT, inputCCT_value), "Entered <b>" + inputCCT_value + "</b> in " + findLeftOrRightFieldName(inputCCT));
                }
                for (WebElement inputCCT_time : oPage_ExaminationTab.list_inputCCT_Time(String.valueOf(cctCount))) {
                    String inputCCT_timeValue = oTemplate_Data.sIOP_TIME;
                    m_assert.assertInfo(setTime(inputCCT_time, inputCCT_timeValue), "Entered <b>" + inputCCT_timeValue + "</b> in " + findLeftOrRightFieldName(inputCCT_time));
                }
                cctCount++;
            }
            for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentCornea) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            for (WebElement btn_diagram : oPage_ExaminationTab.list_BtnCorneaDiagram) {
                clickAndValidateDiagram(btn_diagram);
            }
            for (WebElement btn_diagram : oPage_ExaminationTab.list_BtnCorneaSlitDiagram) {
                clickAndValidateDiagram(btn_diagram);
            }
            //ANTERIOR CHAMBER
            validateBtnExamination(oPage_ExaminationTab.list_btnAnteriorChamber);
            for (WebElement inputComment : oPage_ExaminationTab.list_inputAnteriorChamber) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentAnteriorChamber) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            //PUPIL
            validateBtnExamination(oPage_ExaminationTab.list_btnPupil);
            int sliderIndex = 0;
            for (WebElement sliderPupil : oPage_ExaminationTab.list_sliderPupilSize) {
                moveSlider(sliderPupil, oTemplate_Data.sPUPIL_SIZE, "RIGHT");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ExaminationTab.list_inputPupilSize.get(sliderIndex), "value").equals(oTemplate_Data.sPUPIL_SIZE),
                        "Validated -->Entered " + oTemplate_Data.sPUPIL_SIZE + " in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_inputPupilSize.get(sliderIndex)));
                sliderIndex++;
            }
            for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentPupil) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            //IRIS
            validateBtnExamination(oPage_ExaminationTab.list_btnIris);
            for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentIris) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            //CONTACT LENS
            if (sTEMPLATE_NAME.equalsIgnoreCase("LENS")) {
                for (WebElement inputContactLens : oPage_ExaminationTab.list_inputContactLens) {
                    String contactValue = CommonActions.getRandomNumber();
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputContactLens, contactValue), "Entered " + contactValue + " in " + findLeftOrRightFieldName(inputContactLens));
                }
            }
            //LENS
            validateBtnExamination(oPage_ExaminationTab.list_btnLens);
            for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentLens) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            for (WebElement btnLens : oPage_ExaminationTab.list_btnLens) {
                String value = Cls_Generic_Methods.getTextInElement(btnLens);
                if (value.equalsIgnoreCase("Cataract")) {
                    Cls_Generic_Methods.clickElement(btnLens);
                }
            }
            for (WebElement selectLOCS : oPage_ExaminationTab.list_select_LOCS_grading_lens) {
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(selectLOCS, 3), "Selected " + Cls_Generic_Methods.getSelectedValue(selectLOCS) + " in " + findLeftOrRightFieldName(selectLOCS));
            }
            for (WebElement btn_diagram : oPage_ExaminationTab.list_BtnLensDiagram) {
                clickAndValidateDiagram(btn_diagram);
            }
            //EXTRAOCULAR MOVEMENTS & SQUINT
            validateBtnExamination(oPage_ExaminationTab.list_btnUniocularAndBinocular);
            for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentExtraocularMovements) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sCOMMENT_EXAMINATION), "Entered " + oTemplate_Data.sCOMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            for (WebElement inputComment : oPage_ExaminationTab.list_inputPrismExtraocularMovements) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sPRISM_EXTRAOCULAR_MOVEMENTS), "Entered " + oTemplate_Data.sPRISM_EXTRAOCULAR_MOVEMENTS + " in " + findLeftOrRightFieldName(inputComment));
            }
            int index = 0;
            while (index < 2) {
                for (WebElement btn_Squint : oPage_ExaminationTab.list_BtnSquint(index)) {
                    String squintValue = Cls_Generic_Methods.getTextInElement(btn_Squint);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn_Squint), "<b>" + squintValue + "</b> button is clickable in " + findLeftOrRightFieldName(btn_Squint));
                    if (squintValue.contains("Tropia")) {
                        //Validating Clear Squint
                        m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_ExaminationTab.list_BtnClearSquint(index)), "Clicked Clear Squint button");
                        if (!isButtonSelected(btn_Squint)) {
                            m_assert.assertTrue("Validated --> Clear Squint functionality");
                            Cls_Generic_Methods.clickElement(btn_Squint);
                        } else {
                            m_assert.assertFatal("Unable to validate clear squint");
                        }
                        for (WebElement btn_tropia : oPage_ExaminationTab.list_BtnTropiaSquint(index)) {
                            String tropiaValue = Cls_Generic_Methods.getTextInElement(btn_tropia);
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn_tropia), "<b>" + tropiaValue + "</b> button is clickable in " + findLeftOrRightFieldName(btn_tropia));
                            switch (tropiaValue.toUpperCase()) {
                                case "HORIZONTAL" -> {
                                    for (WebElement btn_Horizontal : oPage_ExaminationTab.list_BtnHorizontalTropiaSquint(index)) {
                                        String horizontalValue = Cls_Generic_Methods.getTextInElement(btn_Horizontal);
                                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn_Horizontal), "<b>" + horizontalValue + "</b> button is clickable in " + findLeftOrRightFieldName(btn_Horizontal));
                                        if (horizontalValue.contains("Esotropia")) {
                                            validateBtnExamination(oPage_ExaminationTab.list_BtnEsotropiaHorizontalTropiaSquint(index));
                                        } else if (horizontalValue.contains("Exotropia")) {
                                            validateBtnExamination(oPage_ExaminationTab.list_BtnExotropiaHorizontalTropiaSquint(index));
                                        }
                                    }
                                }
                                case "VERTICAL" ->
                                        validateBtnExamination(oPage_ExaminationTab.list_BtnVerticalTropiaSquint(index));
                                case "PARALYTIC" ->
                                        validateBtnExamination(oPage_ExaminationTab.list_BtnParalyticTropiaSquint(index));
                            }
                        }
                    } else if (squintValue.contains("Phoria")) {
                        validateBtnExamination(oPage_ExaminationTab.list_BtnPhoriaSquint(index));
                    }
                }
                index++;
            }
            //INTRAOCULAR PRESSURE (IOP)
            if (!oTemplate_Data.sIOP_NOT_EXAMINED) {
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ExaminationTab.list_inputRightIOP, "value").equals(myPatient.getsIOP_RIGHT()), "Validated -->Right IOP value auto-filled from Refraction Tab");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ExaminationTab.list_inputLeftIOP, "value").equals(myPatient.getsIOP_LEFT()), "Validated -->Left IOP value auto-filled from Refraction");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ExaminationTab.list_inputRightIOP_time, "value").replaceAll(" ", ":").equals(oTemplate_Data.sIOP_TIME), "Validated -->Right IOP time auto-filled from Refraction Tab");
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ExaminationTab.list_inputLeftIOP_time, "value").replaceAll(" ", ":").equals(oTemplate_Data.sIOP_TIME), "Validated -->Left IOP time auto-filled from Refraction");
                for (int i = 0; i < 4; i++) {
                    m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ExaminationTab.list_inputIOP("l").get(i), "value").equals(myPatient.getsIOP_RIGHT()), "Validated data flow between refraction and examination -->Entered L/OS IOP" + (i + 1) + " value <b>" + myPatient.getsIOP_RIGHT() + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_ExaminationTab.selectIOP_method("l").get(i)).equals(oTemplate_Data.sIOP_METHOD), "Validated data flow between refraction and examination -->Selected L/OS IOP" + (i + 1) + " method as <b>" + oTemplate_Data.sIOP_METHOD + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ExaminationTab.list_inputIOP("r").get(i), "value").equals(myPatient.getsIOP_RIGHT()), "Validated data flow between refraction and examination -->Entered R/OD IOP" + (i + 1) + " value <b>" + myPatient.getsIOP_RIGHT() + "</b>");
                    m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_ExaminationTab.selectIOP_method("r").get(i)).equals(oTemplate_Data.sIOP_METHOD), "Validated data flow between refraction and examination -->Selected R/OD IOP" + (i + 1) + " method as <b>" + oTemplate_Data.sIOP_METHOD + "</b>");
                }
            }
            for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentIOP) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sGENERAL_COMMENT_EXAMINATION), "Entered " + oTemplate_Data.sGENERAL_COMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            //GONIOSCOPY
            for (WebElement selectSuperior : oPage_ExaminationTab.list_select_superior_gonioscopy) {
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(selectSuperior, 1), "Selected " + Cls_Generic_Methods.getSelectedValue(selectSuperior) + " in " + findLeftOrRightFieldName(selectSuperior));
            }
            for (WebElement selectTemporal : oPage_ExaminationTab.list_select_temporal_gonioscopy) {
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(selectTemporal, 1), "Selected " + Cls_Generic_Methods.getSelectedValue(selectTemporal) + " in " + findLeftOrRightFieldName(selectTemporal));
            }
            for (WebElement selectNasal : oPage_ExaminationTab.list_select_nasal_gonioscopy) {
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(selectNasal, 1), "Selected " + Cls_Generic_Methods.getSelectedValue(selectNasal) + " in " + findLeftOrRightFieldName(selectNasal));
            }
            for (WebElement selectInferior : oPage_ExaminationTab.list_select_inferior_gonioscopy) {
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(selectInferior, 1), "Selected " + Cls_Generic_Methods.getSelectedValue(selectInferior) + " in " + findLeftOrRightFieldName(selectInferior));
            }
            for (WebElement inputComment : oPage_ExaminationTab.list_inputCommentGonioscopy) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputComment, oTemplate_Data.sGENERAL_COMMENT_EXAMINATION), "Entered " + oTemplate_Data.sGENERAL_COMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(inputComment));
            }
            //FUNDUS
            int sideIndex = 0;
            boolean maculaStatus = false;
            while (sideIndex < 2) {
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ExaminationTab.list_BtnNormalFundus.get(sideIndex)), "Clicked " + findLeftOrRightFieldName(oPage_ExaminationTab.list_BtnNormalFundus.get(sideIndex)));
                m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_ExaminationTab.list_select_media_fundus.get(sideIndex)).equalsIgnoreCase("Clear"), "Validated Normal -->Selected <b>Clear</b> in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_select_media_fundus.get(sideIndex)));
                m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_ExaminationTab.list_select_PVD_fundus.get(sideIndex)).equalsIgnoreCase("Absent"), "Validated Normal -->Selected <b>Absent</b> in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_select_PVD_fundus.get(sideIndex)));
                m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_ExaminationTab.list_select_opticDiscSize_fundus.get(sideIndex)).equalsIgnoreCase("Medium"), "Validated Normal -->Selected <b>Medium</b> in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_select_opticDiscSize_fundus.get(sideIndex)));
                m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_ExaminationTab.list_select_cupDiscRatio_fundus.get(sideIndex)).equalsIgnoreCase("0.3"), "Validated Normal -->Selected <b>0.3</b> in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_select_cupDiscRatio_fundus.get(sideIndex)));
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ExaminationTab.list_input_opticDisc_fundus.get(sideIndex), "value").equalsIgnoreCase("healthy"), "Validated Normal -->Entered <b>healthy</b> in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_input_opticDisc_fundus.get(sideIndex)));
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ExaminationTab.list_input_bloodVesselComment_fundus.get(sideIndex), "value").equalsIgnoreCase("normal"), "Validated Normal -->Entered <b>normal</b> in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_input_bloodVesselComment_fundus.get(sideIndex)));
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_ExaminationTab.list_input_fundusComment_fundus.get(sideIndex), "value").equalsIgnoreCase("within normal limits"), "Validated Normal -->Entered <b>within normal limits</b> in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_input_bloodVesselComment_fundus.get(sideIndex)));
                for (WebElement btnMacula : oPage_ExaminationTab.list_btn_macula_fundus) {
                    String value = Cls_Generic_Methods.getTextInElement(btnMacula);
                    if (isButtonSelected(btnMacula)) {
                        if (value.equalsIgnoreCase("Foveal Reflex")) {
                            m_assert.assertTrue("Validated Normal Macula  -->Clicked <b>Foveal Reflex</b> in " + findLeftOrRightFieldName(btnMacula));
                            maculaStatus = true;
                        } else {
                            maculaStatus = false;
                            m_assert.assertFatal(value + " should not be selected as normal value");
                        }
                    }
                }
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ExaminationTab.list_input_mediaComment_fundus.get(sideIndex), oTemplate_Data.sGENERAL_COMMENT_EXAMINATION), "Entered " + oTemplate_Data.sGENERAL_COMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_input_mediaComment_fundus.get(sideIndex)));
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ExaminationTab.list_input_cupRatioComment_fundus.get(sideIndex), oTemplate_Data.sGENERAL_COMMENT_EXAMINATION), "Entered " + oTemplate_Data.sGENERAL_COMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_input_cupRatioComment_fundus.get(sideIndex)));
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ExaminationTab.list_input_maculaComment_fundus.get(sideIndex), oTemplate_Data.sGENERAL_COMMENT_EXAMINATION), "Entered " + oTemplate_Data.sGENERAL_COMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_input_maculaComment_fundus.get(sideIndex)));
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ExaminationTab.list_input_vitreousComment_fundus.get(sideIndex), oTemplate_Data.sGENERAL_COMMENT_EXAMINATION), "Entered " + oTemplate_Data.sGENERAL_COMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_input_vitreousComment_fundus.get(sideIndex)));
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ExaminationTab.list_input_retinalDetachmentComment_fundus.get(sideIndex), oTemplate_Data.sGENERAL_COMMENT_EXAMINATION), "Entered " + oTemplate_Data.sGENERAL_COMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_input_retinalDetachmentComment_fundus.get(sideIndex)));
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ExaminationTab.list_input_peripheralLesionsComment_fundus.get(sideIndex), oTemplate_Data.sGENERAL_COMMENT_EXAMINATION), "Entered " + oTemplate_Data.sGENERAL_COMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_input_peripheralLesionsComment_fundus.get(sideIndex)));
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ExaminationTab.list_inputCommentFundus.get(sideIndex), oTemplate_Data.sGENERAL_COMMENT_EXAMINATION), "Entered " + oTemplate_Data.sGENERAL_COMMENT_EXAMINATION + " in " + findLeftOrRightFieldName(oPage_ExaminationTab.list_inputCommentFundus.get(sideIndex)));
                sideIndex++;
            }
            if (maculaStatus) {
                for (WebElement btn_Foveal : oPage_ExaminationTab.list_btn_fovealReflex_macula_fundus) {
                    String foveal_value = Cls_Generic_Methods.getTextInElement(btn_Foveal);
                    if (isButtonSelected(btn_Foveal)) {
                        if (foveal_value.equalsIgnoreCase("Present")) {
                            m_assert.assertTrue("Validated Normal -->Clicked <b>Present</b> in " + findLeftOrRightFieldName(btn_Foveal));
                        } else {
                            m_assert.assertFatal(foveal_value + " should not be selected as normal value");
                        }
                    }
                }
            }
            validateBtnExamination(oPage_ExaminationTab.list_btn_macula_fundus);
            for (WebElement btn_diagram : oPage_ExaminationTab.list_BtnFundusDiagram) {
                clickAndValidateDiagram(btn_diagram);
            }
            Cls_Generic_Methods.customWait();
            //Validate Resell All
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_ExaminationTab.list_resetAllButton, 20);
            for (WebElement resetAllBtn : oPage_ExaminationTab.list_resetAllButton) {
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, resetAllBtn), "Clicked Reset all in examination");
                driver.switchTo().alert().accept();
            }
            expandAll_Examination();
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_ExaminationTab.list_btnAppearance, 20);
            m_assert.assertTrue(validateResetBtnExamination(oPage_ExaminationTab.list_btnAppearance), "Validated Reset all -->All button in Appearance set to default value ");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCommentAppearance), "Validated Reset all -->cleared Appearance comments");
            //APPENDAGES
            for (WebElement btnHeaderAppendages : oPage_ExaminationTab.list_btnHeaderAppendages) {
                Cls_Generic_Methods.clickElement(btnHeaderAppendages);
            }
            m_assert.assertTrue(validateResetBtnExamination(oPage_ExaminationTab.list_btnAppendages), "Validated Reset all -->All button in Appendages set to default value ");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCommentAppendages), "Validated Reset all -->cleared Appendages comments");
            //CONJUNCTIVA
            m_assert.assertTrue(validateResetBtnExamination(oPage_ExaminationTab.list_btnConjunctiva), "Validated Reset all -->All button in Conjunctiva set to default value ");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCommentConjunctiva), "Validated Reset all -->cleared Conjunctiva comments");
            //SCLERA
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCommentSclera), "Validated Reset all -->cleared Sclera comments");
            //CORNEA
            m_assert.assertTrue(validateResetBtnExamination(oPage_ExaminationTab.list_btnCornea), "Validated Reset all -->All button in cornea set to default value ");
            m_assert.assertTrue(validateResetBtnExamination(oPage_ExaminationTab.list_btnFluorescein_StainingCornea), "Validated Reset all -->All button is Fluorescein Staining set to default value ");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCommentCornea), "Validated Reset all -->cleared Cornea comments");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputSchirmerTestCornea), "Validated Reset all -->cleared Schirmer Test value");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCctReadingCornea), "Validated Reset all -->cleared CCT values");
            //ANTERIOR CHAMBER
            m_assert.assertTrue(validateResetBtnExamination(oPage_ExaminationTab.list_btnAnteriorChamber), "Validated Reset all -->All button is Anterior Chamber set to default value ");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCommentAnteriorChamber), "Validated Reset all -->cleared Anterior chamber comments");
            //PUPIL
            m_assert.assertTrue(validateResetBtnExamination(oPage_ExaminationTab.list_btnPupil), "Validated Reset all -->All button is Anterior Chamber set to default value ");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputPupilSize), "Validated Reset all -->cleared Pupil size value");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCommentPupil), "Validated Reset all -->cleared Pupil comments");
            //IRIS
            m_assert.assertTrue(validateResetBtnExamination(oPage_ExaminationTab.list_btnIris), "Validated Reset all -->All button is Iris set to default value ");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCommentIris), "Validated Reset all -->cleared Iris comments");
            //LENS
            m_assert.assertTrue(validateResetBtnExamination(oPage_ExaminationTab.list_btnLens), "Validated Reset all -->All button is Lens set to default value ");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCommentLens), "Validated Reset all -->cleared Lens comments");
            //EXTRAOCULAR MOVEMENTS & SQUINT
            m_assert.assertTrue(validateResetBtnExamination(oPage_ExaminationTab.list_btnUniocularAndBinocular), "Validated Reset all -->All button is Uniocular and Binocular set to default value ");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputPrismExtraocularMovements), "Validated Reset all -->cleared Prism value");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCommentExtraocularMovements), "Validated Reset all -->cleared Extraocular Movements comments");
            m_assert.assertTrue(validateResetBtnExamination(oPage_ExaminationTab.list_buttonSquintEvaluation), "Validated Reset all -->All button is Squint Extraocular Movements set to default value ");
            //GONIOSCOPY
            //,"Validated Reset all -->De is Squint Extraocular Movements set to default value"
            m_assert.assertTrue(validateSelectExamination(oPage_ExaminationTab.list_selectAll_gonioscopy), "Validated Reset all -->All dropdown in Gonioscopy set to default value");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCommentGonioscopy), "Validated Reset all -->cleared Gonioscopy comments");
            //FUNDUS
            m_assert.assertTrue(validateSelectExamination(oPage_ExaminationTab.list_select_media_fundus), "Validated Reset all -->values in Media fundus set to default value");
            m_assert.assertTrue(validateSelectExamination(oPage_ExaminationTab.list_select_PVD_fundus), "Validated Reset all -->values in PVD fundus set to default value");
            m_assert.assertTrue(validateSelectExamination(oPage_ExaminationTab.list_select_opticDiscSize_fundus), "Validated Reset all -->values in Optic Disc Size set to default value");
            m_assert.assertTrue(validateSelectExamination(oPage_ExaminationTab.list_select_cupDiscRatio_fundus), "Validated Reset all -->values in Cup/Disc Ratio set to default value");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_input_opticDisc_fundus), "Validated Reset all -->cleared Optic Disc value");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_select_bloodVessels_fundus), "Validated Reset all -->cleared Blood Vessel value");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_inputCommentFundus), "Validated Reset all -->cleared fundus general comment");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_input_fundusComment_fundus), "Validated Reset all -->cleared Fundus comment");
            m_assert.assertTrue(validateResetBtnExamination(oPage_ExaminationTab.list_btn_macula_fundus), "Validated Reset all -->All button in Macula Fundus set to default value ");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_input_mediaComment_fundus), "Validated Reset all -->cleared Fundus media comment");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_input_cupRatioComment_fundus), "Validated Reset all -->cleared Cup ratio comment");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_input_maculaComment_fundus), "Validated Reset all -->cleared Macula comment");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_input_vitreousComment_fundus), "Validated Reset all -->cleared Vitreous comment");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_input_retinalDetachmentComment_fundus), "Validated Reset all -->cleared Retinal Detachment comment");
            m_assert.assertTrue(validateResetTextBox(oPage_ExaminationTab.list_input_peripheralLesionsComment_fundus), "Validated Reset all -->cleared Peripheral Lesions comment");
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate examination" + e);
            e.printStackTrace();
        }
    }
    public static void validateAndFillInvestigation() {
        oTemplate_Data = new Template_Data();
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_InvestigationTab = new Page_InvestigationTab(driver);
        try {
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_InvestigationTab.tab_investigation),
                    "Investigation Tab is clicked");
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to click Investigation Tab");
        }
        try {
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, oPage_InvestigationTab.checkBox_NoInvestigation), "Clicked No Investigation Advised checkbox");
            isValid_Header("INVESTIGATION");
            Cls_Generic_Methods.clickElementByAction(driver, oPage_InvestigationTab.checkBox_NoInvestigation);
            m_assert.assertTrue(Cls_Generic_Methods.radioButtonIsSelected(oPage_InvestigationTab.radioBtn_standardOpthalInvestigation), "Standard Ophthal Investigation is selected by default");
            //  m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_InvestigationTab.select_ophthalSetsUnderInvestigations, oTemplate_Data.sOPHTHAL_SET), "Selected <b>" + oTemplate_Data.sOPHTHAL_SET + "</b> under Ophthal set");
            m_assert.assertInfo(selectValueFromSet(oPage_InvestigationTab.select_ophthalSetsUnderInvestigations, oTemplate_Data.sOPHTHAL_SET), "Selected <b>" + oTemplate_Data.sOPHTHAL_SET + "</b> under Ophthal set");
            Cls_Generic_Methods.customWait(3);
            int index = 0;
            int intial_Row = oPage_InvestigationTab.rows_selectedOphthalInvestigations.size();
            while (index < intial_Row) {
                String investigation_value = Cls_Generic_Methods.getElementAttribute(oPage_InvestigationTab.list_inputSelectedOphthalInvestigations.get(index), "value");
                int advisedBtnIndex = 1;
                while (advisedBtnIndex > -1) {
                    WebElement btn = oPage_InvestigationTab.list_radioAdvisedPerformedOphthalInvestigations(index + 1).get(advisedBtnIndex);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, btn), "Clicked <b>" + advisedOrPerformed(btn) + "</b> in " + investigation_value);
                    if (advisedOrPerformed(btn).equals("Performed")) {
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_InvestigationTab.input_findingsPerformed, oTemplate_Data.sFINDINGS_PERFORMED), "Entered " + oTemplate_Data.sFINDINGS_PERFORMED + " in " + investigation_value + " Findings Performed");
                    }
                    advisedBtnIndex--;
                }
                index++;
            }
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_InvestigationTab.list_deleteSelectedOphthalInvestigations.get(index - 1)), "Clicked Delete in ophthal investigation");
            int final_Row = oPage_InvestigationTab.rows_selectedOphthalInvestigations.size();
            m_assert.assertTrue(intial_Row - 1 == final_Row, "Validated Delete --> Ophthal investigations");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_InvestigationTab.input_searchOphthalInvestigation, oTemplate_Data.sSEARCH_OPHTHAL_INVESTIGATIONS), "Entered " + oTemplate_Data.sSEARCH_OPHTHAL_INVESTIGATIONS + " in search ophthal investigations");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.sendKeysIntoElement(oPage_InvestigationTab.input_searchOphthalInvestigation, "" + Keys.DOWN + Keys.ENTER);
            Cls_Generic_Methods.customWait(4);
            final_Row = oPage_InvestigationTab.rows_selectedOphthalInvestigations.size();
            m_assert.assertTrue(intial_Row == final_Row, "Validated Search --> New investigations added");
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, oPage_InvestigationTab.radioBtn_customOpthalInvestigation), "Custom Ophthal Investigation button is clickable");
            //LABORATORY
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_InvestigationTab.tab_laboratoryUnderInvestigationTab), "Clicked Laboratory Investigation");
            m_assert.assertTrue(Cls_Generic_Methods.radioButtonIsSelected(oPage_InvestigationTab.radioBtn_standardLaboratoryInvestigation), "Standard Laboratory Investigation is selected by default");
            m_assert.assertInfo(selectValueFromSet(oPage_InvestigationTab.select_laboratorySetsUnderInvestigations, oTemplate_Data.sLABORATORY_SET), "Selected <b>" + oTemplate_Data.sLABORATORY_SET + "</b> under Laboratory set");
            Cls_Generic_Methods.customWait(4);
            int selectedLaboratoryInvestigations = oPage_InvestigationTab.rows_selectedLaboratoryInvestigations.size();
            m_assert.assertTrue(selectedLaboratoryInvestigations > 0, "Validate at least one Investigation is selected. Current count of Laboratory investigations = " + selectedLaboratoryInvestigations);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, oPage_InvestigationTab.radioBtn_customLaboratoryInvestigation), "Custom Laboratory Investigation button is clickable");
            //RADIOLOGY
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_radiologyUnderInvestigationTab), "Radiology in Investigation Tab Is selected");
            m_assert.assertTrue(Cls_Generic_Methods.radioButtonIsSelected(oPage_InvestigationTab.radioBtn_standardRadiologyInvestigation), "Standard Laboratory Investigation is selected by default");
            m_assert.assertTrue(selectValueFromSet(oPage_InvestigationTab.select_radiologySetsUnderInvestigations, oTemplate_Data.sRADIOLOGY_SET), "Selected <b>" + oTemplate_Data.sRADIOLOGY_SET + "</b> under Radiology set");
            Cls_Generic_Methods.customWait(4);
            int selectedRadiologyInvestigations = oPage_InvestigationTab.rows_selectedRadiologyInvestigations.size();
            m_assert.assertTrue(selectedRadiologyInvestigations > 0, "Validate at least one Investigation is selected. Current count of Radiology investigations = " + selectedRadiologyInvestigations);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, oPage_InvestigationTab.radioBtn_customRadiologyInvestigation), "Custom Radiology Investigation button is clickable");
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate Investigation -->"+e);
            e.printStackTrace();
        }
    }
    public static void validateAndFillDiagnosis() {
        oTemplate_Data = new Template_Data();
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oPage_InvestigationTab = new Page_InvestigationTab(driver);
        oPage_DiagnosisTab = new Page_DiagnosisTab(driver);
        oPage_EyeTemplate = new Page_EyeTemplate(driver);
        try {
            sTEMPLATE_NAME = Cls_Generic_Methods.getTextInElement(oPage_HistoryTab.text_templateName).split(" Template ")[0];
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_DiagnosisTab.tab_diagnosis), "Diagnosis Tab Is selected");
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_DiagnosisTab.checkbox_provisionalDiagnosis), "Provisional diagnosis checkbox is clicked");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DiagnosisTab.input_provisionalDiagnosisComments, oTemplate_Data.sProvisionalDiagnosisComment), "Entered <b>" + oTemplate_Data.sProvisionalDiagnosisComment + "</b> in Provisional Diagnosis Comment");
            Cls_Generic_Methods.clickElement(oPage_DiagnosisTab.tab_diagnosis);
            //   m_assert.assertTrue(Cls_Generic_Methods.radioButtonIsSelected(oPage_DiagnosisTab.input_radioBtn_commonlyUsedDiagnosis), "Custom made Diagnosis is selected by default");
            for (WebElement radioBtnDiagnosis : oPage_DiagnosisTab.list_radioBtn_Diagnosis) {
                String value = Cls_Generic_Methods.getTextInElement(radioBtnDiagnosis.findElement(By.xpath("./following-sibling::label")));
                if (value.equalsIgnoreCase("Commonly Used Diagnosis")) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver, radioBtnDiagnosis), "<b>Commonly Used Diagnosis</b> button is clicked");
                }
            }
            m_assert.assertTrue(selectValueFromSet(oPage_DiagnosisTab.select_commonlyUsedDiagnosisUnderDiagnosis, "Glaucoma"), "Glaucoma" + " option is selected under Commonly Used Diagnosis Sets");
            Cls_Generic_Methods.customWait(4);
            m_assert.assertTrue(selectValueFromSet(oPage_DiagnosisTab.select_listUnderDiagnosis, "Pigmentary glaucoma"), "Steroid responder" + " option is selected under Commonly Used Diagnosis Sets");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DiagnosisTab.btn_save_diagnosis, 10);
            Cls_Generic_Methods.customWait(4);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_DiagnosisTab.select_diagnosisType, 1), "Right eye option is selected as Diagnosis side");
            Cls_Generic_Methods.customWait(4);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_DiagnosisTab.select_subDiagnosisType, 1), "Stage unspecified option is selected as Diagnosis stage");
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DiagnosisTab.btn_save_diagnosis), "Clicked Save diagnosis button");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DiagnosisTab.tab_diagnosis, 20);
            int selectedDiagnosis = oPage_DiagnosisTab.rows_selectedDiagnosis.size();
            m_assert.assertTrue(selectedDiagnosis > 0, "Validate at least one Diagnosis is selected. Current count of Diagnosis = " + selectedDiagnosis);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_DiagnosisTab.btn_editDiagnosis), "Clicked Edit on selected diagnosis");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DiagnosisTab.btn_updateDiagnosis, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_DiagnosisTab.btn_updateDiagnosis), "Clicked Update on selected diagnosis");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DiagnosisTab.btn_deleteDiagnosis, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_DiagnosisTab.btn_deleteDiagnosis), "Clicked Delete on selected diagnosis");
            Cls_Generic_Methods.customWait();
            int selectedDiagnosisAfterDelete = oPage_DiagnosisTab.rows_selectedDiagnosis.size();
            m_assert.assertTrue(selectedDiagnosisAfterDelete == 0, "Validated -->Delete Diagnosis functionality ");
            for (WebElement radioBtnDiagnosis : oPage_DiagnosisTab.list_radioBtn_Diagnosis) {
                String value = Cls_Generic_Methods.getTextInElement(radioBtnDiagnosis.findElement(By.xpath("./following-sibling::label")));
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, radioBtnDiagnosis), "<b>" + value + "</b> button in Diagnosis is clickable");
            }
            if (sTEMPLATE_NAME.equalsIgnoreCase("FREE FORM")) {
                Cls_Generic_Methods.clearValuesInElement(oPage_DiagnosisTab.input_provisionalDiagnosisComments);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_DiagnosisTab.input_provisionalDiagnosisComments, oTemplate_Data.sProvisionalDiagnosisComment), "Entered <b>" + oTemplate_Data.sProvisionalDiagnosisComment + "</b> in Provisional Diagnosis Comment");
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Error while validating Diagnosis. \n" + e);
        }
    }
    public static void validateAndFillAdvise() {
        oTemplate_Data = new Template_Data();
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        medicationSet = map_MedicationSetsInExcel.get(medicationSetKeyUsed);
        oPage_AdviceTab = new Page_AdviceTab(driver);
        oPage_EyeTemplate = new Page_EyeTemplate(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_OPD = new Page_OPD(driver);
        try {
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByIndex(oPage_HistoryTab.select_user_template, 1), Cls_Generic_Methods.getSelectedValue(oPage_HistoryTab.select_user_template) + " is selected as user");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_advice), "Advice Tab Is selected");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdviceTab.select_storeMedication, 10), "Medication Tab is selected by default");
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_AdviceTab.checkbox_noMedicationAdvised), "No Medication Advice check box is clicked");
            isValid_Header("MEDICATIONS");
            Cls_Generic_Methods.clickElementByJS(driver, oPage_AdviceTab.checkbox_noMedicationAdvised);
            //Manual Filling
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_AdviceTab.input_medicineName, medicationSet.getsMEDICATION_NAME()), "Entered <b>" + medicationSet.getsMEDICATION_NAME() + "</b> as Medicine Name");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_AdviceTab.input_medicineType, medicationSet.getsTYPE()), "Entered <b>" + medicationSet.getsTYPE() + "</b> as Medicine Type");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicineQuantity, medicationSet.getsQUANTITY()), "Selected <b>" + medicationSet.getsQUANTITY() + "</b> as Medicine Quantity");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicineFrequency, medicationSet.getsFREQUENCY()), "Selected <b>" + medicationSet.getsFREQUENCY() + "</b> as Medicine Frequency");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicineDurationCount, medicationSet.getsDURATION_COUNT()), "Selected <b>" + medicationSet.getsDURATION_COUNT() + "</b> as Medicine Duration Count");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicineDurationDays, medicationSet.getsDURATION_DAYS()), "Selected <b>" + medicationSet.getsDURATION_DAYS() + "</b> as Medicine Duration Days");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicineEyeSide, medicationSet.getsSIDE()), "Selected <b>" + medicationSet.getsSIDE() + "</b> as Eye Side");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicineInstruction, medicationSet.getsINSTRUCTION()), "Selected <b>" + medicationSet.getsINSTRUCTION() + "</b> as Medicine Instructions");
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_AdviceTab.select_medicineDeleteAction), "Clicked Delete Medicine for selected medicine");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_AdviceTab.input_medicineName, "value").isEmpty(), "Validated Delete Medicine functionality");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicationSetLevel, oTemplate_Data.sMEDICATION_SET_LEVEL), "Selected <b>" + oTemplate_Data.sMEDICATION_SET_LEVEL + "</b> in medication set level");
            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicationSets, oTemplate_Data.sMEDICATION_SET), "Selected " + oTemplate_Data.sMEDICATION_SET + " under " + oTemplate_Data.sMEDICATION_SET_LEVEL + " medication set");
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Error while validating Advice. \n" + e);
        }
        //PROCEDURE
        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_proceduresUnderAdviceTab), "Procedures Tab Is selected");
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_AdviceTab.checkbox_noProceduresAdvised), "No Procedures check box is checked");
            Cls_Generic_Methods.customWait();
            isValid_Header("Procedure");
            Cls_Generic_Methods.clickElementByJS(driver, oPage_AdviceTab.checkbox_noProceduresAdvised);
            m_assert.assertTrue(Cls_Generic_Methods.radioButtonIsSelected(oPage_AdviceTab.radioBtn_commonlyUsedProcedure), "Commonly Used Procedure is selected by default");
            m_assert.assertInfo(selectValueFromSet(oPage_AdviceTab.select_eyeRegionProcedure, oTemplate_Data.sEYE_REGION_PROCEDURE), "Selected <b>" + oTemplate_Data.sEYE_REGION_PROCEDURE + "</b> under Eye Region");
            Cls_Generic_Methods.customWait();
            m_assert.assertInfo(selectValueFromSet(oPage_AdviceTab.select_procedure, oTemplate_Data.sPROCEDURE), "Selected <b>" + oTemplate_Data.sPROCEDURE + "</b> under Procedure");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdviceTab.radioBtn_advisedProcedure, 10);
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_AdviceTab.input_procedureName, "value").contains(oTemplate_Data.sPROCEDURE), "Validated selected procedure --> " + oTemplate_Data.sPROCEDURE);
            m_assert.assertTrue(Cls_Generic_Methods.radioButtonIsSelected(oPage_AdviceTab.radioBtn_advisedProcedure), "Advised Procedure is selected by default");
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_AdviceTab.radioBtn_performedProcedure), "Performed procedure is clickable");
            Cls_Generic_Methods.clickElementByJS(driver, oPage_AdviceTab.radioBtn_advisedProcedure);
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_procedureSide, oTemplate_Data.sPROCEDURE_SIDE), "Selected <b>" + oTemplate_Data.sPROCEDURE_SIDE + "</b> under Procedure Side");
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.button_saveProcedure), "Save Procedure button is clicked");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_AdviceTab.list_rowSelectedProcedures, 10);
            int selectedProcedure = oPage_AdviceTab.list_rowSelectedProcedures.size();
            m_assert.assertTrue(selectedProcedure > 0, "Validate at least one Procedure is selected. Current count of Procedure = " + selectedProcedure);
            for (WebElement radioBtnProcedure : oPage_AdviceTab.list_radioBtnProcedures) {
                String value = Cls_Generic_Methods.getElementAttribute(radioBtnProcedure, "value").toUpperCase();
                if (!Cls_Generic_Methods.radioButtonIsSelected(radioBtnProcedure)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, radioBtnProcedure), "<b>" + value + "</b>_PROCEDURE is clickable");
                }
            }
            // Validate Referral Tab
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_referralUnderAdviceTab), "Referral Tab Is selected");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Referral. \n" + e);
            }
            // Validate Advice Tab
            try {
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_adviceUnderAdviceTab), "Advice Tab Is selected");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Error while validating Advice. \n" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Error while validating Procedures. \n" + e);
        }
    }
    public static void validateAndFillClinicalDetailsAssessment() {
        oTemplate_Data = new Template_Data();
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
        oPage_InvestigationTab = new Page_InvestigationTab(driver);
        oPage_ClinicalDetailsAndAssessmentTab = new Page_ClinicalDetailsAndAssessmentTab(driver);
        oPage_DiagnosisTab = new Page_DiagnosisTab(driver);
        try {
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ClinicalDetailsAndAssessmentTab.tab_ClinicalDetailsAndAssessment),
                    "Clinical Details & Assessment Tab is clicked");
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to click Clinical Details & Assessment Tab");
        }
        try {
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ClinicalDetailsAndAssessmentTab.input_clinicalDetails, "Squint R Eye"), "Entered Squint R Eye in clinical details");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.sendKeysIntoElement(oPage_ClinicalDetailsAndAssessmentTab.input_clinicalDetails, "" + Keys.ENTER);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ClinicalDetailsAndAssessmentTab.tab_diagnosisUnderAssessmentTab),
                    "Diagnosis Tab is clicked");
            for (WebElement radioBtnDiagnosis : oPage_DiagnosisTab.list_radioBtn_Diagnosis) {
                String value = Cls_Generic_Methods.getTextInElement(radioBtnDiagnosis.findElement(By.xpath("./following-sibling::label")));
                if (value.equalsIgnoreCase("Commonly Used Diagnosis")) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver, radioBtnDiagnosis), "<b>Commonly Used Diagnosis</b> button is clicked");
                }
            }
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_DiagnosisTab.select_commonlyUsedDiagnosisUnderDiagnosis, "Glaucoma"), "Glaucoma" + " option is selected under Commonly Used Diagnosis Sets");
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_DiagnosisTab.select_listUnderDiagnosis, "Pigmentary glaucoma"), "Steroid responder" + " option is selected under Commonly Used Diagnosis Sets");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DiagnosisTab.btn_save_diagnosis, 10);
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_DiagnosisTab.select_diagnosisType, 1), "Right eye option is selected as Diagnosis side");
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByIndex(oPage_DiagnosisTab.select_subDiagnosisType, 1), "Stage unspecified option is selected as Diagnosis stage");
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_DiagnosisTab.btn_save_diagnosis), "Clicked Save diagnosis button");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DiagnosisTab.tab_diagnosis, 20);
            int selectedDiagnosis = oPage_DiagnosisTab.rows_selectedDiagnosis.size();
            m_assert.assertTrue(selectedDiagnosis > 0, "Validate at least one Diagnosis is selected. Current count of Diagnosis = " + selectedDiagnosis);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_DiagnosisTab.btn_editDiagnosis), "Clicked Edit on selected diagnosis");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_DiagnosisTab.btn_updateDiagnosis, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_DiagnosisTab.btn_updateDiagnosis), "Clicked Update on selected diagnosis");
            Cls_Generic_Methods.customWait();
            for (WebElement radioBtnDiagnosis : oPage_DiagnosisTab.list_radioBtn_Diagnosis) {
                String value = Cls_Generic_Methods.getTextInElement(radioBtnDiagnosis.findElement(By.xpath("./following-sibling::label")));
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, radioBtnDiagnosis), "<b>" + value + "</b> button in Diagnosis is clickable");
            }
            //......Investigation
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_InvestigationTab.tab_ophthalUnderInvestigationTab),
                    "Ophthal Tab is clicked");
            m_assert.assertTrue(Cls_Generic_Methods.radioButtonIsSelected(oPage_InvestigationTab.radioBtn_standardOpthalInvestigation), "Standard Ophthal Investigation is selected by default");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_InvestigationTab.select_ophthalSetsUnderInvestigations, oTemplate_Data.sOPHTHAL_SET), "Selected <b>" + oTemplate_Data.sOPHTHAL_SET + "</b> under Ophthal set");
            Cls_Generic_Methods.customWait(3);
            int index = 0;
            int intial_Row = oPage_InvestigationTab.rows_selectedOphthalInvestigations.size();
            while (index < intial_Row) {
                String investigation_value = Cls_Generic_Methods.getElementAttribute(oPage_InvestigationTab.list_inputSelectedOphthalInvestigations.get(index), "value");
                int advisedBtnIndex = 1;
                while (advisedBtnIndex > -1) {
                    WebElement btn = oPage_InvestigationTab.list_radioAdvisedPerformedOphthalInvestigations(index + 1).get(advisedBtnIndex);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, btn), "Clicked <b>" + advisedOrPerformed(btn) + "</b> in " + investigation_value);
                    if (advisedOrPerformed(btn).equals("Performed")) {
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_InvestigationTab.input_findingsPerformed, oTemplate_Data.sFINDINGS_PERFORMED), "Entered " + oTemplate_Data.sFINDINGS_PERFORMED + " in " + investigation_value + " Findings Performed");
                    }
                    advisedBtnIndex--;
                }
                index++;
            }
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_InvestigationTab.list_deleteSelectedOphthalInvestigations.get(index - 1)), "Clicked Delete in ophthal investigation");
            int final_Row = oPage_InvestigationTab.rows_selectedOphthalInvestigations.size();
            m_assert.assertTrue(intial_Row - 1 == final_Row, "Validated Delete --> Ophthal investigations");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_InvestigationTab.input_searchOphthalInvestigation, oTemplate_Data.sSEARCH_OPHTHAL_INVESTIGATIONS), "Entered " + oTemplate_Data.sSEARCH_OPHTHAL_INVESTIGATIONS + " in search ophthal investigations");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.sendKeysIntoElement(oPage_InvestigationTab.input_searchOphthalInvestigation, "" + Keys.DOWN + Keys.ENTER);
            Cls_Generic_Methods.customWait();
            final_Row = oPage_InvestigationTab.rows_selectedOphthalInvestigations.size();
            m_assert.assertTrue(intial_Row == final_Row, "Validated Search --> New investigations added");
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, oPage_InvestigationTab.radioBtn_customOpthalInvestigation), "Custom Ophthal Investigation button is clickable");
            //LABORATORY
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_InvestigationTab.tab_laboratoryUnderInvestigationTab), "Clicked Laboratory Investigation");
            m_assert.assertTrue(Cls_Generic_Methods.radioButtonIsSelected(oPage_InvestigationTab.radioBtn_standardLaboratoryInvestigation), "Standard Laboratory Investigation is selected by default");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_InvestigationTab.select_laboratorySetsUnderInvestigations, oTemplate_Data.sLABORATORY_SET), "Selected <b>" + oTemplate_Data.sLABORATORY_SET + "</b> under Laboratory set");
            Cls_Generic_Methods.customWait();
            int selectedLaboratoryInvestigations = oPage_InvestigationTab.rows_selectedLaboratoryInvestigations.size();
            m_assert.assertTrue(selectedLaboratoryInvestigations > 0, "Validate at least one Investigation is selected. Current count of Laboratory investigations = " + selectedLaboratoryInvestigations);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, oPage_InvestigationTab.radioBtn_customLaboratoryInvestigation), "Custom Laboratory Investigation button is clickable");
            //RADIOLOGY
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_InvestigationTab.tab_radiologyUnderInvestigationTab), "Radiology in Investigation Tab Is selected");
            m_assert.assertTrue(Cls_Generic_Methods.radioButtonIsSelected(oPage_InvestigationTab.radioBtn_standardRadiologyInvestigation), "Standard Laboratory Investigation is selected by default");
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_InvestigationTab.select_radiologySetsUnderInvestigations, oTemplate_Data.sRADIOLOGY_SET), "Selected <b>" + oTemplate_Data.sRADIOLOGY_SET + "</b> under Radiology set");
            Cls_Generic_Methods.customWait();
            int selectedRadiologyInvestigations = oPage_InvestigationTab.rows_selectedRadiologyInvestigations.size();
            m_assert.assertTrue(selectedRadiologyInvestigations > 0, "Validate at least one Investigation is selected. Current count of Radiology investigations = " + selectedRadiologyInvestigations);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, oPage_InvestigationTab.radioBtn_customRadiologyInvestigation), "Custom Radiology Investigation button is clickable");
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate Clinical Details & Assessment" + e);
            e.printStackTrace();
        }
    }
    private static boolean moveSlider(WebElement sliderEle, String value, String side) {
        int valueInInt;
        boolean flag = false;
        try {
            valueInInt = Integer.parseInt(value);
        } catch (Exception e) {
            valueInInt = 0;
        }
        try {
            side = side.toLowerCase();
            for (int i = 0; i < valueInInt; i++) {
                if (side.equals("l") || side.equals("left")) {
                    Cls_Generic_Methods.sendKeysIntoElement(sliderEle, String.valueOf(Keys.LEFT));
                    flag = true;
                } else {
                    sliderEle.sendKeys(Keys.RIGHT);
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    private static boolean setTime(WebElement timeElement, String time) {
        boolean flag = false;
        try {
            Cls_Generic_Methods.clickElementByJS(driver,
                    timeElement);
            String[] seperatedTimeValue = time.split(":");
            Cls_Generic_Methods
                    .clearValuesInElement(oPage_NewPatientRegisteration.input_appointmentHourForAppointDetails);
            Cls_Generic_Methods.sendKeysIntoElement(
                    oPage_NewPatientRegisteration.input_appointmentHourForAppointDetails,
                    seperatedTimeValue[0]);
            Cls_Generic_Methods.clearValuesInElement(
                    oPage_NewPatientRegisteration.input_appointmentMinuteForAppointDetails);
            Cls_Generic_Methods.sendKeysIntoElement(
                    oPage_NewPatientRegisteration.input_appointmentMinuteForAppointDetails,
                    seperatedTimeValue[1]);
            Cls_Generic_Methods.clearValuesInElement(
                    oPage_NewPatientRegisteration.input_appointmentMeridianForAppointDetails);
            Cls_Generic_Methods.sendKeysIntoElement(
                    oPage_NewPatientRegisteration.input_appointmentMeridianForAppointDetails,
                    seperatedTimeValue[2]);
            flag = true;
        } catch (Exception e) {
            m_assert.assertFatal("Unable to set Time " + e);
            e.printStackTrace();
        }
        return flag;
    }
    private static boolean fillRefractionTable() {
        boolean flag = false;
        try {
            clickButtonInFillRefraction(oPage_RefractionTab.list_btnSphDistant, oTemplate_Data.sSphDistant);
            clickButtonInFillRefraction(oPage_RefractionTab.list_btnCylDistant, oTemplate_Data.sCylDistant);
            clickButtonInFillRefraction(oPage_RefractionTab.list_btnAxisDistant, oTemplate_Data.sAxisDistant);
            clickButtonInFillRefraction(oPage_RefractionTab.list_btnVisionDistant, oTemplate_Data.sVisionDistant);
            clickButtonInFillRefraction(oPage_RefractionTab.list_btnSphAdd, oTemplate_Data.sSphAdd);
            clickButtonInFillRefraction(oPage_RefractionTab.list_btnVisionNear, oTemplate_Data.sVisionNear);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    private static void clickButtonInFillRefraction(List<WebElement> allBtn, String btnToSelect) {
        try {
            if (btnToSelect.charAt(0) == '-') {
                Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_minus);
            } else if (btnToSelect.charAt(0) == '+') {
                Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_plus);
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(allBtn.get(0), 10);
            for (WebElement btn : allBtn) {
                String btnValue = Cls_Generic_Methods.getTextInElement(btn);
                String btnType = Cls_Generic_Methods.getElementAttribute(btn, "name").replaceAll("refraction_", "").toUpperCase();
                if (btnValue.contains(btnToSelect)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn), "Clicked <b>" + btnValue + "</b> in " + btnType);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static boolean validateRightEqualLeftInRefraction() {
        boolean flag = false;
        try {
            ArrayList<String> rightValue = new ArrayList<>();
            ArrayList<String> leftValue = new ArrayList<>();
            for (WebElement inputRight : oPage_RefractionTab.list_inputRightTableValuesFillRefraction) {
                if (inputRight.isEnabled() || !Cls_Generic_Methods.getTextInElement(inputRight).isEmpty()) {
                    String value = Cls_Generic_Methods.getElementAttribute(inputRight, "value");
                    rightValue.add(value);
                }
            }
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_rightEqualLeftFillRefraction), "Clicked Right=Left Button");
            for (WebElement inputLeft : oPage_RefractionTab.list_inputLeftTableValuesFillRefraction) {
                if (inputLeft.isEnabled() || !Cls_Generic_Methods.getTextInElement(inputLeft).isEmpty()) {
                    String value = Cls_Generic_Methods.getElementAttribute(inputLeft, "value");
                    leftValue.add(value);
                }
            }
            if (rightValue.size() == leftValue.size()) {
                int index = 0;
                for (String value : leftValue) {
                    if (value.equals(rightValue.get(index))) {
                        flag = true;
                    } else {
                        flag = false;
                        break;
                    }
                    index++;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to Validate RightEqualLeft");
        }
        return flag;
    }
    private static void clickAndValidateCopyToGlassPrescription(WebElement copyBtn) {
        ArrayList<String> fromTableCopyValues = new ArrayList<>();
        ArrayList<String> toTableCopyValues = new ArrayList<>();
        boolean copyStatus = false;
        String fromTableName;
        try {
            Cls_Generic_Methods.clickElement(copyBtn);
            fromTableName = Cls_Generic_Methods.getElementAttribute(copyBtn.findElement(By.xpath("./parent::span")), "title").replaceAll("Copy ", "").replaceAll(" to Glass Prescription", "");
            Cls_Generic_Methods.customWait(5);
            for (WebElement input : oPage_RefractionTab.getListInputTable(fromTableName)) {
                String value = Cls_Generic_Methods.getElementAttribute(input, "value");
                if (Cls_Generic_Methods.isElementEnabled(input) && !value.isEmpty()) {
                    fromTableCopyValues.add(value);
                }
            }
            for (WebElement input : oPage_RefractionTab.list_inputGlassPrescriptions) {
                String value = Cls_Generic_Methods.getElementAttribute(input, "value");
                if (Cls_Generic_Methods.isElementEnabled(input) && !value.isEmpty()) {
                    toTableCopyValues.add(value);
                }
            }
            System.out.println(fromTableCopyValues + "  " + toTableCopyValues);
            if (fromTableCopyValues.size() == toTableCopyValues.size()) {
                for (int i = 0; i < fromTableCopyValues.size(); i++) {
                    if (fromTableCopyValues.get(i).equals(toTableCopyValues.get(i))) {
                        copyStatus = true;
                    } else {
                        copyStatus = false;
                        break;
                    }
                }
            }
            System.out.println();
            m_assert.assertTrue(copyStatus, "Validated copy " + fromTableName + " to glass prescription");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static boolean clearGlassPrescriptionValues() {
        boolean flag = false;
        try {
            for (WebElement input : oPage_RefractionTab.list_inputGlassPrescriptions) {
                if (!Cls_Generic_Methods.getElementAttribute(input, "value").isEmpty()) {
                    Cls_Generic_Methods.clearValuesInElement(input);
                    flag = true;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to clear values in Glass prescription");
        }
        return flag;
    }
    public static String findLeftOrRightFieldName(WebElement element) {
        String text = null;
        try {
            text = Cls_Generic_Methods.getElementAttribute(element, "name");
            if (text == null) {
                try {
                    WebElement ele = element.findElement(By.xpath("./ancestor::div[@class='input']/textarea"));
                    text = Cls_Generic_Methods.getElementAttribute(ele, "name");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (text.contains("[")) {
                text = text.substring(text.indexOf("[") + 1, text.indexOf("]")).replaceAll("_", " ").toUpperCase();
            } else {
                text = text.replaceAll("_", " ").toUpperCase();
            }
            if (text.charAt(0) == 'L') {
                text = text.replaceFirst("L", "LEFT");
            } else {
                text = text.replaceFirst(String.valueOf(text.charAt(0)), "RIGHT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
    public static boolean isButtonSelected(WebElement buttonElement) {
        boolean flag = false;
        try {
            if (Cls_Generic_Methods.getElementAttribute(buttonElement, "class").contains("btn-brown") || Cls_Generic_Methods.getElementAttribute(buttonElement, "class").contains("btn-darkblue")) {
                flag = true;
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to find selected button");
        }
        return flag;
    }
    private static void expand_Examination(String fieldNameToExpand) {
        for (WebElement expandBtn : oPage_ExaminationTab.button_expandExamination(fieldNameToExpand)) {
            try {
                Cls_Generic_Methods.clickElement(expandBtn);
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Unable to expand " + fieldNameToExpand + " tab");
            }
        }
    }
    private static void expandAll_Examination() {
        expand_Examination("appearance");
        expand_Examination("injury");
        expand_Examination("appendages");
        expand_Examination("conjunctiva");
        expand_Examination("sclera");
        expand_Examination("cornea");
        expand_Examination("anteriorchamber");
        expand_Examination("pupil");
        expand_Examination("iris");
        expand_Examination("lens");
        expand_Examination("extraocular");
        expand_Examination("IOP");
        expand_Examination("gonioscopy");
        expand_Examination("fundus");
        if (sTEMPLATE_NAME.equalsIgnoreCase("TRAUMA")) {
            expand_Examination("trauma");
        }
    }
    private static void validateBtnExamination(List<WebElement> listOfBtnElements) {
        try {
            for (WebElement btn : listOfBtnElements) {
                String value = Cls_Generic_Methods.getTextInElement(btn);
                if (isButtonSelected(btn)) {
                    if (oTemplate_Data.sSELECTED_BTN_EXAMINATION.contains(value)) {
                        m_assert.assertTrue("<b>" + value + "</b> button is selected by default in " + findLeftOrRightFieldName(btn));
                    } else {
                        m_assert.assertFatal("<b>" + value + "</b> button should not be selected by default in " + findLeftOrRightFieldName(btn));
                    }
                } else {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, btn), "<b>" + value + "</b> button is clickable in " + findLeftOrRightFieldName(btn));
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate Yes or No button");
        }
    }
    private static boolean validateResetBtnExamination(List<WebElement> listOfBtnElements) {
        boolean flag = true;
        try {
            for (WebElement btn : listOfBtnElements) {
                String value = Cls_Generic_Methods.getTextInElement(btn);
                if (isButtonSelected(btn) && Cls_Generic_Methods.isElementDisplayed(btn)) {
                    if (!oTemplate_Data.sSELECTED_BTN_EXAMINATION.contains(value)) {
                        flag = false;
                        m_assert.assertFatal("Unable to reset <b>" + value + "</b> button in " + findLeftOrRightFieldName(btn));
                    }
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate button " + e);
        }
        return flag;
    }
    private static boolean validateResetTextBox(List<WebElement> listOfBtnElements) {
        boolean flag = false;
        for (WebElement input : listOfBtnElements) {
            try {
                if (!Cls_Generic_Methods.getElementAttribute(input, "value").isEmpty()) {
                    m_assert.assertFatal("Unable to reset textbox in " + findLeftOrRightFieldName(input));
                    flag = false;
                } else {
                    flag = true;
                }
            } catch (NullPointerException n) {
                flag = true;
            } catch (Exception e) {
                m_assert.assertFatal("Unable to validate reset text " + e);
            }
        }
        return flag;
    }
    private static boolean validateSelectExamination(List<WebElement> listOfBtnElements) {
        boolean flag = true;
        for (WebElement selectEle : listOfBtnElements) {
            if (selectEle.isSelected()) {
                try {
                    flag = false;
                    m_assert.assertFatal("Unable to validate reset all -->" + findLeftOrRightFieldName(selectEle));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
    public static void clickAndValidateDiagram(WebElement buttonDiagram) {
        String value = null;
        try {
            oPage_ExaminationTab = new Page_ExaminationTab(driver);
            value = Cls_Generic_Methods.getTextInElement(buttonDiagram);
            String fieldValue = Cls_Generic_Methods.getTextInElement(buttonDiagram).replaceAll("Add", "").replaceAll("Diagram", "").trim();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(buttonDiagram), "Clicked <b>" + value + "</b> in " + fieldValue);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ExaminationTab.btn_saveDiagram, 20);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ExaminationTab.btn_saveDiagram), "Clicked Save Diagram button");
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ExaminationTab.img_diagram, 10), "Validated --> Diagram saved in " + fieldValue);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ExaminationTab.btn_editDiagram, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ExaminationTab.btn_editDiagram), "Clicked Edit Diagram button in " + fieldValue);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ExaminationTab.btn_cancelDiagram, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ExaminationTab.btn_cancelDiagram), "Clicked Cancel Diagram button");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ExaminationTab.btn_deleteDiagram, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ExaminationTab.btn_deleteDiagram), "Clicked Delete Diagram button in " + fieldValue);
            Cls_Generic_Methods.customWait(2);
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_ExaminationTab.img_diagram)) {
                m_assert.assertTrue("Validated -->Diagram deleted in " + fieldValue);
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate " + value + "   " + e);
            e.printStackTrace();
        }
    }
    private static boolean isValid_Header(String fieldToValidate) {
        boolean flag = false;
        if (fieldToValidate.equalsIgnoreCase("Glasses/Lens adviced")) {
            fieldToValidate = "glasses";
        }
        if (fieldToValidate.equalsIgnoreCase("Procedure(s)")) {
            fieldToValidate = "procedure";
        }
        if (fieldToValidate.equalsIgnoreCase("follow-up")) {
            fieldToValidate = "followup";
        }
        try {
            WebElement ele = driver.findElement(By.xpath("//li[@id='" + fieldToValidate.toLowerCase() + "_is_valid']"));
            if (Cls_Generic_Methods.getElementAttribute(ele, "class").contains("my-success")) {
                m_assert.assertTrue("Validate Header --> " + fieldToValidate.toUpperCase() + " success");
            }
        } catch (Exception e) {
            m_assert.assertFatal("Enter valid Header");
        }
        return flag;
    }
    private static String advisedOrPerformed(WebElement btn) {
        String value = null;
        try {
            value = Cls_Generic_Methods.getElementAttribute(btn, "value");
            if (value.equals("P")) {
                value = "Performed";
            } else {
                value = "Advised";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
    public static boolean validateFilledTodayTemplate(String... template) {
        boolean status = false;
        oPage_OPD = new Page_OPD(driver);
        String templateName = null;
        try {
            if (template.length > 0) {
                templateName = template[0];
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate,10);
            System.out.println("TOTAL TODAY'S TEMPLATE -->" + oPage_OPD.list_todayFilledTemplates.size());
            if (oPage_OPD.list_todayFilledTemplates.size() > 0) {
                if (templateName == null) {
                    status = true;
                } else {
                    for (WebElement btn_template : oPage_OPD.list_todayFilledTemplates) {
                        String value = Cls_Generic_Methods.getTextInElement(btn_template).split(" | ")[0];
                        if (templateName.equalsIgnoreCase(value)) {
                            status = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate filled today's template" + e);
        }
        return status;
    }
    private static void validateRefraction_VISUAL_ACUITY() {
        try {
            if (sTEMPLATE_NAME.equalsIgnoreCase("FREE FORM")) {
                validateAndFill_ListOfInput(oPage_RefractionTab.list_inputVisualAcuity_unaided_FreeForm, "1/50");
                validateAndFill_ListOfInput(oPage_RefractionTab.list_inputVisualAcuity_unaided_near_FreeForm, "1/5");
                validateAndFill_ListOfInput(oPage_RefractionTab.list_inputVisualAcuity_unaided_pr_FreeForm, "1/2");
                validateAndFill_ListOfInput(oPage_RefractionTab.list_inputVisualAcuity_pinhole_FreeForm, "1/60");
                validateAndFill_ListOfInput(oPage_RefractionTab.list_inputVisualAcuity_glasses_FreeForm, "5");
                validateAndFill_ListOfInput(oPage_RefractionTab.list_inputVisualAcuity_glassesNear_FreeForm, "10");
                validateAndFill_ListOfInput(oPage_RefractionTab.list_inputVisualAcuity_contactLens_FreeForm, "1/2");
                validateAndFill_ListOfInput(oPage_RefractionTab.list_inputComments_VisualAcuity_FreeForm, oTemplate_Data.sCOMMENT_VISUAL_ACUITY);
                for (WebElement clearBtn : oPage_RefractionTab.list_buttonVisualAcuity_clear_FreeForm) {
                    Cls_Generic_Methods.clickElementByJS(driver, clearBtn);
                }
                m_assert.assertInfo("Clear button is clicked in Visual Acuity");
                m_assert.assertInfo(validateResetTextBox(oPage_RefractionTab.list_inputVisualAcuity_unaided_FreeForm), "Validated clear functionality --> UCVA Visual acuity");
                m_assert.assertInfo(validateResetTextBox(oPage_RefractionTab.list_inputVisualAcuity_unaided_near_FreeForm), "Validated clear functionality --> UCVA Near Visual acuity");
                m_assert.assertInfo(validateResetTextBox(oPage_RefractionTab.list_inputVisualAcuity_pinhole_FreeForm), "Validated clear functionality --> Pinhole Visual acuity");
                m_assert.assertInfo(validateResetTextBox(oPage_RefractionTab.list_inputVisualAcuity_glasses_FreeForm), "Validated clear functionality --> Glasses Visual acuity");
                m_assert.assertInfo(validateResetTextBox(oPage_RefractionTab.list_inputVisualAcuity_glassesNear_FreeForm), "Validated clear functionality --> Glasses Near Visual acuity");
                m_assert.assertInfo(validateResetTextBox(oPage_RefractionTab.list_inputVisualAcuity_contactLens_FreeForm), "Validated clear functionality --> Contact Lens Visual acuity");
                throw new Exception("FILLED");
            }
            try {
                Cls_Generic_Methods.clickElement(oPage_RefractionTab.getBtnsVisualAcuity("r").get(0));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(!oPage_RefractionTab.checkbox_rightVA_notExamined.isEnabled(), "Validated R/OD not examined button in visual acuity");
                Cls_Generic_Methods.clickElement(oPage_RefractionTab.getBtnsVisualAcuity("l").get(0));
                Cls_Generic_Methods.customWait();
                m_assert.assertTrue(!oPage_RefractionTab.checkbox_leftVA_notExamined.isEnabled(), "Validated L/OS not examined button in visual acuity");
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("Unable to validate visual acuity not examined button" + e);
            }
            boolean btnClickable = false;
            for (WebElement btn_visualAcuity : oPage_RefractionTab.list_allBtnVisualAcuity) {
                try {
                    Cls_Generic_Methods.clickElementByAction(driver, btn_visualAcuity);
                    btnClickable = true;
                } catch (Exception e) {
                    btnClickable = false;
                    break;
                }
            }
            m_assert.assertTrue(btnClickable, "Validated all buttons in Visual Acuity are clickable");
            int commentCount = 1;
            try {
                for (WebElement input_CommentVisualAcuity : oPage_RefractionTab.list_inputAllCommentsVisualAcuity) {
                    String sComment = oTemplate_Data.sCOMMENT_VISUAL_ACUITY + commentCount;
                    String sCommentBoxType = Cls_Generic_Methods.getElementAttribute(input_CommentVisualAcuity, "id").replaceAll("opdrecord_", "").replaceAll("_", " ").toUpperCase();
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(input_CommentVisualAcuity, sComment), "Entered <b>" + sComment + "</b> in " + sCommentBoxType);
                    commentCount++;
                }
            } catch (Exception e) {
                m_assert.assertFalse("Unable to validate visual acuity comments" + e);
                e.printStackTrace();
            }
            for (WebElement selectPR : oPage_RefractionTab.list_selectPRvisualAcuity) {
                try {
                    String sSelectPRType = Cls_Generic_Methods.getElementAttribute(selectPR, "id").replaceAll("opdrecord_", "").replaceAll("_", " ").toUpperCase();
                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByValue(selectPR, "+"), "Selected " + "<b>+</b> in " + sSelectPRType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                for (WebElement btnClear : oPage_RefractionTab.list_btnClearVisualAcuity) {
                    String sClearType = Cls_Generic_Methods.getElementAttribute(btnClear, "id").replaceAll("clear", "").replaceAll("_", " ").toUpperCase();
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, btnClear), "Clicked clear button in " + sClearType);
                }
            } catch (Exception e) {
                m_assert.assertFalse("Unable to validate clear functionality in visual acuity");
                e.printStackTrace();
            }
            //Validate Clear
            boolean clearStatus = true;
            try {
                for (WebElement btn_visualAcuity : oPage_RefractionTab.list_allBtnVisualAcuity) {
                    if (Cls_Generic_Methods.getElementAttribute(btn_visualAcuity, "class").contains("btn-brown")) {
                        clearStatus = false;
                        break;
                    }
                }
                for (WebElement commentVisual : oPage_RefractionTab.list_inputAllCommentsVisualAcuity) {
                    if (!Cls_Generic_Methods.getElementAttribute(commentVisual, "value").isEmpty()) {
                        clearStatus = false;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            m_assert.assertTrue(clearStatus, "Validated clear functionality in visual acuity");
            //Right To Left Visual Acuity
            String sROD_value = oTemplate_Data.sROD_VISUAL_ACUITY;
            for (WebElement rightBtn : oPage_RefractionTab.getBtnsVisualAcuity("r")) {
                try {
                    String rightBtnText = Cls_Generic_Methods.getTextInElement(rightBtn);
                    String buttonType = Cls_Generic_Methods.getElementAttribute(rightBtn, "name").toUpperCase();
                    if (sROD_value.equals(rightBtnText)) {
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(rightBtn), "Clicked " + sROD_value + " in " + buttonType);
                        rod_VisualAcuity.add(rightBtnText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Unable to click " + sROD_value + " in R/OD Visual Acuity");
                }
            }
            try {
                int rCommentIndex = 0;
                for (WebElement inputRComment : oPage_RefractionTab.getInputCommentsVisualAcuity("r")) {
                    String buttonType = Cls_Generic_Methods.getElementAttribute(inputRComment, "id").replaceAll("opdrecord_", "").replaceAll("_", " ").toUpperCase();
                    String comment = "TEST" + rCommentIndex;
                    m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(inputRComment, comment), "Entered " + comment + " in " + buttonType);
                    rod_comment_VisualAcuity.add(comment);
                    rCommentIndex++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_copyRightToLeft_visualAcuity), "Clicked copy Right to Left in visual acuity");
                for (WebElement leftBtn : oPage_RefractionTab.getBtnsVisualAcuity("l")) {
                    if (Cls_Generic_Methods.getElementAttribute(leftBtn, "class").contains("btn-brown") || Cls_Generic_Methods.getElementAttribute(leftBtn, "class").contains("btn-darkblue")) {
                        String leftSelectedBtnText = Cls_Generic_Methods.getTextInElement(leftBtn);
                        lod_VisualAcuity.add(leftSelectedBtnText);
                    }
                }
                for (WebElement input_lComment : oPage_RefractionTab.getInputCommentsVisualAcuity("l")) {
                    String lComment = Cls_Generic_Methods.getElementAttribute(input_lComment, "value");
                    lod_comment_VisualAcuity.add(lComment);
                }
                int rodLodIndex = 0;
                boolean copyStatus = true;
                for (String rod : rod_VisualAcuity) {
                    if (!rod.equals(lod_VisualAcuity.get(rodLodIndex))) {
                        copyStatus = false;
                        break;
                    }
                    rodLodIndex++;
                }
                int commentIndex=0;
                for (String rodComment : rod_comment_VisualAcuity) {
                    if (!rodComment.equals(lod_comment_VisualAcuity.get(commentIndex))) {
                        copyStatus = false;
                        break;
                    }
                    commentIndex++;
                }
                m_assert.assertTrue(copyStatus, "Validated Copy Right to Left in Visual Acuity");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Clear R/OD
            try {
                for (WebElement clearBtnRod : oPage_RefractionTab.getBtnsClearVisualAcuity("r")) {
                    Cls_Generic_Methods.clickElementByAction(driver, clearBtnRod);
                }
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_copyLeftToRight_visualAcuity), "Clicked copy Left to Right in visual acuity");
                rod_VisualAcuity.clear();
                rod_comment_VisualAcuity.clear();
                for (WebElement rightBtn : oPage_RefractionTab.getBtnsVisualAcuity("r")) {
                    if (Cls_Generic_Methods.getElementAttribute(rightBtn, "class").contains("btn-brown") || Cls_Generic_Methods.getElementAttribute(rightBtn, "class").contains("btn-darkblue")) {
                        String rightSelectedBtnText = Cls_Generic_Methods.getTextInElement(rightBtn);
                        rod_VisualAcuity.add(rightSelectedBtnText);
                    }
                }
                for (WebElement input_rComment : oPage_RefractionTab.getInputCommentsVisualAcuity("r")) {
                    String rComment = Cls_Generic_Methods.getElementAttribute(input_rComment, "value");
                    rod_comment_VisualAcuity.add(rComment);
                }
                int rodLodIndex = 0;
                boolean copyStatus = true;
                for (String rod : rod_VisualAcuity) {
                    if (!rod.equals(lod_VisualAcuity.get(rodLodIndex)) || !rod_comment_VisualAcuity.get(rodLodIndex).equals(lod_comment_VisualAcuity.get(rodLodIndex))) {
                        copyStatus = false;
                        break;
                    }
                    rodLodIndex++;
                }
                m_assert.assertTrue(copyStatus, "Validated Copy Left to Right in Visual Acuity");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (oTemplate_Data.sVISUAL_ACUITY_NOT_EXAMINED) {
                for (WebElement clearBtn : oPage_RefractionTab.list_btnClearVisualAcuity) {
                    try {
                        Cls_Generic_Methods.clickElement(clearBtn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Cls_Generic_Methods.customWait(3);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_RefractionTab.checkbox_rightVA_notExamined), "Clicked R/OD visual acuity not examined");
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_RefractionTab.checkbox_leftVA_notExamined), "Clicked L/OD visual acuity not examined");
                } catch (Exception e) {
                    m_assert.assertFatal("Unable to click not examined button in visual acuity");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
        }
    }
    public static void validateRefraction_IOP() {
        oPage_RefractionTab = new Page_RefractionTab(driver);
        myPatient = TestBase.map_PatientsInExcel.get(patientKey);
        oTemplate_Data = new Template_Data();
        try {
            //Validating Clear
            Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightIop);
            Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_leftIop);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_rightIop, "0");
            Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_leftIop, "0");
            Cls_Generic_Methods.customWait();
            for (WebElement btnClear : oPage_RefractionTab.list_btnClearIOP) {
                Cls_Generic_Methods.clickElementByJS(driver, btnClear);
            }
            if (Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_rightIop, "value").isEmpty() & Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_leftIop, "value").isEmpty()) {
                m_assert.assertTrue("Validated IOP Clear button");
            }
            Cls_Generic_Methods.customWait();
            //Validating slider
            m_assert.assertInfo(moveSlider(oPage_RefractionTab.slider_iop("r"), myPatient.getsIOP_RIGHT(), "RIGHT"), "Entered Right IOP value as <b>" + myPatient.getsIOP_RIGHT() + "</b>");
            m_assert.assertInfo(moveSlider(oPage_RefractionTab.slider_iop("l"), myPatient.getsIOP_LEFT(), "RIGHT"), "Entered Left IOP value as <b>" + myPatient.getsIOP_LEFT() + "</b>");
            String valueInRightIopInput = Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_rightIop, "value");
            String valueInLeftIopInput = Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_leftIop, "value");
            m_assert.assertEquals(valueInRightIopInput, myPatient.getsIOP_RIGHT(), "Validated Right IOP slider");
            m_assert.assertEquals(valueInLeftIopInput, myPatient.getsIOP_LEFT(), "Validated Left IOP slider");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.list_inputCommentsIOP.get(0), oTemplate_Data.sIOP_COMMENT), "Entered <b>" + oTemplate_Data.sIOP_COMMENT + "</b> in R/OD IOP comment");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.list_inputCommentsIOP.get(1), oTemplate_Data.sIOP_COMMENT), "Entered <b>" + oTemplate_Data.sIOP_COMMENT + "</b> in L/OS IOP comment");
            //ADD IOP (MULTIPLE IOP)
            int totalIOP = 1;
            Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightIop);
            for (WebElement addIOP : oPage_RefractionTab.list_buttonAddIOP) {
                if (Cls_Generic_Methods.isElementDisplayed(addIOP)) {
                    String addIopNo = Cls_Generic_Methods.getElementAttribute(addIOP, "class");
                    addIopNo = String.valueOf(addIopNo.charAt(addIopNo.length() - 1));
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, addIOP), "Clicked Add IOP" + addIopNo);
                    totalIOP++;
                }
            }
            for (int i = 0; i < 4; i++) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.list_inputIOP("r").get(i), myPatient.getsIOP_RIGHT()), "Entered R/OD IOP" + (i + 1) + " value <b>" + myPatient.getsIOP_RIGHT() + "</b>");
                m_assert.assertInfo(setTime(oPage_RefractionTab.inputTimeIOP("r").get(i), oTemplate_Data.sIOP_TIME), "Entered R/OD IOP" + (i + 1) + " time as <b>" + oTemplate_Data.sIOP_TIME + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByValue(oPage_RefractionTab.selectIOP_method("r").get(i), oTemplate_Data.sIOP_METHOD), "Selected R/OS IOP" + (i + 1) + " method as <b>" + oTemplate_Data.sIOP_METHOD + "</b>");
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_RefractionTab.list_btnRightToLeftCopyIOP.get(i)), "Clicked copy right to left IOP" + (i + 1));
            }
            for (int i = 0; i < 4; i++) {
                m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.list_inputIOP("l").get(i), "value").equals(myPatient.getsIOP_RIGHT()), "Validated Copy Right to Left -->Entered L/OS IOP" + (i + 1) + " value <b>" + myPatient.getsIOP_RIGHT() + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_RefractionTab.selectIOP_method("l").get(i)).equals(oTemplate_Data.sIOP_METHOD), "Validated Copy Right to Left -->Selected L/OS IOP" + (i + 1) + " method as <b>" + oTemplate_Data.sIOP_METHOD + "</b>");
            }
            //remove iop
            for (WebElement removeIOP : oPage_RefractionTab.list_buttonRemoveIOP) {
                if (Cls_Generic_Methods.isElementDisplayed(removeIOP)) {
                    String addIopNo = Cls_Generic_Methods.getElementAttribute(removeIOP.findElement(By.xpath("./parent::span")), "id");
                    addIopNo = String.valueOf(addIopNo.charAt(addIopNo.length() - 1));
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, removeIOP), "Clicked Remove IOP" + addIopNo);
                    totalIOP--;
                }
            }
            m_assert.assertTrue(totalIOP == 1, "Validated --> Remove IOP functionality");
            if (oTemplate_Data.sIOP_NOT_EXAMINED) {
                for (WebElement btnClear : oPage_RefractionTab.list_btnClearIOP) {
                    Cls_Generic_Methods.clickElementByJS(driver, btnClear);
                }
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_RefractionTab.checkbox_rightIOP_notExamined), "Clicked R/OD IOP not examined");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_RefractionTab.checkbox_leftIOP_notExamined), "Clicked L/OS IOP not examined");
            } else {
                // To validate multiple iop in Examination
                Cls_Generic_Methods.clearValuesInElement(oPage_RefractionTab.input_rightIop);
                for (WebElement addIOP : oPage_RefractionTab.list_buttonAddIOP) {
                    if (Cls_Generic_Methods.isElementDisplayed(addIOP)) {
                        Cls_Generic_Methods.clickElementByJS(driver, addIOP);
                    }
                }
                for (int i = 0; i < 4; i++) {
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.list_inputIOP("r").get(i), myPatient.getsIOP_RIGHT());
                    setTime(oPage_RefractionTab.inputTimeIOP("r").get(i), oTemplate_Data.sIOP_TIME);
                    Cls_Generic_Methods.clickElement(oPage_RefractionTab.list_inputIOP("r").get(i));
                    Cls_Generic_Methods.selectElementByValue(oPage_RefractionTab.selectIOP_method("r").get(i), oTemplate_Data.sIOP_METHOD);
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_RefractionTab.list_btnRightToLeftCopyIOP.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate IOP" + e);
        }
    }
    private static void validateRefraction_OCCULAR_ASSESSMENT() {
        try {
            for (WebElement inputOccular : oPage_RefractionTab.list_inputOccularAssessment) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputOccular, oTemplate_Data.sOCCULAR_ASSESSMENT), "Entered " + oTemplate_Data.sOCCULAR_ASSESSMENT + " in OCCULAR ASSESSMENT-->" + findLeftOrRightFieldName(inputOccular));
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate OCCULAR ASSESSMENT" + e);
        }
    }
    private static void validateRefraction_AUTO_REFRACTION() {
        try {
            boolean autoStatus = true;
            for (WebElement input_autoRefraction : oPage_RefractionTab.list_inputAutoRefraction) {
                Cls_Generic_Methods.sendKeysIntoElement(input_autoRefraction, "1");
                if (Cls_Generic_Methods.getElementAttribute(input_autoRefraction, "value").isEmpty()) {
                    autoStatus = false;
                    break;
                }
            }
            m_assert.assertTrue(autoStatus, "Validated Auto Refraction table");
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Auto Refraction" + e);
        }
    }
    private static void validateRefraction_DRY_REFRACTION() {
        try {
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_fillDryRefraction), "Clicked Fill button in Dry Refraction");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.btn_rightEqualLeftFillRefraction, 10);
            m_assert.assertTrue(fillRefractionTable(), "Validated Fill functionality in Dry Refraction");
            m_assert.assertTrue(validateRightEqualLeftInRefraction(), "Validated Right=Left Functionality in Dry Refraction");
            Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_okFillRefraction);
            clickAndValidateCopyToGlassPrescription(oPage_RefractionTab.btn_copyDryRefractionToGlassPrescription);
            for (WebElement commentEle : oPage_RefractionTab.list_inputDryRefractionComments) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(commentEle, oTemplate_Data.sCommentRefraction), "Entered <b>" + oTemplate_Data.sCommentRefraction + "</b> in " + findLeftOrRightFieldName(commentEle));
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Dry Refraction" + e);
        }
    }
    private static void validateRefraction_DILATED_REFRACTION() {
        try {
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_fillDilatedRefraction), "Clicked Fill button in Dilated Refraction");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.btn_rightEqualLeftFillRefraction, 10);
            m_assert.assertTrue(fillRefractionTable(), "Validated Fill functionality in Dilated Refraction");
            m_assert.assertTrue(validateRightEqualLeftInRefraction(), "Validated Right=Left Functionality in Dilated Refraction");
            Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_okFillRefraction);
            clickAndValidateCopyToGlassPrescription(oPage_RefractionTab.btn_copyDilatedRefractionToGlassPrescription);
            for (WebElement selectDrug : oPage_RefractionTab.list_selectDrugDilatedRefraction) {
                m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(selectDrug, oTemplate_Data.sDrugUsedDilatedRefraction), "Selected <b>" + oTemplate_Data.sDrugUsedDilatedRefraction + "</b> in Drug Used - " + findLeftOrRightFieldName(selectDrug));
            }
            for (WebElement commentEle : oPage_RefractionTab.list_inputDilatedRefractionComments) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(commentEle, oTemplate_Data.sCommentRefraction), "Entered <b>" + oTemplate_Data.sCommentRefraction + "</b> in " + findLeftOrRightFieldName(commentEle));
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Dilated Refraction" + e);
        }
    }
    private static void validateRefraction_PGP() {
        try {
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_fillPGP), "Clicked Fill button in PGP Refraction");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.btn_rightEqualLeftFillRefraction, 10);
            m_assert.assertTrue(fillRefractionTable(), "Validated Fill functionality in PGP Refraction");
            m_assert.assertTrue(validateRightEqualLeftInRefraction(), "Validated Right=Left Functionality in PGP Refraction");
            Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_okFillRefraction);
            clickAndValidateCopyToGlassPrescription(oPage_RefractionTab.btn_copyPGPtoGlassPrescription);
            for (WebElement select_TypeOfLens : oPage_RefractionTab.list_selectTypeOfLensPGP) {
                if (Cls_Generic_Methods.isElementDisplayed(select_TypeOfLens)) {
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(select_TypeOfLens, oTemplate_Data.sTypesOfLensPGP), "Selected <b>" + oTemplate_Data.sTypesOfLensPGP + "</b> in Type of Lens  - " + findLeftOrRightFieldName(select_TypeOfLens));
                }
            }
            for (WebElement commentEle : oPage_RefractionTab.list_inputPGP_RefractionComments) {
                if (Cls_Generic_Methods.isElementDisplayed(commentEle)) {
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(commentEle, oTemplate_Data.sCommentRefraction), "Entered <b>" + oTemplate_Data.sCommentRefraction + "</b> in " + findLeftOrRightFieldName(commentEle));
                }
            }
            int pgpIndex = 0;
            boolean addPGP_status = false;
            for (WebElement btn_addPGP : oPage_RefractionTab.list_btnAddPgp) {
                if (btn_addPGP.isDisplayed()) {
                    pgpIndex++;
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn_addPGP), "Clicked Add PGP" + pgpIndex + " in Refraction");
                }
            }
            if (Cls_Generic_Methods.isElementDisplayed(oPage_RefractionTab.text_PGP3)) {
                addPGP_status = true;
            }
            m_assert.assertTrue(addPGP_status, "Validated Add PGP --> " + pgpIndex + " pgp added");
            boolean removePGP_status = false;
            for (WebElement btn_removePGP : oPage_RefractionTab.list_BtnRemovePGP) {
                if (btn_removePGP.isDisplayed()) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, btn_removePGP), "Clicked Remove PGP" + pgpIndex + " in Refraction");
                    pgpIndex--;
                }
            }
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_RefractionTab.text_PGP3)) {
                removePGP_status = true;
            }
            m_assert.assertTrue(removePGP_status, "Validated Remove PGP in refraction tab");
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate PGP Refraction" + e);
        }
    }
    private static void validateRefraction_GLASS_PRESCRIPTION() {
        try {
            clearGlassPrescriptionValues();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_fillGlassPrescription), "Clicked Fill button in Glass Prescription");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.btn_rightEqualLeftFillRefraction, 10);
            m_assert.assertTrue(fillRefractionTable(), "Validated Fill functionality in Glass Prescription");
            m_assert.assertTrue(validateRightEqualLeftInRefraction(), "Validated Right=Left Functionality in Glass Prescription");
            Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_okFillRefraction);
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.btn_adviseGlassPrescriptions, "class").contains("btn-darkblue"), "Advice button is glass prescription is selected by default");
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_adviseGlassPrescriptions), "Advise button in glass prescription is clickable");
            Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_adviseGlassPrescriptions);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_RefractionTab.checkbox_showAddInPrintGlassPrescription), "Show Add in print checkbox is clickable");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_typeOfLensGlassPrescription("r"), oTemplate_Data.sTYPE_OF_LENS_GLASS_PRESCRIPTION), "Selected <b>" + oTemplate_Data.sTYPE_OF_LENS_GLASS_PRESCRIPTION + "</b> in Type of Lens R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_IPD_GlassPrescription("r"), oTemplate_Data.sIPD_GLASS_PRESCRIPTION), "Entered <b>" + oTemplate_Data.sIPD_GLASS_PRESCRIPTION + "</b> in IPD R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_SizeGlassPrescription("r"), oTemplate_Data.sSIZE_GLASS_PRESCRIPTION), "Entered <b>" + oTemplate_Data.sSIZE_GLASS_PRESCRIPTION + "</b> in Size R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_lensMaterialGlassPrescription("r"), oTemplate_Data.sLENS_MATERIAL), "Selected <b>" + oTemplate_Data.sLENS_MATERIAL + "</b> in Lens Material R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_lensTintGlassPrescription("r"), oTemplate_Data.sLENS_TINT), "Selected <b>" + oTemplate_Data.sLENS_TINT + "</b> in Lens Tint R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_frameMaterialGlassPrescription("r"), oTemplate_Data.sFRAME_MATERIAL), "Selected <b>" + oTemplate_Data.sFRAME_MATERIAL + "</b> in Frame Material R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_diaGlassPrescription("r"), oTemplate_Data.sDIA_GLASS_PRESCRIPTION), "Entered <b>" + oTemplate_Data.sDIA_GLASS_PRESCRIPTION + "</b> in Dia R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_prismBaseGlassPrescription("r"), oTemplate_Data.sPRISM_BASE_GLASS_PRESCRIPTION), "Entered <b>" + oTemplate_Data.sPRISM_BASE_GLASS_PRESCRIPTION + "</b> in Prism Base R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_fittingHeight("r"), oTemplate_Data.sFITTING_HEIGHT_GLASS_PRESCRIPTION), "Entered <b>" + oTemplate_Data.sFITTING_HEIGHT_GLASS_PRESCRIPTION + "</b> in Fitting height R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_adviceCommentGlassPrescription("r"), oTemplate_Data.sADVICE_GLASS_PRESCRIPTION), "Entered <b>" + oTemplate_Data.sADVICE_GLASS_PRESCRIPTION + "</b> in Advice R/OD-->Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_copyRightToLeft_GlassPrescription), "Clicked Copy Right to Left button in glass prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_RefractionTab.select_typeOfLensGlassPrescription("l")).equals(oTemplate_Data.sTYPE_OF_LENS_GLASS_PRESCRIPTION), "Validated Copy Right to Left ->Type of Lens =<b>" + oTemplate_Data.sTYPE_OF_LENS_GLASS_PRESCRIPTION + "</b> in L/OS-->Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_IPD_GlassPrescription("l"), "value").equals(oTemplate_Data.sIPD_GLASS_PRESCRIPTION), "Validated Copy Right to Left ->IPD =<b>" + oTemplate_Data.sIPD_GLASS_PRESCRIPTION + "</b> in L/OS -->Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_SizeGlassPrescription("l"), "value").equals(oTemplate_Data.sSIZE_GLASS_PRESCRIPTION), "Validated Copy Right to Left ->Size =<b>" + oTemplate_Data.sSIZE_GLASS_PRESCRIPTION + "</b> in L/OS -->Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_RefractionTab.select_lensMaterialGlassPrescription("l")).equals(oTemplate_Data.sLENS_MATERIAL), "Validated Copy Right to Left ->Lens Material =<b>" + oTemplate_Data.sLENS_MATERIAL + "</b> in L/OS -->Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_RefractionTab.select_lensTintGlassPrescription("l")).equals(oTemplate_Data.sLENS_TINT), "Validated Copy Right to Left ->Lens Tint =<b>" + oTemplate_Data.sLENS_TINT + "</b> in L/OS -->Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_RefractionTab.select_frameMaterialGlassPrescription("l")).equals(oTemplate_Data.sFRAME_MATERIAL), "Validated Copy Right to Left ->Frame Material =<b>" + oTemplate_Data.sFRAME_MATERIAL + "</b> in L/OS -->Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_diaGlassPrescription("l"), "value").equals(oTemplate_Data.sDIA_GLASS_PRESCRIPTION), "Validated Copy Right to Left ->Dia =<b>" + oTemplate_Data.sDIA_GLASS_PRESCRIPTION + "</b> in L/OS -->Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_prismBaseGlassPrescription("l"), "value").equals(oTemplate_Data.sPRISM_BASE_GLASS_PRESCRIPTION), "Validated Copy Right to Left ->Prism Base =<b>" + oTemplate_Data.sPRISM_BASE_GLASS_PRESCRIPTION + "</b> in L/OS -->Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_fittingHeight("l"), "value").equals(oTemplate_Data.sFITTING_HEIGHT_GLASS_PRESCRIPTION), "Validated Copy Right to Left ->Fitting Height =<b>" + oTemplate_Data.sFITTING_HEIGHT_GLASS_PRESCRIPTION + "</b> in L/OS -->Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_adviceCommentGlassPrescription("l"), "value").equals(oTemplate_Data.sADVICE_GLASS_PRESCRIPTION), "Validated Copy Right to Left ->Advice =<b>" + oTemplate_Data.sADVICE_GLASS_PRESCRIPTION + "</b> in L/OS -->Glass Prescription");
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Glass Prescriptions" + e);
        }
    }
    private static void validateRefraction_INTERMEDIATE_GLASSES_PRESCRIPTIONS() {
        try {
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_fillIntermediateGlassPrescription), "Clicked Fill button in Intermediate Glass Prescription");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.btn_rightEqualLeftFillRefraction, 10);
            m_assert.assertTrue(fillRefractionTable(), "Validated Fill functionality in Intermediate Glass Prescription");
            m_assert.assertTrue(validateRightEqualLeftInRefraction(), "Validated Right=Left Functionality in Intermediate Glass Prescription");
            Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_okFillRefraction);
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.btn_adviseIntermediateGlassPrescriptions, "class").contains("btn-darkblue"), "Advice button is Intermediate glass prescription is selected by default");
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_adviseIntermediateGlassPrescriptions), "Advise button in Intermediate glass prescription is clickable");
            Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_adviseIntermediateGlassPrescriptions);
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_typeOfLensIntermediateGlassPrescription("r"), oTemplate_Data.sTYPE_OF_LENS_GLASS_PRESCRIPTION), "Selected <b>" + oTemplate_Data.sTYPE_OF_LENS_GLASS_PRESCRIPTION + "</b> in Type of Lens R/OD-->Intermediate Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_IPD_IntermediateGlassPrescription("r"), oTemplate_Data.sIPD_GLASS_PRESCRIPTION), "Entered <b>" + oTemplate_Data.sIPD_GLASS_PRESCRIPTION + "</b> in IPD R/OD-->Intermediate Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_lensMaterialIntermediateGlassPrescription("r"), oTemplate_Data.sLENS_MATERIAL), "Selected <b>" + oTemplate_Data.sLENS_MATERIAL + "</b> in Lens Material R/OD-->Intermediate Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_lensTintIntermediateGlassPrescription("r"), oTemplate_Data.sLENS_TINT), "Selected <b>" + oTemplate_Data.sLENS_TINT + "</b> in Lens Tint R/OD-->Intermediate Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_frameMaterialIntermediateGlassPrescription("r"), oTemplate_Data.sFRAME_MATERIAL), "Selected <b>" + oTemplate_Data.sFRAME_MATERIAL + "</b> in Frame Material R/OD-->Intermediate Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_adviceCommentIntermediateGlassPrescription("r"), oTemplate_Data.sADVICE_GLASS_PRESCRIPTION), "Entered <b>" + oTemplate_Data.sADVICE_GLASS_PRESCRIPTION + "</b> in Advice R/OD-->Intermediate Glass Prescription");
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, oPage_RefractionTab.btn_copyRightToLeft_IntermediateGlassPrescription), "Clicked Copy Right to Left button in Intermediate glass prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_RefractionTab.select_typeOfLensIntermediateGlassPrescription("l")).equals(oTemplate_Data.sTYPE_OF_LENS_GLASS_PRESCRIPTION), "Validated Copy Right to Left ->Type of Lens =<b>" + oTemplate_Data.sTYPE_OF_LENS_GLASS_PRESCRIPTION + "</b> in L/OS-->Intermediate Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_IPD_IntermediateGlassPrescription("l"), "value").equals(oTemplate_Data.sIPD_GLASS_PRESCRIPTION), "Validated Copy Right to Left ->IPD =<b>" + oTemplate_Data.sIPD_GLASS_PRESCRIPTION + "</b> in L/OS -->Intermediate Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_RefractionTab.select_lensMaterialIntermediateGlassPrescription("l")).equals(oTemplate_Data.sLENS_MATERIAL), "Validated Copy Right to Left ->Lens Material =<b>" + oTemplate_Data.sLENS_MATERIAL + "</b> in L/OS -->Intermediate Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_RefractionTab.select_lensTintIntermediateGlassPrescription("l")).equals(oTemplate_Data.sLENS_TINT), "Validated Copy Right to Left ->Lens Tint =<b>" + oTemplate_Data.sLENS_TINT + "</b> in L/OS -->Intermediate Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_RefractionTab.select_frameMaterialIntermediateGlassPrescription("l")).equals(oTemplate_Data.sFRAME_MATERIAL), "Validated Copy Right to Left ->Frame Material =<b>" + oTemplate_Data.sFRAME_MATERIAL + "</b> in L/OS -->Intermediate Glass Prescription");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_adviceCommentIntermediateGlassPrescription("l"), "value").equals(oTemplate_Data.sADVICE_GLASS_PRESCRIPTION), "Validated Copy Right to Left ->Advice =<b>" + oTemplate_Data.sADVICE_GLASS_PRESCRIPTION + "</b> in L/OS -->Intermediate Glass Prescription");
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Intermediate Glass Prescription" + e);
        }
    }
    private static void validateRefraction_PMT() {
        try {
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_fillPMT), "Clicked Fill button in PMT Refraction");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.btn_rightEqualLeftFillRefraction, 10);
            m_assert.assertTrue(fillRefractionTable(), "Validated Fill functionality in PMT Refraction");
            m_assert.assertTrue(validateRightEqualLeftInRefraction(), "Validated Right=Left Functionality in PMT Refraction");
            Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_okFillRefraction);
            clickAndValidateCopyToGlassPrescription(oPage_RefractionTab.btn_copyPMTtoGlassPrescription);
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate PMT" + e);
        }
    }
    private static void validateRefraction_RETINOSCOPY() {
        try {
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_retinoscopyTop("r"), oTemplate_Data.sTOP_RETINOSCOPY), "Entered <b>" + oTemplate_Data.sTOP_RETINOSCOPY + "</b> in Top Retinoscopy R/OD");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_retinoscopyBottom("r"), oTemplate_Data.sBOTTOM_RETINOSCOPY), "Entered <b>" + oTemplate_Data.sBOTTOM_RETINOSCOPY + "</b> in Bottom Retinoscopy R/OD");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_retinoscopyLeft("r"), oTemplate_Data.sLEFT_RETINOSCOPY), "Entered <b>" + oTemplate_Data.sLEFT_RETINOSCOPY + "</b> in Left Retinoscopy R/OD");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_retinoscopyRight("r"), oTemplate_Data.sRIGHT_RETINOSCOPY), "Entered <b>" + oTemplate_Data.sRIGHT_RETINOSCOPY + "</b> in Right Retinoscopy R/OD");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_retinoscopyVA("r"), oTemplate_Data.sVA_RETINOSCOPY), "Entered <b>" + oTemplate_Data.sVA_RETINOSCOPY + "</b> in VA Retinoscopy R/OD");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_retinoscopyHA("r"), oTemplate_Data.sHA_RETINOSCOPY), "Entered <b>" + oTemplate_Data.sHA_RETINOSCOPY + "</b> in HA Retinoscopy R/OD");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_retinoscopyDistance("r"), oTemplate_Data.sDISTANCE_RETINOSCOPY), "Entered <b>" + oTemplate_Data.sDISTANCE_RETINOSCOPY + "</b> in At Distance Retinoscopy R/OD");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_retinoscopyDrugUsed("r"), oTemplate_Data.sDRUG_USED_RETINOSCOPY), "Selected <b>" + oTemplate_Data.sDRUG_USED_RETINOSCOPY + "</b> in Drug Used Retinoscopy R/OD");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_retinoscopyComments("r"), oTemplate_Data.sCOMMENT_RETINOSCOPY), "Entered <b>" + oTemplate_Data.sCOMMENT_RETINOSCOPY + "</b> in Comment Retinoscopy R/OD");
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByAction(driver, oPage_RefractionTab.btn_CopyRightToLeft_Retinoscopy), "Clicked Copy Right to Left button in Retinoscopy");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_retinoscopyTop("l"), "value").equals(oTemplate_Data.sTOP_RETINOSCOPY), "Validated Copy Right to Left ->Entered <b>" + oTemplate_Data.sTOP_RETINOSCOPY + "</b> in Top Retinoscopy L/OS");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_retinoscopyBottom("l"), "value").equals(oTemplate_Data.sBOTTOM_RETINOSCOPY), "Validated Copy Right to Left ->Entered <b>" + oTemplate_Data.sBOTTOM_RETINOSCOPY + "</b> in Bottom Retinoscopy L/OS");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_retinoscopyLeft("l"), "value").equals(oTemplate_Data.sLEFT_RETINOSCOPY), "Validated Copy Right to Left ->Entered <b>" + oTemplate_Data.sLEFT_RETINOSCOPY + "</b> in Left Retinoscopy L/OS");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_retinoscopyRight("l"), "value").equals(oTemplate_Data.sRIGHT_RETINOSCOPY), "Validated Copy Right to Left ->Entered <b>" + oTemplate_Data.sRIGHT_RETINOSCOPY + "</b> in Right Retinoscopy L/OS");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_retinoscopyVA("l"), "value").equals(oTemplate_Data.sVA_RETINOSCOPY), "Validated Copy Right to Left ->Entered <b>" + oTemplate_Data.sVA_RETINOSCOPY + "</b> in VA Retinoscopy L/OS");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_retinoscopyHA("l"), "value").equals(oTemplate_Data.sHA_RETINOSCOPY), "Validated Copy Right to Left ->Entered <b>" + oTemplate_Data.sHA_RETINOSCOPY + "</b> in HA Retinoscopy L/OS");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_retinoscopyDistance("l"), "value").equals(oTemplate_Data.sDISTANCE_RETINOSCOPY), "Validated Copy Right to Left ->Entered <b>" + oTemplate_Data.sDISTANCE_RETINOSCOPY + "</b> in At Distance Retinoscopy L/OS");
            m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_RefractionTab.select_retinoscopyDrugUsed("l")).equals(oTemplate_Data.sDRUG_USED_RETINOSCOPY), "Validated Copy Right to Left ->Selected <b>" + oTemplate_Data.sDRUG_USED_RETINOSCOPY + "</b> in Drug Used Retinoscopy L/OS");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_retinoscopyComments("l"), "value").equals(oTemplate_Data.sCOMMENT_RETINOSCOPY), "Validated Copy Right to Left ->Entered <b>" + oTemplate_Data.sDISTANCE_RETINOSCOPY + "</b> in At Distance Retinoscopy L/OS");
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to fill/validate Retinoscopy");
        }
    }
    private static void validateRefraction_KERATOMETRY() {
        try {
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_keratometryKhValue("r"), oTemplate_Data.sKH_VALUE_KERATOMETRY), "Entered <b>" + oTemplate_Data.sKH_VALUE_KERATOMETRY + "</b> in KH VALUE -->R/OD Keratometry");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_keratometryKvValue("r"), oTemplate_Data.sKV_VALUE_KERATOMETRY), "Entered <b>" + oTemplate_Data.sKV_VALUE_KERATOMETRY + "</b> in KV VALUE -->R/OD Keratometry");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_keratometryKhAxis("r"), oTemplate_Data.sKH_VALUE_KERATOMETRY), "Entered <b>" + oTemplate_Data.sKH_AXIS_KERATOMETRY + "</b> in KH AXIS -->R/OD Keratometry");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_keratometryKvAxis("r"), oTemplate_Data.sKH_VALUE_KERATOMETRY), "Entered <b>" + oTemplate_Data.sKV_AXIS_KERATOMETRY + "</b> in KV AXIS -->R/OD Keratometry");
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_copyRightToLeftKeratometry), "Clicked Copy from right to left button keratometry");
            m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_keratometryKhValue("l"), "value").equals(oTemplate_Data.sKH_VALUE_KERATOMETRY), "Validated Copy Right to Left ->Entered <b>" + oTemplate_Data.sKH_VALUE_KERATOMETRY + "</b> in KH VALUE -->L/OS Keratometry");
            m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_keratometryKvValue("l"), "value").equals(oTemplate_Data.sKV_VALUE_KERATOMETRY), "Validated Copy Right to Left ->Entered <b>" + oTemplate_Data.sKV_VALUE_KERATOMETRY + "</b> in KV VALUE -->L/OS Keratometry");
            m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_keratometryKhAxis("l"), "value").equals(oTemplate_Data.sKH_VALUE_KERATOMETRY), "Validated Copy Right to Left ->Entered <b>" + oTemplate_Data.sKH_AXIS_KERATOMETRY + "</b> in KH AXIS -->L/OS Keratometry");
            m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_keratometryKvAxis("l"), "value").equals(oTemplate_Data.sKH_VALUE_KERATOMETRY), "Validated Copy Right to Left ->Entered <b>" + oTemplate_Data.sKV_AXIS_KERATOMETRY + "</b> in KV AXIS -->L/OS Keratometry");
            String expectedAverage = "";
            String expectedCyl = "";
            if (!oTemplate_Data.sKH_VALUE_KERATOMETRY.isEmpty() && !oTemplate_Data.sKV_VALUE_KERATOMETRY.isEmpty()) {
                double khValue = Integer.parseInt(oTemplate_Data.sKH_VALUE_KERATOMETRY);
                double kvValue = Integer.parseInt(oTemplate_Data.sKV_VALUE_KERATOMETRY);
                expectedAverage = String.valueOf((khValue + kvValue) / 2);
                expectedCyl = String.valueOf(khValue - kvValue);
                for (WebElement input_average : oPage_RefractionTab.list_inputAverageKeratometry) {
                    String actualAverage = Cls_Generic_Methods.getElementAttribute(input_average, "value");
                    m_assert.assertTrue(actualAverage.contains(expectedAverage), "Validated Keratometry Average <b>" + expectedAverage + "</b> in " + findLeftOrRightFieldName(input_average));
                }
                for (WebElement input_cyl : oPage_RefractionTab.list_inputCylKeratometry) {
                    String actualCyl = Cls_Generic_Methods.getElementAttribute(input_cyl, "value");
                    m_assert.assertTrue(actualCyl.contains(expectedCyl), "Validated Cyl <b>" + expectedCyl + "</b> in " + findLeftOrRightFieldName(input_cyl));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void validateRefraction_AMSLER() {
        try {
            int rSelectIndex = 0;
            String rSelectText = "";
            for (WebElement btnRightAmsler : oPage_RefractionTab.list_btnRightAmsler) {
                String textAmsler = Cls_Generic_Methods.getTextInElement(btnRightAmsler);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(btnRightAmsler), "<b>" + textAmsler + "</b> button in R/OD Amsler is Clickable");
                if (textAmsler.equalsIgnoreCase(oTemplate_Data.sRIGHT_AMSLER)) {
                    rSelectIndex = oPage_RefractionTab.list_btnRightAmsler.indexOf(btnRightAmsler);
                    rSelectText = textAmsler;
                }
            }
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.list_btnRightAmsler.get(rSelectIndex)), "Clicked <b>" + rSelectText + "</b> in R/OD Amsler");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_commentAmsler("r"), oTemplate_Data.sCOMMENT_AMSLER), "Entered <b>" + oTemplate_Data.sCOMMENT_AMSLER + "</b> in R/OD Ambsler comment");
            int lSelectIndex = 0;
            String lSelectText = "";
            for (WebElement btnLeftAmsler : oPage_RefractionTab.list_btnLeftAmsler) {
                String textAmsler = Cls_Generic_Methods.getTextInElement(btnLeftAmsler);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(btnLeftAmsler), "<b>" + textAmsler + "</b> button in L/OS Amsler is Clickable");
                if (textAmsler.equalsIgnoreCase(oTemplate_Data.sRIGHT_AMSLER)) {
                    lSelectIndex = oPage_RefractionTab.list_btnLeftAmsler.indexOf(btnLeftAmsler);
                    lSelectText = textAmsler;
                }
            }
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.list_btnLeftAmsler.get(lSelectIndex)), "Clicked <b>" + lSelectText + "</b> in L/OS Amsler");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_commentAmsler("l"), oTemplate_Data.sCOMMENT_AMSLER), "Entered <b>" + oTemplate_Data.sCOMMENT_AMSLER + "</b> in L/OS Ambsler comment");
        } catch (Exception e) {
            m_assert.assertFatal("Unable to fix/validate amsler");
            e.printStackTrace();
        }
    }
    private static void validateRefraction_CONTACT_LENS() {
        try {
            sCONTACT_LENS_PRESCRIPTIONS = oTemplate_Data.sCONTACT_LENS_PRESCRIPTION();
            int tableColumn = 0;
            for (WebElement inputTableContactLens : oPage_RefractionTab.list_inputTableHeader_ContactLensPrescription) {
                String table_Header = Cls_Generic_Methods.getTextInElement(inputTableContactLens);
                String table_value = sCONTACT_LENS_PRESCRIPTIONS.get(table_Header);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_contactLens_prescription("r").get(tableColumn), table_value), "Entered <b>" + table_value + "</b> in R/OD " + table_Header);
                tableColumn++;
            }
            m_assert.assertInfo(CommonActions.selectDateFromDatePicker(oPage_RefractionTab.input_revisitDate_contactLensPrescriptions("r"), oTemplate_Data.sREVISIT_DATE), "Entered " + oTemplate_Data.sREVISIT_DATE + " as revisit date -->R/OD Contact Lens Prescriptions");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_color_contactLensPrescription("r"), oTemplate_Data.sCOLOR_CONTACT_LENS), "Entered " + oTemplate_Data.sCOLOR_CONTACT_LENS + " in color -->R/OD Contact Lens Prescriptions");
            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_RefractionTab.select_type_contactLensPrescription("r"), oTemplate_Data.sTYPE_CONTACT_LENS), "Selected " + oTemplate_Data.sTYPE_CONTACT_LENS + " in Type -->R/OD Contact Lens Prescriptions");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_advice_contactLensPrescription("r"), oTemplate_Data.sADVICE_CONTACT_LENS), "Entered " + oTemplate_Data.sADVICE_CONTACT_LENS + " in Advice -->R/OD Contact Lens Prescriptions");
            ;
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_copyRightToLeft_contactLensPrescription), "Clicked copy right to left -->Contact lens Prescription");
            int l_tableColumn = 0;
            for (WebElement inputTableContactLens : oPage_RefractionTab.list_inputTableHeader_ContactLensPrescription) {
                String table_Header = Cls_Generic_Methods.getTextInElement(inputTableContactLens);
                String table_value = sCONTACT_LENS_PRESCRIPTIONS.get(table_Header);
                m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_contactLens_prescription("l").get(l_tableColumn), "value").equals(table_value), "Validated Copy Right to Left <b>" + table_value + "</b> in L/OS " + table_Header);
                l_tableColumn++;
            }
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_revisitDate_contactLensPrescriptions("l"), "value").equals(oTemplate_Data.sREVISIT_DATE), "Validated Copy Right to Left --> L/OS Revisit Date is " + oTemplate_Data.sREVISIT_DATE);
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_color_contactLensPrescription("l"), "value").equals(oTemplate_Data.sCOLOR_CONTACT_LENS), "Validated Copy Right to Left --> L/OS Color is " + oTemplate_Data.sCOLOR_CONTACT_LENS);
            m_assert.assertTrue(Cls_Generic_Methods.getSelectedValue(oPage_RefractionTab.select_type_contactLensPrescription("l")).equals(oTemplate_Data.sTYPE_CONTACT_LENS), "Validated Copy Right to Left --> L/OS Type is  " + oTemplate_Data.sTYPE_CONTACT_LENS);
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_advice_contactLensPrescription("r"), "value").equals(oTemplate_Data.sADVICE_CONTACT_LENS), "Validated Copy Right to Left --> L/OS Advice is  " + oTemplate_Data.sADVICE_CONTACT_LENS);
        } catch (Exception e) {
            m_assert.fail("Unable to validate Contact lens");
            e.printStackTrace();
        }
    }
    private static void validateRefraction_COLOR_VISION() {
        try {
            String value = oTemplate_Data.sCOLOR_VISION;
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_RefractionTab.input_colorVision("r"), value), "Entered " + value + " in R/OD color vision");
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_RefractionTab.btn_copyRightToLeft_colorVision), "Clicked copy right to left -->Color Vision");
            m_assert.assertInfo(Cls_Generic_Methods.getElementAttribute(oPage_RefractionTab.input_colorVision("r"), "value").equals(value), "Validated Copy right to left L/OS Color is " + value);
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate color vision");
            e.printStackTrace();
        }
    }
    private static void validateRefraction_CONTRAST_SENSITIVITY() {
        try {
            boolean buttonClickable = false;
            for (WebElement btn_contrastSensitivity : oPage_RefractionTab.list_allBtnContrastSensitivity) {
                try {
                    String btnText = Cls_Generic_Methods.getTextInElement(btn_contrastSensitivity);
                    if (oTemplate_Data.sCONTRAST_SENSITIVITY.equals(btnText)) {
                        Cls_Generic_Methods.clickElement(btn_contrastSensitivity);
                        buttonClickable = true;
                    }
                } catch (Exception e) {
                    buttonClickable = false;
                    break;
                }
            }
            m_assert.assertTrue(buttonClickable, "Validated all buttons in Contrast Sensitivity are clickable");
            String commentValue = oTemplate_Data.sCOMMENT_CONTRAST_SENSITIVITY;
            for (WebElement button : oPage_RefractionTab.list_allBtnContrastSensitivity) {
                String buttonValue = Cls_Generic_Methods.getTextInElement(button);
                if (buttonValue.equals(oTemplate_Data.sCONTRAST_SENSITIVITY)) {
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(button), "Clicked " + buttonValue + " in " + findLeftOrRightFieldName(button));
                }
            }
            for (WebElement commentContrastSensitivity : oPage_RefractionTab.list_commentContrastSensitivity) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(commentContrastSensitivity, commentValue), "Entered " + commentValue + " in " + findLeftOrRightFieldName(commentContrastSensitivity));
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate contrast sensitivity");
            e.printStackTrace();
        }
    }
    private static void validateRefraction_ORTHOPTICS() {
        try {
            String commentValue = oTemplate_Data.sORTHOPTICS;
            for (WebElement commentOrthoptics : oPage_RefractionTab.list_commentOrthoptics) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(commentOrthoptics, commentValue), "Entered " + commentValue + " in " + findLeftOrRightFieldName(commentOrthoptics));
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate orthoptics");
        }
    }
    private static void validateRefraction_TRIAL_LENS_DETAILS() {
        try {
            String trailValue = oTemplate_Data.sTRAIL_LENS_DETAILS;
            for (WebElement inputTrail : oPage_RefractionTab.list_inputTrailLensDetails) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputTrail, trailValue), "Entered " + trailValue + " in " + findLeftOrRightFieldName(inputTrail));
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Trail Lens Details");
        }
    }
    private static void validateRefraction_FIT_ASSESSMENT() {
        try {
            String fitAssessmentValue = oTemplate_Data.sFIT_ASSESSMENT;
            for (WebElement inputFitAssessment : oPage_RefractionTab.list_inputFitAssessment) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputFitAssessment, fitAssessmentValue), "Entered " + fitAssessmentValue + " in " + findLeftOrRightFieldName(inputFitAssessment));
            }
            for (WebElement inputFitAssessmentComment : oPage_RefractionTab.list_inputFitAssessmentComments) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputFitAssessmentComment, oTemplate_Data.sCommentRefraction), "Entered " + oTemplate_Data.sCommentRefraction + " in " + findLeftOrRightFieldName(inputFitAssessmentComment));
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Fit Assessment");
        }
    }
    private static void validateRefraction_OVER_REACTION_ACCEPTANCE() {
        try {
            String overReactionAcceptanceValue = oTemplate_Data.sOVER_REACTION_ACCEPTANCE;
            for (WebElement inputOverReaction : oPage_RefractionTab.list_inputOverReactionAcceptance) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputOverReaction, overReactionAcceptanceValue), "Entered " + overReactionAcceptanceValue + " in " + findLeftOrRightFieldName(inputOverReaction));
            }
            for (WebElement inputOverReactionComment : oPage_RefractionTab.list_inputOverReactionAcceptanceComments) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputOverReactionComment, oTemplate_Data.sCommentRefraction), "Entered " + oTemplate_Data.sCommentRefraction + " in " + findLeftOrRightFieldName(inputOverReactionComment));
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Over Reaction Acceptance");
        }
    }
    private static void validateRefraction_VIRTUAL_NEEDS() {
        try {
            String virtualNeedsValue = oTemplate_Data.sVIRTUAL_NEEDS;
            for (WebElement inputVirtualNeeds : oPage_RefractionTab.list_inputVirtualNeeds) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputVirtualNeeds, virtualNeedsValue), "Entered " + virtualNeedsValue + " in " + findLeftOrRightFieldName(inputVirtualNeeds));
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Virtual Needs");
        }
    }
    private static void validateRefraction_DISTANCE() {
        try {
            String distanceValue = oTemplate_Data.sDISTANCE;
            for (WebElement inputDistance : oPage_RefractionTab.list_inputDistance) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputDistance, distanceValue), "Entered " + distanceValue + " in " + findLeftOrRightFieldName(inputDistance));
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Distance");
        }
    }
    private static void validateRefraction_FINAL_RECOMMENDATIONS() {
        try {
            String finalRecommendationValue = oTemplate_Data.sFINAL_RECOMMENDATIONS;
            for (WebElement inputFinalRecommendation : oPage_RefractionTab.list_inputFinalRecommendations) {
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(inputFinalRecommendation, finalRecommendationValue), "Entered " + finalRecommendationValue + " in " + findLeftOrRightFieldName(inputFinalRecommendation));
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Final Recommendations");
        }
    }
    private static void validateRefraction_LVA_PROBLEMS() {
        try {
            String lvaProblemValue = oTemplate_Data.sLVA_PROBLEMS;
            for (WebElement btn_LVA_problem : oPage_RefractionTab.list_btn_LVA_problems) {
                String btnValue = Cls_Generic_Methods.getTextInElement(btn_LVA_problem);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(btn_LVA_problem), "<b> " + btnValue + "</b> button in LVA Problem is clickable");
                if (!btnValue.equalsIgnoreCase(lvaProblemValue)) {
                    Cls_Generic_Methods.clickElement(btn_LVA_problem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate LVA Problems");
        }
    }
    private static void validateAndFill_ListOfInput(List<WebElement> listOfInputTextBox, String valueToBeEntered) {
        try {
            for (WebElement input : listOfInputTextBox) {
                String value = Cls_Generic_Methods.getElementAttribute(input, "value");
                if (value.isEmpty()) {
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(input, valueToBeEntered), "Entered <b>" + valueToBeEntered + "</b> in " + TemplateCommonActions.findLeftOrRightFieldName(input));
                    Cls_Generic_Methods.customWait(1 / 4);
                } else {
                    m_assert.assertFatal(value + " should not be entered as default value in " + findLeftOrRightFieldName(input));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean validateVitalSign() {
        boolean flag = false;
        oPage_HistoryTab = new Page_HistoryTab(driver);
        oTemplate_Data = new Template_Data();
        try {
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_temperatureVitalSigns, oTemplate_Data.sTEMPERATURE_VITAL_SIGNS), "Entered <b>" + oTemplate_Data.sTEMPERATURE_VITAL_SIGNS + "</b> in Temperature Vital Sign");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_pulseVitalSigns, oTemplate_Data.sPULSE_VITAL_SIGNS), "Entered <b>" + oTemplate_Data.sPULSE_VITAL_SIGNS + "</b> in Pulse Vital Sign");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_bloodPressureVitalSigns, oTemplate_Data.sBLOOD_PRESSURE_VITAL_SIGNS), "Entered <b>" + oTemplate_Data.sBLOOD_PRESSURE_VITAL_SIGNS + "</b> in Blood Pressure Vital Sign");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_rrVitalSigns, oTemplate_Data.sRR_VITAL_SIGNS), "Entered <b>" + oTemplate_Data.sRR_VITAL_SIGNS + "</b> in RR Vital Sign");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_spO2VitalSigns, oTemplate_Data.sSP02_VITAL_SIGNS), "Entered <b>" + oTemplate_Data.sSP02_VITAL_SIGNS + "</b> in Sp O2 Vital Sign");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_heightVitalSigns, oTemplate_Data.sHEIGHT_VITAL_SIGNS), "Entered <b>" + oTemplate_Data.sHEIGHT_VITAL_SIGNS + "</b> in Height Vital Sign");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_weightVitalSigns, oTemplate_Data.sWEIGHT_VITAL_SIGNS), "Entered <b>" + oTemplate_Data.sWEIGHT_VITAL_SIGNS + "</b> in Weight Vital Sign");
            Cls_Generic_Methods.customWait(2);
            String bmi = Cls_Generic_Methods.getElementAttribute(oPage_HistoryTab.input_bmiVitalSigns, "value");
            m_assert.assertTrue(!bmi.isEmpty(), "Validated BMI in vital sign - Calculated BMI =<b>" + bmi + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_HistoryTab.input_commentVitalSigns, oTemplate_Data.sCOMMENTS_VITAL_SIGNS), "Entered <b>" + oTemplate_Data.sCOMMENTS_VITAL_SIGNS + "</b> in Comments Vital Sign");
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate vital sign " + e);
        }
        return flag;
    }
    private static boolean selectValueFromSet(WebElement selectSet,String value){
        boolean flag=false;
        String setName=null;
        try{
            setName=Cls_Generic_Methods.getElementAttribute(selectSet,"id").toUpperCase();
            List<WebElement> options= selectSet.findElements(By.xpath("./child::option"));
            for(WebElement option:options){
                String text=Cls_Generic_Methods.getTextInElement(option);
                if(text.equalsIgnoreCase(value)){
                    Cls_Generic_Methods.clickElement(option);
                    flag=true;
                }
            }
        }catch(Exception e){
            m_assert.assertFatal("Unable to select "+selectSet+ "in "+setName   +"  -->"+e);
        }
        return flag;
    }
}