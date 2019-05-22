package com.bvc.a2censo.test.model;

import com.bvc.a2censo.test.util.CustomReporter;
import com.bvc.a2censo.test.util.GalenHtmlReporter;
import com.galenframework.reports.model.LayoutReport;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


import static java.util.Arrays.asList;

public class ResponsiveBaseTest {

    protected WebDriver driver;
    protected String baseUrl;
    protected String dataBasePath;
    protected String env;
    protected String operativeSystem;
    protected String specPath;
    protected String responsiveReportPath;
    protected String broswer;

    @BeforeSuite
    public void onInit(){
        responsiveReportPath = "test_info/execution_evidences/responsive/"
                + (new SimpleDateFormat("yyyy-MM-dd_HH-mm")).format(new Timestamp(System.currentTimeMillis()))
                + "/";
        System.setProperty("responsiveReportPath",responsiveReportPath);
    }

    @AfterSuite
    @Parameters({"browser"})
    public void onFinish(String browser){
        try {
            GalenHtmlReporter.build(responsiveReportPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(String browser) {

        //System properties
        env = System.getProperty("env");
        operativeSystem = System.getProperty("operativeSystem");
        baseUrl = System.getProperty("baseUrl");
        dataBasePath = System.getProperty("dataBasePath");

        this.broswer = browser;
        specPath = dataBasePath + "/specs/";

        BroswerFactory factory = new BroswerFactory();
        driver = factory.createWebDriver(operativeSystem,browser,env);
    }

    public void generateGalenReport(LayoutReport layoutReport,String deviceName, String page) throws Exception{
        GalenHtmlReporter.addTest(page+" responsive in "+deviceName+":"+broswer,layoutReport,"Checking "+page+" responsive layout for "+deviceName);
        CustomReporter.log("<a href=../" + System.getProperty("responsiveReportPath")+"report.html> Click to open "+page+" responsive test for "+deviceName+" report</a>");
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
