package com.bvc.a2censo.test.cases.hu1_001;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.cases.gui.UXTest;
import com.bvc.a2censo.test.util.CustomReporter;
import com.bvc.a2censo.test.util.Screenshot;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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
        checkScrolling(testPath);

        CustomReporter.subTitle("Checking UX");
        checkUX(testPath);
    }

    public void checkUX(String testPath){
        UXTest.checkHeaderScroll(driver, wait, testPath);
        UXTest.checkFooterStyles(driver,wait,action,testPath);
        UXTest.checkHeaderStyles(driver,wait,action,testPath);
    }

    public void checkScrolling (String testPath) {
        try {
            CustomReporter.log("Scrolling to the top");
            jsExecutor.executeScript("window.scrollTo(0, 0)");
            Thread.sleep(3000);
            CustomReporter.log(Screenshot.takeScreenshot(driver,testPath,"scroll_to_top"));
            CustomReporter.log("Scrolling to the end");
            jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(2000);
            CustomReporter.log(Screenshot.takeScreenshot(driver,testPath,"scroll_to_footer"));
            CustomReporter.log("Scrolling to the top");
            jsExecutor.executeScript("window.scrollTo(0, 0)");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
