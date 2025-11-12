package api.test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import api.utilities.ExtentReportManager;

@Listeners(ExtentReportManager.class)
public class UserTests {

    private Faker faker;
    private User userPayload;
    private static final Logger logger = LogManager.getLogger(UserTests.class);

    @BeforeClass
    public void setup() {
        logger.info("===== Starting User API Tests =====");

        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstname(faker.name().firstName());
        userPayload.setLastname(faker.name().lastName());
        userPayload.setEmail(faker.internet().emailAddress());
        userPayload.setPassword(faker.internet().password());
        userPayload.setPhone(faker.phoneNumber().cellPhone());

        logger.debug("Generated User Test Data: {}", userPayload);
        logger.info("Test data initialized successfully.");
    }

    @Test(priority = 1, description = "Create a new user and verify successful creation.")
    public void testPostUser() {
        logger.info("==== [TEST] Creating New User ====");
        Response response = UserEndpoints.createUser(userPayload);

        logger.debug("Response Body: {}", response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200, "User creation failed!");
        logger.info("✅ User created successfully with username: {}", userPayload.getUsername());
    }

    @Test(priority = 2, description = "Fetch and validate user details by username.")
    public void testGetUserByUsername() throws InterruptedException {
        logger.info("==== [TEST] Fetching User Details ====");
        Thread.sleep(1000);

        Response response = UserEndpoints.readUser(userPayload.getUsername());
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(), 200, "User not found!");
        logger.info("✅ User details fetched successfully for username: {}", userPayload.getUsername());
    }

    @Test(priority = 3, description = "Update user details and validate response.")
    public void testUpdateUserByName() {
        logger.info("==== [TEST] Updating User Details ====");

        // Modify user data
        userPayload.setFirstname(faker.name().firstName());
        userPayload.setLastname(faker.name().lastName());
        userPayload.setEmail(faker.internet().emailAddress());

        logger.debug("Updated Payload: {}", userPayload);

        Response response = UserEndpoints.updateUser(this.userPayload.getUsername(), userPayload);
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(), 200, "User update failed!");
        logger.info("✅ User updated successfully for username: {}", userPayload.getUsername());

        // Validate updated user
        Response responseAfterUpdate = UserEndpoints.readUser(userPayload.getUsername());
        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
        logger.info("📄 Fetched updated user details successfully.");
    }

    @Test(priority = 4, description = "Delete user and verify deletion.")
    public void testDeleteUserByUsername() {
        logger.info("==== [TEST] Deleting User ====");
        Response response = UserEndpoints.deleteUser(userPayload.getUsername());
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(), 200, "User deletion failed!");
        logger.warn("⚠️ User deleted: {}", userPayload.getUsername());
        logger.info("==== [TEST COMPLETED] User API Workflow ====");
    }
}
