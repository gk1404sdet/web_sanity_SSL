package factory;

import constants.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverFactory {

    public static WebDriver driver;

    public static WebDriver getDriver() {
        if(driver==null){
            initializeDriver();
        }
        return driver;
    }

    public static WebDriver initializeDriver() {
//        WebDriver driver;
        String browser = System.getProperty("browser", String.valueOf(Browser.CHROME));

        switch (Browser.valueOf(browser.toUpperCase())) {
            case CHROME -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                if (System.getProperty("browserMode", "normal").equalsIgnoreCase("head")) {
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("window-size=1920,1080");
                }
                driver = new ChromeDriver(chromeOptions);
            }
            case FIREFOX -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (System.getProperty("browserMode", "normal").equalsIgnoreCase("headless")) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
            }
            default -> throw new RuntimeException("Invalid Browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver;
    }

    public static void quitDriver() {
        if(driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

