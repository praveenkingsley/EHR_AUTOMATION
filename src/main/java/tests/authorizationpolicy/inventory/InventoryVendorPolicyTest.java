package tests.authorizationpolicy.inventory;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.settings.organisationSettings.inventoryAndSupplyChain.Page_VendorMaster;
import pages.store.PharmacyStore.Page_Vendors;


public class InventoryVendorPolicyTest extends TestBase {

    String sPolicyRequired = "INVENTORY";
    String sAddPolicyComponent = "ADD SUPPLIER/VENDOR (VENDORS)";
    String sViewPolicyComponent = "VIEW (VENDORS)";
    String sDisableVendorPolicyComponent = "DISABLE VENDOR (VENDORS)";
    String sEditPolicyComponent = "EDIT (VENDORS)";
    String sEnableVendorPolicyComponent = "ENABLE VENDOR (VENDORS)";

    String sAddPolicyDescription = "Inventory Vendor Create access";
    String sViewPolicyDescription = "Inventory Vendor View access";
    String sDisableVendorPolicyDescription = "Inventory Vendor Disable access";
    String sEditPolicyDescription = "Inventory Vendor Edit access";
    String sEnableVendorPolicyDescription = "Inventory Vendor Enable access";

    String sPolicyUser = EHR_Data.user_PRAkashTest;
    String sStore = "Pharmacy automation- Pharmacy";

    static String sStatusInactive = "Inactive";
    static String sVendorName = CommonActions.getRandomString(5);



    @BeforeMethod
    private void init() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
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

