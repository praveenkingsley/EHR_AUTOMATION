package tests.settings.organisationSettings.general.templateCustomHeader;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.settings.organisationSettings.general.Page_TemplateCustomHeaderSettings;

import java.util.HashMap;
import java.util.Map;


public class TemplateCustomHeader_PharmacyInvoiceTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();

    @Test(enabled = true, description = "Validate storing initial fields order and after moving fields down order")
    public void validateStoringInitialOrderAndMoveFieldOfPharmacyInvoice() {

        Page_TemplateCustomHeaderSettings oPage_TemplateCustomHeaderSettings = new Page_TemplateCustomHeaderSettings(driver);
        HashMap<Integer, String> initialFieldsOrder = new HashMap<>();
        HashMap<Integer, String> afterEditFieldsOrder = new HashMap<>();
        boolean bFieldsOrderEditCorrect = false;
        String beforeEditFirstEntryValue = "";
        int indexOfFieldToMove = -1;
        int indexOfMoveButton = -1;
        int beforeEditFirstEntryKey = -1;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                //get pharmacy invoice settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Template Custom Header");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_pharmacyDepartmentSetting),
                        "Pharmacy Department Selected");

                for (WebElement viewOrder : oPage_TemplateCustomHeaderSettings.list_viewPharmacyInvoiceFieldsOrder) {
                    String fullText[] = viewOrder.getText().trim().split("-");

                    int indexOfField = oPage_TemplateCustomHeaderSettings.list_viewPharmacyInvoiceFieldsOrder.indexOf(viewOrder);
                    String fieldValue[] = fullText[0].split("\\n");
                    initialFieldsOrder.put(indexOfField, fieldValue[0]);
                }

                Map.Entry<Integer, String> beforeEditFirstEntry = initialFieldsOrder.entrySet().stream().findFirst().get();
                beforeEditFirstEntryKey = beforeEditFirstEntry.getKey();
                beforeEditFirstEntryValue = beforeEditFirstEntry.getValue();
                m_assert.assertInfo("Value initially at index <b> " + beforeEditFirstEntryKey + " is " + beforeEditFirstEntryValue + "</b>");

                /*for (Map.Entry<Integer, String> entry : initialFieldsOrder.entrySet()) {
                    if (entry.getValue().equalsIgnoreCase(fieldToMove)) {
                        initialKey = entry.getKey();
                        m_assert.assertInfo("The key for value initially <b> " + fieldToMove + " is " + entry.getKey() + "</b>");
                        break;
                    }
                }*/

                Cls_Generic_Methods.clickElement(oPage_TemplateCustomHeaderSettings.button_pharmacyEditSetting);
                m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.header_mainModalPharmacy, 4),
                        "Settings modal opened");

                for (WebElement editOrder : oPage_TemplateCustomHeaderSettings.list_editPharmacyInvoiceFieldsOrder) {
                    if (Cls_Generic_Methods.getTextInElement(editOrder).contains(beforeEditFirstEntryValue)) {
                        indexOfFieldToMove = oPage_TemplateCustomHeaderSettings.list_editPharmacyInvoiceFieldsOrder.indexOf(editOrder);
                        break;
                    }
                }
                //Find the respected arrow move button and move
                for (WebElement btn_move : oPage_TemplateCustomHeaderSettings.list_buttonMoveDownPharmacyInvoiceFieldsOrder) {
                    if (btn_move.isDisplayed()) {
                        indexOfMoveButton = oPage_TemplateCustomHeaderSettings.list_buttonMoveDownPharmacyInvoiceFieldsOrder.indexOf(btn_move);
                    }

                    if (indexOfFieldToMove == indexOfMoveButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, btn_move), "<b> Arrow down </b> button clicked for field: " + beforeEditFirstEntryValue);
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }
                }

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_savePharmacyOrder),
                        "Pharmacy Invoice Order Settings Saved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.button_pharmacyDepartmentSetting, 5);
                for (WebElement viewOrder : oPage_TemplateCustomHeaderSettings.list_viewPharmacyInvoiceFieldsOrder) {
                    String fullText[] = viewOrder.getText().trim().split("-");
                    int indexOfField = oPage_TemplateCustomHeaderSettings.list_viewPharmacyInvoiceFieldsOrder.indexOf(viewOrder);
                    String fieldValue[] = fullText[0].split("\\n");
                    afterEditFieldsOrder.put(indexOfField, fieldValue[0]);
                }
                Map.Entry<Integer, String> afterEditFirstEntry = afterEditFieldsOrder.entrySet().stream().findFirst().get();
                int afterEditFirstEntryKey = afterEditFirstEntry.getKey();
                String afterEditFirstEntryValue = afterEditFirstEntry.getValue();
                m_assert.assertInfo("Value after moving field <b> " + beforeEditFirstEntryValue + " at index  " + afterEditFirstEntryKey + " is " + afterEditFirstEntryValue + "</b>");

                if (!(beforeEditFirstEntryValue.equalsIgnoreCase(afterEditFirstEntryValue))) {
                    bFieldsOrderEditCorrect = true;
                }
                m_assert.assertTrue(bFieldsOrderEditCorrect, "<b>Pharmacy Invoice Settings Order of fields is correct </b>");

            } catch (Exception e) {
                m_assert.assertFatal("" + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

    }

    @Test(enabled = true, description = "Validate unselecting the field and reselect again")
    public void validateRemovalOfFieldAndReselectFieldOfPharmacyInvoice() {

        Page_TemplateCustomHeaderSettings oPage_TemplateCustomHeaderSettings = new Page_TemplateCustomHeaderSettings(driver);
        HashMap<Integer, String> initialFieldsOrder = new HashMap<>();
        HashMap<Integer, String> afterRemoveFieldsOrder = new HashMap<>();
        boolean bFieldsOrderEditCorrect = false;
        String fieldToRemove = "";
        int indexOfFieldToRemove = -1;
        int indexOfRemoveButton = -1;


        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                //get pharmacy invoice settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Template Custom Header");

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_pharmacyDepartmentSetting),
                        "Pharmacy Department Selected");

                for (WebElement viewOrder : oPage_TemplateCustomHeaderSettings.list_viewPharmacyInvoiceFieldsOrder) {
                    String fullText[] = viewOrder.getText().trim().split("-");

                    int indexOfField = oPage_TemplateCustomHeaderSettings.list_viewPharmacyInvoiceFieldsOrder.indexOf(viewOrder);
                    String fieldValue[] = fullText[0].split("\\n");
                    initialFieldsOrder.put(indexOfField, fieldValue[0]);
                }
