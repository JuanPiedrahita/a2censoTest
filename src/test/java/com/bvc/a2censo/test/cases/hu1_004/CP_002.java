package com.bvc.a2censo.test.cases.hu1_004;

import com.bvc.a2censo.test.model.ResponsiveBaseTest;
import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.model.TestDevice;
import com.bvc.a2censo.test.util.CustomReporter;
import com.bvc.a2censo.test.util.ExcelUtils;
import com.bvc.a2censo.test.util.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.swing.*;

public class CP_002 extends ResponsiveBaseTest {

    @Test(
            description = "This TC will check contact form responsive mode",
            dataProvider = "devices"
    )
    public void CP_002(TestDevice device) {
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";

        navegateToLanding();
        driver.manage().window().maximize();

        CustomReporter.subTitle("Going to contact form");
        String[][] data = ExcelUtils.getData(dataPath+"objects.xlsx","contact",true);

        WebElement contactButton = TestUtils.getElementWithExcel(driver,data[0][1],data[0][2]);
        actions.click(contactButton).build().perform();
        WebElement contactForm = TestUtils.getElementWithExcel(driver,data[1][1],data[1][2]);
        wait.until(ExpectedConditions.visibilityOf(contactForm));

        checkResponsive(device,"contactForm");
    }

}
