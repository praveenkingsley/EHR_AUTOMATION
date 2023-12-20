package data;

import java.util.ArrayList;
import java.util.List;

public class Settings_Data {

    public static final String option_PROFILE_SETTING = "Profile Settings";
    public static final String option_PRACTICE_SETTING = "Practice Settings";
    public static final String option_FACILITY_SETTING = "Facility Settings";
    public static final String option_ORGANISATION_SETTING = "Organisation Settings";
    public static final String option_PRINT_SETTING = "Print Settings";
    public static final String option_MY_ANALYTICS = "My Analytics";
    public static final String option_REPORTS = "Reports";
    public static final String option_EXPENSES = "Expenses";
    public static final String option_FINANCE_REPORTS = "Finance Reports";
    public static final String option_CLINICAL_REPORTS = "Clinical Reports";
    public static final String option_ANALYTICS = "Analytics";

    public static List<String> list_AvailableReferralOptions = new ArrayList<String>();

    public Settings_Data() {
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

    // Adding New user to org or facility level data
    public static final String sSALUTATION = "Mr";
    public static final String sUSER_FULL_NAME = "New user";
    public static final String sEMAIL_ID = "test";
    public static final String sEMAIL_DOMAIN = "@yahoo.com";
    public static final String sUSER_GENDER = "Female";
    public static final String sUSER_MOBILE_NUMBER = "1234567890";
    public static final String sUSER_TELEPHONE_NUMBER = "1234567800";
    public static final String sADDRESS = "BIKKODU";
    public static final String sPINCODE = "560037";
    public static final String sEMPLOYEE_ID = "1234";
    public static final String sDESIGNATION = "DR";
    public static final String sREGISTRATION_NUMBER = "555";
    public static final String sYEAR = "1998";
    public static final String sMONTH = "Jan";

    public static final String sFACILITYNAME = "Automation Testing Facility";
    public static final String sFACILITY_CODE = "ATEST1";
    public static final String sFACILITY_DISPLAYNAME = "Automation Testing Facility";
    public static final String sFACILITY_COUNTRY = "India (IN)";
    public static final String sFACILITY_TIMEZONE = "Kolkata";
    public static final String sFACILITY_REGION = "KARNATAKA";
    public static final String sFACILITY_PINCODE = "452001";
    public static final String sFACILITY_CITY = "INDORE";
    public static final String sFACILITY_STATE = "MADHYA PRADESH";
    public static final String sFACILITY_ADDRESS = "TEST";
    public static final String sFACILITY_EMAIL = "abc";
    public static final String sFACILITY_EMAIL_DOMAIN = "@healthgraph.in";
    public static final String sFACILITY_SPECIALTIY = "OPHTHALMOLOGY";
    public static final String sFACILITY_DEPARTMENT = "OUTPATIENT DEPARTMENT";
}
