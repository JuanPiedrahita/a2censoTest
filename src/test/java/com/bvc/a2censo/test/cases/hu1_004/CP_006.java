package com.bvc.a2censo.test.cases.hu1_004;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.CustomReporter;
import com.bvc.a2censo.test.util.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CP_006 extends TestBase {

    @Test(description = "This TC will register a pqrs and check if the verification email is sent to a parameterized list")
    @Parameters({"browser","hu"})
    public void CP_006(String broswer, String hu) {

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
        String msg = "Email list not parameterized yet";
        CustomReporter.error(msg);
        Assert.fail(msg);
    }

}
