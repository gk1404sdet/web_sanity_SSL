package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class HomePage extends BasePage{

    private final JavascriptExecutor js = (JavascriptExecutor) driver;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public final By men = By.xpath("(//a[@href=\"/category/men\"])[1]");
    public final By women = By.xpath("(//a[@href=\"/category/women\"])[1]");
    public final By kids = By.xpath("(//a[@href=\"/category/kids\"])[1]");
    public final By formalShoes = By.xpath("//li[contains(text(),'Formal Shoes')]");
    public final By topsAndTshirt = By.xpath("//li[contains(text(), 'Tops & T-shirts')]");
    public final By blazerKids = By.xpath("//li[contains(text(), 'Blazers & Coats')]");
    public final By bargains = By.xpath("(//div[@data-testid])[2]");
    private final By sortBy = By.xpath("//button[@role=\"combobox\"]");
    public final By dressWomen = By.xpath("//p[contains(text(), 'Ethnicwear')]");
    public final By shirt = By.xpath("//p[contains(text(), 'Shirts')]");
    public final By bed = By.xpath("//p[contains(text(),'Bed')]");
    public final By womenFragrance = By.xpath("//p[contains(text(), 'Womens Fragrance')]");
    public final By newSort = By.id("radix-:r8:");
    public final By discount = By.id("radix-:r9:");
    public final By lowToHigh = By.id("radix-:ra:");
    public final By highToLow = By.id("radix-:rb:");
    public final By sarees = By.xpath("//li[contains(text(), 'Sarees')]");


    public void clickOnSubCategory(By locator) {
        clickOnElement(locator);
    }

    public void printAndClickFilterOptions(String str, List<Integer> indicesToClick) {
        try {
            clickOnTextButton(str);
            // Fetch all filter options
            List<WebElement> filterDivs = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[@class='bg-transparent mb-2 flex items-start justify-between']")));
            if (filterDivs.isEmpty()) {
                System.out.println("No filter options found.");
                return;
            }
            // filter options
            System.out.println("----- All Available Filters -----");
            int count = 1;
            for (WebElement filter : filterDivs) {
                System.out.println(count + ". " + filter.getText().trim());
                count++;
            }
            // Click selected by index
            System.out.println("----- Clicking Selected Filters -----");
            for (int index : indicesToClick) {
                if (index >= 1 && index <= filterDivs.size()) {
                    WebElement selectedFilter = filterDivs.get(index - 1);
                    System.out.println("Clicking: " + selectedFilter.getText().trim());
                    selectedFilter.click();
                    Thread.sleep(500);
                } else {
                    System.out.println("Invalid index: " + index);
                }
            }
          clickOnTextButton(str);
        } catch (Exception e) {
            Assert.fail("Exception during filter print/click: " + e.getMessage());
        }
    }

    public void printProductDetails() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement container = wait.until(ExpectedConditions.
                    visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Product Details')]")));


            // Fetch all label-value pairs inside the product details section
            List<WebElement> detailElements = container.findElements(By.xpath("//div[@class=\"flex flex-wrap xs:gap-3\"]"));

            if (detailElements.isEmpty()) {
                System.out.println("No product details found.");
                return;
            }

            System.out.println("----- PRODUCT DETAILS -----");
            for (WebElement element : detailElements) {
                String text = element.getText().trim();
                if (!text.isEmpty()) {
                    System.out.println(text);
                }
            }
        } catch (Exception e) {
            Assert.fail("Exception while fetching product details: " + e.getMessage());
        }
    }

    public void clickHomeLinkIfAvailable() {
        try {
            waitForOverlayToDisappear();
            List<WebElement> homeLinks = driver.findElements(By.xpath("//a[@href='/home']"));
            if (!homeLinks.isEmpty()) {
                WebElement homeLink = homeLinks.get(0);
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", homeLink);
                wait.until(ExpectedConditions.elementToBeClickable(homeLink)).click();
            } else {}
        } catch (Exception e) {
            Assert.fail("Error clicking home link: " + e.getMessage());
        }
    }

    public void clickOnSortBy() {
        clickOnElement(sortBy);
    }

    public void applyFilter(By loca) {
        clickOnElement(loca);
    }

}
