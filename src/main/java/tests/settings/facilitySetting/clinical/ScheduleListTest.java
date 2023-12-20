package tests.settings.facilitySetting.clinical;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.dataModels.Model_Patient;
import data.EHR_Data;
import data.Settings_Data;
import data.settingsData.FacilitySettings_Data;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.ipd.Page_IPD;
import pages.settings.facilitySettings.clinical.scheduledAdmissionList.Page_ScheduledAdmissionList;

public class ScheduleListTest extends TestBase {

    static Model_Patient myPatient;
    String patientKey = Cls_Generic_Methods.getConfigValues("patientKeyUsed");
    EHR_Data oEHR_Data = new EHR_Data();
    String originalSelectValue = "-1", updatedSelectValue = "-1";
    String newOptionValue = "5";


    @Test(enabled = true, description = "Desc")
    public void validateScheduleListFunctionality() {

        Page_ScheduledAdmissionList oPage_ScheduledAdmissionList = new Page_ScheduledAdmissionList(driver);
        Page_IPD oPage_IPD = new Page_IPD(driver);
        String originalWindowHandle = null, newWindowHandle = null;
        String dateForIPD = null, updatedDateForIPD = null;
        WebElement eScheduledTabOnIPD = null;
        String sScheduledTabOnIPDText = null;

        try {
            myPatient = map_PatientsInExcel.get(patientKey);
            String expectedLoggedInUser = oEHR_Data.user_PRAkashTest;
            CommonActions.loginFunctionality(expectedLoggedInUser);

            try {
                CommonActions.selectOptionUnderSettings(Settings_Data.option_FACILITY_SETTING);
                Cls_Generic_Methods.customWait(1);
                CommonActions.selectOptionFromLeftInSettingsPanel("Clinical", "Scheduled Admission List");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ScheduledAdmissionList.select_ScheduledListToShowForLastDays, 8);

                originalSelectValue = Cls_Generic_Methods.getSelectedValue(oPage_ScheduledAdmissionList.select_ScheduledListToShowForLastDays);
                int differenceInIntegerFormat = Integer.parseInt(originalSelectValue);

                m_assert.assertInfo("Option Selected for Scheduled List to Show for Last Days = " + differenceInIntegerFormat);
                dateForIPD = Cls_Generic_Methods.getDifferenceInDays(Cls_Generic_Methods.getTodayDate(), - differenceInIntegerFormat);
                dateForIPD = CommonActions.dateFormatForIPDTab(dateForIPD).toUpperCase();

                originalWindowHandle = driver.getWindowHandle();
                Cls_Generic_Methods.switchToNewTab(originalWindowHandle);
                Cls_Generic_Methods.waitForPageLoad(driver, 8);
                newWindowHandle = driver.getWindowHandle();

                CommonActions.loginFunctionality(expectedLoggedInUser);
                CommonActions.selectDepartmentOnApp("IPD");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.tabs_TabsOnIPD.get(0), 8);

                eScheduledTabOnIPD = oPage_IPD.tabs_TabsOnIPD.get(2);
                sScheduledTabOnIPDText = Cls_Generic_Methods.getTextInElement(eScheduledTabOnIPD);

                m_assert.assertTrue(sScheduledTabOnIPDText.contains(dateForIPD),
                        "Validate that Scheduled List to Show for Last Days = " + originalSelectValue + " and IPD tab value contains " + dateForIPD);

                eScheduledTabOnIPD = null;
                sScheduledTabOnIPDText = null;

                Cls_Generic_Methods.switchToOtherTab();

                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ScheduledAdmissionList.select_ScheduledListToShowForLastDays, newOptionValue),
                        "Selected the Option <b>" + newOptionValue + "</b> for Scheduled List to Show for Last Days count");
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_ScheduledAdmissionList.button_Save),
                        "Clicked on Save Button");
                Cls_Generic_Methods.customWait(8);

                updatedSelectValue = Cls_Generic_Methods.getSelectedValue(oPage_ScheduledAdmissionList.select_ScheduledListToShowForLastDays);
                int updatedDifferenceInIntegerFormat = Integer.parseInt(updatedSelectValue);

                m_assert.assertInfo("Option Selected for Scheduled List after Update to Show for Scheduled List to Show for Last Days = "
                        + updatedDifferenceInIntegerFormat);
                updatedDateForIPD = Cls_Generic_Methods.getDifferenceInDays(Cls_Generic_Methods.getTodayDate(), - updatedDifferenceInIntegerFormat);
                updatedDateForIPD = CommonActions.dateFormatForIPDTab(updatedDateForIPD).toUpperCase();

                Cls_Generic_Methods.switchToOtherTab();
                Cls_Generic_Methods.driverRefresh();
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_IPD.tabs_TabsOnIPD.get(0), 8);

                eScheduledTabOnIPD = oPage_IPD.tabs_TabsOnIPD.get(2);
                sScheduledTabOnIPDText = Cls_Generic_Methods.getTextInElement(eScheduledTabOnIPD);
                Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

                m_assert.assertTrue(sScheduledTabOnIPDText.contains(updatedDateForIPD),
                        "Validate that after Updating, Scheduled List to Show for Last Days = " + updatedSelectValue + " and IPD tab value contains " + updatedDateForIPD);

                Cls_Generic_Methods.switchToOtherTab();

                m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_ScheduledAdmissionList.select_ScheduledListToShowForLastDays, originalSelectValue),
                        "Reverting the value to <b>" + originalSelectValue + "</b> for Scheduled List to Show for Last Days count");
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_ScheduledAdmissionList.button_Save),
                        "Clicked on Save Button");

                Cls_Generic_Methods.customWait(8);

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