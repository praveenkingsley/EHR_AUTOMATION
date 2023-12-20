package tests.demo;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class TestsFileC extends TestBase {

	@Test(enabled = true, description = "Validate Webpage Title")
	public void myTestC1() {

		try {
			TestBase.test.log(Status.PASS, "This is a logging event for C1, and it passed!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			driver.quit();
//			driver = null;

		}

	}

	@Test(enabled = true, description = "Validate Webpage Title")
	public void myTestC2() {

		try {
			TestBase.test.log(Status.PASS, "This is a logging event for C2, and it passed!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			driver.quit();
//			driver = null;

		}

	}

	@Test(enabled = true, description = "Validate Webpage Title")
	public void myTestC3() {

		try {
			TestBase.test.log(Status.PASS, "This is a logging event for C3, and it passed!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			driver.quit();
//			driver = null;

		}

	}
}
