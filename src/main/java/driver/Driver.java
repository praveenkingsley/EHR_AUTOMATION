package driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.healthgraph.SeleniumFramework.SeleniumExecutor;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.testng.annotations.Test;

public class Driver {

    public static String env = "";
    private static final Logger logger = LogManager.getLogger();

    @Test
    public static void startDriver() {

        try {

            logger.info("");
            logger.info("<<<<<<<<<<>>>>>>>>>>");
            logger.info("STARTING EXECUTION");
            logger.info("<<<<<<<<<<>>>>>>>>>>");

            try {
                Cls_Generic_Methods.environment = Cls_Generic_Methods.getConfigValues("environment").toUpperCase();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            logger.info("Environment selected = " + Cls_Generic_Methods.environment);
            SeleniumExecutor seleniumExecutor = new SeleniumExecutor();
            seleniumExecutor.execute();

            logger.info("<<<<<<<<<<>>>>>>>>>>");
            logger.info("ENDING EXECUTION");
            logger.info("<<<<<<<<<<>>>>>>>>>>");
            logger.info("");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
