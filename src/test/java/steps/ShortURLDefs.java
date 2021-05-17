package steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.app.neueda.model.RequestURL;
import com.app.neueda.model.URLMappingRecord;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class ShortURLDefs {
	private static final String USER_ID = "9b5f49ab-eea9-45f4-9d66-bcf56a531b85";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin";
	private static final String BASE_URL = "http://localhost:5000/";

	private static Response response;

	private static String token;
	
	private static String shortURL;
	
	private static String endpoint;

	@Given("I am an API user {string}")
	public void iAmAnAPI(String user) {

		token = "Authorization: Basic YWRtaW46YWRtaW4=";
	}

	@When("I hit the API at {string}")
	public void iHitTheAPIAt(String endpoint) {
		ShortURLDefs.endpoint = endpoint;
	}

	@And("send JSON containing url {string}")
	public void sendJSONContainingUrl(String url) {
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.body(new RequestURL(url));
		Response response = httpRequest.post(endpoint);

		// Retrieve the body of the Response
		ResponseBody body = response.getBody();

		// To check for sub string presence get the Response body as a String.
		// Do a String.contains
		URLMappingRecord URLMappingRecordObject = body.as(URLMappingRecord.class);
		shortURL = URLMappingRecordObject.getShortURL();
	}

	@Then("generate a short URL {string}")
	public void generateAShortURL(String url) {
		assertEquals("The generated URL does not match the expectation.", shortURL, url);
	}

	@Given("I am any internet user")
	public void iAmAnyInternetUser() {
		System.out.println("I am a normal user");
	}

	@When("I enter the URL {string} in the browser")
	public void iEnterTheURLInTheBrowser(String url) {
		System.out.println(String.format("I just clicked on the url: %s", url));
	}

	@Then("redirect me to {string}")
	public void redirectMeTo(String url) {
		System.out.println(String.format("And I am redirected to the URL: %s", url));
	}
}
