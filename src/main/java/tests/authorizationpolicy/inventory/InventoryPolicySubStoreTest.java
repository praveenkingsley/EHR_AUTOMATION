package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.authorizationPolicy.Page_InventoryPolicy;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.store.PharmacyStore.Items.Page_Master;
import pages.store.PharmacyStore.Page_SubStore;


import java.util.ArrayList;
import java.util.List;

public class InventoryPolicySubStoreTest extends TestBase {

    String sPolicyRequired = "INVENTORY";
    String sPolicyUser = EHR_Data.user_PRAkashTest;
    static String sStore = "Pharmacy automation- Pharmacy";
    String sSubStoreName;
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_InventoryPolicy oPage_InventoryPolicy;
    Page_CommonElements oPage_CommonElements;
    Page_SubStore oPage_SubStore;
    Page_Master oPage_Master;

    String sViewPolicyComponent = "VIEW (SUB STORE)";
    String sAddPolicyComponent = "ADD (SUB STORE)";

    String sEditPolicyComponent = "EDIT (SUB STORE)";
    String sEnableAccessComponent = "ENABLE (SUB STORE)";
    String sDisableAccessComponent = "DISABLE (SUB STORE)";

    String sViewPolicyDescription = "Inventory Sub Store View Access";
    String sAddPolicyDescription = "Inventory Sub Store Creation Access";
    String sEditPolicyDescription = "Inventory Sub Store Edit Access";
    String sEnableAccessPolicyDescription = "Inventory Sub Store Enable Access";
    String sDisableAccessPolicyDescription = "Inventory Sub Store Disable Access";


