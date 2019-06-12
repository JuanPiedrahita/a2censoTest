package com.bvc.a2censo.test.cases.hu1_004;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CP_004 extends TestBase {

    @Test(description = "This TC will check terms and policies")
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


        CustomReporter.subTitle("Verifying terms");
        String[][] dataTerms = ExcelUtils.getData(dataPath+"objects.xlsx","terms",true);

        for (String[] term: dataTerms) {
            String name = term[0];
            CustomReporter.log("Verifying "+name);
            WebElement termLink = TestUtils.getElementWithExcel(driver,term[1],term[2]);
            termLink.click();
            WebElement termElement = TestUtils.getElementWithExcel(driver,term[3],term[4]);
            try {
                wait.until(ExpectedConditions.visibilityOf(termElement));
                action.moveToElement(termElement).build().perform();
                CustomReporter.log(name + "is visible.");
                CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,name+"is_visible"));
                TestUtils.getElementWithExcel(driver,term[5],term[6]).click();
                Thread.sleep(1000);
            } catch (Exception error) {
                String msg = name + "is not visible.";
                CustomReporter.error(msg);
                CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,name+"_is_not_visible"));
                Assert.fail(msg);
            }
        }


    }

}
