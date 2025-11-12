package api.test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DDTest {
    @Test(priority = 1, dataProvider = "Data",dataProviderClass = DataProviders.class)
    public void testPostUser(String userID, String userName,String fName, String lName, String userEmail, String userPassword, String ph){
        User userPayload= new User();
        userPayload.setId(Integer.parseInt(userID));
        userPayload.setUsername(userName);
        userPayload.setFirstname(fName);
        userPayload.setLastname(lName);
        userPayload.setEmail(userEmail);
        userPayload.setPassword(userPassword);
        userPayload.setPhone(ph);

        Response response = UserEndpoints.createUser(userPayload);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteUserByName(String userName){

        Response response= UserEndpoints.deleteUser(userName);
        Assert.assertEquals(response.getStatusCode(), 200);

    }
}

