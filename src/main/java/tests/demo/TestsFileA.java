package tests.demo;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class TestsFileA extends TestBase {

	@Test(enabled = true, description = "Validate Webpage Title")
	public void myTestA1() {

		try {
			System.out.println("Verify_Smoke_Email Browser is " + sBrowser);
			
			if (driver == null) {
				System.out.println("driver is null");
			}
			
			TestBase.test.log(Status.PASS, "This is a logging event for A1, and it passed!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			driver.quit();
//			driver = null;

		}

	}

	@Test(enabled = true, description = "Validate Webpage Title")
	public void myTestA2() {

		try {
			TestBase.test.log(Status.PASS, "This is a logging event for A2, and it passed!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			driver.quit();
//			driver = null;

		}

	}

	@Test(enabled = true, description = "Validate Webpage Title")
	public void myTestA3() {

		try {
			TestBase.test.log(Status.PASS, "This is a logging event for A3, and it passed!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			driver.quit();
//			driver = null;

		}

	}
}
