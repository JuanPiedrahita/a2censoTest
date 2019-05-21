package com.bvc.a2censo.test;

import com.bvc.a2censo.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public abstract  class BaseTest {

    protected WebDriver driver;
    protected Actions action;
    protected JavascriptExecutor jsExecutor;
    protected String env;
    protected String operativeSystem;
    protected String basePath;
    protected String dataBasePath;
    protected WebDriverWait wait;

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(String browser) {

        //System properties
        env = System.getProperty("env");
        operativeSystem = System.getProperty("operativeSystem");
        basePath = System.getProperty("basePath");
        dataBasePath = System.getProperty("dataBasePath");

        BroswerFactory factory = new BroswerFactory();
        driver = factory.createWebDriver(operativeSystem,browser,env);

        action = new Actions(driver);
        jsExecutor = (JavascriptExecutor) driver;

        wait = new WebDriverWait(driver, 5);

    }

    @AfterClass
    public void tearDown(){
        //driver.close();
        driver.quit();
    }

    public void navegateToLanding(){
        driver.navigate().to(basePath);
    }

    public void checkPageContent(String document,String sheet, String testPath, String dataPath){
        String elementName = "page_content";
        String validationType = "";
        try {
            String[][] data = ExcelUtils.getData(dataPath+document+".xlsx",sheet,true);
            for (int i = 0; i<data.length; i++) {
                String[] rowData = data[i];
                elementName = rowData[0];
                validationType = rowData[3];
                WebElement element = TestUtils.getElementWithExcel(driver, rowData[1],rowData[2]);
                action.moveToElement(element).build().perform();
                element = TestUtils.getValidateType(wait,validationType,element);
                CustomReporter.log(elementName+" is "+validationType+"? Yes");
                CustomReporter.log(Screenshot.takeScreenshot(driver,testPath,elementName+"_"+validationType));
            }
        } catch (Exception e) {
            e.printStackTrace();
            CustomReporter.error(elementName+" is not "+validationType);
            CustomReporter.log(Screenshot.takeScreenshot(driver,testPath,elementName+"_not_"+validationType));
            Assert.fail(elementName+" is not "+validationType);
        }
    }

}
