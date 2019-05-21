package com.bvc.a2censo.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Screenshot {

    private static final String basePath = "test_info/execution_evidences/"
            + (new SimpleDateFormat("yyyy-MM-dd_HH-mm")).format(new Timestamp(System.currentTimeMillis()))
            + "/";

    public static String takeScreenshot (WebDriver driver, String outputFolder, String name) {
        try {
            String folderPath = basePath + outputFolder + "/";
            File directory = new File(folderPath);
            if (! directory.exists()){
                System.out.println("Evidence folder not exist, creating folder '"+folderPath+"'...");
                directory.mkdirs();
                System.out.println("Folder created...");
            }
            //System.out.println("Taking screenshot...<br>");
            String filePath = folderPath+name+".png";
            File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src,new File(filePath));

            //URL path = new File(filePath).toURI().toURL();
            String path = "../"+filePath;
            String text = "<a href=" + path + "> Click to open screenshot of " + name + "</a>";
            return name + text;
        } catch (Exception e) {
            String err = "Exception while taking screenshot "
                    + e.getMessage();
            System.out.println(err);
            return "err";
        }
    }

}
