package com.bvc.a2censo.test;

import com.bvc.a2censo.util.BroswerFactory;
import com.bvc.a2censo.util.ExcelUtils;
import com.bvc.a2censo.util.Screenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
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

        wait = new WebDriverWait(driver, 10);

    }

    @AfterClass
    public void tearDown(){
        //driver.close();
        driver.quit();
    }

    public void navegateToLanding(){
        driver.navigate().to(basePath);
    }

    public WebElement getElementWithExcel(String selector, String selecttValue){
        WebElement element = null;
        if (selector.equals("xpath")) {
            element = driver.findElement(By.xpath(selecttValue));
        } else if (selector.equals("id")) {
            element = driver.findElement(By.id(selecttValue));
        } else if (selector.equals("link")) {
            element = driver.findElement(By.linkText(selecttValue));
        } else if (selector.equals("class")) {
            element = driver.findElement(By.className(selecttValue));
        } else if (selector.equals("name")) {
            element = driver.findElement(By.name(selecttValue));
        }
        return element;
    }

    public WebElement getValidateType(String validationType, WebElement element){
        WebElement elementToValidate = null;
        if(validationType.equals("visibility")){
            elementToValidate = wait.until(ExpectedConditions.visibilityOf(element));
        }
        return  elementToValidate;
    }

    public void checkPageContent(String document,String sheet, String testPath, String dataPath){
        String elementName = "page_content";
        try {
            String[][] data = ExcelUtils.getData(dataPath+document+".xlsx",sheet,true);
            for (int i = 0; i<data.length; i++) {
                String[] rowData = data[i];
                elementName = rowData[0];
                Reporter.log(elementName+" is visible? ");
                //WebElement element = driver.findElement(By.xpath();
                WebElement element = getElementWithExcel(rowData[1],rowData[2]);
                action.moveToElement(element).build().perform();
                element = getValidateType(rowData[3],element);
                Reporter.log("Yes <br>");
                String ssText = Screenshot.takeScreenshot(driver,testPath,elementName+"_visible");
                Reporter.log(ssText);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Reporter.log(elementName+" is not visible<br>");
            String ssText = Screenshot.takeScreenshot(driver,testPath,elementName+"_not_visible");
            Reporter.log(ssText);
            Assert.fail(elementName+" is not visible");
        }
    }

}
