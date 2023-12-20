package tests.sprint69.financeChanges;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.sprint69.financeChanges.Page_FinanceChanges;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TpaMasterTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date date = new Date();
    String date1 = dateFormat.format(date);
    String sTpaName = "TestAutoTpaName" + date1;
    String sTpaDescription = "TestAutoTpaDescription" + date1;
    String sUpdatedTpaName = "UpdatedTestAutoTpaName" + date1;
    String sUpdatedTpaDescription = "UpdatedTestAutoTpaDescription" + date1;

    @Test(enabled = true, description = "Validate Adding New TPA")
    public void validateAddingNewTpaFromMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        String sContactGroupName = "TPA";
        boolean bPayerTypeMasterFound = false;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            CommonActions.selectFacility("TST");
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addTPA, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addTPA),
                        "Add tpa button clicked ");

                //adding new TPA
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.text_addTpaLabel, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.text_addTpaLabel),
                        "Add tpa pop opened");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_tpaName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_tpaName, sTpaName), "Entered TPA name  =  <b> " + sTpaName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_tpaDescription, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_tpaDescription, sTpaDescription), "Entered TPA Description  =  <b> " + sTpaDescription + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveChanges, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveChanges),
                        "Save changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addTPA, 20);
                //Verify newly created TPA in Payer master form.
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPayerMaster),
                        "Add Payer master button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.label_addPayerForm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.label_addPayerForm),
                        "Add Payer form opened");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_payerTypeMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.field_payerTypeMaster),
                        "clicked on payer type master field");
                Cls_Generic_Methods.customWait(5);
                for (WebElement ePayerTypeMasterList : oPage_FinanceChanges.list_payerTypeMaster) {
                    if (ePayerTypeMasterList.getText().equalsIgnoreCase(sTpaName)) {
                        bPayerTypeMasterFound = true;
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(ePayerTypeMasterList), "Clicked on payer type master = <b>" + sTpaName + "</b>");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bPayerTypeMasterFound) {
                    m_assert.assertTrue(true, "payer type master Found in payer form = <b> " + sTpaName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "incorrect payer type master Found in payer form ");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);
                Cls_Generic_Methods.customWait();
            } catch (Exception e) {
                m_assert.assertFatal("Tpa not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Edit And Disable Tpa")
    public void validateEditAndDisableTPAMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        String sContactGroupName = "TPA";
        boolean bPayerTypeMasterFound = false;
        boolean bTPANameFoundInTable = false;
        boolean bTpaDescriptionFoundInTable = false;
        int indexOfTPAName = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addTPA, 20);

                //validate TPA name in table
                for (WebElement eTpaName : oPage_FinanceChanges.list_tpaName) {
                    if (eTpaName.getText().equalsIgnoreCase(sTpaName)) {
                        bTPANameFoundInTable = true;
                        indexOfTPAName = oPage_FinanceChanges.list_tpaName.indexOf(eTpaName);
                        break;
                    }
                }
                m_assert.assertTrue(bTPANameFoundInTable, "TPA Name Found in table <b> " + sTpaName + " </b> ");

                //Validate TPA description
                String sTpaDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_tpaDescription.get(indexOfTPAName));
                if (sTpaDescriptionOnUI.equalsIgnoreCase(sTpaDescription)) {
                    bTpaDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bTpaDescriptionFoundInTable, "TPA Description Found in table <b> " + sTpaDescriptionOnUI + " </b> ");

                //validate edit Tpa functionality
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_editTpaButton.get(indexOfTPAName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_tpaName, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.inputField_tpaName), "Cleared values in Tpa name field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_tpaName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_tpaName, sUpdatedTpaName), "Entered TPA name  =  <b> " + sUpdatedTpaName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_tpaDescription, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.inputField_tpaDescription), "Cleared values in tpa description field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_tpaDescription, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_tpaDescription, sUpdatedTpaDescription), "Entered Tpa Description  =  <b> " + sUpdatedTpaDescription + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveChanges, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveChanges),
                        "Save changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addTPA, 20);

                //validate Updated TPA Name in table
                for (WebElement eTpaName : oPage_FinanceChanges.list_tpaName) {
                    if (eTpaName.getText().equalsIgnoreCase(sUpdatedTpaName)) {
                        bTPANameFoundInTable = true;
                        indexOfTPAName = oPage_FinanceChanges.list_tpaName.indexOf(eTpaName);
                        break;
                    }
                }
                m_assert.assertTrue(bTPANameFoundInTable, "TPA Name Found in table <b> " + sUpdatedTpaName + " </b> ");

                //Validate updated TPA description in table
                String sUpdatedTPADescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_tpaDescription.get(indexOfTPAName));
                if (sUpdatedTPADescriptionOnUI.equalsIgnoreCase(sUpdatedTpaDescription)) {
                    bTpaDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bTpaDescriptionFoundInTable, "Updated TPA Description Found in table <b> " + sUpdatedTPADescriptionOnUI + " </b> ");
                //Verify updated  TPA in Payer master form.
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPayerMaster),
                        "Add Payer master button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.label_addPayerForm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.label_addPayerForm),
                        "Add Payer form opened");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName);
                Cls_Generic_Methods.customWait(5);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.field_payerTypeMaster),
                        "clicked on payer type master field");
                Cls_Generic_Methods.customWait(5);
                for (WebElement ePayerTypeMasterList : oPage_FinanceChanges.list_payerTypeMaster) {
                    if (ePayerTypeMasterList.getText().equalsIgnoreCase(sUpdatedTpaName)) {
                        bPayerTypeMasterFound = true;
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(ePayerTypeMasterList), "Clicked on updated payer type master = <b>" + sUpdatedTpaName + "</b>");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bPayerTypeMasterFound) {
                    m_assert.assertTrue(true, "Updated payer type master Found in payer form = <b> " + sUpdatedTpaName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "incorrect payer type master Found in payer form");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.scrollToTop();
                //Validate Disable TPA functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addTPA, 20);
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_disableAndActiveTpaButton.get(indexOfTPAName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_confirm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_confirm),
                        "Confirm button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addTPA, 20);

                //Verify Disabled panel is not present in Payer master form.
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPayerMaster),
                        "Add Payer master button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.label_addPayerForm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.label_addPayerForm),
                        "Add Payer form opened");
                Cls_Generic_Methods.customWait(3);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_payerTypeMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.field_payerTypeMaster),
                        "clicked on payer type master field");
                Cls_Generic_Methods.customWait(5);
                for (WebElement ePayerTypeMasterList : oPage_FinanceChanges.list_payerTypeMaster) {
                    String sDisabledTPAName = ePayerTypeMasterList.getText();
                    if (sDisabledTPAName.equals(sUpdatedTpaName)) {
                        m_assert.assertTrue(false, "Disabled panel Found in payer form <b> " + sUpdatedTpaName + " </b> ");
                    } else {
                        m_assert.assertTrue(true, "Disabled panel = <b> " + sUpdatedTpaName + " </b> is not Preset in the payer type master dropdown " + "current TPA name inside the payer type master dropdown =<b>  " + sDisabledTPAName + " </b>");
                    }
                }
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
            } catch (Exception e) {
                m_assert.assertFatal("TPA is not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate activating Tpa functionality")
    public void validateActiveTpaMaster() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        String sContactGroupName = "TPA";
        boolean bPayerTypeMasterFound = false;
        boolean bTpaNameFoundInTable = false;
        boolean bTpaDescriptionFoundInTable = false;
        int indexOfTpaName = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "TPA Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInsurance, 20);

                //validate TPA Name in table
                for (WebElement eTpaName : oPage_FinanceChanges.list_tpaName) {
                    if (eTpaName.getText().equalsIgnoreCase(sUpdatedTpaName)) {
                        bTpaNameFoundInTable = true;
                        indexOfTpaName = oPage_FinanceChanges.list_tpaName.indexOf(eTpaName);
                        break;
                    }
                }
                m_assert.assertTrue(bTpaNameFoundInTable, "TPA Name Found in table <b> " + sUpdatedTpaName + " </b> ");

                //Validate description in table
                String sUpdatedTpaDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_tpaDescription.get(indexOfTpaName));
                if (sUpdatedTpaDescriptionOnUI.equalsIgnoreCase(sUpdatedTpaDescription)) {
                    bTpaDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bTpaDescriptionFoundInTable, "Updated Tpa Description Found in table <b> " + sUpdatedTpaDescriptionOnUI + " </b> ");

                //Validate Activate Tpa functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addTPA, 20);
                String sButtonName = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_disableAndActiveTpaButton.get(indexOfTpaName));
                if (sButtonName.equalsIgnoreCase("Active")) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_disableAndActiveTpaButton.get(indexOfTpaName)), "Clicked on <b> Active </b> button");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addTPA, 20);
                //Verify Activated panel present in Payer master form.
                Cls_Generic_Methods.driverRefresh();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPayerMaster),
                        "Add Payer master button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.label_addPayerForm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.label_addPayerForm),
                        "Add Payer form opened");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_contactGroup, 20);
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_payerTypeMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.field_payerTypeMaster),
                        "clicked on payer type master field");
                Cls_Generic_Methods.customWait(5);
                for (WebElement ePayerTypeMasterList : oPage_FinanceChanges.list_payerTypeMaster) {
                    if (ePayerTypeMasterList.getText().equalsIgnoreCase(sUpdatedTpaName)) {
                        bPayerTypeMasterFound = true;
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(ePayerTypeMasterList), "Clicked on payer type master = <b>" + sUpdatedTpaName + "</b>");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bPayerTypeMasterFound) {
                    m_assert.assertTrue(true, "payer type master Found in payer form = <b> " + sUpdatedTpaName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "incorrect payer type master Found in payer form ");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);
            } catch (Exception e) {
                m_assert.assertFatal("TPA is not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }
}
