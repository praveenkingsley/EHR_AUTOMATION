package data.settingsData;

import java.util.ArrayList;
import java.util.List;

public class FacilitySettings_Data {

    //Advice set
    public static final String sADVICE_SET_NAME = "Automation advice set";
    public static final String sADVICE_SPECIALITY_NAME = "Ophthalmology";
    public static final String sADVICE_LANGUAGE = "English";

    public static final String sADVICE_SET_CONTENT = "Automation advice set content";

    //Medical certificate
    public static final String sMEDICAL_CERTIFICATE_NAME = "Testing medical certificate";
    public static final String sUPDATED_MEDICAL_CERTIFICATE_NAME = "Updated medical certificate";
    public static final String sMEDICAL_CERTIFICATE_HEADING = "Medical Certificate for Patient ";
    public static final String sMEDICAL_CERTIFICATE_SUBJECT = "Medical Certificate Request ";
    public static final String sMEDICAL_CERTIFICATE_CONTENT = "Medical Certificate content";

    //Referral message
    public static final String sREFERRAL_MESSAGE_NAME = "Testing referral message";
    public static final String sUPDATED_REFERRAL_MESSAGE_NAME = "Updated referral message";
    public static final String sREFERRAL_MESSAGE_HEADING = "Referral message for patient";
    public static final String sREFERRAL_MESSAGE_SUBJECT = "Referral message request";
    public static final String sREFERRAL_MESSAGE_CONTENT = "Referral message content";

    // procedure note
    public static final String sPROCEDURE_NOTE_NAME = "Procedure note Name";
    public static final String sPROCEDURE_NOTE = "Perform cataract surgery";
    public static final String sPROCEDURE_NOTE_SPECIALITY = "Ophthalmology";


    //Medication list
    public static final String sMEDICINE_NAME = "Proparacaine";
    public static final String sDISPENSING_UNIT = "Tablets";
    public static final String sMEDICINE_CLASS = "Eye Corticosteroids";
    public static final String sGENERIC_NAME = "Acacia";
    public static final String sGENERIC_COMPOSITION_VALUE = "0.001";
    public static final String sGENERIC_COMPOSITION_QUANTITY = "mg";

    //Medication set
    public static final String sMEDICATION_SET_NAME = "Common set";
    public static final String sMEDICINE_TYPE = "Tablet";
    public static final String sMEDICINE_QUANTITY = "2";
    public static final String sMEDICINE_FREQUENCY = "1";
    public static final String sMEDICINE_DURATION = "2";
    public static final String sMEDICINE_DURATION_PERIOD = "Days";
    public static final String sMEDICINE_DOSAGE = "2";
    public static final String sMEDICINE_INTERVAL = "2";
    public static final String sSIDE = "L";
    public static final String sMEDICINE_INSTRUCTION = "One time a day";

    //Ophthal set
    public static final String sOPHTHAL_SET_NAME = "Ophthal set";
    public static final String sINVESTIGATION_TYPE1 = "Standard Investigations";
    public static final String sEYE_REGION_STANDARD_INVESTIGATION = "ALL";
    public static final String sSTANDARD_OPHTHAL_INVESTIGATION_NAME = "Aberrometry";
    public static final String sINVESTIGATION_TYPE2 = "Custom Investigations";
    public static final String sEYE_REGION_CUSTOM_INVESTIGATION = "Glaucoma";
    public static final String sCUSTOM_OPHTHAL_INVESTIGATION_NAME = "CPCV";

    //LABORATORY SET
    public static final String sLABORATORY_SET_NAME = "Laboratory set";
    public static final String sSTANDARD_LABORATORY_INVESTIGATION_NAME = "X-Ray, Orbits";
    public static final String sCUSTOM_LABORATORY_INVESTIGATION_NAME = "MRA";

    public static final String sSet_Name = "AutoSet";
    public static final String sSet_EditName = "UPDATED " + sSet_Name;



    //RADIOLOGY SET
    public static final String sRADIOLOGY_SET_NAME = "Radiology set";
    public static final String sSTANDARD_RADIOLOGY_INVESTIGATION_NAME = "S. Calcium";
    public static final String sCUSTOM_RADIOLOGY_INVESTIGATION_NAME = "cataract";

    //FACILITY CONTACT LIST
    public static final String sCONTACT_GROUP = "TPA";
    public static final String sSALUTATION_PRIMARY_CONTACT = "Mr.";
    public static final String sFIRST_NAME_PRIMARY_CONTACT = "Demo_FName";
    public static final String sMIDDLE_NAME_PRIMARY_CONTACT = "Demo_MName";
    public static final String sLAST_NAME_PRIMARY_CONTACT = "Demo_LName";
    public static final String sCOMPANY_NAME = "Health Graph";
    public static final String sABBREVIATION = "HG";
    public static final String sDISPLAY_NAME = "Bhavani testing auto";
    public static final String sDISPLAY_NAME1 = "E-Meditek TPA Services Limited";
    public static final String sEMAIL_CONTACT = "hg@mail.com";
    public static final String sMOBILE_NUMBER_IN_CONTACT_FORM = "8666666660";
    public static final String sWORK_CONTACT_NUMBER = "8999999999";
    public static final String sPINCODE_IN_CONTACT_FORM = "560037";
    public static final String sSTATE_IN_CONTACT_FORM = "KARNATAKA";
    public static final String sCITY_IN_CONTACT_FORM = "Bengaluru";
    public static final String sADDRESS_1_IN_CONTACT_FORM = "Add1";
    public static final String sADDRESS_2_IN_CONTACT_FORM = "Add2";

