package tests.sprint69.financeChanges;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class financeChangesTest extends TestBase {
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

        @FindBy(xpath = "//span[@id='select2-invoice_template_payer_master_id-container']")
        public WebElement dropdown_contactSelectionFieldReceiptTemplateModal;

        @FindBy(xpath = "//ul[@id='select2-invoice_template_payer_master_id-results']//li")
        public List<WebElement> list_contactTypeContactField;
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

        @FindBy(xpath = "//button[@class='btn btn-danger btn-xs remove_service']")
        public List<WebElement> list_RemoveFieldButtonReceiptTemplateModal;

        @FindBy(xpath = "//button[@class='btn btn-primary btn-xs add_service_item']")
        public WebElement button_AddNewItemButtonUnderReceiptTemplateModal;

        @FindBy(xpath = "//button[@class='btn btn-primary btn-xs add_service_package']")
        public WebElement button_AddNewPackageButtonUnderReceiptTemplateModal;

        @FindBy(xpath = "//input[@value='Create Template']")
        public WebElement button_createReceiptTemplate;

        @FindBy(xpath = "//button[text()='Close']")
        public WebElement button_closeReceiptTemplate;

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

        @FindBy(xpath = "//*[@id='new_invoice_ipd']/div[3]/input")
        public WebElement button_confirmDelete;
    }
}