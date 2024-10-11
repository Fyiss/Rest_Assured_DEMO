package pk_HotelBooking;

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Create_Booking_Using_Json_Multiple {
	private static final String BASE_URI = "https://restful-booker.herokuapp.com";
	private static final String CONTENT_TYPE = "application/json";
	private static final String BOOKING_ENDPOINT = "/booking";
	private static final String TEST_DATA_PATH = ".\\TestData\\CreateBooking_MultipleData.json";

	@Test
	public void createBookings() {
		JSONArray bookingsData = readJsonFile(TEST_DATA_PATH);

		for (Object bookingObj : bookingsData) {
			JSONObject bookingData = (JSONObject) bookingObj;
			createBooking(bookingData);
		}
	}

	private void createBooking(JSONObject bookingData) {
		RestAssured.baseURI = BASE_URI;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", CONTENT_TYPE);
		request.body(bookingData.toJSONString());

		Response response = request.request(Method.POST, BOOKING_ENDPOINT);
		response.prettyPrint();

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Expected status code 200");

		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		String fname = jsonPathEvaluator.get("booking.firstname").toString();
		String lname = jsonPathEvaluator.get("booking.lastname").toString();

		System.out.println("First Name is =>  " + fname);
		System.out.println("Last Name is =>  " + lname);

		Assert.assertEquals(bookingData.get("firstname"), fname);
		Assert.assertEquals(bookingData.get("lastname"), lname);
	}

	private JSONArray readJsonFile(String filePath) {
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(filePath)) {
			Object obj = jsonParser.parse(reader);
			return (JSONArray) obj;
		} catch (IOException | org.json.simple.parser.ParseException e) {
			throw new RuntimeException("Failed to read JSON file: " + e.getMessage(), e);
		}
	}
}
