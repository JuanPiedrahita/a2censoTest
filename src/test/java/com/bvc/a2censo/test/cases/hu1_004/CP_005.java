package com.bvc.a2censo.test.cases.hu1_004;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CP_005 extends TestBase {

    @Test(description = "This TC will register a pqrs")
    @Parameters({"browser","hu"})
    public void CP_005(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        navegateToLanding();

        String[] pqrsData = ExcelUtils.getData(dataPath+"objects.xlsx","pqrs_data",true)[0];
        CP_005.fillPQRSForm(dataPath,testPath,driver,action,wait,pqrsData);
        String pqrsId = CP_005.sendPQRSForm(dataPath,testPath,driver,action,wait);

        CustomReporter.log("Pqrs registered with id "+pqrsId);

        CustomReporter.subTitle("Email verification");
        if (System.getProperty("env").equals("dev")) {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                Assert.fail("Error waiting mail");
            }

            CustomReporter.log("Looking for email");
            String emailMessage = MailUtils.getEmailPQRS(pqrsId);
            if (emailMessage == null) {
                String msg = "Error getting the email info";
                CustomReporter.log(msg);
                Assert.fail(msg);
            } else {
                CustomReporter.log("Email verified");
                CustomReporter.log(emailMessage);
            }
        } else {
            CustomReporter.subTitle("Email verification only works on dev mode");
        }

    }


    protected static void fillPQRSForm(String dataPath, String testPath, WebDriver driver, Actions action, WebDriverWait wait, String[] pqrsData){
        CustomReporter.subTitle("Going to contact form");
        String[][] dataContact = ExcelUtils.getData(dataPath+"objects.xlsx","contact",true);

        WebElement contactButton = TestUtils.getElementWithExcel(driver,dataContact[0][1],dataContact[0][2]);
        action.click(contactButton).build().perform();
        WebElement contactForm = TestUtils.getElementWithExcel(driver,dataContact[1][1],dataContact[1][2]);
        wait.until(ExpectedConditions.visibilityOf(contactForm));

        CustomReporter.subTitle("Registering pqrs");
        String[][] pqrsFields = ExcelUtils.getData(dataPath+"objects.xlsx","pqrs_fields",true);


        CustomReporter.subTitle("Filling fields");
        TestUtils.sendDataToFields(driver,pqrsFields,pqrsData);

        try{
            Thread.sleep(2000);
        } catch (Exception e){
            e.printStackTrace();
        }
        //String time =  (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")).format(new Timestamp(System.currentTimeMillis()));
        String time = System.currentTimeMillis()+"";
        CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"form_filled"+time));
    }


    public static String sendPQRSForm(String dataPath, String testPath, WebDriver driver, Actions action, WebDriverWait wait){
        String pqrsId = null;
        CustomReporter.subTitle("Sending pqrs");
        String[][] sendPqrsData = ExcelUtils.getData(dataPath+"objects.xlsx","send_pqrs",true);
        WebElement submitButton = TestUtils.getElementWithExcel(driver,sendPqrsData[0][1],sendPqrsData[0][2]);
        submitButton.click();

        WebElement responseModal = TestUtils.getElementWithExcel(driver,sendPqrsData[1][1],sendPqrsData[1][2]);
        try{
            wait.until(ExpectedConditions.visibilityOf(responseModal));
            String responseMessage = TestUtils.getElementWithExcel(driver,sendPqrsData[2][1],sendPqrsData[2][2]).getText();
            pqrsId = responseMessage.split(" ")[5];
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,pqrsId+"_pqrs_registered"));
        } catch (Exception e){
            String msg = "Error registering pqrs";
            CustomReporter.error(msg);
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"pqrs_not_registered"));
            Assert.fail(msg);
        }

        return pqrsId;
    }

    public static void verifySubmitButtonNotEnabled(String dataPath, String testPath, WebDriver driver, Actions action, WebDriverWait wait) {
        CustomReporter.subTitle("Sending pqrs");
        String[][] sendPqrsData = ExcelUtils.getData(dataPath + "objects.xlsx", "send_pqrs", true);
        WebElement submitButton = TestUtils.getElementWithExcel(driver, sendPqrsData[0][1], sendPqrsData[0][2]);
        action.moveToElement(submitButton).build().perform();
        wait.until(ExpectedConditions.visibilityOf(submitButton));
        //String time =  (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")).format(new Timestamp(System.currentTimeMillis()));
        String time = System.currentTimeMillis()+"";
        if (submitButton.isEnabled()) {
            String msg = "Error button is enabled and the pqrs can be registered.";
            CustomReporter.error(msg);
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"submit_button_enabled_"+time));
            Assert.fail(msg);
        } else {
            CustomReporter.subTitle("OK, Submit button is not enabled.");
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"submit_button_not_enabled_"+time));
        }
    }

}