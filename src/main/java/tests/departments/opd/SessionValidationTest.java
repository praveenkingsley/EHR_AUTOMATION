package tests.departments.opd;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import data.EHR_Data;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.Test;
import pages.commonElements.CommonActions;
import pages.commonElements.Page_CommonElements;
import pages.login.Page_Login;
import static pages.commonElements.CommonActions.oPage_CommonElements;
import static pages.commonElements.CommonActions.oPage_Navbar;

public class SessionValidationTest extends TestBase {
    String sStore = "Pharmacy automation- Pharmacy";
    @Test(enabled = true, description = "Destroying session manually with 1tab")
    public void ValidateSessionForSingleTab(){
        Page_Login oPage_Login = new Page_Login(driver);
        try{
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            SessionId s = ((RemoteWebDriver) driver).getSessionId();
            m_assert.assertTrue("Session Id is: " + s);
            m_assert.assertTrue(CommonActions.selectFacility("TST"),
                    "User Is able to perform action when session id Present");
            driver.manage().deleteAllCookies();
            m_assert.assertTrue(CommonActions.selectFacility("OPTHA1"),
                    "User Is not able to perform action when session id Not Present");
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Login.login_button),
                    " Login Button is Displayed correctly");
            Cls_Generic_Methods.customWait(3);
        }
        catch(Exception e){
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating session by performing logout action")
    public void ValidateSessionForMultipleTabByPerformingLogoutAction() {
        Page_Login oPage_Login = new Page_Login(driver);
        oPage_CommonElements=new Page_CommonElements(driver);
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            SessionId s = ((RemoteWebDriver) driver).getSessionId();
            m_assert.assertTrue("Session Id is: " + s);
            m_assert.assertTrue(CommonActions.selectFacility("TST"),
                    "User Is able to perform action when session id Present");
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(6);
            driver.manage().deleteAllCookies();
            Cls_Generic_Methods.customWait(4);
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_logout);
            Cls_Generic_Methods.waitForPageLoad(driver, 12);
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_Login.login_button,30);
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Login.login_button),
                    " Login Button is Displayed correctly");
            Cls_Generic_Methods.closeCurrentTabAndSwitchToOtherTab();
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_SettingsNdLogout);
            Cls_Generic_Methods.waitForElementToBecomeVisible(oPage_Navbar.button_logout, 5);
            Cls_Generic_Methods.clickElement(driver, oPage_Navbar.button_logout);
            Cls_Generic_Methods.waitForPageLoad(driver, 12);
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Login.login_button),
                    " Login Button is Displayed correctly");
            Cls_Generic_Methods.customWait(4);
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }

    @Test(enabled = true, description = "Validating session by performing different actions")
    public void ValidateSessionForMultipleTabByPerformingDiffActions() {
        Page_Login oPage_Login = new Page_Login(driver);
        oPage_CommonElements=new Page_CommonElements(driver);
        try {
            CommonActions.loginFunctionality(EHR_Data.user_PRAkashTest);
            SessionId s = ((RemoteWebDriver) driver).getSessionId();
            m_assert.assertTrue("Session Id is: " + s);
            m_assert.assertTrue(CommonActions.selectFacility("TST"),
                    "User Is able to perform action when session id Present");
            Cls_Generic_Methods.customWait(3);
            CommonActions.selectStoreOnApp(sStore);
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.waitForElementToBeDisplayed(oPage_CommonElements.header_storePopup, 10);
            Cls_Generic_Methods.clickElement(oPage_CommonElements.button_closeTemplateWithoutSaving);
            Cls_Generic_Methods.customWait(5);
            driver.manage().deleteAllCookies();
            Cls_Generic_Methods.customWait(4);
            CommonActions.selectOptionFromLeftInInventoryStorePanel("Order", "Sales Order");
            Cls_Generic_Methods.customWait(6);
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Login.login_button),
                    " Login Button is Displayed correctly");
            Cls_Generic_Methods.switchToOtherTab();
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.clickElementByJS(driver, oPage_Navbar.button_addAppointment),
                    "User Is not able to perform action when session id Not Present");
            Cls_Generic_Methods.customWait(3);
            Cls_Generic_Methods.driverRefresh();
            Cls_Generic_Methods.customWait(3);
            m_assert.assertTrue(Cls_Generic_Methods.clickElement(oPage_Login.button_logoutFromErrorPage),"clicked on logout link");
            Cls_Generic_Methods.customWait(5);
            m_assert.assertTrue(Cls_Generic_Methods.isElementDisplayed(oPage_Login.login_button),
                    " Login Button is Displayed correctly");
            Cls_Generic_Methods.customWait(3);
        } catch (Exception e) {
            e.printStackTrace();
            m_assert.assertFatal("" + e);
        }
    }
}
