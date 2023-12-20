package pages.settings.organisationSettings.inventoryAndSupplyChain;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_SubCategoryMaster extends TestBase{
    @SuppressWarnings("unused")
    private WebDriver driver;

    public Page_SubCategoryMaster(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
    @FindBy(xpath = "//h4[text()='MANAGE INVENTORY SUB CATEGORY']")
    public WebElement header_subCategoryMasterTitle;

    @FindBy(xpath = "//input[@placeholder='Search Sub Category']")
    public WebElement input_searchSubCategory;

    @FindBy(xpath = "//a[text()=' Add Sub Category']")
    public WebElement button_addSubCategory;

    @FindBy(xpath = "//h4[text()='Add Sub Category']")
    public WebElement header_addSubCategoryTemplateHeader;

    @FindBy(xpath = "//strong[text()='Sub Category Information']")
    public WebElement label_subCategoryInformationLabel;

    @FindBy(xpath = "//strong[text()='Category']")
    public WebElement label_categoryLabelInTemplate;

    @FindBy(xpath = "//strong[text()='Name']")
    public WebElement label_nameLabelInTemplate;

    @FindBy(xpath = "//strong[text()='Description']")
    public WebElement label_descriptionLabelInTemplate;

    @FindBy(xpath = "//select[@id='inventory_sub_category_category_id']")
    public WebElement select_categoryDropdownInTemplate;

    @FindBy(xpath = "//select[@id='inventory_sub_category_category_id']/option")
    public List<WebElement> list_categoryListInTemplate;

    @FindBy(xpath = "//input[@id='inventory_sub_category_name']")
    public WebElement input_nameInAddSubCategory;

    @FindBy(xpath = "//textarea[@id='inventory_sub_category_description']")
    public WebElement textarea_descriptionInAddSubCategory;

    @FindBy(xpath = "//label[@id='inventory_sub_category_name-error']")
    public WebElement label_requiredErrorForNameField;

    @FindBy(xpath = "//label[@id='inventory_sub_category_category_id-error']")
    public WebElement label_requiredErrorForCategoryField;

    @FindBy(xpath = "//tbody[@id='sub_category_master-list']/tr")
    public List<WebElement> list_subCategoryRowsInSubCategoryList;

    @FindBy(xpath = "//tbody[@id='sub_category_master-list']/tr/td[1]")
    public List<WebElement> list_subCategoryNameInSubCategoryMaster;

    @FindBy(xpath = "//tbody[@id='sub_category_master-list']/tr/td[4]")
    public List<WebElement> list_subCategoryActionsForSubCategoryName;

}
