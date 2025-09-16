package hooks;

import context.TestContext;
import factory.DriverFactory;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import pages.*;
import utilities.ConfigLoader;
import utilities.CredsLoader;

import java.io.*;

public class Hooks {
    private static WebDriver driver;
    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @BeforeAll
    public static void setUpAll() {
        driver = DriverFactory.initializeDriver();
//        driver = DriverFactory.getDriver();
    }

    @Before
    public void setUp(Scenario scenario) {
//        driver = DriverFactory.initializeDriver();
//        driver = DriverFactory.getDriver();
        context.driver = driver;
        context.credsLoader = new CredsLoader();
        context.configLoader = new ConfigLoader();
        context.loginPage = new LoginPage(context.driver);
        context.accountPage = new AccountPage(context.driver);
        context.checkoutPage = new CheckoutPage(context.driver);
        context.headerPage = new HeaderPage(context.driver);
        context.wishlistPage = new WishlistPage(context.driver);
        context.cartPage = new CartPage(context.driver);
        context.homePage = new HomePage(context.driver);
        context.scenario = scenario;

    }

    @After
    public void tearDown(Scenario scenario) throws IOException {
        // Take screenshot if the scenario failed
        if (scenario.isFailed()) {
            File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            byte[] fileContent = FileUtils.readFileToByteArray(sourcePath);
            scenario.attach(fileContent, "image/png", "image");
        }

        String scenarioName = scenario.getName();
        if (scenarioName.contains("Verify the OTP functionality with an invalid OTP") ||
                scenarioName.contains("Login as a user with an invalid mobile number") ||
                scenarioName.contains("Login as a user with an invalid email id")) {

            driver.navigate().refresh();
        }
    }

    @AfterAll
    public static void tearDownAll() {
        DriverFactory.quitDriver();
    }
}
