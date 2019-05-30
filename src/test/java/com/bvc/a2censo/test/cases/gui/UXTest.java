package com.bvc.a2censo.test.cases.gui;

import com.bvc.a2censo.test.model.AbstractJson;
import com.bvc.a2censo.test.model.GUIElement;
import com.bvc.a2censo.test.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;

public class UXTest {

    private static String jsonPath = System.getProperty("dataBasePath")+"gui/ux.json";
    private static String dataPath = System.getProperty("dataBasePath")+"gui/";

    public static void checkHeaderScroll(WebDriver driver,WebDriverWait wait, String testPath) {
        CustomReporter.subSubTitle("Checking Menu Header Scroll");
        String pixelsToScroll = "200";
        WebElement menuBar = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[1]/div/nav"));
        try{
            CustomReporter.log("Scrolling down "+pixelsToScroll+"px, header must hide");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,"+(pixelsToScroll)+")");
            wait.until(ExpectedConditions.attributeContains(menuBar,"class","navbar--hidden"));
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"scroll_down_menu_hide"));
        } catch (Exception e){
            e.printStackTrace();
            CustomReporter.error("Menu does not hide");
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"scroll_down_menu_does_not_hide"));
            Assert.fail("Scroll down menu does not hide");
        }
        try{
            CustomReporter.log("Scrolling up "+pixelsToScroll+"px, header must appear");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-"+(pixelsToScroll)+")");
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.attributeContains(menuBar,"class","navbar--show"),
                    ExpectedConditions.attributeContains(menuBar,"class","navbar--onload")
            ));
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"scroll_up_menu_apppear"));
        } catch (Exception e){
            e.printStackTrace();
            CustomReporter.error("Menu does not appear");
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"scroll_up_menu_does_not_apppear"));
            Assert.fail("Scroll up menu appears");
        }
    }

    public static void checkButtonStyles(WebDriver driver, String testPath){

    }

    public static void checkHeaderStyles(WebDriver driver, WebDriverWait wait, Actions action, String testPath) {
        CustomReporter.subSubTitle("Checking Header Menu Styles");
        checkPageStyle(driver,wait,action, "objects", "menu", testPath, dataPath);
    }

    public static void checkFooterStyles(WebDriver driver, WebDriverWait wait, Actions action, String testPath){
        CustomReporter.subSubTitle("Checking Footer Styles");
        try {
            WebElement footer = driver.findElement(By.xpath("//*[@id=\"footer\"]"));
            action.moveToElement(footer).build().perform();
            Thread.sleep(2000);
            Robot robot = new Robot();
            robot.mouseMove(footer.getLocation().x,footer.getLocation().y);
            checkPageStyle(driver,wait,action, "objects", "footer", testPath, dataPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkTabStyles(){

    }

    public static void checkLinkStyles(){

    }

    public static AbstractJson[] getJsonUxConfiguration(){
        return JsonUtils.getJsonFromFile(jsonPath, GUIElement[].class);
    }

    public static boolean getValidateStyleType(WebDriverWait wait, String validationType, final WebElement element, GUIElement elementStyles, Actions action){
        WebElement elementToValidate = null;
        String property = "";
        String validateValue = "";
        if (validationType.equals("textColor")){
            property = "color";
            validateValue = elementStyles.getTextColor();
        } else if (validationType.equals("textColorOver")){
            property = "color";
            validateValue = elementStyles.getTextColorOver();
            action.moveToElement(element).perform();
        }
        final String propertyToValidate = property;
        final String validationValue = validateValue;
        return wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return element.getCssValue(propertyToValidate).equals(validationValue);
            }
        });
    }

    public static void checkPageStyle(WebDriver driver, WebDriverWait wait, Actions action,String document,String sheet, String testPath, String dataPath){
        AbstractJson[] guiElements = getJsonUxConfiguration();
        String elementName = "page_style";
        String validationType = "";
        String elementType = "";
        try {
            String[][] data = ExcelUtils.getData(dataPath+document+".xlsx",sheet,true);
            for (int i = 0; i<data.length; i++) {
                String[] rowData = data[i];
                elementName = rowData[0];
                validationType = rowData[4];
                elementType = rowData[3];
                WebElement element = TestUtils.getElementWithExcel(driver, rowData[1],rowData[2]);
                getValidateStyleType(wait,validationType,element, (GUIElement) AbstractJson.getByName(guiElements,elementType),action);
                CustomReporter.log("Validating "+validationType+" for "+elementName+" of type "+elementType+": "
                        + validationType+" for "+elementName+" matches");
                CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,elementName+"_"+validationType));
            }
        } catch (Exception e) {
            e.printStackTrace();
            CustomReporter.error("Validating "+validationType+" for "+elementName+" of type "+elementType+": "+
                    validationType+" for "+elementName+" does not matches ");
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,elementName+"_not_"+validationType));
            Assert.fail(validationType+" for "+elementName+" does not matches");
        }
    }

}
