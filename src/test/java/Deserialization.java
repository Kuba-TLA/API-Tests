import data.DataProviders;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Deserialization {
    String taskURL = "https://tla-school-api.herokuapp.com/api/school/programs/sdetcourse";

    //NOTE: String some values of a response in a variable - aka Parse Json Response using "extract" method

    @Test(description = "parse into a String")
    void test01(){
        String name =
                given()
                        .queryParam("name", "Basic Math")
                        .get(taskURL)
                        .then()
                        .statusCode(200)
                        .body("data[0].name", equalTo("Basic Math"))
                        .extract()
                        .path("data[0].name");

        System.out.println(name);
    }

    int id;
    @Test(description = "extract int id")
    void test02(){
        Map<String, String> user = new HashMap<>();
        user.put("name", "Alicia");
        user.put("gender", "Female");
        user.put("email", "alicia4@test.com");
        user.put("status", "Active");

        id = given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2("2e93a51c1044d5e261dd2c08198f9a02d1cb00edb22a875c534e1589ff0f8e73")
                .body(user)
                .when()
                .post("https://gorest.co.in/public-api/users")
                .then()
                .statusCode(200)
                .extract()
                .path("data.id");

        System.out.println(id);
    }

    //Class task: from SDETCourse api find "RestAssured" course, extract and print id and duration of that course
    @Test()
    void test03(){
        String endPoint = "https://tla-school-api.herokuapp.com/api/school/programs/sdetcourse";

        String id = given()
                .queryParam("name", "RestAssured")
                .get(endPoint)
                .then()
                .extract()
                .path("data[0]._id");

        String duration =
                given()
                .queryParam("name", "RestAssured")
                .get(endPoint)
                .then()
                .extract()
                .path("data[0].duration");

        System.out.println("id: " + id);
        System.out.println("duration: " + duration);
    }

    @Test(description = "getting id's of all students")
    void test04(){
        List<String> id = given()
                .queryParam("firstName", "Wilson")
                .get("https://tla-school-api.herokuapp.com/api/school/resources/students")
                .then()
                .statusCode(200)
                .extract()
                .path("data._id");

        id.forEach(each -> System.out.println(each));
    }

    //NOTE: Parsing using .jsonPath() method

    @Test(description = "using JSON Path")
    void test05(){
        String endPoint = "https://jsonplaceholder.typicode.com/users/1";

        String companyName = given()
                .get(endPoint)
                .jsonPath()
                .getString("company.name");

        System.out.println(companyName);
    }

    @Test(description = "getting list of all names")
    void test06(){
        String endPoint = "https://jsonplaceholder.typicode.com/users";

        List<String> names = given()
                .get(endPoint)
                .jsonPath()
                .getList("company.name");

        for(int i = 0; i < names.size(); i++){
            System.out.println(names.get(i));
        }
    }

    @Test(description = "getting part of resonse as a Map")
    void test07(){
        String endPoint = "https://jsonplaceholder.typicode.com/users/1";

        Map<String, String> company = given()
                .get(endPoint)
                .jsonPath()
                .getMap("company");

        System.out.println(company);
    }

    @Test(description = "getting part of resonse as a list of Map")
    void test08(){
        String endPoint = "https://jsonplaceholder.typicode.com/users";

        List<Map<String, String>> company = given()
                .get(endPoint)
                .jsonPath()
                .getList("company");

        for(int i = 0; i < company.size(); i++){
            System.out.println(company.get(i));
            //company.get(i).forEach((key, value) -> System.out.println("key: " + key + " | value: " + value));
            System.out.println("-----------------");
        }
    }

}
