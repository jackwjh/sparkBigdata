package com.spark.bigdata.util;

import java.io.InputStream;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
public class FileReaderUtil {

    public static String getConfig(String prop, String key) {
        ResourceBundle rb = ResourceBundle.getBundle(prop.trim());
        return rb.getString(key);
    }


    public static void main(String []args){
        System.out.println(FileReaderUtil.getConfig("kafka","brokerList"));
    }

}
