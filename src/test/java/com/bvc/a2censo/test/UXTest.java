package com.bvc.a2censo.test;

import com.bvc.a2censo.util.Screenshot;
import jdk.nashorn.internal.scripts.JO;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import javax.swing.*;

public class UXTest {

    public static void checkHeaderScroll(WebDriver driver,WebDriverWait wait, String testPath) {
        Reporter.log("Checking Menu Header Scroll");
        String pixelsToScroll = "200";
        WebElement menuBar = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[1]/div/nav"));
        try{
            Reporter.log("Scrolling down "+pixelsToScroll+"px, header must hide<br>");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,"+(pixelsToScroll)+")");
            wait.until(ExpectedConditions.attributeContains(menuBar,"class","navbar--hidden"));
            Reporter.log(Screenshot.takeScreenshot(driver,testPath,"scroll_down_menu_hide"));
        } catch (Exception e){
            e.printStackTrace();
            Reporter.log("Menu does not hide<br>");
            Reporter.log(Screenshot.takeScreenshot(driver,testPath,"scroll_down_menu_does_not_hide"));
            Assert.fail("Scroll down menu does not hide");
        }
        try{
            Reporter.log("Scrolling up "+pixelsToScroll+"px, header must appear<br>");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-"+(pixelsToScroll)+")");
            wait.until(ExpectedConditions.attributeToBe(menuBar,"class","navbar"));
            Reporter.log(Screenshot.takeScreenshot(driver,testPath,"scroll_up_menu_apppear"));
        } catch (Exception e){
            e.printStackTrace();
            Reporter.log("Menu does not appear<br>");
            Reporter.log(Screenshot.takeScreenshot(driver,testPath,"scroll_up_menu_does_not_apppear"));
            Assert.fail("Scroll up menu appears");
        }
    }

    public static void checkButtonStyles(WebDriver driver, String testPath){

    }

    public static void checkHeaderStyles(WebDriver driver, WebDriverWait wait, final Actions action, String testPath) {
        final String menuLinkUp = System.getProperty("menuLinkUp");
        final String menuLinkOver = System.getProperty("menuLinkOver");
        Reporter.log("Checking styles in menu items<br>");
        for(int i = 1; i<= 4 ; i++){
            String xpath = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div/nav/div[2]/div/div["+i+"]";
            final WebElement menuItem = driver.findElement(By.xpath(xpath));
            Reporter.log("Checking "+menuItem.getText()+" item properties<br>");
            if(menuItem.getCssValue("color").equals(menuLinkUp)){
                Reporter.log("Color up matches");
                Reporter.log(Screenshot.takeScreenshot(driver,testPath,"color_up_matches"));
            } else {
                Reporter.log("Color up does not matches");
                Reporter.log(Screenshot.takeScreenshot(driver,testPath,"color_up_does_not_matches"));
                Assert.fail("Color up does not matches");
            }
            action.moveToElement(menuItem).perform();
            try{
                wait.until(new ExpectedCondition<Boolean>() {
                   public Boolean apply(WebDriver driver) {
                        return menuItem.getCssValue("color").equals(menuLinkOver);
                   }
               });
                Reporter.log("Color over matches");
                Reporter.log(Screenshot.takeScreenshot(driver,testPath,"color_over_matches"));
            } catch (Exception e) {
                Reporter.log("Color over does not matches");
                Reporter.log(Screenshot.takeScreenshot(driver,testPath,"color_over_does_not_matches"));
                Assert.fail("Color over does not matches");
            }

        }
    }

    public static void checkFooterStyles(WebDriver driver, String testPath){

    }

    public static void checkTabStyles(){

    }

    public static void checkLinkStyles(){

    }

}
