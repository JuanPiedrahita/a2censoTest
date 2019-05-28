package com.bvc.a2censo.test.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import sun.misc.BASE64Decoder;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ImageUtils {

    private static final String basePath = "test_info/execution_evidences/"
            + (new SimpleDateFormat("yyyy-MM-dd_HH-mm")).format(new Timestamp(System.currentTimeMillis()))
            + "/";

    private static String getFolderPath(String outputFolder){
        String folderPath = basePath + outputFolder + "/";
        File directory = new File(folderPath);
        if (! directory.exists()){
            System.out.println("Evidence folder not exist, creating folder '"+folderPath+"'...");
            directory.mkdirs();
            System.out.println("Folder created...");
        }
        return folderPath;
    }

    public static String takeScreenshot (WebDriver driver, String outputFolder, String name) {
        try {
            String folderPath = ImageUtils.getFolderPath(outputFolder);
            String filePath = folderPath+name+".png";
            File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src,new File(filePath));
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

    public static String downloadImage(String url, String outputFolder, String imageName, String imageFormat){
        String res = "";
        String folderPath = ImageUtils.getFolderPath(outputFolder);
        String imagePath = folderPath + imageName + "." + imageFormat;
        try{
            BufferedImage image = null;
            if (url.contains("data:")){
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] imageByte = decoder.decodeBuffer(url.split(",")[1]);
                ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                image = ImageIO.read(bis);
                imageFormat = "png";
                imagePath = folderPath + imageName + "." + imageFormat;
                bis.close();
                ImageIO.write(image, imageFormat, new File(imagePath));
            } else {
                URL imageURL = new URL(url);
                FileUtils.copyURLToFile(imageURL, new File(imagePath));
            }
            String path = "../"+imagePath;
            res = "Image \""+imageName+"\" could be downloaded, <a href="+path+">Click here to open it</a>";
        } catch (Exception e){
            e.printStackTrace();
            res = "error";
        } finally {
            return res;
        }
    }

}
