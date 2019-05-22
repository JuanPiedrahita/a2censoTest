package com.bvc.a2censo.test.util;

import org.testng.Reporter;

public class CustomReporter {

    private static String errorTemplate = "<font color=\"red\">{{message}}<br></font>";
    private static String normalTemplate = "{{message}}<br>";
    private static String titleTemplate = "<h2>{{message}}<br></h2>";
    private static String subTitleTemplate = "<h4>{{message}}<br></h4>";
    private static String subSubTitleTemplate = "<strong>{{message}}<br></strong>";

    public static void error(String message){
        Reporter.log(errorTemplate.replace("{{message}}",message));
    }

    public static void log(String message){
        Reporter.log(normalTemplate.replace("{{message}}",message));
    }

    public static void title(String message){
        Reporter.log(titleTemplate.replace("{{message}}",message));
    }

    public static void subTitle(String message){
        Reporter.log(subTitleTemplate.replace("{{message}}",message));
    }

    public static void subSubTitle(String message){
        Reporter.log(subSubTitleTemplate.replace("{{message}}",message));
    }

}
