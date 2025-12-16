package ro.hapifyme.tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SystemHealthTest extends BaseTest {

    @Test(description = "Verifică starea de sănătate a sistemului (Health Check)")
    public void testSystemHealth() {
        // Cheie API pentru autorizare (preluată din documentație)
        String apiKey = "3ecace779eb54668082835ffd2e0e2db5389b4571a9973ec5adf894ff35486ab";

        given()
                .baseUri("https://test.hapifyme.com/api")
                .header("Authorization", apiKey)
                // Logăm doar dacă testul eșuează pentru a nu aglomera consola în execuția paralelă
                .log().ifValidationFails()
                .when()
                .get("/system/health_check.php")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("message", equalTo("System health check successful."));

        // Adăugăm un mic delay artificial doar pentru a observa paralelizarea în consolă
        // (În producție nu facem asta, dar aici ajută didactic)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("System Health Check finalizat cu succes pe thread-ul: " + Thread.currentThread().getId());
    }
}