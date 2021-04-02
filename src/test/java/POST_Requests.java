import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

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


}
