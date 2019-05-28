package com.bvc.a2censo.test.cases.hu1_001;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.CustomReporter;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CP_005 extends TestBase {

    @Test(description = "This TC will check the menu function and content")
    @Parameters({"browser","hu"})
    public void CP_005(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        CustomReporter.log("Navigating to "+baseUrl);
        this.navegateToLanding();

        CustomReporter.subTitle("Validating vitrina redirection");
        checkPageContent("objects","vitrina_links",testPath,dataPath);

        CustomReporter.subTitle("Checking vitrina access with open cases");
        CustomReporter.error("Functionality not available yet");
        Assert.fail("Functionality not available yet");

    }

}
