package tests.settings.profileSetting;


import com.codoid.products.exception.FilloException;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import com.healthgraph.SeleniumFramework.Util.SuiteUtil.SuiteUtil;
import data.EHR_Data;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Recordset;
import data.Settings_Data;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.navbar.Page_Navbar;
import pages.login.Page_ForgotPasswordPage;
import pages.login.Page_Login;
import pages.settings.organisationSettings.general.Page_OrganisationSetup;
import pages.settings.profileSetting.Page_PasswordResetPage;
import pages.settings.profileSetting.Page_ProfileSetting;

import java.io.File;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;

public class PeriodicPasswordResetTest extends TestBase {

    //Close TestData_PeriodicPasswordReset.xlsx .. Before running this script

    boolean auto_rakeBuild = true; //[auto_rakeBuild=false] --> If u want to run rake manually or If u want to run this script on PROD

    String admin_user = EHR_Data.user_PRAkashTest;
    String sPasswordResetInterval = "90";
    Page_PasswordResetPage oPage_PasswordResetPage;
    Page_Login oPage_Login;
    Page_Navbar oPage_Navbar;
    Page_OrganisationSetup oPage_OrganisationSetup;
    Page_ForgotPasswordPage oPage_ForgotPasswordPage;
    Page_ProfileSetting oPage_ProfileSetting;
    String sUpdatedPassword = "";
    String currentDateAndTime = "";
    String facilityName = "";

    //EXCEL COLUMN (*IF COLUMN HEADER IS CHANGED IN EXCEL ,MODIFY VALUE HERE*)
    String sUSERNAME = "USERNAME";
    String sPASSWORD = "PASSWORD";
    String sSECURITY_QUESTION = "SECURITY_QUESTION";
    String sSET_SECURITY_QUESTION = "SET_SECURITY_QUESTION";
    String sSECURITY_ANSWER = "SECURITY_ANSWER";
    String sNEW_PASSWORD = "NEW_PASSWORD";
    String sEXPIRE_IN_DAYS = "EXPIRE_IN_DAYS";
    String sEMAIL_ID = "EMAIL_ID";

    //EXCEL SHEET NAME
    String sVALID_PASSWORD = "VALID_PASSWORD";
    String sPASSWORD_ABOUT_TO_EXPIRE = "PASSWORD_ABOUT_TO_EXPIRE";
    String sPASSWORD_EXPIRED = "PASSWORD_EXPIRED";
    String sADMIT_RESET_PASSWORD = "ADMIT_RESET_PASSWORD";
    String sFORGOT_PASSWORD = "FORGOT_PASSWORD";
    String sNEW_USER = "NEW_USER";

    //EVENT
    String sEVENT_PASSWORD = "Password";
    String sEVENT_SECURITY_QUESTION = "Security question";

    //ENCRYPTED CODE TO RESET PASSWORD
    //2752671cb53244d9f60da07c5f6528aabe0690812b77a66e8219cc88871add33

    //https://mail.tutanota.com/login

