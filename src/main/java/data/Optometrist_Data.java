package data;

import java.util.ArrayList;
import java.util.List;

public class Optometrist_Data {
    public List<String> list_TEMPLATES = new ArrayList<String>();
    public List<String> list_UCVA_ABSENT = new ArrayList<String>();
    public String sUCVA_COMMENT = "Vision Tab Comments";
    public String sVISUAL_ACUITY_COMMENT = "Visual acuity comment";
    public String sIPD_VALUE = "2";
    public String sINTROCULAR_PRESSURE_VALUE = "20";

    public Optometrist_Data() {
        addListOfTemplates();
        addListDataForUCVAabsentUnderVisualAcuity();

    }

    private void addListOfTemplates() {
        list_TEMPLATES.add("eye");
        list_TEMPLATES.add(" Optometrist ");
        list_TEMPLATES.add("lens");
        list_TEMPLATES.add("pmt");
        list_TEMPLATES.add("postop");
        list_TEMPLATES.add("quickeye");
        list_TEMPLATES.add("paediatrics");
        list_TEMPLATES.add("orthoptics");
        list_TEMPLATES.add("express");
        list_TEMPLATES.add("trauma");
        list_TEMPLATES.add("freeform");
        list_TEMPLATES.add("nursing");
        list_TEMPLATES.add("vitalsign");
        list_TEMPLATES.add("_blank");

    }

    private void addListDataForUCVAabsentUnderVisualAcuity() {

        list_UCVA_ABSENT.add("PL-");
        list_UCVA_ABSENT.add("PL+");
        list_UCVA_ABSENT.add("FL");
        list_UCVA_ABSENT.add("HM");
        list_UCVA_ABSENT.add("CFCF");
        list_UCVA_ABSENT.add("FC");
        list_UCVA_ABSENT.add("1/60");
        list_UCVA_ABSENT.add("2/60");
        list_UCVA_ABSENT.add("3/60");
        list_UCVA_ABSENT.add("4/60");
        list_UCVA_ABSENT.add("5/60");
        list_UCVA_ABSENT.add("6/60");
        list_UCVA_ABSENT.add("6/36");
        list_UCVA_ABSENT.add("6/24");
        list_UCVA_ABSENT.add("6/18");
        list_UCVA_ABSENT.add("6/12");
        list_UCVA_ABSENT.add("6/9");
        list_UCVA_ABSENT.add("6/7.5");
        list_UCVA_ABSENT.add("6/6");
        list_UCVA_ABSENT.add("6/5");

    }
}