    //REFERRAL SOURCE
    public static final String sREFERRAL_NAME = "REFERRAL NAME ";
    public static final String sREFERRAL_MOBILE_NUMBER = "8666666660";
    public static final String sREFERRAL_EMAIL = "ref@gmail.com";
    public static final String sREFERRAL_SPECIALITY = "General medicine";
    public static final String sREFERRAL_LOCATION = "Ophthalpractice";
    public static final String sREFERRAL_CITY = "HASSAN";
    public static final String sREFERRAL_STATE = "KARNATAKA";
    public static final String sREFERRAL_COMMENT = "REFERRAL COMMENT";
    public static List<String> list_AvailableReferralOptions = new ArrayList<String>();

    //WARD SETUP
    public static final String sWARD_NAME = "WARD ABC";
    public static final String sWARD_CODE = "123A";
    public static final String sROOM_NAME = "ROOM A";
    public static final String sROOM_TYPE = "General Ward";
    public static final String sROOM_CODE = "123R";
    public static final String sBED_NAME = "BED1";
    public static final String sBED_CODE = "123B";
    public static final String sTOTAL_BEDS = "12";
    public static final String sBED_PRICE = "1500";

    //OT SETUP
    public static final String sOT_NAME1 = "Bhavani";
    public static final String sOT_NAME2 = "Bhavani ot";
    public static final String sOT_CAPACITY1 = "3";
    public static final String sOT_CAPACITY2 = "4";

    //SURGERY PACKAGE
    public static final String sSURGERY_PACKAGE_ITEM = "surgery item";
    public static final String sSURGERY_PACKAGE_ITEM_TYPE = "Room Rent";
    public static final String sSURGERY_PACKAGE_NAME = "surgery item";
    public static final String sSURGERY_PACKAGE_FACILITY = "Room Rent";
    public static final String sSURGERY_PACKAGE_SPECIALITY = "Ophthalmology";
    public static final String sSURGERY_PACKAGE_SUB_SPECIALITY = "Cataract";
    public static final String sSURGERY_PACKAGE_DEPARTMENT = "OPD";
    public static final String sSURGERY_PACKAGE_CATEGORY = "Administration";
    public static final String sSURGERY_PACKAGE_SUB_CATEGORY = "Opd";
    public static final String sSURGERY_PACKAGE_DISPLAY_NAME = "Surgery Package";
    public static final String sSURGERY_PACKAGE_VALID_FROM = "02/06/2022";
    public static final String sSURGERY_PACKAGE_VALID_TILL = "02/09/2022";
    public static final String sSURGERY_PACKAGE_TYPE = "Procedure";
    public static final String sSURGERY_PACKAGE_SEARCH_TYPE = "Syringing and probing";
    public static final String sPAYER_TYPE_UNDER_SURGERY_PACKAGE_DETAILS = "Dsad";
    public static final String sPAYER_NAME_UNDER_SURGERY_PACKAGE_DETAILS = "invoice";
    public static final String sDAYS_UNDER_SURGERY_PACKAGE_DETAILS = "2";
    public static final String sSURGERY_PACKAGE_COST = "2000";

    //update pricing master
    public static final String sSERVICE_NAME = "Test service";
    public static final String sSERVICE_CODE = "212";
    public static final String sSTANDARD_RATE = "2000";
    public static final String sINTERNAL_COMMENT = "INTERNAL COMMENT";
    public static final String sEXTERNAL_COMMENT = "EXTERNAL COMMENT";

    //Add exception under update pricing master
    public static final String sEXCEPTION1_TYPE = "By Room";
    public static final String sNAME_UNDER_EXCEPTION1 = "General Ward";
    public static final String sAMOUNT_UNDER_EXCEPTION1 = "2000";

    //Add contact pricing under update pricing master
    public static final String sGROUP_NAME_UNDER_CONTACT_PRICING = "Dsad";
    public static final String sPAYEE_LIST_UNDER_CONTACT_PRICING = "invoice";
    public static final String sRATE_CODE_UNDER_CONTACT_PRICING = "123W";
    public static final String sAMOUNT__UNDER_CONTACT_PRICING = "1230";

    //remove this ward setup data

    public FacilitySettings_Data() {
        addOptionsIntoReferral();
    }

    public static void addOptionsIntoReferral() {
        list_AvailableReferralOptions.add("Referring Doctor");
        list_AvailableReferralOptions.add("Staff Referral");
        list_AvailableReferralOptions.add("Campaign");
        list_AvailableReferralOptions.add("Camp");
        list_AvailableReferralOptions.add("Institutional Referral");
        list_AvailableReferralOptions.add("Agent");
        list_AvailableReferralOptions.add("Online");
        list_AvailableReferralOptions.add("Third Party");
        list_AvailableReferralOptions.add("Consultant");
    }
}
