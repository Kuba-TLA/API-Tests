import data.SdetCourse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Serialization {

    @Test
    void test01(){
        SdetCourse sdetCourse = new SdetCourse("Java", "3 months");

        RestAssured.baseURI = "https://tla-school-api.herokuapp.com/api/school/programs/sdetcourse";
        RequestSpecification request = RestAssured.given();

        request
                .contentType(ContentType.JSON)
                .body(sdetCourse)
                .when()
                .post()
                .then()
                .statusCode(200)
                .log()
                .body();
    }
}
