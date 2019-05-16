package com.bvc.a2censo.test.hu1_001;

import com.bvc.a2censo.test.BaseTest;
import com.bvc.a2censo.util.Screenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.swing.*;

public class CP_001 extends BaseTest {

    @Test(description = "This TC will access to a2censo portal")
    @Parameters({"browser","hu"})
    public void CP_001(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();

        Reporter.log("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer+"<br>");

        String url = basePath;

        Reporter.log("Navigating to "+url+"<br>");
        driver.navigate().to(url);

        String ssText = Screenshot.takeScreenshot(driver,testPath,broswer);
        Reporter.log(ssText);
    }

}
