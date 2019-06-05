# a2censo Tests

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](http://a2censo.com/)
Pruebas automatizadas para el proyecto de a2censo (Producto de BVC).
* Pruebas de guías de estilo.
* Pruebas de guías de interacción.
* Pruebas funcionales del sistema.

### Dependencias
Este proyecto esta elaborado con **Maven** y además tiene las siguientes dependencias:
- [Selenium WebDriver](https://www.seleniumhq.org/projects/webdriver/ "Selenium WebDriver")
- [TestNG](https://testng.org/ "TestNG")
- [Log4j](https://logging.apache.org/log4j/2.x/ "Log4j")
- [Apache Commons IO](https://commons.apache.org/proper/commons-io/ "Apache Commons IO")
- [Apache POI](https://poi.apache.org/ "Apache POI")
- [Gson](https://github.com/google/gson "Gson")
- [Galen Framework](http://galenframework.com/ "Galen Framework")
- [Ashot](https://github.com/pazone/ashot "Ashot")
---
### Definición de casos de prueba
Se utiliza **TestNG** para la definición de los casos de prueba, es decir, usando la notación del framework se definen suites para cada historia de usuario especificando los casos que este contiene. La notación de un caso de prueba es la siguiente:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="HU1-001">
    <!-- Parametros de la suite -->
    <parameter name="hu" value="HU1-001"/>
    <test name="HU1-001_chrome">
        <!-- Parametros de la prueba -->
        <parameter name="browser" value="chrome" />
        <classes>
            <!-- Casos de prueba que se incluyen -->
            <class name="com.bvc.a2censo.test.cases.hu1_001.CP_001"/>
            <class name="com.bvc.a2censo.test.cases.hu1_001.CP_002"/>
        </classes>
    </test>
</suite>
```
Se definen los [casos de prueba](test_info/definition "Definición de los casos de prueba") para las siguientes historias de usuario:
- [HU1-01](test_info/definition/hu1_001.xml "Casos de prueba para HU1-01")
- [HU1-02](test_info/definition/hu1_002.xml "Casos de prueba para HU1-02")
- [HU1-04](test_info/definition/hu1_004.xml "Casos de prueba para HU1-04")

##### Broswser Factory
Debido a que el sistema debe funcionar en diferentes navegadores se creo una fabrica de navegadores la cual recibe como parametro el sistema operativo y el nombre de navegador y crea el driver de acuerdo a lo solicitado, el parametro se define en la suite con la etiqueta `<parameter name="browser"value="name"/>`. Los navegadores aceptados y las etiquetas correspondientes son:

| Navegador | Etiqueta |
| --- | --- |
| Firefox | `<parameter name="browser"value="firefox"/>` |
| Chrome | `<parameter name="browser"value="chrome"/>` |
| Opera | `<parameter name="browser"value="opera"/>` |

##### Headless Mode
Para que las pruebas corran en headless mode (Puede usarse en integración continua) solo debe cambiarse la etiqueta `<env>` de **dev** a **prod** de forma que esta queda así en el archivo POM:
```xml
<env>prod</env>
```

### Datos para las pruebas
Se utiliza ***Gson,Apache POI y Commons IO*** para hacer lectura y escritura de archivos de tipo `.xlsx` y `.json` y de esta forma facilitar la entrada de valores a las pruebas.
Se definen clases util con métodos estaticos para simplificar la tarea de lectura de archivos, de esta forma se tiene:
- Para leer archivos `.xlsx`:
```java
String[][] data = ExcelUtils.getData("PathToFile","sheetName",ignoreFirstRow?true:false);
```
- Para leer archivos `.json`:
```java
JsonUtils.getJsonFromFile("jsonPath", object.class);
```

### Surfire Plugin
Dentro del archivo [POM](pom.xml "Archivo POM") del proyecto, se define el uso del plungin **Surfire Reports**. De la configuración del plugin se utilizan las siguientes etiquetas:
- `<reportsDirectory>`: Directorio de salida de los reportes.
- `<suiteXmlFiles>`: Suits definidas en lenguaje de TestNG que se ejecutarán.
- `<systemPropertyVariables>`: Variables de entorno que se definene en el sistema y se pueden obtener usando `System.getProperty("name");`

De forma que se tiene la siguiente definición para la sección del plugin:
```xml
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.14.1</version>
            <configuration>
            <reportsDirectory>test-output/</reportsDirectory>
            <suiteXmlFiles>
                <suiteXmlFile>test_info/definition/hu1_001.xml</suiteXmlFile>
                <suiteXmlFile>test_info/definition/hu1_002.xml</suiteXmlFile>
            </suiteXmlFiles>
            <systemPropertyVariables>
                <operativeSystem>windows</operativeSystem>
                <env>dev</env>
                <baseUrl>http://a2censo.com.test.portal.s3-website-us-east-1.amazonaws.com/</baseUrl>
                <dataBasePath>test_info/data/</dataBasePath>
            </systemPropertyVariables>
        </configuration>
    </plugin>
</plugins>
```
### Evidencias de ejecución
##### Salida
   - ###### Test-output: Todos los reportes de ejecución se generan en la carpeta **test-output**.
   - ###### Evidencias: Todas las evidencais de ejecución de la prueba se guardan en la carpeta **test_info/execution_evidences/fecha_ejecucion**.
##### Clases
   - ###### Reporter
Se usa la clase **Reporter** de **TestNG** para crear un log de los reportes, pero debido a que este permite una salida con html se define la clase [**CustomReporter**](src/test/java/com/bvc/a2censo/test/util/CustomReporter.java "Clase Util Custom Reporter") que permite personalizar los logs, por ejemplo:
```java
CustomReporter.log(msg); //Salida normal
CustomReporter.title(msg); //Para titulo
CustomReporter.subTitle(msg); //Para subtitulo
CustomReporter.subSubTitle(msg); //Para subsubtitulo
CustomReporter.error(msg); //Para error
```
- ###### ScreenShot
```java
//Para tomar el screenshot de una página
ImageUtils.takeScreenshot(driver,testPath,imageName)
// Para tomar el screenshot y referenciar la ruta en el reporte
CustomReporter.log(ImageUtils.takeScreenshot(driver,testPath,imageName));
// Para tomar el screenchot de un elemento
ImageUtils.takeElementScreenShot(driver,element,testPath,imageName)
// Para tomar el screenshot y referenciar la ruta en el reporte
CustomReporter.log(ImageUtils.takeElementScreenShot(driver,element, testPath,imageName));
```
### Responsive Test
Para probar el responsive de la aplicación se usa [Galen Framework](http://galenframework.com/ "Galen Framework").
###### Archivos `.spec`
Los archivos para las pruebas se definen en la carpeta [specs](test_info/data/specs/ "Spec files") y se llama al verificación desde el caso de prueba haciendo uso del comando:
```java
Galen.checkLayout(driver, pageSpectPath, device.getTags()),device.getName(),page
```
La clase Device permite identificar el tamaño y nombre del dispositivo en donde se esta probando. Los dispositivos donde se realiza la prueba se definen con el siguiente `@DataProvider`:
```java
@DataProvider(name = "devices")
    public Object [][] devices () {
        return new Object[][] {
                {new TestDevice("mobile", new Dimension(450, 800), asList("mobile"))},
                {new TestDevice("tablet", new Dimension(750, 800), asList("tablet"))},
                {new TestDevice("desktop", new Dimension(1024, 800), asList("desktop"))}
        };
    }
```

###### Reportes
Los reportes se generan con el framework de **Galen** y se asocian al reporte generado por **TestNG** por medio de un link.


