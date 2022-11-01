package project;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

public class GithubRESTTest {

    RequestSpecification reqSpec;
    String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCYCVoWcR5vEsnVXwQ15ItbGasLyhWpeYYK5BCDmmkpW4OLUZLmRYpkEzeKkiE1CjJYX1c68PIdfd4pG4ryzE3Ds0KwHc5ODuVn7fYXCgILJFmGi31f6eArag+LwnmAFYzKRyZT3OSszpPzBITkI38mktC2SoPKXiGinx6BGGOC2GaIUS6uYjKqUcSy5pAIwhgXWqtQInkykDElub+r4PN/gt6waSlRwnKGFmDDYvo9BxvtQBz6tQLiW02NPaApwtzwqzsaSjBmYIzRaj6+qiRewZQYoqlLSE25jmgOwJETJ8ocP21Cvv+CtJRawCStOvK0sHOWNaBvD8M0GaCnmnmh";
    private int keyId;

    @BeforeClass
    public void setUp() {
        reqSpec = new RequestSpecBuilder().setBaseUri("https://api.github.com")
                .setContentType(ContentType.JSON)
                .setAuth(oauth2("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"))
                .build();
        //.addHeader("Authorization", "token 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")
    }

    @Test(priority = 1)
    public void postSSHkey() {

        // Request Body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("title", "TestAPIKey");
        reqBody.put("key", sshKey);
        // Generate Response
        Response response = given().spec(reqSpec).body(reqBody).when().post("/user/keys");
        // extract keyId
        keyId = response.then().log().all().extract().path("id");
        // Assertion
        response.then().statusCode(201);
    }

    @Test(priority = 2)
    public void getSSHkey() {
        // Generate Response
        Response response = given().spec(reqSpec).when().get("/user/keys/" + keyId);
        // Assertion
        response.then().log().all().statusCode(200);
    }

    @Test(priority = 3)
    public void deleteSSHkey() {
        // Generate Response
        Response response = given().spec(reqSpec).when().pathParam("keyId", keyId).delete("/user/keys/{keyId}");
        // Assertion
        response.then().log().all().statusCode(204);
        Reporter.log(response.asPrettyString());
    }

}
