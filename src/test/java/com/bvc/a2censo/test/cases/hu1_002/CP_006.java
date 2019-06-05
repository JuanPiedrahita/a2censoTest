package com.bvc.a2censo.test.cases.hu1_002;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.CustomReporter;
import com.bvc.a2censo.test.util.ExcelUtils;
import com.bvc.a2censo.test.util.ImageUtils;
import com.bvc.a2censo.test.util.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CP_006 extends TestBase {

    @Test(description = "This TC will check access to Crear cuenta")
    @Parameters({"browser","hu"})
    public void CP_006(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        navegateToLanding();
        CustomReporter.log("Reading data");
        String[][] data = ExcelUtils.getData(dataPath+"objects.xlsx","buttons",true);
        CustomReporter.subTitle("Validating buttons for crear cuenta");
        for (int i = 0; i < data.length ; i++){
            String[] rowData = data[i];
            String optionName = rowData[0];
            String finalUrl = rowData[6];
            WebElement option = TestUtils.getElementWithExcel(driver,rowData[1],rowData[2]);
            action.moveToElement(option).build().perform();
            wait.until(ExpectedConditions.elementToBeClickable(option));
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,optionName));
            option.click();
            String subOption = rowData[3];
            if(!subOption.equals("") && subOption != null){
                WebElement subButton = TestUtils.getElementWithExcel(driver,rowData[4],rowData[5]);
                wait.until(ExpectedConditions.elementToBeClickable(subButton));
                action.moveToElement(subButton).build().perform();
                CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,optionName+"_modal"));
                CustomReporter.log("Selecting option "+ subOption);
                subButton.click();
            }
            try {
                wait.until(ExpectedConditions.urlMatches(finalUrl));
                CustomReporter.log("Url matches "+finalUrl);
                CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,optionName+"_final_url"));
                driver.navigate().back();
            } catch (Exception e){
                String msg = "Url does not matches"+finalUrl;
                CustomReporter.error(msg);
                CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,optionName+"_error"));
                Assert.fail(msg);
            }
        }

    }

}
