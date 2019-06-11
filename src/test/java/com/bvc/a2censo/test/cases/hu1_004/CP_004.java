package com.bvc.a2censo.test.cases.hu1_004;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CP_004 extends TestBase {

    @Test(description = "This TC will register a pqrs")
    @Parameters({"browser","hu"})
    public void CP_004(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        navegateToLanding();

        CustomReporter.subTitle("Going to contact form");
        String[][] dataContact = ExcelUtils.getData(dataPath+"objects.xlsx","contact",true);

        WebElement contactButton = TestUtils.getElementWithExcel(driver,dataContact[0][1],dataContact[0][2]);
        action.click(contactButton).build().perform();
        WebElement contactForm = TestUtils.getElementWithExcel(driver,dataContact[1][1],dataContact[1][2]);
        wait.until(ExpectedConditions.visibilityOf(contactForm));

        CustomReporter.subTitle("Registering pqrs");
        String[][] pqrsFields = ExcelUtils.getData(dataPath+"objects.xlsx","pqrs_fields",true);
        String[] pqrsData = ExcelUtils.getData(dataPath+"objects.xlsx","pqrs_data",true)[0];

        CustomReporter.subTitle("Filling fields");
        TestUtils.sendDataToFields(driver,pqrsFields,pqrsData);

        CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"form_filled"));

        CustomReporter.subTitle("Sending pqrs");
        String[][] sendPqrsData = ExcelUtils.getData(dataPath+"objects.xlsx","send_pqrs",true);
        WebElement submitButton = TestUtils.getElementWithExcel(driver,sendPqrsData[0][1],sendPqrsData[0][2]);
        submitButton.click();

        WebElement responseModal = TestUtils.getElementWithExcel(driver,sendPqrsData[1][1],sendPqrsData[1][2]);
        try{
            wait.until(ExpectedConditions.visibilityOf(responseModal));
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"pqrs_registered"));
        } catch (Exception e){
            String msg = "Error registering pqrs";
            CustomReporter.error(msg);
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"pqrs_not_registered"));
            Assert.fail(msg);
        }

        String responseMessage = TestUtils.getElementWithExcel(driver,sendPqrsData[2][1],sendPqrsData[2][2]).getText();
        String pqrsId = responseMessage.split(" ")[5];
        CustomReporter.log("Pqrs registered with id: "+pqrsId);

        if (System.getProperty("env").equals("dev")) {
            CustomReporter.subTitle("Email verification");
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
        }
    }



}
