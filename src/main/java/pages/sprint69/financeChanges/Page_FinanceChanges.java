package pages.sprint69.financeChanges;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_FinanceChanges extends TestBase {
    private WebDriver driver;

    public Page_FinanceChanges(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);


    }
    //Reciept template changes

    @FindBy(xpath = "//input[@id='invoice_template_name']")
    public WebElement input_ReceiptTemplateName;

    @FindBy(xpath = "//select[@id='invoice_template_facility_id']")
    public WebElement select_locationFieldUnderReceiptTemplateModal;

    @FindBy(xpath = "//select[@id='invoice_template_specialty_id']")
    public WebElement select_specialityFieldUnderReceiptTemplateModal;

    @FindBy(xpath = "//button[@name='department_id']")
    public List<WebElement> list_departmentButton;

    @FindBy(xpath = "//select[@id='invoice_template_payer_master_id']")
    public WebElement select_contactType;

    @FindBy(xpath = "//span[@title='Select Service']")
    public List<WebElement> list_serviceSelectionFieldUnderReceiptTemplateModal;

    @FindBy(xpath = "//ul[@class='select2-results__options']//li")
    public List<WebElement> list_serviceOrPackagePresentUnderServiceSelectionField;

    @FindBy(xpath = "//span[@title='Select Package']")
    public List<WebElement> list_packageSelectionUnderReceiptTemplateModal;

    @FindBy(xpath = "//input[@placeholder='Quantity']")
    public List<WebElement> list_inputQuantityFieldUnderReceiptTemplateModal;

    @FindBy(xpath = "//input[@placeholder='Unit Price']")
    public List<WebElement> list_inputUnitPriceFieldUnderReceiptTemplateModal;

    @FindBy(xpath = "//input[@placeholder='Total']")
    public List<WebElement> list_inputTotalFieldUnderReceiptTemplateModal;

    @FindBy(xpath = "//button[contains(@class, 'remove_service')]")
    public List<WebElement> list_RemoveFieldButtonReceiptTemplateModal;

    @FindBy(xpath = "//button[contains(@class, 'add_service_item')]")
    public WebElement button_AddNewItemButtonUnderReceiptTemplateModal;

    @FindBy(xpath = "  //button[contains(@class, 'add_service_package')]")
    public WebElement button_AddNewPackageButtonUnderReceiptTemplateModal;

    @FindBy(xpath = "//input[@value='Create Template']")
    public WebElement button_createReceiptTemplate;

    @FindBy(xpath = "//a[@id='new-invoice-template']")
    public WebElement button_addInvoiceTemplate;

    @FindBy(xpath = "//tbody[@id='invoice_template-list']//tr/td[1]")
    public List<WebElement> list_invoiceTemplateName;

    @FindBy(xpath = "//tbody[@id='invoice_template-list']//tr/td[3]/a[1]")
    public List<WebElement> list_editInvoiceTemplateButton;

    @FindBy(xpath = "//tbody[@id='invoice_template-list']//tr/td[3]/a[2]")
    public List<WebElement> list_deleteInvoiceTemplateButton;

    @FindBy(xpath = "//input[@value='Update Template']")
    public WebElement button_updateTemplate;

    @FindBy(xpath = "//select[@id='template_set_details_option']")
    public WebElement select_invoiceSets;

    @FindBy(xpath = "//b[text()='Invoice Type :']")
    public WebElement text_invoiceType;

    @FindBy(xpath = "//input[@id='invoice_total_payment_remaining']")
    public WebElement input_amountRemainingFieldUnderDraftBill;

    @FindBy(xpath = "//div[@class='row service_item_list']/div[1]/div[1]/div[1]/div[2]")
    public List<WebElement> list_itemDescription;

    @FindBy(xpath = "//input[@name='invoice_opd[payment_pending_breakups_attributes][0][amount]']")
    public WebElement input_amountFieldUnderOPDDraftBill;

    @FindBy(xpath = "//input[@name='invoice_ipd[payment_pending_breakups_attributes][0][amount]']")
    public WebElement input_amountFieldUnderIPDDraftBill;
    @FindBy(xpath = "//input[@name='invoice_opd[payment_received_breakups_attributes][0][amount_received]']")
    public WebElement input_amountFieldUnderTemplateDraftBill;
    @FindBy(xpath = "//input[@name='invoice_ipd[payment_received_breakups_attributes][0][amount_received]']")
    public WebElement input_amountFieldUnderIpdTemplateDraftBill;


    @FindBy(xpath = "//input[@value='Save as Draft']")
    public WebElement button_saveDraftBill;

    @FindBy(xpath = "//*[@id='appointment-overview-tab']//button[contains(text(),'₹Template Bills')] ")
    public WebElement button_opdTemplateBills;

    @FindBy(xpath = "//*[@id='admission_summary']//button[contains(text(),'₹Template Bills')]")
    public WebElement button_ipdTemplateBills;

    @FindBy(xpath = "//*[@id='appointment-overview-tab']/div[2]/div/div/div[2]/div[1]/ul/li")
    public List<WebElement> list_receiptTemplateUnderOPDTemplateBills;

    @FindBy(xpath = "//*[@id='admission_summary']/div/div[5]/div[2]/div[2]/div/div[1]/ul/li   ")
    public List<WebElement> list_receiptTemplateUnderIPDTemplateBills;

    @FindBy(xpath = "//*[@id='new_invoice_opd']/div[3]/input ")
    public WebElement button_saveOPDReceipt;

    @FindBy(xpath = "//*[@id='new_invoice_ipd']/div[3]/input")
    public WebElement button_saveIPDReceipt;

    @FindBy(xpath = "//div[@class='modal false fade in']//button[text()='Confirm']")
    public WebElement button_confirmDelete;

    //Insurance Master
    @FindBy(xpath = "//a[contains(text(),'Add Insurance')]")
    public WebElement button_addInsurance;

    @FindBy(xpath = "//h4[contains(text(),'Add Insurance')]")
    public WebElement text_addInsuranceLabel;

    @FindBy(xpath = "//input[@id='finance_insurance_master_name']")
    public WebElement inputField_insuranceName;

    @FindBy(xpath = "//input[@id='finance_insurance_master_description']")
    public WebElement inputField_insuranceDescription;

    @FindBy(xpath = "//input[@value='Save Changes']")
    public WebElement button_saveChanges;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_Close;

    @FindBy(xpath = "//tbody[@id='insurance_master-list']/tr/td[1]/b")
    public List<WebElement> list_insuranceName;

    @FindBy(xpath = "//tbody[@id='insurance_master-list']/tr/td[2]/b")
    public List<WebElement> list_insuranceDescription;

    @FindBy(xpath = "//tbody[@id='insurance_master-list']/tr/td[3]/table/tbody/tr/td[1]/a")
    public List<WebElement> list_editButton;

    @FindBy(xpath = "//tbody[@id='insurance_master-list']/tr/td[3]/table/tbody/tr/td[2]")
    public List<WebElement> list_disableAndActiveButton;

    @FindBy(xpath = "//div[@id='confirmation-modal']//a[text()='Confirm']")
    public WebElement button_confirm;

    //TPA master

    @FindBy(xpath = "//a[contains(text(),'Add Tpa')]")
    public WebElement button_addTPA;

    @FindBy(xpath = "//h4[contains(text(),'Add Tpa')]")
    public WebElement text_addTpaLabel;

    @FindBy(xpath = "//input[@id='finance_tpa_master_name']")
    public WebElement inputField_tpaName;

    @FindBy(xpath = "//input[@id='finance_tpa_master_description']")
    public WebElement inputField_tpaDescription;

    @FindBy(xpath = "//input[@placeholder='Search By Tpa Name']")
    public WebElement search_tpaName;

    @FindBy(xpath = "//tbody[@id='tpa_master-list']/tr/td[1]/b")
    public List<WebElement> list_tpaName;

    @FindBy(xpath = "//tbody[@id='tpa_master-list']/tr/td[2]/b")
    public List<WebElement> list_tpaDescription;

    @FindBy(xpath = "//tbody[@id='tpa_master-list']/tr/td[3]/table/tbody/tr/td[1]/a")
    public List<WebElement> list_editTpaButton;

    @FindBy(xpath = "//tbody[@id='tpa_master-list']/tr/td[3]/table/tbody/tr/td[2]")
    public List<WebElement> list_disableAndActiveTpaButton;

