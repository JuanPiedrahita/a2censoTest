package com.bvc.a2censo.test.cases.hu1_004;

import com.bvc.a2censo.test.model.TestBase;
import com.bvc.a2censo.test.util.CustomReporter;
import com.bvc.a2censo.test.util.ExcelUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class CP_009 extends TestBase {

    @Test(
            description = "This TC will check pqrs form",
            dataProvider =  "getPQRSData"
    )
    public void CP_009(String broswer, String hu, String[] pqrsData) {

        String testCase = hu+"-"+this.getClass().getSimpleName();
        String testPath = hu+"/"+broswer+"/"+this.getClass().getSimpleName();
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        CustomReporter.title("Starting test: "+testCase+",with OS: "+operativeSystem+" in "+broswer);

        navegateToLanding();

        CP_005.fillPQRSForm(dataPath,testPath,driver,action,wait,pqrsData);
        CP_005.verifySubmitButtonNotEnabled(dataPath,testPath,driver,action,wait);

    }

    @DataProvider(name = "getPQRSData")
    public Object[][] getPQRSData(ITestContext ctx){
        String broswer = ctx.getCurrentXmlTest().getParameter("browser");
        String hu = ctx.getCurrentXmlTest().getParameter("hu");
        String dataPath = dataBasePath+"/"+hu+"/"+this.getClass().getSimpleName().replace("_","-")+"/";
        String[][] excelData = ExcelUtils.getData(dataPath+"objects.xlsx","pqrs_data",true);
        Object[][] data = new Object[excelData.length][3];
        for (int i = 0; i < excelData.length; i++) {
            data[i][0] = broswer;
            data[i][1] = hu;
            data[i][2] = excelData[i];
        }
        return data;
    }

}
