package com.bvc.a2censo.test.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
            String element_selector = validationValue.split(":",2)[0];
            String element_select_value = validationValue.split(":",2)[1];
            jsExecutor.executeScript("arguments[0].click();", element);
            wait.until(ExpectedConditions.visibilityOf(TestUtils.getElementWithExcel(driver,element_selector,element_select_value)));
            Thread.sleep(2000);
            elementToValidate = element;
        }
        return  elementToValidate;
    }

}
