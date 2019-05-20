package com.bvc.a2censo.test.hu1_001;

import com.bvc.a2censo.test.BaseTest;
import com.bvc.a2censo.util.ExcelUtils;
import com.bvc.a2censo.util.Screenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
        String dataPath = dataBasePath+"/"+hu+"/";
        Reporter.log("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer+"<br>");

        Reporter.log("Navigating to "+basePath+"<br>");
        this.navegateToLanding();

        Reporter.log("Title shoud be 'a2censo'<br>");
        Assert.assertTrue(driver.getTitle().equals("a2censo"));

        Reporter.log("Url shoud be '"+basePath+"'<br>");
        Assert.assertTrue(driver.getCurrentUrl().equals(basePath));

        Reporter.log("Validating menu content<br>");
        checkPageContent("object","menu",testPath,dataPath);
        Reporter.log("Validating body content<br>");
        checkPageContent("object","body",testPath,dataPath);
        checkScrolling(testPath);
    }

    public void checkScrolling (String testPath) {
        try {
            Reporter.log("Scrolling to the top<br>");
            jsExecutor.executeScript("window.scrollTo(0, 0)");
            Thread.sleep(2000);
            Reporter.log(Screenshot.takeScreenshot(driver,testPath,"scroll_to_top"));
            Reporter.log("Scrolling to the end<br>");
            jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(2000);
            Reporter.log(Screenshot.takeScreenshot(driver,testPath,"scroll_to_footer"));
            Reporter.log("Scrolling to the top<br>");
            jsExecutor.executeScript("window.scrollTo(0, 0)");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