    @Test(enabled = true, description = "Policy For Vendors View")
    public void inventoryPolicyForVendorsView() {
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        boolean bVendorsView = true;

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            bVendorsView = CommonActions.selectParentOptionFromLeftInInventoryStorePanel("Vendors");
            m_assert.assertFalse(bVendorsView, "<b> Vendors Can't be viewed. It's Disabled </b>");
            Cls_Generic_Methods.customWait(6);

            //enabling for PO View
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sViewPolicyComponent, sViewPolicyDescription);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            bVendorsView = CommonActions.selectParentOptionFromLeftInInventoryStorePanel("Vendors");
            m_assert.assertTrue(bVendorsView, "<b> Vendor Can be viewed. It's enabled </b>");

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Vendor Add")
    public void inventoryPolicyForVendorAdd() {
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Vendors oPage_Vendors = new Page_Vendors(driver);

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
            Cls_Generic_Methods.customWait();
            CommonActions.selectParentOptionFromLeftInInventoryStorePanel("Vendors");
            Cls_Generic_Methods.customWait(6);
            boolean bAddButtonNotFound = false;
            if (!Cls_Generic_Methods.isElementDisplayed(oPage_Vendors.button_addVendor)) {
                bAddButtonNotFound = true;
            }
            m_assert.assertTrue(bAddButtonNotFound, "<b> Add button in Vendor is disabled </b>");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sAddPolicyComponent, sAddPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectParentOptionFromLeftInInventoryStorePanel("Vendors");
            Cls_Generic_Methods.customWait(6);
            boolean bAddButtonFound = false;
            if (Cls_Generic_Methods.isElementDisplayed(oPage_Vendors.button_addVendor)) {
                bAddButtonFound = true;
            }
            m_assert.assertTrue(bAddButtonFound, "<b> Add button in Vendor is  Enabled </b> ");

            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Vendors Edit")
    public void inventoryPolicyForVendorsEdit() {
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Vendors oPage_Vendors = new Page_Vendors(driver);

        boolean bVendorFound = false;
        boolean bEditButtonNotFound = false;
        boolean bEditButtonFound = false;
        boolean bVendorCreated = false;

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEditPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectParentOptionFromLeftInInventoryStorePanel("Vendors");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Vendors.button_addVendor, 8);
            bVendorFound = selectStatusOfVendor();
            if (!bVendorFound) {
                bVendorCreated = createVendor();
                bVendorFound = selectStatusOfVendor();
            }

            if (bVendorFound) {
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_Vendors.button_editVendor)) {
                    bEditButtonNotFound = true;
                }
                m_assert.assertTrue(bEditButtonNotFound, "<b> Edit button in Vendor is disabled </b> ");
            }

            //enabling for vendor edit
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            if (bVendorCreated) {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 16);

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUsers), "clicked on All users");
            }
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEditPolicyComponent, sEditPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectParentOptionFromLeftInInventoryStorePanel("Vendors");
            Cls_Generic_Methods.customWait();

            bVendorFound = selectStatusOfVendor();
            if (!bVendorFound) {
                createVendor();
                bVendorFound = selectStatusOfVendor();
            }

            if (bVendorFound) {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Vendors.button_editVendor, 6);
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Vendors.button_editVendor)) {
                    bEditButtonFound = true;
                }
                m_assert.assertTrue(bEditButtonFound, "<b> Edit button in Vendor is enabled </b> ");
            }

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Vendors Disable")
    public void inventoryPolicyForVendorsDisable() {
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Vendors oPage_Vendors = new Page_Vendors(driver);

        boolean bVendorFound = false;
        boolean bDisableButtonNotFound = false;
        boolean bDisableButtonFound = false;

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sDisableVendorPolicyComponent, sDisableVendorPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectParentOptionFromLeftInInventoryStorePanel("Vendors");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Vendors.button_addVendor, 8);
            bVendorFound = selectStatusOfVendor();

            if (bVendorFound) {
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_Vendors.button_disableVendor)) {
                    bDisableButtonNotFound = true;
                }
                m_assert.assertTrue(bDisableButtonNotFound, "<b> Disable button in Vendor is disabled </b> ");
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();

            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sDisableVendorPolicyComponent, sDisableVendorPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectParentOptionFromLeftInInventoryStorePanel("Vendors");
            Cls_Generic_Methods.customWait();

            bVendorFound = selectStatusOfVendor();

            if (bVendorFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Vendors.button_disableVendor)) {
                    bDisableButtonFound = true;
                    Cls_Generic_Methods.clickElement(oPage_Vendors.button_disableVendor);
                    Cls_Generic_Methods.customWait();
                }
                m_assert.assertTrue(bDisableButtonFound, "<b> Disable button in Vendor is enabled </b> ");
            }

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    @Test(enabled = true, description = "Policy For Vendors Enable")
    public void inventoryPolicyForVendorsEnable() {
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Vendors oPage_Vendors = new Page_Vendors(driver);

        boolean bVendorFound = false;
        boolean bEnableButtonNotFound = false;
        boolean bEnableButtonFound = false;

        try {
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            CommonActions.disablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEnableVendorPolicyComponent, sEnableVendorPolicyDescription);
            Cls_Generic_Methods.customWait(3);
            //Opening store to validate the policy
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectParentOptionFromLeftInInventoryStorePanel("Vendors");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Vendors.button_addVendor, 8);

            bVendorFound = selectStatusOfVendor(sStatusInactive);

            if (bVendorFound) {
                if (!Cls_Generic_Methods.isElementDisplayed(oPage_Vendors.button_enableVendor)) {
                    bEnableButtonNotFound = true;
                }
                m_assert.assertTrue(bEnableButtonNotFound, "<b> Enable button in Vendor is disabled </b> ");
            }


            //enabling for enable vendor
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.switchToOtherTab();
            CommonActions.enablePolicyForTheUser(sPolicyUser, sPolicyRequired, sEnableVendorPolicyComponent, sEnableVendorPolicyDescription);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.driverRefresh();
            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            Cls_Generic_Methods.customWait();
            CommonActions.selectParentOptionFromLeftInInventoryStorePanel("Vendors");
            Cls_Generic_Methods.customWait();

            bVendorFound = selectStatusOfVendor(sStatusInactive);

            if (bVendorFound) {
                if (Cls_Generic_Methods.isElementDisplayed(oPage_Vendors.button_enableVendor)) {
                    bEnableButtonFound = true;
                    Cls_Generic_Methods.clickElement(oPage_Vendors.button_enableVendor);
                    Cls_Generic_Methods.customWait();
                }
                m_assert.assertTrue(bEnableButtonFound, "<b> Enable button in Vendor is enabled </b> ");
            }

            Cls_Generic_Methods.customWait(6);
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }

    }

    public static boolean selectStatusOfVendor(String... status) {
        boolean bVendorFound = false;
        Page_Vendors oPage_Vendors = new Page_Vendors(driver);
        String sStatus = "Active";
        try {
            if (status.length > 0) {
                sStatus = status[0];
            }
            for (WebElement eStatus : oPage_Vendors.list_statusOfVendors) {
                if (eStatus.getText().equals(sStatus)) {
                    bVendorFound = true;
                    Cls_Generic_Methods.clickElement(eStatus);
                    Cls_Generic_Methods.customWait(5);
                    break;
                }
            }
            if (bVendorFound) {
                m_assert.assertTrue(bVendorFound, "Vendor Found with status: " + sStatus);
            } else {
                m_assert.assertFalse(bVendorFound, "Vendor not Found with status: " + sStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("Unable to find vendor " + e);
        }
        return bVendorFound;
    }

    //creating vendor to test vendor policies if any vendor is not created
    public static boolean createVendor() {
        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_CommonElements oPage_CommonElements = new Page_CommonElements(driver);
        Page_Vendors oPage_Vendors = new Page_Vendors(driver);
        Page_VendorMaster oPage_VendorMaster = new Page_VendorMaster(driver);
        String selectedCategoryName = "LNPL Medication";
        String sCountry = "India (IN) ";
        String sVendorType = " Vendor Group";
        String sAddress = "vendor address";
        String sMobileNumber = "1234567891";
        boolean bVendorCreated = false;

        try {
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                    "Store pop up closed");
            CommonActions.selectParentOptionFromLeftInInventoryStorePanel("Vendors");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Vendors.button_addVendor, 6);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Vendors.button_addVendor),
                    "Clicked on add vendor");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Vendors.input_fullName, 6);
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Vendors.input_fullName, sVendorName),
                    "Vendor Name entered: " + sVendorName);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Vendors.dropdown_vendorType, sVendorType),
                    "Vendor type selected: <b>" + sVendorType + "</b>");
            Cls_Generic_Methods.customWait();
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Vendors.input_mobileNumber, sMobileNumber),
                    "Mobile number entered: " + sMobileNumber);
            m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_Vendors.dropdown_country, sCountry),
                    "Country selected: <b> " + sCountry + "</b>");
            m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_Vendors.input_address, sAddress),
                    "Address entered: " + sAddress);
            Cls_Generic_Methods.clickElement(oPage_Vendors.button_saveVendor);
            Cls_Generic_Methods.customWait(5);
            Cls_Generic_Methods.switchToOtherTab();

            //in vendor master verify and link category to the created vendor
            try {
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("Inventory & Supply Chain", "Vendor Master");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_addVendorButton, 6);
                m_assert.assertTrue(Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_searchVendorNameBox, sVendorName),
                        " Vendor Enter In search Box as <b> " + sVendorName + "</b>");
                Cls_Generic_Methods.customWait(2);
                boolean clickOnLinkCategoryActions = CommonActions.getActionToPerformInInventorySetting(oPage_VendorMaster.list_vendorNameForVendorMaster,
                        sVendorName, oPage_VendorMaster.list_vendorActionsForVendorName, "Link Category");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.header_linkCategoryTemplateTitle, 2);
                m_assert.assertTrue(clickOnLinkCategoryActions, "Link Category Button is Clicked in Vendor Master List");

                if (clickOnLinkCategoryActions) {
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.input_selectCategorySearchBox, 6);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.input_selectCategorySearchBox),
                            "Select Category Input Box Clicked");
                    Cls_Generic_Methods.customWait(1);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_VendorMaster.input_selectCategorySearchBox, selectedCategoryName);
                    for (WebElement categoryName : oPage_VendorMaster.list_selectCategoryValuesList) {
                        if (Cls_Generic_Methods.getTextInElement(categoryName).contains(selectedCategoryName)) {
                            m_assert.assertTrue(Cls_Generic_Methods.clickElement(categoryName),
                                    "Category clicked as : <b>" + selectedCategoryName + "</b>");
                            break;
                        }
                    }
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_saveLinkCategoryInVendorMaster, 1);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_VendorMaster.button_saveLinkCategoryInVendorMaster),
                            " Save button In Link Existing Category Clicked ");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_VendorMaster.button_addVendorButton, 5);


                    //verifying the vendor in store
                    Cls_Generic_Methods.switchToOtherTab();
                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 4);
                    m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving),
                            "Store pop up closed");
                    Cls_Generic_Methods.customWait();
                    CommonActions.selectParentOptionFromLeftInInventoryStorePanel("Vendors");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Vendors.button_addVendor, 8);
                    for (WebElement eVendorName : oPage_Vendors.list_nameOfVendors) {
                        if (eVendorName.getText().equalsIgnoreCase(sVendorName)) {
                            bVendorCreated = true;
                            Cls_Generic_Methods.clickElement(eVendorName);
                            break;
                        }
                    }
                    m_assert.assertTrue(bVendorCreated, "Vendor displaying under vendors in store");
                }
            } catch (Exception e) {
                e.printStackTrace();
                m_assert.assertFatal("" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
        return bVendorCreated;
    }

}