//Panel master

    @FindBy(xpath = "//a[contains(text(),'Add Panel')]")
    public WebElement button_addPanel;

    @FindBy(xpath = "//h4[contains(text(),'Add Panel')]")
    public WebElement text_addPanelLabel;

    @FindBy(xpath = "//input[@id='finance_panel_master_name']")
    public WebElement inputField_panelName;

    @FindBy(xpath = "//input[@id='finance_panel_master_description']")
    public WebElement inputField_panelDescription;

    @FindBy(xpath = "//input[@placeholder='Search By Panel Name']")
    public WebElement search_panelName;

    @FindBy(xpath = "//tbody[@id='panel_master-list']/tr/td[1]/b")
    public List<WebElement> list_panelName;

    @FindBy(xpath = "//tbody[@id='panel_master-list']/tr/td[2]/b")
    public List<WebElement> list_panelDescription;

    @FindBy(xpath = "//tbody[@id='panel_master-list']/tr/td[3]/table/tbody/tr/td[1]/a")
    public List<WebElement> list_editPanelButton;

    @FindBy(xpath = "//tbody[@id='panel_master-list']/tr/td[3]/table/tbody/tr/td[2]")
    public List<WebElement> list_disableAndActivePanelButton;

    @FindBy(xpath = "//select[@id='payer_master_contact_group_id']")
    public WebElement select_contactGroup;

    @FindBy(xpath = "//span[@id='select2-payer_master_payer_type_id-container']")
    public WebElement field_payerTypeMaster;

    @FindBy(xpath = "//ul[@id='select2-payer_master_payer_type_id-results']/li")
    public List<WebElement> list_payerTypeMaster;

