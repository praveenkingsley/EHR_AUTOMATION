package tests.settings.organisationSettings.general.templateCustomHeader;


import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.Settings_Data;
import data.settingsData.OrganisationSettings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.settings.organisationSettings.general.Page_TemplateCustomHeaderSettings;

import java.util.ArrayList;
import java.util.List;

import static pages.commonElements.CommonActions.oEHR_Data;


public class TemplateCustomHeader_OpticalInvoiceTest extends TestBase {


    @Test(enabled = true, description = "Validate the optical button under template custom header")
    public void validateOpticalLinkUnderTemplateCustomerHeader() {

        Page_TemplateCustomHeaderSettings oPage_TemplateCustomerHeader = new Page_TemplateCustomHeaderSettings(driver);

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            String sDepartmentName = "OPTICAL";
            boolean bOpticalButtonFound = false;
            try {

                //get clinical laboratory settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Template Custom Header");

                //click on Template Custom Header
                Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomerHeader.link_templateCustomerHeader);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomerHeader.button_Optical, 10);
                for(WebElement eDepartmentButton: oPage_TemplateCustomerHeader.button_department){
                    if(Cls_Generic_Methods.getTextInElement(eDepartmentButton).equals(sDepartmentName)){
                        bOpticalButtonFound = true;
                        Cls_Generic_Methods.clickElement(eDepartmentButton);
                        break;
                    }
                }
                m_assert.assertTrue(bOpticalButtonFound,"Optical Link present under template custom header");

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Edit the optical invoice settings")
    public void editSelectedLabelOfOpticalSetting() {

        Page_TemplateCustomHeaderSettings oPage_TemplateCustomerHeader = new Page_TemplateCustomHeaderSettings(driver);
        List<String> initial_labelList = new ArrayList<String>();
        List<String> label_listFromEditBox = new ArrayList<String>();
        List<String> label_afterEdit = new ArrayList<String>();

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                //get clinical laboratory settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Template Custom Header");

                //click on Template Custom Header
                Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomerHeader.link_templateCustomerHeader);
                Cls_Generic_Methods.customWait(1);

