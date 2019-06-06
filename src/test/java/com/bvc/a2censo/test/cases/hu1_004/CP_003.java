package com.bvc.a2censo.test.cases.hu1_004;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.CustomReporter;
import com.bvc.a2censo.test.util.ExcelUtils;
import com.bvc.a2censo.test.util.ImageUtils;
import com.bvc.a2censo.test.util.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class CP_003 extends TestBase {

    @Test(description = "This TC will check dropdown options for inputs in contact form")
    @Parameters({"browser","hu"})
    public void CP_003(String broswer, String hu) {

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

        CustomReporter.subTitle("Getting select options");
        String[][] dataSelect = ExcelUtils.getData(dataPath+"objects.xlsx","select",true);
        WebElement selectElement = TestUtils.getElementWithExcel(driver,dataSelect[0][1],dataSelect[0][2]);
        Select select = new Select(selectElement);
        List<WebElement> options = select.getOptions();
        CustomReporter.log("Select has "+options.size()+" options");
        action.moveToElement(selectElement).click().build().perform();
        wait.until(ExpectedConditions.visibilityOfAllElements(options));

        if (options.size() > 1) {
            CustomReporter.log("Options are: ");
            CustomReporter.log(options.stream().map( option -> (!option.getText().equals(""))?option.getText():"Default").collect(Collectors.joining(", ")));
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"options"));
        } else {
            String msg = "Not enough options";
            CustomReporter.error(msg);
            CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,"not_enough_options"));
            Assert.fail(msg);
        }

    }

}
