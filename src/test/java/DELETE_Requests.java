import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonUtils;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DELETE_Requests {
    String courseURL = "https://tla-school-api.herokuapp.com/api/school/programs/sdetcourse";

    @Test(description = "deleting a course")
    void test01(){
        given()
                .queryParam("name", "Flying")
        .when()
                .delete(courseURL)
        .then()
                .statusCode(200)
                .body("data.deletedCount", equalTo(1))
        .log()
                .all();
    }

    @Test
    void test02(){
        String courseURL = "https://tla-school-api.herokuapp.com/api/school/programs/sdetcourse";
        given()
                .body(ContentType.JSON)
                .queryParam("name","Basic Math")
                .when()
                .delete(courseURL)
                .then()
                .statusCode(200)
                .log()
                .body();
    }
}
