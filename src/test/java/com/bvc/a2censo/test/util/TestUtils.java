package com.bvc.a2censo.test.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    public static WebElement getValidateType(WebDriverWait wait, String validationType, WebElement element){
        WebElement elementToValidate = null;
        if(validationType.equals("visible")){
            elementToValidate = wait.until(ExpectedConditions.visibilityOf(element));
        }
        return  elementToValidate;
    }

}
