package tests.settings.organisationSettings.general.GenerateDuplicateSequence.Util;

import com.aventstack.extentreports.ExtentTest;

public class ExtentFactory {


    private static ExtentFactory instance=new ExtentFactory();

    public static ExtentFactory getInstance(){
        return instance;
    }

    ThreadLocal<ExtentTest> extent =new ThreadLocal<>();

    public ExtentTest getExtent() {
        return extent.get();
    }

    public void setExtent(ExtentTest extentParams) {
        extent.set(extentParams);
    }


}
