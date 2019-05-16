package com.bvc.a2censo.test;

import com.bvc.a2censo.util.BroswerFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public abstract  class BaseTest {

    protected WebDriver driver;
    protected Actions action;
    protected JavascriptExecutor jsExecutor;
    protected  String env;
    protected  String operativeSystem;
    protected  String basePath;

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(String browser) {

        //System properties
        env = System.getProperty("env");
        operativeSystem = System.getProperty("operativeSystem");
        basePath = System.getProperty("basePath");

        BroswerFactory factory = new BroswerFactory();
        driver = factory.createWebDriver(operativeSystem,browser,env);

        action = new Actions(driver);
        jsExecutor = (JavascriptExecutor) driver;

    }

    @AfterClass
    public void tearDown(){
        //driver.close();
        driver.quit();
    }

}
