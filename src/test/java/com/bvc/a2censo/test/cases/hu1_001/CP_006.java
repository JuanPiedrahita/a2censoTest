package com.bvc.a2censo.test.cases.hu1_001;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.CustomReporter;
import com.bvc.a2censo.test.util.ExcelUtils;
import com.bvc.a2censo.test.util.ImageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

public class CP_006 extends TestBase {

    @Test(description = "This TC will check that images from excel file can not be downloaded")
    @Parameters({"browser","hu"})
    public void CP_006_from_file(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        CustomReporter.log("Navigating to "+baseUrl);
        this.navegateToLanding();

        CustomReporter.subTitle("Validating that bvc resources can not be downloaded");
        String[][] data = ExcelUtils.getData(dataPath+"objects.xlsx","images",true);

        for (int i = 0; i < data.length ; i++){
            String[] row = data[i];
            String imageName = row[0];
            String imageUrl = row[1];
            String imageFormat = row[2];
            CustomReporter.log("Trying to download image: "+imageName);
            String result = ImageUtils.downloadImage(baseUrl+imageUrl, testPath, imageName, imageFormat);
            if(!result.equals("error")){
                CustomReporter.error(result);
                Assert.fail("Image can be downloaded");
            } else {
                CustomReporter.log("Image could not be downloaded");
            }
        }

    }

    @Test(description = "This TC will check that images from page can not be downloaded")
    @Parameters({"browser","hu"})
    public void CP_006_from_tags(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        CustomReporter.log("Navigating to "+baseUrl);
        this.navegateToLanding();

        CustomReporter.subTitle("Validating that bvc resources can not be downloaded");

        List<WebElement> images = driver.findElements(By.tagName("img"));

        for (int i = 0; i < images.size() ; i++){
            WebElement image = images.get(i);
            String imageName = "image_from_tag"+(i+1);
            String imageUrl = image.getAttribute("src");
            if (!imageUrl.equals("") && imageUrl!=null) {
                String imageFormat = imageUrl.split("\\.")[imageUrl.split("\\.").length - 1];
                CustomReporter.log("Trying to download image: " + imageName);
                String result = ImageUtils.downloadImage(imageUrl, testPath, imageName, imageFormat);
                if (!result.equals("error")) {
                    CustomReporter.error(result);
                } else {
                    CustomReporter.log("Image could not be downloaded");
                }
            }
        }

    }
}
