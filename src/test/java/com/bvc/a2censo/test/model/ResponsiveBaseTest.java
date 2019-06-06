package com.bvc.a2censo.test.model;

import com.bvc.a2censo.test.util.CustomReporter;
import com.bvc.a2censo.test.util.GalenHtmlReporter;
import com.galenframework.api.Galen;
import com.galenframework.reports.model.LayoutReport;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
    protected WebDriverWait wait;
    protected String hu;
    protected Actions actions;

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
    @Parameters({"browser","hu"})
    public void setUp(String browser, String hu) {

        //System properties
        env = System.getProperty("env");
        operativeSystem = System.getProperty("operativeSystem");
        baseUrl = System.getProperty("baseUrl");
        dataBasePath = System.getProperty("dataBasePath");

        this.broswer = browser;
        specPath = dataBasePath + "/specs/";

        this.hu = hu;

        BroswerFactory factory = new BroswerFactory();
        driver = factory.createWebDriver(operativeSystem,browser,env);

        wait = new WebDriverWait(driver,5);
        actions = new Actions(driver);
    }

    public void generateGalenReport(LayoutReport layoutReport,String deviceName, String page) throws Exception{
        GalenHtmlReporter.addTest(page+" responsive in "+deviceName+":"+broswer,layoutReport,"Checking "+page+" responsive layout for "+deviceName);
        CustomReporter.log("<a href=../" + System.getProperty("responsiveReportPath")+"report.html> Click to open "+page+" responsive test for "+deviceName+" report</a>");
    }


    protected void checkResponsive(TestDevice device, String page){
        try {
            CustomReporter.title("Starting responsive test on "+page+" page with dimensions: "+device.toString());
            driver.manage().window().setSize(device.getScreenSize());
            String pageSpectPath = specPath+page+".spec";
            generateGalenReport(Galen.checkLayout(driver, pageSpectPath, device.getTags()),device.getName(),page);
        } catch (Exception e){
            CustomReporter.error("Error verifying landing responsive layout");
            e.printStackTrace();
        }
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
