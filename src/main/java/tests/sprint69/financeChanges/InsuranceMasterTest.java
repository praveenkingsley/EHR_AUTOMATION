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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InsuranceMasterTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    public static Map<String, String> mapTracker = new HashMap<String, String>();
    public static String key_UpdatedInsuranceName = "key_InsuranceName";
    Date date = new Date();
    String date1 = dateFormat.format(date);

    String sInsuranceName = "TestAutoInsuranceName" + date1;
    String sInsuranceDescription = "TestAutoInsuranceDescription" + date1;
    String sUpdatedInsuranceName = "UpdatedTestAutoInsuranceName" + date1;
    String sUpdatedInsuranceDescription = "UpdatedTestAutoInsuranceDescription" + date1;


    @Test(enabled = true, description = "Validate Adding New Insurance")
    public void validateAddingNewInsurance() {
        String sContactGroupName = "Insurance";
        boolean bPayerTypeMasterFound = false;
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInsurance, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addInsurance),
                        "Add Insurance button clicked ");

                //adding new Insurance
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.text_addInsuranceLabel, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.text_addInsuranceLabel),
                        "Add insurance pop opened");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_insuranceName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_insuranceName, sInsuranceName), "Entered Insurance name  =  <b> " + sInsuranceName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_insuranceDescription, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_insuranceDescription, sInsuranceDescription), "Entered Insurance Description  =  <b> " + sInsuranceDescription + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveChanges, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveChanges),
                        "Save changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInsurance, 20);

                //Verify newly created Insurance in Payer master form.
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPayerMaster),
                        "Add Payer master button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.label_addPayerForm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.label_addPayerForm),
                        "Add Payer form opened");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_payerTypeMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.field_payerTypeMaster),
                        "clicked on payer type master field");
                Cls_Generic_Methods.customWait();
                for (WebElement ePayerTypeMasterList : oPage_FinanceChanges.list_payerTypeMaster) {
                    if (ePayerTypeMasterList.getText().equalsIgnoreCase(sInsuranceName)) {
                        bPayerTypeMasterFound = true;
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(ePayerTypeMasterList), "Clicked on payer type master = <b>" + sInsuranceName + "</b>");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bPayerTypeMasterFound) {
                    m_assert.assertTrue(true, "payer type master Found in payer form =  <b> " + sInsuranceName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "incorrect payer type master Found in payer form  ");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);
                Cls_Generic_Methods.customWait();
            } catch (Exception e) {
                m_assert.assertFatal("Insurance not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Edit And Disable Insurance Functionality")
    public void validateEditAndDisableInsuranceFunctionality() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        String sContactGroupName = "Insurance";
        boolean bPayerTypeMasterFound = false;
        boolean bInuranceNameFoundInTable = false;
        boolean bInuranceDescriptionFoundInTable = false;
        int indexOfInsuranceName = -1;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInsurance, 20);

                //validate Insurance name in table
                for (WebElement eInsuranceName : oPage_FinanceChanges.list_insuranceName) {
                    if (eInsuranceName.getText().equalsIgnoreCase(sInsuranceName)) {
                        bInuranceNameFoundInTable = true;
                        indexOfInsuranceName = oPage_FinanceChanges.list_insuranceName.indexOf(eInsuranceName);
                        break;
                    }
                }
                m_assert.assertTrue(bInuranceNameFoundInTable, "Insurance Name Found in table <b> " + sInsuranceName + " </b> ");

                //Validate insurance description
                String sInsuranceDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_insuranceDescription.get(indexOfInsuranceName));
                if (sInsuranceDescriptionOnUI.equalsIgnoreCase(sInsuranceDescription)) {
                    bInuranceDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bInuranceDescriptionFoundInTable, "Insurance Description Found in table <b> " + sInsuranceDescriptionOnUI + " </b> ");

                //validate edit insurance functionality
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_editButton.get(indexOfInsuranceName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_insuranceName, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.inputField_insuranceName), "Cleared values in insurance name field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_insuranceName, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_insuranceName, sUpdatedInsuranceName), "Entered Insurance name  =  <b> " + sUpdatedInsuranceName + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_insuranceDescription, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clearValuesInElement(oPage_FinanceChanges.inputField_insuranceDescription), "Cleared values in insurance description field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.inputField_insuranceDescription, 20);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_FinanceChanges.inputField_insuranceDescription, sUpdatedInsuranceDescription), "Entered Insurance Description  =  <b> " + sUpdatedInsuranceDescription + " </b>");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_saveChanges, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_saveChanges),
                        "Save changes button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInsurance, 20);

                //validate Updated Insurance Name in table
                for (WebElement eInsuranceName : oPage_FinanceChanges.list_insuranceName) {
                    if (eInsuranceName.getText().equalsIgnoreCase(sUpdatedInsuranceName)) {
                        bInuranceNameFoundInTable = true;
                        mapTracker.put(key_UpdatedInsuranceName, sUpdatedInsuranceName);
                        indexOfInsuranceName = oPage_FinanceChanges.list_insuranceName.indexOf(eInsuranceName);
                        break;
                    }
                }
                m_assert.assertTrue(bInuranceNameFoundInTable, "Insurance Name Found in table <b> " + sUpdatedInsuranceName + " </b> ");

                //Validate updated insurance description in table
                String sUpdatedInsuranceDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_insuranceDescription.get(indexOfInsuranceName));
                if (sUpdatedInsuranceDescriptionOnUI.equalsIgnoreCase(sUpdatedInsuranceDescriptionOnUI)) {
                    bInuranceDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bInuranceDescriptionFoundInTable, "Updated Insurance Description Found in table <b> " + sUpdatedInsuranceDescriptionOnUI + " </b> ");

                //Verify updated  Insurance in Payer master form.
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPayerMaster),
                        "Add Payer master button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.label_addPayerForm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.label_addPayerForm),
                        "Add Payer form opened");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_payerTypeMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.field_payerTypeMaster),
                        "clicked on payer type master field");
                Cls_Generic_Methods.customWait(4);
                for (WebElement ePayerTypeMasterList : oPage_FinanceChanges.list_payerTypeMaster) {
                    if (ePayerTypeMasterList.getText().equalsIgnoreCase(sUpdatedInsuranceName)) {
                        bPayerTypeMasterFound = true;
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(ePayerTypeMasterList), "Clicked on updated payer type master = <b>" + sUpdatedInsuranceName + "</b>");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bPayerTypeMasterFound) {
                    m_assert.assertTrue(true, "Updated payer type master Found in payer form = <b> " + sUpdatedInsuranceName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "incorrect payer type master Found in payer form  ");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 3);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);

                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.customWait(2);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();

                //Validate Disable insurance functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInsurance, 20);
                Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_disableAndActiveButton.get(indexOfInsuranceName));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_confirm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.button_confirm),
                        "Confirm button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInsurance, 20);
                //Verify Disabled Insurance is not present in Payer master form.
                Cls_Generic_Methods.driverRefresh();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPayerMaster),
                        "Add Payer master button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.label_addPayerForm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.label_addPayerForm),
                        "Add Payer form opened");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_payerTypeMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.field_payerTypeMaster),
                        "clicked on payer type master field");
                Cls_Generic_Methods.customWait(4);
                for (WebElement ePayerTypeMasterList : oPage_FinanceChanges.list_payerTypeMaster) {
                    String sDisabledInsuranceName = ePayerTypeMasterList.getText();
                    if (sDisabledInsuranceName.equals(sInsuranceName)) {
                        m_assert.assertTrue(false, "Disabled Insurance Found in payer form <b> " + sInsuranceName + " </b> ");
                    } else {
                        m_assert.assertTrue(true, "Disabled Insurance = <b> " + sInsuranceName + " </b> is not Preset in the payer type master dropdown, " + "current Insurnace name inside the payer type master dropdown =<b>  " + sDisabledInsuranceName + " </b>");
                    }
                }
                Cls_Generic_Methods.driverRefresh();
            } catch (Exception e) {
                m_assert.assertFatal("Insurance is not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate Activate Insurance functionality")
    public void validateActiveInsuranceFunctionality() {
        Page_FinanceChanges oPage_FinanceChanges = new Page_FinanceChanges(driver);
        boolean bInuranceNameFoundInTable = false;
        boolean bInuranceDescriptionFoundInTable = false;
        int indexOfInsuranceName = -1;
        String sContactGroupName = "Insurance";
        boolean bPayerTypeMasterFound = false;
        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Insurance Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInsurance, 20);

                //validate Insurance Name in table
                for (WebElement eInsuranceName : oPage_FinanceChanges.list_insuranceName) {
                    if (eInsuranceName.getText().equalsIgnoreCase(sUpdatedInsuranceName)) {
                        bInuranceNameFoundInTable = true;
                        mapTracker.put(key_UpdatedInsuranceName, sUpdatedInsuranceName);
                        indexOfInsuranceName = oPage_FinanceChanges.list_insuranceName.indexOf(eInsuranceName);
                        break;
                    }
                }
                m_assert.assertTrue(bInuranceNameFoundInTable, "Insurance Name Found in table <b> " + sUpdatedInsuranceName + " </b> ");

                //Validate description in table
                String sUpdatedInsuranceDescriptionOnUI = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_insuranceDescription.get(indexOfInsuranceName));
                if (sUpdatedInsuranceDescriptionOnUI.equalsIgnoreCase(sUpdatedInsuranceDescriptionOnUI)) {
                    bInuranceDescriptionFoundInTable = true;
                }
                m_assert.assertTrue(bInuranceDescriptionFoundInTable, "Updated Insurance Description Found in table <b> " + sUpdatedInsuranceDescriptionOnUI + " </b> ");

                //Validate Activate insurance functionality
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInsurance, 20);
                String sButtonName = Cls_Generic_Methods.getTextInElement(oPage_FinanceChanges.list_disableAndActiveButton.get(indexOfInsuranceName));
                if (sButtonName.equalsIgnoreCase("Active")) {
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.list_disableAndActiveButton.get(indexOfInsuranceName)), "Clicked on <b> Active </b> button");
                }
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addInsurance, 20);

                //Verify Activated Insurance present in Payer master form.
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.customWait();
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Payer Master");
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.scrollToTop();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_addPayerMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_FinanceChanges.button_addPayerMaster),
                        "Add Payer master button clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.label_addPayerForm, 20);
                m_assert.assertInfo(Cls_Generic_Methods.isElementDisplayed(oPage_FinanceChanges.label_addPayerForm),
                        "Add Payer form opened");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.select_contactGroup, 20);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.selectElementByVisibleText(oPage_FinanceChanges.select_contactGroup, sContactGroupName);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.field_payerTypeMaster, 20);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_FinanceChanges.field_payerTypeMaster),
                        "clicked on payer type master field");
                Cls_Generic_Methods.customWait();
                for (WebElement ePayerTypeMasterList : oPage_FinanceChanges.list_payerTypeMaster) {
                    if (ePayerTypeMasterList.getText().equalsIgnoreCase(sUpdatedInsuranceName)) {
                        bPayerTypeMasterFound = true;
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(ePayerTypeMasterList), "Clicked on payer type master = <b>" + sUpdatedInsuranceName + "</b>");
                        Cls_Generic_Methods.customWait();
                        break;
                    }
                }
                if (bPayerTypeMasterFound) {
                    m_assert.assertTrue(true, "payer type master Found in payer form = <b> " + sUpdatedInsuranceName + " </b> ");
                } else {
                    m_assert.assertTrue(false, "incorrect payer type master Found in payer form");
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_FinanceChanges.button_Close, 20);
                Cls_Generic_Methods.clickElement(oPage_FinanceChanges.button_Close);
                Cls_Generic_Methods.customWait();
            } catch (Exception e) {
                m_assert.assertFatal("Insurance is not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }
}