    @BeforeMethod
    private void init() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        try {
            //Opening Organization Settings
            String expectedLoggedInUser = EHR_Data.user_PRAkashTest;
            Cls_Generic_Methods.customWait(3);
            CommonActions.loginFunctionality(expectedLoggedInUser);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 16);

            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUsers), "clicked on All users");
        } catch (Exception e) {
            m_assert.assertFatal("Unable to open organisation setting " + e);
        }
    }

    @Test(enabled = true, description = "Validate View Sub Store inventory policy")
    public void validateViewSubStoreInventoryPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_SubStore = new Page_SubStore(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait();
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            boolean bSubStoreTab = Cls_Generic_Methods.isElementDisplayed(oPage_SubStore.header_subStorePanelOnLeft);
            m_assert.assertFalse(bSubStoreTab, "Validated -->Inventory Sub Store View Access-->Sub Store Tab is disabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            bSubStoreTab = Cls_Generic_Methods.isElementDisplayed(oPage_SubStore.header_subStorePanelOnLeft);
            m_assert.assertTrue(bSubStoreTab, "Validated --> Inventory Sub Store View Access--> Sub Store Tab is enabled in store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate view Sub Store policy " + e);
        }

    }

    //Inventory sub store creation
    @Test(enabled = true, description = "Validate Add Sub Store inventory policy")
    public void validateAddSubStoreInventoryPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_SubStore=new Page_SubStore(driver);


        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.header_subStorePanelOnLeft,10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SubStore.header_subStorePanelOnLeft),"Selected <b>Sub-Store</b> on store left panel");
            Cls_Generic_Methods.customWait(3);
            boolean bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_SubStore.button_addSubStore);
            m_assert.assertFalse(bAddButtonFound, "Validated --> Inventory Sub Store Creation Access--> Add functionality is disabled in Sub-Store");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.header_subStorePanelOnLeft,10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SubStore.header_subStorePanelOnLeft),"Selected <b>Sub-Store</b> on store left panel");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.button_addSubStore, 6);
            bAddButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_SubStore.button_addSubStore);
            m_assert.assertTrue(bAddButtonFound, "Validated -->Inventory Sub Store Creation Access-->Add functionality is enabled in Sub Store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate add Sub Store policy" + e);
        }
    }

    //Inventory sub store edit access
    @Test(enabled = true, description = "Validate Edit sub store inventory policy")
    public void validateEditSubStoreInventoryPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_SubStore=new Page_SubStore(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent,sEditPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.header_subStorePanelOnLeft,10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SubStore.header_subStorePanelOnLeft),"Selected <b>Sub-Store</b> on store left panel");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.button_addSubStore, 6);
            sSubStoreName= selectSubStore("Active");
            Cls_Generic_Methods.customWait();
            boolean bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_SubStore.button_editSubStore);
            m_assert.assertFalse(bEditButtonFound, "Validated -->Inventory Sub Store edit-->Edit functionality is disabled in Sub-Store");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent,sEditPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.header_subStorePanelOnLeft,10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SubStore.header_subStorePanelOnLeft),"Selected <b>Sub-Store</b> on store left panel");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.button_addSubStore, 6);
            sSubStoreName= selectSubStore("Active");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.button_editSubStore,10);
            bEditButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_SubStore.button_editSubStore);
            m_assert.assertTrue(bEditButtonFound, "Validated -->Inventory Sub Store edit-->Edit functionality is enabled in Sub-Store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Edit Sub-Store" + e);
        }
    }

    //Inventory sub store disable access
    @Test(enabled = true, description = "Validate Disable access sub store inventory policy")
    public void validateDisableAccessSubStoreInventoryPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_SubStore=new Page_SubStore(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired,  sDisableAccessComponent,sDisableAccessPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.header_subStorePanelOnLeft,10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SubStore.header_subStorePanelOnLeft),"Selected <b>Sub-Store</b> on store left panel");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.button_addSubStore, 6);
            sSubStoreName= selectSubStore("Active");
            Cls_Generic_Methods.customWait();
            boolean bDisableButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_SubStore.button_disableSubStore);
            m_assert.assertFalse(bDisableButtonFound, "Validated -->Inventory Sub Store Disable Access--> <b>Disable Sub-Store</b> button is not displayed in Sub-Store");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sDisableAccessComponent,sDisableAccessPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.header_subStorePanelOnLeft,10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SubStore.header_subStorePanelOnLeft),"Selected <b>Sub-Store</b> on store left panel");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.button_addSubStore, 6);
            sSubStoreName= selectSubStore("Active");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.button_editSubStore,10);
            bDisableButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_SubStore.button_disableSubStore);
            m_assert.assertTrue(bDisableButtonFound, "Validated -->Inventory Sub Store Disable Access--> <b>Disable Sub-Store</b> button is displayed in Sub-Store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Disable Access Sub-Store" + e);
        }
    }

    @Test(enabled = true, description = "Validate Enable access sub store inventory policy")
    public void validateEnableAccessSubStoreInventoryPolicy() {
        oPage_Navbar = new Page_Navbar(driver);
        oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        oPage_InventoryPolicy = new Page_InventoryPolicy(driver);
        oPage_CommonElements = new Page_CommonElements(driver);
        oPage_Master = new Page_Master(driver);
        oPage_SubStore=new Page_SubStore(driver);

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired,  sEnableAccessComponent,sEnableAccessPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.header_subStorePanelOnLeft,10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SubStore.header_subStorePanelOnLeft),"Selected <b>Sub-Store</b> on store left panel");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.button_addSubStore, 6);
            sSubStoreName= selectSubStore("Inactive");
            Cls_Generic_Methods.customWait();
            boolean bEnableButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_SubStore.button_enableSubStore);
            m_assert.assertFalse(bEnableButtonFound, "Validated -->Inventory Sub Store Enable Access--> <b>Enable Sub-Store</b> button is not displayed in Sub-Store");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEnableAccessComponent,sEnableAccessPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.header_subStorePanelOnLeft,10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_SubStore.header_subStorePanelOnLeft),"Selected <b>Sub-Store</b> on store left panel");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.button_addSubStore, 6);
            sSubStoreName= selectSubStore("Inactive");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_SubStore.button_editSubStore,10);
            bEnableButtonFound = Cls_Generic_Methods.isElementDisplayed(oPage_SubStore.button_enableSubStore);
            m_assert.assertTrue(bEnableButtonFound, "Validated -->Inventory Sub Store Enable Access--> <b>Enable Sub-Store</b> button is displayed in Sub-Store");
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to validate Enable Access Sub-Store" + e);
        }
    }
    private String selectSubStore(String status) {
        String selectedSubStore = "";

        try {

            List<String> subStoreHeaderList = new ArrayList<String>();

            for (WebElement headerList : oPage_SubStore.list_subStoreHeaderList) {
                subStoreHeaderList.add(Cls_Generic_Methods.getTextInElement(headerList));
            }
            for (WebElement row : oPage_SubStore.list_rowCreatedSubStore) {

                if (Cls_Generic_Methods.isElementDisplayed(row)) {
                    List<WebElement> subStoreRow = row.findElements(By.xpath("./child::*"));
                    String subStoreName = Cls_Generic_Methods.getTextInElement(subStoreRow.get(subStoreHeaderList.indexOf("Name")));
                    String subStoreStatus = Cls_Generic_Methods.getTextInElement(subStoreRow.get(subStoreHeaderList.indexOf("Status")));

                    if (subStoreStatus.equals(status)) {
                        Cls_Generic_Methods.clickElement(row);
                        selectedSubStore = subStoreName;
                        break;
                    }
                }
            }

        } catch (Exception e) {
            m_assert.assertFatal("Unable to select Sub store " + e);
        }
        return selectedSubStore;
    }
}
