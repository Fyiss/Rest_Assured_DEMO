package pk_MyNotes;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Create2 {
    private static String outh_token; // Store the token here

    @Test
    public void createNote() {
        // Step 1: Get the authentication token
    	outh_token = createToken();

        // Step 2: Base URI for the API
        RestAssured.baseURI = "https://practice.expandtesting.com/notes/api/";
        RequestSpecification request = RestAssured.given();

        // Create a JSON object for the note details
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "My Note Title");
        requestParams.put("description", "This is the content of the note.");
        requestParams.put("category", "This is the category of the note.");

        // Set the content type and body
        request.header("Content-Type", "application/json");
        request.header("x-auth-token", outh_token); // Add the authentication token here
        request.body(requestParams.toJSONString());

        // Send POST request to create a note
        Response response = request.request(Method.POST, "/notes");

        // Print the response for debugging
        response.prettyPrint();

        // Assert the status code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200); // Adjust based on expected success status

        // Extracting note ID or other relevant information if needed
        JsonPath jsonPathEvaluator = response.getBody().jsonPath();
        String noteId = jsonPathEvaluator.get("data.id").toString(); // Adjust the key based on response
        System.out.println("Created Note ID is => " + noteId);
    }

    private String createToken() {
        // Base URI for authentication
        RestAssured.baseURI = "https://practice.expandtesting.com/notes/api/";
        RequestSpecification request = RestAssured.given();

        // Create a JSON object for the authentication details
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "yourUsername"); // Replace with actual username
        requestParams.put("password", "yourPassword"); // Replace with actual password

        // Set the content type and body
        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        // Send POST request to authenticate
        Response response = request.request(Method.POST, "/auth");

        // Print the response for debugging
        response.prettyPrint();

        // Assert the status code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200); // Adjust based on expected success status

        // Extracting token from the response
        JsonPath jsonPathEvaluator = response.getBody().jsonPath();
        return jsonPathEvaluator.get("data.id").toString(); // Adjust based on response
    }
}
