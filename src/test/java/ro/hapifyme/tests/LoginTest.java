package ro.hapifyme.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginWithLog4j() {
        String endpoint = "https://test.hapifyme.com/api/user/login.php";
        String payload = "{\"username\": \"john1765654889247_doe1765654889247\", \"password\": \"5Wp}px<8xUyS\"}";

        logger.info("Pas 1: Pregătire request către endpoint-ul: " + endpoint);
        logger.debug("Payload trimis: " + payload); // Apare doar dacă setăm Level=DEBUG în xml

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(payload)
                .post(endpoint);

        logger.info("Pas 2: Request trimis. Status primit: " + response.getStatusCode());

        if (response.getStatusCode() != 200) {
            logger.error("Login eșuat! Body răspuns: " + response.getBody().asString());
        } else {
            logger.info("Login reușit. Token extras.");
        }

        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("Login Test finalizat cu succes pe thread-ul: " + Thread.currentThread().getId());

    }
}