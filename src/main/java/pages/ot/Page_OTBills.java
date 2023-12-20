package pages.ot;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_OTBills
{
	@SuppressWarnings("unused")
	private WebDriver driver;
	public Page_OTBills(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	
	@FindBy(xpath = "//a[text()=' Schedule OT']")
	public WebElement button_Scheduled_Ot;
	
	@FindBy(xpath = "//div[contains(@id,'all_ot_list')]//div[@class='overflow patient-name']")
	public List<WebElement> rows_patientNamesOnOT;
	
	@FindBy(xpath = "//button[contains(text(),'₹ Bills')]")
	public WebElement button_Bills;
	
	@FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a[@data-toggle='modal']")
	public List<WebElement> rows_AllBills;
	
	@FindBy(xpath = "//*[@id='ipd-add-services']/i")
	public WebElement button_clickPlusButton;
	
	@FindBy(xpath = "//textarea[@id='invoice_ipd_patient_comment']")
	public WebElement input_billRemarksComment;
	
	@FindBy(xpath = "//textarea[@id='invoice_ipd_comments']")
	public WebElement input_billInternalComment;
	
	@FindBy(xpath = "//textarea[@id='invoice_ipd_additional_discount_comment']")
	public WebElement input_billDiscountComment;
	
	@FindBy(xpath = "//button[text()='Mark As Done']")
	public WebElement button_markAsDone;
	
	@FindBy(xpath = "//a[text()=' Send to Ward ']")
	public WebElement button_sendToWard;
	
	
}
