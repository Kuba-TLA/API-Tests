package data;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "courseData")
    public Object[][] courseData(){
        Object[][] data = new Object[2][2];

        data[0][0] = "Synchronization";
        data[0][1] = "1 day";

        data[1][0] = "iFrames2";
        data[1][1] = "2 hours";

        return data;
    }
}
