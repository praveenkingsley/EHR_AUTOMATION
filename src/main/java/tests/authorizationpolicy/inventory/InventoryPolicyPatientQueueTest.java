package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.IPD_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.common_tabs.Page_RefractionTab;
import pages.commonElements.common_tabs.advice.Page_AdviceTab;
import pages.commonElements.navbar.Page_Navbar;
import pages.commonElements.newPatientRegisteration.Page_NewPatientRegisteration;
import pages.commonElements.patientAppointmentDetails.Page_PatientAppointmentDetails;
import pages.commonElements.scheduleAdmission.Page_ScheduleAdmission;
import pages.ipd.Page_IPD;
import pages.ipd.forms.intraOperative.Page_BillOfMaterial;
import pages.ipd.forms.preOperative.Page_AdmissionInPreOperative;
import pages.opd.Page_OPD;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.OpticalStore.Page_SalesOrder;
import pages.store.OtStore.Page_OtStore;
import pages.store.Page_PatientQueue;
import pages.store.Page_Store;
import pages.store.PharmacyStore.Items.Page_Master;

import java.util.List;

public class InventoryPolicyPatientQueueTest extends TestBase {
    String sPolicyRequired = "INVENTORY";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    static String sStore = "Pharmacy automation- Pharmacy";
    static String sIPDStore = "TESTING_STORE- IPD";
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_Master oPage_Master;
    Page_Store oPage_Store;
    Page_PatientQueue oPage_PatientQueue;
    Page_SalesOrder oPage_SalesOrder;
    Page_OtStore oPage_OtStore ;


    String sViewComponent = "VIEW (PATIENT QUEUE)";
    String sViewConsolidateReportsComponent = "VIEW CONSOLIDATE REPORTS (PATIENT QUEUE)";
    String sMarkConvertedPolicyComponent = "MARK CONVERTED (PATIENT QUEUE)";
    String sViewBillingDetailsComponent = "VIEW BILLING DETAILS (PATIENT QUEUE)";
    String sViewPreviousBillsComponent = "VIEW PREVIOUS BILLS (PATIENT QUEUE)";
    String sAdvanceReceiptPolicyComponent = "ADVANCE RECEIPT (PATIENT QUEUE)";
    String sAdvanceCancelPolicyComponent = "ADVANCE CANCELLATION (PATIENT QUEUE)";
    String sAdvanceRefundPolicyComponent = "ADVANCE REFUND (PATIENT QUEUE)";
    String sViewTemplatePolicyComponent = "VIEW TEMPLATE (PATIENT QUEUE)";
    String sCreateTrayPolicyComponent="CREATE TRAY (PATIENT QUEUE)";
    String sViewTrayPolicyComponent="VIEW TRAY (PATIENT QUEUE)";
    String sEditTrayPolicyComponent="EDIT TRAY (PATIENT QUEUE)";
    String sDeleteTrayPolicyComponent="DELETE TRAY (PATIENT QUEUE)";
    String sCloseTrayPolicyComponent="CLOSE TRAY (PATIENT QUEUE)";
    String sViewDescription = "Inventory Patient Queue View Access";
    String sViewConsolidateReportsDescription = "Inventory Consolidate Reports View Access";
    String sMarkConvertedPolicyDescription = "Inventory Patient Conversion Access";
    String sViewBillingDetailsDescription = "Inventory Billing Details View Access";
    String sViewPreviousBillsDescription = "Inventory Previous Bills View Access";
    String sAdvanceReceiptDescription = "Inventory Advance Receipt Create Access";
    String sAdvanceCancelDescription = "Inventory Advance Cancellation Access";
    String sAdvanceRefundPolicyDescription = "Inventory Advance Refund Access";
    String sViewTemplatePolicyDescription = "Inventory Patient OPD Template View Access";
    String sCreateTrayPolicyDescription="Inventory IPD Tray Creation Access";
    String sViewTrayPolicyDescription="Inventory Tray Details View Access";
    String sEditTrayPolicyDescription="Inventory Tray Edit Access";
    String sDeleteTrayPolicyDescription="Inventory Tray Delete Access";
    String sCloseTrayPolicyDescription="Inventory Tray Close Access";
    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");

