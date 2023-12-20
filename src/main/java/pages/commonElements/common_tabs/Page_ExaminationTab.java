package pages.commonElements.common_tabs;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;


public class Page_ExaminationTab extends TestBase {
	@SuppressWarnings("unused")
	private WebDriver driver;

	public Page_ExaminationTab(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	@FindBy(xpath = "//li[@id='history_step']")
	public WebElement tab_HistoryTab;

	@FindBy(xpath = "//button[@id='complaints_blurring_diminution_of_vision']")
	public WebElement button_OneChiefComplaints;

	@FindBy(xpath = "//button[@id='speciality_histories_glaucoma']")
	public WebElement button_OneOphthalmicHistory;

	@FindBy(xpath = "//button[@id='personal_histories_diabetes']")
	public WebElement button_SystemmicHistory;

	@FindBy(xpath = "//button[@id='drug_allergies_antimicrobial_agents']")
	public WebElement button_Allergy;

	@FindBy(xpath = "//li[@class='history_step btn-pointer my-danger my-error my-error-dark']")
	public WebElement tab_RedColourInHistoryTab;

	@FindBy(xpath = "//li[@class='history_step btn-pointer my-success my-success-dark']")
	public WebElement tab_GreenColourInHistoryTab;

	@FindAll
			({
					@FindBy(xpath = "//li[@id='examination_step']"),
					@FindBy(xpath = "//a[@href='#examination']"),
			})
	public WebElement tab_ExaminationTab;

	@FindBy(xpath = "//*[@id='opdrecord_generalexaminationcommoncomments']")
	public WebElement input_commentsExamination;

	@FindBy(xpath = "//button[@name='generalexamination']")
	public List<WebElement> list_BtnGeneralExamination;

	@FindBy(xpath = "//input[@id='opdrecord_generalexaminationcomments']")
	public WebElement input_abnormalComment_GeneralExamination;

	@FindBy(xpath = "//button[contains(@class,'one_eyed')]")
	public List<WebElement> list_buttonOneEyed;

	@FindBy(xpath = "//button[contains(@class,'squint_evaluation')]")
	public List<WebElement> list_buttonSquintEvaluation;

	@FindBy(xpath="//button[contains(@id,'local_examination_normal')]")
	public List<WebElement> list_normalButton;

	@FindBy(xpath="//button[contains(@id,'examination_resetall')]")
	public List<WebElement> list_resetAllButton;

	@FindBy(xpath = "//button[contains(@name,'ocular_trauma_injury')]")
	public List<WebElement> list_btnOcularTrauma;

	@FindBy(xpath = "//textarea[contains(@id,'trauma_comment')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentOcularTrauma;

	@FindBy(xpath = "//textarea[contains(@id,'examination')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public WebElement input_examinationComments_FreeForm;

	@FindBy(xpath = "//button[contains(@class,'appearance')]")
	public List<WebElement> list_btnAppearance;

	@FindBy(xpath = "//textarea[contains(@id,'appearance_comments')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentAppearance;

	@FindBy(xpath = "//button[contains(@id,'appendages_tests')]")
	public List<WebElement> list_btnHeaderAppendages;

	@FindBy(xpath = "//button[contains(@class,'appendages') and contains(@class,'custom-radio-button')]")
	public List<WebElement> list_btnAppendages;

	@FindAll
			({
					@FindBy(xpath = "//textarea[contains(@id,'appendages_comments')]/parent::div/descendant::div[contains(@class,'note-editable')]"),
					@FindBy(xpath = "//input[contains(@id,'appendages_comments')]/parent::div/descendant::div[contains(@class,'note-editable')]"),
			})
	public List<WebElement> list_inputCommentAppendages;

	@FindBy(xpath = "//button[contains(@class,'addappendagesdiagram')]")
	public List<WebElement> list_BtnAppendagesDiagram;

	@FindBy(xpath = "//button[contains(text(),'Save Diagram')]")
	public WebElement btn_saveDiagram;

	@FindBy(xpath = "//button[contains(text(),'Save Diagram')]/following-sibling::button")
	public WebElement btn_cancelDiagram;

	@FindBy(xpath = "//a[contains(text(),'Edit') and contains(@class,'diagram')]")
	public WebElement btn_editDiagram;

	@FindBy(xpath = "//a[contains(text(),'Delete') and contains(@class,'diagram')]")
	public WebElement btn_deleteDiagram;

	@FindBy(xpath = "//div[contains(@class,'diagram')]/descendant::img")
	public WebElement img_diagram;

	@FindBy(xpath = "//button[contains(@class,'conjunctiva') and contains(@class,'custom-radio-button')]")
	public List<WebElement> list_btnConjunctiva;

	@FindBy(xpath = "//textarea[contains(@id,'conjunctiva_comments')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentConjunctiva;

	@FindBy(xpath = "//textarea[contains(@id,'scleracomm')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentSclera;

	@FindBy(xpath = "//button[contains(@class,'cornea') and contains(@class,'custom')]")
	public List<WebElement> list_btnCornea;

	@FindBy(xpath = "//button[contains(@class,'fluorescein_staining') and contains(@class,'custom')]")
	public List<WebElement> list_btnFluorescein_StainingCornea;

	@FindBy(xpath = "//input[contains(@id,'cornea_schirmer')]")
	public List<WebElement> list_inputSchirmerTestCornea;

	@FindBy(xpath = "//input[contains(@id,'cornea_cct_reading')][@type='text']")
	public List<WebElement> list_inputCctReadingCornea;

	@FindBy(xpath = "//span[contains(text(),'CCT')]/parent::strong/following-sibling::button/span")
	public List<WebElement> list_btn_addCCT_TestCornea;

	@FindBy(xpath = "//textarea[contains(@id,'cornea')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentCornea;

	@FindBy(xpath = "//button[contains(@class,'corneadiagram')]")
	public List<WebElement> list_BtnCorneaDiagram;

	@FindBy(xpath = "//button[contains(@class,'corneaslitdiagram')]")
	public List<WebElement> list_BtnCorneaSlitDiagram;

	@FindBy(xpath = "//button[contains(@class,'anteriorchamber') and contains(@class,'custom')]")
	public List<WebElement> list_btnAnteriorChamber;

	@FindBy(xpath = "//textarea[contains(@id,'anteriorchamber')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentAnteriorChamber;

	@FindBy(xpath = "//input[contains(@id,'anteriorchamber') and contains(@id,'comment')]")
	public List<WebElement> list_inputAnteriorChamber;

	@FindBy(xpath = "//button[contains(@class,'pupil') and contains(@class,'custom')]")
	public List<WebElement> list_btnPupil;

	@FindBy(xpath = "//input[contains(@id,'pupilsize')]")
	public List<WebElement> list_inputPupilSize;

	@FindBy(xpath = "//div[contains(@id,'pupilsize-slider-range')]/span")
	public List<WebElement> list_sliderPupilSize;

	@FindBy(xpath = "//textarea[contains(@id,'pupil')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentPupil;

	@FindBy(xpath = "//button[contains(@class,'iris') and contains(@class,'custom')]")
	public List<WebElement> list_btnIris;

	@FindBy(xpath = "//textarea[contains(@id,'iris')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentIris;

	@FindBy(xpath = "//button[contains(@class,'r_lens') or contains(@class,'l_lens')]")
	public List<WebElement> list_btnLens;

	@FindBy(xpath = "//select[contains(@id,'lens_grading')]")
	public List<WebElement> list_select_LOCS_grading_lens;

	@FindBy(xpath = "//textarea[contains(@id,'lens')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentLens;

	@FindBy(xpath = "//button[contains(@class,'lens_diagram')]")
	public List<WebElement> list_BtnLensDiagram;

	@FindBy(xpath = "//button[contains(@class,'binocular') or contains(@class,'uniocular')]")
	public List<WebElement> list_btnUniocularAndBinocular;

	@FindBy(xpath = "//div[contains(@class,'answersforcontactlens')]//input[contains(@id,'contact_lens')]")
	public List<WebElement> list_inputContactLens;

	@FindBy(xpath = "//textarea[contains(@id,'lens')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputExtraocularMovementsAndSquint;

	@FindBy(xpath = "//input[contains(@id,'extraocularmovements_prism')]")
	public List<WebElement> list_inputPrismExtraocularMovements;

	@FindBy(xpath = "//textarea[contains(@id,'extraocular')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentExtraocularMovements;

	@FindBy(xpath = "//button[@name='l_extraocularmovements_squint' or @name='r_extraocularmovements_squint']")
	public List<WebElement> list_btnSquintExtraocularMovements;

	@FindBy(xpath = "//*[contains(@class,'answersforiop')]//input[contains(@id,'r_intraocularpressure') and (@size='4')]")
	public WebElement list_inputRightIOP;

	@FindBy(xpath = "//*[contains(@class,'answersforiop')]//input[contains(@id,'l_intraocularpressure') and (@size='4')]")
	public WebElement list_inputLeftIOP;

	@FindBy(xpath = "//*[contains(@class,'answersforiop')]//input[contains(@id,'r_intraocularpressure') and (@size='8')]")
	public WebElement list_inputRightIOP_time;

	@FindBy(xpath = "//*[contains(@class,'answersforiop')]//input[contains(@id,'l_intraocularpressure') and (@size='8')]")
	public WebElement list_inputLeftIOP_time;

	@FindBy(xpath = "//textarea[contains(@id,'iop')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentIOP;

	@FindBy(xpath = "//select[contains(@id,'gonioscopy')]")
	public List<WebElement> list_selectAll_gonioscopy;

	@FindBy(xpath = "//select[contains(@id,'gonioscopy_eye_superior')]")
	public List<WebElement> list_select_superior_gonioscopy;

	@FindBy(xpath = "//select[contains(@id,'gonioscopy_eye_temporal')]")
	public List<WebElement> list_select_temporal_gonioscopy;

	@FindBy(xpath = "//select[contains(@id,'gonioscopy_eye_nasal')]")
	public List<WebElement> list_select_nasal_gonioscopy;

	@FindBy(xpath = "//select[contains(@id,'gonioscopy_eye_inferior')]")
	public List<WebElement> list_select_inferior_gonioscopy;

	@FindBy(xpath = "//textarea[contains(@id,'gonioscopy')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentGonioscopy;

	@FindBy(xpath = "//select[contains(@id,'fundus_media')]")
	public List<WebElement> list_select_media_fundus;

	@FindBy(xpath = "//select[contains(@id,'fundus_pvd')]")
	public List<WebElement> list_select_PVD_fundus;

	@FindBy(xpath = "//select[contains(@id,'fundus_optdisc')]")
	public List<WebElement> list_select_opticDiscSize_fundus;

	@FindBy(xpath = "//select[contains(@id,'fundus_cup_disc_ratio')]")
	public List<WebElement> list_select_cupDiscRatio_fundus;

	@FindBy(xpath = "//select[contains(@id,'fundus_bloodvessel')]")
	public List<WebElement> list_select_bloodVessels_fundus;

	@FindBy(xpath = "//select[contains(@id,'fundus_vitreous')]")
	public List<WebElement> list_select_vitreous_fundus;

	@FindBy(xpath = "//select[contains(@id,'fundus_retinal_detachment')]")
	public List<WebElement> list_select_retinalDetachment_fundus;

	@FindBy(xpath = "//select[contains(@id,'fundus_peripheral_lesions')]")
	public List<WebElement> list_select_peripheralLesions_fundus;

	@FindBy(xpath = "//input[contains(@id,'fundus_opticdisc_comment')]")
	public List<WebElement> list_input_opticDisc_fundus;

	@FindBy(xpath = "//button[contains(@id,'fundus_macula')]")
	public List<WebElement> list_btn_macula_fundus;

	@FindBy(xpath = "//button[contains(@name,'fundus_macula_fovealreflex')]")
	public List<WebElement> list_btn_fovealReflex_macula_fundus;

	@FindBy(xpath = "//input[contains(@id,'fundustext_comment')]")
	public List<WebElement> list_input_fundusComment_fundus;

	@FindBy(xpath = "//input[contains(@id,'fundus_media_comment')]")
	public List<WebElement> list_input_mediaComment_fundus;

	@FindBy(xpath = "//input[contains(@id,'fundus_cupratio_comment')]")
	public List<WebElement> list_input_cupRatioComment_fundus;

	@FindBy(xpath = "//input[contains(@id,'fundus_bloodvessel_comment')]")
	public List<WebElement> list_input_bloodVesselComment_fundus;

	@FindBy(xpath = "//input[contains(@id,'fundus_macula_comment')]")
	public List<WebElement> list_input_maculaComment_fundus;

	@FindBy(xpath = "//input[contains(@id,'fundus_vitreous_comment')]")
	public List<WebElement> list_input_vitreousComment_fundus;

	@FindBy(xpath = "//input[contains(@id,'fundus_retinal_detachment_comment')]")
	public List<WebElement> list_input_retinalDetachmentComment_fundus;

	@FindBy(xpath = "//input[contains(@id,'fundus_peripheral_lesions_comment')]")
	public List<WebElement> list_input_peripheralLesionsComment_fundus;

	@FindBy(xpath = "//textarea[contains(@id,'fundus')]/parent::div/descendant::div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputCommentFundus;

	@FindBy(xpath = "//button[contains(@class,'fundusdiagram')]")
	public List<WebElement> list_BtnFundusDiagram;

	@FindBy(xpath = "//button[contains(@id,'fundus_normal')]")
	public List<WebElement> list_BtnNormalFundus;

	@FindBy(xpath = "//span[contains(@id,'fill_normal')]")
	public List<WebElement> list_buttonQuickEyeShortcut_Normal;

	@FindBy(xpath = "//div[@class='input']//div[contains(@class,'note-editable')]")
	public List<WebElement> list_inputQuickEyeExamination;

	@FindBy(xpath = "//button[@name='head_posture']")
	public List<WebElement> list_btnHeadPosture_orthoptics;

	@FindBy(xpath = "//button[@name='face_turn']")
	public List<WebElement> list_btnFaceTurn_orthoptics;

	@FindBy(xpath = "//button[@name='head_tilt']")
	public List<WebElement> list_btnHeadTilt_orthoptics;

	@FindBy(xpath = "//button[contains(@name,'hirschberg_test')]")
	public List<WebElement> list_btnHirschbergTest_orthoptics;

	@FindBy(xpath = "//input[contains(@id,'cover_test')][@type='text']")
	public List<WebElement> list_inputCoverTest_orthoptics;

	@FindBy(xpath = "//button[contains(@name,'patterns')]")
	public List<WebElement> list_btnPatterns_orthoptics;

	@FindBy(xpath = "//button[contains(@name,'prism_bar_cover_test')]")
	public List<WebElement> list_btnPrismBarCoverTest_orthoptics;

	@FindBy(xpath = "//button[contains(@name,'prism_bar_reflex_test')]")
	public List<WebElement> list_btnPrismBarReflexTest_orthoptics;

	@FindBy(xpath = "//select[contains(@id,'prism_bar')]")
	public List<WebElement> select_prismBarTest_orthoptics;

	@FindBy(xpath = "//select[contains(@id,'ocular_movement')]")
	public List<WebElement> list_selectOcularMovement_orthoptics;

	@FindBy(xpath = "//input[contains(@id,'nystagmus')]")
	public List<WebElement> list_inputNystagmus_orthoptics;

	@FindBy(xpath = "//input[contains(@id,'maddox_test')]")
	public List<WebElement> list_inputMaddoxTest_orthoptics;

	@FindBy(xpath = "//input[contains(@id,'worth_four_dot_test')]")
	public List<WebElement> list_inputWorthFourDotTest_orthoptics;

	@FindBy(xpath = "//input[contains(@id,'stereopsis')]")
	public List<WebElement> list_inputStereopsis_orthoptics;

	@FindBy(xpath = "//input[contains(@id,'hess_screen_test')]")
	public List<WebElement> list_inputHessScreen_orthoptics;

	public List<WebElement> selectIOP_method(String side){
		return driver.findElements(By.xpath("//div[contains(@class,'"+leftOrRight(side)+"-iop')]//select[contains(@name,'"+leftOrRight(side)+"_iop_method')]"));
	}

	public List<WebElement> list_inputIOP(String side){
		return driver.findElements(By.xpath("//div[contains(@class,'"+leftOrRight(side)+"-iop')]//input[contains(@id,'"+leftOrRight(side)+"_intraocularpressure')][@size='4']"));
	}

	public List<WebElement> button_expandExamination(String expandFieldName){
		expandFieldName=expandFieldName.replaceAll(" ","").toLowerCase();
		return driver.findElements(By.xpath("//div[contains(@id,'questionsfor"+expandFieldName+"')]//descendant::span"));
	}

	public List<WebElement> list_BtnSquint(int index){
		return driver.findElements(By.xpath("//button[@name='"+findRightOrLeftIndex(index)+"_extraocularmovements_squint']"));
	}

	public List<WebElement> list_BtnTropiaSquint(int index){
		return driver.findElements(By.xpath("//button[@name='"+findRightOrLeftIndex(index)+"_extraocularmovements_squint_tropia']"));
	}

	public List<WebElement> list_BtnHorizontalTropiaSquint(int index){
		return driver.findElements(By.xpath("//button[@name='"+findRightOrLeftIndex(index)+"_extraocularmovements_squint_tropia_horizontal']"));
	}

	public List<WebElement> list_BtnEsotropiaHorizontalTropiaSquint(int index){
		return driver.findElements(By.xpath("//button[@name='"+findRightOrLeftIndex(index)+"_extraocularmovements_squint_tropia_horizontal_esotropia']"));
	}

	public List<WebElement> list_BtnExotropiaHorizontalTropiaSquint(int index){
		return driver.findElements(By.xpath("//button[@name='"+findRightOrLeftIndex(index)+"_extraocularmovements_squint_tropia_horizontal_exotropia']"));
	}

	public List<WebElement> list_BtnVerticalTropiaSquint(int index){
		return driver.findElements(By.xpath("//button[@name='"+findRightOrLeftIndex(index)+"_extraocularmovements_squint_tropia_vertical']"));
	}

	public List<WebElement> list_BtnParalyticTropiaSquint(int index){
		return driver.findElements(By.xpath("//button[@name='"+findRightOrLeftIndex(index)+"_extraocularmovements_squint_tropia_paralytic']"));
	}

	public List<WebElement> list_BtnPhoriaSquint(int index){
		return driver.findElements(By.xpath("//button[@name='"+findRightOrLeftIndex(index)+"_extraocularmovements_squint_phoria']"));
	}

	public WebElement list_BtnClearSquint(int index){
		return driver.findElement(By.xpath("//small[@id='clear_"+findRightOrLeftIndex(index)+"_squint']"));
	}

	public List<WebElement> list_BtnNatureOfInjury(int index){
		return driver.findElements(By.xpath("//button[contains(@class,'"+findRightOrLeftIndex(index)+"_nature_of_injury')]"));
	}

	public List<WebElement> list_BtnOpenGlobeInjuries(int index){
		return driver.findElements(By.xpath("//button[contains(@class,'"+findRightOrLeftIndex(index)+"_open_globe')]"));
	}

	public List<WebElement> list_BtnClosedGlobeInjuries(int index){
		return driver.findElements(By.xpath("//button[contains(@class,'"+findRightOrLeftIndex(index)+"_closed_globe')]"));
	}

	public List<WebElement> list_inputCCT_Reading(String CCTno){
		return driver.findElements(By.xpath("//input[contains(@id,'cornea_cct_reading"+CCTno+"')][@type='text']"));
	}

	public List<WebElement> list_inputCCT_Time(String CCTno){
		return driver.findElements(By.xpath("//input[contains(@id,'reading_time"+CCTno+"')][@type='text']"));
	}

	private static String findRightOrLeftIndex(int index){
		String value=null;
		if(index>0){
			value="l";
		}
		else{
			value="r";
		}
		return  value;
	}

	public String leftOrRight(String side) {
		side = side.toLowerCase();
		if (side.equals("l") || side.equals("left")) {
			side = "l";
		} else {
			side = "r";
		}
		return side;
	}



}
