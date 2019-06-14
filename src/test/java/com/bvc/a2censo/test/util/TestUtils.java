package com.bvc.a2censo.test.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class TestUtils {

    public static WebElement getElementWithExcel(WebDriver driver, String selector, String selectValue){
        WebElement element = null;
        if (selector.equals("xpath")) {
            element = driver.findElement(By.xpath(selectValue));
        } else if (selector.equals("id")) {
            element = driver.findElement(By.id(selectValue));
        } else if (selector.equals("link")) {
            element = driver.findElement(By.linkText(selectValue));
        } else if (selector.equals("class")) {
            element = driver.findElement(By.className(selectValue));
        } else if (selector.equals("name")) {
            element = driver.findElement(By.name(selectValue));
        }
        return element;
    }

    public static WebElement getValidateType(WebDriver driver, Actions action,JavascriptExecutor jsExecutor, WebDriverWait wait, String validationType, WebElement element, String validationValue) throws InterruptedException {
        WebElement elementToValidate = null;
        if(validationType.equals("visible")){
            action.moveToElement(element).build().perform();
            elementToValidate = wait.until(ExpectedConditions.visibilityOf(element));
        } else if(validationType.equals("url")){
            jsExecutor.executeScript("arguments[0].click();", element);
            wait.until(ExpectedConditions.urlContains(element.getAttribute("href")));
            Thread.sleep(2000);
            elementToValidate = element;
        } else if(validationType.equals("element_makes_visible")){
            action.moveToElement(element).build().perform();
            String element_selector = validationValue.split(":",2)[0];
            String element_select_value = validationValue.split(":",2)[1];
            jsExecutor.executeScript("arguments[0].click();", element);
            WebElement visibleElement = TestUtils.getElementWithExcel(driver,element_selector,element_select_value);
            action.moveToElement(visibleElement).build().perform();
            wait.until(ExpectedConditions.visibilityOf(visibleElement));
            Thread.sleep(2000);
            elementToValidate = element;
        }
        return  elementToValidate;
    }

    public static void sendDataToFields(WebDriver driver, String[][] fields, String[] data){
        for(int i = 0; i < fields.length; i++){
            String[] row = fields[i];
            String fieldName = row[0], fieldSelector = row[1], selectValue = row[2], fieldType = row[3];
            String fieldData = data[i];
            WebElement field = TestUtils.getElementWithExcel(driver,fieldSelector,selectValue);
            if (fieldData.equals("") || fieldData == null) {
                CustomReporter.log(fieldName+" data is empty");
            } else {
                if (fieldType.equals("text") || fieldType.equals("number")) {
                    CustomReporter.log("Sending value " + fieldData + " to field " + fieldName);
                    field.sendKeys(fieldData);
                } else if (fieldType.equals("not_enabled")) {
                    CustomReporter.log("Field " + fieldName + " not enabled.");
                } else if (fieldType.equals("select")) {
                    CustomReporter.log("Selecting option " + fieldData + " on field " + fieldName);
                    Select selecField = new Select(field);
                    selecField.selectByValue(fieldData);
                } else if (fieldType.equals("checkbox")) {
                    CustomReporter.log("Selecting option " + fieldData + " on field " + fieldName);
                    if (fieldData.equals("true")) {
                        field.click();
                    }
                }
            }
        }
    }

}
