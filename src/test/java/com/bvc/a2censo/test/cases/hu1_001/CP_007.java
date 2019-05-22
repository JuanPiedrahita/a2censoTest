package com.bvc.a2censo.test.cases.hu1_001;

import com.bvc.a2censo.test.model.ResponsiveBaseTest;
import com.bvc.a2censo.test.model.TestDevice;
import com.bvc.a2censo.test.util.CustomReporter;
import com.galenframework.api.Galen;
import org.testng.annotations.Test;

public class CP_007 extends ResponsiveBaseTest {

    @Test(
            description = "This TC will check responsive design of a2censo portal",
            dataProvider = "devices"
    )
    public void CP_007(TestDevice device) {

        driver.manage().window().setSize(device.getScreenSize());
        CustomReporter.title("Starting test with dimensions: "+device.toString());

        navegateToLanding();

        try {
            String page = "landing";
            String pageSpectPath = specPath+page+".spec";
            generateGalenReport(Galen.checkLayout(driver, pageSpectPath, device.getTags()),device.getName(),page);
        } catch (Exception e){
            CustomReporter.error("Error verifying landing responsive layout");
            e.printStackTrace();
        }

    }

}
