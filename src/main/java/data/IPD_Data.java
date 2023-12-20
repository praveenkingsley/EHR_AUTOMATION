package data;

import java.util.ArrayList;
import java.util.List;

public class IPD_Data {

    public static final String tab_MY_QUEUE = "My Queue";
    public static final String tab_ALL = "All";
    public static final String tab_NOT_ARRIVED = "Not Arrived";
    public static final String tab_COMPLETED = "Completed";
    public static final String tab_REFERRALS = "Referrals";
    public static final String tab_UNASSIGNED = "Unassigned";
    public static final String tab_MY_APPOINTMENT = "My Appointment";

    //public static final String tab_Scheduled_Today = "IPD_SCHEDULED";
    public static final String tab_Scheduled_Today = "SCHEDULED_TODAY";

    public List<String> List_ANESTHESIA_PLANNED = new ArrayList<String>();
    public String sSURGERY_NAME = "CATRACT";
    public String sSURGEON_NAME = "ROHITH";
    public String sEXTRA_INFORMATION_TO_DOCTOR = "SOME COMPLICATION";
    public String sMEDICATION = "EYE DROP";
    public String sCOMMENT_BOX_IN_OT_CHECKLIST = "COMMENT";
    public String sRESPIRATION_RATE = "12";
    public String sBP = "80";
    public String sPULSE = "60";
    public String sSEDATION_SCORE = "100";
    public String sBOLUS_ML = "60";
    public String sBOLUS_MG = "60";
    public String sO2_SATURATION = "92";
    public String sCOMMENTS_SEDATION_CHART = "COMMENTS";
    public String sNAME_SEDATION_CHART = "Name";
    public String sBILLABLE_UNIT_PRICE = "2";
    public String sCONSUMED_QUANTITY = "2";
    public String sWARD_NOTE = "NOTE";
    public String sHEIGHT = "5.3";
    public String sWEIGHT = "20";
    public String sTEMPERATURE = "32";
    public String sSPo2 = "94";
    public String sOPERATIVE_NOTE_PERSONNEL_COMMENT = "comments";
    public String sIRRIGATION_SOLUTION = "irrigation solution";
    public String sBATCH_NO = "123";
    public String sCOMPLICATION = "Intraoperative Complications";
    public String sPOST_OP_PLAN = "plan";
    public String sTREATMENT_NOTES = "treatment notes";
    public String sPRECAUTIONS = "Advised";
    public String sRECEIVED_BY_FIELD_UNDER_OTHERS_TAB_IN_PAIN_ASSESSMENT_TEMPLATE = "User";
    public String sDOSE_GIVEN_BY = "Manasa";
    public String sPROCEDURE_CODE = "1PRO";


    public IPD_Data() {
        addListOfAnesthesiaPlanned();

    }

    private void addListOfAnesthesiaPlanned() {
        List_ANESTHESIA_PLANNED.add("GA");
        List_ANESTHESIA_PLANNED.add("EA");
        List_ANESTHESIA_PLANNED.add("SA");
        List_ANESTHESIA_PLANNED.add("LA");
        List_ANESTHESIA_PLANNED.add("Sedation");
        List_ANESTHESIA_PLANNED.add("Topical");
        List_ANESTHESIA_PLANNED.add("Sub-tenon's");
        List_ANESTHESIA_PLANNED.add("Sub conjunctival");

    }

}
