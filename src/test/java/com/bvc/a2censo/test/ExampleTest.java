package com.bvc.a2censo.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExampleTest extends BaseTest {

    @Test(description = "This TC will perforrm a valid ...")
    @Parameters({"os","browser"})
    public void probarPortal(String os, String broswer) {

        Reporter.log("Starting test with OS: "+os+" in "+broswer+"\n");

        String url = "http://www.a2censo.com";

        Reporter.log("Navigating to "+url+"\n");
        driver.navigate().to(url);

        Reporter.log("Title should be 'a2censo\n'");
        Assert.assertTrue(driver.getTitle().equals("a2censo"));
        Reporter.log("Title is 'a2censo': " + driver.getTitle().equals("a2censo")+"\n");

        //Seleccionar opción registrarse
        WebElement btnRegistrate = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[2]/div/div/button"));
        btnRegistrate.click();

        //Seleccionar opción Soy Empresario
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement btnEmpresario = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id=\"pyme\"]")));
        btnEmpresario.click();

        //Llenar formulario empresario
        {
            WebElement inputName = driver.findElement(By.id("nombreEmpresaValidacion"));
            inputName.sendKeys("Juan");
            System.out.println("texto input " + inputName.getAttribute("value"));

            Reporter.log("Input value should be 'Juan'\n");
            Assert.assertTrue(inputName.getAttribute("value").equals("Juan"));
            Reporter.log("Input value is 'Juan': " + inputName.getAttribute("value").equals("Juan")+"\n");

            driver.findElement(By.id("nitValidacion")).sendKeys("1018494294");

            //Seleccionar ingresos
            driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div[2]/div/nav/div/div/form/div[3]/div/div[1]")).click();
            //Opcion
            driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/nav/div/div/form/div[3]/div[2]/div/div[1]")).click();
            //Opción escogida
            System.out.println(driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div[2]/div/nav/div/div/form/div[3]/div/div[1]/div[1]")).getText());

            //Seleccionar departamento
            driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div[2]/div/nav/div/div/form/div[4]/div/div[1]")).click();
            //Opcion
            driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/nav/div/div/form/div[4]/div[2]/div/div[1]")).click();

            //Seleccionar municipio
            driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div[2]/div/nav/div/div/form/div[5]/div/div[1]")).click();
            //Opcion
            driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/nav/div/div/form/div[5]/div[2]/div/div[1]")).click();

            //Seleccionar sector
            driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div[2]/div/nav/div/div/form/div[6]/div/div[1]")).click();
            //Opcion
            driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/nav/div/div/form/div[6]/div[2]/div/div[1]")).click();

            driver.findElement(By.id("nombreContactoValidacion")).sendKeys("Juan Sebastian");

            driver.findElement(By.id("apellidoContactoValidacion")).sendKeys("Piedrahita Gonzalez");

            driver.findElement(By.id("emailValidacion")).sendKeys("test@gmail.com");

            driver.findElement(By.id("numeroContactoValidacion")).sendKeys("3193983104");

            //Aceptar terminos
            WebElement checkTerminos = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/nav/div/div/form/div[11]/div[1]/label/span"));
            action.moveToElement(checkTerminos);
            action.click();
            action.perform();

            //enviar formulario
            WebElement btnConfirmar = driver.findElement(By.id("confirmar"));
            jsExecutor.executeScript("arguments[0].scrollIntoView();", btnConfirmar);
            action.moveToElement(btnConfirmar);
            action.perform();

        }

        //volver formulario
        WebElement btnRegresar = driver.findElement(By.id("alternativa"));
        jsExecutor.executeScript("arguments[0].scrollIntoView();", btnRegresar);
        action.moveToElement(btnRegresar);
        action.click();
        action.perform();

        //Seleccionar opción Soy Inversionista
        WebElement btnInversionista = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id=\"inversionista\"]")));
        btnInversionista.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
