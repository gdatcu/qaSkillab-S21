package ro.hapifyme.tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("hapifyMe Core")
@Feature("Authentication Module")
public class HapifyMeLoginTest extends BaseTest {

    @Test(priority = 1, description = "Validare Login API cu user corect")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Acest test verifică endpoint-ul /api/user/login.php. Se așteaptă un token JWT în răspuns.")
    @Story("JIRA-101: Ca utilizator, vreau să mă pot loga.")
    public void testValidLogin() {
        // Logica testului este spartă în pași
        String username = "john1765654889247_doe1765654889247";
        String password = "5Wp}px<8xUyS";

        // Pas 1: Acțiunea
        Response response = performLogin(username, password);

        // Pas 2: Verificarea
        // Apelăm noul pas de verificare a performanței
        verifyResponseTime(response);
        verifyLoginSuccess(response);
    }

    // Adnotarea @Step face ca această metodă să apară în raport ca o acțiune distinctă
    // {0} și {1} sunt înlocuite cu valorile parametrilor (username, password)
    @Step("Trimite cerere POST login pentru user: {0} și parola: {1}")
    public Response performLogin(String user, String pass) {
        String endpoint = "https://test.hapifyme.com/api/user/login.php";
        String payload = "{\"username\": \"" + user + "\", \"password\": \"" + pass + "\"}";

        // Logăm și pentru debug (vezi Cap 1)
        logger.info("Se execută pasul Allure: performLogin");

        // Atașăm request-ul în raport (pentru debugging vizual)
        attachTextToReport("Request Payload", payload);

        return RestAssured.given()
                .contentType("application/json")
                .body(payload)
                .post(endpoint);
    }

    @Step("Validează status 200 și prezența token-ului")
    public void verifyLoginSuccess(Response response) {
        // Atașăm răspunsul complet în raport
        attachTextToReport("API Response", response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200, "Status code incorect!");
        String token = response.jsonPath().getString("token");

        Assert.assertNotNull(token, "Token-ul nu a fost returnat!");
        logger.info("Validare reușită. Token: " + token);
    }

    @Step("Verificare timp de răspuns (trebuie să fie sub 2000ms)")
    public void verifyResponseTime(Response response) {
        long timeInMs = response.time();
        logger.info("Timpul de răspuns a fost: " + timeInMs + " ms");

        // Atașăm valoarea exactă în raport pentru claritate
        attachTextToReport("Timp măsurat", String.valueOf(timeInMs) + " ms");

        Assert.assertTrue(timeInMs < 2000,
                "Performanță slabă! Timpul de răspuns (" + timeInMs + "ms) a depășit limita de 2000ms.");
    }

    // Metodă ajutătoare pentru a atașa text (JSON, Logs) în raportul Allure
    @Attachment(value = "{0}", type = "text/plain")
    public String attachTextToReport(String attachmentName, String message) {
        return message; // Allure ia valoarea returnată și o pune în raport
    }
}