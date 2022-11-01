package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {
    // Set base URL
    final static String ROOT_URI = "https://petstore.swagger.io/v2/user";

    @Test(priority=1)
    public void addNewUserFromFile() throws IOException {
        // Import JSON file
        FileInputStream inputJSON = new FileInputStream("src/test/java/activities/userinfo.json");
        // Read JSON file as String
        String reqBody = new String(inputJSON.readAllBytes());

        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .body(reqBody) // Pass request body from file
                        .when().post(ROOT_URI); // Send POST request

        inputJSON.close();

        // Assertion
        response.then().log().all().body("code", equalTo(200));
        response.then().body("message", equalTo("9909"));
    }

    @Test(priority=2)
    public void getUserInfo() {
        // Import JSON file to write to
        File outputJSON = new File("src/test/java/activities/userGETResponse.json");

        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .pathParam("username", "p9justinc") // Pass request body from file
                        .when().get(ROOT_URI + "/{username}"); // Send POST request

        // Get response body
        String resBody = response.getBody().asPrettyString();

        try {
            // Write response body to external file
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException excp) {
            excp.printStackTrace();
        }

        // Assertion
        response.then().log().all().body("id", equalTo(9909));
        response.then().body("username", equalTo("p9justinc"));
        response.then().body("firstName", equalTo("Justin"));
        response.then().body("lastName", equalTo("Case"));
        response.then().body("email", equalTo("justincase@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9812763450"));
    }

    @Test(priority=3)
    public void deleteUser() {
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .pathParam("username", "p9justinc") // Add path parameter
                        .when().delete(ROOT_URI + "/{username}"); // Send POST request

        // Assertion
        response.then().log().all().body("code", equalTo(200));
        response.then().body("message", equalTo("p9justinc"));
    }
}