    String tab_ALL = "ALL";
    String expectedLoggedInUser = EHR_Data.user_PRAkashTest;

    String patientName = "Patient Queue Policy Test".toUpperCase();
    String advanceAmount = "10";

    @BeforeMethod(onlyForGroups = "createPatient")
    public void createPatient() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_CommonElements = new Page_CommonElements(driver);

        try {
            Page_OPD oPage_OPD = new Page_OPD(driver);
            Page_RefractionTab oPage_RefractionTab = new Page_RefractionTab(driver);
            Page_AdviceTab oPage_AdviceTab = new Page_AdviceTab(driver);
            Page_NewPatientRegisteration oPage_NewPatientRegisteration = new Page_NewPatientRegisteration(driver);
            Page_PatientAppointmentDetails oPage_PatientAppointmentDetails = new Page_PatientAppointmentDetails(driver);
            Page_ScheduleAdmission oPage_ScheduleAdmission=new Page_ScheduleAdmission(driver);

            String selectType = "Name";
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_addAppointment);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_NewPatientRegisteration.select_searchPatientOrAddNewPatient, 5);
            selectByOptions(oPage_NewPatientRegisteration.select_searchPatientOrAddNewPatient, selectType);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_NewPatientRegisteration.input_searchPatient, patientName);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_NewPatientRegisteration.button_searchPatient);

            do {
                Cls_Generic_Methods.customWait(2);
            } while (oPage_NewPatientRegisteration.list_textSearchResults.size() < 1);

            for (WebElement searchResult : oPage_NewPatientRegisteration.list_textSearchResults) {
                String value = Cls_Generic_Methods.getTextInElement(searchResult).toUpperCase();
                if (value.contains(patientName)) {
                    Cls_Generic_Methods.clickElementByJS(driver, searchResult);
                    break;
                }
            }
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.rows_appointmentDetails, 20);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_NewPatientRegisteration.button_createAppointmentPatientRegForm);
            Cls_Generic_Methods.customWait(7);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_OPD.rows_patientAppointments,20);
            CommonActions.selectPatientNameInOpd(oPage_OPD.rows_patientAppointments, patientName);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OPD.button_clickNewTemplate, 10);
            Cls_Generic_Methods.clickElement(driver, oPage_OPD.button_clickNewTemplate);
            CommonActions.selectTemplateOption(oPage_OPD.listButtons_selectOptionsOnTemplate, "Post OP");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_RefractionTab.tab_RefractionTab, 20);

            for (WebElement noVaCheckBox : oPage_RefractionTab.list_checkboxVaNotExamined) {
                Cls_Generic_Methods.clickElementByJS(driver, noVaCheckBox);
            }
            Cls_Generic_Methods.customWait();
            for (WebElement noIopCheckBox : oPage_RefractionTab.list_checkboxIopNotExamined) {
                Cls_Generic_Methods.clickElementByJS(driver, noIopCheckBox);
            }


            Cls_Generic_Methods.clickElement(driver, oPage_AdviceTab.tab_advice);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.selectElementByVisibleText(oPage_AdviceTab.select_medicationSets, "automation set");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_SaveTemplate);

            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_OPD.text_headerOPDSummary, 15);
            Cls_Generic_Methods.clickElement(driver, oPage_CommonElements.button_CloseTemplate);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientAppointmentDetails.button_scheduleAdmission,10);
            Cls_Generic_Methods.clickElement(driver, oPage_PatientAppointmentDetails.button_scheduleAdmission);
            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_ScheduleAdmissionForm, 20);

            for (WebElement radioAdmissionBtn :
                    oPage_ScheduleAdmission.list_radioAdmissionType) {
                String admissionTypeBtn = Cls_Generic_Methods.getTextInElement(radioAdmissionBtn);

                if (admissionTypeBtn.equals("Same Day")) {
                    Cls_Generic_Methods.clickElement(driver,radioAdmissionBtn);
                }
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_ScheduleAdmission.button_viewCaseDetails);
            Cls_Generic_Methods.clickElement(driver, oPage_ScheduleAdmission.button_createAdmission);
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_ScheduleAdmission.header_assignBed, 15);
            Cls_Generic_Methods.driverRefresh();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @BeforeMethod
    private void init() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        try {
            //Opening Organization Settings
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 16);

            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUsers), "clicked on All users");
        } catch (Exception e) {
            m_assert.assertFatal("Unable to open organisation setting " + e);
        }
    }

    @Test(enabled = true, description = "Validate View Patient Queue inventory policy", groups = "createPatient")
    public void validatePolicyViewPatientQueue() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_PatientQueue = new Page_PatientQueue(driver);
        oPage_Store = new Page_Store(driver);
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewComponent, sViewDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            boolean patientQueueTab = Cls_Generic_Methods.isElementDisplayed(oPage_Store.tab_patientQueue);
            m_assert.assertFalse(patientQueueTab, "Validated -->Inventory Patient Queue View Access-->Patient Queue Tab is disabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewComponent, sViewDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            patientQueueTab = Cls_Generic_Methods.isElementDisplayed(oPage_Store.tab_patientQueue);
            m_assert.assertTrue(patientQueueTab, "Validated -->Inventory Patient Queue View Access-->Patient Queue Tab is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Patient Queue policy " + e);
        }
    }

    @Test(enabled = true, description = "Validate View Consolidate Reports inventory policy")
    public void validatePolicyViewConsolidateReports() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_PatientQueue = new Page_PatientQueue(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewConsolidateReportsComponent, sViewConsolidateReportsDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_consolidateReportsButtonInStore, 5);
            boolean bConsolidateReportButton = Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_consolidateReportsButtonInStore);
            m_assert.assertFalse(bConsolidateReportButton, "Validated -->Inventory Consolidate Reports View Access-->Consolidate Reports is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewConsolidateReportsComponent, sViewConsolidateReportsDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_consolidateReportsButtonInStore, 5);
            bConsolidateReportButton = Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_consolidateReportsButtonInStore);
            m_assert.assertTrue(bConsolidateReportButton, "Validated -->Inventory Consolidate Reports View Access-->Consolidate Reports Tab is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Consolidate Reports policy " + e);
        }
    }

    @Test(enabled = true, description = "Validate View Billing Details inventory policy")
    public void validatePolicyViewBillingDetails() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_PatientQueue = new Page_PatientQueue(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewBillingDetailsComponent, sViewBillingDetailsDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_consolidateReportsButtonInStore, 5);
            boolean bBillDetails = Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_billButton);
            m_assert.assertFalse(bBillDetails, "Validated -->Inventory Billing Details View Access-->Billing Details is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewBillingDetailsComponent, sViewBillingDetailsDescription);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_billButton, 5);
            bBillDetails = Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_billButton);
            m_assert.assertTrue(bBillDetails, "Validated -->Inventory Billing Details View Access-->Billing Details is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Billing Details policy " + e);
        }
    }

    @Test(enabled = true, description = "Validate View Previous Billing Details inventory policy")
    public void validatePolicyViewPreviousBills() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_PatientQueue = new Page_PatientQueue(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPreviousBillsComponent, sViewPreviousBillsDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_consolidateReportsButtonInStore, 5);
            Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_billButton);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptButton, 6);

            boolean bPreviousBillDetails = Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.text_previousBills);
            m_assert.assertFalse(bPreviousBillDetails, "Validated -->Inventory Previous Bills View Access-->Previous Bills Details is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPreviousBillsComponent, sViewPreviousBillsDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_consolidateReportsButtonInStore, 5);
            Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_billButton);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptButton, 6);

            bPreviousBillDetails = Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.text_previousBills);
            m_assert.assertTrue(bPreviousBillDetails, "Validated -->Inventory Previous Bills View Access-->Previous Bills Details is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Previous Billing Details policy " + e);
        }
    }
    @Test(enabled = true, description = "Validate Advance Receipt inventory policy")
    public void validatePolicyAdvanceReceipt() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_PatientQueue = new Page_PatientQueue(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAdvanceReceiptPolicyComponent, sAdvanceReceiptDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_consolidateReportsButtonInStore, 5);
            Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_billButton);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptButton, 2);

            boolean bAdvanceReceiptButton = Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_advanceReceiptButton);
            m_assert.assertFalse(bAdvanceReceiptButton, "Validated -->Inventory Advance Receipt Create Access-->Advance Receipt is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAdvanceReceiptPolicyComponent, sAdvanceReceiptDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_consolidateReportsButtonInStore, 5);
            Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_billButton);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptButton, 6);

            bAdvanceReceiptButton = Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_advanceReceiptButton);
            m_assert.assertTrue(bAdvanceReceiptButton, "Validated -->Inventory Advance Receipt Create Access-->Advance Receipt is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Advance Receipt policy " + e);
        }
    }
    @Test(enabled = true, description = "Validate Advance Cancellation inventory policy")
    public void validatePolicyAdvanceCancellation() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_PatientQueue = new Page_PatientQueue(driver);
        String advanceReason = "TEST";


        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAdvanceCancelPolicyComponent, sAdvanceCancelDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_consolidateReportsButtonInStore, 5);
            Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_billButton);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptButton, 3);
            Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_advanceReceiptButton);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_advanceReceiptTemplate, 5);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_reasonAdvance, advanceReason);
            selectByOptions(oPage_PatientQueue.select_mop, "Cash");
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_amountAdvance, advanceAmount);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_saveAdvance);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_cancellationAdvance, 5);
            boolean bCancellationButton = Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_cancellationAdvance);
            m_assert.assertFalse(bCancellationButton, "Validated -->Inventory Advance Cancellation Access-->Advance Cancellation is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAdvanceCancelPolicyComponent, sAdvanceCancelDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_billButton, 6);
            Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_createdAdvance);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_cancellationAdvance, 5);
            bCancellationButton = Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_cancellationAdvance);
            m_assert.assertTrue(bCancellationButton, "Validated -->Inventory Advance Cancellation Access-->Advance Cancellation is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Advance Receipt policy " + e);
        }
    }
    @Test(enabled = true, description = "Validate View Template Patient Queue inventory policy")
    public void validatePolicyViewTemplatePatientQueue() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Store = new Page_Store(driver);

        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewTemplatePolicyComponent, sViewTemplatePolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_viewTemplateButton, 5);
            boolean bTemplateButton = Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_viewTemplateButton);

            m_assert.assertFalse(bTemplateButton, "Validated --> Inventory Patient OPD Template View Access --> Template is disabled in Patient Queue");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewTemplatePolicyComponent, sViewTemplatePolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_viewTemplateButton, 10);
            bTemplateButton = Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_viewTemplateButton);
            m_assert.assertTrue(bTemplateButton, "Validated --> Inventory Patient OPD Template View Access --> Template is enabled in Patient Queue");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate View Template Patient Queue policy " + e);
        }

    }
    @Test(enabled = true, description = "Validate Mark Converted Patient Queue inventory policy")
    public void validatePolicyMarkConvertedPatientQueue() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Store = new Page_Store(driver);


        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sMarkConvertedPolicyComponent, sMarkConvertedPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_markPatientVisitedButton, 5);
            boolean bMarkPatientVisitedButton = Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_markPatientVisitedButton);

            m_assert.assertFalse(bMarkPatientVisitedButton, "Validated --> Inventory Patient Conversion Access --> Mark Converted is disabled in Patient Queue");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sMarkConvertedPolicyComponent, sMarkConvertedPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_markPatientVisitedButton, 10);
            bMarkPatientVisitedButton = Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_markPatientVisitedButton);
            m_assert.assertTrue(bMarkPatientVisitedButton, "Validated --> Inventory Patient Conversion Access --> Mark Converted is enabled in Patient Queue");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Mark Converted Patient Queue policy " + e);
        }
    }
    @Test(enabled = true, description = "Validate Advance Return inventory policy")
    public void validatePolicyAdvanceReturn() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_PatientQueue = new Page_PatientQueue(driver);



        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAdvanceRefundPolicyComponent, sAdvanceRefundPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_consolidateReportsButtonInStore, 5);
            createSalesOrder();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_billButton, 6);
            Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_createdAdvance);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_refundAdvance, 5);
            boolean bRefundButton = Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_refundAdvance);
            m_assert.assertFalse(bRefundButton, "Validated -->Inventory Advance Return Access-->Advance Return is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired,  sAdvanceRefundPolicyComponent, sAdvanceRefundPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(4);
            selectPatientInPatientQueue();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_billButton, 6);
            Cls_Generic_Methods.clickElement(oPage_PatientQueue.button_createdAdvance);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PatientQueue.button_refundAdvance, 5);
            bRefundButton = Cls_Generic_Methods.isElementDisplayed(oPage_PatientQueue.button_refundAdvance);
            m_assert.assertTrue(bRefundButton, "Validated -->Inventory Advance Return Access-->Advance Return is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Advance Return policy " + e);
        }
    }
    @Test(enabled = true, description = "Validate Create Tray inventory policy")
    public void validatePolicyCreateTray() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Store = new Page_Store(driver);
        oPage_OtStore = new Page_OtStore(driver);


        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCreateTrayPolicyComponent, sCreateTrayPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sIPDStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueueOTStore();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 5);
            boolean bCreateTrayButton = Cls_Generic_Methods.isElementDisplayed(oPage_OtStore.button_createTray);

            m_assert.assertFalse(bCreateTrayButton, "Validated --> Inventory IPD Tray Creation Access --> Create Tray button is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCreateTrayPolicyComponent, sCreateTrayPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueueOTStore();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 10);
            bCreateTrayButton = Cls_Generic_Methods.isElementDisplayed(oPage_OtStore.button_createTray);
            m_assert.assertTrue(bCreateTrayButton, "Validated --> Inventory IPD Tray Creation Access --> Create Tray button is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Create Tray Patient Queue policy " + e);
        }
    }
    @Test(enabled = true, description = "Validate View Tray inventory policy")
    public void validatePolicyViewTray() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Store = new Page_Store(driver);
        oPage_OtStore = new Page_OtStore(driver);
        oPage_SalesOrder=new Page_SalesOrder(driver);

        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewTrayPolicyComponent, sViewTrayPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sIPDStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueueOTStore();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 10);
            Cls_Generic_Methods.clickElement(oPage_OtStore.button_createTray);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult,10);
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult.get(0));
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine,"1");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray,10);

            boolean bViewTrayButton = Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_viewTray);

            m_assert.assertFalse(bViewTrayButton, "Validated --> Inventory Tray Details View Access --> View Tray button is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewTrayPolicyComponent, sViewTrayPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueueOTStore();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 10);
            bViewTrayButton = Cls_Generic_Methods.isElementDisplayed(oPage_Store.button_viewTray);
            m_assert.assertTrue(bViewTrayButton, "Validated --> Inventory Tray Details View Access --> View Tray button is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate View Tray Patient Queue policy " + e);
        }
    }
    @Test(enabled = true, description = "Validate Edit Tray inventory policy")
    public void validatePolicyEditTray() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Store = new Page_Store(driver);
        oPage_OtStore = new Page_OtStore(driver);

        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTrayPolicyComponent, sEditTrayPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sIPDStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueueOTStore();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 10);
            Cls_Generic_Methods.clickElement(oPage_Store.button_viewTray);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_deleteButtonInViewTray,5);
            boolean bEditTrayButton = Cls_Generic_Methods.isElementDisplayed(oPage_OtStore.button_EditTrayButtonInViewTray);

            m_assert.assertFalse(bEditTrayButton, "Validated --> Inventory Tray Edit Access --> Edit Tray button is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditTrayPolicyComponent, sEditTrayPolicyDescription);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueueOTStore();
            Cls_Generic_Methods.clickElement(oPage_Store.button_viewTray);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_deleteButtonInViewTray,5);
            bEditTrayButton = Cls_Generic_Methods.isElementDisplayed(oPage_OtStore.button_EditTrayButtonInViewTray);
            m_assert.assertTrue(bEditTrayButton, "Validated --> Inventory Tray Edit Access --> Edit Tray button is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Edit Tray Patient Queue policy " + e);
        }
    }
    @Test(enabled = true, description = "Validate Delete Tray inventory policy")
    public void validatePolicyDeleteTray() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Store = new Page_Store(driver);
        oPage_OtStore = new Page_OtStore(driver);

        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sDeleteTrayPolicyComponent, sDeleteTrayPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sIPDStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueueOTStore();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 10);
            Cls_Generic_Methods.clickElement(oPage_Store.button_viewTray);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_EditTrayButtonInViewTray,5);
            boolean bDeleteTrayButton = Cls_Generic_Methods.isElementDisplayed(oPage_OtStore.button_deleteButtonInViewTray);

            m_assert.assertFalse(bDeleteTrayButton, "Validated --> Inventory Tray Delete Access --> Delete Tray button is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired,  sDeleteTrayPolicyComponent, sDeleteTrayPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueueOTStore();
            Cls_Generic_Methods.clickElement(oPage_Store.button_viewTray);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_EditTrayButtonInViewTray,5);
            bDeleteTrayButton = Cls_Generic_Methods.isElementDisplayed(oPage_OtStore.button_deleteButtonInViewTray);
            m_assert.assertTrue(bDeleteTrayButton, "Validated --> Inventory Tray Delete Access --> Delete Tray button is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Delete Tray Patient Queue policy " + e);
        }
    }
    @Test(enabled = true, description = "Validate Close Tray inventory policy" ,groups = "createBOM")
    public void validatePolicyCloseTray() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_Store = new Page_Store(driver);
        oPage_OtStore = new Page_OtStore(driver);

        try {

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sCloseTrayPolicyComponent, sCloseTrayPolicyDescription);
            Cls_Generic_Methods.customWait(3);

            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sIPDStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueueOTStore();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_createTray, 10);
            Cls_Generic_Methods.clickElement(oPage_Store.button_viewTray);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_CloseTrayButtonInViewTray,5);
            boolean bCloseTrayButton = Cls_Generic_Methods.isElementDisplayed(oPage_OtStore.button_CloseTrayButtonInViewTray);

            m_assert.assertFalse(bCloseTrayButton, "Validated --> Inventory Tray Close Access --> Close Tray button is disabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired,  sCloseTrayPolicyComponent, sCloseTrayPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup,10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            selectPatientInPatientQueueOTStore();
            Cls_Generic_Methods.clickElement(oPage_Store.button_viewTray);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OtStore.button_CloseTrayButtonInViewTray,5);
            bCloseTrayButton = Cls_Generic_Methods.isElementDisplayed(oPage_OtStore.button_CloseTrayButtonInViewTray);
            m_assert.assertTrue(bCloseTrayButton, "Validated --> Inventory Tray Close Access --> Close Tray button is enabled");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Close Tray Patient Queue policy " + e);
        }
    }
    public void createSalesOrder() {
        oPage_PatientQueue = new Page_PatientQueue(driver);
        oPage_Store = new Page_Store(driver);
        oPage_SalesOrder = new Page_SalesOrder(driver);
        String myMedicationName = "SalesOrderTest1";


        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_markPatientVisitedButton,10);
            Cls_Generic_Methods.clickElement(oPage_Store.button_markPatientVisitedButton);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.button_yesMarkConvertedButton, 10);
            Cls_Generic_Methods.clickElement(oPage_Store.button_yesMarkConvertedButton);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Store.input_salesOrderDoctorName, 10);
            Cls_Generic_Methods.customWait(5);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_searchMedicineNameInDescription, myMedicationName), "Entering the medication name as" + myMedicationName + " in description textbox for sales order");
            Cls_Generic_Methods.customWait(3);
            oPage_SalesOrder.input_searchMedicineNameInDescription.sendKeys(Keys.ENTER);
            Cls_Generic_Methods.customWait(3);
            boolean myMedicationFound = false;
            Cls_Generic_Methods.customWait(5);

            for (WebElement eMedication : oPage_SalesOrder.list_namesOfMedicinesOnLeftInSearchResult) {
//                    Cls_Generic_Methods.customWait(5);
                if(Cls_Generic_Methods.isElementDisplayed(eMedication)){
                    if (Cls_Generic_Methods.getTextInElement(eMedication).contains(myMedicationName)) {
                        Cls_Generic_Methods.clickElement(eMedication);
                        myMedicationFound = true;
                        Cls_Generic_Methods.customWait(4);
                        break;
                    }
                }
            }
            m_assert.assertTrue(myMedicationFound, "Required medication " + myMedicationName + " found for Sales Order");

            Cls_Generic_Methods.customWait(5);
            if(Cls_Generic_Methods.isElementDisplayed(oPage_SalesOrder.checkout_lot)){
                Cls_Generic_Methods.clickElementByJS(driver,oPage_SalesOrder.checkout_lot);
                Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_saveChangesLot);
            }
            Cls_Generic_Methods.customWait(5);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_SalesOrder.input_quantityOfMedicine, "1");
            Cls_Generic_Methods.customWait(5);
            Cls_Generic_Methods.selectElementByValue(oPage_PatientQueue.select_modeOfPaymentInSalesOrder, "Cash");

            String totalAmountValue = Cls_Generic_Methods.getElementAttribute(oPage_PatientQueue.input_totalAmount, "value");
            double settleAdvance=(Double.parseDouble(advanceAmount)-5);
            totalAmountValue=String.valueOf(Double.parseDouble(totalAmountValue)-settleAdvance);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_totalAmountInPayment, totalAmountValue);

            Cls_Generic_Methods.clearValuesInElement(oPage_PatientQueue.input_settleAmountAdvanceInPayment);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PatientQueue.input_settleAmountAdvanceInPayment,String.valueOf(settleAdvance));

            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_SaveChanges);
            Cls_Generic_Methods.customWait(5);
            Cls_Generic_Methods.clickElement(oPage_SalesOrder.button_closeModalOfSalesOrder);
            Cls_Generic_Methods.customWait(2);

        } catch (Exception e) {
            m_assert.assertFatal("Unable to create sales order "+e);
            e.printStackTrace();
        }
    }

    @BeforeMethod(onlyForGroups = "createBOM")
    public void createBillOfMaterial(){
        Page_IPD oPage_IPD=new Page_IPD(driver);
        Page_AdmissionInPreOperative oPage_AdmissionInPreOperative=new Page_AdmissionInPreOperative(driver);
        Page_BillOfMaterial oPage_BillOfMaterial=new Page_BillOfMaterial(driver);
        oPage_CommonElements=new Page_CommonElements(driver);
        try{
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait();
            CommonActions.selectDepartmentOnApp("IPD");
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_IPD.rows_patientNamesOnIPD,10);
            CommonActions.selectTabOnDepartmentPage(oPage_IPD.tabs_TabsOnIPD, IPD_Data.tab_Scheduled_Today);
            Cls_Generic_Methods.waitForElementsToBeDisplayed(oPage_IPD.rows_patientNamesOnIPD,10);
            CommonActions.selectPatientNameInIpd(oPage_IPD.rows_patientNamesOnIPD, patientName);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection, 16);

            Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_admissionInPreOperative);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AdmissionInPreOperative.tab_AdminTabOnAdmissionUnderPreOperative, 16);

            Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.tab_AdminTabOnAdmissionUnderPreOperative);
            Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.input_reasonForAdmissionUnderAdminTab);
            Cls_Generic_Methods.clearValuesInElement(oPage_AdmissionInPreOperative.input_reasonForAdmissionUnderAdminTab);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_AdmissionInPreOperative.input_reasonForAdmissionUnderAdminTab, "AUTO");

            Cls_Generic_Methods.clickElement(oPage_AdmissionInPreOperative.button_saveOnModalHeader);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.button_CloseTemplate, 16);
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_CloseTemplate);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_billOfMaterialTemplate, 20);
            Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_billOfMaterialTemplate);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_addBillOfMaterial, 4);
            Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_addBillOfMaterial);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_BillOfMaterial.button_saveBom, 8);

            for (WebElement consumedQty : oPage_BillOfMaterial.list_inputConsumedQuantityListInBom) {
                if (Cls_Generic_Methods.isElementDisplayed(consumedQty)) {
                    Cls_Generic_Methods.sendKeysIntoElement(consumedQty, "1");
                }
            }
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.clickElement(driver, oPage_BillOfMaterial.button_saveBom);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.text_preOperativeSection,10);

        }catch(Exception e){
            e.printStackTrace();
            m_assert.assertFatal("Unable to create Bill Of Material ->"+e);
        }
    }


    public void selectPatientInPatientQueue() {
        try {
            oPage_Store = new Page_Store(driver);
            oPage_PatientQueue=new Page_PatientQueue(driver);
            try {
                CommonActions.selectTabOnDepartmentPage(oPage_Store.tabs_storeTabsIPharmacyPatientQueuePage, tab_ALL);
            }catch (Exception ignored){}
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectPatientNameInPatientQueue(oPage_PatientQueue.rows_patientAppointmentsInPharmacyPatientQueue, patientName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void  selectPatientInPatientQueueOTStore() {

        Page_OtStore oPage_OtStore = new Page_OtStore(driver);
        boolean bPatientNameFoundAndClicked = false;

        try {
            for (WebElement patientNameElement : oPage_OtStore.list_patientNameListInQueue) {
                if (Cls_Generic_Methods.isElementDisplayed(patientNameElement)) {
                    String value = Cls_Generic_Methods.getElementAttribute(patientNameElement, "title");
                    if (patientName.equals(value.trim())) {
                        Cls_Generic_Methods.clickElement(driver, patientNameElement);
                        Cls_Generic_Methods.customWait(5);
                        bPatientNameFoundAndClicked = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Patient Is not present or not clicked" + e);
        }
    }
    public boolean selectByOptions(WebElement selectElement, String optionToSelect) {
        boolean status = false;
        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(selectElement, 10);
            Cls_Generic_Methods.clickElementByJS(driver, selectElement);
            List<WebElement> allOptions = selectElement.findElements(By.xpath("./option"));
            for (WebElement option : allOptions) {
                String optionValue = Cls_Generic_Methods.getTextInElement(option);
                if (optionValue.contains((optionToSelect))) {
                    Cls_Generic_Methods.clickElement(option);
                    status = true;
                    break;
                }
            }
        } catch (Exception e) {
            m_assert.assertFatal("Unable to select " + optionToSelect + " -->" + e);
        }
        return status;
    }


}
