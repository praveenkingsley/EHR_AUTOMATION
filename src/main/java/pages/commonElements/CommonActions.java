package pages.commonElements;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import data.storeData.InventoryStore_Data;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.templates.Page_InventorySearchCommonElements;
import pages.commonElements.templates.eye.Page_EyeTemplate;
import pages.login.Page_Login;
import pages.opd.Page_OPD;
import pages.settings.Page_Settings;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_ItemMaster;
import pages.store.OtStore.Page_OtStore;
import pages.store.Page_Store;
import pages.store.PharmacyStore.Order.Page_Indent;
import pages.store.PharmacyStore.Order.Page_Requisition;
import pages.store.PharmacyStore.Transaction.Page_Purchase;
import pages.store.PharmacyStore.Transaction.Page_Sale;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class CommonActions extends TestBase {

    public static Page_Navbar oPage_Navbar;
    public static Page_NewPatientRegisteration oPage_NewPatientRegisteration;
    public static Page_PatientAppointmentDetails oPage_PatientAppointmentDetails;
    public static EHR_Data oEHR_Data = new EHR_Data();
    Page_InventoryPolicy oPage_InventoryPolicy=new Page_InventoryPolicy(driver);
    public static Page_InventoryFilterCommonElements oPage_InventoryFilterCommonElements = new Page_InventoryFilterCommonElements(driver);
    static Page_Login oPage_Login;
    public static Page_CommonElements oPage_CommonElements;

    /**
     * Click on Add -> Add New Patient Button
     *
     * @throws Exception
     */
    public static void openPatientRegisterationAndAppointmentForm() throws Exception {

        try {
            oPage_Navbar = new Page_Navbar(driver);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);

            // Add New
            // Patients------------------------------------------------------------------------------------------------------------------
            // Validating Default Form Behavior
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_addAppointment),
                    "<b>Add</b> Appointment Button is clicked");

            m_assert.assertInfo(
                    Cls_Generic_Methods
                            .waitForElementToBecomeVisible(oPage_NewPatientRegisteration.button_addNewPatient, 15),
                    "Add New Patient Button is displayed");

            m_assert.assertTrue(
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_addNewPatient),
                    "<b>Add New Patient</b> Button is clicked");

            Cls_Generic_Methods.customWait();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Choose an option from list of Select options
     *
     * @param listOfElements
     * @param sOptionValue
     * @return
     */
    public static boolean selectOptionFromListBasedOnTextOrValue(List<WebElement> listOfElements, String sOptionValue) {

        boolean valueSelected = false;

        for (WebElement e : listOfElements) {
            if (e.getText().trim().equalsIgnoreCase(sOptionValue)
                    || e.getAttribute("value").equalsIgnoreCase(sOptionValue)) {
                System.out.println(e.getText() + " is selected.");
                e.click();
                valueSelected = true;
                break;
            }
        }

        return valueSelected;

    }

    /**
     * Validates that only one Radio button is selected among a list of Radio
     * Buttons
     *
     * @param listOfElements
     * @return
     */
    public static boolean validateOnlyOneRadioButtonIsSelected(List<WebElement> listOfElements) {

        boolean bOnlyOneRadioButtonIsSelected = false;
        int checkedCounter = 0;

        for (WebElement e : listOfElements) {

            String jscriptForPseudoElementCheck_Before = ((JavascriptExecutor) driver)
                    .executeScript(
                            "return window.getComputedStyle(arguments[0], ':before').getPropertyValue('content');", e)
                    .toString();
            String jscriptForPseudoElementCheck_After = ((JavascriptExecutor) driver)
                    .executeScript(
                            "return window.getComputedStyle(arguments[0], ':after').getPropertyValue('content');", e)
                    .toString();

            System.out.println(e.getText() + "     -     ::before = " + jscriptForPseudoElementCheck_Before + "     "
                    + "::after = " + jscriptForPseudoElementCheck_After);

            if (!jscriptForPseudoElementCheck_After.contains("none")) {
                ++checkedCounter;
            }

        }

        if (checkedCounter == 1) {
            bOnlyOneRadioButtonIsSelected = true;
        } else {
            bOnlyOneRadioButtonIsSelected = false;
        }

        return bOnlyOneRadioButtonIsSelected;

    }

    public static boolean validateIf_EHR_ButtonIsClickable(WebElement inputButtonElement) throws Exception {
        boolean bButtonIsClickable = false, bClickActionWorking = false, bUnlickActionWorking = false;

        inputButtonElement.click();
        Thread.sleep(500);

        if (inputButtonElement.getAttribute("class").contains("btn-darkblue")
                || inputButtonElement.getAttribute("class").contains("btn-brown")) {
            bClickActionWorking = true;
        }

        inputButtonElement.click();
        Thread.sleep(500);
        if (!inputButtonElement.getAttribute("class").contains("btn-darkblue")
                || !inputButtonElement.getAttribute("class").contains("btn-brown")) {
            bUnlickActionWorking = true;
        }

        if (bClickActionWorking && bUnlickActionWorking) {
            bButtonIsClickable = true;
        }

        return bButtonIsClickable;
    }

    public static boolean validateIf_EHR_ButtonIsClickable_WithRegression(WebElement inputButtonElement, int elementIndex)
            throws Exception {
        boolean bButtonIsClickable = false, bClickActionWorking = false, bUnlickActionWorking = false;

        String selectedButtonName_Text = inputButtonElement.getText().toString().trim();
        inputButtonElement.click();
        Thread.sleep(500);

        if (inputButtonElement.getAttribute("class").contains("btn-darkblue")
                || inputButtonElement.getAttribute("class").contains("btn-brown")) {
            bClickActionWorking = true;
        }

        inputButtonElement.click();
        Thread.sleep(500);
        if (!inputButtonElement.getAttribute("class").contains("btn-darkblue")) {
            bUnlickActionWorking = true;
        }

        if (bClickActionWorking && bUnlickActionWorking) {
            bButtonIsClickable = true;
        }

        if (Cls_Generic_Methods.getConfigValues("testRunType").equalsIgnoreCase("Regression")) {

            inputButtonElement.click();
            Thread.sleep(500);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);

            int requiredRowIndex = -1;

            // Elements for Disorder WITHOUT Left & Right Selection
            WebElement text_selectedDisorder_Name = null;
            WebElement selectElement_selectedDisorder_Duration = null;
            WebElement selectElement_selectedDisorder_DurationUnit = null;
            WebElement input_selectedDisorder_Comments = null;
            boolean indexFound = false;

            for (WebElement e : oPage_NewPatientRegisteration.row_disordersDetailsAfterButtonClickOnPatientRegForm) {
                List<WebElement> childrenUnderRowElement = e.findElements(By.xpath("./child::*"));

//				if (disorderName.equalsIgnoreCase(childrenUnderRowElement.get(0).getText())
//						|| ( disorderName.contains(childrenUnderRowElement.get(0).getText())  && !childrenUnderRowElement.get(0).getText().equals("") )
//						|| disorderName.replace("_", " ").trim().contains(childrenUnderRowElement.get(0).getText().trim())) {
//					requiredRowIndex = oPage_NewPatientRegisteration.row_disordersDetailsAfterButtonClickOnPatientRegForm.indexOf(e);
//				}

                if (!indexFound) {
                    if (!childrenUnderRowElement.get(0).getText().isEmpty()) {

                        String elementText = childrenUnderRowElement.get(0).getText().trim().replace("_", " ")
                                .replace("+ ", "");
                        String buttonText = selectedButtonName_Text.replace("_", " ").replace("+ ", "");

                        // Special Case where Row Element Name is lot different from Button Name on -
                        // History Tab |Systematic History Section
                        if (selectedButtonName_Text.equals("Benign Prostatic Hyperplasia(BPH)")) {
                            buttonText = selectedButtonName_Text.replace("(BPH)", "");
                        }

                        if (buttonText.equals(elementText)) {
                            requiredRowIndex = oPage_NewPatientRegisteration.row_disordersDetailsAfterButtonClickOnPatientRegForm
                                    .indexOf(e);
                            indexFound = true;
                            break;
                        }
                    }
                }

                if (!indexFound) {
                    if (selectedButtonName_Text.trim()
                            .equalsIgnoreCase(childrenUnderRowElement.get(0).getText().trim())) {
                        requiredRowIndex = oPage_NewPatientRegisteration.row_disordersDetailsAfterButtonClickOnPatientRegForm
                                .indexOf(e);
                        indexFound = true;
                        break;
                    }
                }

//				if (!indexFound) {
//					if (!childrenUnderRowElement.get(0).getText().isEmpty()) {
//						if (disorderName_ButtonText.contains(childrenUnderRowElement.get(0).getText())) {
//							requiredRowIndex = oPage_NewPatientRegisteration.row_disordersDetailsAfterButtonClickOnPatientRegForm
//									.indexOf(e);
//							indexFound = true;
//							break;
//						}
//					}
//				}

            }

            List<WebElement> childrenUnderRowElement = oPage_NewPatientRegisteration.row_disordersDetailsAfterButtonClickOnPatientRegForm
                    .get(requiredRowIndex).findElements(By.xpath("./child::*"));

            int numberOf_ElementSections_UnderSelectedDisorderRow = childrenUnderRowElement.size();

            if (numberOf_ElementSections_UnderSelectedDisorderRow == 5) {
                // Disorder with Left & Right Selection
                // Only in Ophthalmic History Section

            } else if (numberOf_ElementSections_UnderSelectedDisorderRow == 4) {
                // Disorder WITHOUT Left & Right Selection

                Cls_Generic_Methods.scrollToElementByAction(driver,
                        oPage_NewPatientRegisteration.row_disordersDetailsAfterButtonClickOnPatientRegForm
                                .get(requiredRowIndex));

                String assertMessage = null;

                try {

                    text_selectedDisorder_Name = childrenUnderRowElement.get(0).findElement(By.xpath(".//h5"));
                    selectElement_selectedDisorder_Duration = childrenUnderRowElement.get(1)
                            .findElement(By.xpath(".//select"));
                    selectElement_selectedDisorder_DurationUnit = childrenUnderRowElement.get(2)
                            .findElement(By.xpath(".//select"));
                    input_selectedDisorder_Comments = childrenUnderRowElement.get(3)
                            .findElement(By.xpath(".//input[@placeholder='Comment...']"));

                    Cls_Generic_Methods.selectElementByVisibleText(selectElement_selectedDisorder_Duration,
                            oEHR_Data.sDISORDER_DURATION);
                    Cls_Generic_Methods.selectElementByVisibleText(selectElement_selectedDisorder_DurationUnit,
                            oEHR_Data.sDISORDER_UNIT);
                    Cls_Generic_Methods.clickElement(driver, input_selectedDisorder_Comments);
                    Cls_Generic_Methods.sendKeysIntoElement(input_selectedDisorder_Comments,
                            (selectedButtonName_Text + oEHR_Data.sDISORDER_COMMENT).replace(" ", "_"));

                    assertMessage = "ROW DETAILS || Name = " + text_selectedDisorder_Name.getText() + "\t | Duration = "
                            + oEHR_Data.sDISORDER_DURATION + "\t | Duration Unit = " + oEHR_Data.sDISORDER_UNIT
                            + "\t | Comments = "
                            + (selectedButtonName_Text + oEHR_Data.sDISORDER_COMMENT).replace(" ", "_");

                    m_assert.assertTrue(true, assertMessage);

                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    m_assert.assertTrue(false, "Unable to Enter data for " + selectedButtonName_Text + " disorder.");
                }

            }
        }

        return bButtonIsClickable;
    }

    /**
     * Convert the input date in format (dd/MM/yyyy) from Data file to parsed format
     * (d:MMMM:yyy)
     *
     * @param inputDateAsString
     * @return
     * @throws ParseException
     */
    public static String formatInputToRequiredDate(String inputDateAsString) throws ParseException {

        String result = null;

        SimpleDateFormat inputSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date oDate = inputSimpleDateFormat.parse(inputDateAsString);
        result = new SimpleDateFormat("d:MMMM:yyy").format(oDate);

        return result;

    }

    /**
     * Get the count of Days between two dates The input date(s) must in the
     * format dd/MM/yyyy.
     *
     * @param inputDate1
     * @param optionalInputDate2
     * @return
     * @throws Exception
     */
    public static long getDifferencesInDays(String inputDate1, String... optionalInputDate2) throws Exception {

        long returnDiff = 0;

        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            Date firstDate = sdf.parse(inputDate1);
            Date secondDate = null;

            if (optionalInputDate2.length == 0) {
                secondDate = cal.getTime();
            } else {
                secondDate = sdf.parse(optionalInputDate2[0]);
            }

            System.out.println("Getting difference in days between - ");
            System.out.println(firstDate);
            System.out.println(secondDate);

            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            returnDiff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnDiff;
    }

    /**
     * Get the count of Months between two dates The input date(s) must in the
     * format dd/MM/yyyy.
     *
     * @param inputDate
     * @return
     * @throws Exception
     */
    public static int getDifferenceInMonthsForFutureDate(String inputDate) throws Exception {

        int monthsDiff = 0;

        try {
            String dateOnCalendar = inputDate.split("\\/")[0];
            String monthOnCalendar = inputDate.split("\\/")[1];
            String yearOnCalendar = inputDate.split("\\/")[2];

            Calendar calInputDate = new GregorianCalendar(Integer.parseInt(yearOnCalendar),
                    Integer.parseInt(monthOnCalendar), Integer.parseInt(dateOnCalendar));
            Calendar today = new GregorianCalendar();
            today.setTime(new Date());
//			int yearsInBetween = today.get(Calendar.YEAR) - inputDate.get(Calendar.YEAR);
//			int monthsDiff = today.get(Calendar.MONTH) - inputDate.get(Calendar.MONTH);
            int yearsInBetween = calInputDate.get(Calendar.YEAR) - today.get(Calendar.YEAR);
            monthsDiff = calInputDate.get(Calendar.MONTH) - today.get(Calendar.MONTH) - 1;

            long ageInMonths = yearsInBetween * 12 + monthsDiff;
//			long age = yearsInBetween;

            System.out.println("Number of months from input date : " + ageInMonths);
//			System.out.println("Number of years from input date : " + age);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return monthsDiff;
    }

    /**
     * Get the count of Years between two dates The input date(s) must in the
     * format dd/MM/yyyy.
     *
     * @param inputDate
     * @return
     * @throws Exception
     */
    public static int getDifferenceInYearsForFutureDate(String inputDate) throws Exception {

        int yearsInBetween = 0;

        try {
            String dateOnCalendar = inputDate.split("\\/")[0];
            String monthOnCalendar = inputDate.split("\\/")[1];
            String yearOnCalendar = inputDate.split("\\/")[2];

            Calendar calInputDate = new GregorianCalendar(Integer.parseInt(yearOnCalendar),
                    Integer.parseInt(monthOnCalendar), Integer.parseInt(dateOnCalendar));
            Calendar today = new GregorianCalendar();
            today.setTime(new Date());
//			int yearsInBetween = today.get(Calendar.YEAR) - inputDate.get(Calendar.YEAR);
//			int monthsDiff = today.get(Calendar.MONTH) - inputDate.get(Calendar.MONTH);
            yearsInBetween = calInputDate.get(Calendar.YEAR) - today.get(Calendar.YEAR);
            int monthsDiff = calInputDate.get(Calendar.MONTH) - today.get(Calendar.MONTH) - 1;

            long ageInMonths = yearsInBetween * 12 + monthsDiff;

            System.out.println("Number of months from input date : " + ageInMonths);
//			System.out.println("Number of years from input date : " + age);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return yearsInBetween;
    }

    public static boolean validateIf_DrugAllergies_ChildButtonIsClickable(WebElement inputButtonElement, int elementIndex)
            throws Exception {
        boolean bButtonIsClickable = false, bClickActionWorking = false, bUnlickActionWorking = false;

        inputButtonElement.click();
        Thread.sleep(500);

        if (inputButtonElement.getAttribute("class").contains("btn-darkblue")) {
            bClickActionWorking = true;
        }

        inputButtonElement.click();
        Thread.sleep(500);
        if (!inputButtonElement.getAttribute("class").contains("btn-darkblue")) {
            bUnlickActionWorking = true;
        }

        if (bClickActionWorking && bUnlickActionWorking) {
            bButtonIsClickable = true;
        }

        if (Cls_Generic_Methods.getConfigValues("testRunType").equalsIgnoreCase("Regression")) {

            inputButtonElement.click();
            Thread.sleep(500);
            oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);

            String disorderName = inputButtonElement.getText().toString().trim();
            List<WebElement> list_Elements = null;
            List<String> list_BUTTON_DATA = null;
            String subSectionName = null;

            switch (disorderName) {
                case "Antimicrobial Agents":
                    list_Elements = oPage_NewPatientRegisteration.buttons_drugAllergies___Antimicrobial;
                    list_BUTTON_DATA = oEHR_Data.list_DRUG_ALLERGIES_Antimicrobial;
                    subSectionName = "Antimicrobial";
                    break;

                case "Antifungal Agents":
                    list_Elements = oPage_NewPatientRegisteration.buttons_drugAllergies___Antifungal;
                    list_BUTTON_DATA = oEHR_Data.list_DRUG_ALLERGIES_Antifungal;
                    subSectionName = "Antifungal";
                    break;

                case "Antiviral Agents":
                    list_Elements = oPage_NewPatientRegisteration.buttons_drugAllergies___Antiviral;
                    list_BUTTON_DATA = oEHR_Data.list_DRUG_ALLERGIES_Antiviral;
                    subSectionName = "Antiviral";
                    break;

                case "Nsaids":
                    list_Elements = oPage_NewPatientRegisteration.buttons_drugAllergies___Nsaids;
                    list_BUTTON_DATA = oEHR_Data.list_DRUG_ALLERGIES_Nsaids;
                    subSectionName = "Nsaids";
                    break;

                case "Eye Drops":
                    list_Elements = oPage_NewPatientRegisteration.buttons_drugAllergies___EyeDrops;
                    list_BUTTON_DATA = oEHR_Data.list_DRUG_ALLERGIES_EyeDrops;
                    subSectionName = "Eye Drops";
                    break;

                default:
                    list_Elements = null;
                    break;
            }

            int buttonsCounter = 0;
            for (WebElement buttonElement : list_Elements) {

                int index = list_Elements.indexOf(buttonElement);

                if (buttonElement.getText().trim().equals(list_BUTTON_DATA.get(index))) {
                    buttonsCounter++;
                    m_assert.assertInfo(true, "Validate " + list_BUTTON_DATA.get(index)
                            + " is present under Drug Allergies - " + subSectionName + " section");
                } else {
                    m_assert.assertInfo(false,
                            "Validate " + list_BUTTON_DATA.get(index) + " is present under Drug Allergies - "
                                    + subSectionName + "section. Actual = " + buttonElement.getText());
                }

            }

            if ((list_BUTTON_DATA.size() == buttonsCounter)) {
                m_assert.assertTrue(true,
                        "Validate " + buttonsCounter + " disorders are present under Drug Allergies section");
            } else {
                m_assert.assertTrue(false, "Validate " + list_BUTTON_DATA.size()
                        + " disorders are present under Drug Allergies section. Actual = " + buttonsCounter);
            }

            try {
                for (WebElement eAntimicrobialButton : list_Elements) {

                    boolean buttonIsClickable = false;
                    buttonIsClickable = CommonActions.validateIf_EHR_ButtonIsClickable_WithRegression(eAntimicrobialButton,
                            list_Elements.indexOf(eAntimicrobialButton));

                    m_assert.assertTrue(buttonIsClickable,
                            "Validate Child Elements for " + disorderName + " is clickable.");
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                m_assert.assertTrue(false, "Validate Child Elements for " + disorderName + " is clickable.\n" + e);
            }

        }

        return bButtonIsClickable;
    }

    public static boolean selectTabOnDepartmentPage(List<WebElement> listOfTabsElement, String nameOfTabToSelect) {

        boolean tabIsSelected = false;
        int selectedTabIndex = -1;

        try {
            nameOfTabToSelect = nameOfTabToSelect.toUpperCase();

            for (WebElement eTabElement : listOfTabsElement) {

                String[] splitTabText = eTabElement.getText().split("\\n");
                int currentCountOfPatientsInTab = Integer.parseInt(splitTabText[0]);
                String sTabname = splitTabText[1].trim();

                System.out.println(sTabname + "\t" + currentCountOfPatientsInTab);
                if (nameOfTabToSelect.equalsIgnoreCase(sTabname)) {
                    Cls_Generic_Methods.clickElement(eTabElement);
                    selectedTabIndex = listOfTabsElement.indexOf(eTabElement);
                    break;
                } else if (sTabname.contains("SCHEDULED") && (nameOfTabToSelect.equalsIgnoreCase(IPD_Data.tab_Scheduled_Today))) {
                    selectedTabIndex = listOfTabsElement.indexOf(eTabElement);
                    Cls_Generic_Methods.clickElement(listOfTabsElement.get(selectedTabIndex));
                    break;
                }

            }

            String classOfSelectedtab = Cls_Generic_Methods.getElementAttribute(listOfTabsElement.get(selectedTabIndex),
                    "class");

            if (classOfSelectedtab.contains("active")) {
                tabIsSelected = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tabIsSelected;

    }

    /**
     * Returns the count of patients in the given tab
     *
     * @param listOfTabsElement
     * @param nameOfTabToSelect
     * @return
     */
    public static int getCountOfPatientsInTab(List<WebElement> listOfTabsElement, String nameOfTabToSelect) {

        int currentCountOfPatientsInTab = -1;
        try {
            nameOfTabToSelect = nameOfTabToSelect.toUpperCase();
            for (WebElement eTabElement : listOfTabsElement) {

                String[] splitTabText = eTabElement.getText().split("\\n");

                String sTabname = splitTabText[1].trim();

                if (nameOfTabToSelect.equalsIgnoreCase(sTabname)) {
                    currentCountOfPatientsInTab = Integer.parseInt(splitTabText[0]);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentCountOfPatientsInTab;

    }

    public static boolean selectDepartmentOnApp(String nameOfDeptToSelect) {
        oPage_Navbar = new Page_Navbar(driver);
        boolean tabIsSelected = false;

        try {
            // Works in case the Departments are in a dropdown
            //       Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_storesSelector);
            // Cls_Generic_Methods.customWait();

            Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_departmentFromDropdownSelector);

            for (WebElement eDepartment : oPage_Navbar.list_departmentSelector) {
                String currentDeptName = Cls_Generic_Methods.getTextInElement(eDepartment);

                if (currentDeptName.equalsIgnoreCase(nameOfDeptToSelect)) {
                    eDepartment.click();
                    tabIsSelected = true;
                    m_assert.assertInfo(currentDeptName + " department is selected.");
                    Cls_Generic_Methods.waitForPageLoad(driver, 20);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            // Works in case the Departments are NOT in a dropdown but visible upfront

            try {
                for (WebElement eDepartment : oPage_Navbar.list_departmentsInOneLine) {
                    String currentDeptName = Cls_Generic_Methods.getTextInElement(eDepartment);
                    if (currentDeptName.equalsIgnoreCase(nameOfDeptToSelect)) {
                        eDepartment.click();
                        tabIsSelected = true;
                        m_assert.assertInfo(currentDeptName + " department is selected.");
                        Cls_Generic_Methods.waitForPageLoad(driver, 20);
                        break;
                    }
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                m_assert.assertFatal("" + e1);
            }
        }
        return tabIsSelected;
    }

    public static boolean validateSendToDepartmentButtons(String parentButtonName) {

        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        int iChildButtonsCounter = 0;
        boolean bChildElementsValidated = false;
        WebElement parentButton = null;
        List<WebElement> listOfChildElement = new ArrayList<WebElement>();

        try {

            switch (parentButtonName) {
                case "Receptionist":
                    parentButton = oPage_PatientAppointmentDetails.button_sendToReceptionist;
                    listOfChildElement = oPage_PatientAppointmentDetails.listButtons_sendToReceptionist;
                    break;
                case "Optometrist":
                    parentButton = oPage_PatientAppointmentDetails.button_sendToOptometrist;
                    listOfChildElement = oPage_PatientAppointmentDetails.listButtons_sendToOptometrist;
                    break;
                case "Doctor":
                    parentButton = oPage_PatientAppointmentDetails.button_sendToDoctor;
                    listOfChildElement = oPage_PatientAppointmentDetails.listButtons_sendToDoctor;
                    break;
                case "Nurse":
                    parentButton = oPage_PatientAppointmentDetails.button_sendToNurse;
                    listOfChildElement = oPage_PatientAppointmentDetails.listButtons_sendToNurse;
                    break;
                case "Counsellor":
                    parentButton = oPage_PatientAppointmentDetails.button_sendToCounsellor;
                    listOfChildElement = oPage_PatientAppointmentDetails.listButtons_sendToCounsellor;
                    break;
                case "Floormanager":
                    parentButton = oPage_PatientAppointmentDetails.button_sendToFloormanager;
                    listOfChildElement = oPage_PatientAppointmentDetails.listButtons_sendToFloormanager;
                    break;
                case "Ar Nct":
                    parentButton = oPage_PatientAppointmentDetails.button_sendToArNct;
                    listOfChildElement = oPage_PatientAppointmentDetails.listButtons_sendToArNct;
                    break;
                case "Cashier":
                    parentButton = oPage_PatientAppointmentDetails.button_sendToCashier;
                    listOfChildElement = oPage_PatientAppointmentDetails.listButtons_sendToCashier;
                    break;
                case "Departments":
                    parentButton = oPage_PatientAppointmentDetails.button_sendToDepartments;
                    listOfChildElement = oPage_PatientAppointmentDetails.listButtons_sendToDepartments;
                    break;
                default:
                    break;
            }

            Cls_Generic_Methods.clickElement(driver, parentButton);
            m_assert.assertTrue(parentButtonName + " button is clicked.");
            Thread.sleep(500);

            for (WebElement child : listOfChildElement) {

                String userQueueCount = null, completeString = null;
                WebElement countElement = null;

                iChildButtonsCounter++;
                countElement = child.findElement(By.xpath("//label"));
                completeString = Cls_Generic_Methods.getTextInElement(child);
                userQueueCount = countElement.getText();

                try {
                    System.out.println("------------------------------------");
                    System.out.println(completeString);
                    System.out.println(userQueueCount);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

//				String departmentElementText = child.getAttribute("innerText");
//				String delim = departmentElementText.substring(departmentElementText.length() - 2);
//				delim = delim.substring(delim.length() - 1);
//				int moveBackCounter = 1;
//
//				if(delim.equals(" ")) {
//					moveBackCounter = 2;
//				}
//
//				departmentElementText = departmentElementText.substring(departmentElementText.length() - moveBackCounter);
//				departmentElementText = departmentElementText.trim();
//				System.out.println(departmentElementText);


            }

            m_assert.assertTrue(iChildButtonsCounter + " options are present under " + parentButtonName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bChildElementsValidated;

    }

    /**
     * Fill the details for Dilation Options
     *
     * @param dropsName
     * @param eyeLaterality
     * @param advisingDoctor
     * @param dilatingUser
     * @param dilatingComment
     */
    public static boolean fillDilationDetails(String dropsName, String eyeLaterality, String advisingDoctor,
                                              String dilatingUser, String dilatingComment) {

        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        boolean dilationSuccess = false;
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.modal_dilationOptions, 8);

            Cls_Generic_Methods.selectElementByVisibleText(oPage_PatientAppointmentDetails.select_dilationDrops, dropsName);

            Cls_Generic_Methods.selectElementByVisibleText(oPage_PatientAppointmentDetails.select_dilationEye, eyeLaterality);

            Cls_Generic_Methods.selectElementByVisibleText(oPage_PatientAppointmentDetails.select_dilationAdvisingDoctor, advisingDoctor);

            Cls_Generic_Methods.clearValuesInElement(oPage_PatientAppointmentDetails.input_dilatingUser);
            Thread.sleep(1000);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientAppointmentDetails.input_dilatingUser, dilatingUser);

            Cls_Generic_Methods.clearValuesInElement(oPage_PatientAppointmentDetails.input_dilatingComment);
            Thread.sleep(1000);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientAppointmentDetails.input_dilatingComment, dilatingComment);

            Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_startDilation);
            dilationSuccess = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dilationSuccess;


    }

    /**
     * Fill the details for Dilation Options by giving Indexes of Options
     *
     * @param dropsName
     * @param eyeLaterality
     * @param advisingDoctor
     * @param dilatingUser
     * @param dilatingComment
     * @return
     */
    public static boolean fillDilationDetailsByIndex(int dropsName, int eyeLaterality, int advisingDoctor,
                                                     String dilatingUser, String dilatingComment) {

        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        boolean dilationSuccess = false;
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.modal_dilationOptions, 8);

            Cls_Generic_Methods.selectElementByIndex(oPage_PatientAppointmentDetails.select_dilationDrops, 1);

            Cls_Generic_Methods.selectElementByIndex(oPage_PatientAppointmentDetails.select_dilationEye, 1);

            Cls_Generic_Methods.selectElementByIndex(oPage_PatientAppointmentDetails.select_dilationAdvisingDoctor, 1);

            Cls_Generic_Methods.clearValuesInElement(oPage_PatientAppointmentDetails.input_dilatingUser);
            Thread.sleep(1000);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientAppointmentDetails.input_dilatingUser, dilatingUser);

            Cls_Generic_Methods.clearValuesInElement(oPage_PatientAppointmentDetails.input_dilatingComment);
            Thread.sleep(1000);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientAppointmentDetails.input_dilatingComment, dilatingComment);

            Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_startDilation);
            dilationSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dilationSuccess;


    }

    public static String getDilationBadgeClassForPatient(String concatPatientFullName) {

        String patientName = null, returnClassName = null;
        Page_OPD oPage_OPD = new Page_OPD(driver);
        Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
        try {
            for (WebElement patient : oPage_OPD.rows_patientAppointments) {
                if (patient.isDisplayed()) {

                    List<WebElement> patientDetailsOnRow = patient.findElements(By.xpath("./child::*"));
                    patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");

                    if (concatPatientFullName.equals(patientName.trim())) {
                        Cls_Generic_Methods.clickElement(driver, patient);
                        Cls_Generic_Methods
                                .waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 8);

                        WebElement dilationBadge = driver.findElement(By
                                .xpath("//div[contains(@class,'active-appointment')]//div[@id='dilation-state']/span"));

                        returnClassName = Cls_Generic_Methods.getElementAttribute(dilationBadge, "class");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnClassName;
    }

    public static boolean selectTemplateOption(List<WebElement> listOfTemplateElements, String nameOfTemplateToSelect) {
        boolean templateIsSelected = false;
        Page_EyeTemplate oPage_EyeTemplate = new Page_EyeTemplate(driver);

        try {
            for (WebElement eTemplateElement : listOfTemplateElements) {
                String sTemplateName = Cls_Generic_Methods.getTextInElement(eTemplateElement);
                System.out.println(nameOfTemplateToSelect + " vs " + sTemplateName);
                if (sTemplateName.contains(nameOfTemplateToSelect)) {
                    Cls_Generic_Methods.clickElement(driver, eTemplateElement);
                    Cls_Generic_Methods.customWait();
                    break;
                }
            }

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_EyeTemplate.header_templateHeaderText, 12);
            String templateNameOnUI = Cls_Generic_Methods.getTextInElement(oPage_EyeTemplate.header_templateHeaderText);

            if (templateNameOnUI.contains(nameOfTemplateToSelect)) {
                templateIsSelected = true;
                m_assert.assertTrue(true, "Validate <b>" + nameOfTemplateToSelect + "</b> template is selected and opened. Header of Template = " + templateNameOnUI);
            } else {
                m_assert.assertTrue(false, "Validate <b>" + nameOfTemplateToSelect + "</b> template is selected and opened.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertTrue(false, "Error while selecting the Template - " + nameOfTemplateToSelect + ". \n" + e);
        }

        return templateIsSelected;

    }

    public static boolean selectListOption(List<WebElement> listOfElements, String nameOfOptionToSelect) {
        boolean bElementIsSelected = false;

        try {
            for (WebElement eTemplateElement : listOfElements) {
                String sTemplateName = Cls_Generic_Methods.getTextInElement(eTemplateElement);

                System.out.println(nameOfOptionToSelect + " vs " + sTemplateName);

                if (sTemplateName.contains(nameOfOptionToSelect)) {
                    Cls_Generic_Methods.clickElement(driver, eTemplateElement);
                    Cls_Generic_Methods.customWait(1);
                    bElementIsSelected = true;
                    break;
                }
            }
            m_assert.assertTrue(bElementIsSelected, "Validate <b>" + nameOfOptionToSelect + "</b> template is selected and opened.");


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertTrue(false, "Error while selecting the Option - " + nameOfOptionToSelect + ". \n" + e);
        }

        return bElementIsSelected;
    }

    public static boolean templateBadgeIsRed(WebElement eBadgeElement) {
        boolean templateBadgeIsRed = false;

        try {
            String sBadgeClass = Cls_Generic_Methods.getElementAttribute(eBadgeElement, "class");
            String sBadgeText = Cls_Generic_Methods.getTextInElement(eBadgeElement);
            if (sBadgeClass.contains("my-danger")) {
                templateBadgeIsRed = true;
            }
            m_assert.assertTrue(templateBadgeIsRed, "The colour of Badge " + sBadgeText + " is Red.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return templateBadgeIsRed;
    }

    public static boolean templateBadgeIsGreen(WebElement eBadgeElement) {
        boolean templateBadgeIsGreen = false;

        try {
            String sBadgeClass = Cls_Generic_Methods.getElementAttribute(eBadgeElement, "class");
            String sBadgeText = Cls_Generic_Methods.getTextInElement(eBadgeElement);
            if (sBadgeClass.contains("my-success")) {
                templateBadgeIsGreen = true;
            }
            m_assert.assertTrue(templateBadgeIsGreen, "The colour of Badge " + sBadgeText + " is Green.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return templateBadgeIsGreen;
    }

    public static boolean selectFacility(String... sFacilityCode) {

        String sSwitchToFacilityCode = "";
        boolean bFacilitySelected = false;

        try {
            if (sFacilityCode.length > 0) {
                sSwitchToFacilityCode = sFacilityCode[0];
            } else {
                sSwitchToFacilityCode = "TST";
            }

            sSwitchToFacilityCode = sSwitchToFacilityCode.toUpperCase();
            String currentFacility = Cls_Generic_Methods.getTextInElement(oPage_Navbar.button_facilitySelector);
            if (!currentFacility.contains(sSwitchToFacilityCode)) {
                Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_facilitySelector);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.list_facilitySelector.get(0), 4);

                for (WebElement eFacility : oPage_Navbar.list_facilitySelector) {
                    String sFacilityName = Cls_Generic_Methods.getTextInElement(eFacility);
                    String sFacilityNameCode = sFacilityName.split("-")[1].trim();

                    if (sFacilityName.contains(sSwitchToFacilityCode) && sFacilityNameCode.equalsIgnoreCase(sSwitchToFacilityCode)) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByAction(driver, eFacility),
                                "Selected the Facility - <b>" + sFacilityName + "</b>");
                        Cls_Generic_Methods.waitForPageLoad(driver, 20);
                        bFacilitySelected = true;
                        break;
                    }
                }
            } else if (currentFacility.contains(sSwitchToFacilityCode)) {
                bFacilitySelected = true;
                m_assert.assertTrue(true, "The Facility - <b>" + currentFacility + "</b> is already selected.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Error while selecting the Facility. \n" + e);
        }

        return bFacilitySelected;
    }

    public static boolean loginFunctionality(String expectedLoggedInUser) throws Exception {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_Login = new Page_Login(driver);
        String currentLoggedUserName = null;
        boolean correctUserLoggedIn = false;
        System.out.println("Launching test in " + sBrowser + " Browser.");

        try {

            if (!driver.getTitle().equals(oEHR_Data.sEXPECTED_TITLE)) {
                Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
                Cls_Generic_Methods.waitForPageLoad(driver, 20);
            }

            //for future if captcha is not there
           /* if (!env.toUpperCase().equals("PROD")) {
                selectFacility();
                return true;
            }*/

            try {
                try {
                    if (Cls_Generic_Methods.isElementDisplayed(oPage_Login.login_button)) {
                        String[] inputDataForLogin = oEHR_Data.list_userName.get(expectedLoggedInUser).split(",");
                        String userName = inputDataForLogin[0];
                        String password = inputDataForLogin[1];

                        oPage_Login.login_username.click();
                        Cls_Generic_Methods.clearValuesInElement(oPage_Login.login_username);
                        Cls_Generic_Methods.customWait(1);
                        oPage_Login.login_username.sendKeys(userName);

                        oPage_Login.login_password.click();
                        Cls_Generic_Methods.clearValuesInElement(oPage_Login.login_password);
                        Cls_Generic_Methods.customWait(1);
                        oPage_Login.login_password.sendKeys(password);

                        // oPage_Login.login_button.click();
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Login.login_button);
                        Cls_Generic_Methods.waitForPageLoad(driver, 8);

                        m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 20),
                                "Validate Login successful");

                        selectFacility();

                        Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                        Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_SettingsNdLogout, 5);
                        currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser);
                        String[] userNameAndIdArray = currentLoggedUserName.split("\\n");
                        currentLoggedUserName = userNameAndIdArray[0];
                        Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);

                        m_assert.assertInfo(currentLoggedUserName.equals(expectedLoggedInUser),
                                "Logged In User = " + currentLoggedUserName);
                        return true;
                    }
                } catch (NoSuchElementException e) {
                    m_assert.assertInfo("User already logged in");
                }

                // Get current logged in user
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                //Thread.sleep(1000);
                Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_SettingsNdLogout, 5);
                currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser);
                String[] userNameAndIdArray = currentLoggedUserName.split("\\n");
                currentLoggedUserName = userNameAndIdArray[0];
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);


                if (currentLoggedUserName.equals(expectedLoggedInUser)) {
                    correctUserLoggedIn = true;
                    m_assert.assertInfo(true, "Expected User is Logged in - <b>" + expectedLoggedInUser + "</b> .Current User is " + currentLoggedUserName);
                    return correctUserLoggedIn;
                } else {
                    //logout and login using other credentials

                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_logout);
                    Cls_Generic_Methods.waitForPageLoad(driver, 12);

                    //get username corresponding to given parameter
                    String[] inputDataForLogin = oEHR_Data.list_userName.get(expectedLoggedInUser).split(",");
                    String userName = inputDataForLogin[0];
                    String password = inputDataForLogin[1];

                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_username,10);
                    oPage_Login.login_username.click();
                    Cls_Generic_Methods.clearValuesInElement(oPage_Login.login_username);
                    oPage_Login.login_username.sendKeys(userName);

                    oPage_Login.login_password.click();
                    Cls_Generic_Methods.clearValuesInElement(oPage_Login.login_password);
                    oPage_Login.login_password.sendKeys(password);

                    //oPage_Login.login_button.click();
                    Cls_Generic_Methods.clickElementByJS(driver, oPage_Login.login_button);
                    Cls_Generic_Methods.waitForPageLoad(driver, 8);
                    m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 20),
                            "Validate Login successful");
                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_SettingsNdLogout, 5);
                    currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser);
                    userNameAndIdArray = currentLoggedUserName.split("\\n");
                    currentLoggedUserName = userNameAndIdArray[0];
                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);

                    m_assert.assertInfo(currentLoggedUserName.equals(expectedLoggedInUser),
                            "Logged In User = " + currentLoggedUserName);

                    correctUserLoggedIn = true;
                }
            } catch (Exception e) {
                m_assert.assertWarn(" " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }
        return correctUserLoggedIn;
    }

    public static String getFullPatientName(Model_Patient patient) {
        String finalName = "";
        if (!patient.getsSALUTATION().isEmpty()) {
            finalName = finalName + patient.getsSALUTATION();
        }
        if (!patient.getsFIRST_NAME().isEmpty()) {
            finalName = finalName + " " + patient.getsFIRST_NAME();
        }
        if (!patient.getsMIDDLE_NAME().isEmpty()) {
            finalName = finalName + " " + patient.getsMIDDLE_NAME();
        } else {
//            finalName = finalName + " ";
        }
        if (!patient.getsLAST_NAME().isEmpty()) {
            finalName = finalName + " " + patient.getsLAST_NAME();
        }
        m_assert.assertTrue(true, "Patient name - " + finalName);
        return finalName;
    }

    public static String dateFormatForIPDTab(String... inputDateAsString) throws ParseException {

        String result = null;

        SimpleDateFormat inputSimpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        if (inputDateAsString.length > 0) {
            Date oDate = inputSimpleDateFormat.parse(inputDateAsString[0]);
            result = new SimpleDateFormat("dd MMM''yy").format(oDate);
        } else {
            Calendar calendar = Calendar.getInstance();
            Date oDate = calendar.getTime();
            result = new SimpleDateFormat("dd MMM''yy").format(oDate);
        }
        return result;
    }

    public static boolean selectPatientNameInOpd(List<WebElement> listOfPatientNames, String expectedPatientName) {
        boolean nameIsSelected = false;
        String patientName = null;
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            for (WebElement eTabElement : listOfPatientNames) {

                if (eTabElement.isDisplayed()) {
                    List<WebElement> patientDetailsOnRow = eTabElement.findElements(By.xpath("./child::*"));
                    patientName = Cls_Generic_Methods.getElementAttribute(patientDetailsOnRow.get(0), "title");

                    if (expectedPatientName.equals(patientName.trim())) {
                        Cls_Generic_Methods.clickElement(driver, eTabElement);
                        nameIsSelected = true;
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection,
                                16);
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

    public static boolean selectPatientNameInIpd(List<WebElement> listOfPatientNames, String expectedPatientName) {
        boolean nameIsSelected = false;
        String patientName = null;
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            for (WebElement eTabElement : listOfPatientNames) {
                if (eTabElement.isDisplayed()) {
                    patientName = Cls_Generic_Methods.getTextInElement(eTabElement);
                    String spatientName[] = patientName.trim().split("\n");
                    if (expectedPatientName.equals(spatientName[0])) {
                        Cls_Generic_Methods.clickElement(driver, eTabElement);
                        nameIsSelected = true;
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 16);
                        break;
                    }
                }
            }
            m_assert.assertTrue(nameIsSelected, "Patient name found in IPD: <b> " + expectedPatientName + "</b>");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nameIsSelected;
    }

    public static boolean selectPatientNameInOT(List<WebElement> listOfPatientNames, String expectedPatientName) {
        boolean nameIsSelected = false;
        String patientName = null;
        oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);

        try {

            for (WebElement eTabElement : listOfPatientNames) {

                if (eTabElement.isDisplayed()) {
                    patientName = Cls_Generic_Methods.getTextInElement(eTabElement);
                    if (expectedPatientName.equals(patientName.trim())) {
                        Cls_Generic_Methods.clickElement(driver, eTabElement);
                        nameIsSelected = true;
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.img_patientProfilePicOnPatientDetailsSection, 16);

                        break;
                    }
                }
            }
            m_assert.assertTrue(nameIsSelected, "Patient name found in OT: <b> " + expectedPatientName + "</b>");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nameIsSelected;
    }

    public static boolean selectOptionUnderSettings(String inputOptionName) {
        boolean operationSuccess = false;
        oPage_Navbar = new Page_Navbar(driver);
        Page_Settings oPage_Settings = new Page_Settings(driver);

        try {
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.text_currentUser, 5);

            for (WebElement eOption : oPage_Navbar.list_OptionsUnderSettingsAndLogout) {
                String sOptionText = Cls_Generic_Methods.getTextInElement(eOption);

                if (sOptionText.equalsIgnoreCase(inputOptionName)) {
                    Cls_Generic_Methods.clickElement(eOption);
                    m_assert.assertInfo(true, "Selected Option <b>" + sOptionText + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Settings.panel_SettingsPanelOnLeft, 16);
                    Cls_Generic_Methods.scrollToElementByJS(oPage_Settings.panel_SettingsPanelOnLeft);
                    operationSuccess = true;
                    break;
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return operationSuccess;
    }

    public static boolean selectOptionFromLeftInSettingsPanel(String sInputParentOption, String sInputChildOption) {
        boolean operationSuccess = false;
        boolean parentOptionSelected = false;
        Page_Settings oPage_Settings = new Page_Settings(driver);


        try {
            Cls_Generic_Methods.scrollToElementByJS(oPage_Settings.header_panelOnLeft);

            for (WebElement parentElement : oPage_Settings.list_ParentOptionsOnLeft) {
                String sParentOptionTextOnUI = Cls_Generic_Methods.getTextInElement(parentElement);
                sParentOptionTextOnUI = sParentOptionTextOnUI.split("\\n")[0];

                System.out.println(sParentOptionTextOnUI);
                int requiredIndex = oPage_Settings.list_ParentOptionsOnLeft.indexOf(parentElement);

                if (sParentOptionTextOnUI.equalsIgnoreCase(sInputParentOption)) {
                    Cls_Generic_Methods.scrollToElementByJS(parentElement);
                    if (requiredIndex != 0) {
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Settings.list_ArrowBesideParentOptionsOnLeft.get(requiredIndex));
                        Cls_Generic_Methods.customWait(1);
                    }
                    parentOptionSelected = true;
                    m_assert.assertInfo("Selected Option in the Left Panel = <b>" + sParentOptionTextOnUI + "</b>");

                    for (WebElement childElement : oPage_Settings.list_ChildOptionsOnLeft) {

                        if (childElement.isDisplayed()) {
                            Cls_Generic_Methods.scrollToElementByJS(oPage_Settings.header_panelOnLeft);
                            String sChildOptionTextOnUI = Cls_Generic_Methods.getTextInElement(childElement);

                            System.out.println(sChildOptionTextOnUI);
                            System.out.println(oPage_Settings.list_ChildOptionsOnLeft.indexOf(childElement));

                            if (sChildOptionTextOnUI.equalsIgnoreCase(sInputChildOption)) {
                                Cls_Generic_Methods.clickElementByAction(driver, childElement);
                                Cls_Generic_Methods.customWait(4);
                                m_assert.assertInfo("Selected Sub Option in the Left Panel = <b>" + sChildOptionTextOnUI + "</b>");
                                operationSuccess = true;
                                break;
                            }
                        }
                    }
                }

                if (parentOptionSelected) {
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return operationSuccess;
    }

    public static String getRandomUniqueString(int... length) {

        String randomString = "";
        int randomStringLength = 3;

        try {
            if (length.length > 0) {
                randomStringLength = length[0];
            }

            final char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
            final Random random = new Random();
            for (int i = 1; i <= randomStringLength; i++) {
                randomString = randomString + chars[random.nextInt(chars.length)];
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return randomString;
    }

    public static boolean getActionOfFacilityStores(List<WebElement> facilityNameList, String expectedFacilityName,
                                                    List<WebElement> storeColumn, List<WebElement> actionColumn, String actionToPerform) {

        boolean bActionStatus = false;
        int indexOfFacility = -1;
        String facilityNameInStoreSetup = null;
        List<WebElement> listOfRequiredStores = new ArrayList<>();
        List<WebElement> listOfRequiredButtons = new ArrayList<>();
        int requiredRowIndex = -1;

        try {
            for (WebElement facilityName : facilityNameList) {
                if (expectedFacilityName.contains(Cls_Generic_Methods.getTextInElement(facilityName))) {
                    facilityNameInStoreSetup = Cls_Generic_Methods.getTextInElement(facilityName);
                    m_assert.assertInfo("<b> " + facilityNameInStoreSetup + " </b> is present in Facility Name Column");
                    indexOfFacility = facilityNameList.indexOf(facilityName);
                    break;
                }
            }
            List<WebElement> storesNameList = storeColumn.get(indexOfFacility).
                    findElements(By.xpath("./child::*"));
            List<WebElement> listOfStores = storesNameList.get(0).findElements(By.xpath(".//tr"));


            for (WebElement storeName : listOfStores) {
                listOfRequiredStores.add(storeName);
            }
            List<WebElement> buttonNameList = actionColumn.get(indexOfFacility).
                    findElements(By.xpath("./child::*"));

            List<WebElement> listOfStoresButtonsRow = buttonNameList.get(0).findElements(By.xpath(".//tr"));

            for (WebElement buttonNameRow : listOfStoresButtonsRow) {
                listOfRequiredButtons.add(buttonNameRow);
            }

            for (WebElement buttonNamesInRow : listOfRequiredButtons) {

                if (Cls_Generic_Methods.getTextInElement(buttonNamesInRow).contains("Disable")) {

                    requiredRowIndex = listOfRequiredButtons.indexOf(buttonNamesInRow);
                    break;
                }
            }

            List<WebElement> listOfStoresButtonsInOneRow = listOfStoresButtonsRow.get(requiredRowIndex).findElements(By.xpath(".//td"));

            for (WebElement buttonName : listOfStoresButtonsInOneRow) {
                if (Cls_Generic_Methods.getTextInElement(buttonName).contains(actionToPerform)) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(buttonName),
                            "<b> " + actionToPerform + " </b> Button is clicked for First active store In " + facilityNameInStoreSetup);
                    ;
                    bActionStatus = true;
                    break;
                }
            }


        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return bActionStatus;
    }


    public static boolean getActionsOfSelectedStoreOfFacility(List<WebElement> facilityNameList, String expectedFacilityName,
                                                              List<WebElement> storeColumn, List<WebElement> actionColumn,
                                                              String actionToPerform, String expectedStoreName, String condition) {

        // here actionToPerform stands for actions like : edit,link,unlink,disable
        //condition stands for : enable , disable

        boolean bActionStatus = false;
        int indexOfFacility = -1;
        String facilityNameInStoreSetup = null;
        List<WebElement> listOfRequiredStores = new ArrayList<>();
        List<WebElement> listOfRequiredButtons = new ArrayList<>();
        int requiredRowIndex = -1;
        int requiredStoreIndex = -1;

        try {
            for (WebElement facilityName : facilityNameList) {
                if (expectedFacilityName.contains(Cls_Generic_Methods.getTextInElement(facilityName))) {
                    facilityNameInStoreSetup = Cls_Generic_Methods.getTextInElement(facilityName);
                    m_assert.assertInfo("<b> " + facilityNameInStoreSetup + " </b> is present in Facility Name Column");
                    indexOfFacility = facilityNameList.indexOf(facilityName);
                    break;
                }
            }
            List<WebElement> storesNameList = storeColumn.get(indexOfFacility).
                    findElements(By.xpath("./child::*"));
            List<WebElement> listOfStores = storesNameList.get(0).findElements(By.xpath(".//tr//b"));
            for (WebElement storeName : listOfStores) {
                listOfRequiredStores.add(storeName);
            }

            for (WebElement storeName : listOfRequiredStores) {
                if (Cls_Generic_Methods.getTextInElement(storeName).equals(expectedStoreName)) {
                    requiredStoreIndex = listOfRequiredStores.indexOf(storeName);
                    break;
                }
            }
            List<WebElement> buttonNameList = actionColumn.get(indexOfFacility).
                    findElements(By.xpath("./child::*"));


            List<WebElement> listOfStoresButtonsRow = buttonNameList.get(0).findElements(By.xpath(".//tr"));

            for (WebElement buttonNameRow : listOfStoresButtonsRow) {
                listOfRequiredButtons.add(buttonNameRow);
            }


            for (WebElement buttonNamesInRow : listOfRequiredButtons) {

                if (Cls_Generic_Methods.getTextInElement(buttonNamesInRow).contains(condition)) {
                    requiredRowIndex = requiredStoreIndex;
                    break;
                }
            }

            List<WebElement> listOfStoresButtonsInOneRow = listOfStoresButtonsRow.get(requiredRowIndex).findElements(By.xpath(".//td"));

            for (WebElement buttonName : listOfStoresButtonsInOneRow) {
                if (Cls_Generic_Methods.getTextInElement(buttonName).contains(actionToPerform)) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,buttonName),
                            "<b> " + actionToPerform + " </b> button is clicked for <b> " + expectedStoreName + " </b> In <b>  " + facilityNameInStoreSetup + " </b> ");
                    bActionStatus = true;
                    break;
                }
            }


        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return bActionStatus;
    }

    public static boolean selectOptionFromLeftInInventoryStorePanel(String sInputParentOption, String sInputChildOption) {
        boolean operationSuccess = false;
        boolean parentOptionSelected = false;
        Page_Store oPage_Store = new Page_Store(driver);


        try {
            try {
                Cls_Generic_Methods.scrollToElementByJS(oPage_Store.header_panelOnLeft);
            } catch (Exception e) {
                System.out.println(e);
            }

            for (WebElement parentElement : oPage_Store.list_ParentOptionsOnLeft) {
                String sParentOptionTextOnUI = Cls_Generic_Methods.getTextInElement(parentElement);
                sParentOptionTextOnUI = sParentOptionTextOnUI.split("\\n")[0];

                System.out.println(sParentOptionTextOnUI);
                int requiredIndex = oPage_Store.list_ParentOptionsOnLeft.indexOf(parentElement);

                if (sParentOptionTextOnUI.equalsIgnoreCase(sInputParentOption)) {
                    Cls_Generic_Methods.scrollToElementByJS(parentElement);
                    if (requiredIndex != 0) {
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_Store.list_namesParentOptionsOnLeft.get(requiredIndex));
                        Cls_Generic_Methods.customWait(1);
                    }
                    parentOptionSelected = true;
                    m_assert.assertInfo("Selected Option in the Left Panel = <b>" + sParentOptionTextOnUI + "</b>");

                    for (WebElement childElement : oPage_Store.list_ChildOptionsOnLeft) {

                        if (childElement.isDisplayed()) {
                            try {
                                Cls_Generic_Methods.scrollToElementByJS(oPage_Store.header_panelOnLeft);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            String sChildOptionTextOnUI = Cls_Generic_Methods.getTextInElement(childElement);

                            System.out.println(sChildOptionTextOnUI);
                            System.out.println(oPage_Store.list_ChildOptionsOnLeft.indexOf(childElement));

                            if (sChildOptionTextOnUI.equalsIgnoreCase(sInputChildOption)) {
                                Cls_Generic_Methods.scrollToElementByJS(childElement);
                                Cls_Generic_Methods.customWait();
                                Cls_Generic_Methods.clickElementByAction(driver, childElement);
                                Cls_Generic_Methods.customWait(4);
                                m_assert.assertInfo("Selected Sub Option in the Left Panel = <b>" + sChildOptionTextOnUI + "</b>");
                                operationSuccess = true;
                                break;
                            }
                        }
                    }
                }

                if (parentOptionSelected) {
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return operationSuccess;
    }

    public static boolean selectStoreOnApp(String nameOfStoreToSelect) {
        oPage_Navbar = new Page_Navbar(driver);
        boolean tabIsSelected = false;

        try {
            //Checking whether dropdown is already clicked
            if(!Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.input_searchStore,1)){
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_storesSelector);
            }

            Cls_Generic_Methods.clearValuesInElement(oPage_Navbar.input_searchStore);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_Navbar.input_searchStore,nameOfStoreToSelect);
            Cls_Generic_Methods.customWait(2);
            // Works in case the Stores are in a dropdown
           // Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_storesSelector);

            // Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_departmentFromDropdownSelector);

            //  for (WebElement eStore : oPage_Navbar.list_storesSelector) {
            for (WebElement eStore : oPage_Navbar.updated_list_storesSelector) {

                String currentStoreName = Cls_Generic_Methods.getTextInElement(eStore);

                if (currentStoreName.equals(nameOfStoreToSelect)) {
                    eStore.click();
                    tabIsSelected = true;
                    m_assert.assertInfo(currentStoreName + " store is selected.");
                    Cls_Generic_Methods.waitForPageLoad(driver, 20);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return tabIsSelected;
    }

    public static boolean getActionToPerformInInventorySetting(List<WebElement> categoryNameList, String expectedCategoryName,
                                                               List<WebElement> categoryActionsList, String actionToPerform) {

        boolean bActionStatus = false;
        boolean bCategoryFound = false;
        int indexOfCategoryName = -1;

        try {
            for (WebElement categoryName : categoryNameList) {
                if (expectedCategoryName.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(categoryName))) {
                    indexOfCategoryName = categoryNameList.indexOf(categoryName);
                    bCategoryFound = true;
                    break;
                }
            }
            m_assert.assertTrue(bCategoryFound, expectedCategoryName + " is present in category master list");

            if (bCategoryFound) {

                List<WebElement> buttonNameList = categoryActionsList.get(indexOfCategoryName).
                        findElements(By.xpath("./child::*"));
                List<WebElement> listOfButtons = buttonNameList.get(0).findElements(By.xpath(".//td/*"));

                for (WebElement eButton : listOfButtons) {
                    if (Cls_Generic_Methods.isElementDisplayed(eButton)) {
                        if (actionToPerform.contains(Cls_Generic_Methods.getTextInElement(eButton))) {
                            bActionStatus = true;
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(eButton),
                                    actionToPerform + " button clicked for " + expectedCategoryName);

                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return bActionStatus;
    }

    public static boolean getActionButtonStatusInInventorySetting(List<WebElement> nameList, String expectedName,
                                                                  List<WebElement> nameActionsList, String actionToPerform) {

        boolean bActionStatus = false;
        boolean bNameFound = false;
        int indexOfName = -1;

        try {
            for (WebElement name : nameList) {
                if (expectedName.equalsIgnoreCase(Cls_Generic_Methods.getTextInElement(name))) {
                    indexOfName = nameList.indexOf(name);
                    bNameFound = true;
                    break;
                }
            }
            m_assert.assertTrue(bNameFound, "<b>" + expectedName + "</b>" + " is present list");

            if (bNameFound) {

                List<WebElement> buttonNameList = nameActionsList.get(indexOfName).
                        findElements(By.xpath("./child::*"));
                List<WebElement> listOfButtons = buttonNameList.get(0).findElements(By.xpath(".//td/*"));

                for (WebElement eButton : listOfButtons) {
                    if (Cls_Generic_Methods.isElementDisplayed(eButton)) {
                        if (actionToPerform.contains(Cls_Generic_Methods.getTextInElement(eButton))) {
                            bActionStatus = true;
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return bActionStatus;
    }

    public static boolean selectOptionFromDropdown(List<WebElement> listOfCategoryElements, String nameOfCategoryToSelect) {
        boolean categorySelected = false;

        try {
//            Page_StoreSetUp oPage_storeSetUp = new Page_StoreSetUp(driver);
            for (WebElement eCategoryElement : listOfCategoryElements) {
                String sCategoryName = Cls_Generic_Methods.getTextInElement(eCategoryElement);
                System.out.println(nameOfCategoryToSelect + " vs " + sCategoryName);
                if (sCategoryName.contains(nameOfCategoryToSelect)) {
                    categorySelected = true;
                    Cls_Generic_Methods.customWait(2);
                    Cls_Generic_Methods.clickElement(eCategoryElement);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertTrue(false, "Error while selecting the category - " + nameOfCategoryToSelect + ". \n" + e);
        }

        return categorySelected;

    }

    public static String getRandomString(int... length) {

        String randomString = "";
        int randomStringLength = 3;

        try {
            if (length.length > 0) {
                randomStringLength = length[0];
            }

            final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            final Random random = new Random();
            for (int i = 1; i <= randomStringLength; i++) {
                randomString = randomString + chars[random.nextInt(chars.length)];
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return randomString;
    }

    public static String getRandomAlphanumericString(int... length) {

        String randomString = "";
        int randomStringLength = 3;

        try {
            if (length.length > 0) {
                randomStringLength = length[0];
            }

            final char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
            final Random random = new Random();
            for (int i = 1; i <= randomStringLength; i++) {
                randomString = randomString + chars[random.nextInt(chars.length)];
            }
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

        return randomString;
    }

    /**
     * Returns a formatted Date or Time based on Input Date or Time
     * Example -
     * <ul>
     * <li>   sInitialPattern = <b>dd/MM/yyyy</b>
     * <li>   sOutputPattern = yyyy-MM-dd
     * <li>   sInitialPattern = <b>30/08/2022</b>
     * <li>   Result = 2022-08-30
     * </ul>
     *
     * <ul>
     * <b>K:mm a</b>    ->    hh:mm a  <br>
     * <b>3:56 AM</b>   ->    03:56 am
     * </ul>
     * <p>
     * Gist = https://docs.oracle.com/javase/9/docs/api/java/text/SimpleDateFormat.html
     *
     * @param sInitialPattern Input Pattern for Date or Time
     * @param sOutputPattern  Output Pattern for Date or Time
     * @return String value of formatted Date or Time
     * @throws ParseException
     */
    public static String getRequiredFormattedDateTime(String sInitialPattern, String sOutputPattern, String sInputDateOrTime) {

        String sResult = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(sInitialPattern);
            Date date = formatter.parse(sInputDateOrTime);
            sResult = new SimpleDateFormat(sOutputPattern).format(date);
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

        return sResult;

    }

    /**
     * Returns a formatted STRING value of Final Calculation with two decimal places
     *
     * @param sUnitCostWithoutTax Input
     * @param sUnitCount          Input
     * @param sTaxPercentage      Input
     * @param sDiscount           Input
     *                            <ul>
     *                             <li>sDiscount can accept values for both <b>Unit Currency</b> & <b>Percentage</b></li>
     *                             <li>For Discount in <b>Currency Units</b>, pass sDiscount value <b>WITHOUT %</b> symbol</li>
     *                             <li>For Discount in <b>Percentage</b>, pass sDiscount value <b>WITH %</b> symbol</li>
     *                             <li>For No Discount, pass empty string</li>
     *                            </ul>
     * @return Formatted <b>String</b> value of Final Calculation
     */
    public static String getFinalPriceCalculationWithTaxAndDiscount(String sUnitCostWithoutTax, String sUnitCount, String sTaxPercentage, String sDiscount) {

        double dTotalCost = 0;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        try {
            double dUnitCostWithoutTax = Double.parseDouble(sUnitCostWithoutTax);
            double dUnitCount = Double.parseDouble(sUnitCount);

            sTaxPercentage = sTaxPercentage.replaceAll(" ", "").replace("tax", "").replace("%", "");
            double dTaxPercentage = Double.parseDouble(sTaxPercentage);

            double dTaxPercentageAmount;
            dTotalCost = dUnitCostWithoutTax * dUnitCount;

            double dDiscount = 0;

            // If Discount value is provided
            if (!sDiscount.equals("")) {

                if (sDiscount.contains("%")) {
                    // Discount in Percentage
                    sDiscount = sDiscount.replace("%", "");
                    dDiscount = Double.parseDouble(sDiscount);
                    double dDiscountFinalAmount = (dDiscount / 100) * dTotalCost;
                    dTotalCost = dTotalCost - dDiscountFinalAmount;

                } else {
                    // Discount in Unit Currency
                    dDiscount = Double.parseDouble(sDiscount);
                    dTotalCost = dTotalCost - dDiscount;

                }
            }

            dTaxPercentageAmount = (dTaxPercentage / 100) * dTotalCost;
            dTotalCost = dTotalCost + dTaxPercentageAmount;

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

        return decimalFormat.format(dTotalCost);
    }

    public static boolean addItemInInventory(String itemDescription) {

        Page_ItemMaster oPage_ItemMaster = new Page_ItemMaster(driver);
        Page_Store oPage_Store = new Page_Store(driver);

        String categoryName = "Medication";
        String subCategoryName = "itemSubCategory_1";
        String hsnCode = "HSN" + getRandomUniqueString(4);
        //  String itemPropertiesTax = "GST5 - 5.0%";
        String itemPossibleVariantName = "Variant1";
        String itemPossibleVariantValue = "1";

        boolean bCategoryFound = false;
        boolean bAddItemStatus = false;

        try {

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Store.button_addNewButtonInventory),
                    " Add Item Button Clicked");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.header_addItemMasterTemplateTitle, 8);

            // Entering Required fields and fill data in Item Details ,Properties and Possible Variant to create Item

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.dropdown_categoryArrow),
                    "Category Dropdown Clicked in add item ");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, categoryName);
            Cls_Generic_Methods.customWait(1);

            for (WebElement itemCategory : oPage_ItemMaster.list_inventoryItemCategoryList) {
                if (Cls_Generic_Methods.getTextInElement(itemCategory).contains(categoryName)) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(itemCategory), "Category selected: <b> " + categoryName + "</b>");
                    bCategoryFound = true;
                    break;
                }
            }

            if (bCategoryFound) {
                   /* Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.button_subCategoryDropdownArrow, 2);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_subCategoryDropdownArrow),
                            "Sub Category Dropdown Arrow Clicked In Add Item");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemMasterInputBox, 2);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemMasterInputBox, subCategoryName);
                    Cls_Generic_Methods.customWait(1);

                    for (WebElement subCategory : oPage_ItemMaster.list_inventoryItemSubCategoryList) {
                        if (Cls_Generic_Methods.getTextInElement(subCategory).contains(subCategoryName)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(subCategory), "Sub Category selected: <b> " + subCategoryName + "</b>");
                            break;

                        }
                    }
*/
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemHsnCode, 1);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemHsnCode, hsnCode),
                        " Item HSN code Entered as : <b>" + hsnCode + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemDescription, itemDescription),
                        "Item Description Entered as : <b>" + itemDescription + "</b>");
                Cls_Generic_Methods.clickElement(oPage_ItemMaster.select_itemPropertiesTaxList);
                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ItemMaster.select_itemPropertiesTaxList, InventoryStore_Data.sSTORE_TAX_RATE_GST5),
                        "Item Properties Tax Entered as : <b>" + InventoryStore_Data.sSTORE_TAX_RATE_GST5 + "</b>");
                if(Cls_Generic_Methods.isElementDisplayed(oPage_ItemMaster.checkbox_propertiesExpiryPresent)) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.checkbox_propertiesExpiryPresent),
                            "Item Properties Expiry Present Checkbox Clicked");
                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.checkbox_propertiesPrescriptionMandatory),
                        "Item Properties Prescription Mandatory Checkbox Clicked");
             //   m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.checkbox_propertiesUnitLevel),
            //            "Item Properties Unit Level Checkbox Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ItemMaster.input_itemPossibleVariantName, 2);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemPossibleVariantName, itemPossibleVariantName),
                        "Item Possible Variant Name Entered as : <b>" + itemPossibleVariantName + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.input_itemPossibleVariantValue),
                        "Item Possible Variant Value Clicked");

                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_itemPossibleVariantValue, itemPossibleVariantValue);
                Cls_Generic_Methods.customWait(1);
                m_assert.assertTrue(CommonActions.selectOptionFromListBasedOnTextOrValue(oPage_ItemMaster.list_itemPossibleVariantValuesList, itemPossibleVariantValue),
                        "Item Possible Variant Value Entered as : <b>" + itemPossibleVariantValue + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ItemMaster.button_saveAddItemTemplate),
                        "Save Button Clicked with filled required field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_addNewButtonInventory, 17);

                Cls_Generic_Methods.clearValuesInElement(oPage_ItemMaster.input_searchInventoryInStoreInventory);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_ItemMaster.input_searchInventoryInStoreInventory, itemDescription);
                Cls_Generic_Methods.customWait(3);
                oPage_ItemMaster.input_searchInventoryInStoreInventory.sendKeys(Keys.ENTER);
                Cls_Generic_Methods.customWait(3);
                for (WebElement items : oPage_ItemMaster.list_itemListInStoreInventory) {
                    List<WebElement> itemNameRow = items.findElements(By.xpath("./child::*"));
                    String itemDescriptionText = Cls_Generic_Methods.getTextInElement(itemNameRow.get((1)));

                    if (itemDescriptionText.contains(itemDescription)) {
                        bAddItemStatus = true;
                        break;
                    }

                }

            } else {
                m_assert.assertTrue(bCategoryFound, "Category Name " + categoryName + " not in category master");
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }


        return bAddItemStatus;
    }

    public static boolean getPurchaseTransactionFromTransactionList(String dateAndTime, String totalNetAmount,
                                                                    String vendorName, String transactionNotes, String status) {

        Page_Purchase oPage_Purchase = new Page_Purchase(driver);
        boolean bPurchaseTransactionFound = false;
        List<String> purchaseTransactionHeaderList = new ArrayList<String>();


        try {

            for (WebElement purchaseHeaderList : oPage_Purchase.list_purchaseTransactionHeaderList) {
                purchaseTransactionHeaderList.add(Cls_Generic_Methods.getTextInElement(purchaseHeaderList));
            }

            for (WebElement row : oPage_Purchase.list_transactionPurchaseList) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> purchaseRow = row.findElements(By.xpath("./child::*"));

                    String dateTimeFull = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("GRN Info.")));
                    String purchaseVendorName = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Vendor")));
                    String purchaseNote = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Note")));
                    String purchaseAmount = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Amount")));
                    String purchaseStatus = Cls_Generic_Methods.getTextInElement(purchaseRow.get(purchaseTransactionHeaderList.indexOf("Status")));

                    String date = dateTimeFull.split("\\|")[0].trim();
                    String time = dateTimeFull.split("\\|")[1].trim();
                    String purchaseDateAndTime = date + "|" + time;

                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    String purchaseNetAmountUI = decimalFormat.format(convertStringToDouble(purchaseAmount));

                    if (purchaseDateAndTime.equals(dateAndTime) &&
                            purchaseStatus.equalsIgnoreCase(status) &&
                            purchaseNetAmountUI.equalsIgnoreCase(totalNetAmount) &&
                            purchaseVendorName.contains(vendorName) &&
                            purchaseNote.equalsIgnoreCase(transactionNotes)
                    ) {
                        bPurchaseTransactionFound = true;
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(row),
                                "Purchase Transaction Clicked  In Transaction List");
                        Cls_Generic_Methods.customWait(2);
                        break;
                    }

                }
            }

            // If Purchase Not Found In Purchase Transaction List on basis of Date and Time and Vendor
            if (!bPurchaseTransactionFound) {
                m_assert.assertFalse(" Purchase Order Is not found for date : " + dateAndTime + "and vendor name :" + vendorName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

        return bPurchaseTransactionFound;

    }

    public static double convertStringToDouble(String sToConvert) {

        double convertedDouble = 0.0;
        try {
            convertedDouble = Double.parseDouble(sToConvert);
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return convertedDouble;
    }

    public static double convertTaxToAmount(String taxPercentage)
    {
        double dTaxPercentage = 0.0;
        try {
            String sTax= taxPercentage.replace("%","").replace(" ","");
            dTaxPercentage = Double.parseDouble(sTax)/100;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return dTaxPercentage;

    }
    public static boolean  selectDateFromDatePicker(WebElement ele,String ddmmyy) throws Exception {
        oPage_CommonElements=new Page_CommonElements(driver);
        String[] dateMonthYear =ddmmyy.split("/");
        int date=Integer.parseInt(dateMonthYear[0]);
        int monthNo=Integer.parseInt(dateMonthYear[1]);
        String year=dateMonthYear[2];
        String month="";
        boolean status=false;


        switch (monthNo) {
            case 1 -> month = "January";
            case 2 -> month = "February";
            case 3 -> month = "March";
            case 4 -> month = "April";
            case 5 -> month = "May";
            case 6 -> month = "June";
            case 7 -> month = "July";
            case 8 -> month = "August";
            case 9 -> month = "September";
            case 10 -> month = "October";
            case 11 -> month = "November";
            case 12 -> month = "December";
        }
        Cls_Generic_Methods.clickElement(ele);
        if(month.length()>0) {
            String expectedMonthYear = month + " " + year;
            String currentMonthYear =Cls_Generic_Methods.getTextInElement(oPage_CommonElements.text_monthYearInDatePicker);

            while(!currentMonthYear.equals(expectedMonthYear)){
                Cls_Generic_Methods.clickElement(oPage_CommonElements.button_nextDatePicker);
                currentMonthYear =Cls_Generic_Methods.getTextInElement(oPage_CommonElements.text_monthYearInDatePicker);
            }
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,driver.findElement(By.xpath("//a[text()='"+date+"']"))) ,ddmmyy+" is selected" );
            status=true;
        }else{
            m_assert.assertFatal("Enter Valid Data");
        }
        return status;

    }

    public static void clickPolicyButtonForTheUser(String userName) throws Exception {
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        String sUserName = userName;
        Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_searchUser,userName);
        Cls_Generic_Methods.customWait(3);
        int size = oPage_OrganisationSetup.list_userName.size();
        for(int i=0;i<size;i++)
        {
            if(Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.list_userName.get(i)).contains(sUserName)){
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.list_userName.get(i).findElement(By.xpath("./following-sibling::td[6]/span/a[text()='Policy']"))),"Clicked user policy for user : "+sUserName);
            }
        }
    }

    public static void enablePolicyForTheUser(String userName, String policyType, String headerName, String sPolicy) throws Exception {
        Page_InventoryPolicy oPage_InventoryPolicy=new Page_InventoryPolicy(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

        String sUserName = userName;
        Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_searchUser);
        Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_searchUser,userName);
        Cls_Generic_Methods.customWait(3);
        int sizeUserName = oPage_OrganisationSetup.list_userName.size();
        //opening policy for the username
        for(int i=0;i<sizeUserName;i++)
        {
            if(Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.list_userName.get(i)).contains(sUserName)){
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.list_userName.get(i).findElement(By.xpath("./following-sibling::td[6]/span/a[text()='Policy']"))),"Clicked user policy for user : "+sUserName);
            }
        }
        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventoryPolicy.text_userNameOnPolicy, 15);
        m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventoryPolicy.text_userNameOnPolicy).contains(sUserName),"opened authorization policy page for the user : "+sUserName);
        //Opening Policy Type
        boolean bInventoryPolicyClicked = false;
        for (WebElement element :oPage_InventoryPolicy.list_typeOfPolicies) {
            String sPolicyName = Cls_Generic_Methods.getTextInElement(element);
            if (sPolicyName.contains(policyType))
            {
                Cls_Generic_Methods.clickElement(element);
                bInventoryPolicyClicked = true;
                break;
            }
        }
        m_assert.assertTrue(bInventoryPolicyClicked,policyType+" policy clicked");
        Cls_Generic_Methods.customWait(5);
        if(policyType.equalsIgnoreCase("INVENTORY")) {
            //enabling policy for the user
            int sizeComponent = oPage_InventoryPolicy.list_inventoryPolicyComponentHeader.size();
            for (int i = 0; i < sizeComponent; i++) {
                String sHeader = Cls_Generic_Methods.getTextInElement(oPage_InventoryPolicy.list_inventoryPolicyComponentHeader.get(i));
                if (sHeader.contains(headerName)) {
                    Cls_Generic_Methods.scrollToElementByJS(oPage_InventoryPolicy.list_inventoryPolicyComponentHeader.get(i));
                    List<WebElement> list_policyDescription = oPage_InventoryPolicy.list_InventoryPolicyComponent.get(i).findElements(By.xpath("./div/div/div"));
                    int descSize = list_policyDescription.size();
                    for (int j = 0; j < descSize; j++) {
                        String sPolicyDescription = Cls_Generic_Methods.getTextInElement(list_policyDescription.get(j));


                        if (sPolicyDescription.contains(sPolicy) && sPolicyDescription.contains("YES")) {
                            WebElement element = list_policyDescription.get(j).findElement(By.xpath("./div[4]/div/label"));
                            Cls_Generic_Methods.scrollToElementByJS(element);
                            Cls_Generic_Methods.clickElementByJS(driver, element);
                            m_assert.assertInfo(sPolicyDescription);
                            Cls_Generic_Methods.customWait();
                            break;

                        }
                    }
                }
            }
        }
        else {
            int sizeComponent = oPage_InventoryPolicy.list_otherPolicyComponentHeader.size();
            for (int i = 0; i < sizeComponent; i++) {
                String sHeader = Cls_Generic_Methods.getTextInElement(oPage_InventoryPolicy.list_otherPolicyComponentHeader.get(i));
                if (sHeader.equals(headerName)) {
                    Cls_Generic_Methods.scrollToElementByJS(oPage_InventoryPolicy.list_otherPolicyComponentHeader.get(i));
                    List<WebElement> list_policyDescription = oPage_InventoryPolicy.list_otherPolicyComponent.get(i).findElements(By.xpath("./div/div/div"));
                    int descSize = list_policyDescription.size();
                    for (int j = 0; j < descSize; j++) {
                        String sPolicyDescription = Cls_Generic_Methods.getTextInElement(list_policyDescription.get(j));


                        if (sPolicyDescription.contains(sPolicy) && sPolicyDescription.contains("YES")) {
                            WebElement element = list_policyDescription.get(j).findElement(By.xpath("./div[4]/div/label"));
                            Cls_Generic_Methods.scrollToElementByJS(element);
                            Cls_Generic_Methods.clickElementByJS(driver, element);
                            m_assert.assertInfo(sPolicyDescription);
                            Cls_Generic_Methods.customWait();
                            break;

                        }
                    }
                    break;
                }
            }
        }
        Cls_Generic_Methods.scrollToElementByJS(oPage_InventoryPolicy.button_SaveChanges);
        Cls_Generic_Methods.clickElement(oPage_InventoryPolicy.button_SaveChanges);
    }

    public static void disablePolicyForTheUser(String userName, String policyType, String headerName, String sPolicy) throws Exception {
        Page_InventoryPolicy oPage_InventoryPolicy=new Page_InventoryPolicy(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

        String sUserName = userName;
        Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_searchUser);
        Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_searchUser,userName);
        Cls_Generic_Methods.customWait(3);
        int sizeUserName = oPage_OrganisationSetup.list_userName.size();
        //opening policy for the username
        for(int i=0;i<sizeUserName;i++)
        {
            if(Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.list_userName.get(i)).contains(sUserName)){
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.list_userName.get(i).findElement(By.xpath("./following-sibling::td[6]/span/a[text()='Policy']"))),"Clicked user policy for user : "+sUserName);
            }
        }
        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventoryPolicy.text_userNameOnPolicy, 16);
        m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_InventoryPolicy.text_userNameOnPolicy).contains(sUserName),"opened authorization policy page for the user : "+sUserName);
        //Opening Policy Type
        boolean bInventoryPolicyClicked = false;
        for (WebElement element :oPage_InventoryPolicy.list_typeOfPolicies) {
            String sPolicyName = Cls_Generic_Methods.getTextInElement(element);
            if (sPolicyName.contains(policyType))
            {
                Cls_Generic_Methods.clickElement(element);
                bInventoryPolicyClicked = true;
                break;
            }
        }
        m_assert.assertTrue(bInventoryPolicyClicked,policyType+" policy clicked");
        Cls_Generic_Methods.customWait(5);
        if(policyType.equalsIgnoreCase("INVENTORY")) {
            //enabling policy for the user
            int sizeComponent = oPage_InventoryPolicy.list_inventoryPolicyComponentHeader.size();
            for (int i = 0; i < sizeComponent; i++) {
                String sHeader = Cls_Generic_Methods.getTextInElement(oPage_InventoryPolicy.list_inventoryPolicyComponentHeader.get(i));
                if (sHeader.contains(headerName)) {
                    Cls_Generic_Methods.scrollToElementByJS(oPage_InventoryPolicy.list_inventoryPolicyComponentHeader.get(i));
                    List<WebElement> list_policyDescription = oPage_InventoryPolicy.list_InventoryPolicyComponent.get(i).findElements(By.xpath("./div/div/div"));
                    int descSize = list_policyDescription.size();
                    for (int j = 0; j < descSize; j++) {
                        String sPolicyDescription = Cls_Generic_Methods.getTextInElement(list_policyDescription.get(j));


                        if (sPolicyDescription.contains(sPolicy) && sPolicyDescription.contains("NO")) {
                            WebElement element = list_policyDescription.get(j).findElement(By.xpath("./div[4]/div/label"));
                            Cls_Generic_Methods.scrollToElementByJS(element);
                            Cls_Generic_Methods.clickElementByJS(driver, element);
                            m_assert.assertInfo(sPolicyDescription);
                            Cls_Generic_Methods.customWait();
                            break;

                        }
                    }
                }
            }
        }
        else {
            int sizeComponent = oPage_InventoryPolicy.list_otherPolicyComponentHeader.size();
            for (int i = 0; i < sizeComponent; i++) {
                String sHeader = Cls_Generic_Methods.getTextInElement(oPage_InventoryPolicy.list_otherPolicyComponentHeader.get(i));
                if (sHeader.contains(headerName)) {
                    Cls_Generic_Methods.scrollToElementByJS(oPage_InventoryPolicy.list_otherPolicyComponentHeader.get(i));
                    List<WebElement> list_policyDescription = oPage_InventoryPolicy.list_otherPolicyComponent.get(i).findElements(By.xpath("./div/div/div"));
                    int descSize = list_policyDescription.size();
                    for (int j = 0; j < descSize; j++) {
                        String sPolicyDescription = Cls_Generic_Methods.getTextInElement(list_policyDescription.get(j));


                        if (sPolicyDescription.contains(sPolicy) && sPolicyDescription.contains("NO")) {
                            WebElement element = list_policyDescription.get(j).findElement(By.xpath("./div[4]/div/label"));
                            Cls_Generic_Methods.scrollToElementByJS(element);
                            Cls_Generic_Methods.clickElementByJS(driver, element);
                            m_assert.assertInfo(sPolicyDescription);
                            Cls_Generic_Methods.customWait();
                            break;

                        }
                    }
                    break;
                }
            }
        }
        Cls_Generic_Methods.scrollToElementByJS(oPage_InventoryPolicy.button_SaveChanges);
        Cls_Generic_Methods.clickElement(oPage_InventoryPolicy.button_SaveChanges);
    }

    public static boolean selectParentOptionFromLeftInInventoryStorePanel(String sInputParentOption) {
        boolean operationSuccess = false;
        boolean parentOptionSelected = false;
        Page_Store oPage_Store = new Page_Store(driver);


        try {
            try {
                Cls_Generic_Methods.scrollToElementByJS(oPage_Store.header_panelOnLeft);
            } catch (Exception e) {
                System.out.println(e);
            }

            for (WebElement parentElement : oPage_Store.list_ParentOptionsOnLeft) {
                String sParentOptionTextOnUI = Cls_Generic_Methods.getTextInElement(parentElement);
                sParentOptionTextOnUI = sParentOptionTextOnUI.split("\\n")[0];

                System.out.println(sParentOptionTextOnUI);
                int requiredIndex = oPage_Store.list_ParentOptionsOnLeft.indexOf(parentElement);

                if (sParentOptionTextOnUI.equalsIgnoreCase(sInputParentOption)) {
                    Cls_Generic_Methods.scrollToElementByJS(parentElement);
                    if (requiredIndex != 0) {
                        Cls_Generic_Methods.clickElement(driver, parentElement);
                        Cls_Generic_Methods.customWait(1);
                    }
                    parentOptionSelected = true;
                    operationSuccess = true ;
                    m_assert.assertInfo("Selected Option in the Left Panel = <b>" + sParentOptionTextOnUI + "</b>");


                }

                if (parentOptionSelected) {
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return operationSuccess;
    }

    public static boolean uploadFileUsingRobotClass(String uploadFileLocation){
        boolean flag=false;
        try{
            StringSelection stringSelection=new StringSelection(uploadFileLocation);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null);
            System.out.println("COPIED FILE-->"+Cls_Generic_Methods.getClipboardContent());
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.delay(1000);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(1000);
            robot.keyRelease(KeyEvent.VK_ENTER);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
            m_assert.assertFatal("Unable To upload the file"+e);
        }
        return flag;
    }

    public static boolean selectPatientNameInPatientQueue(List<WebElement> listOfTabsElement,String expectedPatientName) {

        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        boolean bPatientNameFoundAndClicked = false;
        String patientName = "";
        try {
            for (WebElement patientNameElement : listOfTabsElement) {
                if (Cls_Generic_Methods.isElementDisplayed(patientNameElement)) {
                    patientName = Cls_Generic_Methods.getTextInElement(patientNameElement);
                    if (expectedPatientName.equals(patientName.trim())) {
                        Cls_Generic_Methods.clickElement(driver, patientNameElement);
                        Cls_Generic_Methods.customWait(5);
                        bPatientNameFoundAndClicked = true;
                        break;
                    }
                }
            }

            m_assert.assertTrue(bPatientNameFoundAndClicked, "Patient name found in Store : <b> " + expectedPatientName + "</b>");

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Patient Is not present or not clicked" + e);
        }

        return bPatientNameFoundAndClicked;
    }
    public static void validatingCommonElementsInStorePatientDetailsRHS(List<String> listOfPatientInformation,List<String> listOfPatientHistory) {

        Page_Store oPage_Store = new Page_Store(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);

        String[] patientHistoryHeaders = {"Ophthalmic & Systemic :","Medical :","Family :","Drug (Allergies) :","Contact (Allergies) :",
                "Food (Allergies) :","Other (Allergies) :"};

        String[] patientDetailSectionLabels = {"Full Name","Patient ID","Date of Birth","Primary Contact"};
        String patientNotesText = "Patient Notes Check";
        try {

            String storeDetailsHeaderText = Cls_Generic_Methods.getTextInElement(oPage_Store.header_storeDetailsHeader);
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.header_storeDetailsHeader),
                    "Store Details Header Displayed as : "+storeDetailsHeaderText);
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.img_patientProfilePic),
                    "Profile Picture Image Displayed");
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_consolidateReportsButtonInStore),
                    "Consolidate Reports Button Displayed");
            for(WebElement labels : oPage_Store.keysInRow_PatientDetails){
                int indexOfLabel = oPage_Store.keysInRow_PatientDetails.indexOf(labels);
                String labelText = Cls_Generic_Methods.getTextInElement(labels);

                if(indexOfLabel<4){
                if(labelText.equalsIgnoreCase(patientDetailSectionLabels[indexOfLabel])){
                    m_assert.assertTrue(labelText + " Displayed in Patient Details Section");
                }else {
                    if(labelText.equalsIgnoreCase("Date of Birth | Age"))
                        m_assert.assertTrue(labelText + " Displayed in Patient Details Section");
                    else
                        m_assert.assertFalse(labelText + " Not Displayed in Patient Details Section");

                }
                }else {

                    if (patientHistoryHeaders[indexOfLabel-4].contains(labelText)) {
                        m_assert.assertTrue(labelText + " Displayed in Patient Details Section");
                    } else {
                        m_assert.assertFalse(labelText + " Not Displayed in Patient Details Section");
                    }
                }
            }

            for(WebElement patientInfo : oPage_Store.valuesInRow_PatientDetails){

                int index = oPage_Store.valuesInRow_PatientDetails.indexOf(patientInfo);
                String patientInfoValue = Cls_Generic_Methods.getTextInElement(patientInfo).trim();

                if(index<4) {

                    if (patientInfoValue.equalsIgnoreCase(listOfPatientInformation.get(index))) {
                        m_assert.assertTrue(" Patient Details Value Displayed Correctly as : " + patientDetailSectionLabels[index] + " : " + patientInfoValue);
                    } else {
                        if (index == 2) {
                            m_assert.assertTrue(patientInfoValue.equalsIgnoreCase("Unavailable"),
                                    " Date of Birth Not Entered While Creating Patient");
                        } else {
                            m_assert.assertFalse(" Date Of Birth Not displaying correctly");
                        }
                    }
                }else{

                    if(patientInfoValue.equalsIgnoreCase(listOfPatientHistory.get(index-4))){
                        m_assert.assertTrue(patientHistoryHeaders[index-4]+" Text Displayed correctly In Store as : "+ patientInfoValue );
                    }else{
                        m_assert.assertWarn(patientHistoryHeaders[index-4]+" Text Not Displayed correctly as "+ patientInfoValue);
                    }
                }
            }

          /*  for(WebElement header : oPage_Store.list_allergiesHeaderListInStore){

                String historyHeader = Cls_Generic_Methods.getTextInElement(header);
                if(Cls_Generic_Methods.isElementDisplayed(header)){

                    int index = oPage_Store.list_allergiesHeaderListInStore.indexOf(header);
                    m_assert.assertTrue(historyHeader.equalsIgnoreCase(patientHistoryHeaders[index]),
                            historyHeader+ " header displayed in store");
                }else{
                    m_assert.assertWarn(historyHeader+" Either Not present or not advised to patient ");
                }

            }

            int indexOfPatientHistory = 0 ;
            for(WebElement ophthalmicSystemicHistory : oPage_Store.list_ophthalmicAndSystemicHistoryDataListInStore){

                int indexOfOphthalmicSystemicHistory =  oPage_Store.list_ophthalmicAndSystemicHistoryDataListInStore.indexOf(ophthalmicSystemicHistory);
                if(Cls_Generic_Methods.isElementDisplayed(ophthalmicSystemicHistory)){
                    String ophthalmicSystemicHistoryText = Cls_Generic_Methods.getTextInElement(ophthalmicSystemicHistory);
                    if(ophthalmicSystemicHistoryText.equalsIgnoreCase(listOfPatientHistory.get(indexOfOphthalmicSystemicHistory))){
                        m_assert.assertTrue(" Ophthalmic and Systemic Text Displayed correctly In Store as : "+ ophthalmicSystemicHistoryText );
                    }else{
                        m_assert.assertWarn(" Ophthalmic and Systemic Text Not Displayed correctly as "+ ophthalmicSystemicHistoryText);
                    }
                }
                indexOfPatientHistory ++ ;
            }

            String medicalHistoryInStore = Cls_Generic_Methods.getTextInElement(oPage_Store.list_medicalHistoryDataListInStore);
            String familyHistoryInStore = Cls_Generic_Methods.getTextInElement(oPage_Store.list_familyHistoryDataListInStore);
            m_assert.assertTrue(medicalHistoryInStore.equalsIgnoreCase(listOfPatientHistory.get(indexOfPatientHistory)),
                    " Medical History Comment Displayed Correctly In Store as : "+medicalHistoryInStore);
            indexOfPatientHistory ++;
            m_assert.assertTrue(familyHistoryInStore.equalsIgnoreCase(listOfPatientHistory.get(indexOfPatientHistory)),
                    " Family History Comment Displayed Correctly In Store as : "+familyHistoryInStore);
            indexOfPatientHistory ++ ;

            for(WebElement drugAllergiesHistory : oPage_Store.list_drugAllergiesHistoryDataListInStore){
                String drugAllergiesHistoryText = Cls_Generic_Methods.getTextInElement(drugAllergiesHistory);

                if(drugAllergiesHistoryText.equalsIgnoreCase(listOfPatientHistory.get(indexOfPatientHistory))){
                    m_assert.assertTrue(" Drug Allergies Text Displayed correctly In Store as : "+drugAllergiesHistoryText);
                }else{
                    m_assert.assertWarn(drugAllergiesHistoryText +" Drug Allergies Text Not Displayed correctly");
                }
                indexOfPatientHistory ++ ;
            }
            for(WebElement contactAllergiesHistory : oPage_Store.list_contactAllergiesHistoryDataListInStore){

                String contactAllergiesHistoryText = Cls_Generic_Methods.getTextInElement(contactAllergiesHistory);
                if(contactAllergiesHistoryText.equalsIgnoreCase(listOfPatientHistory.get(indexOfPatientHistory))){
                    m_assert.assertTrue( " Contact Allergies Text Displayed correctly In Store as "+contactAllergiesHistoryText);
                }else{
                    m_assert.assertWarn(contactAllergiesHistoryText +" Contact Allergies Text Not Displayed correctly");
                }
                indexOfPatientHistory ++ ;
            }

            for(WebElement foodAllergiesHistory : oPage_Store.list_foodAllergiesHistoryDataListInStore){

                String foodAllergiesHistoryText = Cls_Generic_Methods.getTextInElement(foodAllergiesHistory);
                if(foodAllergiesHistoryText.equalsIgnoreCase(listOfPatientHistory.get(indexOfPatientHistory))){
                    m_assert.assertTrue( " Food Allergies Text Displayed correctly In Store as "+ foodAllergiesHistoryText);
                }else{
                    m_assert.assertWarn(foodAllergiesHistoryText +" Food Allergies Text Not Displayed correctly");
                }
                indexOfPatientHistory ++ ;
            }

            String otherHistoryInStore = Cls_Generic_Methods.getTextInElement(oPage_Store.list_otherAllergiesHistoryDataListInStore);
            m_assert.assertTrue(otherHistoryInStore.equalsIgnoreCase(listOfPatientHistory.get(12)),
                    "  Other History Comment Displayed Correctly In Store as : "+otherHistoryInStore);
*/
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.label_arrivedStatus),
                    Cls_Generic_Methods.getTextInElement(oPage_Store.label_arrivedStatus) +" Status Present");
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.arrow_userDirectionArrow),
                    " User Flow Direction Indicator Displayed In User Flow");
            m_assert.assertTrue(Cls_Generic_Methods.getTextInElement(oPage_Store.label_assignedDoctorName).equalsIgnoreCase("Dr."+oEHR_Data.user_PRAkashTest),
                    " Assigned Doctor Name Displaying Correctly In User Flow Box as : "+oEHR_Data.user_PRAkashTest);

            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_tagLineButton),
                    " Tag Line Button Displayed Correctly");
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.span_advisedByTextLabel),
                    "Advised By Label Displayed correctly");
            String assignedByDoctorName = Cls_Generic_Methods.getTextInElement(oPage_Store.span_assignedByDoctorName);
            m_assert.assertTrue(assignedByDoctorName.equalsIgnoreCase("Dr. "+oEHR_Data.user_PRAkashTest),
                    " Assigned by Doctor Name Correctly as : "+ assignedByDoctorName);

            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.span_viewTemplateLabel),
                    " View Template Label Displayed Correctly as : "+Cls_Generic_Methods.getTextInElement(oPage_Store.span_viewTemplateLabel));
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Store.button_viewTemplateButton),
                    "Template Button Displayed and Clicked");
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.template_viewContentTemplate),
                    " Template Opened In Store");
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(5);

            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.header_patientNote),
                    " Patient Notes Header Displayed in Store");
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.textarea_patientNoteTextBox),
                    "Patient Notes Text Box Displayed");
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.input_savePatientNote),
                    "Save Notes Button Displayed In Store");
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.text_noNotesPresentInfoText),
                    " No Notes Present Text Displayed ");

            Cls_Generic_Methods.sendKeysIntoElement(oPage_Store.textarea_patientNoteTextBox,patientNotesText);
            DateFormat dateFormat = new SimpleDateFormat("hh:mm a dd-MM-yy");
            String dateAndTime = dateFormat.format(new Date()).toString();
            Cls_Generic_Methods.clickElement(oPage_Store.input_savePatientNote);
            Cls_Generic_Methods.customWait();

            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.createdPatientNotesRow),
                    " Notes Created Successfully ");
            String createdDetails = Cls_Generic_Methods.getTextInElement(oPage_Store.createdPatientNotesRow);
            //  m_assert.assertTrue(createdDetails.equalsIgnoreCase(oEHR_Data.user_PRAkashTest+": "+patientNotesText+" "+dateAndTime),
                  //  " Notes Created Details as :" +createdDetails);

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Store.button_deletePatientNote),
                    "Delete Note Button Clicked ");
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.text_noNotesPresentInfoText),
                    "Notes Deleted Successfully as No Notes Present Text Displayed");

            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Store.button_markPatientVisitedButton),
                    " Mark Patient Visited Button Displayed and Clicked");
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.text_markConvertedLabelText),
                    "Mark Converted Label Displayed");
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_markPatientNotVisitedButton),
                    " Mark Patient Not Visited Displayed");
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_yesMarkConvertedButton),
                    "Yes Button displayed against Mark Converted Label");
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_noMarkConvertedButton),
                    "No Button displayed against Mark Converted Label") ;


        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

    }

    public static boolean  MouseHoverAction (WebDriver driver, WebElement element) throws Exception {

        boolean flag = false;
        Actions actions = new Actions(driver);
        try {
          //  Cls_Generic_Methods.scrollToElementByAction(driver, element);

            actions.moveToElement(element).build().perform();

            flag = true;
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception("Mouse hovering action is not working " + e);
        }

        return flag;
    }
    public static String getDateByAddSub(String date, String action,int nrOfDays,String... dateFormat) {
        String dateTimeFormatterString = null;
        String finalDate = null;
        try {
            if (dateFormat.length > 0) {
                m_assert.assertInfo("INTO getTodayDate method with " + dateFormat[0]);
                dateTimeFormatterString = dateFormat[0];
            } else {
                m_assert.assertInfo("INTO getTodayDate method");
                dateTimeFormatterString = "dd/MM/yyyy";
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateTimeFormatterString);
            if(action.equalsIgnoreCase("Add")){
                LocalDate date1 = LocalDate.parse(date,dtf);
                LocalDate dateAfterAction= date1.plusDays(nrOfDays);
                finalDate = dtf.format(dateAfterAction);
            }
            if(action.equalsIgnoreCase("Sub")){
                LocalDate date1 = LocalDate.parse(date,dtf);
                LocalDate dateAfterAction = date1.minusDays(nrOfDays);
                finalDate = dtf.format(dateAfterAction);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFalse("Error while fetching today's date. \n" + e);
        }
        return finalDate;
    }
    public static boolean selectOptionUnderOrgSettings(String inputOptionName) {
        boolean operationSuccess = false;
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

        try {
            for (WebElement eOption : oPage_OrganisationSetup.list_typeOfSetting) {
                String sOptionText = Cls_Generic_Methods.getTextInElement(eOption);

                if (sOptionText.equalsIgnoreCase(inputOptionName)) {
                    Cls_Generic_Methods.clickElement(eOption);
                    m_assert.assertInfo(true, "Selected Option <b>" + sOptionText + "</b>");
                    Cls_Generic_Methods.customWait(4);
                    operationSuccess = true;
                    break;
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

        return operationSuccess;
    }

    public static boolean selectOptionAndSearch(String searchType ,String searchValue) {
        boolean operationSuccess = false;
        boolean typeSelected = false;
        Page_Requisition oPage_Requisition = new Page_Requisition(driver);
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);

        try {
              m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                      " Search Type Dropdown Clicked");
              Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_InventorySearchCommonElements.list_searchTypeList,5);
            for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                  String typeText = Cls_Generic_Methods.getTextInElement(type);
                  if(typeText.equalsIgnoreCase(searchType)){
                      m_assert.assertTrue(Cls_Generic_Methods.clickElement(type),
                              " Search Type Clicked In Dropdown List");
                      Cls_Generic_Methods.customWait();
                      typeSelected = true ;
                      break;
                  }
              }

              m_assert.assertTrue(typeSelected , " Search Type Selected as : <b> "+ searchType+ "</b>");

            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"value")
                            .equalsIgnoreCase(""),
                    " Input Search Box is empty by default for selected search type"+searchType+ "Displayed correctly");
            m_assert.assertTrue(Cls_Generic_Methods.getElementAttribute(oPage_InventorySearchCommonElements.input_searchBoxInput,"placeholder")
                            .contains(searchType),
                    " Input Search Box Place holder for selected search type Displayed correctly");
            Cls_Generic_Methods.clearValuesInElement(oPage_InventorySearchCommonElements.input_searchBoxInput);
              m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_InventorySearchCommonElements.input_searchBoxInput,searchValue),
                     " Search Value Entered as : "+searchValue );
              Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox,5);
              m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchButtonInSearchBox),
                      " Search Icon Clicked In Search Box");
              Cls_Generic_Methods.customWait(4);
              if(searchType.equalsIgnoreCase("Item Description")) {
                  if (oPage_Requisition.list_dateTimeOfRequisition.size() >= 1 &&
                         !Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay)) {
                      operationSuccess = true;
                  }
              }else {
                  if (oPage_Requisition.list_dateTimeOfRequisition.size() == 1 &&
                          !Cls_Generic_Methods.isElementDisplayed(oPage_InventorySearchCommonElements.text_nothingToDisplay)) {
                      operationSuccess = true;
                  }
              }

            }
        catch (Exception e){
            e.printStackTrace();
            m_assert.assertFalse("Error while Performing Search \n" + e);
        }
       return operationSuccess ;
    }
    public static boolean selectOption(String searchType) {
        boolean operationSuccess = false;
        boolean typeSelected = false;
        Page_InventorySearchCommonElements oPage_InventorySearchCommonElements = new  Page_InventorySearchCommonElements(driver);

        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_InventorySearchCommonElements.button_searchTypeDropdown),
                    " Search Type Dropdown Clicked");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_InventorySearchCommonElements.list_searchTypeList,5);
            for(WebElement type : oPage_InventorySearchCommonElements.list_searchTypeList){
                String typeText = Cls_Generic_Methods.getTextInElement(type);
                if(typeText.equalsIgnoreCase(searchType)){
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(type),
                            " Search Type Clicked In Dropdown List");
                    Cls_Generic_Methods.customWait();
                    typeSelected = true ;
                    break;
                }
            }

            m_assert.assertTrue(typeSelected , " Search Type Selected as : <b> "+ searchType+ "</b>");

        }
        catch (Exception e){
            e.printStackTrace();
            m_assert.assertFalse("Error while Performing Search \n" + e);
        }
        return typeSelected ;
    }

    public static boolean selectItemInInventory(List<WebElement> listOfElement,List<String> itemStoreList,int count,List<WebElement> listOfElementInView){

        boolean bCountCorrect = false ;
        try {
            for (int i = 0 ; i< count ;i++) {
                   String sSelectedItemName = Cls_Generic_Methods.getTextInElement(listOfElement.get(i));
                    Cls_Generic_Methods.scrollToElementByJS(listOfElement.get(i));
                    Cls_Generic_Methods.customWait(1);
                    Cls_Generic_Methods.clickElementByJS(driver,listOfElement.get(i));
                    Cls_Generic_Methods.customWait(1);
                    itemStoreList.add(sSelectedItemName);
                    m_assert.assertTrue("<b>"+sSelectedItemName +" </b> Item Name Selected From Left Side Item List at "+i);
            }
            Cls_Generic_Methods.customWait(3);
            if(listOfElementInView.size() == count){
                bCountCorrect = true ;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            m_assert.assertFalse("Error while Performing Search \n" + e);
        }

        return bCountCorrect;
    }

    public static String getRandomNumber() {
        Random random=new Random();
        int randomNumber =random.nextInt(100);
        return String.valueOf(randomNumber);
    }

    public static void filterItemsByStock(String filterByStock) {
        oPage_InventoryFilterCommonElements = new Page_InventoryFilterCommonElements(driver);
        try {
            if (filterByStock.equalsIgnoreCase("Items With Stock")) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver,oPage_InventoryFilterCommonElements.radioButton_itemWithStock),"Item With Stock Radio Button Is Selected");
            } else if (filterByStock.equalsIgnoreCase("Running Low Stock")) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_InventoryFilterCommonElements.radioButton_runningLowStock), "Running low Stock Radio Button Is Selected");
            } else if (filterByStock.equalsIgnoreCase("Out Of Stock")) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_InventoryFilterCommonElements.radioButton_outOfStock), "Out Of Stock Radio Button Is Selected");
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sortBy(String sortOption) {
        oPage_InventoryFilterCommonElements = new Page_InventoryFilterCommonElements(driver);
        try {
            if (sortOption.equalsIgnoreCase("Stock")) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_InventoryFilterCommonElements.button_stockButton), "Stock button is selected in Sort by option");
            } else if (sortOption.equalsIgnoreCase("Description")) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_InventoryFilterCommonElements.button_descriptionButton), "Descrption button is Selected in Sort by option");
            } else if (sortOption.equalsIgnoreCase("Created Time")) {
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_InventoryFilterCommonElements.button_createdTimeButton), "Created Time Button is Selected in SOrt By Option");
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void filterItemsByCategories(String Category) {
        oPage_InventoryFilterCommonElements = new Page_InventoryFilterCommonElements(driver);
        try {
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_InventoryFilterCommonElements.select_itemsByCategory,Category),"In Filter items by category <b>" + Category + " </b> Is Selected");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean validatePrintButtonFunctionality(WebElement printButton , String printButtonName){

        Page_Sale oPage_Sale = new Page_Sale(driver);
        boolean bPrintWork = false ;

        try {
            int preWindowsCount = driver.getWindowHandles().size();
            String initialWindowHandle = driver.getWindowHandle();
            String printButtonHrefText = Cls_Generic_Methods.getElementAttribute(printButton,"href");

            m_assert.assertInfo(Cls_Generic_Methods.clickElement(printButton),
                    printButtonName+" Button Displayed and Clicked ");
            Cls_Generic_Methods.customWait(2);
            if(Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_printDropdownFirstOptionInAdvanceReceipt)){
                Cls_Generic_Methods.clickElement(oPage_Sale.list_printDropdownFirstOptionInAdvanceReceipt);
                Cls_Generic_Methods.customWait();
            }else {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Sale.list_printDropdownFirstOption)) {
                    Cls_Generic_Methods.clickElement(oPage_Sale.list_printDropdownFirstOption);
                    Cls_Generic_Methods.customWait();
                }
            }
            Cls_Generic_Methods.customWait(6);
            int postWindowsCount = driver.getWindowHandles().size();



            m_assert.assertTrue(postWindowsCount > preWindowsCount, "Validated Print --> Print page opened In New Tab");

            for (String currentWindowHandle : driver.getWindowHandles()) {
                if (!currentWindowHandle.equals(driver.getWindowHandle())) {
                    driver.switchTo().window(currentWindowHandle);
                    String currentWindowUrl = driver.getCurrentUrl();
                    m_assert.assertTrue(currentWindowUrl.equalsIgnoreCase(printButtonHrefText),
                            " Print Page URL Display Correctly and Changed as :<b>"+currentWindowUrl+" </b>");
                    bPrintWork =  true;
                }
            }

            driver.close();
            driver.switchTo().window(initialWindowHandle);
            Cls_Generic_Methods.customWait(4);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return bPrintWork ;
    }

    public static boolean validateEmailButtonFunctionality(WebElement emailButton, String place){

        Page_Sale oPage_Sale = new Page_Sale(driver);
        boolean emailWork = false ;

        try {
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(emailButton),
                    "Email Button Clicked In "+ place);
            m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Sale.input_emailAddressInComposeEmail,4),
                    " Compose Email Preview Template Opened and header Displayed as : Compose Email");
            if(!Cls_Generic_Methods.isElementDisplayed(oPage_Sale.span_emailIdExistInComposeEmail)) {
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_emailAddressInComposeEmail, "deepak.malviya@healthgraph.in"),
                        " Email address entered in Send To box as : deepak.malviya@healthgraph.in");
            }
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Sale.input_emailSubjectInComposeEmail, "Invoice Email 1"),
                    " Email Subject entered in Subject as : Invoice Email 1");
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.input_previewButtonInComposeEmail),
                    " Preview Button Clicked");
            Cls_Generic_Methods.customWait(4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Sale.input_sendEmailButtonInComposeEmail),
                    " Send Email Button Clicked");
            Cls_Generic_Methods.customWait(8);
            if(!Cls_Generic_Methods.isElementDisplayed(oPage_Sale.input_sendEmailButtonInComposeEmail)){
                emailWork = true ;
            }


        }catch (Exception e) {
            e.printStackTrace();
        }

        return  emailWork ;
    }

}



