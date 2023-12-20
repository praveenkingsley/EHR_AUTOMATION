package data;
import pages.commonElements.CommonActions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
public class Template_Data {
    public static boolean sTODAY_FILLED_EYE_TEMPLATE =false;
    public String sVISIT_TYPE="General Checkup";
    public String sVISIT_COMMENTS="TEST COMMENT";
    //CHIEF COMPLAINTS
    public String[] sCHIEF_COMPLAINTS={"Headache/Strain"};
    public String[] sSIDE_CHIEF_COMPLAINT={"B/E"};
    public String[] sDURATION_CHIEF_COMPLAINT={"8"};
    public String[] sDURATION_UNIT_CHIEF_COMPLAINTS={"Days"};
    public String sCOMMENTS_CHIEF_COMPLAINTS="TEST COMMENT...";
    //OPHTHALMIC HISTORY
    public String[] sOPHTHALMIC_HISTORY={"Contact Lens"};
    public String[] sRIGHT_DURATION={"6"};
    public String[] sRIGHT_DURATION_UNIT={"Days"};
    public String[] sLEFT_DURATION={"7"};
    public String[] sLEFT_DURATION_UNIT={"Days"};
    public boolean sCOPY_RIGHT_TO_LEFT=true;
    public String sCOMMENTS_OPHTHALMIC_HISTORY="TEST COMMENT...";
    //Systemic History
    public String[] sSYSTEMIC_HISTORY={"Hypertension"};
    public String[] sDURATION_SYSTEMIC_HISTORY={"10"};
    public String[] sDURATION_UNIT_SYSTEMIC_HISTORY={"Years"};
    public String[] sCOMMENT_SYSTEMIC_HISTORY={"TEST COMMENT..."};
    //All Allergies
    private final String[] AGENT_DRUG_ALLERGIES={"Ciplox"};
    public String[] sDURATION_DRUG_ALLERGIES={"6"};
    public String[] sDURATION_UNIT_DRUG_ALLERGIES={"Days"};
    public String[] sCONTACT_ALLERGIES={"Latex"};
    public String[] sDURATION_CONTACT_ALLERGIES={"9"};
    public String[] sDURATION_UNIT_CONTACT_ALLERGIES={"Months"};
    public String[] sFOOD_ALLERGIES={"Peanuts"};
    public String[] sDURATION_FOOD_ALLERGIES={"9"};
    public String[] sDURATION_UNIT_FOOD_ALLERGIES={"Months"};
    public String sCOMMENTS_ALLERGIES="TEST ..";
    //Vital Signs
    public String sTEMPERATURE_VITAL_SIGNS="100";
    public String sPULSE_VITAL_SIGNS="72";
    public String sBLOOD_PRESSURE_VITAL_SIGNS="120";
    public String sRR_VITAL_SIGNS="11";
    public String sSP02_VITAL_SIGNS="98";
    public String sHEIGHT_VITAL_SIGNS="180";
    public String sWEIGHT_VITAL_SIGNS="88";
    public String sCOMMENTS_VITAL_SIGNS ="Test Comment ..";
    //REFRACTION TAB
    public String sCOMMENT_VISUAL_ACUITY="TEST";
    public boolean sVISUAL_ACUITY_NOT_EXAMINED=true;
    public String sROD_VISUAL_ACUITY="6/24";
    public boolean sIOP_NOT_EXAMINED=false;
    public String sIOP_TIME="7:00:PM";
    public String sIOP_COMMENT="IOP TEST";
    public String sOCCULAR_ASSESSMENT="1.8";
    public String sAUTO_REFRACTION="2";
    public String sSphDistant="+5.50";
    public String sSphAdd="1.50";
    public String sCylDistant="-3.25";
    public String sAxisDistant="100";
    public String sVisionDistant="6/6";
    public String sVisionNear="N8";
    public String sDrugUsedDilatedRefraction="Ciplox";
    public String sTypesOfLensPGP="Bifocal";
    public String sCommentRefraction="Refraction test comment";
    //Glass Prescription
    public String sTYPE_OF_LENS_GLASS_PRESCRIPTION="Progressive";
    public String sIPD_GLASS_PRESCRIPTION="12";
    public String sSIZE_GLASS_PRESCRIPTION="3";
    public String sLENS_MATERIAL="Trivex";
    public String sLENS_TINT="Yellow";
    public String sFRAME_MATERIAL="Plastic";
    public String sDIA_GLASS_PRESCRIPTION="8";
    public String sPRISM_BASE_GLASS_PRESCRIPTION="18";
    public String sFITTING_HEIGHT_GLASS_PRESCRIPTION="28";
    public String sADVICE_GLASS_PRESCRIPTION="TEST123";
    public String sTRAIL_LENS_DETAILS="10";
    public String sFIT_ASSESSMENT="20";
    public String sOVER_REACTION_ACCEPTANCE="99";
    public String sVIRTUAL_NEEDS="12";
    public String sDISTANCE="12";
    public String sFINAL_RECOMMENDATIONS= CommonActions.getRandomAlphanumericString(2);
    public String sLVA_PROBLEMS="Loss of peripheral vision";
    //RETINOSCOPY
    public String sTOP_RETINOSCOPY="2";
    public String sBOTTOM_RETINOSCOPY="4";
    public String sLEFT_RETINOSCOPY="6";
    public String sRIGHT_RETINOSCOPY="8";
    public String sVA_RETINOSCOPY="18";
    public String sHA_RETINOSCOPY="7";
    public String sDISTANCE_RETINOSCOPY="23";
    public String sDRUG_USED_RETINOSCOPY="Homide";
    public String sCOMMENT_RETINOSCOPY="TEST XYZ..";
    //Keratometry
    public String sKH_VALUE_KERATOMETRY="150";
    public String sKV_VALUE_KERATOMETRY="50";
    public String sKH_AXIS_KERATOMETRY="200";
    public String sKV_AXIS_KERATOMETRY="400";
    //Amsler
    public String sRIGHT_AMSLER="Normal";
    public String sLEFT_AMSLER="Abnormal";
    public String sCOMMENT_AMSLER="TEST 321";
    //CONTACT LENS
    private String BC="10";
    private String DIA="15";
    private String SPH="20";
    private String CYL="10";
    private String AXIS="100";
    private String ADD="90";
    public String sREVISIT_DATE="31/07/2023";
    public String sCOLOR_CONTACT_LENS="GREY";
    public String sTYPE_CONTACT_LENS="Soft";
    public String sADVICE_CONTACT_LENS="Wash your eyes";
    public String sCOLOR_VISION="RGB";
    public String sCONTRAST_SENSITIVITY="1.50";
    public String sCOMMENT_CONTRAST_SENSITIVITY="TEST ..";
    public String sORTHOPTICS="TEST123";
    public String sSTORE="OpticalStore";
    //EXAMINATION TAB
    public String sGENERAL_COMMENT_EXAMINATION="TEST COMMENT 123";
    public String sGENERAL_EXAMINATION_TYPE="Normal";
    public boolean sONE_EYED_EXAMINATION=false;
    public boolean sSQUINT_EXAMINATION=false;
    public boolean sNORMAL_EXAMINATION=false;
    public String sCOMMENT_EXAMINATION="TEST123";
    public String sSCHIRMER_TEST_EXAMINATION="3";
    public String sSELECTED_BTN_EXAMINATION="No/Patent/Normal/Clear/Central/Full/Foveal Reflex";
    public String sPUPIL_SIZE="8";
    public String sPRISM_EXTRAOCULAR_MOVEMENTS="5";
    public String sIOP_METHOD="NCT";
    //INVESTIGATION
    public String sOPHTHAL_SET="glucoma";
    public String sFINDINGS_PERFORMED="TEST 123";
    public String sSEARCH_OPHTHAL_INVESTIGATIONS="Lenstar";
    public String sLABORATORY_SET="cornea";
    public String sRADIOLOGY_SET="huzi";
    public String sProvisionalDiagnosisComment="Demo Provisional Diagnosis Comment";
    //ADVISE
    public String sSTORE_MEDICATION="Automation_Store";
    public String sMEDICATION_SET_LEVEL="All";
    public String sMEDICATION_SET="Cornea set";
    //PROCEDURE
    public String sEYE_REGION_PROCEDURE="Retina";
    public String sPROCEDURE="VR surgery";
    public String sPROCEDURE_SIDE="Left";
    public ArrayList<String> sAGENT_DRUG_ALLERGIES(){
        ArrayList<String> sAGENT_DRUG_ALLERGIES=new ArrayList<>();
        try {
            sAGENT_DRUG_ALLERGIES.addAll(Arrays.asList(AGENT_DRUG_ALLERGIES));
        }catch (NullPointerException e){
        }
        return sAGENT_DRUG_ALLERGIES;
    }
    public HashMap<String,String> sCONTACT_LENS_PRESCRIPTION(){
        HashMap<String,String> data= new HashMap<>();
        data.put("BC",BC);
        data.put("DIA",DIA);
        data.put("SPH",SPH);
        data.put("CYL",CYL);
        data.put("AXIS",AXIS);
        data.put("ADD",ADD);
        return data;
    }
}