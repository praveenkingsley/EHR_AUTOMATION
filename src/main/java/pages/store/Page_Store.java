package pages.store;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Store extends TestBase {
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_Store(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = " //div[@class='mainpanel']//h4")
    public WebElement header_panelOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li/a")
    public List<WebElement> list_ParentOptionsOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li/a/span")
    public List<WebElement> list_namesParentOptionsOnLeft;

    @FindBy(xpath = "//ul[@class='nav nav-pills nav-stacked nav-bracket']/li//li")
    public List<WebElement> list_ChildOptionsOnLeft;
    @FindBy(xpath = "//a[contains(text(),'Vendors')]")
    public WebElement button_storeInventoryVendorsButton;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']/tr")
    public List<WebElement> list_tableRowListInStoreInventory;

    @FindBy(xpath = "//tbody[@class='inventory-table-body']/tr/td[4]")
    public WebElement list_emptyItemListInStoreInventory;

    @FindBy(xpath = "//strong[text()='Nothing to Display']")
    public WebElement text_nothingToDisplayMessage;

    @FindBy(xpath = "//a[text()=' New']")
    public WebElement button_addNewButtonInventory;
    @FindBy(xpath = "//div[contains(@class,'row no_margin optical_tabs')]//li")
    public List<WebElement> tabs_storeTabsInPatientQueuePage;
    @FindBy(xpath = "//div[contains(@class,'row no_margin pharmacy_tabs')]//li")
    public List<WebElement> tabs_storeTabsIPharmacyPatientQueuePage;
    @FindBy(xpath = "//div[contains(@class,'row no_margin optical_tabs')]//li//span[@class='my_queue_list']")
    public WebElement tabs_storeMyQueueTabCountsInPatientQueuePage;
    @FindBy(xpath = "//div[contains(@class,'row no_margin optical_tabs')]//li//span[@class='all_list_count']")
    public WebElement tabs_storeAllTabCountsInPatientQueuePage;
    @FindBy(xpath = "//div[contains(@class,'row no_margin optical_tabs')]//li//span[@class='converted_list_count']")
    public WebElement tabs_storeConvertedCountsInPatientQueuePage;
    @FindBy(xpath = "//div[contains(@class,'row no_margin optical_tabs')]//li//span[@class='not_converted_list_count']")
    public WebElement tabs_storeNotConvertedCountsInPatientQueuePage;

    @FindBy(xpath = "//div[contains(@class,'row no_margin pharmacy_tabs')]//li//span[@class='my_queue_list']")
    public WebElement tabs_storeMyQueueTabCountsPharmacyInPatientQueuePage;
    @FindBy(xpath = "//div[contains(@class,'row no_margin pharmacy_tabs')]//li//span[@class='all_list_count']")
    public WebElement tabs_storeAllTabCountsPharmacyInPatientQueuePage;
    @FindBy(xpath = "//div[contains(@class,'row no_margin pharmacy_tabs')]//li//span[@class='converted_list_count']")
    public WebElement tabs_storeConvertedCountsPharmacyInPatientQueuePage;
    @FindBy(xpath = "//div[contains(@class,'row no_margin pharmacy_tabs')]//li//span[@class='not_converted_list_count']")
    public WebElement tabs_storeNotConvertedCountsPharmacyInPatientQueuePage;

    @FindBy(xpath = "//div[contains(@class,'row no_margin summary_optical summary_optical_clickable')]")
    public List<WebElement> rows_patientAppointmentsInPatientQueue;
    @FindBy(xpath = "//div[@class='container-fluid']//h4")
    public WebElement header_storeDetailsHeader;
    @FindBy(xpath = "//img[@class='patient_profile_pic']")
    public WebElement img_patientProfilePic;
    @FindBy(xpath = "//div[@id='optical_summary']//div[text()=':']/preceding-sibling::div")
    public List<WebElement> keysInRow_PatientDetails;
    @FindBy(xpath = "//div[@id='optical_summary']//div[text()=':']/following-sibling::div")
    public List<WebElement> valuesInRow_PatientDetails;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-primary-transparent btn-xs']")
    public WebElement button_consolidateReportsButtonInStore;
    @FindBy(xpath = "//div[@id='patient_history_allergy']/div/div/div/div[1]/b")
    public List<WebElement> list_allergiesHeaderListInStore;
    @FindBy(xpath = "//b[text()='Ophthalmic & Systemic :']/parent::h5/following-sibling::label")
    public List<WebElement> list_ophthalmicAndSystemicHistoryDataListInStore;
    @FindBy(xpath = "//b[text()='Medical :']/parent::h5/following-sibling::label")
    public WebElement list_medicalHistoryDataListInStore;
    @FindBy(xpath = "//b[text()='Family :']/parent::h5/following-sibling::label")
    public WebElement list_familyHistoryDataListInStore;
    @FindBy(xpath = "//b[text()='Drug (Allergies) :']/parent::h5/following-sibling::label")
    public List<WebElement> list_drugAllergiesHistoryDataListInStore;
    @FindBy(xpath = "//b[text()='Contact (Allergies) :']/parent::h5/following-sibling::label")
    public List<WebElement> list_contactAllergiesHistoryDataListInStore;
    @FindBy(xpath = "//b[text()='Food (Allergies) :']/parent::h5/following-sibling::label")
    public List<WebElement> list_foodAllergiesHistoryDataListInStore;
    @FindBy(xpath = "//b[text()='Other (Allergies) :']/parent::h5/following-sibling::label")
    public WebElement list_otherAllergiesHistoryDataListInStore;
    @FindBy(xpath = "//label[@class='label label-danger']")
    public WebElement label_arrivedStatus;
    @FindBy(xpath = "//i[@class='fa fa-long-arrow-alt-right']")
    public WebElement arrow_userDirectionArrow;
    @FindBy(xpath = "//label[@class='label label-primary']")
    public WebElement label_assignedDoctorName;
    @FindBy(xpath = "//a[@class='btn btn-xs btn-primary']")
    public WebElement button_tagLineButton;
    @FindBy(xpath = "//span[text()='Advised By:']")
    public WebElement span_advisedByTextLabel;
    @FindBy(xpath = "//span[text()='Advised By:']/following-sibling::strong")
    public WebElement span_assignedByDoctorName;
    @FindBy(xpath = "//span[text()='View Template:']")
    public WebElement span_viewTemplateLabel;
    @FindBy(xpath = "//a[@class='btn btn-primary btn-xs navnew-btn-pri hg-tooltip']")
    public WebElement button_viewTemplateButton;
    @FindBy(xpath = "//button[text()='Mark Patient Visited']")
    public WebElement button_markPatientVisitedButton;
    @FindBy(xpath = "//div[text()=' Mark Converted: ']")
    public WebElement text_markConvertedLabelText;
    @FindBy(xpath = "//a[text()='Yes']")
    public WebElement button_yesMarkConvertedButton;
    @FindBy(xpath = "//a[text()='No']")
    public WebElement button_noMarkConvertedButton;
    @FindBy(xpath = "//div[@class='panel-heading']")
    public WebElement header_patientNote;
    @FindBy(xpath = "//textarea[contains(@class,'form-control patient-note-message message')]")
    public WebElement textarea_patientNoteTextBox;
    @FindBy(xpath = "//input[@value='Save']")
    public WebElement input_savePatientNote;
    @FindBy(xpath = "//div[contains(text(),' No Note Present')]")
    public WebElement text_noNotesPresentInfoText;
    @FindBy(xpath = "//div[@class='col-md-12 row mb5 notes']")
    public WebElement createdPatientNotesRow;
    @FindBy(xpath = "//a[@id='delete']")
    public WebElement button_deletePatientNote;

    @FindBy(xpath = "//div[@class='modal-dialog modal-hg-save-record-template']/div[@class='modal-content']")
    public WebElement template_viewContentTemplate;

    @FindBy(xpath = "//input[@id='invoice_inventory_order_consultant_name']")
    public WebElement input_salesOrderDoctorName;

    @FindBy(xpath = "//button[text()='Mark Patient Not Visited']")
    public WebElement button_markPatientNotVisitedButton;

    @FindBy(xpath="//a[@class='patient-prescription-queue']")
    public WebElement tab_patientQueue;
    @FindBy(xpath="//a[contains(text(),'View Tray')]")
    public WebElement button_viewTray;

}
