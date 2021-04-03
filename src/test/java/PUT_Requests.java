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

public class PUT_Requests {
    String coursesURL = "https://tla-school-api.herokuapp.com/api/school/programs/sdetcourse";

    @Test(description = "updating a course name and duration")
    void test01(){
        Map<String, String> course = new HashMap<>();
        course.put("name", "Flying");
        course.put("duration", "4 months");

        given()
                .headers("content-type", ContentType.JSON)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("name", "API course 3")
                .body(course)
                .when()
                .put(coursesURL)
                .then()
                .statusCode(200)
                .log().all();

    }

    @Test(description = "API key example as a query parameter")
    void test02(){
        JSONObject jsonObject = JsonUtils.readBodyFromJsonFile("src/test/java/data/student.json");
        String existingStudentID = "5f6d17992021da00172f7159";

        given()
                .contentType(ContentType.JSON)
                .queryParam("key", "d03e989018msh7f4691c614e87a9p1a8181j")
                .pathParam("studentID", existingStudentID)
                .body(jsonObject)
                .when()
                .put("https://tla-school-api.herokuapp.com/api/school/resources/students/{studentID}")
                .then()
                .statusCode(200)
                .log()
                .body();
    }

    @Test(description = "API key example as a header parameter")
    void test03(){
        JSONObject jsonObject = JsonUtils.readBodyFromJsonFile("src/test/java/data/student.json");
        String existingInstructorID = "5f6d17992021da00172f7159";

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "d03e989018msh7f4691c614e87a9p1a8181j")
                .pathParam("instructorID", existingInstructorID)
                .body(jsonObject)
                .when()
                .put("https://tla-school-api.herokuapp.com/api/school/resources/instructors/{studentID}")
                .then()
                .statusCode(200)
                .log()
                .body();
    }

}
