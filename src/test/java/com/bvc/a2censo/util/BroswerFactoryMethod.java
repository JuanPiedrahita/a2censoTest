package com.bvc.a2censo.util;

import org.openqa.selenium.WebDriver;

public interface BroswerFactoryMethod {

    public WebDriver createWebDriver(String os, String broswerName,String env);

}
