package com.bvc.a2censo.test.cases.hu1_001;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.CustomReporter;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CP_008 extends TestBase {

    @Test(description = "This TC will access to a2censo portal from a client profile")
    @Parameters({"browser","hu"})
    public void CP_008(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        CustomReporter.log("Navigating to "+baseUrl);
        this.navegateToLanding();

        CustomReporter.error("Functionality HU1-008-SC01-CP-002 not available yet");
        Assert.fail("Functionality HU1-008-SC01-CP-002 not available yet");
    }

}
