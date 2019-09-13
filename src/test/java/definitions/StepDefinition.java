package definitions;


import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.integrationTestCucumber.IntegrationTestCucumberApplicationTests;
import com.integrationTestCucumber.model.Person;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.JsonObject;
import model.Constants;
import model.PersonInsert;


@Ignore
public class StepDefinition extends IntegrationTestCucumberApplicationTests{
	@Autowired
    private TestRestTemplate testRestTemplate;
	private PersonInsert person = null;
	JsonObject responseFromInsert = null;
	JsonObject responseFromGet = null;
	HttpEntity<Person> request = null;
	ResponseEntity<String> result = null;



	@Given("^The endpoint host \"([^\"]*)\"$")
	public void the_endpoint_host(String arg1) throws Throwable {

		Constants.baseUri = arg1+":"+ port +"/";
		System.out.println(Constants.baseUri);
	}

	@When("The client make a {string} request at {string}")
	public void the_client_make_a_request_at(String string, String string2) {
		Constants.url = buildPath(string2);
		Constants.RequestMethod = string.toUpperCase();
	}
	
	@When("Sending the required attribute name {string} lastname {string}")
	public void sending_the_required_attribute_name_lastname(String string, String string2) {
		person = new PersonInsert();
		if(isString(string)) {
			person.setName(string);
			
		}
		if(isString(string2)) {
			person.setLastname(string2);
		}
	}


	@Then("^The status response should be (\\d+)$")
	public void the_status_response_should_be(int arg1) throws Throwable {
		
		int statusCode = 0;
		
	    if(Constants.RequestMethod.equals("GET")) {
			   request = null;
			   result = testRestTemplate.exchange(Constants.url, HttpMethod.GET, request, String.class);
			   System.out.println(result.getBody());
		       responseFromGet = convertToJson(result.getBody());
		       statusCode = result.getStatusCode().value();
		   
	    }
	    else {
			   result = testRestTemplate.postForEntity(Constants.url, person, String.class);
			   JsonObject json = convertToJson(result.getBody());
			   statusCode = result.getStatusCode().value();
			   if(statusCode == 200) {
				   Constants.personId = json.get("personId").getAsString();
			   }
	    }
		//Validation if status code is correct
		assertEquals(arg1, statusCode);
	

	}
	
	@Then("The response message status is {string}")
	public void the_response_message_status_is(String string)  {
				JsonObject jsonResponse = convertToJson(result.getBody());
				System.out.println(jsonResponse.has("status"));
				assertEquals(Boolean.parseBoolean(string), jsonResponse.get("status").getAsBoolean());
	}
	
	@Then("Verify if the response meets the expected schema {string}")
	public void verify_if_the_response_meets_the_expected_schema(String fileSchema) throws Exception {
				Path pathJsonSchema = Paths.get(fileSchema);
				if(!Files.exists(pathJsonSchema)){
		            throw new Exception("Schema File doesnt exist: " + fileSchema);
		        }
				System.out.println("Path:" + pathJsonSchema);
				try (InputStream inputStream = getClass().getResourceAsStream(fileSchema)) {
					  JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
					  Schema schema = SchemaLoader.load(rawSchema);
					  schema.validate(responseFromInsert); // throws a ValidationException if this object is invalid
					}catch (Exception ex){
		                System.out.println(ex.getMessage());
		            }
		
	}	


	@When("Insert the personId on the path")
	public void insert_the_personId_on_the_path() {
			    Constants.url= Constants.url + Constants.personId;
			    System.out.println(Constants.url);
	}



	@Then("The response must be contains name {string} and lastname {string}")
	public void the_response_must_be_contains_name_and_lastname(String string, String string2) {
	    	   String name = responseFromGet.get("person").getAsJsonObject().get("name").getAsString();
	    	   String lastname = responseFromGet.get("person").getAsJsonObject().get("lastname").getAsString();
			   assertEquals(string, name);
		       assertEquals(string2,lastname);
	}
	

	
	public String buildPath(String path) {
		String fullPath = Constants.baseUri + path;
		return fullPath;
	}
	
	public boolean isString(String attribute) {
		boolean result = false;
		if(attribute != null && !attribute.trim().isEmpty()) {
			result = true;
		}
		return result;
	}
	
	public JsonObject convertToJson(String string) {
		JsonObject jsonResponse = new Gson().fromJson(string, JsonObject.class);
		return jsonResponse;
	}
}

