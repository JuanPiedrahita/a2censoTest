package com.bvc.a2censo.test.hu1_001;

import com.bvc.a2censo.model.TestDevice;
import com.bvc.a2censo.test.TestBase;
import com.bvc.a2censo.util.CustomReporter;
import com.galenframework.api.Galen;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;

import java.util.LinkedList;
import java.util.List;

public class CP_007 extends TestBase {

    @Test(
            description = "This TC will check responsive design of a2censo portal",
            dataProvider = "devices"
    )
    public void CP_007(TestDevice device) {

        driver.manage().window().setSize(device.getScreenSize());

        String testCase = this.getClass().getSimpleName();
        //String testPath = hu + "/" + broswer + "/" + this.getClass().getSimpleName();
        //String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        String specPath = dataBasePath + "/specs/";
        CustomReporter.title("Starting test with dimensions: "+device.toString());

        navegateToLanding();

        try {
            LayoutReport layoutReport = Galen.checkLayout(driver, specPath+"landing.spec", device.getTags());
            //Creating a list of tests
            List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();
            //The object you create will be consisting the information regarding the test
            GalenTestInfo test = GalenTestInfo.fromString("Test Automation Using Galen Framework");
            //Adding layout report to the test report
            test.getReport().layout(layoutReport, "Verifyng landing responsive");
            tests.add(test);
            //Exporting all test report to html
            new HtmlReportBuilder().build(tests, "target/galen-html-reports");
        } catch (Exception e){
            CustomReporter.error("Error verifying landing responsive layout");
            e.printStackTrace();
        }

    }

}
