package tests.settings.organisationSettings.general.templateCustomHeader;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.settings.organisationSettings.general.Page_IDPrefix;
import pages.settings.organisationSettings.general.Page_TemplateCustomHeaderSettings;

import java.util.HashMap;
import java.util.Map;

public class TemplateCustomHeader_IpdTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();

    @Test(enabled = true, description = "Validating Rearrangement of Field Using Edit in IPD Template")
    public void validateRearrangingFieldInTemplateCustomHeaderOrderInIpdTemplate() {
        boolean bFieldsOrderEditCorrect = false;
        String beforeEditFirstEntryValue = "";
        int indexOfFieldToMove = -1;
        int indexOfMoveButton = -1;
        int beforeEditFirstEntryKey = -1;

        Page_TemplateCustomHeaderSettings oPage_TemplateCustomHeaderSettings = new Page_TemplateCustomHeaderSettings(driver);
        Page_IDPrefix oPage_IDPrefix = new Page_IDPrefix(driver);
        HashMap<Integer, String> initialFieldsOrder = new HashMap<>();
        HashMap<Integer, String> afterEditFieldsOrder = new HashMap<>();


        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Selection Template Custom Header In Organisation Setting
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Template Custom Header");

                // Selecting IPD In Template Custom Header

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.button_ipd, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_ipd), "IPD Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdTemplateSettingHeader, 5);

                //Restoring IPD Template Information before rearrangement

                for (WebElement viewOrder : oPage_TemplateCustomHeaderSettings.list_ipdTemplateSettingViewHeadersList) {

                    String fullText[] = viewOrder.getText().trim().split("-");
                    int indexOfField = oPage_TemplateCustomHeaderSettings.list_ipdTemplateSettingViewHeadersList.indexOf(viewOrder);
                    String fieldValue[] = fullText[0].split("\\n");
                    initialFieldsOrder.put(indexOfField, fieldValue[0]);
                }

                Map.Entry<Integer, String> initialEntry = initialFieldsOrder.entrySet().stream().findFirst().get();
                beforeEditFirstEntryKey = initialEntry.getKey();
                beforeEditFirstEntryValue = initialEntry.getValue();
                m_assert.assertInfo("Value initially at index <b> " + beforeEditFirstEntryKey + " is " + beforeEditFirstEntryValue + "</b>");

                //  Rearranging Field Order In IPD Template Setting

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_TemplateCustomHeaderSettings.button_editIpdTemplateSetting), "Edit IPD Template Setting Button Clicked");
                m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdTemplatePrintSetting, 5),
                        "Edit IPD Template Opened");
                for (WebElement editOrder : oPage_TemplateCustomHeaderSettings.list_editSelectedFieldsOrder) {
                    if (Cls_Generic_Methods.getTextInElement(editOrder).contains(beforeEditFirstEntryValue)) {
                        indexOfFieldToMove = oPage_TemplateCustomHeaderSettings.list_editSelectedFieldsOrder.indexOf(editOrder);
                        break;
                    }

                }

                //Find the respected arrow move button and move

                for (WebElement btn_move : oPage_TemplateCustomHeaderSettings.list_buttonMoveDownFieldsOrder) {
                    if (btn_move.isDisplayed()) {
                        indexOfMoveButton = oPage_TemplateCustomHeaderSettings.list_buttonMoveDownFieldsOrder.indexOf(btn_move);
                    }

                    if (indexOfFieldToMove == indexOfMoveButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, btn_move), "<b> Arrow down </b> button clicked for field: " + beforeEditFirstEntryValue);
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }

                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_saveCustomTemplate),
                        "Save Button Clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdTemplateSettingHeader, 5);

                // Verifying Selected Field Moved Down to Second Places

                for (WebElement viewOrder : oPage_TemplateCustomHeaderSettings.list_ipdTemplateSettingViewHeadersList) {

                    String fullText[] = viewOrder.getText().trim().split("-");
                    int indexOfField = oPage_TemplateCustomHeaderSettings.list_ipdTemplateSettingViewHeadersList.indexOf(viewOrder);
                    String fieldValue[] = fullText[0].split("\\n");
                    afterEditFieldsOrder.put(indexOfField, fieldValue[0]);
                }

                Map.Entry<Integer, String> afterEditEntry = afterEditFieldsOrder.entrySet().stream().findFirst().get();
                int key1 = afterEditEntry.getKey();
                String value1 = afterEditEntry.getValue();
                m_assert.assertInfo("Value after moving field <b> " + beforeEditFirstEntryValue + " at index  " + key1 + " is " + value1 + "</b>");

                if (!(beforeEditFirstEntryValue.equalsIgnoreCase(value1))) {
                    bFieldsOrderEditCorrect = true;
                }
                m_assert.assertTrue(bFieldsOrderEditCorrect, "<b>IPD Invoice Settings Order of fields is correct </b>");


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Removal of Field Using Edit in IPD Template")
    public void validateRemovalAndReselectFieldInTemplateCustomHeaderOrderInIpdTemplate() {

        Page_TemplateCustomHeaderSettings oPage_TemplateCustomHeaderSettings = new Page_TemplateCustomHeaderSettings(driver);
        Page_IDPrefix oPage_IDPrefix = new Page_IDPrefix(driver);
        HashMap<Integer, String> initialFieldsOrder = new HashMap<>();
        HashMap<Integer, String> afterRemoveFieldsOrder = new HashMap<>();
        boolean bFieldsOrderEditCorrect = false;
        String fieldToRemove = "";
        int indexOfFieldToRemove = -1;
        int indexOfRemoveButton = -1;
        boolean bFieldsOrderReselectCorrect = false;

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Selection Template Custom Header In Organisation Setting
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Template Custom Header");

                // Selecting OPD In Template Custom Header

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.button_ipd, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_ipd), "IPD Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdTemplateSettingHeader, 5);

                //Restoring OPD Template Information before Removal

                for (WebElement viewOrder : oPage_TemplateCustomHeaderSettings.list_ipdTemplateSettingViewHeadersList) {

                    String fullText[] = viewOrder.getText().trim().split("-");
                    int indexOfField = oPage_TemplateCustomHeaderSettings.list_ipdTemplateSettingViewHeadersList.indexOf(viewOrder);
                    String fieldValue[] = fullText[0].split("\\n");
                    initialFieldsOrder.put(indexOfField, fieldValue[0]);
                }

                m_assert.assertInfo("Field Values initially: <br><b>" + initialFieldsOrder.values() + "</b>");
                Map.Entry<Integer, String> initialEntry = initialFieldsOrder.entrySet().stream().findFirst().get();
                int initialKey = initialEntry.getKey();
                fieldToRemove = initialEntry.getValue();

                m_assert.assertInfo("Value initially at index <b> " + initialKey + " is " + fieldToRemove + "</b>");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_TemplateCustomHeaderSettings.button_editIpdTemplateSetting), "Edit IPD Template Setting Button Clicked");
                m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdTemplatePrintSetting, 5),
                        "Edit IPD Template Opened");

                //Find the respected delete button and delete

                for (WebElement removeField : oPage_TemplateCustomHeaderSettings.list_editSelectedFieldsOrder) {
                    if (Cls_Generic_Methods.getTextInElement(removeField).contains(fieldToRemove)) {
                        indexOfFieldToRemove = oPage_TemplateCustomHeaderSettings.list_editSelectedFieldsOrder.indexOf(removeField);
                        break;
                    }

                }

                //Find the respected delete button and delete

                for (WebElement btn_remove : oPage_TemplateCustomHeaderSettings.list_buttonRemoveSelectedField) {
                    if (btn_remove.isDisplayed()) {
                        indexOfRemoveButton = oPage_TemplateCustomHeaderSettings.list_buttonRemoveSelectedField.indexOf(btn_remove);
                    }

                    if (indexOfFieldToRemove == indexOfRemoveButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, btn_remove), "<b> Remove </b> button clicked for " + fieldToRemove);
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }

                }


                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_saveCustomTemplate),
                        "Save Button Clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdTemplateSettingHeader, 5);

                for (WebElement viewOrder : oPage_TemplateCustomHeaderSettings.list_ipdTemplateSettingViewHeadersList) {

                    String fullText[] = viewOrder.getText().trim().split("-");
                    int indexOfField = oPage_TemplateCustomHeaderSettings.list_ipdTemplateSettingViewHeadersList.indexOf(viewOrder);
                    String fieldValue[] = fullText[0].split("\\n");
                    afterRemoveFieldsOrder.put(indexOfField, fieldValue[0]);

                }

                m_assert.assertInfo("Field Values after removing field " + fieldToRemove + " <b> " + afterRemoveFieldsOrder.values() + "</b>");
                if (!(initialFieldsOrder.equals(afterRemoveFieldsOrder))) {
                    bFieldsOrderEditCorrect = true;
                }

                m_assert.assertTrue(bFieldsOrderEditCorrect, "<b>Field removed correctly </b>");

                //Re-Select the removed field

                Cls_Generic_Methods.scrollToTop();
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_editIpdTemplateSetting), "Edit IPD Template Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdTemplatePrintSetting, 5);

                for (WebElement removedField : oPage_TemplateCustomHeaderSettings.list_unselectedOrdersFieldsName) {
                    if (Cls_Generic_Methods.getTextInElement(removedField).contains(fieldToRemove)) {
                        indexOfFieldToRemove = oPage_TemplateCustomHeaderSettings.list_unselectedOrdersFieldsName.indexOf(removedField);
                        break;
                    }
                }

                //Find the respected re-select button and re-select
                for (WebElement btn_reselect : oPage_TemplateCustomHeaderSettings.list_buttonsReselectInUnselectedList) {
                    if (btn_reselect.isDisplayed()) {
                        indexOfRemoveButton = oPage_TemplateCustomHeaderSettings.list_buttonsReselectInUnselectedList.indexOf(btn_reselect);
                    }

                    if (indexOfFieldToRemove == indexOfRemoveButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, btn_reselect), "<b> Re-Select button clicked for " + fieldToRemove + " </b> field");
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }

                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_saveCustomTemplate),
                        "Save Button Clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdTemplateSettingHeader, 5);
                for (WebElement reselectField : oPage_TemplateCustomHeaderSettings.list_ipdTemplateSettingViewHeadersList) {
                    if (Cls_Generic_Methods.getTextInElement(reselectField).contains(fieldToRemove)) {

                        bFieldsOrderReselectCorrect = true;
                        break;
                    }

                }

                if (bFieldsOrderReselectCorrect)
                    m_assert.assertInfo(fieldToRemove + " is reselected successfully");
                else
                    m_assert.assertInfo(fieldToRemove + " is not reselected successfully");


            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validating Rearrangement of Field Using Edit in IPD Invoices")
    public void validateRearrangingFieldInTemplateCustomHeaderOrderInIpdInvoices() {

        Page_TemplateCustomHeaderSettings oPage_TemplateCustomHeaderSettings = new Page_TemplateCustomHeaderSettings(driver);
        Page_IDPrefix oPage_IDPrefix = new Page_IDPrefix(driver);
        HashMap<Integer, String> initialFieldsOrder = new HashMap<>();
        HashMap<Integer, String> afterEditFieldsOrder = new HashMap<>();
        boolean bFieldsOrderEditCorrect = false;
        String initialFieldToMove = "";
        int indexOfFieldToMove = -1;
        int indexOfMoveButton = -1;
        int initialKey = -1;


        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Selection Template Custom Header In Organisation Setting
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Template Custom Header");

                // Selecting IPD In Template Custom Header

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.button_ipd, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_ipd), "IPD Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdTemplateSettingHeader, 5);

                //Restoring IPD Invoices Information before rearrangement

                for (WebElement viewOrder : oPage_TemplateCustomHeaderSettings.list_ipdInvoicesSettingViewHeadersList) {

                    String fullText[] = viewOrder.getText().trim().split("-");
                    int indexOfField = oPage_TemplateCustomHeaderSettings.list_ipdInvoicesSettingViewHeadersList.indexOf(viewOrder);
                    String fieldValue[] = fullText[0].split("\\n");
                    initialFieldsOrder.put(indexOfField, fieldValue[0]);
                }

                Map.Entry<Integer, String> initialEntry = initialFieldsOrder.entrySet().stream().findFirst().get();
                initialKey = initialEntry.getKey();
                initialFieldToMove = initialEntry.getValue();
                m_assert.assertInfo("Value initially at index <b> " + initialKey + " is " + initialFieldToMove + "</b>");

                //  Rearranging Field Order In IPD Invoices Setting

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_TemplateCustomHeaderSettings.button_editIpdInvoicesSetting), "Edit IPD Invoices Setting Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdInvoicesPrintSetting, 5);
                m_assert.assertInfo("Edit IPD Invoices Opened");
                for (WebElement editOrder : oPage_TemplateCustomHeaderSettings.list_editSelectedFieldsOrder) {
                    if (Cls_Generic_Methods.getTextInElement(editOrder).contains(initialFieldToMove)) {
                        indexOfFieldToMove = oPage_TemplateCustomHeaderSettings.list_editSelectedFieldsOrder.indexOf(editOrder);
                        break;
                    }

                }

                //Find the respected arrow move button and move

                for (WebElement btn_move : oPage_TemplateCustomHeaderSettings.list_buttonMoveDownFieldsOrder) {
                    if (btn_move.isDisplayed()) {
                        indexOfMoveButton = oPage_TemplateCustomHeaderSettings.list_buttonMoveDownFieldsOrder.indexOf(btn_move);
                    }

                    if (indexOfFieldToMove == indexOfMoveButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, btn_move), "<b> Arrow down </b> button clicked for field: " + initialFieldToMove);
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }

                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_saveCustomTemplate),
                        "Save Button Clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdInvoicesSettingHeader, 5);

                // Verifying Selected Field Moved Down to Second Places

                for (WebElement viewOrder : oPage_TemplateCustomHeaderSettings.list_ipdTemplateSettingViewHeadersList) {

                    String fullText[] = viewOrder.getText().trim().split("-");
                    int indexOfField = oPage_TemplateCustomHeaderSettings.list_ipdTemplateSettingViewHeadersList.indexOf(viewOrder);
                    String fieldValue[] = fullText[0].split("\\n");
                    afterEditFieldsOrder.put(indexOfField, fieldValue[0]);
                }

                Map.Entry<Integer, String> afterEditEntry = afterEditFieldsOrder.entrySet().stream().findFirst().get();
                int key1 = afterEditEntry.getKey();
                String value1 = afterEditEntry.getValue();
                m_assert.assertInfo("Value after moving field <b> " + initialFieldToMove + " at index  " + key1 + " is " + value1 + "</b>");

                if (!(initialFieldToMove.equalsIgnoreCase(value1))) {
                    bFieldsOrderEditCorrect = true;
                }
                m_assert.assertTrue(bFieldsOrderEditCorrect, "<b>IPD Invoice Settings Order of fields is correct </b>");


            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating Removal of Field Using Edit in IPD Invoices")
    public void validateRemovalAndReselectFieldInTemplateCustomHeaderOrderInIpdInvoices() {

        Page_TemplateCustomHeaderSettings oPage_TemplateCustomHeaderSettings = new Page_TemplateCustomHeaderSettings(driver);
        Page_IDPrefix oPage_IDPrefix = new Page_IDPrefix(driver);
        HashMap<Integer, String> initialFieldsOrder = new HashMap<>();
        HashMap<Integer, String> afterRemoveFieldsOrder = new HashMap<>();
        boolean bFieldsOrderEditCorrect = false;
        String fieldToRemove = "";
        int indexOfFieldToRemove = -1;
        int indexOfRemoveButton = -1;
        boolean bFieldsOrderReselectCorrect = false;

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                // Selection Template Custom Header In Organisation Setting
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IDPrefix.input_idPrefixTextBox, 5);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Template Custom Header");

                // Selecting IPD In Template Custom Header

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.button_ipd, 5);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_ipd), "IPD Button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdTemplateSettingHeader, 5);

                //Restoring IPD Invoices Information before Removal

                for (WebElement viewOrder : oPage_TemplateCustomHeaderSettings.list_ipdInvoicesSettingViewHeadersList) {

                    String fullText[] = viewOrder.getText().trim().split("-");
                    int indexOfField = oPage_TemplateCustomHeaderSettings.list_ipdInvoicesSettingViewHeadersList.indexOf(viewOrder);
                    String fieldValue[] = fullText[0].split("\\n");
                    initialFieldsOrder.put(indexOfField, fieldValue[0]);
                }

                m_assert.assertInfo("Field Values initially: <br><b>" + initialFieldsOrder.values() + "</b>");
                Map.Entry<Integer, String> initialEntry = initialFieldsOrder.entrySet().stream().findFirst().get();
                int initialKey = initialEntry.getKey();
                fieldToRemove = initialEntry.getValue();

                m_assert.assertInfo("Value initially at index <b> " + initialKey + " is " + fieldToRemove + "</b>");

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_TemplateCustomHeaderSettings.button_editIpdInvoicesSetting), "Edit OPD Invoices Setting Button Clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdInvoicesPrintSetting, 5);
                m_assert.assertInfo("Edit IPD Invoices Opened");

                //Find the respected delete button and delete

                for (WebElement removeField : oPage_TemplateCustomHeaderSettings.list_editSelectedFieldsOrder) {
                    if (Cls_Generic_Methods.getTextInElement(removeField).contains(fieldToRemove)) {
                        indexOfFieldToRemove = oPage_TemplateCustomHeaderSettings.list_editSelectedFieldsOrder.indexOf(removeField);
                        break;
                    }

                }

                //Find the respected delete button and delete

                for (WebElement btn_remove : oPage_TemplateCustomHeaderSettings.list_buttonRemoveSelectedField) {
                    if (btn_remove.isDisplayed()) {
                        indexOfRemoveButton = oPage_TemplateCustomHeaderSettings.list_buttonRemoveSelectedField.indexOf(btn_remove);
                    }

                    if (indexOfFieldToRemove == indexOfRemoveButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, btn_remove), "<b> Remove </b> button clicked for " + fieldToRemove);
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }

                }
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_saveCustomTemplate),
                        "Save Button Clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdInvoicesSettingHeader, 5);

                for (WebElement viewOrder : oPage_TemplateCustomHeaderSettings.list_ipdInvoicesSettingViewHeadersList) {
                    String fullText[] = viewOrder.getText().trim().split("-");
                    int indexOfField = oPage_TemplateCustomHeaderSettings.list_ipdInvoicesSettingViewHeadersList.indexOf(viewOrder);
                    String fieldValue[] = fullText[0].split("\\n");
                    afterRemoveFieldsOrder.put(indexOfField, fieldValue[0]);
                }
                m_assert.assertInfo("Field Values after removing field " + fieldToRemove + "<br> <b> " + afterRemoveFieldsOrder.values() + "</b>");

                if (!(initialFieldsOrder.equals(afterRemoveFieldsOrder))) {
                    bFieldsOrderEditCorrect = true;
                }

                m_assert.assertTrue(bFieldsOrderEditCorrect, "<b>Field removed correctly </b>");

                //Re-Select the removed field

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_editIpdInvoicesSetting), "Edit IPD Invoices Button clicked");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdInvoicesSettingHeader, 5);

                for (WebElement removedField : oPage_TemplateCustomHeaderSettings.list_unselectedOrdersFieldsName) {
                    if (Cls_Generic_Methods.getTextInElement(removedField).contains(fieldToRemove)) {
                        indexOfFieldToRemove = oPage_TemplateCustomHeaderSettings.list_unselectedOrdersFieldsName.indexOf(removedField);
                        break;
                    }
                }

                //Find the respected re-select button and re-select
                for (WebElement btn_reselect : oPage_TemplateCustomHeaderSettings.list_buttonsReselectInUnselectedList) {
                    if (btn_reselect.isDisplayed()) {
                        indexOfRemoveButton = oPage_TemplateCustomHeaderSettings.list_buttonsReselectInUnselectedList.indexOf(btn_reselect);
                    }

                    if (indexOfFieldToRemove == indexOfRemoveButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, btn_reselect), "<b> Re-Select button clicked for " + fieldToRemove + " </b> field");
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }

                }

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_saveCustomTemplate),
                        "Save Button is clicked ");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.text_ipdInvoicesSettingHeader, 5);
                for (WebElement reselectField : oPage_TemplateCustomHeaderSettings.list_ipdInvoicesSettingViewHeadersList) {
                    if (Cls_Generic_Methods.getTextInElement(reselectField).contains(fieldToRemove)) {

                        bFieldsOrderReselectCorrect = true;
                        break;
                    }

                }

                if (bFieldsOrderReselectCorrect)
                    m_assert.assertInfo(fieldToRemove + " is reselected successfully");
                else
                    m_assert.assertInfo(fieldToRemove + " is not reselected successfully");


            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

    }


}
