package com.bvc.a2censo.test.cases.hu1_002;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.CustomReporter;
import com.bvc.a2censo.test.util.ExcelUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CP_004 extends TestBase {

    @Test(description = "This TC will check vitrina section content")
    @Parameters({"browser","hu"})
    public void CP_004(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        navegateToLanding();

        String[][] data = ExcelUtils.getData(dataPath+"objects.xlsx","url",true);
        String sectionName = data[0][0];
        String sectionUrl = baseUrl + data[0][1];
        CustomReporter.log("Navigating to section "+sectionName+" "+sectionUrl);
        driver.navigate().to(sectionUrl);

        CustomReporter.log("Url shoud be '"+sectionUrl);
        wait.until(ExpectedConditions.urlMatches(sectionUrl));

        CustomReporter.subTitle("Validating section "+sectionName+" content");
        checkPageContent("objects",sectionName+"_content",testPath,dataPath);

        CustomReporter.error("Section vitrina not finished");
        Assert.fail("Section vitrina not finished");

    }

}
