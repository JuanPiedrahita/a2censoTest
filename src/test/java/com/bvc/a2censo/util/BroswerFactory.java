package com.bvc.a2censo.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class BroswerFactory implements BroswerFactoryMethod{

    public WebDriver createWebDriver(String os, String broswer, String env) {

        String pathToDriver = "";
        WebDriver driver = null;

        //Create driver
        //os -> Operative System: windows, linux
        //broswerName: chrome, ie, firefox
        if (broswer.equals("chrome")) {
            pathToDriver = (os.equals("windows"))?"driver\\windows\\chromedriver.exe":"driver/linux/chromedriver";
            System.setProperty("webdriver.chrome.driver", pathToDriver);

            ChromeOptions options = new ChromeOptions();
            if (!env.equals("dev")) {
                options.setHeadless(true);
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-extensions");
                options.setExperimentalOption("useAutomationExtension", false);
                options.addArguments("--proxy-server='direct://'");
                options.addArguments("--proxy-bypass-list=*");
                options.addArguments("--start-maximized");
                options.addArguments("--headless");
            }
            driver = new ChromeDriver(options);
        } else if (broswer.equals("ie")) {
            //Not supported by a2censo
            pathToDriver = "driver\\windows\\IEDriverServer.exe";
            System.setProperty("webdriver.ie.driver",pathToDriver);
            driver = new InternetExplorerDriver();
        } else if (broswer.equals("edge")) {
            //Not supported by a2censo
            System.setProperty("webdriver.edge.driver",pathToDriver);
            driver = new EdgeDriver();
        } else if (broswer.equals("firefox")) {
            pathToDriver = (os.equals("windows"))?"driver\\windows\\geckodriver.exe":"driver/linux/geckodriver";
            System.setProperty("webdriver.gecko.driver", pathToDriver);

            FirefoxOptions options = new FirefoxOptions();
            if (!env.equals("dev")) {
                options.setHeadless(true);
            }
            driver = new FirefoxDriver(options);
        } else if (broswer.equals("opera")) {
            pathToDriver = (os.equals("windows"))?"driver\\windows\\operadriver.exe":"driver/linux/operadriver";
            System.setProperty("webdriver.opera.driver", pathToDriver);

            OperaOptions options = new OperaOptions();
            options.setBinary("C:\\Users\\juan_piedrahita\\AppData\\Local\\Programs\\Opera\\60.0.3255.70\\opera.exe");

            if (!env.equals("dev")) {
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-extensions");
                options.setExperimentalOption("useAutomationExtension", false);
                options.addArguments("--proxy-server='direct://'");
                options.addArguments("--proxy-bypass-list=*");
                options.addArguments("--start-maximized");
                options.addArguments("--headless");
            }

            driver = new OperaDriver(options);
        }

        //Manage timeouts
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        //Manage window
        driver.manage().window().maximize();

        //Manage cookies
        driver.manage().deleteAllCookies();

        return driver;

    }

}
