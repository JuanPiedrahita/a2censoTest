package com.bvc.a2censo.test.cases.hu1_001;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.CustomReporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CP_003 extends TestBase {

    @Test(description = "This TC will check the contact button visibility")
    @Parameters({"browser","hu"})
    public void CP_003(String broswer, String hu) {

        String testCase = hu + "-" + this.getClass().getSimpleName();
        String testPath = hu + "/" + broswer + "/" + this.getClass().getSimpleName();
        String dataPath = dataBasePath + "/" + hu + "/" + this.getClass().getSimpleName().replace("_", "-") + "/";
        CustomReporter.title("Starting test: " + testCase + ",with OS: " + operativeSystem + " in " + broswer);

        CustomReporter.log("Navigating to " + baseUrl);
        this.navegateToLanding();

        CustomReporter.subTitle("Checking contact button visibility");
        checkPageContent("objects","body",testPath,dataPath);
    }

}
