package StepDefinition;
import com.github.javafaker.Faker;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import org.junit.Assert;

public class caseApiTest {

    Faker faker = new Faker();
    private Response response;
    String name = faker.name().firstName();
    String email = name + "@gmail.com";
    String password = "Testing!2";
    String token;

    @Given("User want to create account in api")
    public void userWantToCreateAccountInApi() {
        RestAssured.baseURI = ("https://apingweb.com/api");
        RestAssured.basePath = "/register";
    }
    @And("User input param for create account in api")
    public void userInputParamForCreateAccountInApi() {
        JSONObject payload = new JSONObject();
        payload.put("name",name);
        payload.put("email",email);
        payload.put("phone","5555551234");
        payload.put("password",password);
        payload.put("password_confirmation",password);
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .post();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200,statusCode);
    }

    @Then("User verify body respond from create account api")
    public void userVerifyBodyRespondFromCreateAccountApi() {
        String responseBody = response.getBody().asString();

        JSONObject jsonResponse = new JSONObject(responseBody);
        boolean success = jsonResponse.getBoolean("success");
        String message = jsonResponse.getString("message");

        Assert.assertTrue(success);
        Assert.assertEquals("Registration success", message);
    }

    @And("User want to login account in api")
    public void userWantToLoginAccountInApi() {
        RestAssured.baseURI = ("https://apingweb.com/api");
        RestAssured.basePath = "/login";
    }

    @And("User input param for login account in api")
    public void userInputParamForLoginAccountInApi() {
        JSONObject payload = new JSONObject();
        payload.put("email",email);
        payload.put("password",password);
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .post();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200,statusCode);
    }

    @Then("User verify body respond from login account api")
    public void userVerifyBodyRespondFromLoginAccountApi() {
        String responseBody = response.getBody().asString();

        JSONObject jsonResponse = new JSONObject(responseBody);
        boolean success = jsonResponse.getBoolean("success");
        String message = jsonResponse.getString("message");

        String emailRespond = jsonResponse.getJSONObject("result").getString("email");
        String nameRespond = jsonResponse.getJSONObject("result").getString("name");
        Assert.assertTrue(success);
        Assert.assertEquals("Login success", message);
        Assert.assertEquals(nameRespond,name);
        Assert.assertEquals(emailRespond,email);
        Assert.assertNotNull(jsonResponse.getString("token"));
    }

    @And("User want to forgot password in api")
    public void userWantToForgotPasswordInApi() {
        RestAssured.baseURI = ("https://apingweb.com/api");
        RestAssured.basePath = "/forgot-password";
    }

    @And("user input param for forgot password in api")
    public void userInputParamForForgotPasswordInApi() {
        JSONObject payload = new JSONObject();
        payload.put("email",email);
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .post();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200,statusCode);
    }

    @Then("User verify body respond from forgot password in api")
    public void userVerifyBodyRespondFromForgotPasswordInApi() {
        String responseBody = response.getBody().asString();

        JSONObject jsonResponse = new JSONObject(responseBody);
        boolean success = jsonResponse.getBoolean("success");
        String message = jsonResponse.getString("message");
        token = jsonResponse.getString("or_use_this_token");
        Assert.assertTrue(success);
        Assert.assertEquals("We have e-mailed your password reset link, check your inbox or spam", message);
        Assert.assertNotNull(jsonResponse.getString("or_use_this_token"));
    }

    @And("User want to reset password in api")
    public void userWantToResetPasswordInApi() {
        RestAssured.baseURI = ("https://apingweb.com/api");
        RestAssured.basePath = "/reset-password";
    }

    @And("user input param for reset password in api")
    public void userInputParamForResetPasswordInApi() {
        String changePassword = "Testing!3";
        JSONObject payload = new JSONObject();
        payload.put("password",changePassword);
        payload.put("password_confirmation",changePassword);
        payload.put("email",email);
        payload.put("token",token);

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .post();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200,statusCode);
    }

    @Then("User verify body respond from reset password in api")
    public void userVerifyBodyRespondFromResetPasswordInApi() {
        String responseBody = response.getBody().asString();
        JSONObject jsonResponse = new JSONObject(responseBody);
        boolean success = jsonResponse.getBoolean("success");
        String message = jsonResponse.getString("message");
        Assert.assertTrue(success);
        Assert.assertEquals("Your password has been changed", message);
    }
}
