package com.bvc.a2censo.test.model;

import com.bvc.a2censo.test.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public abstract class TestBase {

    protected WebDriver driver;
    protected Actions action;
    protected JavascriptExecutor jsExecutor;
    protected String env;
    protected String operativeSystem;
    protected String baseUrl;
    protected String dataBasePath;
    protected WebDriverWait wait;

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(String browser) {

        //System properties
        env = System.getProperty("env");
        operativeSystem = System.getProperty("operativeSystem");
        baseUrl = System.getProperty("baseUrl");
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
        driver.navigate().to(baseUrl);
    }

    public void checkPageContent(String document,String sheet, String testPath, String dataPath){
        String elementName = "page_content";
        String validationType = "";
        String validationValue = "";
        try {
            String[][] data = ExcelUtils.getData(dataPath+document+".xlsx",sheet,true);
            for (int i = 0; i<data.length; i++) {
                String[] rowData = data[i];
                elementName = rowData[0];
                validationType = rowData[3];
                if (rowData.length > 4) {
                    validationValue = rowData[4];
                }
                WebElement element = TestUtils.getElementWithExcel(driver, rowData[1],rowData[2]);
                element = TestUtils.getValidateType(driver,action,jsExecutor,wait,validationType,element,validationValue);
                CustomReporter.log(elementName+" matches "+validationType+"? Yes");
                CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,elementName+"_"+validationType));
            }
        } catch (Exception e) {
            e.printStackTrace();
            CustomReporter.error(elementName+" is not "+validationType);
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,elementName+"_not_"+validationType));
            Assert.fail(elementName+" is not "+validationType);
        }
    }

}
