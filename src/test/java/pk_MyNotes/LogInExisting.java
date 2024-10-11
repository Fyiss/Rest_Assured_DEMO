package pk_MyNotes;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LogInExisting {
	@Test
	public static void createToken() {

		RestAssured.baseURI = "https://practice.expandtesting.com/notes/api/";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("email", "darshithtn@gmail.com");
		requestParams.put("password", "123456");
		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		request.body(requestParams.toJSONString());
		// POST the Response
		Response response = request.request(Method.POST, "/users/login");
		// Response response = request.request(Method.POST,"/spree_oauth/token");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		// System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);
		// To get the Token from JSON Response
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String outh_token = jsonPathEvaluator.get("data.token").toString();
		System.out.println("oAuth Token is =>  " + outh_token);
	}

}