                //Click on Optical button
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TemplateCustomerHeader.button_Optical), "Click on optical button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomerHeader.button_editOpticalInvoice, 10);

                //Store the initial labels present on the optical summary
                int initialLabel = oPage_TemplateCustomerHeader.label_summaryOptical.size();
                for(WebElement eLabel: oPage_TemplateCustomerHeader.label_summaryOptical )
                {
                    initial_labelList.add(Cls_Generic_Methods.getTextInElement(eLabel));
                }
                m_assert.assertTrue(initial_labelList + "\n initial label on optical summary page");

                //click on edit icon of optical invoice settings
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TemplateCustomerHeader.button_editOpticalInvoice), "Click on edit optical button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomerHeader.header_opticalInvoiceSettingsEdit, 5);

                //click on down arrow
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TemplateCustomerHeader.button_firstDownArrow), "Click on down arrow button");
                Cls_Generic_Methods.customWait(2);

                //store all the selected labels on a list optical invoice settings page
                int existingLabel = oPage_TemplateCustomerHeader.name_labelInInvoice.size();
                for(WebElement eLabel: oPage_TemplateCustomerHeader.name_labelInInvoice )
                {
                    label_listFromEditBox.add(Cls_Generic_Methods.getTextInElement(eLabel));
                }
                m_assert.assertTrue(label_listFromEditBox + "\n Label BEFORE edit on page");

                //Do save changes
                Cls_Generic_Methods.customWait(2);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TemplateCustomerHeader.input_saveSettings), "Click on save optical invoice settings");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomerHeader.button_editOpticalInvoice, 5);

                //store all the selected labels on a list for edit optical summary
                oPage_TemplateCustomerHeader = new Page_TemplateCustomHeaderSettings(driver);
                int editedLabel = oPage_TemplateCustomerHeader.label_summaryOptical.size();

                for(WebElement eLabel: oPage_TemplateCustomerHeader.label_summaryOptical )
                {
                    label_afterEdit.add(Cls_Generic_Methods.getTextInElement(eLabel));
                }
                m_assert.assertTrue(label_afterEdit + "\n Label AFTER edit on summary ");

                //Validate labels from Edit box should be equals to labels after edit
                try {
                    if (label_listFromEditBox.size() == label_afterEdit.size()) {
                        for (int i = 0; i < label_listFromEditBox.size(); i++) {

                            if (label_listFromEditBox.get(i).equals(label_afterEdit.get(i))) {
                                m_assert.assertInfo(
                                        "Label [" + label_afterEdit.get(i) + "] is matched in lists from Edit Box & After edit");
                            } else {
                                m_assert.assertInfo(false,
                                        "Label [" + label_afterEdit.get(i) + "] from After Edit is at different position as compared to its location on Edit box.");
                            }

                        }
                    } else {
                        m_assert.assertTrue(false, "The count of elements in lists from Edit Box & after edit is not same.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Exception while comparing lists from Edit Box & After Edit.\n" + e);
                }

                try {
                    if (label_listFromEditBox.size() == label_afterEdit.size()) {
                        if (!initial_labelList.get(0).equals(label_afterEdit.get(0))) {
                            m_assert.assertTrue(true,
                                    "Validate top label before Edit list <b>[" + initial_labelList.get(0) + "]</b> and After Edit list <b>[" + label_afterEdit.get(0) + "]</b> is not same.");

                            if(!initial_labelList.get(0).equals(label_afterEdit.get(1))){
                                m_assert.assertTrue("Validate that the expected top element after element = " + label_afterEdit.get(1) + " & found = " + label_afterEdit.get(0));
                            }
                        } else {
                            m_assert.assertTrue(false,
                                    "Validate top label before Edit list <b>[" + initial_labelList.get(0) + "]</b> and After Edit list <b>[" + label_afterEdit.get(0) + "]</b> is not same.");
                        }
                    } else {
                        m_assert.assertTrue(false, "The count of elements in list before & after edit is not same.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("Exception while comparing lists Before Edit & After Edit.\n" + e);
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Validate delete label on optical invoice")
    public void deleteSelectedLabelOfOpticalSetting() {

        Page_TemplateCustomHeaderSettings oPage_TemplateCustomerHeader = new Page_TemplateCustomHeaderSettings(driver);
        List<String> initial_labelList = new ArrayList<String>();
        List<String> label_afterDeleteOnEditBox = new ArrayList<String>();
        List<String> label_afterDeleteOnSummary = new ArrayList<String>();

        try {

            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                //get clinical laboratory settings

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Template Custom Header");

                //click on Template Custom Header

                Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomerHeader.link_templateCustomerHeader);
                Cls_Generic_Methods.customWait(1);

                //Click on Optical button
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TemplateCustomerHeader.button_Optical), "Click on optical button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomerHeader.button_editOpticalInvoice, 10);

                //Store the initial labels present on the optical summary
//                int initialLabelSize = oPage_TemplateCustomerHeader.label_summaryOptical.size();
                for (WebElement eLabel : oPage_TemplateCustomerHeader.label_summaryOptical) {
                    initial_labelList.add(Cls_Generic_Methods.getTextInElement(eLabel));
                }
                m_assert.assertTrue(initial_labelList + "\n initial label on optical summary page");

                //click on edit icon of optical invoice settings
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TemplateCustomerHeader.button_editOpticalInvoice), "Click on edit optical button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomerHeader.header_opticalInvoiceSettingsEdit, 5);

                // Delete Patient (Must be existing) label
                // TO change the label, update in OrganisationSettings_Data.sLabelToRemove

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TemplateCustomerHeader.span_removeSelectedLabel), "Click on the remove button for " + OrganisationSettings_Data.sLabelToRemove);
                m_assert.assertTrue(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomerHeader.label_unSelectedField, 5), "Deleted label is displayed under unselected fields");

                //store all the selected labels on a list after delete on pages
