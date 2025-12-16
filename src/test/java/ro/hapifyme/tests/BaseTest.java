package ro.hapifyme.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ro.hapifyme.utils.TestContext;
import java.lang.reflect.Method;

public class BaseTest {
    // Inițializăm Logger-ul (Log4j)
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setup(Method method) {
        // Logăm informațiile esențiale la începutul fiecărui test
        logger.info("========================================");
        logger.info("Începere Test: " + method.getName());
        // Adăugăm ID-ul thread-ului pentru a putea depana execuția paralelă
        logger.info("Running on Thread ID: " + Thread.currentThread().getId());
        logger.info("Base URL: https://test.hapifyme.com");
        logger.info("========================================");
    }

    @AfterMethod
    public void tearDown() {
        // Critic pentru paralelizare: Curățăm datele din ThreadLocal
        // Dacă nu facem asta, următorul test care refolosește acest thread ar putea vedea date vechi
        TestContext.clear();
        logger.debug("Context curățat pentru Thread ID: " + Thread.currentThread().getId());
    }
}
