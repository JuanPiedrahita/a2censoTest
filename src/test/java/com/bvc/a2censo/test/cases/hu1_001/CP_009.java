package com.bvc.a2censo.test.cases.hu1_001;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.CustomReporter;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CP_009 extends TestBase {

    @Test(description = "This TC will check a2censo portal ux")
    @Parameters({"browser","hu"})
    public void CP_009(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        CustomReporter.log("Navigating to "+baseUrl);
        this.navegateToLanding();

        CustomReporter.subTitle("Checking UX");
        (new CP_001()).checkUX(testPath,driver,wait,action,jsExecutor);

        CustomReporter.error("UX not available yet");
        Assert.fail("UX not available yet");
    }

}
