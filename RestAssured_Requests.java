package tests;

import static org.testng.Assert.assertEquals;

import org.hamcrest.core.IsEqual;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseBodyData;
import junit.framework.Assert;

public class RestAssured_Requests {

	@Test
	public void getAllusers() {

		given().spec(RestApiHelper.getBasicAuth()).when().get("/users").then().statusCode(200).log().all();

	}

	@Test
	public void testPostAndGet() {
		// Send a POST request and capture the response
		Response postResponse = sendPostRequest();

		// Extract necessary data from the POST response
		int userId = extractUserId(postResponse);
		String FirstName = extractFirstName(postResponse);

		// Use the extracted data in a GET request
		GetRequest_Userid(userId);
		GetRequest_FirstName(FirstName);
		PutRequest_Userid(userId);
		DeleteRequest_Userid(userId);

	}

	public Response sendPostRequest() {
		// Send a POST request and return the response
		JSONObject request = new JSONObject();
		request.put("user_first_name", "sree");
		request.put("user_last_name", "vidya");
		request.put("user_contact_number", "9867857432");
		request.put("user_email_id", "sree.vidya1@gmail.com");
		JSONObject address = new JSONObject();
		address.put("plotNumber", "596-D");
		address.put("street", "Maple Road");
		address.put("state", "Georgia");
		address.put("country", "USA");
		address.put("zipCode", "34567");
		request.put("userAddress", address);
		request.put("userAddress", new JSONObject(address));

		System.out.println("Request body:\n" + request.toJSONString());

		Response response = given().spec(RestApiHelper.getBasicAuth()).contentType(ContentType.JSON)
				.body(request.toJSONString()).when().post("/createusers");

		System.out.println("Response Body:\n" + response.getBody().asString());

		assertEquals(response.getStatusCode(), 201);
		Assert.assertEquals("sree", response.jsonPath().getString("user_first_name"));
		Assert.assertEquals("vidya", response.jsonPath().getString("user_last_name"));
		Assert.assertEquals("sree.vidya1@gmail.com", response.jsonPath().getString("user_email_id"));
		Assert.assertEquals("9867857432", response.jsonPath().getString("user_contact_number"));
		return response;
	}

	public int extractUserId(Response postResponse) {
		// Extract necessary data from the POST response and return it
		return postResponse.jsonPath().getInt("user_id");

	}

	public String extractFirstName(Response postResponse) {
		// Extract necessary data from the POST response and return it
		return postResponse.jsonPath().getString("user_first_name");

	}

	public void GetRequest_Userid(int user_id) {
		Response response = given().spec(RestApiHelper.getBasicAuth()).contentType(ContentType.JSON).when()
				.get("/user/" + user_id);
		System.out.println("Get user by userid: " + user_id + "\n" + response.getBody().asString());
		System.out.println(response.getStatusLine());
		Assert.assertEquals(response.getStatusCode(), 200);

	}

	public void GetRequest_FirstName(String first_name) {
		Response response = given().spec(RestApiHelper.getBasicAuth()).contentType(ContentType.JSON).when()
				.get("/users/username/" + first_name);
		System.out.println("Get user by user first_name: " + first_name + "\n" + response.getBody().asString());
		System.out.println(response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200);

	}

	public void PutRequest_Userid(int user_id) {
		JSONObject request = new JSONObject();
		request.put("user_first_name", "sreevidya");
		request.put("user_last_name", "malineni");
		request.put("user_contact_number", "9869657432");
		request.put("user_email_id", "sree.vidya.malineni.65@gmail.com");
		JSONObject address = new JSONObject();
		address.put("plotNumber", "556-D");
		address.put("street", "Beach Road");
		address.put("state", "Florida");
		address.put("country", "USA");
		address.put("zipCode", "34197");
		request.put("userAddress", address);
		request.put("userAddress", new JSONObject(address));

		System.out.println("Put Request body:\n" + request.toJSONString());

		Response response = given().spec(RestApiHelper.getBasicAuth()).contentType(ContentType.JSON)
				.body(request.toJSONString()).when().put("/updateuser/" + user_id);

		System.out.println("Updated User details using userid: " + user_id + "\n" + response.getBody().asString());

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(request.get("user_first_name"), response.jsonPath().getString("user_first_name"));
		Assert.assertEquals(request.get("user_last_name"), response.jsonPath().getString("user_last_name"));
		Assert.assertEquals(request.get("user_email_id"), response.jsonPath().getString("user_email_id"));
		Assert.assertEquals(request.get("user_contact_number"), response.jsonPath().getString("user_contact_number"));
		Assert.assertEquals(request.get("plotNumber"), response.jsonPath().getString("plotNumber"));
		Assert.assertEquals(request.get("street"), response.jsonPath().getString("street"));
		Assert.assertEquals(request.get("state"), response.jsonPath().getString("state"));
		Assert.assertEquals(request.get("country"), response.jsonPath().getString("country"));
		Assert.assertEquals(request.get("zipCode"), response.jsonPath().getString("zipCode"));

	}

	public void DeleteRequest_Userid(int user_id) {
		Response response = given().spec(RestApiHelper.getBasicAuth()).contentType(ContentType.JSON).when()
				.delete("/deleteuser/" + user_id);
		System.out.println("Deleted user by userid: " + user_id + "\n" + response.getBody().asString());
		System.out.println(response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200);

	}

}