// Corporate master

    @FindBy(xpath = "//a[contains(text(),'Add Corporate')]")
    public WebElement button_addCorporate;

    @FindBy(xpath = "//h4[contains(text(),'Add Corporate')]")
    public WebElement text_addCorporateLabel;

    @FindBy(xpath = "//input[@id='finance_corporate_master_name']")
    public WebElement inputField_corporateName;

    @FindBy(xpath = "//input[@id='finance_corporate_master_description']")
    public WebElement inputField_corporateDescription;

    @FindBy(xpath = "//input[@placeholder='Search By Corporate Name']")
    public WebElement search_corporateName;

    @FindBy(xpath = "//tbody[@id='corporate_master-list']/tr/td[1]/b")
    public List<WebElement> list_corporateName;

    @FindBy(xpath = "//tbody[@id='corporate_master-list']/tr/td[2]/b")
    public List<WebElement> list_corporateDescription;

    @FindBy(xpath = "//tbody[@id='corporate_master-list']/tr/td[3]/table/tbody/tr/td[1]/a")
    public List<WebElement> list_editCorporateButton;

    @FindBy(xpath = "//tbody[@id='corporate_master-list']/tr/td[3]/table/tbody/tr/td[2]")
    public List<WebElement> list_disableAndActiveCorporateButton;


// Dispensary master

    @FindBy(xpath = "//a[contains(text(),' Add Dispensary')]")
    public WebElement button_addDispensary;

    @FindBy(xpath = "//h4[contains(text(),'Add Dispensary')]")
    public WebElement text_addDispensaryLabel;

    @FindBy(xpath = "//input[@id='finance_dispensary_master_name']")
    public WebElement inputField_dispensaryName;

    @FindBy(xpath = "//input[@id='finance_dispensary_master_description']")
    public WebElement inputField_dispensaryDescription;

    @FindBy(xpath = "//input[@placeholder='Search By Dispensary Name']")
    public WebElement search_dispensaryName;

    @FindBy(xpath = "//tbody[@id='dispensary_master-list']/tr/td[1]/b")
    public List<WebElement> list_dispensaryName;

    @FindBy(xpath = "//tbody[@id='dispensary_master-list']/tr/td[2]/b")
    public List<WebElement> list_dispensaryDescription;

    @FindBy(xpath = "//tbody[@id='dispensary_master-list']/tr/td[3]/table/tbody/tr/td[1]/a")
    public List<WebElement> list_editDispensaryButton;

    @FindBy(xpath = "//tbody[@id='dispensary_master-list']/tr/td[3]/table/tbody/tr/td[2]")
    public List<WebElement> list_disableAndActiveDispensaryButton;

