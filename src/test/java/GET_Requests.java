import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
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

    //NOTE: Working with headers and cache

    @Test(description = "asserting header's content-type")
    void test008(){
        given()
                .get(userURL)
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8")
                .log().headers();
    }

    //Class task: using taskURL validate that server name is "Cowboy"
    @Test()
    void test0081(){
        given()
                .get(taskURL)
                .then()
                .statusCode(200)
                .header("Server", "Cowboy");
//                .header("Server", equalTo("Cowboy"));
    }

    @Test(description = "Extracting header info")
    void test009(){
        Headers headers = given()
                .get(taskURL)
                .getHeaders();

        System.out.println(headers.get("Connection"));
        System.out.println(headers.get("Date"));

        Assert.assertEquals(headers.get("Server").toString(), "Server=Cowboy");
    }

    @Test(description = "Check if cookie contains property connect.sid")
    void test010(){
        given()
                .get(taskURL)
                .then()
                .statusCode(200)
                .cookie("connect.sid")
                .log().cookies();
    }

    @Test(description = "Getting cookie details and storing it")
    void test020(){
        Cookie cookie = given()
                .get("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(200)
                .extract()
                .detailedCookie("__cfduid");


        System.out.println(cookie.getDomain());
        System.out.println(cookie.getValue());
        System.out.println(cookie.getExpiryDate());
    }

    //Class task: using taskURL print out etag of header, content-type of header, print value of cookie and if it is secured
    @Test()
    void test021(){
        Headers headers = given().get(taskURL).getHeaders();
        Cookie cookie = given().get(taskURL).then().extract().detailedCookie("connect.sid");

        System.out.println(headers.get("etag"));
        System.out.println(headers.get("content-type"));

        System.out.println(cookie.getValue());
        System.out.println(cookie.isSecured());
    }

    //NOTE: Parameters

    @Test(description = "path parameter name should be inserted in .get method in {} braces")
    void test030(){
        String baseURL = "https://gorest.co.in/public-api";

        given()
                .pathParam("resourceUser", "users")
                .pathParam("resourceOneuser", "1")
                .when()
                .get(baseURL + "/{resourceUser}/{resourceOneuser}")
                .then()
                .statusCode(200)
                .log()
                .body();
    }

    //NOTE: DataProvider example with path parameter

    @DataProvider(name = "resourceData")
    public Object[] getResources(){
        return new String[]{"users", "posts", "comments", "todos", "categories", "products", "product-categories"};
    }

    @Test(dataProvider = "resourceData")
    void test040(String resource){
        String baseURL = "https://gorest.co.in/public-api";

        given()
                .pathParam("resourceName", resource)
                .when()
                .get(baseURL + "/{resourceName}")
                .then()
                .statusCode(200)
                .log()
                .headers();
    }

    //Class Task: api: https://jsonplaceholder.typicode.com   Using dataProvider get all resources data, verify status code and log body
    @DataProvider(name = "data")
    public Object[] getData(){
        return new String[]{"users", "posts", "comments", "todos", "albums", "photos"};
    }

    @Test(dataProvider = "data")
    void test041(String data){
        String baseURL = "https://jsonplaceholder.typicode.com";
        given()
                .pathParam("resource", data)
                .when()
                .get(baseURL + "/{resource}")
                .then()
                .statusCode(200)
                .log().body();
    }

    //NOTE: Query parameters

    @Test(description = "inserting page=5 as a query parameter")
    void test050(){
        given()
                .queryParam("page", 5)
                .when()
                .get(userURL)
                .then()
                .body("meta.pagination.page",  equalTo(5))
                .log().body();
    }

    //Class task: Find course based on query parameter Ex: Soft Skills
    @Test
    void test051(){
        given()
                .queryParam("name", "Soft Skills")
                .get(taskURL)
                .then()
                .headers("Content-Type", "application/json; charset=utf-8")
                .statusCode(200)
                .body("data[0].name", equalTo("Soft Skills"))
                .log()
                .body();
    }

    @Test(description = "chaining query parameters")
    void test060(){

        given()
                .queryParam("name", new String[]{"API Course", "Basic Math", "RestAssured"})
                .get(taskURL)
                .then()
                .body("data.name", hasItems("API Course", "Basic Math", "RestAssured"))
                .log()
                .body();
    }
    //NOTE: Writing code using variables

    @Test
    void test070(){
        RestAssured.baseURI = "https://tla-school-api.herokuapp.com/api/school/resources";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "students/5f63cc11daf8740017b08e21");

        String body = response.getBody().asString();
        System.out.println(body);

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);

        Assert.assertEquals(statusCode, 200);

        String statusLine = response.getStatusLine();
        System.out.println(statusLine);
    }

}
