package ro.hapifyme.tests;

import ro.hapifyme.utils.TestContext;
import io.restassured.RestAssured;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ThreadSafeTest extends BaseTest {

    @Test
    public void testUserFlow() {
        // 1. Simulare Login și salvare Token în Context
        // În realitate, ai face un request POST /login și ai lua token-ul din răspuns
        String generatedToken = "mock_token_thread_" + Thread.currentThread().getId();

        // SALVĂM token-ul în "buzunarul" thread-ului curent
        TestContext.setToken(generatedToken);

        System.out.println("Thread " + Thread.currentThread().getId() + " a setat token: " + generatedToken);

        // 2. Apelăm metode ulterioare FĂRĂ să pasăm token-ul manual
        checkUserProfile();
    }

    // Această metodă poate fi într-o clasă separată (ex: API Actions)
    public void checkUserProfile() {
        // RECUPERĂM token-ul direct din context
        String currentToken = TestContext.getToken();

        System.out.println("Thread " + Thread.currentThread().getId() + " folosește token: " + currentToken);

        given()
                .header("Authorization", "Bearer " + currentToken) // Folosim token-ul corect
                .baseUri("https://test.hapifyme.com/api")
                .when()
                .get("/user/get_profile.php")
                .then()
                // .statusCode(200) // Comentat pentru demo
                .log().ifValidationFails();
    }
}