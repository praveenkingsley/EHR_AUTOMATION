package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util;

import org.openqa.selenium.WebDriver;

public class DriverFactory {


    private static DriverFactory instance=new DriverFactory();

    public static DriverFactory getInstance(){
        return instance;
    }

    ThreadLocal<WebDriver>  driver=new ThreadLocal<>();

    public  WebDriver getDriver() {
        return driver.get();
    }

    public void setDriver(WebDriver driverParams) {
        driver.set(driverParams);
    }

    public void closeBrowser(){
        driver.get().close();
        driver.remove();
    }

}