//                int iListAfterDeleteOnPageOpticalSize = oPage_TemplateCustomerHeader.name_selectedLabel.size();
                for (WebElement eLabel : oPage_TemplateCustomerHeader.name_selectedLabel) {
                    label_afterDeleteOnEditBox.add(Cls_Generic_Methods.getTextInElement(eLabel));
                }
                m_assert.assertTrue(label_afterDeleteOnEditBox + "\n Label after delete on Edit Box");

                //Do save Changes

                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TemplateCustomerHeader.input_saveSettings), "Click on save optical invoice settings");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomerHeader.button_editOpticalInvoice, 5);

                //store all the selected labels on a list after delete on optical summary page
//                int iAfterDeleteLabelsSize = oPage_TemplateCustomerHeader.label_summaryOptical.size();
                for (WebElement eLabel : oPage_TemplateCustomerHeader.label_summaryOptical) {
                    label_afterDeleteOnSummary.add(Cls_Generic_Methods.getTextInElement(eLabel));
                }

                m_assert.assertTrue(label_afterDeleteOnSummary + "\n Label after delete on optical summary");

                //Validate the labels after delete on optical pages should be equals to the label after save on optical summary
                try {
                    if (label_afterDeleteOnEditBox.size() == label_afterDeleteOnSummary.size()) {
                        for (int i = 0; i < label_afterDeleteOnEditBox.size(); i++) {

                            if (label_afterDeleteOnEditBox.get(i).contains(label_afterDeleteOnSummary.get(i))) {
                                m_assert.assertInfo("Label [" + label_afterDeleteOnSummary.get(i) + "] is matched in lists from Edit Box & After delete");
                            } else {
                                m_assert.assertInfo(false, "Label [" + label_afterDeleteOnSummary.get(i) + "] from After Delete is at different position as compared to its location on Edit box.");
                            }

                        }
                    } else {
                        m_assert.assertTrue(false, "The count of elements in lists from Edit Box & after delete is not same.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

                //Validate the deleted labels if there exist on optical print summary after delete and save label on the optical edit box.
                try {
                    if (label_afterDeleteOnSummary.contains(OrganisationSettings_Data.sLabelToRemove)) {
                        m_assert.assertTrue(false, "The label [" + OrganisationSettings_Data.sLabelToRemove + "]still displaying on the optical summary after removing from the optical box");
                    } else {
                        m_assert.assertTrue("The label [" + OrganisationSettings_Data.sLabelToRemove + "] is not there on the optical summary after removing from the optical box");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    m_assert.assertFatal("" + e);
                }

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }


        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Reselect all the deleted labels on optical invoice")
    public void reselectDeletedLabelOfOpticalSetting() {

        Page_TemplateCustomHeaderSettings oPage_TemplateCustomerHeader = new Page_TemplateCustomHeaderSettings(driver);

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {

                //get clinical laboratory settings
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Template Custom Header");

                //click on Template Custom Header
                Cls_Generic_Methods.clickElement(driver, oPage_TemplateCustomerHeader.link_templateCustomerHeader);
                Cls_Generic_Methods.customWait(1);

                //Click on Optical button
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TemplateCustomerHeader.button_Optical), "Click on optical button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomerHeader.button_editOpticalInvoice, 5);

                //click on edit icon of optical invoice settings
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TemplateCustomerHeader.button_editOpticalInvoice), "Click on edit optical button");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomerHeader.header_opticalInvoiceSettingsEdit, 5);

                for (WebElement reSelectButton : oPage_TemplateCustomerHeader.buttonList_Reselect) {
                    int indexOfCurrentButton = oPage_TemplateCustomerHeader.buttonList_Reselect.indexOf(reSelectButton);
                    String sLabelText = Cls_Generic_Methods.getTextInElement(oPage_TemplateCustomerHeader.textList_UnselectedLabels.get(indexOfCurrentButton));
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(reSelectButton), "Click on Reselect button for <b>" + sLabelText + "</b>");
                    Cls_Generic_Methods.customWait(1);
                }

                //Reselect the deleted label


                //Do save Changes
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_TemplateCustomerHeader.input_saveSettings), "Click on Save optical invoice settings");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_TemplateCustomerHeader.button_editOpticalInvoice, 5);

            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

}