// Patient payer data master

    @FindBy(xpath = "//a[contains(text(),' Add Patient Payer Data')]")
    public WebElement button_addPatientPayerData;

    @FindBy(xpath = "//h4[contains(text(),'Add Patient Payer Data')]")
    public WebElement text_addPatientPayerDataLabel;

    @FindBy(xpath = "//input[@id='finance_patient_payer_data_master_name']")
    public WebElement inputField_patientPayerDataName;

    @FindBy(xpath = "//input[@id='finance_patient_payer_data_master_description']")
    public WebElement inputField_patientPayerDataDescription;

    @FindBy(xpath = "//input[@placeholder='Search By Patient Payer Data Name']")
    public WebElement search_PatientPayerDataName;

    @FindBy(xpath = "//tbody[@id='patient_payer_master-list']//tr/td[1]/b")
    public List<WebElement> list_PatientPayerDataName;

    @FindBy(xpath = "//tbody[@id='patient_payer_master-list']//tr/td[2]/b")
    public List<WebElement> list_PatientPayerDataDescription;

    @FindBy(xpath = "//tbody[@id='patient_payer_master-list']/tr/td[3]/table/tbody/tr/td[1]/a")
    public List<WebElement> list_editPatientPayerDataButton;

    @FindBy(xpath = "//tbody[@id='patient_payer_master-list']/tr/td[3]/table/tbody/tr/td[2]")
    public List<WebElement> list_disableAndActivePatientPayerDataButton;

    @FindBy(xpath = "//div[@target-input-id='payer_master_patient_payer_data_details']//button")
    public List<WebElement> list_patientPayerDataButton;

    //payer master
    @FindBy(xpath = "//a[contains(text(),'Add Payer Master')]")
    public WebElement button_addPayerMaster;

    @FindBy(xpath = "//h4[contains(text(),'Add Payer Form')]")
    public WebElement label_addPayerForm;

    //billing section
    @FindBy(xpath = "//label[@for='invoice_type_credit']")
    public WebElement radioButton_creditType;

    @FindBy(xpath = "//select[@id='invoice_opd_contact_group_id']")
    public WebElement select_payerType;

    @FindBy(xpath = "//span[@id='select2-invoice_opd_corporate_id-container']")
    public WebElement field_corporateDropdown;

    @FindBy(xpath = "//select[@id='invoice_opd_payer_master_id']")
    public WebElement select_contactUnderBills;

    @FindBy(xpath = "//span[@id='select2-invoice_opd_dispensary_id-container']")
    public WebElement field_dispensaryDropdownUnderBillingSection;

    @FindBy(xpath = "//ul[@id='select2-invoice_opd_corporate_id-results']/li")
    public List<WebElement> list_corporateNameUnderBills;

    @FindBy(xpath = "//ul[@id='select2-invoice_opd_dispensary_id-results']/li")
    public List<WebElement> list_dispensaryNameUnderBills;

