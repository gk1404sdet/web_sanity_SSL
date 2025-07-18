package context;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import pages.*;
import utilities.ConfigLoader;
import utilities.CredsLoader;

public class TestContext {
    public WebDriver driver;
    public CredsLoader credsLoader;
    public ConfigLoader configLoader;
    public Scenario scenario;
    public LoginPage loginPage;
    public AccountPage accountPage;
    public CheckoutPage checkoutPage;
    public HeaderPage headerPage;
    public WishlistPage wishlistPage;
    public CartPage cartPage;
    public HomePage homePage;

}
