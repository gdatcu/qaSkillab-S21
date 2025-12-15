package ro.hapifyme.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;

public class BaseTest {
    // Inițializăm Logger-ul pentru clasa curentă
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setup(Method method) {
        logger.info("========================================");
        logger.info("Începere Test: " + method.getName());
        logger.info("Base URL setat la: [https://test.hapifyme.com](https://test.hapifyme.com)");
        logger.info("========================================");
    }
}