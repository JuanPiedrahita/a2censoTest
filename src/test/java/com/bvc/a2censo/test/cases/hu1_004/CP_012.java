package com.bvc.a2censo.test.cases.hu1_004;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CP_012 extends TestBase {

    @Test(description = "This TC will register a pqrs double time")
    @Parameters({"browser","hu"})
    public void CP_005(String broswer, String hu) {
        if (System.getProperty("env").equals("dev")) {
            String testCase = hu+"-"+this.getClass().getSimpleName();
            String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
            String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
            CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

            navegateToLanding();

            String[] pqrsData = ExcelUtils.getData(dataPath+"objects.xlsx","pqrs_data",true)[0];
            CP_005.fillPQRSForm(dataPath,testPath,driver,action,wait,pqrsData);

            CustomReporter.subTitle("Sending pqrs double time");
            String[][] sendPqrsData = ExcelUtils.getData(dataPath+"objects.xlsx","send_pqrs",true);
            WebElement submitButton = TestUtils.getElementWithExcel(driver,sendPqrsData[0][1],sendPqrsData[0][2]);
            action.moveToElement(submitButton).build().perform();
            submitButton.click();

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                Assert.fail("Error waiting");
            }

            if (submitButton.isEnabled()) {
                String msg = "Submit button is enabled after sending pqrs";
                CustomReporter.error(msg);
                CustomReporter.error(ImageUtils.takeScreenshot(driver,testPath,"sumit_button_enabled"));
                Assert.fail(msg);
            } else {
                CustomReporter.log("Submit button is not enabled");
                CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"sumit_button_not_enabled"));
            }

        } else {
            CustomReporter.subTitle("Email verification only works on dev mode");
        }

    }

}
