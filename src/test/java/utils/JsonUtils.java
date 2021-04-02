package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonUtils {

    public static JSONObject readBodyFromJsonFile(String filePath){
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try{
            Object object = parser.parse(new FileReader(filePath));
            jsonObject  = (JSONObject) object;
        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

}
