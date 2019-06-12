package com.bvc.a2censo.test.cases.hu1_004;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.CustomReporter;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CP_007 extends TestBase {

    @Test(description = "This TC will check terms and policies")
    @Parameters({"browser","hu"})
    public void CP_004(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        navegateToLanding();

        CustomReporter.error("Functionality HU1-008-SC01-CP-002 not available yet");
        Assert.fail("Functionality HU1-008-SC01-CP-002 not available yet");

    }

}
