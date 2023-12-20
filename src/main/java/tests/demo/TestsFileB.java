package tests.demo;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.healthgraph.SeleniumFramework.TestNG.TestBase;

public class TestsFileB extends TestBase {

	@Test(enabled = true, description = "Validate Webpage Title")
	public void myTestB1() {

		try {
			TestBase.test.log(Status.PASS, "This is a logging event for B1, and it passed!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			driver.quit();
//			driver = null;

		}

	}

	@Test(enabled = true, description = "Validate Webpage Title")
	public void myTestB2() {

		try {
//			test.log(Status.DEBUG, "This is a logging event for B2, and it DEBUG!");
//			test.log(Status.ERROR, "This is a logging event for B2, and it ERROR!");
			test.log(Status.FAIL, "This is a logging event for B2, and it FAIL!");
//			test.log(Status.FATAL, "This is a logging event for B2, and it FATAL!");
			test.log(Status.INFO, "This is a logging event for B2, and it INFO!");
			test.log(Status.PASS, "This is a logging event for B2, and it PASS!");
			test.log(Status.SKIP, "This is a logging event for B2, and it SKIP!");
			test.log(Status.WARNING, "This is a logging event for B2, and it WARNING!");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			driver.quit();
//			driver = null;

		}

	}

	@Test(enabled = true, description = "Validate Webpage Title")
	public void myTestB3() {

		try {
			TestBase.test.log(Status.PASS, "This is a logging event for B3, and it passed!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			driver.quit();
//			driver = null;

		}

	}
}
