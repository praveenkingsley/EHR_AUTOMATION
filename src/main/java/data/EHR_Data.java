package data;

import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EHR_Data {

    public static final String user_PRAkashTest = "PR.Akash test";
    public static final String user_ArpitSingh = "Arpit Singh";

    public static final String user_mansa1 = "manasa1";
    public static final String user_ARNCT2 = "AR NCT 2";
    public static final String user_HGOptom1 = "HG Optom1";
    public static final String user_HGCounsellor = "HG Counsellor";
    public static final String user_HGNurse2 = "HG Nurse2";
    public static final String user_HGLaboratoryTech = "HG Laboratory Tech";
    public static final String user_QMSAdmin = "Karthik";

    // Variables that can differ in regions
    public String sEXPECTED_TITLE;
    public List<String> list_PATIENT_REGISTERATION_FORM_TABS = new ArrayList<String>();
    public List<String> list_SYSTEMIC_HISTORY_DISORDERS = new ArrayList<String>();

    public List<String> list_OPHTHALMIC_HISTORY_DISORDERS = new ArrayList<String>();
    public List<String> list_NUTRIRIONAL_ASSESSMENTS = new ArrayList<String>();
    public List<String> list_IMMUNIZATION_ASSESSMENTS = new ArrayList<String>();
    public List<String> list_DRUG_ALLERGIES = new ArrayList<String>();
    public List<String> list_CONTACT_ALLERGIES = new ArrayList<String>();
    public List<String> list_FOOD_ALLERGIES = new ArrayList<String>();

    public List<String> list_DRUG_ALLERGIES_Antimicrobial;
    public List<String> list_DRUG_ALLERGIES_Antifungal;
    public List<String> list_DRUG_ALLERGIES_Antiviral;
    public List<String> list_DRUG_ALLERGIES_Nsaids;
    public List<String> list_DRUG_ALLERGIES_EyeDrops;

    // Common Variables across all regions
    public String sPASSWORD = "HGraph@3$3$";
    public String sUSER_NAME = "hgdummydoc";
    public List<String> list_RECEPTIONIST_TABS = new ArrayList<String>();
    public List<String> list_arnctStatus = new ArrayList<String>();
    public static Map<String, String> list_userName = new HashMap<String, String>();
    // Patient Details Tab
//	public String sFIRST_NAME = "Demo_FName";
//	public String sMIDDLE_NAME = "Demo_MName";
//	public String sLAST_NAME = "Demo_LName";
//	public String sMOBILE_NUMBER = "1234567890";
//	public String sSECONDARY_MOBILE_NUMBER = "3216549870";
//	public String sEMAIL = "demo.email@mail.com";
//	public String sPRIMARY_LANGUAGE = "Hindi";
//	public String sSECONDARY_LANGUAGE = "English";
//	public String sPINCODE = "560037";
//	public String sSTATE = "KARNATAKA";
//	public String sCITY = "Bengaluru";
//	public String sADDRESS_1 = "Sample Add. 1";
//	public String sADDRESS_2 = "Sample Add. 2";
    public String sMEDICAL_REPORT_NUMBER = "MRN_1118";
    //	public String sAADHAR_NUMBER = "123456789101";
//	public String sPAN_NUMBER = "ANMPT2557J";
//	public String sDRIVING_LICENSE_NUMBER = "OR68310567";
    public String sPATIENT_TYPE_COMMENT="NA";
    public String sPATIENT_REFERRAl_SOURCE = "None Add";
    public String sPATIENT_SUB_REFERRAL_SOURCE="test";
    public String sCOMPULSORY_FIELDS_MESSAGE = "First Name and Mobile Number are Compulsory.";
    public String sSTYLE_OF_RED_ALERT_MSG_FOR_MANODATORY_FIELDS = "color: rgb(51, 51, 51); text-shadow: red 0px 0px 10px;";

    // Other Details Tab
//	public String sBLOOD_GROUP = "B+";
//	public String sMARITAL_STATUS = "Single";
//	public String sONE_EYED = "No";
//	public String sEMERGENCY_CONTACT_NAME = "Emergency Contact Name";
//	public String sEMERGENCY_CONTACT_NUMBER = "4561327890";

    // History Tab
//	public String sSYSTEMIC_HISTORY_COMMENT = "Demo Systemic History Comment";
    public String sMEDICAL_HISTORY_COMMENT = "Demo Medical History Comment";
    public String sFAMILY_HISTORY_COMMENT = "Demo Family History Comment";
//	public String sNUTRITIONAL_ASSESSMENT_COMMENT = "Demo Nutritional Assessment Comment";
//	public String sIMMUNIZATION_ASSESSMENT_COMMENT = "Demo Immunization Assessment Comment";

    public String sDISORDER_DURATION = "3";
    public String sDISORDER_UNIT = "Weeks";
    public String sDISORDER_COMMENT = "_Demo_Comment";

    // Allergies Tab
//	public String sDRUG_ALLERGIES_COMMENT = "Demo Drug Allergies Comment";
//	public String sCONTACT_ALLERGIES_COMMENT = "Demo Contact Allergies Comment";
//	public String sFOOD_ALLERGIES_COMMENT = "Demo Food Allergies Comment";

    // Appointment Details Section
//	public String sAPPOINTMENT_TYPE = "Walk-in";
    public String sAPPOINTMENT_TYPE = "Appointment";
    public String sAPPOINTMENT_DATE = "28/02/2023";
    public String sAPPOINTMENT_TIME = "7:00:PM";
    public String sAPPOINTMENT_LOCATION = "TESTING_FACILITY";
    public String sSPECIALITIES_AVAILABLE= "Ophthalmology";
    public String sAPPOINTMENT_CONSULTANT = "PR.Akash test";
    public String sVISIT_TYPES = "Review";
    public String sVISIT_CATEGORY = "Paid";

    public String sDOCTOR_REFERRAL = "Doctor Referal";
    public String sDOCTOR_NAME = "Automation Test User";
    public String sDOCTOR_MOBILE_NUMBER = "88008248940";
    public String sDOCTOR_EMAIL = "bki@gmail.com";
    public String sDOCTOR_SPECIALITY = "Medicine Specilist";
    public String sDOCTOR_LOCATION = "Marathalli";
    public String sDOCTOR_CITY = "Bangalore";
    public String sDOCTOR_STATE = "Karnataka";
    public String sDOCTOR_COMMENTS = "Test Comments";
    public String sSTAFF_REFERRAL = "staffreferal";
    public String sRELATIVE = "Relative";
    public String sRELATIVE_NAME = "Test Relative Name";
    public String sRELATIVE_MOBILE_NUMBER = "7894561230";
    public String sRELATIVE_EMAIL = "RelativeEmail@mail.com";
    public String sRELATIVE_RELATION_TO_PATIENT = "Tester";
    public String sRELATIVE_COMMENT = "Relative Test Comment";
    public String sCAMPAIGN = "campaign";
    public String sCAMP = "camp";

    //Admission Form
    public String sDOCTOR_NAME_ADMISSION ="PR.Akash test";
    public String sLOCATION_ADMISSION="TESTING_FACILITY";
    public String[] sADDITIONAL_DOCTORS_ADMISSION={"Arpit Singh","HG Doctor5"};
    public String sADMISSION_TIME="11:00:PM";
    public String sDISCHARGE_DATE="24/02/2023";
    public String sAdmitAdmission_Time = "05:30:PM";
    public String sDISCHARGE_TIME="11:30:PM";
    public String sREPORTING_TIME = "10:00:PM";
    public String sCATEGORY_TYPE="IOL";
    public static String sPlannedAdmissionNotifyMsg="If Planned Admission is chosen, Admission Date will be selected next to 4 days automatically.";

    public EHR_Data(String country) {

        if (country.equalsIgnoreCase("") || country.equals(null)) {
            sEXPECTED_TITLE = "Foss - EHR";
            addListDataForReceptionistTabs();
            addListDataForTabsOnPatientRegForm();
            addListDataForSystemicHistoryDisordersOnHistoryTab();
            addListDataForOphthalmicHistoryDisordersOnHistoryTab();
            addListDataForNutritionalAssessmentOnHistoryTab();
            addListDataForImmunizationAssessmentOnHistoryTab();
            addListDataForDrugAllergiesOnAllergiesTab();
            addListDataForContactAllergiesOnAllergiesTab();
            addListDataForFoodAllergiesOnAllergiesTab();

        } else if (country.equalsIgnoreCase("Vietnam") || country.equalsIgnoreCase("VNM")) {
            this.sEXPECTED_TITLE = "Foss - EHR VNM";
            addListDataForReceptionistTabs();
            addListDataForTabsOnPatientRegForm_VNM();
            addListDataForOphthalmicHistoryDisordersOnHistoryTab();
            addListDataForSystemicHistoryDisordersOnHistoryTab();
            addListDataForNutritionalAssessmentOnHistoryTab();
            addListDataForImmunizationAssessmentOnHistoryTab();
            addListDataForDrugAllergiesOnAllergiesTab();
            addListDataForContactAllergiesOnAllergiesTab();
            addListDataForFoodAllergiesOnAllergiesTab();
        }

    }

    public EHR_Data() {

        sEXPECTED_TITLE = "Foss - EHR";
        addListDataForReceptionistTabs();
        addListDataForTabsOnPatientRegForm();
        addListDataForSystemicHistoryDisordersOnHistoryTab();
        addListDataForOphthalmicHistoryDisordersOnHistoryTab();
        addListDataForNutritionalAssessmentOnHistoryTab();
        addListDataForImmunizationAssessmentOnHistoryTab();
        addListDataForDrugAllergiesOnAllergiesTab();
        addListDataForContactAllergiesOnAllergiesTab();
        addListDataForFoodAllergiesOnAllergiesTab();
        addListDataForArnctStatus();
        addListDataForUserName();
    }

    private void addListDataForReceptionistTabs() {

        list_RECEPTIONIST_TABS.add("My Queue");
        list_RECEPTIONIST_TABS.add("All");
        list_RECEPTIONIST_TABS.add("Not Arrived");
        list_RECEPTIONIST_TABS.add("My Appointment");
        list_RECEPTIONIST_TABS.add("Referrals");
        list_RECEPTIONIST_TABS.add("Provisional");

    }

    private void addListDataForArnctStatus() {
        list_arnctStatus.add("Ar Nct 2");
        list_arnctStatus.add("Arnct User");
        list_arnctStatus.add("Hg Ar Nct1");
    }

    private void addListDataForUserName() {
        list_userName.put(user_PRAkashTest, "hgdummydoc,FO#sSpRd12$23$");
        list_userName.put(user_mansa1, "hgdummyrec,HGraph@3$3$");
        list_userName.put(user_ARNCT2, "hgdummyarnct,HGraph@2$2$");
        list_userName.put(user_HGOptom1, "hgdummyopto,HGraph@2$2$");
        list_userName.put(user_HGCounsellor, "hgdummycon,HGraph@2$2$");
        list_userName.put(user_HGNurse2, "hgdummynurse,HGraph@2$2$");
        list_userName.put(user_HGLaboratoryTech, "hgdummylab,HGraph@3$3$");
        list_userName.put(user_ArpitSingh,"hgdummyadmin,HGraph@3$3$");
        list_userName.put(user_QMSAdmin,"hgind_qmsadmin,HGraph@3$3$");
    }

    private void addListDataForTabsOnPatientRegForm() {

        list_PATIENT_REGISTERATION_FORM_TABS.add("Patient Details");
        list_PATIENT_REGISTERATION_FORM_TABS.add("Other Details");
        list_PATIENT_REGISTERATION_FORM_TABS.add("History");
        list_PATIENT_REGISTERATION_FORM_TABS.add("Allergies");
    }

    private void addListDataForTabsOnPatientRegForm_VNM() {

        list_PATIENT_REGISTERATION_FORM_TABS.add("Patient Details");
        list_PATIENT_REGISTERATION_FORM_TABS.add("Other Details");

    }

    public void addListDataForOphthalmicHistoryDisordersOnHistoryTab(){

        list_OPHTHALMIC_HISTORY_DISORDERS.add("Glaucoma");
        list_OPHTHALMIC_HISTORY_DISORDERS.add("Retinal Detachment");
        list_OPHTHALMIC_HISTORY_DISORDERS.add("Glass");
        list_OPHTHALMIC_HISTORY_DISORDERS.add("Eye Disease");
        list_OPHTHALMIC_HISTORY_DISORDERS.add("Eye Surgery");
        list_OPHTHALMIC_HISTORY_DISORDERS.add("Uveitis");
        list_OPHTHALMIC_HISTORY_DISORDERS.add("Retinal laser");
    }

    private void addListDataForSystemicHistoryDisordersOnHistoryTab() {

        list_SYSTEMIC_HISTORY_DISORDERS.add("Diabetes");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Hypertension");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Alcoholism");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Smoking Tobacco");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Cardiac Disorder");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Steroid Intake");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Drug Abuse");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Hiv Aids");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Cancer Tumor");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Tuberculosis");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Asthma");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Cns Disorder Stroke");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Hypothyroidism");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Hyperthyroidism");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Hepatitis Cirrhosis");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Renal Disorder");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Acidity");
        list_SYSTEMIC_HISTORY_DISORDERS.add("On insulin");
        list_SYSTEMIC_HISTORY_DISORDERS.add("On Aspirin Blood Thinners");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Consanguinity");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Thyroid Disorder");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Chewing Tobacco");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Rheumatoid Arthritis");
        list_SYSTEMIC_HISTORY_DISORDERS.add("Benign Prostatic Hyperplasia(BPH)");

    }

    private void addListDataForNutritionalAssessmentOnHistoryTab() {

        list_NUTRIRIONAL_ASSESSMENTS.add("Malnourished");
        list_NUTRIRIONAL_ASSESSMENTS.add("Well Nourished");
        list_NUTRIRIONAL_ASSESSMENTS.add("Overweight");
        list_NUTRIRIONAL_ASSESSMENTS.add("Obese");

    }

    private void addListDataForImmunizationAssessmentOnHistoryTab() {

        list_IMMUNIZATION_ASSESSMENTS.add("Complete");
        list_IMMUNIZATION_ASSESSMENTS.add("Pending");

    }

    private void addListDataForDrugAllergiesOnAllergiesTab() {

        list_DRUG_ALLERGIES.add("Antimicrobial Agents");
        list_DRUG_ALLERGIES.add("Antifungal Agents");
        list_DRUG_ALLERGIES.add("Antiviral Agents");
        list_DRUG_ALLERGIES.add("Nsaids");
        list_DRUG_ALLERGIES.add("Eye Drops");

        if (Cls_Generic_Methods.getConfigValues("testRunType").equals("Regression")) {
            list_DRUG_ALLERGIES_Antimicrobial = new ArrayList<String>();
            list_DRUG_ALLERGIES_Antifungal = new ArrayList<String>();
            list_DRUG_ALLERGIES_Antiviral = new ArrayList<String>();
            list_DRUG_ALLERGIES_Nsaids = new ArrayList<String>();
            list_DRUG_ALLERGIES_EyeDrops = new ArrayList<String>();

            // Antimicrobial agents
            list_DRUG_ALLERGIES_Antimicrobial.add("Ampicillin");
            list_DRUG_ALLERGIES_Antimicrobial.add("Amoxicillin");
            list_DRUG_ALLERGIES_Antimicrobial.add("Ceftriaxone");
            list_DRUG_ALLERGIES_Antimicrobial.add("Ciprofloxacin");
            list_DRUG_ALLERGIES_Antimicrobial.add("Clarithromycin");
            list_DRUG_ALLERGIES_Antimicrobial.add("Co Trimoxazole");
            list_DRUG_ALLERGIES_Antimicrobial.add("Ethambutol");
            list_DRUG_ALLERGIES_Antimicrobial.add("Isoniazid");
            list_DRUG_ALLERGIES_Antimicrobial.add("Metronidazole");
            list_DRUG_ALLERGIES_Antimicrobial.add("Penicillin");
            list_DRUG_ALLERGIES_Antimicrobial.add("Rifampicin");
            list_DRUG_ALLERGIES_Antimicrobial.add("Streptomycin");

            // Antifungal agents
            list_DRUG_ALLERGIES_Antifungal.add("Ketoconazole");
            list_DRUG_ALLERGIES_Antifungal.add("Fluconazole");
            list_DRUG_ALLERGIES_Antifungal.add("Itraconazole");

            // Antiviral Agents
            list_DRUG_ALLERGIES_Antiviral.add("Acyclovir");
            list_DRUG_ALLERGIES_Antiviral.add("Efavirenz");
            list_DRUG_ALLERGIES_Antiviral.add("Enfuvirtide");
            list_DRUG_ALLERGIES_Antiviral.add("Nelfinavir");
            list_DRUG_ALLERGIES_Antiviral.add("Nevirapine");
            list_DRUG_ALLERGIES_Antiviral.add("Zidovudine");

            // Nsaids
            list_DRUG_ALLERGIES_Nsaids.add("Aspirin");
            list_DRUG_ALLERGIES_Nsaids.add("Paracetamol");
            list_DRUG_ALLERGIES_Nsaids.add("Ibuprofen");
            list_DRUG_ALLERGIES_Nsaids.add("Diclofenac");
            list_DRUG_ALLERGIES_Nsaids.add("Aceclofenac");
            list_DRUG_ALLERGIES_Nsaids.add("Naproxen");

            // Eye Drops
            list_DRUG_ALLERGIES_EyeDrops.add("Tropicamide_P");
            list_DRUG_ALLERGIES_EyeDrops.add("Tropicamide");
            list_DRUG_ALLERGIES_EyeDrops.add("Timolol");
            list_DRUG_ALLERGIES_EyeDrops.add("Homide");
            list_DRUG_ALLERGIES_EyeDrops.add("Latanoprost");
            list_DRUG_ALLERGIES_EyeDrops.add("Brimonidine");
            list_DRUG_ALLERGIES_EyeDrops.add("Travoprost");
            list_DRUG_ALLERGIES_EyeDrops.add("Tobramycin");
            list_DRUG_ALLERGIES_EyeDrops.add("Moxifloxacin");
            list_DRUG_ALLERGIES_EyeDrops.add("Homatropine");
            list_DRUG_ALLERGIES_EyeDrops.add("Pilocarpine");
            list_DRUG_ALLERGIES_EyeDrops.add("Cyclopentolate");
            list_DRUG_ALLERGIES_EyeDrops.add("Atropine");
            list_DRUG_ALLERGIES_EyeDrops.add("Phenylephrine");
            list_DRUG_ALLERGIES_EyeDrops.add("Tropicacyl");
            list_DRUG_ALLERGIES_EyeDrops.add("Paracain");
            list_DRUG_ALLERGIES_EyeDrops.add("Ciplox");
            list_DRUG_ALLERGIES_EyeDrops.add("Tropicamide P + Distilled Water");
            list_DRUG_ALLERGIES_EyeDrops.add("Tropicamide P + Lubrex");

        }

    }

    private void addListDataForContactAllergiesOnAllergiesTab() {

        list_CONTACT_ALLERGIES.add("Alcohol");
        list_CONTACT_ALLERGIES.add("Latex");
        list_CONTACT_ALLERGIES.add("Betadine");
        list_CONTACT_ALLERGIES.add("Adhesive Tape");
        list_CONTACT_ALLERGIES.add("Tegaderm");
        list_CONTACT_ALLERGIES.add("Transpore");

    }

    private void addListDataForFoodAllergiesOnAllergiesTab() {

        list_FOOD_ALLERGIES.add("All Seafood");
        list_FOOD_ALLERGIES.add("Corn");
        list_FOOD_ALLERGIES.add("Egg");
        list_FOOD_ALLERGIES.add("Milk Proteins");
        list_FOOD_ALLERGIES.add("Peanuts");
        list_FOOD_ALLERGIES.add("Shellfish Only");
        list_FOOD_ALLERGIES.add("Soy Protein");
        list_FOOD_ALLERGIES.add("Lactose");
        list_FOOD_ALLERGIES.add("Mushroom");

    }
}
