package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class FileReader {

    private static final String filePath = "src/test/resources/TestData/config.properties";

    private static final FileReader  fileReader = new FileReader();

    private Properties properties;

    private FileReader(){

        try {

            File file = new File(filePath);

            FileInputStream fis = new FileInputStream(file);

            properties = new Properties();

            properties.load(fis);

        } catch (Exception e){

            e.printStackTrace();;
        }

    }


    public static FileReader getInstance(){

        return fileReader;
    }

    public String getEndPoint(){

        return properties.getProperty("endPoint");
    }

    public String getUserName(){

        return properties.getProperty("userName");
    }

    public String getPassword(){

        return properties.getProperty("password");
    }

    public String getExcelFilePath(){

        return properties.getProperty("excelFilePath");
    }

    public String getAttachmentFilePath(){

        return properties.getProperty("attachmentFilePath");
    }

}
