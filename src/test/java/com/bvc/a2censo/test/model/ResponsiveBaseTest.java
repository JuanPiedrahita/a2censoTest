package com.bvc.a2censo.test.model;

import com.bvc.a2censo.test.util.CustomReporter;
import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class ResponsiveBaseTest {

    protected WebDriver driver;
    protected String baseUrl;
    protected String dataBasePath;
    protected String env;
    protected String operativeSystem;
    protected String specPath;
    protected static final String responsiveReportPath = "test_info/execution_evidences/responsive/"
            + (new SimpleDateFormat("yyyy-MM-dd_HH-mm")).format(new Timestamp(System.currentTimeMillis()))
            + "/";

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(String browser) {

        //System properties
        env = System.getProperty("env");
        operativeSystem = System.getProperty("operativeSystem");
        baseUrl = System.getProperty("baseUrl");
        dataBasePath = System.getProperty("dataBasePath");

        specPath = dataBasePath + "/specs/";

        BroswerFactory factory = new BroswerFactory();
        driver = factory.createWebDriver(operativeSystem,browser,env);
    }

    public void generateGalenReport(LayoutReport layoutReport,String deviceName) throws Exception{
        //Creating a list of tests
        List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();
        //The object you create will be consisting the information regarding the test
        GalenTestInfo test = GalenTestInfo.fromString("Test Automation Using Galen Framework");
        //Adding layout report to the test report
        test.getReport().layout(layoutReport, "Verifying landing responsive");
        tests.add(test);
        new HtmlReportBuilder().build(tests, responsiveReportPath+deviceName);
        CustomReporter.log("<a href=../" + responsiveReportPath+deviceName+"/" + "report.html> Click to open responsive test report</a>");
    }

    @AfterClass
    public void tearDown(){
        //driver.close();
        driver.quit();
    }

    public void navegateToLanding(){
        driver.navigate().to(baseUrl);
    }

    @DataProvider(name = "devices")
    public Object [][] devices () {
        return new Object[][] {
                {new TestDevice("mobile", new Dimension(450, 800), asList("mobile"))},
                {new TestDevice("tablet", new Dimension(750, 800), asList("tablet"))},
                {new TestDevice("desktop", new Dimension(1024, 800), asList("desktop"))}
        };
    }


}