// Cashless hospitalisation add on Xpaths
   @FindBy(xpath = "//select[@id='payer_master_facility_id']")
   public WebElement select_facilityToCreatePayer;

    @FindBy(xpath = "//select[@id='payer_master_payer_type_id']")
    public WebElement select_payerTypeMaster;

    @FindBy(xpath = "//input[@id='payer_master_display_name']")
    public WebElement input_payerMasterDisplayName;

    @FindBy(xpath = "//input[@value='Save Payer']")
    public WebElement button_savePayerForm;

    @FindBy(xpath = "//button[contains(@class,'payer-master-search')]/span[@id='payer-master-search-criteria']")
    public WebElement button_payerMasterSearchCriteriaButton;

    @FindBy(xpath = "//ul[contains(@class,'payer-dropdown')]/li")
    public List<WebElement> list_payerMasterSearchCriteriaDropdown;

    @FindBy(xpath = "//input[@id='payer-master-search']")
    public WebElement input_payerMasterSearchField;

    @FindBy(xpath = "//button/following::button/span[text()='Submit']")
    public WebElement button_submitPayerMasterSearch;

    @FindBy(xpath = "//tbody[@id='payer_master-list']/tr/td[1]/span")
    public List<WebElement> list_facilityNameUnderPayerMasterSection;

    @FindBy(xpath = "//tbody[@id='payer_master-list']/tr/td[2]/a")
    public List<WebElement> list_countOfPayersUnderEachFacilitysection;

    @FindBy(xpath = "//tbody[@id='payer_master-list']/tr/td[1]/span/b")
    public List<WebElement> list_payersPresentUnderSelectedFacility;

    @FindBy(xpath = "//div[@class='row payer-list']/descendant::div[1]/b")
    public WebElement text_facilityName;

    @FindBy(xpath = "//h4[text()='Pricing Master']")
    public WebElement text_pricingMaster;

    @FindBy(xpath = "//tbody[@id='pricing_master-list']/tr/td[1]")
    public List<WebElement> list_facilityNameUnderPricingMasterSection;

    @FindBy(xpath = "//tbody[@id='pricing_master-list']/tr/td[3]/span/a[1]")
    public List<WebElement> list_countOfPricingMastersUnderEachFacility;

    @FindBy(xpath = "//a[contains(text(),'Add Payee Pricing')]")
    public WebElement button_addPricingMaster;

    @FindBy(xpath = "//tbody[@id='sub-pricing_master-list']/tr/td[3]")
    public List<WebElement> list_departmentUnderPricingMasterSection;

    @FindBy(xpath = "//tbody[@id='sub-pricing_master-list']/tr/td[6]/a")
    public List<WebElement> list_editPricingDetailsButtonUnderPricingMasterSection;

    @FindBy(xpath = "//button[contains(@class,'btn-add-pricing')]")
    public WebElement button_addContactPricingUnderUpdatePricingMasterForm;

    @FindBy(xpath = "//select[contains(@name,'contact_group_id')]")
    public List<WebElement> list_contactGroupFieldUnderUpdatePricingDetailsButtonUnderUpdatePricingMaster;

    @FindBy(xpath = "//button[@id='service-master-dropdown']/span[1]")
    public WebElement button_addServiceMaster;

    @FindBy(xpath = "//ul[contains(@class,'service-master-dropdown-content')]/li/a")
    public List<WebElement> list_serviceMasterDropdownContent;

    @FindBy(xpath = "//select[@id='service_master_specialty_id']")
    public WebElement select_serviceMasterSpeciality;

    @FindBy(xpath = "//select[@id='service_master_sub_specialty_id']")
    public WebElement select_serviceMasterSubSpeciality;

    @FindBy(xpath = "//button[text()='IPD']")
    public WebElement button_IpdDepartment;

    @FindBy(xpath = "//select[@id='service_master_service_type']")
    public WebElement select_serviceMasterType;

    @FindBy(xpath = "//select[@id='service_master_service_group_id']")
    public WebElement select_serviceMasterGroup;

    @FindBy(xpath = "//select[@id='service_master_service_sub_group_id']")
    public WebElement select_serviceMasterSubGroup;

    @FindBy(xpath = "//input[@id='service_master_service_name']")
    public WebElement input_serviceMasterName;

    @FindBy(xpath = "//input[@id='service_master_organisation_service_code']")
    public WebElement input_serviceMasterCode;

    @FindBy(xpath = "//input[@id='service_master_service_amount']")
    public WebElement input_serviceMasterAmount;

    @FindBy(xpath = "//button[@id='select_all_facility']")
    public WebElement hyperlink_selectAllFacility;

    @FindBy(xpath = "//input[@id='new-service-master-form']")
    public WebElement button_saveServiceMasterForm;

    @FindBy(xpath = "//button[text()='Close']")
    public WebElement button_closeServiceMasterForm;

    @FindBy(xpath = "//input[contains(@class,'search-pricing-master')]")
    public WebElement input_searchServiceName;

    @FindBy(xpath = "//select[contains(@name,'payer_master_id')]")
    public List<WebElement> list_payeeListFieldUnderUpdatePricingDetailsButtonUnderUpdatePricingMaster;
    @FindBy(xpath = "//input[@value='Save']")
    public WebElement button_saveUpdatedPricingMasterForm;





}



