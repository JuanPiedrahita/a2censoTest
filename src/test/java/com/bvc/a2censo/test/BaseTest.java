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

    @BeforeClass
    @Parameters({"os","browser","env"})
    public void setUp(String os, String broswer, String env) {

        BroswerFactory factory = new BroswerFactory();
        driver = factory.createWebDriver(os,broswer,env);

        action = new Actions(driver);
        jsExecutor = (JavascriptExecutor) driver;
    }

    @AfterClass
    public void tearDown(){
        //driver.close();
        driver.quit();
    }

}
