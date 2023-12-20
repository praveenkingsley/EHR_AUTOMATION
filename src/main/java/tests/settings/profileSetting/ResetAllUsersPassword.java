package tests.settings.profileSetting;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.settings.profileSetting.Page_PasswordResetPage;

import java.time.LocalDate;
import java.util.List;

public class ResetAllUsersPassword extends TestBase {

    String adminUser = EHR_Data.user_PRAkashTest;

    //PASSWORD FORMAT = [sPasswordPrefix] + [MM] + [$] + [YY] + [$]
    static final String sPasswordPrefix = "F0#sSpRd";


    //This method will reset all the active user's password
    @Test
    public void resetUserPassword() {

        String password = generatePassword();

        Page_Navbar oPage_Navbar = new Page_Navbar(driver);
        Page_OrganisationSetup oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
        Page_PasswordResetPage oPage_PasswordResetPage = new Page_PasswordResetPage(driver);

        int activeUserCount = 0;
        int passwordUpdatedUserCount = 0;

        try {
            CommonActions.loginFunctionality(adminUser);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);

            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 16);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUsers);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);

            int sizeUserName = oPage_OrganisationSetup.list_userName.size();
            List<WebElement> currentUserList = oPage_OrganisationSetup.list_emailUserName;

            m_assert.assertInfo("<b>NEW PASSWORD : <span style='background-color: #FFFF00'>" + password + "</span></b>");

            int i = -1;
            while (true) {
                i++;
                boolean activeUser = isUserActive(currentUserList, i);

                if (activeUser) {

                    activeUserCount++;
                    String username = Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.list_userName.get(i));
                    username = username.split("\n")[0].trim();

                    Cls_Generic_Methods.clickElementByJS(driver, currentUserList.get(i).findElement(By.xpath("./following-sibling::td//a[contains(@href,'reset_password')]")));
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_resetPassword, 10);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_newPasswordResetPassword, password);
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_confirmPasswordResetPassword, password);
                    Cls_Generic_Methods.customWait();
                    Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_resetPassword);
                    Cls_Generic_Methods.customWait(4);

                    boolean passwordUpdated = true;

                    if (Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 2)) {
                        Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_close);
                        m_assert.assertInfo("<font color='red'>RESET PASSWORD FAILED : </font>" + username);
                        passwordUpdated = false;
                    }

                    if (passwordUpdated) {
                        System.out.println("USER PASSWORD UPDATED --> "+username);
                        passwordUpdatedUserCount++;
                        m_assert.assertTrue("<font color='green'>USER PASSWORD UPDATED : </font>" + username);
                    }
                }

                if (i == sizeUserName - 1) {
                    if (!Cls_Generic_Methods.getElementAttribute(oPage_OrganisationSetup.button_nextManageUser, "class").contains("disabled")) {
                        Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_nextManageUser);
                        Cls_Generic_Methods.customWait(5);
                        currentUserList = oPage_OrganisationSetup.list_emailUserName;
                        sizeUserName = oPage_OrganisationSetup.list_userName.size();
                        i = -1;
                    } else {
                        break;
                    }
                }

            }

            boolean status = false;
            String sStatusCode;
            if (activeUserCount == passwordUpdatedUserCount) {
                status = true;
                sStatusCode = "<font color='green'>";
            } else {
                sStatusCode = "<font color='red'>";
            }

            m_assert.assertTrue(status, sStatusCode + "ACTIVE USERS : <b>" + activeUserCount + "</b> | PASSWORD UPDATED USERS : <b>" + passwordUpdatedUserCount + "</b></font>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generatePassword() {
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear() % 100;

        String formattedMonth = String.format("%02d", month);
        String formattedYear = String.format("%02d", year);

        //F0#sSQA
        return sPasswordPrefix + formattedMonth + "$" + formattedYear + "$";
    }
    private static boolean isUserActive(List<WebElement> eListOfUser, int index) {
        boolean activeUser = false;
        try {
            eListOfUser.get(index).findElement(By.xpath("./following-sibling::td//a[contains(@href,'reset_password')]"));
            activeUser = true;
        } catch (Exception ignored) {
        }
        return activeUser;
    }

}
