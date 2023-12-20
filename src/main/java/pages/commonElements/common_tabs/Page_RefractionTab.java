package pages.commonElements.common_tabs;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_RefractionTab extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_RefractionTab(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindAll
			({
					@FindBy(xpath = "//li[@id='refraction_step']"),
					@FindBy(xpath = "//a[@href='#refraction']"),
			})
	public WebElement tab_RefractionTab;

	// UCVA Absent/unaided under visual acuity R/OD
	@FindBy(xpath = "//div[@target-input-id='opdrecord_r_visualacuity_unaided']/button[@name='r_visualacuity_unaided']")
	public List<WebElement> buttons_ucvaAbsentUnderVisualAcuityRight;

	// UCVA Absent/unaided under visual acuity L/OD
	@FindBy(xpath = "//div[@target-input-id='opdrecord_l_visualacuity_unaided']/button[@name='l_visualacuity_unaided']")
	public List<WebElement> buttons_ucvaAbsentUnderVisualAcuityLeft;

	// comments under ucva R/OD
	@FindBy(xpath = "//input[@id='opdrecord_r_visualacuity_unaided_comments']")
	public WebElement input_ucvaCommentUnderVisualAcuityRightSide;

	// comments under ucva R/OD
	@FindBy(xpath = "//input[@id='opdrecord_l_visualacuity_unaided_comments']")
	public WebElement input_ucvaCommentUnderVisualAcuityLeftSide;

	// comments under visual acuity R/OD
	@FindBy(xpath = "//textarea[@id='opdrecord_r_visualacuity_comments']")
	public WebElement input_CommentUnderVisualAcuityRightSide;

	// comments under visual acuity L/OD
	@FindBy(xpath = "//textarea[@id='opdrecord_l_visualacuity_comments']")
	public WebElement input_CommentUnderVisualAcuityLefttSide;

	// fill interocular pressure in template
	@FindBy(xpath = "//input[@id='opdrecord_r_intraocularpressure']")
	public WebElement input_rightIop;

	@FindBy(xpath = "//input[@id='opdrecord_l_intraocularpressure']")
	public WebElement input_leftIop;

	@FindBy(xpath = "//li[@class='refraction_step_iop btn-pointer my-danger my-error my-error-dark']")
	public WebElement tab_RedColourInIopTab;

	@FindBy(xpath = "//li[@id='iop_is_valid']")
	public WebElement status_iopBadge;

	@FindBy(xpath = "//li[@class='refraction_step_iop btn-pointer my-success my-success-dark']")
	public WebElement tab_GreenColourInIopTab;

	@FindBy(xpath = "//li[@class='refraction_step_vision btn-pointer my-danger my-error my-error-dark']")
	public WebElement tab_RedColourInVisionTab;

	@FindBy(xpath = "//li[@class='refraction_step_vision btn-pointer my-success my-success-dark']")
	public WebElement tab_GreenColourInVisioTab;
	// Glass prescription

	@FindBy(xpath = "//select[@id='opdrecord_r_acceptance_typeoflens']/option")
	public List<WebElement> listbutton_ToSelectOfTypeOfLens;

	@FindBy(xpath = "//select[@id='opdrecord_r_acceptance_lensmaterial']/option")
	public List<WebElement> listbutton_ToSelectOfLensMaterial;

	@FindBy(xpath = "//select[@id='opdrecord_r_acceptance_lenstint']/option")
	public List<WebElement> listbutton_ToSelectOfLensTint;

	@FindBy(xpath = "//input[@id='opdrecord_r_acceptance_ipd']")
	public WebElement input_ToFillIpdValues;

	@FindBy(xpath = "//select[@id='opdrecord_r_acceptance_framematerial']/option")
	public List<WebElement> listbutton_ToSelectOfFrameMaterial;
	@FindBy(xpath = "//input[@id='opdrecord_r_glassesprescriptions_distant_sph']")
	public WebElement input_rightGlassDistantSph;
	@FindBy(xpath = "//input[@id='opdrecord_r_glassesprescriptions_distant_cyl']")
	public WebElement input_rightGlassDistantCyl;
	@FindBy(xpath = "//input[@id='opdrecord_l_glassesprescriptions_distant_sph']")
	public WebElement input_leftGlassDistantSph;
	@FindBy(xpath = "//input[@id='opdrecord_l_glassesprescriptions_distant_cyl']")
	public WebElement input_leftGlassDistantCyl;
	@FindBy(xpath = "//input[@id='opdrecord_r_pgp_distant_sph']")
	public WebElement input_rightPgpDistantSph;
	@FindBy(xpath = "//input[@id='opdrecord_r_pgp_distant_cyl']")
	public WebElement input_rightPgpDistantCyl;
	@FindBy(xpath = "//input[@id='opdrecord_l_pgp_distant_sph']")
	public WebElement input_leftPgpDistantSph;
	@FindBy(xpath = "//input[@id='opdrecord_l_pgp_distant_cyl']")
	public WebElement input_leftPgpDistantCyl;
	@FindBy(xpath = "//div[@id='l-questionsforpgp']//button[contains(@class,'editViewActivate_l')]")
	public WebElement button_editPgpData;

	@FindBy(xpath = "//div[contains(@class,'answersforglassesprescriptions mainview')]//button[contains(@class,'editViewActivate_l')]")
	public WebElement button_editGlassPrescriptionData;
	@FindBy(xpath = "//span[@id='fill-glasses-prescription']//i")
	public WebElement btn_fillGlassPrescription;
	@FindBy(xpath = "//div[@title='Copy from Right to Left']")
	public WebElement btn_rightEqualLeftFillRefraction;

	@FindBy(xpath = "//button[@id='fill-refraction-modal']")
	public WebElement btn_okFillRefraction;
	@FindBy(xpath = "//div[@id='r-questionsforacceptance']//i[@title='Copy Left to Right']")
	public WebElement btn_leftToRightCopyGlassPrescription;
	@FindBy(xpath = "//select[@id='opdrecord_r_acceptance_typeoflens']")
	public WebElement select_rightSideOfLensGlassPrescription;
	@FindBy(xpath = "//select[@id='opdrecord_l_acceptance_typeoflens']")
	public WebElement select_leftSideOfLensGlassPrescription;
	@FindBy(xpath = "//input[@id='opdrecord_r_acceptance_ipd']")
	public WebElement select_rightSideOfIPDGlassPrescription;
	@FindBy(xpath = "//input[@id='opdrecord_r_acceptance_size']")
	public WebElement select_rightSideOfSizeGlassPrescription;
	@FindBy(xpath = "//select[@id='opdrecord_r_acceptance_lensmaterial']")
	public WebElement select_rightSideLensOfMaterialGlassPrescription;
	@FindBy(xpath = "//select[@id='opdrecord_r_acceptance_lenstint']")
	public WebElement select_rightSideLensTintGlassPrescription;
	@FindBy(xpath = "//select[@id='opdrecord_r_acceptance_framematerial']")
	public WebElement select_rightSideFrameMaterialGlassPrescription;
	@FindBy(xpath = "//input[@id='opdrecord_r_acceptance_dia']")
	public WebElement input_rightSideDiaGlassPrescription;
	@FindBy(xpath = "//input[@id='opdrecord_r_acceptance_prismbase']")
	public WebElement input_rightSidePrismBaseGlassPrescription;
	@FindBy(xpath = "//input[@id='opdrecord_r_acceptance_fittingheight']")
	public WebElement input_rightSideFittingHeightGlassPrescription;
	@FindBy(xpath = "//textarea[@id='opdrecord_r_acceptance_comments']")
	public WebElement textarea_rightSideAdviceCommentGlassPrescription;
	@FindBy(xpath = "//button[@name='refraction_distant_sph']")
	public List<WebElement> list_btnSphDistant;
	@FindBy(xpath = "//button[@name='refraction_add_sph']")
	public List<WebElement> list_btnSphAdd;
	@FindBy(xpath = "//div[@target-input-id='refraction_refraction_add_sph']//button[text()='+0.75']")
	public WebElement btn_0_75_SphAdd;
	@FindBy(xpath = "//button[@name='refraction_distant_cyl']")
	public List<WebElement> list_btnCylDistant;
	@FindBy(xpath = "//button[@name='refraction_distant_axis']")
	public List<WebElement> list_btnAxisDistant;
	@FindBy(xpath = "//button[@name='refraction_distant_vision']")
	public List<WebElement> list_btnVisionDistant;
	@FindBy(xpath = "//button[@name='refraction_near_vision']")
	public List<WebElement> list_btnVisionNear;
	@FindBy(xpath = "//*[@name='plus_minus'][@input-value='minus']")
	public WebElement btn_minus;
	@FindBy(xpath = "//*[@name='plus_minus'][@input-value='plus']")
	public WebElement btn_plus;
	@FindBy(xpath = "//input[contains(@id,'va_advised')]")
	public List<WebElement> list_checkboxVaNotExamined;
	@FindBy(xpath = "//input[contains(@id,'iop_advised')]")
	public List<WebElement> list_checkboxIopNotExamined;
	@FindBy(xpath = "//input[@id='r_refraction_near_sph']")
	public WebElement input_nearSph;
	@FindBy(xpath = "//input[@id='r_refraction_near_cyl']")
	public WebElement input_nearCyl;
	@FindBy(xpath = "//input[@id='r_refraction_near_axis']")
	public WebElement input_nearAxis;

	@FindBy(xpath = "//button[text()=' Edit Refraction ']")
	public WebElement button_editRefractionButton;

	@FindBy(xpath = "//button[contains(@class,'visualacuity')]")
	public List<WebElement> list_allBtnVisualAcuity;
	@FindBy(xpath = "//*[contains(@class,'form-control-comment')]")
	public List<WebElement> list_inputAllCommentsVisualAcuity;
	@FindBy(xpath = "//small[contains(@id,'visualacuity')]")
	public List<WebElement> list_btnClearVisualAcuity;
	@FindBy(xpath="//select[contains(@id,'visualacuity')]")
	public List<WebElement> list_selectPRvisualAcuity;
	@FindBy(xpath="//input[@id='opdrecord_no_right_va_advised']")
	public WebElement checkbox_rightVA_notExamined;
	@FindBy(xpath="//input[@id='opdrecord_no_left_va_advised']")
	public WebElement checkbox_leftVA_notExamined;
	@FindBy(xpath="//i[contains(@class,'rvacopybutton')]")
	public WebElement btn_copyRightToLeft_visualAcuity;
	@FindBy(xpath="//i[contains(@class,'lvacopybutton')]")
	public WebElement btn_copyLeftToRight_visualAcuity;
	@FindAll
			({
					@FindBy(xpath = "//input[contains(@id,'intraocularpressure_comments')]"),
					@FindBy(xpath = "//input[contains(@id,'iop_comments')]"),
			})
	public List<WebElement> list_inputCommentsIOP;

	@FindBy(xpath = "//div[contains(@class,'text')]//small[contains(@title,'IOP')][normalize-space()='clear']")
	public List<WebElement> list_btnClearIOP;

	@FindBy(xpath = "//input[@id='opdrecord_no_right_iop_advised']")
	public WebElement checkbox_rightIOP_notExamined;

	@FindBy(xpath = "//input[@id='opdrecord_no_left_iop_advised']")
	public WebElement checkbox_leftIOP_notExamined;

	@FindBy(xpath = "//div[contains(@class,'intraocularpressure')]//i[contains(@class,'rcopybutton')]")
	public List<WebElement> list_btnRightToLeftCopyIOP;

	@FindBy(xpath = "//input[contains(@id,'autorefraction')]")
	public List<WebElement> list_inputAutoRefraction;
	@FindBy(xpath = "//a[contains(@href,'auto_refraction')]/i")
	public WebElement btn_fillAutoRefraction;
	@FindBy(xpath = "//a[contains(@href,'dry_refraction')]/i")
	public WebElement btn_fillDryRefraction;
	@FindBy(xpath = "//a[contains(@href,'dilated_refraction')]/i")
	public WebElement btn_fillDilatedRefraction;
	@FindBy(xpath = "//a[contains(@href,'pgp')]/i")
	public WebElement btn_fillPGP;

	@FindBy(xpath = "//i[@class='fa fa-arrow-circle-right fill_inter_glass_btn']")
	public WebElement btn_fillIntermediateGlassPrescription;
	@FindBy(xpath = "//span[@id='fill-pmt']//i")
	public WebElement btn_fillPMT;
	@FindBy(xpath = "//span[@title='Add PGP']//i[normalize-space()='PGP']")
	public List<WebElement> list_btnAddPgp;

	@FindBy(xpath = "//input[contains(@id,'l_refraction')][contains(@class,'fill')]")
	public List<WebElement> list_inputLeftTableValuesFillRefraction;
	@FindBy(xpath = "//input[contains(@id,'r_refraction')][contains(@class,'fill')]")
	public List<WebElement> list_inputRightTableValuesFillRefraction;

	@FindBy(xpath = "//span[@title='Copy Dry Refraction to Glass Prescription']//i[normalize-space()='copy']")
	public WebElement btn_copyDryRefractionToGlassPrescription;
	@FindBy(xpath = "//span[@title='Copy PMT to Glass Prescription']//i[normalize-space()='copy']")
	public WebElement btn_copyPMTtoGlassPrescription;
	@FindBy(xpath = "//span[@title='Copy PGP to Glass Prescription']//i[normalize-space()='copy']")
	public WebElement btn_copyPGPtoGlassPrescription;
	@FindBy(xpath = "//span[@title='Copy Dilated Refraction to Glass Prescription']//i[normalize-space()='copy']")
	public WebElement btn_copyDilatedRefractionToGlassPrescription;
	@FindBy(xpath = "//input[contains(@id,'glassesprescriptions')][@type='text']")
	public List<WebElement> list_inputGlassPrescriptions;
	@FindBy(xpath = "//textarea[contains(@id,'_refraction_comments')]")
	public List<WebElement> list_inputDryRefractionComments;
	@FindBy(xpath = "//textarea[contains(@id,'dilatedrefraction_comments')]")
	public List<WebElement> list_inputDilatedRefractionComments;
	@FindBy(xpath = "//textarea[contains(@id,'pgp_comments')]")
	public List<WebElement> list_inputPGP_RefractionComments;
	@FindBy(xpath = "//select[contains(@id,'dilatedrefraction_drug_used')]")
	public List<WebElement> list_selectDrugDilatedRefraction;
	@FindBy(xpath = "//div[contains(@class,'pgp3')]//strong[1]")
	public WebElement text_PGP3;
	@FindBy(xpath="//span[@title='Remove PGP']/i")
	public List<WebElement> list_BtnRemovePGP;
	@FindBy(xpath = "//select[contains(@id,'pgp_typeoflens')]")
	public List<WebElement> list_selectTypeOfLensPGP;
	@FindBy(xpath="//button[@id='advise_glasses_advise']")
	public WebElement btn_adviseGlassPrescriptions;
	@FindBy(xpath="//button[@id='intermediate_advise_glasses_advise']")
	public WebElement btn_adviseIntermediateGlassPrescriptions;
	@FindBy(xpath = "//input[@id='glassesprescriptions_show_add']")
	public WebElement checkbox_showAddInPrintGlassPrescription;
	@FindBy(xpath = "//strong[text()='GLASSES']/following-sibling::i[@title='Copy Left to Right']")
	public WebElement btn_copyRightToLeft_GlassPrescription;
	@FindBy(xpath = "//strong[text()='INTERMEDIATE GLASSES']/following-sibling::i[contains(@class,'rcopybutton')]")
	public WebElement btn_copyRightToLeft_IntermediateGlassPrescription;
	@FindBy(xpath="//div[@id='r-questionsforretinoscopy']//i[@title='Copy Left to Right']")
	public WebElement btn_CopyRightToLeft_Retinoscopy;
	@FindBy(xpath = "//input[contains(@id,'keratometry_average')]")
	public List<WebElement> list_inputAverageKeratometry;
	@FindBy(xpath = "//input[contains(@id,'keratometry_cyl')]")
	public List<WebElement> list_inputCylKeratometry;
	@FindBy(xpath = "//div[@id='r-questionsforkeratometry']//i[@title='Copy Left to Right']")
	public WebElement btn_copyRightToLeftKeratometry;
	@FindBy(xpath="//button[@name='r_amsler']")
	public List<WebElement> list_btnRightAmsler;
	@FindBy(xpath="//button[@name='l_amsler']")
	public List<WebElement> list_btnLeftAmsler;
	@FindBy(xpath="//div[contains(@class,'r-contactlens')]//th")
	public List<WebElement> list_inputTableHeader_ContactLensPrescription;
	@FindBy(xpath = "//strong[text()='CONTACT LENS']/following-sibling::i[@title='Copy Left to Right']")
	public WebElement btn_copyRightToLeft_contactLensPrescription;
	@FindBy(xpath = "//strong[text()='COLOR']/following-sibling::i[@title='Copy Left to Right']")
	public WebElement btn_copyRightToLeft_colorVision;
	@FindBy(xpath = "//textarea[contains(@id,'orthoptics')]")
	public List<WebElement> list_commentOrthoptics;
	@FindBy(xpath = "//select[@id='opdrecord_view_optical_store_id']")
	public WebElement select_Store;
	@FindBy(xpath = "//button[contains(@class,'_contrastsensitivity')]")
	public List<WebElement> list_allBtnContrastSensitivity;
	@FindBy(xpath = "//*[contains(@id,'contrastsensitivity_comments')]")
	public List<WebElement> list_commentContrastSensitivity;
	@FindBy(xpath="//div[contains(@class,'answersforkeratometry')]//input")
	public List<WebElement>list_inputOccularAssessment;
	@FindBy(xpath="//div[contains(@class,'answersfortriallensdetails')]//input")
	public List<WebElement> list_inputTrailLensDetails;
	@FindBy(xpath="//div[contains(@class,'answersfortriallensdetails')]//input")
	public List<WebElement> list_inputFitAssessment;
	@FindBy(xpath="//textarea[contains(@id,'fit_comments')]")
	public List<WebElement> list_inputFitAssessmentComments;
	@FindBy(xpath="//div[contains(@class,'answersfordilatedrefraction')]//input")
	public List<WebElement> list_inputOverReactionAcceptance;
	@FindBy(xpath="//textarea[contains(@id,'over_reaction_comments')]")
	public List<WebElement> list_inputOverReactionAcceptanceComments;
	@FindBy(xpath="//div[contains(@class,'answersforvirtual')]//input")
	public List<WebElement> list_inputVirtualNeeds;
	@FindBy(xpath="//div[contains(@class,'answersfordistance')]//input[@type='text']")
	public List<WebElement> list_inputDistance;
	@FindBy(xpath="//div[contains(@class,'answersforautorefraction')]//input")
	public List<WebElement> list_inputFinalRecommendations;
	@FindBy(xpath="//div[contains(@class,'answersfordistance')]//button")
	public List<WebElement> list_btn_LVA_problems;
	@FindBy(xpath = "//input[contains(@id,'visualacuity')]")
	public List<WebElement> list_inputVisualAcuity_FreeForm;
	@FindBy(xpath = "//input[contains(@id,'visualacuity_unaided')]")
	public List<WebElement> list_inputVisualAcuity_unaided_FreeForm;
	@FindBy(xpath = "//input[contains(@id,'visualacuity_ua_near')]")
	public List<WebElement> list_inputVisualAcuity_unaided_near_FreeForm;
	@FindBy(xpath = "//div[text()='PR:']/parent::div//input[contains(@id,'visualacuity_ua')]")
	public List<WebElement> list_inputVisualAcuity_unaided_pr_FreeForm;
	@FindBy(xpath = "//input[contains(@id,'visualacuity_pinhole')]")
	public List<WebElement> list_inputVisualAcuity_pinhole_FreeForm;
	@FindBy(xpath = "//input[contains(@id,'visualacuity_glasses')]")
	public List<WebElement> list_inputVisualAcuity_glasses_FreeForm;
	@FindBy(xpath = "//input[contains(@id,'visualacuity_near')]")
	public List<WebElement> list_inputVisualAcuity_glassesNear_FreeForm;
	@FindBy(xpath = "//input[contains(@id,'visualacuity_lens')]")
	public List<WebElement> list_inputVisualAcuity_contactLens_FreeForm;
	@FindBy(xpath = "//div[contains(@class,'visualacuity-row')]//small[text()='clear']")
	public List<WebElement> list_buttonVisualAcuity_clear_FreeForm;
	@FindBy(xpath = "//textarea[contains(@id,'visualacuity_comments')]")
	public List<WebElement> list_inputComments_VisualAcuity_FreeForm;
	@FindBy(xpath = "//i[contains(@class,'show_or_hide_refraction_field')]")
	public List<WebElement> list_showOrHideRefractionField_FreeForm;
	@FindBy(xpath = "//i[contains(@class,'addiopfieldsbutton')]")
	public List<WebElement> list_buttonAddIOP;
	@FindBy(xpath = "//i[contains(@class,'removeiopfieldsbutton')]")
	public List<WebElement> list_buttonRemoveIOP;
	public List<WebElement> getBtnsVisualAcuity(String side){
		return driver.findElements(By.xpath("//button[contains(@class,'"+leftOrRight(side)+"_visualacuity')]"));
	}
	public List<WebElement> getInputCommentsVisualAcuity(String side){
		return driver.findElements(By.xpath("//*[contains(@id,'opdrecord_"+leftOrRight(side)+"_visualacuity') and contains(@id,'comments')]"));
	}
	public List<WebElement> getBtnsClearVisualAcuity(String side){
		return driver.findElements(By.xpath("//small[contains(@id,'clear_"+leftOrRight(side)+"_visualacuity')]"));
	}
	public WebElement slider_iop(String side){
		return driver.findElement(By.xpath("//div[@id='"+leftOrRight(side)+"-intraocularpressure-slider-range']/span"));
	}
	public List<WebElement> inputTimeIOP(String side){
		return driver.findElements(By.xpath("//div[contains(@class,'"+leftOrRight(side)+"-intraocularpressure')]//input[contains(@id,'opdrecord_"+leftOrRight(side)+"_intraocularpressure_time')]"));
	}
	public List<WebElement> selectIOP_method(String side){
		return driver.findElements(By.xpath("//div[contains(@class,'"+leftOrRight(side)+"-intraocularpressure')]//select[contains(@name,'"+leftOrRight(side)+"_iop_method')]"));
	}
	public List<WebElement> list_inputIOP(String side){
		return driver.findElements(By.xpath("//div[contains(@class,'"+leftOrRight(side)+"-intraocularpressure')]//input[contains(@id,'"+leftOrRight(side)+"_intraocularpressure')][@size='4']"));
	}
	public List<WebElement> getListInputTable(String tableName){
		tableName=tableName.trim().toLowerCase().replaceAll(" ","-");
		return driver.findElements(By.xpath("//input[contains(@class,'"+tableName+"')]"));
	}
	public WebElement select_typeOfLensGlassPrescription(String side){
		return driver.findElement(By.xpath("//select[@id='opdrecord_"+leftOrRight(side)+"_acceptance_typeoflens']"));
	}
	public WebElement input_IPD_GlassPrescription(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_acceptance_ipd']"));
	}
	public WebElement input_SizeGlassPrescription(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_acceptance_size']"));
	}
	public WebElement select_lensMaterialGlassPrescription(String side){
		return driver.findElement(By.xpath("//select[@id='opdrecord_"+leftOrRight(side)+"_acceptance_lensmaterial']"));
	}
	public WebElement select_lensTintGlassPrescription(String side){
		return driver.findElement(By.xpath("//select[@id='opdrecord_"+leftOrRight(side)+"_acceptance_lenstint']"));
	}
	public WebElement select_frameMaterialGlassPrescription(String side){
		return driver.findElement(By.xpath("//select[@id='opdrecord_"+leftOrRight(side)+"_acceptance_framematerial']"));
	}
	public WebElement input_diaGlassPrescription(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_acceptance_dia']"));
	}
	public WebElement input_prismBaseGlassPrescription(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_acceptance_prismbase']"));
	}
	public WebElement input_fittingHeight(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_acceptance_fittingheight']"));
	}
	public WebElement input_adviceCommentGlassPrescription(String side){
		return driver.findElement(By.xpath("//textarea[@id='opdrecord_"+leftOrRight(side)+"_acceptance_comments']"));
	}
	public WebElement select_typeOfLensIntermediateGlassPrescription(String side){
		return driver.findElement(By.xpath("//select[@id='opdrecord_"+leftOrRight(side)+"_intermediate_acceptance_typeoflens']"));
	}
	public WebElement input_IPD_IntermediateGlassPrescription(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_intermediate_acceptance_ipd']"));
	}
	public WebElement select_lensMaterialIntermediateGlassPrescription(String side){
		return driver.findElement(By.xpath("//select[@id='opdrecord_"+leftOrRight(side)+"_intermediate_acceptance_lensmaterial']"));
	}
	public WebElement select_lensTintIntermediateGlassPrescription(String side){
		return driver.findElement(By.xpath("//select[@id='opdrecord_"+leftOrRight(side)+"_intermediate_acceptance_lenstint']"));
	}
	public WebElement select_frameMaterialIntermediateGlassPrescription(String side){
		return driver.findElement(By.xpath("//select[@id='opdrecord_"+leftOrRight(side)+"_intermediate_acceptance_framematerial']"));
	}
	public WebElement input_adviceCommentIntermediateGlassPrescription(String side){
		return driver.findElement(By.xpath("//textarea[@id='opdrecord_"+leftOrRight(side)+"_intermediate_acceptance_comments']"));
	}
	public WebElement input_retinoscopyTop(String side){
		return driver.findElement(By.xpath("//input[contains(@id,'opdrecord_"+leftOrRight(side)+"_dilated_retinoscopy_top')]"));
	}
	public WebElement input_retinoscopyBottom(String side){
		return driver.findElement(By.xpath("//input[contains(@id,'opdrecord_"+leftOrRight(side)+"_dilated_retinoscopy_bottom')]"));
	}
	public WebElement input_retinoscopyRight(String side){
		return driver.findElement(By.xpath("//input[contains(@id,'opdrecord_"+leftOrRight(side)+"_dilated_retinoscopy_right')]"));
	}
	public WebElement input_retinoscopyLeft(String side){
		return driver.findElement(By.xpath("//input[contains(@id,'opdrecord_"+leftOrRight(side)+"_dilated_retinoscopy_left')]"));
	}
	public WebElement input_retinoscopyVA(String side){
		return driver.findElement(By.xpath("//input[contains(@id,'opdrecord_"+leftOrRight(side)+"_dilated_retinoscopy_va')]"));
	}
	public WebElement input_retinoscopyHA(String side){
		return driver.findElement(By.xpath("//input[contains(@id,'opdrecord_"+leftOrRight(side)+"_dilated_retinoscopy_ha')]"));
	}
	public WebElement input_retinoscopyDistance(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_dilated_retinoscopy_distance']"));
	}
	public WebElement select_retinoscopyDrugUsed(String side){
		return driver.findElement(By.xpath("//select[@id='opdrecord_"+leftOrRight(side)+"_retinoscopy_drug_used']"));
	}
	public WebElement input_retinoscopyComments(String side){
		return driver.findElement(By.xpath("//textarea[@id='opdrecord_"+leftOrRight(side)+"_retinoscopy_comments']"));
	}
	public WebElement input_keratometryKhValue(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_keratometry_distant_kh']"));
	}
	public WebElement input_keratometryKvValue(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_keratometry_near_kv']"));
	}
	public WebElement input_keratometryKhAxis(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_keratometry_distant_axis']"));
	}
	public WebElement input_keratometryKvAxis(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_keratometry_near_axis']"));
	}
	public WebElement input_commentAmsler(String side){
		return driver.findElement(By.xpath("//textarea[@id='opdrecord_"+leftOrRight(side)+"_amsler_comment']"));
	}
	public List<WebElement> input_contactLens_prescription(String side){
		return driver.findElements(By.xpath("//td//input[contains(@id,'"+leftOrRight(side)+"_contactlens')]"));
	}
	public WebElement input_revisitDate_contactLensPrescriptions(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_revisit_date']"));
	}
	public WebElement input_color_contactLensPrescription(String side){
		return driver.findElement(By.xpath("//input[@id='opdrecord_"+leftOrRight(side)+"_contactlens_color']"));
	}
	public WebElement input_advice_contactLensPrescription(String side){
		return driver.findElement(By.xpath("//*[@id='opdrecord_"+leftOrRight(side)+"_contactlens_comments']"));
	}
	public WebElement select_type_contactLensPrescription(String side){
		return driver.findElement(By.xpath("//select[@id='opdrecord_"+leftOrRight(side)+"_contactlens_types']"));
	}
	public WebElement input_colorVision(String side){
		return driver.findElement(By.xpath("//*[@id='opdrecord_"+leftOrRight(side)+"_color_vision']"));
	}

	private String leftOrRight(String side) {
		side = side.toLowerCase();
		if (side.equals("l") || side.equals("left")) {
			side = "l";
		} else {
			side = "r";
		}
		return side;
	}
}
