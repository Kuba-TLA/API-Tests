import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GET_Requests {
    String userURL = "https://gorest.co.in/public-api/users";
    String taskURL = "https://tla-school-api.herokuapp.com/api/school/programs/sdetcourse";

    @Test(description = "get request and print out the response body")
    void test001(){
        Response response = RestAssured.get(userURL);
        response.prettyPrint();
    }

    //print body and status code of the response
    @Test()
    void test002(){
        Response response = RestAssured.get(taskURL);
        response.getBody().prettyPrint();
        System.out.println(response.statusCode());
    }

    @Test(description = "asserting that id is equal to expected")
    void test003(){
        given()
                .get(userURL)
                .then()
                .body("data[0].id", equalTo(1));
    }

    @Test(description = "asserting status code is 200")
    void test004(){
        given()
                .get(userURL)
                .then()
                .statusCode(200);
    }

    @Test(description = "asserting status code is 200 and log the info")
    void test005(){
        given()
                .get(userURL)
                .then()
                .statusCode(200)
                .log()
                .body();
    }

    @Test(description = "asserting if data.name properties has given multiple names")
    void test006(){
        given()
                .get(userURL)
                .then()
                .statusCode(200)
                .body("data.name", hasItems("vijitha reddyvari", "Vyctor Hugo", "Bhoj Prajapat III"))
                .log().body();
    }

    //Task: Check taskURL response contains "Basic Math" and "RestAssured"
    @Test
    void test007(){
        given()
                .get(taskURL)
                .then()
                .statusCode(200)
                .body("data.name", hasItems("Basic Math", "RestAssured"));
    }


}
