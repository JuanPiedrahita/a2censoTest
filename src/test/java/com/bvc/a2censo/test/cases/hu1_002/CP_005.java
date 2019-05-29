package com.bvc.a2censo.test.cases.hu1_002;

import com.bvc.a2censo.test.model.ResponsiveBaseTest;
import com.bvc.a2censo.test.model.TestDevice;
import com.bvc.a2censo.test.util.CustomReporter;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CP_005 extends ResponsiveBaseTest {

    @Test(
            description = "This TC will check responsive design of a2censo portal",
            dataProvider = "devices"
    )
    public void CP_005(TestDevice device) {

        navegateToLanding();

        checkResponsive(device, "landing");

        CustomReporter.error("Responsive not available yet");
        Assert.fail("Responsive not available yet");

    }

}