//                System.out.println("here" + initialFieldsOrder.values());
                m_assert.assertInfo("Field Values initially: <br><b>" + initialFieldsOrder.values() + "</b>");

                Map.Entry<Integer, String> initialEntry = initialFieldsOrder.entrySet().stream().findFirst().get();
                int initialEntryKey = initialEntry.getKey();
                fieldToRemove = initialEntry.getValue();
                m_assert.assertInfo("Value initially at index <b> " + initialEntryKey + " is " + fieldToRemove + "</b>");

                Cls_Generic_Methods.clickElement(oPage_TemplateCustomHeaderSettings.button_pharmacyEditSetting);
                m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.header_mainModalPharmacy, 4),
                        "Settings modal opened");

                for (WebElement removeField : oPage_TemplateCustomHeaderSettings.list_editPharmacyInvoiceFieldsOrder) {
                    if (Cls_Generic_Methods.getTextInElement(removeField).contains(fieldToRemove)) {
                        indexOfFieldToRemove = oPage_TemplateCustomHeaderSettings.list_editPharmacyInvoiceFieldsOrder.indexOf(removeField);
                        break;
                    }
                }

                //Find the respected delete button and delete
                for (WebElement btn_remove : oPage_TemplateCustomHeaderSettings.list_buttonRemovePharmacyInvoiceFields) {
                    if (btn_remove.isDisplayed()) {
                        indexOfRemoveButton = oPage_TemplateCustomHeaderSettings.list_buttonRemovePharmacyInvoiceFields.indexOf(btn_remove);
                    }

                    if (indexOfFieldToRemove == indexOfRemoveButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, btn_remove), "<b> Remove </b> button clicked for " + fieldToRemove);
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }
                }

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_savePharmacyOrder),
                        "Order Settings Saved");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.button_pharmacyDepartmentSetting, 5);
                for (WebElement viewOrder : oPage_TemplateCustomHeaderSettings.list_viewPharmacyInvoiceFieldsOrder) {
                    String fullText[] = viewOrder.getText().trim().split("-");
                    int indexOfField = oPage_TemplateCustomHeaderSettings.list_viewPharmacyInvoiceFieldsOrder.indexOf(viewOrder);
                    String fieldValue[] = fullText[0].split("\\n");
                    afterRemoveFieldsOrder.put(indexOfField, fieldValue[0]);
                }
                m_assert.assertInfo("Field Values after removing field " + fieldToRemove + "<br> <b> " + afterRemoveFieldsOrder.values() + "</b>");

                if (!(initialFieldsOrder.equals(afterRemoveFieldsOrder))) {
                    bFieldsOrderEditCorrect = true;
                }
                m_assert.assertTrue(bFieldsOrderEditCorrect, "<b>Field removed correctly </b>");

                //Re-Select the removed field
                Cls_Generic_Methods.clickElement(oPage_TemplateCustomHeaderSettings.button_pharmacyEditSetting);
                m_assert.assertInfo(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.header_mainModalPharmacy, 4),
                        "Settings modal opened for re-selecting removed field");

                for (WebElement removedField : oPage_TemplateCustomHeaderSettings.list_removedPharmacyInvoiceFields) {
                    if (Cls_Generic_Methods.getTextInElement(removedField).contains(fieldToRemove)) {
                        indexOfFieldToRemove = oPage_TemplateCustomHeaderSettings.list_removedPharmacyInvoiceFields.indexOf(removedField);
                        break;
                    }
                }

                //Find the respected re-select button and re-select
                for (WebElement btn_reselect : oPage_TemplateCustomHeaderSettings.list_buttonReSelectPharmacyInvoiceFields) {
                    if (btn_reselect.isDisplayed()) {
                        indexOfRemoveButton = oPage_TemplateCustomHeaderSettings.list_buttonReSelectPharmacyInvoiceFields.indexOf(btn_reselect);
                    }

                    if (indexOfFieldToRemove == indexOfRemoveButton) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(driver, btn_reselect),
                                "<b> Re-Select button clicked for " + fieldToRemove + " </b> field");
                        Cls_Generic_Methods.customWait(3);
                        break;
                    }
                }

                m_assert.assertInfo(Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomHeaderSettings.button_savePharmacyOrder),
                        "Order Settings Saved after re-selecting field");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomHeaderSettings.button_pharmacyDepartmentSetting, 5);

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