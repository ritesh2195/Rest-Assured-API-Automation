package com.jira.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class FileReaderUtil {

    private static final String filePath = "src/test/resources/config/config.properties";

    private static final FileReaderUtil fileReader = new FileReaderUtil();

    private Properties properties;

    private FileReaderUtil(){

        try {

            File file = new File(filePath);

            FileInputStream fis = new FileInputStream(file);

            properties = new Properties();

            properties.load(fis);

        } catch (Exception e){

            e.printStackTrace();;
        }

    }


    public static FileReaderUtil getInstance(){

        return fileReader;
    }

    public String getBaseURI(){

        return properties.getProperty("baseURI");
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

}
