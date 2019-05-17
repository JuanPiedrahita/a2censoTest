package com.bvc.a2censo.test.hu1_001;

import com.bvc.a2censo.test.BaseTest;
import com.bvc.a2censo.util.Screenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
        Reporter.log("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer+"<br>");

        Reporter.log("Navigating to "+basePath+"<br>");
        this.navegateToLanding();

        Reporter.log("Title shoud be 'a2censo'<br>");
        Assert.assertTrue(driver.getTitle().equals("a2censo"));

        Reporter.log("Url shoud be '"+basePath+"'<br>");
        Assert.assertTrue(driver.getCurrentUrl().equals(basePath));

        Reporter.log("Scrolling to the end<br>");
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        Reporter.log("Footer is visible?");
        try {
            WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("footer")));
            Reporter.log("true <br>");
            String ssText = Screenshot.takeScreenshot(driver,testPath,"footer_visible");
            Reporter.log(ssText);
        } catch (Exception e) {
            Reporter.log("false <br>");
            String ssText = Screenshot.takeScreenshot(driver,testPath,"footer__not_visible");
            Reporter.log(ssText);
            Assert.fail("Footer is not visible");
        }
    }

}
