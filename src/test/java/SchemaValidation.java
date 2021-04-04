import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidation {

    @Test(description = "Store body as a string and then validata")
    void test01(){
        String responseBody = given()
                .queryParam("name", "Java")
                .get("https://tla-school-api.herokuapp.com/api/school/programs/devcourse")
                .body().asString();

        assertThat(responseBody, matchesJsonSchemaInClasspath("devCourseSchema.json"));
    }

    @Test(description = "without storing as a string")
    void test02(){
        given()
                .queryParam("name", "API-Course")
                .get("https://tla-school-api.herokuapp.com/api/school/programs/devcourse")
                .then()
                .body(matchesJsonSchemaInClasspath("devCourseSchema.json"))
                .log()
                .body();
    }

    //Class task: validate schema of Gorest API, resource users = https://gorest.co.in/public-api/users
    @Test
    void test03(){
        given()
                .get("https://gorest.co.in/public-api/users/14")
                .then()
                .body(matchesJsonSchemaInClasspath("userSchema.json"))
                .log()
                .body();
    }

}
