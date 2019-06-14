package com.bvc.a2censo.test.cases.hu1_004;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.CustomReporter;
import com.bvc.a2censo.test.util.ExcelUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CP_008 extends TestBase {

    @Test(description = "This TC will check uniques id generation for pqrs")
    @Parameters({"browser","hu"})
    public void CP_008(String broswer, String hu) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        int times = Integer.parseInt(ExcelUtils.getData(dataPath+"objects.xlsx","times",true)[0][0].replace(" ",""));

        List<String> idList = new ArrayList<String>();
        for ( int i = 0; i < times ; i++) {
            String pqrsId = addPqrs(dataPath,testPath,driver,action,wait);
            if(idList.contains(pqrsId)){
                CustomReporter.log("Duplicated ids");
                Assert.fail("Error duplicated ids");
            } else {
                idList.add(pqrsId);
            }
        }

        CustomReporter.subTitle(times+" PQRS's registered, all ids were different");
    }

    public String addPqrs(String dataPath, String testPath, WebDriver driver, Actions action, WebDriverWait wait){
        navegateToLanding();
        String[] pqrsData = ExcelUtils.getData(dataPath+"objects.xlsx","pqrs_data",true)[0];
        CP_005.fillPQRSForm(dataPath,testPath,driver,action,wait,pqrsData);
        String pqrsId = CP_005.sendPQRSForm(dataPath,testPath,driver,action,wait);

        CustomReporter.log("Pqrs registered with id "+pqrsId);

        return  pqrsId;
    }

}
