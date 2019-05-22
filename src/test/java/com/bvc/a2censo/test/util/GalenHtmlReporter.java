package com.bvc.a2censo.test.util;

import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;

import java.util.LinkedList;
import java.util.List;

public class GalenHtmlReporter {

    private List<GalenTestInfo> tests;
    private static  GalenHtmlReporter reporter;

    private static GalenHtmlReporter getReporter() {
        if (reporter == null) {
            reporter = new GalenHtmlReporter();
        }
        return reporter;
    }

    private GalenHtmlReporter(){
        tests = new LinkedList<GalenTestInfo>();
    }

    public static void addTest(String testInfo, LayoutReport layoutReport, String testDesc){
        GalenTestInfo test = GalenTestInfo.fromString(testInfo);
        //Adding layout report to the test report
        test.getReport().layout(layoutReport, testDesc);
        GalenHtmlReporter.getReporter().getTests().add(test);
    }

    public static void build(String path) throws Exception{
        new HtmlReportBuilder().build(GalenHtmlReporter.getReporter().getTests(), path);

    }

    public List<GalenTestInfo> getTests() {
        return tests;
    }

}
