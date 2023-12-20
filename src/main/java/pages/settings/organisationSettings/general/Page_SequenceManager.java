package pages.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_SequenceManager extends TestBase {

    @SuppressWarnings("unused")
    private WebDriver driver;
    public Page_SequenceManager(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//select[@id='department-filter']")
    public WebElement select_departmentFilter;
    @FindBy(xpath = "//select[@id='counter-filter']")
    public WebElement select_counterFilter;
    @FindBy(xpath = "//select[@id='department-filter']/option")
    public List<WebElement> select_departmentFilterOption;
    @FindBy(xpath = "//select[@id='counter-filter']/option")
    public List<WebElement>  select_counterFilterOption;
    @FindBy(xpath = "//input[@id='searchInput']")
    public WebElement input_searchModuleInputBox;
    @FindBy(xpath = "//label[text()='Department']")
    public WebElement label_departmentLabel;
    @FindBy(xpath = "//label[text()='Counter level']")
    public WebElement label_counterLabel;
    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']/thead/tr/th")
    public List<WebElement> list_sequenceTableHeaderList;
    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']/tbody/tr/td[1]")
    public List<WebElement> list_sequenceTableModuleDataList;
    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']/tbody/tr/td[2]")
    public List<WebElement> list_sequenceTableModuleFieldDataList;
    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']/tbody/tr/td[3]")
    public List<WebElement> list_sequenceTableDepartmentList;
    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']/tbody/tr/td[6]")
    public List<WebElement> list_sequenceTablePropertiesList;
    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']/tbody/tr/td[7]")
    public List<WebElement> list_sequenceTableActionList;
    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']/tbody/tr/td[7]//div[text()='Disable']")
    public List<WebElement> list_sequenceTableDisableActionList;


    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']/tbody/tr/td[4]")
    public List<WebElement> list_sequenceTableFormateList;
    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']/tbody/tr/td[5]/input")
    public List<WebElement> list_sequenceTableDefaultList;
    @FindBy(xpath = "//table[@id='manage_inventory_stores_table']/tbody/tr")
    public List<WebElement> list_sequenceTableRawDataList;
    @FindBy(xpath = "//div[@id='sequence-manager-modal']//h4[@class='modal-title']")
    public WebElement header_editSequenceManagerHeading;
    @FindBy(xpath = "//strong[text()='Counter Level: ']")
    public WebElement strong_counterLevelText;
    @FindBy(xpath = "//strong[text()='Module Name: ']")
    public WebElement strong_moduleNameText;
    @FindBy(xpath = "//strong[text()='Prefix Level: ']")
    public WebElement strong_prefixLevelText;
    @FindBy(xpath = "//strong[text()='Counter Length: ']")
    public WebElement strong_counterLengthText;
    @FindBy(xpath = "//strong[text()='Department: ']")
    public WebElement strong_departmentText;
    @FindBy(xpath = "//select[@id='sequence_manager_counter_level']")
    public WebElement select_counterLevelDropdownInEditSequence;
    @FindBy(xpath = "//select[@id='sequence_manager_module_name']")
    public WebElement select_moduleNameDropdownInEditSequence;
    @FindBy(xpath = "//select[@id='sequence_manager_prefix_level']")
    public WebElement select_prefixLevelDropdownInEditSequence;
    @FindBy(xpath = "//select[@id='sequence_manager_department_id']")
    public WebElement select_departmentDropdownInEditSequence;
    @FindBy(xpath = "//input[@id='sequence_manager_counter_length']")
    public WebElement input_counterLengthBoxInEditSequence;
    @FindBy(xpath = "//div[@target-input-id='sequence_manager_display_entities']/button")
    public List<WebElement> list_displayEntitiesListInEditSequence;
    @FindBy(xpath = "//select[@id='separators']")
    public WebElement select_addSeparators;
    @FindBy(xpath = "//div[contains(@class,'display_field_details')]//div[@class='title']//strong")
    public List<WebElement> list_sequenceCreationTableHeaderListInEditSequence;
    @FindBy(xpath = "//div[@id='display_entities_fields_add']/div")
    public List<WebElement> list_sequenceCreationTableDataListInEditSequence;
    @FindBy(xpath = "//button[text()='Preview']")
    public WebElement button_previewButton;
    @FindBy(xpath = "//button[@class='btn btn-success btn-submit']")
    public WebElement button_createButton;
    @FindBy(xpath = "//a[@title='Create Sequence']")
    public WebElement button_addSequenceButton;
    @FindBy(xpath = "//h4[text()='MANAGE SEQUENCES']")
    public WebElement header_manageSequenceHeader;
    @FindBy(xpath = "//input[@id='sequence_manager_module_prefix']")
    public WebElement input_modulePrefix;
    @FindBy(xpath = "//select[@id='sequence_manager_date_format']")
    public WebElement select_dateFormat;
    @FindBy(xpath = "//select[@id='sequence_manager_year_format']")
    public WebElement select_yearFormat;
    @FindBy(xpath = "//div[@class='row mb20 div_preview well']")
    public WebElement text_previewSequenceText;
    @FindBy(xpath = "//input[@id='sequence_manager_organisation_abbreviation']")
    public WebElement input_organisationCodeAbbreviation;
    @FindBy(xpath = "//input[@id='sequence_manager_facility_abbreviation']")
    public WebElement input_facilityCodeAbbreviation;
    @FindBy(xpath = "//input[@id='sequence_manager_region_abbreviation']")
    public WebElement input_regionCodeAbbreviation;
    @FindBy(xpath = "//input[@id='sequence_manager_original_counter']")
    public WebElement input_counterCodeAbbreviation;

    @FindBy(xpath = "//div[@id='display_entities_fields_add']//div[@class='title']")
    public List<WebElement> list_sequenceCreationTableNameDataListInEditSequence;
    @FindBy(xpath = "//div[@id='confirmation-modal']//a[text()='Make Primary']")
    public WebElement button_makePrimaryButton;
    @FindBy(xpath = "//label[@id='sequence_manager_original_counter-error']")
    public WebElement text_originalCounter;
    @FindBy(xpath = "//div[@id='display_entities_fields_add']/div/div[2]")
    public List<WebElement> text_sequenceFormat;
    @FindBy(xpath = "//button[contains(@id,'counter')]")
    public WebElement button_counterAddSequence;
    @FindBy(xpath = "//button[text()='Update']")
    public WebElement button_updateEditSequence;




}
