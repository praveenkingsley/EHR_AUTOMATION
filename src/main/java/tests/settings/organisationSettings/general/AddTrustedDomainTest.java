package tests.settings.organisationSettings.general;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.apache.poi.sl.usermodel.Slide;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.settings.organisationSettings.general.Page_AddTrustedDomain;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;

import java.util.ArrayList;
import java.util.List;


public class AddTrustedDomainTest extends TestBase {
    EHR_Data oEHR_Data = new EHR_Data();
    static String domainName = "test.com";

    @Test(enabled = true, description = "Validate adding new domain")
    public void validateAddingNewDomain() {
        Page_AddTrustedDomain oPage_AddTrustedDomain = new Page_AddTrustedDomain(driver);
        boolean bDomainFoundInTable = false;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Add Trusted Domains");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AddTrustedDomain.button_addNewDomain, 3);
                m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_AddTrustedDomain.button_addNewDomain),
                        "Add new domain button clicked ");

                //adding new domain
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AddTrustedDomain.input_domainName, 5);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_AddTrustedDomain.input_domainName, domainName),
                        "Entered domain name <b> " + domainName + " </b>");
                Cls_Generic_Methods.clickElement(driver, oPage_AddTrustedDomain.button_addDomainModal);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AddTrustedDomain.button_addNewDomain, 3);

                //validate domain name in table
                for (WebElement eDomainName : oPage_AddTrustedDomain.list_domainNamesTable) {
                    if (Cls_Generic_Methods.isElementDisplayed(eDomainName)) {
                        if (eDomainName.getText().equalsIgnoreCase(domainName)) {
                            bDomainFoundInTable = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bDomainFoundInTable, "Domain Found in table <b> " + domainName + " </b> ");
            } catch (Exception e) {
                m_assert.assertFatal("Domain not created " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate disable and enable functionality for the added domain")
    public void validateDisableAndEnableDomain() {
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_AddTrustedDomain oPage_AddTrustedDomain = new Page_AddTrustedDomain(driver);

        boolean bDomainFoundInTable = false;
        boolean bDisableDomain = false;
        boolean bDisabledSuccessfully = false;
        int indexOfDomainName = -1;
        ArrayList<String> afterDisableDomainNames = new ArrayList<String>();
        ArrayList<String> afterEnableDomainNames = new ArrayList<String>();


        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Add Trusted Domains");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AddTrustedDomain.button_addNewDomain, 3);


                //validate domain name in table
                for (WebElement eDomainName : oPage_AddTrustedDomain.list_domainNamesTable) {
                    if (Cls_Generic_Methods.isElementDisplayed(eDomainName)) {
                        if (eDomainName.getText().equalsIgnoreCase(domainName)) {
                            indexOfDomainName = oPage_AddTrustedDomain.list_domainNamesTable.indexOf(eDomainName);
                            m_assert.assertInfo("Domain <b> " + domainName + " </b> found at index " + indexOfDomainName);
                            bDomainFoundInTable = true;
                            break;
                        }
                    }
                }
                m_assert.assertTrue(bDomainFoundInTable, "Domain Found in table <b> " + domainName + " </b> ");

                if (bDomainFoundInTable) {
                    List<WebElement> buttonNameList = oPage_AddTrustedDomain.list_actionsStatus.get(indexOfDomainName).
                            findElements(By.xpath("./child::*"));
                    if (buttonNameList.get(0).getText().equalsIgnoreCase("Disable")) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(buttonNameList.get(0)),
                                "<b> " + buttonNameList.get(0).getText() + " </b> button clicked for domain " + domainName);
                        bDisableDomain = true;

                        Cls_Generic_Methods.customWait();

                        CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addFacility, 3);
                        Cls_Generic_Methods.scrollToTop();
                        Cls_Generic_Methods.customWait(1);
                        Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_addFacility);
                        Cls_Generic_Methods.customWait(4);

                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_addFacility),
                                "Add Facility Button clicked");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_facilityName, 5);

                        Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.dropdown_emailDomain);

                        for (WebElement e : oPage_OrganisationSetup.list_emailDomain) {
                            afterDisableDomainNames.add(Cls_Generic_Methods.getTextInElement(e));
                        }
                        afterDisableDomainNames.remove(0);
                        m_assert.assertInfo("After Disabling domain " + domainName + " left domains are : <b> " + afterDisableDomainNames + " </b> ");
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_close),
                                "facility modal closed");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addFacility, 3);
                    }
                }
                m_assert.assertTrue(bDisableDomain, "Button Disabled clicked successfully");

                if (bDisableDomain) {
                    CommonActions.selectOptionFromLeftInSettingsPanel("General", "Add Trusted Domains");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AddTrustedDomain.button_addNewDomain, 3);

                    List<WebElement> buttonNameList2 = oPage_AddTrustedDomain.list_actionsStatus.get(indexOfDomainName).
                            findElements(By.xpath("./child::*"));
                    if (buttonNameList2.get(0).getText().equalsIgnoreCase("Enable")) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(buttonNameList2.get(0)),
                                "<b> " + buttonNameList2.get(0).getText() + " </b> button clicked for domain " + domainName);
                        Cls_Generic_Methods.customWait();

                        CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addFacility, 5);
                        Cls_Generic_Methods.scrollToTop();
                        Cls_Generic_Methods.customWait(1);
                        Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_addFacility);
                        Cls_Generic_Methods.customWait(4);
                        m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_addFacility),
                                "Add Facility Button clicked");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_facilityName, 5);

                        Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.dropdown_emailDomain);

                        for (WebElement e : oPage_OrganisationSetup.list_emailDomain) {
                            afterEnableDomainNames.add(Cls_Generic_Methods.getTextInElement(e));
                        }
                        afterEnableDomainNames.remove(0);
                        m_assert.assertInfo("After Enabling domain " + domainName + " domains are : <b> " + afterEnableDomainNames + " </b>");
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_close),
                                "facility modal closed");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addFacility, 3);
                    }
                }
                if (!(afterEnableDomainNames.equals(afterDisableDomainNames))) {
                    bDisabledSuccessfully = true;
                }
                m_assert.assertTrue(bDisabledSuccessfully, "Domain is Disabled and Enabled successfully");
            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validate remove functionality for the added domain")
    public void validateRemoveDomainFunctionality() {
        Page_AddTrustedDomain oPage_AddTrustedDomain = new Page_AddTrustedDomain(driver);
        boolean bDomainFoundInTable = false;
        boolean bDomainRemoved = false;
        int indexOfDomainName = -1;

        try {
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);
            try {

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Add Trusted Domains");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_AddTrustedDomain.button_addNewDomain, 3);

                //validate domain name in table
                for (WebElement eDomainName : oPage_AddTrustedDomain.list_domainNamesTable) {
                    if (Cls_Generic_Methods.isElementDisplayed(eDomainName)) {
                        if (eDomainName.getText().equalsIgnoreCase(domainName)) {
                            indexOfDomainName = oPage_AddTrustedDomain.list_domainNamesTable.indexOf(eDomainName);
                            m_assert.assertInfo(domainName + " found at index " + indexOfDomainName);
                            bDomainFoundInTable = true;
                            break;
                        }
                    }
                }

                m_assert.assertTrue(bDomainFoundInTable, "Domain Found in table <b> " + domainName + " </b> ");
                //find respective remove button
                if (bDomainFoundInTable) {
                    List<WebElement> buttonNameList = oPage_AddTrustedDomain.list_actionsStatus.get(indexOfDomainName).
                            findElements(By.xpath("./child::*"));
                    if (buttonNameList.get(1).getText().equalsIgnoreCase("Remove")) {
                        m_assert.assertTrue(Cls_Generic_Methods.clickElement(buttonNameList.get(1)),
                                "<b> " + buttonNameList.get(1).getText() + " </b> button successfully clicked for domain " + domainName);
                   Cls_Generic_Methods.customWait(3);
                    }
                    //validation
                    for (WebElement eDomainName : oPage_AddTrustedDomain.list_domainNamesTable) {
                        if (eDomainName.isDisplayed()) {
                            if (!(eDomainName.getText().equalsIgnoreCase(domainName))) {
                                bDomainRemoved = true;
                                break;
                            }
                        }
                    }
                    m_assert.assertTrue(bDomainRemoved, "Domain removed successfully");
                }


            } catch (Exception e) {
                m_assert.assertFatal("Error loading the application " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            m_assert.assertFatal("Error loading the application " + e);
            e.printStackTrace();
        }
    }


}