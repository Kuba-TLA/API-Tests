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
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class POST_Requests {
    String taskURL = "https://tla-school-api.herokuapp.com/api/school/programs/sdetcourse";

    @Test(description = "creating a json object using a Map and inserting in json Object")
    void test01(){
        Map<String, String> map  = new HashMap<>();
        map.put("name", "Javascript");
        map.put("duration", "2 months");

        System.out.println(map);

        JSONObject jsonObject = new JSONObject(map);
        System.out.println(jsonObject);
    }

    //Class Task: create a student object or gorest API https://gorest.co.in/public-api/users
    @Test()
    void test011(){
        Map<String, String> map = new HashMap<>();
        map.put("name", "Alex");
        map.put("email", "al@al.com");
        map.put("gender", "not specified");
        map.put("status", "unknown");

        System.out.println(map);
        JSONObject object = new JSONObject(map);
        System.out.println(object);
    }

    @Test(description = "creating a json object without using a map")
    void test02(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Node.js");
        jsonObject.put("duration", "1 month");

        System.out.println(jsonObject);
    }

    @Test(description = "read from .json file")
    void test03(){
        JSONObject jsonObject = JsonUtils.readBodyFromJsonFile("src\\test\\java\\data\\student.json");
        System.out.println(jsonObject);
    }

    //Class Task: Create a json file user.json, add json object compatible with gorest api and read it in test method
    @Test()
    void test04(){
        JSONObject jsonObject = JsonUtils.readBodyFromJsonFile("src\\test\\java\\data\\user.json");
        System.out.println(jsonObject);
    }

    //NOTE: POST Requests -------------------------------------------

    @Test(description = "sending a POST Request using JsonObject")
    void test05(){
        JSONObject newCourse = new JSONObject();
        newCourse.put("name", "Running");
        newCourse.put("duration", "2 months");

        given()
                //.contentType("application/json")
                .contentType(ContentType.JSON)
                .body(newCourse)
                .when()
                .post(taskURL)
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test(description = "Post request with variables + API as a query parameter")
    void test06(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Mike");
        jsonObject.put("lastName", "Jhonson");
        jsonObject.put("email", "mj@test.com");
        jsonObject.put("batch", 3);

        RestAssured.baseURI = "https://tla-school-api.herokuapp.com/api/school/resources";
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.headers("Content-Type", ContentType.JSON);
        httpRequest.queryParam("key", "d03e989018msh7f4691c614e87a9p1a8181j");
        httpRequest.body(jsonObject);

        Response response = httpRequest.request(Method.POST, "/students");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);

        int status = response.getStatusCode();
        System.out.println(status);

        Assert.assertEquals(status, 200);
    }

    @Test(description = "Post request without variables, without much confusion")
    void test07(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Mike T");
        jsonObject.put("lastName", "Jhonson");
        jsonObject.put("email", "mjT@test.com");
        jsonObject.put("batch", 3);

        given()
                .headers("Content-Type", ContentType.JSON)
                .queryParam("key", "d03e989018msh7f4691c614e87a9p1a8181j")
                .body(jsonObject)
                .when()
                .post("https://tla-school-api.herokuapp.com/api/school/resources/students")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test(description = "Bearer token example")
    void test08(){
        String token = "2e93a51c1044d5e261dd2c08198f9a02d1cb00edb22a875c534e1589ff0f8e73";

        Map<String, String> user = new HashMap<>();
        user.put("name", "Alicia");
        user.put("gender", "Female");
        user.put("email", "alicia1@test.com");
        user.put("status", "Active");

        given()
                .headers("Authorization", "Bearer " + token)
                .headers("content-type", ContentType.JSON)
                .body(user)
                .when()
                .post("https://gorest.co.in/public-api/users")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test(description = "basic authorization")
    void test09(){
        String URL = "https://tla-school-api.herokuapp.com/api/school/departments/gettoken";

        given()
                .auth()
                .preemptive()
                .basic("user", "user123")
                .get(URL)
                .then()
                .statusCode(200)
                .log()
                .body();
    }

    //NOTE: Using dataProvider to create multiple courses
    @Test(description = "POST request using DataProvider", dataProvider = "courseData", dataProviderClass = DataProviders.class)
    void test010(String courseName, String duration){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", courseName);
        jsonObject.put("duration", duration);

        given()
                .contentType(ContentType.JSON)
                .body(jsonObject)
                .when()
                .post("https://tla-school-api.herokuapp.com/api/school/programs/devcourse")
                .then()
                .statusCode(200)
                .body("data.name", equalTo(courseName))
                .log().body();
    }






}
