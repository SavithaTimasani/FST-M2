package project;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {

    // Create Map for the headers
    Map<String, String> headers = new HashMap<>();
    // Set resource URI
    String createUser = "/api/users";

    // Create Pact contract
    @Pact(provider = "UserProvider", consumer = "UserConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) throws ParseException {
        // Add headers
        headers.put("Content-Type", "application/json");

        // Create JSON body, Here request-response body are same,hence creating single object
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id", 1)
                .stringType("firstName", "Pawan")
                .stringType("lastName", "Gupta")
                .stringType("email", "pawan@example.com");

        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                    .path(createUser)
                    .method("POST")
                    .headers(headers)
                    .body(requestResponseBody)
                .willRespondWith()
                    .status(201)
                    .body(requestResponseBody)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "UserProvider", port = "8080")
    public void runTest() {

        // Base URI
        String baseURI = "http://localhost:8080" + createUser;
        // Create request body
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("firstName", "Pawan");
        map.put("lastName", "Gupta");
        map.put("email", "pawan@example.com");

        given().headers(headers).body(map).log().all()
                .when().post(baseURI)
                .then().statusCode(201).log().all();
    }

}
