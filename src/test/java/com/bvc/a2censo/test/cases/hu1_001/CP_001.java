package com.bvc.a2censo.test.cases.hu1_001;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.cases.gui.UXTest;
import com.bvc.a2censo.test.util.CustomReporter;
import com.bvc.a2censo.test.util.ImageUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.Driver;

public class CP_001 extends TestBase {

    @Test(description = "This TC will access to a2censo portal")
    @Parameters({"browser","hu"})
    public void CP_001(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        CustomReporter.log("Navigating to "+baseUrl);
        this.navegateToLanding();

        CustomReporter.log("Title shoud be 'a2censo'");
        Assert.assertTrue(driver.getTitle().equals("a2censo"));

        CustomReporter.log("Url shoud be '"+baseUrl);
        Assert.assertTrue(driver.getCurrentUrl().equals(baseUrl));

        CustomReporter.subTitle("Validating menu content");
        checkPageContent("objects","menu",testPath,dataPath);
        CustomReporter.subTitle("Validating body content");
        checkPageContent("objects","body",testPath,dataPath);

        CustomReporter.subTitle("Checking UX");
        checkUX(testPath,driver,wait,action,jsExecutor);
    }

    public void checkUX(String testPath, WebDriver driver, WebDriverWait wait, Actions action, JavascriptExecutor jsExecutor){
        checkScrolling(testPath,jsExecutor);
        UXTest.checkHeaderScroll(driver, wait, testPath);
        UXTest.checkHeaderStyles(driver,wait,action,testPath);
        UXTest.checkFooterStyles(driver,wait,action,testPath);
    }

    public void checkScrolling (String testPath, JavascriptExecutor jsExecutor) {
        try {
            CustomReporter.subSubTitle("Checking scrolling");
            CustomReporter.log("Scrolling to the top");
            jsExecutor.executeScript("window.scrollTo(0, 0)");
            Thread.sleep(3000);
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"scroll_to_top"));
            CustomReporter.log("Scrolling to the end");
            jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(2000);
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"scroll_to_footer"));
            CustomReporter.log("Scrolling to the top");
            jsExecutor.executeScript("window.scrollTo(0, 0)");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