    private void autoRakeDeployment(ArrayList<HashMap<String, String>> allUserDetails, String condition) {

        String jenkins_url = "https://non-prod-jenkins.healthgraph.in/job/rake-deployment/";
        String jenkins_username = "praveen.kingsley@healthgraph.in";
        String jenkins_password = "praveen@jenkins";

        oPage_PasswordResetPage = new Page_PasswordResetPage(driver);

        String finalRakeCommand = "";

        try {


            if (condition.equals(sVALID_PASSWORD)) {

                StringBuilder rakeCommand = new StringBuilder("bundle exec rake rake_for_all:show_data\\['@user = User.where(:username.in => [");

                for (int i = 0; i < allUserDetails.size(); i++) {
                    rakeCommand.append("\\\"").append(allUserDetails.get(i).get(sUSERNAME)).append("\\\"");
                    if (i < allUserDetails.size() - 1) {
                        rakeCommand.append("\\,");
                    }
                }
                rakeCommand.append("]); @user.each do |a| a.future_password_reset_date = DateTime.now + 10; a.save; end'\\]");
                finalRakeCommand = rakeCommand.toString();

                System.out.println(finalRakeCommand);
            } else if (condition.equals(sPASSWORD_ABOUT_TO_EXPIRE)) {

                StringBuilder rakeCommand = new StringBuilder("bundle exec rake rake_for_all:show_data\\['@user = User.where(:username.in =>[");

                for (int i = 0; i < allUserDetails.size(); i++) {
                    rakeCommand.append("\\\"").append(allUserDetails.get(i).get(sUSERNAME)).append("\\\"");
                    if (i < allUserDetails.size() - 1) {
                        rakeCommand.append("\\,");
                    }
                }
                rakeCommand.append("]); @user.each do |a| a.future_password_reset_date = DateTime.now + 5; a.save; end'\\]");
                finalRakeCommand = rakeCommand.toString();
                System.out.println(finalRakeCommand);

                //If security Question is not selected


                StringBuilder securityRakeCommand = new StringBuilder("bundle exec rake rake_for_all:show_data\\['@user = User.where(:username.in =>[");


                int userAdded = 0;
                boolean securityRake = false;
                for (HashMap<String, String> userDetail : allUserDetails) {

                    if (userDetail.get(sSECURITY_QUESTION).isBlank()) {
                        securityRake = true;

                        if (userAdded != 0) {
                            securityRakeCommand.append("\\,");
                        }

                        securityRakeCommand.append("\\\"").append(userDetail.get(sUSERNAME)).append("\\\"");
                        userAdded++;
                    }
                }
                securityRakeCommand.append("]); @user.each do |a| a.security_question_code = nil; a.save; end'\\]");

                if (securityRake) {
                    finalRakeCommand = finalRakeCommand + "\n" + securityRakeCommand;
                    System.out.println(finalRakeCommand);
                }


            } else if (condition.equals(sPASSWORD_EXPIRED) || condition.equals(sADMIT_RESET_PASSWORD) || condition.equals(sFORGOT_PASSWORD)) {
                StringBuilder rakeCommand = new StringBuilder("bundle exec rake rake_for_all:show_data\\['@user = User.where(:username.in =>[");

                for (int i = 0; i < allUserDetails.size(); i++) {
                    rakeCommand.append("\\\"").append(allUserDetails.get(i).get(sUSERNAME)).append("\\\"");
                    if (i < allUserDetails.size() - 1) {
                        rakeCommand.append("\\,");
                    }
                }
                rakeCommand.append("]); @user.each do |a| a.future_password_reset_date = DateTime.now - 1; a.save; end'\\]");
                finalRakeCommand = rakeCommand.toString();
                System.out.println(finalRakeCommand);

                //If security Question is not selected
                StringBuilder securityRakeCommand = new StringBuilder("bundle exec rake rake_for_all:show_data\\['@user = User.where(:username.in => [");

                int userAdded = 0;
                boolean securityRake = false;
                for (HashMap<String, String> userDetail : allUserDetails) {

                    if (userDetail.get(sSECURITY_QUESTION).isBlank()) {
                        securityRake = true;

                        if (userAdded != 0) {
                            securityRakeCommand.append("\\,");
                        }

                        securityRakeCommand.append("\\\"").append(userDetail.get(sUSERNAME)).append("\\\"");
                        userAdded++;
                    }
                }
                securityRakeCommand.append("]); @user.each do |a| a.security_question_code = nil; a.save; end'\\]");

                if (securityRake) {
                    finalRakeCommand = finalRakeCommand + "\n" + securityRakeCommand;
                    System.out.println(finalRakeCommand);
                }
            }


            Cls_Generic_Methods.getURL(driver, jenkins_url);
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            boolean loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.jenkins_button_signIn, 5);
            if (loginStatus) {
                Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.jenkins_input_username, jenkins_username);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.jenkins_input_password, jenkins_password);
                Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.jenkins_button_signIn);
            }


            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.jenkins_link_buildWithParameter, 10);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_PasswordResetPage.jenkins_link_buildWithParameter);

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.jenkins_input_rake, 10);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.jenkins_input_rake, finalRakeCommand);

            if (!env.toUpperCase().equals("PROD")) {
                Cls_Generic_Methods.selectElementByValue(oPage_PasswordResetPage.jenkins_select_env, env.toUpperCase());
            }
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.jenkins_build);
            Cls_Generic_Methods.customWait(60);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validating Whether Reset Password is not displayed for the user whose Password is valid")
    public void validateLoginIfPasswordIsNotExpired() {

        oPage_PasswordResetPage = new Page_PasswordResetPage(driver);
        oPage_Login = new Page_Login(driver);
        oPage_ProfileSetting = new Page_ProfileSetting(driver);
        oPage_Navbar = new Page_Navbar(driver);
        try {

            ArrayList<HashMap<String, String>> allUserDetails = getUserDataFromExcel(sVALID_PASSWORD);

            if (auto_rakeBuild) {
                autoRakeDeployment(allUserDetails, sVALID_PASSWORD);
            }

            for (HashMap<String, String> userDetail : allUserDetails) {

                m_assert.assertInfo("<b><font color='green'> TEST " + userDetail.get("SNO") + " ---> USER ID : " + userDetail.get(sUSERNAME) + "</font></b>");
                boolean loginBtnClicked = login(userDetail, false);

                if (loginBtnClicked) {
                    boolean loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                    m_assert.assertInfo(loginStatus, "User redirected to homepage");

                    if (!loginStatus) {
                        continue;
                    }

                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);

                    String currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser).split("\\n")[1].split(":")[1].trim();

                    boolean validUser = currentLoggedUserName.equals(userDetail.get(sUSERNAME));
                    m_assert.assertTrue(validUser, "Login successful -> Logged In User = <b>" + currentLoggedUserName + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_profileSetting, 5);

                    //Navigating to Profile Setting > Login and Security
                    Cls_Generic_Methods.clickElement(driver, oPage_ProfileSetting.link_profileSetting);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_loginAndSecurity, 5);
                    Cls_Generic_Methods.clickElement(oPage_ProfileSetting.link_loginAndSecurity);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_headerLoginAndSecurity, 5);

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_auditTrails), "Clicked Login And Security > Audit Trails");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_passwordExpireOn, 5);

                    String txt_passwordExpireOn = Cls_Generic_Methods.getTextInElement(oPage_ProfileSetting.text_passwordExpireOn).split("expire on")[1].trim();

                    boolean validPassword = isDateTimeInFuture(txt_passwordExpireOn);
                    int daysDifference = calculateDaysDifference(txt_passwordExpireOn);

                    m_assert.assertTrue(validPassword && daysDifference >= 5, "Validated --> User : " + currentLoggedUserName + "'s Password will Expiry on " + txt_passwordExpireOn);

                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_logout);
                    Cls_Generic_Methods.waitForPageLoad(driver, 12);
                }


            }


        } catch (Exception e) {
            m_assert.assertFatal(" " + e);
            e.printStackTrace();
        }

    }

    @Test(enabled = true, description = "Validating Whether Reset Password is displayed for the user whose Password is about to expire in 5days")
    public void validatePasswordAboutToExpire() {

        try {
            oPage_PasswordResetPage = new Page_PasswordResetPage(driver);
            oPage_Login = new Page_Login(driver);
            oPage_ProfileSetting = new Page_ProfileSetting(driver);
            oPage_Navbar = new Page_Navbar(driver);

            ArrayList<HashMap<String, String>> allUserDetails = getUserDataFromExcel(sPASSWORD_ABOUT_TO_EXPIRE);

            if (auto_rakeBuild) {
                autoRakeDeployment(allUserDetails, sPASSWORD_ABOUT_TO_EXPIRE);
            }

            for (HashMap<String, String> userDetail : allUserDetails) {

                m_assert.assertInfo("<b><font color='green'> TEST " + userDetail.get("SNO") + " ---> USER ID : " + userDetail.get(sUSERNAME) + "</font></b>");

                boolean loginBtnClicked = login(userDetail);

                if (loginBtnClicked) {
                    boolean loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_createNewPassword, 7);
                    m_assert.assertInfo(loginStatus, "User redirected to Password Reset page");

                    if (!loginStatus) {
                        continue;
                    }

                    //Validate Back To Login
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.link_backToLogin), "Clicked Back To Login Button");
                    boolean loginPageDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                    if (loginPageDisplayed) {
                        m_assert.assertTrue("Validated Back to Login -> User redirected to Login Page");
                        loginBtnClicked = login(userDetail, false);
                        if (loginBtnClicked) {
                            loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_createNewPassword, 7);
                            if (!loginStatus) {
                                continue;
                            }
                        } else {
                            m_assert.assertFatal("Unable to Login");
                            continue;
                        }
                    } else {
                        m_assert.assertFalse("Unable to validate Back to Login -> User is not navigated to Login Page");
                    }

                    //validate header
                    String text_passwordExpiryIn = Cls_Generic_Methods.getTextInElement(oPage_PasswordResetPage.text_headerMsg);
                    try {
                        text_passwordExpiryIn = Cls_Generic_Methods.getTextInElement(oPage_PasswordResetPage.text_headerMsg).split("days")[0].split("expire in")[1].trim();
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }

                    m_assert.assertTrue(text_passwordExpiryIn.equals(userDetail.get(sEXPIRE_IN_DAYS)), "Validated Displayed Header -> Password will expire in " + text_passwordExpiryIn + " Days");
                    Cls_Generic_Methods.customWait();

                    //Validate username
                    String text_username = Cls_Generic_Methods.getElementAttribute(oPage_PasswordResetPage.input_username, "value");
                    m_assert.assertTrue(text_username.equals(userDetail.get(sUSERNAME)), "Validated Displayed Username -> " + text_username);

                    //Validate Security question is selected
                    String selectedSecurityQuestion = Cls_Generic_Methods.getSelectedValue(oPage_PasswordResetPage.select_securityQuestion);

                    if (selectedSecurityQuestion.contains("Select")) {
                        m_assert.assertTrue(userDetail.get(sSECURITY_QUESTION).isBlank(), "Validated -> Security Question is not set for the user");
                    } else {
                        m_assert.assertTrue(selectedSecurityQuestion.equals(userDetail.get(sSECURITY_QUESTION)), "Validated ->Selected Security Question->" + selectedSecurityQuestion);
                    }

                    //Click Proceed for Now
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_proceedForNow), "Clicked <b>Proceed For Now</b> button");

                    boolean redirectedToHomepage = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                    m_assert.assertTrue(redirectedToHomepage, "Validated Proceed for Now -> User redirected to homepage");

                    //valid username
                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                    String currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser).split("\\n")[1].split(":")[1].trim();

                    boolean validUser = currentLoggedUserName.equals(userDetail.get(sUSERNAME));
                    m_assert.assertTrue(validUser, "Logged In User = <b>" + currentLoggedUserName + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_profileSetting, 5);

                    //Navigating to Profile Setting > Login and Security
                    Cls_Generic_Methods.clickElement(driver, oPage_ProfileSetting.link_profileSetting);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_loginAndSecurity, 5);
                    Cls_Generic_Methods.clickElement(oPage_ProfileSetting.link_loginAndSecurity);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_headerLoginAndSecurity, 5);

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_auditTrails), "Clicked Login And Security > Audit Trails");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_passwordExpireOn, 5);

                    // Checking displayed password expiry date in audit trial
                    String txt_passwordExpireOn = Cls_Generic_Methods.getTextInElement(oPage_ProfileSetting.text_passwordExpireOn).split("expire on")[1].trim();
                    boolean validPassword = Integer.parseInt(userDetail.get(sEXPIRE_IN_DAYS)) == calculateDaysDifference(txt_passwordExpireOn);
                    m_assert.assertTrue(validPassword, "Validated --> User : " + currentLoggedUserName + "'s Password will Expiry on " + txt_passwordExpireOn + " [AFTER " + calculateDaysDifference(txt_passwordExpireOn) + " DAYS]");

                    //Set Security Question
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_ProfileSetting.tab_securityQuestion), "Clicked Security Questions Tab");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.button_editSecurityQuestion, 10);

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.button_editSecurityQuestion), "Clicked Edit Security Question");
                    Cls_Generic_Methods.customWait();
                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_ProfileSetting.select_securityQuestionSetSQTab, userDetail.get(sSET_SECURITY_QUESTION)),
                            "Selected <b>" + userDetail.get(sSET_SECURITY_QUESTION) + "</b> in Security Question Dropdown");

                    Cls_Generic_Methods.customWait(1);
                    Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_securityAnswerSetSQTab);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_securityAnswerSetSQTab, userDetail.get(sSECURITY_ANSWER)), "Entered " + userDetail.get(sSECURITY_ANSWER) + " in Security Answer");

                    Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_passwordSetSQTab);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_passwordSetSQTab, userDetail.get(sPASSWORD)), "Entered " + userDetail.get(sPASSWORD) + " in Password");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.button_saveChangesSetSQTab), "Clicked Save Security Question");
                    currentDateAndTime = getCurrentDateFormatted();

                    //Check Log
                    Cls_Generic_Methods.driverRefresh();
                    checkLogInAuditTrails(sEVENT_SECURITY_QUESTION, currentDateAndTime, true);

                    //Logout
                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_logout);
                    Cls_Generic_Methods.waitForPageLoad(driver, 12);
                }

                //Login again to reset the password
                loginBtnClicked = login(userDetail, false);

                if (loginBtnClicked) {
                    boolean loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_createNewPassword, 7);
                    m_assert.assertInfo(loginStatus, "User redirected to Password Reset page");

                    if (!loginStatus) {
                        continue;
                    }

                    //Validate Security question is selected
                    String selectedSecurityQuestion = Cls_Generic_Methods.getSelectedValue(oPage_PasswordResetPage.select_securityQuestion);

                    if (selectedSecurityQuestion.contains("Select")) {
                        m_assert.assertFatal("Newly set Security Question is not selected in Password Reset Page");
                        continue;
                    } else {
                        m_assert.assertTrue(selectedSecurityQuestion.equals(userDetail.get(sSET_SECURITY_QUESTION)), "Validated ->Newly Set Security Question is auto-selected in Reset Page->" + selectedSecurityQuestion);
                    }

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_currentPassword);

                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password Button without Filling current password or security answer");
                    boolean notifyMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 10);
                    m_assert.assertTrue(notifyMsg, "Error Notify message is displayed");

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_newPassword, "ZXAZPOA"), "Entered Invalid format in New Password [Which doesn't meet the requirement]");
                    Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.input_confirmNewPassword);
                    boolean errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorMsgNewPassword, 3);
                    m_assert.assertTrue(errorMsg, "Error message is displayed");

                    sUpdatedPassword = generateRandomPassword(9);
                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_newPassword);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_newPassword, sUpdatedPassword), "Entered " + sUpdatedPassword + " in New Password ");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password Button without filling Confirm Password");
                    errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                    m_assert.assertTrue(errorMsg, "Error message is displayed");

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmNewPassword, userDetail.get(sPASSWORD)), "Entered different Password in Confirm Password [NEW PASSWORD != CONFIRM PASSWORD] ");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password");
                    errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                    m_assert.assertTrue(errorMsg, "Error Notify message is displayed");

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_confirmNewPassword);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmNewPassword, sUpdatedPassword), "Entered Valid Password in Confirm Password [NEW PASSWORD == CONFIRM PASSWORD] ");

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_currentPassword);
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, "ZXAZPOA"), "Entered invalid value in Security Answer");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password ");
                    errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                    m_assert.assertTrue(errorMsg, "Error Notify message is displayed");

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, userDetail.get(sSECURITY_ANSWER)), "Entered <b>" + userDetail.get(sSECURITY_ANSWER) + "</b> in Security Answer");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password");
                    currentDateAndTime = getCurrentDateFormatted();

                    //User redirected to Login page
                    boolean loginPageDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                    if (loginPageDisplayed) {

                        m_assert.assertTrue("New Password Created Successfully -> User redirected to Login Page");

                        //Try to Log in with Old Password
                        loginBtnClicked = login(userDetail, false);
                        if (loginBtnClicked) {
                            loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                            m_assert.assertFalse(loginStatus, "Validated -> User unable to Login with Old Password");
                            if (loginStatus) {
                                continue;
                            }
                        }
                    } else {
                        m_assert.assertFatal("Unable to create New Password / User is not navigated to Login Page");
                    }

                    //Try to Log in with New Password
                    loginBtnClicked = login(userDetail, true);
                    if (loginBtnClicked) {
                        loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);

                        if (loginStatus) {

                            m_assert.assertTrue("Validated -> User able to Login with New Password");
                            //valid username
                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                            String currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser).split("\\n")[1].split(":")[1].trim();

                            boolean validUser = currentLoggedUserName.equals(userDetail.get(sUSERNAME));
                            m_assert.assertTrue(validUser, "Logged In User = <b>" + currentLoggedUserName + "</b>");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_profileSetting, 5);

                            //Navigating to Profile Setting > Login and Security
                            Cls_Generic_Methods.clickElement(driver, oPage_ProfileSetting.link_profileSetting);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_loginAndSecurity, 5);
                            Cls_Generic_Methods.clickElement(oPage_ProfileSetting.link_loginAndSecurity);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_headerLoginAndSecurity, 5);

                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_auditTrails), "Clicked Login And Security > Audit Trails");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_passwordExpireOn, 5);

                            // Checking displayed password expiry date in audit trial
                            String txt_passwordExpireOn = Cls_Generic_Methods.getTextInElement(oPage_ProfileSetting.text_passwordExpireOn).split("expire on")[1].trim();
                            boolean validPassword = Integer.parseInt(sPasswordResetInterval) == calculateDaysDifference(txt_passwordExpireOn);
                            m_assert.assertTrue(validPassword, "Validated Create New Password --> User : " + currentLoggedUserName + "'s Password will Expiry on " + txt_passwordExpireOn + " [AFTER " + calculateDaysDifference(txt_passwordExpireOn) + " DAYS]");

                            //Check Audit Trails
                            checkLogInAuditTrails(sEVENT_PASSWORD, currentDateAndTime, false);

                            //Change Password From Profile setting
                            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_ProfileSetting.tab_changePassword), "Clicked Change Password Tab");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.input_userNameCPTab, 10);

                            String txt_userName = Cls_Generic_Methods.getElementAttribute(oPage_ProfileSetting.input_userNameCPTab, "value");
                            m_assert.assertTrue(txt_userName.equals(userDetail.get(sUSERNAME)), "Validated ->Displayed Username : " + txt_userName);

                            //Validate Security question is selected
                            selectedSecurityQuestion = Cls_Generic_Methods.getSelectedValue(oPage_ProfileSetting.select_securityQuestionCPTab);
                            m_assert.assertTrue(selectedSecurityQuestion.equals(userDetail.get(sSET_SECURITY_QUESTION)), "Validated ->Newly Set Security Question is auto-selected in Reset Page->" + selectedSecurityQuestion);


                            Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_currentPasswordCPTab);

                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.button_changePasswordCPTab), "Clicked Change Password Button without Filling current password or security answer");
                            notifyMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 10);
                            m_assert.assertTrue(notifyMsg, "Error Notify message is displayed");

                            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_newPasswordCPTab, sNEW_PASSWORD), "Entered previously used Password in New Password");
                            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_confirmPasswordCPTab, sNEW_PASSWORD), "Entered previously used Password in Confirm Password");
                            Cls_Generic_Methods.clickElement(oPage_ProfileSetting.button_changePasswordCPTab);
                            errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                            m_assert.assertTrue(errorMsg, "Error Notify message is displayed");

                            sUpdatedPassword = generateRandomPassword(12);
                            updateExcel(sPASSWORD_ABOUT_TO_EXPIRE, sUSERNAME, userDetail.get(sUSERNAME), sNEW_PASSWORD, sUpdatedPassword);

                            Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_newPasswordCPTab);
                            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_newPasswordCPTab, sUpdatedPassword), "Entered " + sUpdatedPassword + " in New Password ");

                            Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_confirmPasswordCPTab);
                            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_confirmPasswordCPTab, sUpdatedPassword), "Entered Valid Password in Confirm Password ");

                            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_securityAnswerCPTab, "ZXAZPOA"), "Entered invalid value in Security Answer");
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.button_changePasswordCPTab), "Clicked Change Password ");
                            errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                            m_assert.assertTrue(errorMsg, "Error Notify message is displayed ");

                            Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_securityAnswerCPTab);

                            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_currentPasswordCPTab, "ZXAZPOA"), "Entered invalid value in Current Password");
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.button_changePasswordCPTab), "Clicked Change Password ");
                            errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                            m_assert.assertTrue(errorMsg, "Error Notify message is displayed");

                            Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_securityAnswerCPTab);
                            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_securityAnswerCPTab, userDetail.get(sSECURITY_ANSWER)), "Entered <b>" + userDetail.get(sSECURITY_ANSWER) + "</b> in Security Answer");
                            Cls_Generic_Methods.customWait(1);
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Change Password");
                            currentDateAndTime = getCurrentDateFormatted();

                            //User redirected to Login page
                            loginPageDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                            if (loginPageDisplayed) {

                                m_assert.assertTrue("New Password Created Successfully -> User redirected to Login Page");

                                //Try to Log in with Old Password [User can set previously set password for first time]
                                loginBtnClicked = login(userDetail, false);
                                if (loginBtnClicked) {
                                    loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                                    m_assert.assertFalse(loginStatus, "Validated -> User unable to Login with Old Password");
                                    if (loginStatus) {
                                        continue;
                                    }
                                }
                            } else {
                                m_assert.assertFatal("Unable to create New Password / User is not navigated to Login Page");
                            }


                            //Try to Log in with New Password Which User changed in Profile Setting
                            loginBtnClicked = login(userDetail, true);

                            if (loginBtnClicked) {
                                loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                                if (loginStatus) {
                                    m_assert.assertTrue("Validated -> User able to Login with New Password");

                                    //valid username
                                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                                    currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser).split("\\n")[1].split(":")[1].trim();

                                    validUser = currentLoggedUserName.equals(userDetail.get(sUSERNAME));
                                    m_assert.assertTrue(validUser, "Logged In User = <b>" + currentLoggedUserName + "</b>");
                                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_profileSetting, 5);
                                    checkLogInAuditTrails(sEVENT_PASSWORD, currentDateAndTime, true);
                                } else {
                                    m_assert.assertFalse("Validated -> User not able to Login with New Password");

                                }
                            }
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(enabled = true, description = "Validating Whether Reset Password is displayed for the user whose Password is expired")
    public void validatePasswordExpired() {

        try {
            oPage_PasswordResetPage = new Page_PasswordResetPage(driver);
            oPage_Login = new Page_Login(driver);
            oPage_ProfileSetting = new Page_ProfileSetting(driver);
            oPage_Navbar = new Page_Navbar(driver);

            ArrayList<HashMap<String, String>> allUserDetails = getUserDataFromExcel(sPASSWORD_EXPIRED);
            if (auto_rakeBuild) {
                autoRakeDeployment(allUserDetails, sPASSWORD_EXPIRED);
            }

            for (HashMap<String, String> userDetail : allUserDetails) {

                m_assert.assertInfo("<b><font color='green'> TEST " + userDetail.get("SNO") + " ---> USER ID : " + userDetail.get(sUSERNAME) + "</font></b>");

                boolean loginBtnClicked = login(userDetail);

                if (loginBtnClicked) {
                    boolean loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_createNewPassword, 7);
                    m_assert.assertInfo(loginStatus, "User redirected to Password Reset page");

                    if (!loginStatus) {
                        continue;
                    }

                    //Validate Back To Login
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.link_backToLogin), "Clicked Back To Login Button");
                    boolean loginPageDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                    if (loginPageDisplayed) {
                        m_assert.assertTrue("Validated Back to Login -> User redirected to Login Page");
                        loginBtnClicked = login(userDetail);
                        if (loginBtnClicked) {
                            loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_createNewPassword, 7);
                            if (!loginStatus) {
                                continue;
                            }
                        } else {
                            m_assert.assertFatal("Unable to Login");
                            continue;
                        }
                    } else {
                        m_assert.assertFalse("Unable to validate Back to Login -> User is not navigated to Login Page");
                    }

                    //validate header
                    String text_passwordExpiryIn = Cls_Generic_Methods.getTextInElement(oPage_PasswordResetPage.text_headerMsg);
                    m_assert.assertTrue(text_passwordExpiryIn.contains("expired"), "Validated Displayed Header -> User's Password has expired");
                    Cls_Generic_Methods.customWait();

                    //Validate username
                    String text_username = Cls_Generic_Methods.getElementAttribute(oPage_PasswordResetPage.input_username, "value");
                    m_assert.assertTrue(text_username.equals(userDetail.get(sUSERNAME)), "Validated Displayed Username -> " + text_username);

                    //Validate Security question is selected
                    String selectedSecurityQuestion = Cls_Generic_Methods.getSelectedValue(oPage_PasswordResetPage.select_securityQuestion);

                    if (selectedSecurityQuestion.contains("Select")) {
                        m_assert.assertTrue(userDetail.get(sSECURITY_QUESTION).isBlank(), "Validated -> Security Question is not set for the user");
                    } else {
                        m_assert.assertTrue(selectedSecurityQuestion.equals(userDetail.get(sSECURITY_QUESTION)), "Validated ->Selected Security Question->" + selectedSecurityQuestion);
                    }

                    //Click Proceed for Now
                    m_assert.assertFalse(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_proceedForNow, 1), "Validated -> <b>Proceed For Now</b> button is not displayed for Password expired user");

                    if (userDetail.get(sSECURITY_QUESTION).isBlank()) {
                        m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_PasswordResetPage.select_securityQuestion, userDetail.get(sSET_SECURITY_QUESTION)), "Selected " + userDetail.get(sSET_SECURITY_QUESTION) + " in security question");
                    } else {
                        //Clearing Current password for validation
                        Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_currentPassword);
                    }

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_newPassword, "ZXAZPOA"), "Entered Invalid format in New Password [Which doesn't meet the requirement]");
                    Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword);
                    boolean errorNotifyMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 5);
                    boolean errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorMsgNewPassword, 5);

                    m_assert.assertTrue(errorMsg || errorNotifyMsg, "Error message is displayed");


                    sUpdatedPassword = generateRandomPassword(8);
                    updateExcel(sPASSWORD_EXPIRED, sUSERNAME, userDetail.get(sUSERNAME), sNEW_PASSWORD, sUpdatedPassword);

                    //Checking without filling confirm password . It should not allow user to create new password
                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_newPassword);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_newPassword, sUpdatedPassword), "Entered " + sUpdatedPassword + " in New Password ");
                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, "xzapo");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password Button without filling Confirm Password");
                    errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                    m_assert.assertTrue(errorMsg, "Error message is displayed");

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmNewPassword, userDetail.get(sPASSWORD)), "Entered different Password in Confirm Password [NEW PASSWORD != CONFIRM PASSWORD] ");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password");
                    errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                    m_assert.assertTrue(errorMsg, "Error Notify message is displayed");

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_confirmNewPassword);
                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmNewPassword, sUpdatedPassword), "Entered Valid Password in Confirm Password [NEW PASSWORD == CONFIRM PASSWORD] ");

                    //If security question is already set , then check with invalid answer

                    if (!userDetail.get(sSECURITY_QUESTION).isBlank()) {
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, "ZXAZPOA"), "Entered invalid value in Security Answer");
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password ");
                        errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                        m_assert.assertTrue(errorMsg, "Error Notify message is displayed");
                    }
                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, userDetail.get(sSECURITY_ANSWER)), "Entered " + userDetail.get(sSECURITY_ANSWER) + " in Security Answer");

                    Cls_Generic_Methods.customWait(4);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password");
                    currentDateAndTime = getCurrentDateFormatted();

                    //User redirected to Login page
                    loginPageDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                    if (loginPageDisplayed) {

                        m_assert.assertTrue("New Password Created Successfully -> User redirected to Login Page");

                        //Try to Log in with Old Password
                        loginBtnClicked = login(userDetail, false);
                        if (loginBtnClicked) {
                            loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                            m_assert.assertFalse(loginStatus, "Validated -> User unable to Login with Old Password");
                            if (loginStatus) {
                                continue;
                            }
                        }
                    } else {
                        m_assert.assertFatal("Unable to create New Password / User is not navigated to Login Page");
                    }

                    //Try to Log in with New Password
                    loginBtnClicked = login(userDetail, true);
                    if (loginBtnClicked) {
                        loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                        m_assert.assertTrue("Validated -> User able to Login with New Password");
                        if (loginStatus) {

                            //valid username
                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                            String currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser).split("\\n")[1].split(":")[1].trim();

                            boolean validUser = currentLoggedUserName.equals(userDetail.get(sUSERNAME));
                            m_assert.assertTrue(validUser, "Logged In User = <b>" + currentLoggedUserName + "</b>");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_profileSetting, 5);

                            //Navigating to Profile Setting > Login and Security
                            Cls_Generic_Methods.clickElement(driver, oPage_ProfileSetting.link_profileSetting);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_loginAndSecurity, 5);
                            Cls_Generic_Methods.clickElement(oPage_ProfileSetting.link_loginAndSecurity);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_headerLoginAndSecurity, 5);

                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_auditTrails), "Clicked Login And Security > Audit Trails");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_passwordExpireOn, 5);

                            // Checking displayed password expiry date in audit trial
                            String txt_passwordExpireOn = Cls_Generic_Methods.getTextInElement(oPage_ProfileSetting.text_passwordExpireOn).split("expire on")[1].trim();
                            boolean validPassword = Integer.parseInt(sPasswordResetInterval) == calculateDaysDifference(txt_passwordExpireOn);
                            m_assert.assertTrue(validPassword, "Validated Create New Password --> User : " + currentLoggedUserName + "'s Password will Expiry on " + txt_passwordExpireOn + " [AFTER " + calculateDaysDifference(txt_passwordExpireOn) + " DAYS]");

                            //Check Audit Trails
                            checkLogInAuditTrails(sEVENT_PASSWORD, currentDateAndTime, false);

                            //Validate Selected Security Question is displayed in Profile setting
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_changePassword), "Clicked Change Password Tab");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.input_userNameCPTab, 10);

                            //Validate Security question is selected
                            selectedSecurityQuestion = Cls_Generic_Methods.getSelectedValue(oPage_ProfileSetting.select_securityQuestionCPTab);

                            if (!userDetail.get(sSECURITY_QUESTION).isBlank()) {
                                m_assert.assertTrue(selectedSecurityQuestion.equals(userDetail.get(sSECURITY_QUESTION)), "Validated -> Selected Security Question in Reset Page->" + selectedSecurityQuestion);
                            }else {
                                m_assert.assertTrue(selectedSecurityQuestion.equals(userDetail.get(sSET_SECURITY_QUESTION)), "Validated ->Newly Set Security Question is auto-selected in Reset Page->" + selectedSecurityQuestion);
                            }
                            //Set New Security Question
                            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_ProfileSetting.tab_securityQuestion), "Clicked Security Questions Tab");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.button_editSecurityQuestion, 10);

                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.button_editSecurityQuestion), "Clicked Edit Security Question");
                            Cls_Generic_Methods.customWait();

                            String sUpdatedSecurityQuestion = "What is the year of your high school graduation?";

                            m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_ProfileSetting.select_securityQuestionSetSQTab, sUpdatedSecurityQuestion),
                                    "Selected <b>" + sUpdatedSecurityQuestion + "</b> in Security Question Dropdown");

                            Cls_Generic_Methods.customWait(1);
                            Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_securityAnswerSetSQTab);
                            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_securityAnswerSetSQTab, userDetail.get(sSECURITY_ANSWER)), "Entered " + userDetail.get(sSECURITY_ANSWER) + " in Security Answer");

                            Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_passwordSetSQTab);
                            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_passwordSetSQTab, sUpdatedPassword), "Entered " + sUpdatedPassword + " in Password");
                            Cls_Generic_Methods.customWait();
                            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_ProfileSetting.button_saveChangesSetSQTab), "Clicked Save Security Question");
                            currentDateAndTime = getCurrentDateFormatted();

                            //Check Log
                            Cls_Generic_Methods.driverRefresh();
                            checkLogInAuditTrails(sEVENT_SECURITY_QUESTION, currentDateAndTime, true);

                            //Logout
                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_logout);
                            Cls_Generic_Methods.waitForPageLoad(driver, 12);

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validating Password reset by admin")
    public void validatePasswordResetByAdmin() {

        String user_newPassword = generateRandomPassword(8);
        try {
            oPage_PasswordResetPage = new Page_PasswordResetPage(driver);
            oPage_Login = new Page_Login(driver);
            oPage_ProfileSetting = new Page_ProfileSetting(driver);
            oPage_Navbar = new Page_Navbar(driver);
            oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

            ArrayList<HashMap<String, String>> allUserDetails = getUserDataFromExcel(sADMIT_RESET_PASSWORD);

            if (auto_rakeBuild) {
                autoRakeDeployment(allUserDetails, sADMIT_RESET_PASSWORD);
            }

            for (HashMap<String, String> userDetail : allUserDetails) {

                //Login With admin user
                m_assert.assertInfo(CommonActions.loginFunctionality(admin_user), "Logged in with Admin User");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
                facilityName = getFacilityName();

                CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
                Cls_Generic_Methods.customWait(3);
                CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUsers, 16);
                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_allUsers), "Clicked on All users");

                String sUserName = userDetail.get(sUSERNAME);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 16);
                Cls_Generic_Methods.clearValuesInElement(oPage_OrganisationSetup.input_searchUser);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_searchUser, sUserName);
                Cls_Generic_Methods.customWait(3);

                int sizeUserName = oPage_OrganisationSetup.list_userName.size();

                for (int i = 0; i < sizeUserName; i++) {
                    if (Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.list_emailUserName.get(i)).contains(sUserName)) {
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.list_emailUserName.get(i).findElement(By.xpath("./following-sibling::td//a[contains(@href,'reset_password')]"))), "Clicked Reset Password for user id : " + sUserName);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_resetPassword, 10);

                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_newPasswordResetPassword, user_newPassword), "Entered " + user_newPassword + " in New Password");
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_confirmPasswordResetPassword, user_newPassword), "Entered " + user_newPassword + " in Confirm Password");
                        Cls_Generic_Methods.customWait();
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_resetPassword), "Clicked Reset Password");
                        currentDateAndTime = getCurrentDateFormatted();
                        updateExcel(sADMIT_RESET_PASSWORD, sUSERNAME, sUserName, sPASSWORD, user_newPassword);
                        Cls_Generic_Methods.customWait(4);
                        break;
                    }
                }
            }

            allUserDetails = getUserDataFromExcel(sADMIT_RESET_PASSWORD);

            for (HashMap<String, String> userDetail : allUserDetails) {

                m_assert.assertInfo("<b><font color='green'> TEST " + userDetail.get("SNO") + " ---> USER ID : " + userDetail.get(sUSERNAME) + "</font></b>");

                boolean loginBtnClicked = login(userDetail);

                if (loginBtnClicked) {
                    if (!userDetail.get(sSECURITY_QUESTION).isBlank()) {
                        boolean loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                        m_assert.assertInfo(loginStatus, "User redirected to Home page [Security Question is already Set]");

                        //valid username
                        Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                        Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                        String currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser).split("\\n")[1].split(":")[1].trim();

                        boolean validUser = currentLoggedUserName.equals(userDetail.get(sUSERNAME));
                        m_assert.assertTrue(validUser, "Logged In User = <b>" + currentLoggedUserName + "</b>");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_profileSetting, 5);

                        //Navigating to Profile Setting > Login and Security
                        Cls_Generic_Methods.clickElement(driver, oPage_ProfileSetting.link_profileSetting);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_loginAndSecurity, 5);
                        Cls_Generic_Methods.clickElement(oPage_ProfileSetting.link_loginAndSecurity);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_headerLoginAndSecurity, 5);

                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_auditTrails), "Clicked Login And Security > Audit Trails");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_passwordExpireOn, 5);

                        // Checking displayed password expiry date in audit trial
                        String txt_passwordExpireOn = Cls_Generic_Methods.getTextInElement(oPage_ProfileSetting.text_passwordExpireOn).split("expire on")[1].trim();
                        boolean validPassword = Integer.parseInt(sPasswordResetInterval) == calculateDaysDifference(txt_passwordExpireOn);
                        m_assert.assertTrue(validPassword, "Validated Create New Password --> User : " + currentLoggedUserName + "'s Password will Expiry on " + txt_passwordExpireOn + " [AFTER " + calculateDaysDifference(txt_passwordExpireOn) + " DAYS]");

                        //Validate Selected Security Question is displayed in Profile setting
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_changePassword), "Clicked Change Password Tab");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.input_userNameCPTab, 10);

                        //Validate Security question is selected
                        String selectedSecurityQuestion = Cls_Generic_Methods.getSelectedValue(oPage_ProfileSetting.select_securityQuestionCPTab);
                        m_assert.assertTrue(selectedSecurityQuestion.equals(userDetail.get(sSET_SECURITY_QUESTION)), "Validated -> Security Question is selected in Profile Page->" + selectedSecurityQuestion);

                        //Validate Audit Trails (If Admin reset password)
                        checkLogInAuditTrails(sEVENT_PASSWORD, currentDateAndTime, true);
                        m_assert.assertTrue("Validated -> If User has already set the question . Then User is not redirected to Set Security Question / Reset Password Page");
                        continue;
                    }

                    boolean loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_createNewPassword, 7);
                    m_assert.assertInfo(loginStatus, "User redirected to Password Reset page [User has not set any security question]");

                    if (!loginStatus) {
                        continue;
                    }

                    //Validate Back To Login
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.link_backToLogin), "Clicked Back To Login Button");
                    boolean loginPageDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                    if (loginPageDisplayed) {
                        m_assert.assertTrue("Validated Back to Login -> User redirected to Login Page");
                        loginBtnClicked = login(userDetail);
                        if (loginBtnClicked) {
                            loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_createNewPassword, 7);
                            if (!loginStatus) {
                                continue;
                            }
                        } else {
                            m_assert.assertFatal("Unable to Login");
                            continue;
                        }
                    } else {
                        m_assert.assertFalse("Unable to validate Back to Login -> User is not navigated to Login Page");
                    }

                    //validate header
                    String text_passwordExpiryIn = Cls_Generic_Methods.getTextInElement(oPage_PasswordResetPage.text_headerMsg);

                    m_assert.assertTrue(text_passwordExpiryIn.contains("security question"), "Validated Displayed Header -> " + text_passwordExpiryIn);
                    Cls_Generic_Methods.customWait();

                    //Validate username
                    String text_username = Cls_Generic_Methods.getElementAttribute(oPage_PasswordResetPage.input_username, "value");
                    m_assert.assertTrue(text_username.equals(userDetail.get(sUSERNAME)), "Validated Displayed Username -> " + text_username);

                    //Validate Security question is selected
                    String selectedSecurityQuestion = Cls_Generic_Methods.getSelectedValue(oPage_PasswordResetPage.select_securityQuestion);

                    if (selectedSecurityQuestion.contains("Select")) {
                        m_assert.assertTrue(userDetail.get(sSECURITY_QUESTION).isBlank(), "Validated -> Security Question is not set for the user");
                    } else {
                        m_assert.assertFalse("If User has already set the security question .Then User should not redirected to Reset Page");
                        continue;
                    }

                    m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_PasswordResetPage.select_securityQuestion, userDetail.get(sSET_SECURITY_QUESTION)), "Selected " + userDetail.get(sSET_SECURITY_QUESTION) + " in security question");

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_newPassword, "ZXAZPOA"), "Entered Invalid format in New Password [Which doesn't meet the requirement]");
                    Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.input_confirmNewPassword);
                    boolean errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorMsgNewPassword, 3);
                    m_assert.assertTrue(errorMsg, "Error message is displayed");

                    //Checking without filling confirm password . It should not allow user to create new password

                    sUpdatedPassword = generateRandomPassword(10);

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_newPassword);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_newPassword, sUpdatedPassword), "Entered " + sUpdatedPassword + " in New Password ");

                    //Updating in excel
                    updateExcel(sADMIT_RESET_PASSWORD, sUSERNAME, userDetail.get(sUSERNAME), sNEW_PASSWORD, sUpdatedPassword);

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, "xzapo");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password Button without filling Confirm Password");
                    errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                    m_assert.assertTrue(errorMsg, "Error message is displayed");

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmNewPassword, userDetail.get(sPASSWORD)), "Entered different Password in Confirm Password [NEW PASSWORD != CONFIRM PASSWORD] ");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password");
                    errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                    m_assert.assertTrue(errorMsg, "Error Notify message is displayed");

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_confirmNewPassword);
                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmNewPassword, sUpdatedPassword), "Entered Valid Password in Confirm Password [NEW PASSWORD == CONFIRM PASSWORD] ");

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, userDetail.get(sSECURITY_ANSWER)), "Entered " + userDetail.get(sSECURITY_ANSWER) + " in Security Answer");

                    Cls_Generic_Methods.customWait(4);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password");
                    currentDateAndTime = getCurrentDateFormatted();

                    //User redirected to Login page
                    loginPageDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 20);

                    if (loginPageDisplayed) {

                        m_assert.assertTrue("New Password Created Successfully -> User redirected to Login Page");

                        //Try to Log in with Old Password
                        loginBtnClicked = login(userDetail);
                        if (loginBtnClicked) {
                            loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                            m_assert.assertFalse(loginStatus, "Validated -> User unable to Login with Old Password");
                            if (loginStatus) {
                                continue;
                            }
                        }
                    } else {
                        m_assert.assertFatal("Unable to create New Password / User is not navigated to Login Page");
                    }

                    //Try to Log in with New Password
                    loginBtnClicked = login(userDetail, true);
                    if (loginBtnClicked) {
                        loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                        m_assert.assertTrue("Validated -> User able to Login with New Password");
                        if (loginStatus) {

                            //valid username
                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                            String currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser).split("\\n")[1].split(":")[1].trim();

                            boolean validUser = currentLoggedUserName.equals(userDetail.get(sUSERNAME));
                            m_assert.assertTrue(validUser, "Logged In User = <b>" + currentLoggedUserName + "</b>");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_profileSetting, 5);

                            //Navigating to Profile Setting > Login and Security
                            Cls_Generic_Methods.clickElement(driver, oPage_ProfileSetting.link_profileSetting);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_loginAndSecurity, 5);
                            Cls_Generic_Methods.clickElement(oPage_ProfileSetting.link_loginAndSecurity);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_headerLoginAndSecurity, 5);

                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_auditTrails), "Clicked Login And Security > Audit Trails");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_passwordExpireOn, 5);

                            // Checking displayed password expiry date in audit trial
                            String txt_passwordExpireOn = Cls_Generic_Methods.getTextInElement(oPage_ProfileSetting.text_passwordExpireOn).split("expire on")[1].trim();
                            boolean validPassword = Integer.parseInt(sPasswordResetInterval) == calculateDaysDifference(txt_passwordExpireOn);
                            m_assert.assertTrue(validPassword, "Validated Create New Password --> User : " + currentLoggedUserName + "'s Password will Expiry on " + txt_passwordExpireOn + " [AFTER " + calculateDaysDifference(txt_passwordExpireOn) + " DAYS]");

                            //Check Audit Trails
                            checkLogInAuditTrails(sEVENT_PASSWORD, currentDateAndTime, false);

                            //Validate Selected Security Question is displayed in Profile setting
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_changePassword), "Clicked Change Password Tab");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.input_userNameCPTab, 10);

                            //Validate Security question is selected
                            selectedSecurityQuestion = Cls_Generic_Methods.getSelectedValue(oPage_ProfileSetting.select_securityQuestionCPTab);
                            m_assert.assertTrue(selectedSecurityQuestion.equals(userDetail.get(sSET_SECURITY_QUESTION)), "Validated ->Newly Set Security Question is auto-selected in Profile Setting page->" + selectedSecurityQuestion);

                        }
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validating Password reset through Forgot Password Link")
    public void validateResetByForgotPassword() {

        String emailUrl = "https://mail.tutanota.com/login";
        String emailPwd="HGraph@2$2$";

        try {
            oPage_PasswordResetPage = new Page_PasswordResetPage(driver);
            oPage_Login = new Page_Login(driver);
            oPage_ProfileSetting = new Page_ProfileSetting(driver);
            oPage_Navbar = new Page_Navbar(driver);
            oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
            oPage_ForgotPasswordPage = new Page_ForgotPasswordPage(driver);

            ArrayList<HashMap<String, String>> allUserDetails = getUserDataFromExcel(sFORGOT_PASSWORD);

            if (auto_rakeBuild) {
                autoRakeDeployment(allUserDetails, sFORGOT_PASSWORD);
            }

            Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            boolean userInLoginPage = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 4);

            if (!userInLoginPage) {

                //logout
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_logout);
                Cls_Generic_Methods.waitForPageLoad(driver, 12);

            }


            for (HashMap<String, String> userDetail : allUserDetails) {

                m_assert.assertInfo("<b><font color='green'> TEST " + userDetail.get("SNO") + " ---> USER ID : " + userDetail.get(sUSERNAME) + "</font></b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.link_forgotPassword, 10);
                m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_Login.link_forgotPassword), "Clicked <b>I Forgot my Password</b>");

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ForgotPasswordPage.input_userName, 10);
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ForgotPasswordPage.input_userName, userDetail.get(sUSERNAME)), "Entered " + userDetail.get(sUSERNAME) + " in Username");
                m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ForgotPasswordPage.input_emailAddress, userDetail.get(sEMAIL_ID)), "Entered " + userDetail.get(sEMAIL_ID) + " in Email Address");


                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(oPage_ForgotPasswordPage.iframe_captcha));
                wait.until(ExpectedConditions.elementToBeClickable(oPage_ForgotPasswordPage.checkbox_captcha)).click();

                Cls_Generic_Methods.switchToDefaultFrame(driver);

                System.out.println("Clicked the checkbox --->> Kindly Solve the Captcha");
                Cls_Generic_Methods.clickElementByJS(driver, wait.until(ExpectedConditions.elementToBeClickable(oPage_ForgotPasswordPage.button_verifyUser)));

                boolean emailSentSuccessMsgDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ForgotPasswordPage.text_successfulMsg, 10);
                m_assert.assertTrue(emailSentSuccessMsgDisplayed, "Validated -> Success Msg Displayed - Reset Link sent to User's registered Email ");


                //  Switch To User Email
                Cls_Generic_Methods.getURL(driver, emailUrl);
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.tutanota_button_login, 10);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.tutanota_input_emailAddress, userDetail.get(sEMAIL_ID));
                Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.tutanota_input_password, emailPwd);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.tutanota_button_login);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.tutanota_link_inboxSentMail, 10);
                m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.tutanota_link_inboxSentMail), "Email Sent to the User's Registered Email Address");
                Cls_Generic_Methods.customWait();

                JavascriptExecutor js = (JavascriptExecutor) driver;
                String script = "return document.querySelector('#mail-body').shadowRoot.querySelector('#signup-url-content > div > a')";
                WebElement resetLink = (WebElement) js.executeScript(script);

                m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, resetLink), "Clicked <b>Reset Password</b> Link in the Received mail");
                Cls_Generic_Methods.switchToOtherTab();

                boolean resetPageDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_createNewPassword, 10);

                if (resetPageDisplayed) {

                    //Validate username
                    String text_username = Cls_Generic_Methods.getElementAttribute(oPage_PasswordResetPage.input_username, "value");
                    m_assert.assertTrue(text_username.equals(userDetail.get(sUSERNAME)), "Validated Displayed Username -> " + text_username);

                    //Validate Security question is selected
                    String selectedSecurityQuestion = Cls_Generic_Methods.getSelectedValue(oPage_PasswordResetPage.select_securityQuestion);

                    if (selectedSecurityQuestion.contains("Select")) {
                        m_assert.assertTrue(userDetail.get(sSECURITY_QUESTION).isBlank(), "Validated -> Security Question is not set for the user");
                    } else {
                        m_assert.assertTrue(selectedSecurityQuestion.equals(userDetail.get(sSECURITY_QUESTION)), "Validated ->Selected Security Question->" + selectedSecurityQuestion);
                    }

                    //Click Proceed for Now
                    m_assert.assertFalse(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_proceedForNow, 1), "Validated -> <b>Proceed For Now</b> button is not displayed for Password expired user");

                    if (userDetail.get(sSECURITY_QUESTION).isBlank()) {
                        m_assert.assertInfo(Cls_Generic_Methods.selectElementByVisibleText(oPage_PasswordResetPage.select_securityQuestion, userDetail.get(sSET_SECURITY_QUESTION)), "Selected " + userDetail.get(sSET_SECURITY_QUESTION) + " in security question");
                        updateExcel(sFORGOT_PASSWORD, sUSERNAME, userDetail.get(sUSERNAME), sSECURITY_QUESTION, userDetail.get(sSET_SECURITY_QUESTION));

                    } else {
                        //Clearing Current password for validation
                        Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_currentPassword);
                    }

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_newPassword, "ZXAZPOA"), "Entered Invalid format in New Password [Which doesn't meet the requirement]");
                    Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword);
                    boolean errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 6);
                    m_assert.assertTrue(errorMsg, "Error message is displayed");

                    //updating excel
                    sUpdatedPassword = generateRandomPassword(10);
                    updateExcel(sFORGOT_PASSWORD, sUSERNAME, userDetail.get(sUSERNAME), sPASSWORD, sUpdatedPassword);

                    //Checking without filling confirm password . It should not allow user to create new password
                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_newPassword);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_newPassword, sUpdatedPassword), "Entered " + sUpdatedPassword + " in New Password ");

                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, "xzapo");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password Button without filling Confirm Password");
                    errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                    m_assert.assertTrue(errorMsg, "Error message is displayed");

                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmNewPassword, userDetail.get(sPASSWORD)), "Entered different Password in Confirm Password [NEW PASSWORD != CONFIRM PASSWORD] ");
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password");
                    errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                    m_assert.assertTrue(errorMsg, "Error Notify message is displayed");

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_confirmNewPassword);
                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmNewPassword, sUpdatedPassword), "Entered Valid Password in Confirm Password [NEW PASSWORD == CONFIRM PASSWORD] ");

                    //If security question is already set , then check with invalid answer

                    if (!userDetail.get(sSECURITY_QUESTION).isBlank()) {
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, "ZXAZPOA"), "Entered invalid value in Security Answer");
                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password ");
                        errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                        m_assert.assertTrue(errorMsg, "Error Notify message is displayed");
                    }

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, userDetail.get(sSECURITY_ANSWER)), "Entered " + userDetail.get(sSECURITY_ANSWER) + " in Security Answer");

                    Cls_Generic_Methods.customWait(4);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password");
                    currentDateAndTime = getCurrentDateFormatted();

                    //User redirected to Login page
                    boolean loginPageDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                    if (loginPageDisplayed) {

                        m_assert.assertTrue("New Password Created Successfully -> User redirected to Login Page");

                        //Try to Log in with Old Password
                        boolean loginBtnClicked = login(userDetail);
                        if (loginBtnClicked) {
                            boolean loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                            m_assert.assertFalse(loginStatus, "Validated -> User unable to Login with Old Password");
                            if (loginStatus) {
                                continue;
                            }
                        }
                    } else {
                        m_assert.assertFatal("Unable to create New Password / User is not navigated to Login Page");
                    }

                    //Try to Log in with New Password
                    boolean loginBtnClicked = login(userDetail, true);

                    if (loginBtnClicked) {
                        boolean loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                        m_assert.assertTrue("Validated -> User able to Login with New Password");
                        if (loginStatus) {

                            //valid username
                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                            String currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser).split("\\n")[1].split(":")[1].trim();

                            boolean validUser = currentLoggedUserName.equals(userDetail.get(sUSERNAME));
                            m_assert.assertTrue(validUser, "Logged In User = <b>" + currentLoggedUserName + "</b>");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_profileSetting, 5);

                            //Navigating to Profile Setting > Login and Security
                            Cls_Generic_Methods.clickElement(driver, oPage_ProfileSetting.link_profileSetting);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_loginAndSecurity, 5);
                            Cls_Generic_Methods.clickElement(oPage_ProfileSetting.link_loginAndSecurity);
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_headerLoginAndSecurity, 5);

                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_auditTrails), "Clicked Login And Security > Audit Trails");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_passwordExpireOn, 5);

                            // Checking displayed password expiry date in audit trial
                            String txt_passwordExpireOn = Cls_Generic_Methods.getTextInElement(oPage_ProfileSetting.text_passwordExpireOn).split("expire on")[1].trim();
                            boolean validPassword = Integer.parseInt(sPasswordResetInterval) == calculateDaysDifference(txt_passwordExpireOn);
                            m_assert.assertTrue(validPassword, "Validated Create New Password --> User : " + currentLoggedUserName + "'s Password will Expiry on " + txt_passwordExpireOn + " [AFTER " + calculateDaysDifference(txt_passwordExpireOn) + " DAYS]");

                            //Check Audit Trails
                            checkLogInAuditTrails(sEVENT_PASSWORD, currentDateAndTime, false);

                            //Validate Selected Security Question is displayed in Profile setting
                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_changePassword), "Clicked Change Password Tab");
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.input_userNameCPTab, 10);

                            //Validate Security question is selected
                            selectedSecurityQuestion = Cls_Generic_Methods.getSelectedValue(oPage_ProfileSetting.select_securityQuestionCPTab);
                            m_assert.assertTrue(selectedSecurityQuestion.equals(userDetail.get(sSET_SECURITY_QUESTION)), "Validated ->Newly Set Security Question is auto-selected in Reset Page->" + selectedSecurityQuestion);

                            //Logout
                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_logout);
                            Cls_Generic_Methods.waitForPageLoad(driver, 12);

                            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();

                            //Click Same Link after reset password [It should allow user to change password]
                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.tutanota_link_inboxSentMail, 10);
                            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, resetLink), "Clicked <b>Reset Password</b> Link in the Received mail [AFTER USER SUCCESSFULLY RESET THE PASSWORD]");
                            Cls_Generic_Methods.switchToOtherTab();

                            resetPageDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);
                            m_assert.assertTrue(resetPageDisplayed, "Validate -> User is navigated to Login Page");

                        }
                    }


                } else {
                    m_assert.assertFalse("User not redirected to Reset Password Page");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Test(enabled = true, description = "Validating Past 10 previously used Password is not accepted")
    public void validatePreviouslyUsedPassword() {

        String sValidPassword = ""; // [USED PASSWORD - BEFORE PAST 10 PASSWORDS]

        try {
            oPage_PasswordResetPage = new Page_PasswordResetPage(driver);
            oPage_Login = new Page_Login(driver);
            oPage_ProfileSetting = new Page_ProfileSetting(driver);
            oPage_Navbar = new Page_Navbar(driver);
            oPage_OrganisationSetup = new Page_OrganisationSetup(driver);
            oPage_ForgotPasswordPage = new Page_ForgotPasswordPage(driver);

            ArrayList<HashMap<String, String>> allUserDetails = getUserDataFromExcel(sFORGOT_PASSWORD);

            if (auto_rakeBuild) {
                //   autoRakeDeployment(allUserDetails, sVALID_PASSWORD);
            }

            ArrayList<String> sPreviouslyUsedPasswords = new ArrayList<>();

            for (HashMap<String, String> userDetail : allUserDetails) {

                sValidPassword = userDetail.get(sPASSWORD);
                while (sPreviouslyUsedPasswords.size() < 10) {
                    boolean loginBtnClicked;

                    if (sPreviouslyUsedPasswords.size() < 1) {
                        loginBtnClicked = login(userDetail, false);
                    } else {
                        loginBtnClicked = login(userDetail, true);
                    }

                    if (loginBtnClicked) {
                        boolean loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);

                        if (!loginStatus) {
                            continue;
                        }

                        Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                        Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);

                        String currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser).split("\\n")[1].split(":")[1].trim();

                        boolean validUser = currentLoggedUserName.equals(userDetail.get(sUSERNAME));
                        m_assert.assertTrue(validUser, "Login successful -> Logged In User = <b>" + currentLoggedUserName + "</b>");
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_profileSetting, 5);

                        //Navigating to Profile Setting > Login and Security
                        Cls_Generic_Methods.clickElement(driver, oPage_ProfileSetting.link_profileSetting);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_loginAndSecurity, 20);
                        Cls_Generic_Methods.clickElement(oPage_ProfileSetting.link_loginAndSecurity);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_headerLoginAndSecurity, 5);

                        Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_currentPasswordCPTab);

                        sUpdatedPassword = generateRandomPassword(12);
                        sPreviouslyUsedPasswords.add(sUpdatedPassword);

                        Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_newPasswordCPTab);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_newPasswordCPTab, sUpdatedPassword);

                        Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_confirmPasswordCPTab);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_confirmPasswordCPTab, sUpdatedPassword);

                        Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_securityAnswerCPTab);

                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_securityAnswerCPTab, userDetail.get(sSECURITY_ANSWER));
                        Cls_Generic_Methods.customWait(1);
                        Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword);

                        boolean passwordSet = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                        if (passwordSet) {
                            m_assert.assertTrue("<font color='green'>" + sPreviouslyUsedPasswords.size() + " . NEW PASSWORD CREATED ->  <b>" + sUpdatedPassword + " </font></b>");
                        }
                    }
                }

                //PRE: 10 PASSWORD SET

                //Validate in Profile Setting

                boolean loginBtnClicked = login(userDetail, true);

                if (loginBtnClicked) {

                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);

                    String currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser).split("\\n")[1].split(":")[1].trim();

                    boolean validUser = currentLoggedUserName.equals(userDetail.get(sUSERNAME));
                    m_assert.assertTrue(validUser, "Login successful -> Logged In User = <b>" + currentLoggedUserName + "</b>");
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_profileSetting, 5);

                    //Navigating to Profile Setting > Login and Security
                    Cls_Generic_Methods.clickElement(driver, oPage_ProfileSetting.link_profileSetting);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_loginAndSecurity, 5);

                    boolean status = false;

                    for (String sOldPassword : sPreviouslyUsedPasswords) {

                        Cls_Generic_Methods.driverRefresh();
                        Cls_Generic_Methods.clickElement(oPage_ProfileSetting.link_loginAndSecurity);
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.input_currentPasswordCPTab, 10);

                        Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_currentPasswordCPTab);
                        Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_newPasswordCPTab);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_newPasswordCPTab, sOldPassword);

                        Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_confirmPasswordCPTab);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_confirmPasswordCPTab, sOldPassword);

                        Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_securityAnswerCPTab);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_securityAnswerCPTab, userDetail.get(sSECURITY_ANSWER));
                        Cls_Generic_Methods.customWait(1);
                        Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword);
                        boolean passwordSet = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 7);

                        if (passwordSet) {
                            status = false;
                            break;
                        } else {
                            status = true;
                        }
                    }
                    m_assert.assertTrue(status, "Validate -> User not able to Create New Password with Previously Used 10 Passwords");


                    //Try to create Password With Previously Used Password [NOT FROM PAST 10]

                    Cls_Generic_Methods.driverRefresh();
                    Cls_Generic_Methods.clickElement(oPage_ProfileSetting.link_loginAndSecurity);
                    Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.input_currentPasswordCPTab, 10);

                    Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_currentPasswordCPTab);
                    Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_newPasswordCPTab);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_newPasswordCPTab, sValidPassword), "Entered Previously Used password : " + sValidPassword + " in New Password [Old Password that is used by the user before past 10 Password]");

                    Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_confirmPasswordCPTab);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_confirmPasswordCPTab, sValidPassword), "Entered Same Password in Confirm Password ");

                    Cls_Generic_Methods.clearValuesInElement(oPage_ProfileSetting.input_securityAnswerCPTab);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_ProfileSetting.input_securityAnswerCPTab, userDetail.get(sSECURITY_ANSWER)), "Entered <b>" + userDetail.get(sSECURITY_ANSWER) + "</b> in Security Answer");
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Change Password");
                    boolean passwordSet = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                    if (passwordSet) {
                        m_assert.assertTrue("User able to set the password that is used before Past 10 passwords [IN PROFILE SETTING PAGE]");
                        //Adding new password in previously used password
                        sPreviouslyUsedPasswords.add(sValidPassword);
                        sUpdatedPassword = sValidPassword;

                        sValidPassword = sPreviouslyUsedPasswords.get(0);
                        sPreviouslyUsedPasswords.remove(0);


                    } else {
                        m_assert.assertFalse("Unable to set Password that is used by user before past 10 Password");
                        break;
                    }
                }

                //Running rake to make user password To expire
                autoRakeDeployment(allUserDetails, sPASSWORD_EXPIRED);

                //ON RESET PAGE (CHECK WHETHER PREVIOUSLY USED 10 PASSWORD IS NOT ACCEPTED)
                loginBtnClicked = login(userDetail, true);

                if (loginBtnClicked) {
                    boolean loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_createNewPassword, 7);
                    m_assert.assertInfo(loginStatus, "User redirected to Password Reset page [To Validate User able to set previously used 10 passwords]");

                    if (!loginStatus) {
                        continue;
                    }

                    boolean status = false;
                    for (String sOldPassword : sPreviouslyUsedPasswords) {
                        Cls_Generic_Methods.driverRefresh();
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.input_newPassword, 10);
                        Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_newPassword);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_newPassword, sOldPassword);

                        Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_confirmNewPassword);
                        Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                        Cls_Generic_Methods.customWait(1);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmNewPassword, sOldPassword);

                        Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                        Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, userDetail.get(sSECURITY_ANSWER));

                        Cls_Generic_Methods.customWait(4);
                        Cls_Generic_Methods.clickElementByJS(driver, oPage_PasswordResetPage.button_createNewPassword);

                        boolean passwordSet = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 7);

                        if (passwordSet) {
                            status = false;
                            break;
                        } else {
                            status = true;
                        }
                    }
                    m_assert.assertTrue(status, "Validate -> User not able to Create New Password with Previously Used Password in Password Reset Page");

                    //Try to create Password With Previously Used Password [NOT FROM PAST 10]

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_newPassword);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_newPassword, sValidPassword), "Entered Previously used password " + sValidPassword + " in New Password [Old Password that is used by the user before past 10 Password]");

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_confirmNewPassword);
                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                    Cls_Generic_Methods.customWait(1);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmNewPassword, sValidPassword), "Entered the same in Confirm Password");

                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, userDetail.get(sSECURITY_ANSWER)), "Entered " + userDetail.get(sSECURITY_ANSWER) + " in Security Answer");

                    Cls_Generic_Methods.customWait(4);
                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_PasswordResetPage.button_createNewPassword), "Clicked Reset Password");

                    boolean passwordSet = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                    m_assert.assertTrue(passwordSet, "User able to set the password that is used before past 10 passwords [IN PASSWORD RESET PAGE]");


                    //Clear security answer for future purpose
                    updateExcel(sFORGOT_PASSWORD, sUSERNAME, userDetail.get(sUSERNAME), sSECURITY_QUESTION, " ");

                }

            }


        } catch (Exception e) {
            m_assert.assertFatal("Unable to validate Previously Used Password ->" + e);
            e.printStackTrace();
        }
    }

    @Test(enabled = true, description = "Validating Password reset For New User")
    public void validatePasswordResetForNewUser() {

        String sUserFullName = "Reset " + generateUniqueUsername();
        String sEmailID = "";
        String sNewUserName = "RESET_" + generateUniqueUsername();
        String sTemporaryPassword = generateRandomPassword(10);

        try {
            oPage_PasswordResetPage = new Page_PasswordResetPage(driver);
            oPage_Login = new Page_Login(driver);
            oPage_ProfileSetting = new Page_ProfileSetting(driver);
            oPage_Navbar = new Page_Navbar(driver);
            oPage_OrganisationSetup = new Page_OrganisationSetup(driver);

            //Login With admin user
            m_assert.assertInfo(CommonActions.loginFunctionality(admin_user), "Logged in with Admin User");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.button_SettingsNdLogout, 10);
            facilityName = getFacilityName();

            Cls_Generic_Methods.switchToNewTab(driver.getWindowHandle());
            sEmailID = createYopMail();
            Cls_Generic_Methods.switchToOtherTab();

            CommonActions.selectOptionUnderSettings(Settings_Data.option_ORGANISATION_SETTING);
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectOptionFromLeftInSettingsPanel("General", "Organisation Setup");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 15);
            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_addUser), "Clicked Add User Button");

            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.select_salutaionForTheUser, 15);

            Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_salutaionForTheUser, "Dr");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_userFullName, sUserFullName), "User Full Name =  <b>Dr " + sUserFullName + " </b>");
            m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_emailAddressOfTheUser, sEmailID.split("@")[0]), "Email ID  =  <b> " + sEmailID + "@yopmail.com</b>");
            Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_emailDomain, "@yopmail.com");

            Cls_Generic_Methods.selectElementByVisibleText(oPage_OrganisationSetup.select_genderOfTheUser, "Male");

            Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.input_datePicker);
            Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.list_birthDateOfTheUser.get(0));
            Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_mobileNumberOfTheUser, Settings_Data.sUSER_MOBILE_NUMBER);
            Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_telephoneNumberOfTheUser, Settings_Data.sUSER_TELEPHONE_NUMBER);

            Cls_Generic_Methods.customWait(2);
            Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.input_countryNamefield);
            Cls_Generic_Methods.selectElementByValue(oPage_OrganisationSetup.select_countryId, "IN_108");
            Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_address, Settings_Data.sADDRESS);
            Cls_Generic_Methods.customWait();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_pincodeOfTheUser, 10);

            try {
                Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_pincodeOfTheUser, (Settings_Data.sPINCODE));
                Cls_Generic_Methods.customWait();

                for (WebElement pinCodeElement : oPage_OrganisationSetup.inputOptions_pincodeOnAddUserForm) {
                    if (Cls_Generic_Methods.getTextInElement(pinCodeElement).equals(Settings_Data.sPINCODE)) {
                        Cls_Generic_Methods.clickElement(pinCodeElement);
                        break;
                    }
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_next, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.button_next);
                Cls_Generic_Methods.sendKeysIntoElement(oPage_OrganisationSetup.input_userDesignation, Settings_Data.sDESIGNATION);
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_doctorRole);

                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.checkbox_outPatientDepartmant);
                Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_saveUser);

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.input_facilityNameField, 10);
                Cls_Generic_Methods.clickElement(driver, oPage_OrganisationSetup.input_facilityNameField);
                Cls_Generic_Methods.customWait(1);

                for (WebElement eFacilityElement : oPage_OrganisationSetup.list_facilityNameFromDropdown) {
                    String nameOfFacilityToSelect = Cls_Generic_Methods.getTextInElement(eFacilityElement);
                    if (facilityName.contains(nameOfFacilityToSelect)) {
                        Cls_Generic_Methods.clickElement(driver, eFacilityElement);
                        facilityName = nameOfFacilityToSelect;
                        break;
                    }
                }

                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_save, 5);
                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.checkbox_authPolicy);
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_save);

                if (Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_close, 10)) {
                    Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_close);
                    Cls_Generic_Methods.customWait(5);
                }

                boolean newUserCreatedStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_allUserInOrgLevel, 10);

                m_assert.assertTrue(newUserCreatedStatus, "New User Created -> Full Name : " + sUserFullName);

                Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.button_lastUserPagination);

                Cls_Generic_Methods.customWait(10);
                int sizeUserName = oPage_OrganisationSetup.list_userName.size();
                boolean activationLinkSent = false;

                for (int i = 0; i < sizeUserName; i++) {
                    if (Cls_Generic_Methods.getTextInElement(oPage_OrganisationSetup.list_userName.get(i)).contains(sUserFullName)) {
                        Cls_Generic_Methods.scrollToElementByJS(oPage_OrganisationSetup.list_activateButtonName.get(i));
                        Cls_Generic_Methods.customWait();
                        Cls_Generic_Methods.clickElement(oPage_OrganisationSetup.list_activateButtonName.get(i));
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_sendActivationUserLink, 10);
                        m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_OrganisationSetup.button_sendActivationUserLink), "Clicked on send activation link button");
                        activationLinkSent = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_OrganisationSetup.button_addUser, 10);
                        break;
                    }
                }

                if (activationLinkSent) {
                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                    Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                    Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_logout);
                    Cls_Generic_Methods.waitForPageLoad(driver, 12);
                    Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                    boolean clickedActivationLink = clickActivationLinkOnInboxYopMail();

                    if (clickedActivationLink) {
                        Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
                        Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.input_userNameActivation, 10);

                        Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.input_userNameActivation);
                        Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_userNameActivation);
                        Cls_Generic_Methods.customWait();
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_userNameActivation, sNewUserName), "Entered <b>" + sNewUserName + "</b> in Username");

                        Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_temporaryPasswordActivation);
                        Cls_Generic_Methods.customWait();
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_temporaryPasswordActivation, sTemporaryPassword), "Entered <b>" + sTemporaryPassword + "</b> in Temporary Password");

                        Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_confirmTemporaryPasswordActivation);
                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmTemporaryPasswordActivation, sTemporaryPassword), "Entered the same in Confirm Temporary Password");

                        Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_setPasswordActivation);

                        boolean temporaryPasswordCreated = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                        if (temporaryPasswordCreated) {
                            updateExcel(sNEW_USER, "SNO", "1", sUSERNAME, sNewUserName);
                            updateExcel(sNEW_USER, "SNO", "1", sPASSWORD, sTemporaryPassword);
                            updateExcel(sNEW_USER, "SNO", "1", sEMAIL_ID, sEmailID + "@yopmail.com");

                            ArrayList<HashMap<String, String>> allUserDetails = getUserDataFromExcel(sNEW_USER);


                            for (HashMap<String, String> userDetail : allUserDetails) {

                                boolean loginBtnClicked = login(userDetail);

                                if (loginBtnClicked) {
                                    boolean loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_createNewPassword, 7);
                                    m_assert.assertTrue(loginStatus, "User redirected to Password Reset page");

                                    if (!loginStatus) {
                                        break;
                                    }

                                    //Validate Back To Login
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.link_backToLogin), "Clicked Back To Login Button");
                                    boolean loginPageDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                                    if (loginPageDisplayed) {
                                        m_assert.assertTrue("Validated Back to Login -> User redirected to Login Page");
                                        loginBtnClicked = login(userDetail);
                                        if (loginBtnClicked) {
                                            loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_createNewPassword, 7);
                                            if (!loginStatus) {
                                                continue;
                                            }
                                        } else {
                                            m_assert.assertFatal("Unable to Login");
                                            continue;
                                        }
                                    } else {
                                        m_assert.assertFalse("Unable to validate Back to Login -> User is not navigated to Login Page");
                                    }

                                    //validate header
                                    String text_passwordExpiryIn = Cls_Generic_Methods.getTextInElement(oPage_PasswordResetPage.text_headerMsg);
                                    m_assert.assertTrue(text_passwordExpiryIn.contains("Welcome to Healthgraph"), "Validated Displayed Header -> " + text_passwordExpiryIn);
                                    Cls_Generic_Methods.customWait();

                                    //Validate username
                                    String text_username = Cls_Generic_Methods.getElementAttribute(oPage_PasswordResetPage.input_username, "value");
                                    m_assert.assertTrue(text_username.equals(userDetail.get(sUSERNAME)), "Validated Displayed Username -> " + text_username);

                                    //Validate Security question is selected
                                    String selectedSecurityQuestion = Cls_Generic_Methods.getSelectedValue(oPage_PasswordResetPage.select_securityQuestion);
                                    m_assert.assertTrue(selectedSecurityQuestion.contains("Select"), "Validated -> Security Question is not set for the user");
                                    m_assert.assertTrue(Cls_Generic_Methods.selectElementByVisibleText(oPage_PasswordResetPage.select_securityQuestion,userDetail.get(sSET_SECURITY_QUESTION)), "Selected Security Question : "+userDetail.get(sSET_SECURITY_QUESTION));

                                    //Click Proceed for Now
                                    m_assert.assertFalse(Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.button_proceedForNow, 1), "Validated -> <b>Proceed For Now</b> button is not displayed for Password expired user");

                                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_newPassword, "ZXAZPOA"), "Entered Invalid format in New Password [Which doesn't meet the requirement]");
                                    Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.input_confirmNewPassword);
                                    boolean errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorMsgNewPassword, 3);
                                    m_assert.assertTrue(errorMsg, "Error message is displayed");

                                    sUpdatedPassword = generateRandomPassword(8);
                                    updateExcel(sNEW_USER, sUSERNAME, userDetail.get(sUSERNAME), sNEW_PASSWORD, sUpdatedPassword);

                                    //Checking without filling confirm password . It should not allow user to create new password
                                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_newPassword);
                                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_newPassword, sUpdatedPassword), "Entered " + sUpdatedPassword + " in New Password ");
                                    Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, "xzapo");
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Create New Password Button without filling Confirm Password");
                                    errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                                    m_assert.assertTrue(errorMsg, "Error message is displayed");

                                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmNewPassword, userDetail.get(sPASSWORD)), "Entered different Password in Confirm Password [NEW PASSWORD != CONFIRM PASSWORD] ");
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Create New Password");
                                    errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                                    m_assert.assertTrue(errorMsg, "Error Notify message is displayed");

                                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_confirmNewPassword);
                                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                                    Cls_Generic_Methods.customWait(1);
                                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_confirmNewPassword, sUpdatedPassword), "Entered Valid Password in Confirm Password [NEW PASSWORD == CONFIRM PASSWORD] ");

                                    //If security question is already set , then check with invalid answer

                                    if (!userDetail.get(sSECURITY_QUESTION).isBlank()) {
                                        m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, "ZXAZPOA"), "Entered invalid value in Security Answer");
                                        m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.button_createNewPassword), "Clicked Create New Password ");
                                        errorMsg = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.text_errorNotifyMsg, 3);
                                        m_assert.assertTrue(errorMsg, "Error Notify message is displayed");
                                    }
                                    Cls_Generic_Methods.clearValuesInElement(oPage_PasswordResetPage.input_securityAnswer);
                                    m_assert.assertInfo(Cls_Generic_Methods.sendKeysIntoElement(oPage_PasswordResetPage.input_securityAnswer, userDetail.get(sSECURITY_ANSWER)), "Entered " + userDetail.get(sSECURITY_ANSWER) + " in Security Answer");

                                    Cls_Generic_Methods.customWait(4);
                                    m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_PasswordResetPage.button_createNewPassword), "Clicked Create New Password");
                                    currentDateAndTime = getCurrentDateFormatted();

                                    //User redirected to Login page
                                    loginPageDisplayed = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 10);

                                    if (loginPageDisplayed) {

                                        m_assert.assertTrue("New Password Created Successfully -> User redirected to Login Page");

                                        //Try to Log in with Old Password
                                        loginBtnClicked = login(userDetail);
                                        if (loginBtnClicked) {
                                            loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                                            m_assert.assertFalse(loginStatus, "Validated -> User unable to Login with Temporary Password After Creating New Password");
                                            if (loginStatus) {
                                                continue;
                                            }
                                        }
                                    } else {
                                        m_assert.assertFatal("Unable to create New Password / User is not navigated to Login Page");
                                    }

                                    //Try to Log in with New Password
                                    loginBtnClicked = login(userDetail, true);
                                    if (loginBtnClicked) {
                                        loginStatus = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Navbar.logo_FF_EHS, 7);
                                        m_assert.assertTrue("Validated -> User able to Login with New Password");
                                        if (loginStatus) {

                                            //valid username
                                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                                            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                                            String currentLoggedUserName = Cls_Generic_Methods.getTextInElement(oPage_Navbar.text_currentUser).split("\\n")[1].split(":")[1].trim();

                                            boolean validUser = currentLoggedUserName.equals(userDetail.get(sUSERNAME));
                                            m_assert.assertTrue(validUser, "Logged In User = <b>" + currentLoggedUserName + "</b>");
                                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_profileSetting, 5);

                                            //Navigating to Profile Setting > Login and Security
                                            Cls_Generic_Methods.clickElement(driver, oPage_ProfileSetting.link_profileSetting);
                                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.link_loginAndSecurity, 5);
                                            Cls_Generic_Methods.clickElement(oPage_ProfileSetting.link_loginAndSecurity);
                                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_headerLoginAndSecurity, 5);

                                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_auditTrails), "Clicked Login And Security > Audit Trails");
                                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_passwordExpireOn, 5);

                                            // Checking displayed password expiry date in audit trial
                                            String txt_passwordExpireOn = Cls_Generic_Methods.getTextInElement(oPage_ProfileSetting.text_passwordExpireOn).split("expire on")[1].trim();
                                            boolean validPassword = Integer.parseInt(sPasswordResetInterval) == calculateDaysDifference(txt_passwordExpireOn);
                                            m_assert.assertTrue(validPassword, "Validated Create New Password --> User : " + currentLoggedUserName + "'s Password will Expiry on " + txt_passwordExpireOn + " [AFTER " + calculateDaysDifference(txt_passwordExpireOn) + " DAYS]");

                                            //Check Audit Trails
                                            checkLogInAuditTrails(sEVENT_PASSWORD, currentDateAndTime, false);

                                            //Validate Selected Security Question is displayed in Profile setting
                                            m_assert.assertInfo(Cls_Generic_Methods.clickElement(oPage_ProfileSetting.tab_changePassword), "Clicked Change Password Tab");
                                            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.input_userNameCPTab, 10);

                                            //Validate Security question is selected
                                            selectedSecurityQuestion = Cls_Generic_Methods.getSelectedValue(oPage_ProfileSetting.select_securityQuestionCPTab);
                                            m_assert.assertTrue(selectedSecurityQuestion.equals(userDetail.get(sSET_SECURITY_QUESTION)), "Validated ->Newly Set Security Question is auto-selected in Reset Page->" + selectedSecurityQuestion);

                                            //Logout
                                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                                            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
                                            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_logout);
                                            Cls_Generic_Methods.waitForPageLoad(driver, 12);

                                        }
                                    }
                                }
                            }

                        } else {
                            m_assert.assertFalse("Temporary Password not created");
                        }

                    } else {
                        m_assert.assertFalse("Activation Link Not clicked");
                    }

                } else {
                    m_assert.assertFalse("Activation Link Not Sent");
                }


            } catch (Exception e) {
                m_assert.assertFatal("Unable to validate password reset page for new user");
                e.printStackTrace();
            }


        } catch (
                Exception e) {
            e.printStackTrace();
        }


    }


    //If New Password is Created for First Time, Then newPasswordCreated=true;
    //If User updated password for Second Time after Creating New Password , Then modifiedNewPassword=true;

    public boolean login(HashMap<String, String> expectedLoggedInUser, boolean... modifiedNewPassword) {


        boolean userInLoginPage = false;
        boolean clickedLoginButton = false;
        oPage_Login = new Page_Login(driver);
        oPage_Navbar = new Page_Navbar(driver);

        try {

            Cls_Generic_Methods.getURL(driver, Cls_Generic_Methods.getConfigValues("URL_" + env.toUpperCase()));
            Cls_Generic_Methods.waitForPageLoad(driver, 20);

            userInLoginPage = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button, 4);


            if (!userInLoginPage) {

                //logout
                Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
                Cls_Generic_Methods.customWait();
                userInLoginPage = Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_logout);
                Cls_Generic_Methods.waitForPageLoad(driver, 12);

            }


            if (userInLoginPage) {

                String userName = expectedLoggedInUser.get(sUSERNAME);
                String password = expectedLoggedInUser.get(sPASSWORD);


                if (modifiedNewPassword.length > 0 && modifiedNewPassword[0]) {
                    password = sUpdatedPassword;
                }

                oPage_Login.login_username.click();
                Cls_Generic_Methods.customWait(1);
                Cls_Generic_Methods.clearValuesInElement(oPage_Login.login_username);
                Cls_Generic_Methods.customWait(1);
                oPage_Login.login_username.sendKeys(userName);

                oPage_Login.login_password.click();
                Cls_Generic_Methods.clearValuesInElement(oPage_Login.login_password);
                Cls_Generic_Methods.customWait(1);
                oPage_Login.login_password.sendKeys(password);

                // oPage_Login.login_button.click();
                clickedLoginButton = Cls_Generic_Methods.clickElementByJS(driver, oPage_Login.login_button);
                Cls_Generic_Methods.waitForPageLoad(driver, 8);

                m_assert.assertInfo(clickedLoginButton, "Entered Login Credentials of User ID: <b>" + userName + "</b> and clicked on Login");
            }

        } catch (Exception e) {
            e.printStackTrace();
            clickedLoginButton = false;
            m_assert.assertFatal("Unable to Login " + e);
        }

        return clickedLoginButton;
    }


    /*condition = Sheet Name
    This Method will return all the users data present in the Respective Sheet */
    public ArrayList<HashMap<String, String>> getUserDataFromExcel(String condition) {
        String patientsExcelFileName = "TestData_PeriodicPasswordReset.xlsx";
        String dataModelsPath = System.getProperty("user.dir") + File.separator + "Resources"
                + File.separator + "Data_Files" + File.separator;
        String filePath = dataModelsPath + patientsExcelFileName;

        Connection connection = null;
        HashMap<String, String> userData;
        ArrayList<HashMap<String, String>> allUserData = new ArrayList<>();
        boolean addUser = false;

        try {
            connection = SuiteUtil._connectToExcel(filePath);
            String query = "SELECT * FROM " + condition;
            Recordset recordset = connection.executeQuery(query);
            ArrayList<String> columnHeader = recordset.getFieldNames();


            while (recordset.next()) {
                userData = new HashMap<>();

                for (String columnValue : columnHeader) {
                    try {
                        if (!recordset.getField("SNO").isEmpty()) {
                            userData.put(columnValue.trim(), recordset.getField(columnValue.trim()));
                            addUser = true;
                        }
                    } catch (Exception ignored) {
                    }
                }
                if (addUser) {
                    allUserData.add(userData);
                    addUser = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return allUserData;
    }

    public void updateExcel(String sheetName, String columnName, String searchValue, String updateColumnName, String newValue) {


        String patientsExcelFileName = "TestData_PeriodicPasswordReset.xlsx";
        String dataModelsPath = System.getProperty("user.dir") + File.separator + "Resources"
                + File.separator + "Data_Files" + File.separator;
        String filePath = dataModelsPath + patientsExcelFileName;

        try {
            Connection connection = SuiteUtil._connectToExcel(filePath);
            String query = "UPDATE " + sheetName + " SET " + updateColumnName + "='" + newValue + "' WHERE " + columnName + "='" + searchValue + "'";
            int update = connection.executeUpdate(query);
            System.out.println("Updated " + update + " record(s).");
            connection.close();
        } catch (FilloException e) {
            e.printStackTrace();
        }
    }

    public boolean isDateTimeInFuture(String inputDateTime) {

        boolean isDateTimeInFuture = false;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a, dd/MM/yyyy");
            Date inputDate = dateFormat.parse(inputDateTime);
            Date currentDate = new Date();

            int comparisonResult = inputDate.compareTo(currentDate);

            if (comparisonResult > 0) {
                isDateTimeInFuture = true;
            }

        } catch (ParseException e) {
            System.err.println("Error parsing the input date and time string: " + e.getMessage());
        }
        return isDateTimeInFuture;
    }

    public int calculateDaysDifference(String targetDateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a, dd/MM/yyyy", Locale.US);

        LocalDateTime givenDateTime = LocalDateTime.parse(targetDateTime, formatter);
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Calculate the difference between the two date-times
        long daysDifference = givenDateTime.getLong(ChronoField.EPOCH_DAY) - currentDateTime.getLong(ChronoField.EPOCH_DAY);

        // Convert the difference to an integer
        return (int) daysDifference;
    }

    public String getFacilityName() {

        String sFacilityName = "";
        try {
            Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_facilitySelector);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_currentFacilityName, 4);
            sFacilityName = Cls_Generic_Methods.getTextInElement(oPage_ProfileSetting.text_currentFacilityName);
            Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_facilitySelector);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sFacilityName;
    }

    public void checkLogInAuditTrails(String event, String currentDateAndTime, boolean facility, boolean... adminReset) {
        try {

            String userFullName = "";
            String sFacilityName = getFacilityName();

            //profile setting > profile information (to get user full name)

            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
            Cls_Generic_Methods.clickElement(driver, oPage_ProfileSetting.link_profileSetting);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.button_editProfileInformation, 10);

            Cls_Generic_Methods.clickElementByJS(driver, oPage_ProfileSetting.button_editProfileInformation);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.input_salutationEditProfileInformation, 10);
            userFullName = userFullName.concat(Cls_Generic_Methods.getElementAttribute(oPage_ProfileSetting.input_salutationEditProfileInformation, "value"));
            userFullName = userFullName.concat(" ").concat(Cls_Generic_Methods.getElementAttribute(oPage_ProfileSetting.input_fullNameEditProfileInformation, "value"));
            Cls_Generic_Methods.clickElement(oPage_ProfileSetting.button_closeEditProfileInformation);

            Cls_Generic_Methods.clickElementByJS(driver, oPage_ProfileSetting.link_loginAndSecurity);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_headerLoginAndSecurity, 5);

            m_assert.assertInfo(Cls_Generic_Methods.clickElementByJS(driver, oPage_ProfileSetting.tab_auditTrails), "Clicked Login And Security > Audit Trails");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_ProfileSetting.text_passwordExpireOn, 5);

            ArrayList<String> header = new ArrayList<>();

            if (adminReset.length > 0) {
                userFullName = admin_user;
                sFacilityName = facilityName;
            }

            for (WebElement eHeader : oPage_ProfileSetting.list_tableHeaderAuditTrails) {
                header.add(Cls_Generic_Methods.getTextInElement(eHeader));
            }

            for (WebElement row : oPage_ProfileSetting.list_tableRowAuditTrails) {
                String eventType = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(header.indexOf("Event")));
                if (eventType.equals(event)) {
                    String aDateAndTime = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(header.indexOf("Date and Time")));
                    String aUser = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(header.indexOf("User")));
                    String aFacility = Cls_Generic_Methods.getTextInElement(row.findElements(By.xpath("./td")).get(header.indexOf("Facility/Location")));

                    if (compareDate(aDateAndTime, currentDateAndTime)) {
                        m_assert.assertTrue("Validated Displayed Date and Time in Audit Trails -> " + aDateAndTime);
                        m_assert.assertTrue(aUser.equalsIgnoreCase(userFullName), "Validated Displayed User Full name in Audit Trails -> " + aUser);
                        m_assert.assertTrue("Validated Displayed Event in Audit Trails -> " + eventType);
                        if (facility) {
                            m_assert.assertTrue(aFacility.contains(sFacilityName.split("-")[0].trim()), "Validated Displayed Facility Name in Audit Trails -> " + aFacility);
                        }
                    } else {
                        m_assert.assertFalse("Unable to Find Log in Audit Trails");
                    }

                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean compareDate(String inputDate1, String inputDate2) {

        boolean compareDateStatus = false;

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a, dd/MM/yyyy");

            Date date1 = dateFormat.parse(inputDate1);
            Date date2 = dateFormat.parse(inputDate2);

            long timeDifferenceMillis = Math.abs(date1.getTime() - date2.getTime());

            // Convert the time difference to minutes
            long timeDifferenceMinutes = timeDifferenceMillis / (60 * 1000);
            compareDateStatus = timeDifferenceMinutes <= 1 || date1.equals(date2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return compareDateStatus;
    }

    public String getCurrentDateFormatted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a, dd/MM/yyyy");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    public String generateRandomPassword(int length) {

        final String SPECIAL_CHARACTERS = "@#$%^&+=!*?:;./(){}[]_-~";
        final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String SMALL_LETTERS = "abcdefghijklmnopqrstuvwxyz";
        final String NUMBERS = "0123456789";

        if (length < 8) {
            throw new IllegalArgumentException("Password length must be at least 8 characters.");
        }

        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        // Atleast one
        password.append(CAPITAL_LETTERS.charAt(random.nextInt(CAPITAL_LETTERS.length())));
        password.append(SMALL_LETTERS.charAt(random.nextInt(SMALL_LETTERS.length())));
        password.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // Generate the remaining characters
        int remainingLength = length - 4;
        for (int i = 0; i < remainingLength; i++) {
            String charSet = CAPITAL_LETTERS + SMALL_LETTERS + NUMBERS + SPECIAL_CHARACTERS;
            password.append(charSet.charAt(random.nextInt(charSet.length())));
        }

        // Shuffle the characters in the password
        for (int i = 0; i < length; i++) {
            int swapIndex = random.nextInt(length);
            char temp = password.charAt(i);
            password.setCharAt(i, password.charAt(swapIndex));
            password.setCharAt(swapIndex, temp);
        }

        return password.toString();
    }

    public String generateUniqueUsername() {
        Random random = new Random();
        StringBuilder username = new StringBuilder();

        char letter1 = (char) (random.nextInt(26) + 'A');
        username.append(letter1);

        char letter2 = (char) (random.nextInt(26) + 'A');
        username.append(letter2);

        for (int i = 0; i < 3; i++) {
            int digit = random.nextInt(10);
            username.append(digit);
        }

        return username.toString();
    }

    public String createYopMail() {
        String emailId = null;
        try {
            Cls_Generic_Methods.getURL(driver, "https://yopmail.com/en/email-generator");
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.yopMail_newEmailId,10);
            Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.yopMail_newEmailId);
            Cls_Generic_Methods.customWait();
            emailId = Cls_Generic_Methods.getTextInElement(oPage_PasswordResetPage.yopMail_textEmailId).trim();
            Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.yopMail_checkInbox);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emailId;
    }

    public boolean clickActivationLinkOnInboxYopMail() {
        boolean clickedActivationLink = false;
        try {
            boolean inboxOpened = Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.yopMail_refreshButton, 10);
            Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.yopMail_refreshButton);

            if (inboxOpened) {
                Cls_Generic_Methods.customWait();
                Cls_Generic_Methods.switchToFrame(driver, driver.findElement(By.id("ifinbox")));
                Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.yopMail_emailSub);
                driver.switchTo().defaultContent();
                Cls_Generic_Methods.switchToFrame(driver, driver.findElement(By.id("ifmail")));
                Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_PasswordResetPage.yopMail_emailActivateAccount, 10);
                clickedActivationLink = Cls_Generic_Methods.clickElement(oPage_PasswordResetPage.yopMail_emailActivateAccount);

            } else {
                m_assert.assertFalse("Inbox is not opened");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clickedActivationLink;
    }


}




