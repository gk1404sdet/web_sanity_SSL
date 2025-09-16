package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
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
    public final By newSort = By.xpath("//span[contains(text(), 'New')]");
    public final By discount = By.xpath("//span[contains(text(), 'Discount')]");
    public final By lowToHigh = By.xpath("//span[contains(text(), 'Price Low to High')]");
    public final By highToLow = By.xpath("//span[contains(text(), 'Price High to Low')]");
    public final By sarees = By.xpath("//li[contains(text(), 'Sarees')]");
    private final By filterDivsLocator = By.xpath("//div[@class='bg-transparent mb-2 flex items-start justify-between']");
    private final By productDetailsButton = By.xpath("//button[contains(text(), 'Product Details')]");
    private final By productDetailElements = By.xpath("//div[@class='flex flex-wrap xs:gap-3']");
    private final By homeLinkLocator = By.xpath("//a[@href='/home']");
    private final By productDetails = By.xpath("//button[contains(text(), 'Product Details')]");
    private final By aboutTheBrand = By.xpath("//button[contains(text(), 'About The Brand')]");
    private final By ratingAndReview = By.xpath("//button[contains(text(), 'RATINGS & REVIEWS')]");
    private final By checkDelivery = By.xpath("//div[contains(text(), 'Check Delivery')]");




    public void moveToCategory(By locat) {
        moveToElement(locat);
    }

    public void clickOnSubCategory(By locator) {
        clickOnElement(locator);
    }

    public void printAndClickFilterOptions(String str, List<Integer> indicesToClick) {
        try {
            clickOnTextButton(str);

            // Fetch all filter options
            List<WebElement> filterDivs = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(filterDivsLocator));

            if (filterDivs.isEmpty()) {
                return;
            }

            // Click selected by index
            for (int index : indicesToClick) {
                if (index >= 1 && index <= filterDivs.size()) {
                    WebElement selectedFilter = filterDivs.get(index - 1);
                    selectedFilter.click();
                    Thread.sleep(500);
                }
            }
            clickOnTextButton(str);
        } catch (Exception ignored) {}
    }

    public boolean validateProductDetailSections(List<String> expectedSections) {
        List<By> expectedComponents = Arrays.asList(
                productDetails,
                aboutTheBrand,
                ratingAndReview,
                checkDelivery
        );
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean allFound = true;
        for (By locator : expectedComponents) {
            try {
                if (locator.equals(productDetails) ||
                        locator.equals(aboutTheBrand) ||
                        locator.equals(ratingAndReview))  {
                    WebElement element = driver.findElement(locator);
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                }

                wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            } catch (TimeoutException e) {
                allFound = false;
            }
        }
        return allFound;
    }

    public void printProductDetails() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(productDetailsButton));

            // Fetch all label-value pairs inside the product details section
            List<WebElement> detailElements = container.findElements(productDetailElements);

            for (WebElement element : detailElements) {
                String text = element.getText().trim();
                if (!text.isEmpty()) {}
            }
        } catch (Exception e) {}
    }

    public void clickHomeLinkIfAvailable() {
        try {
            waitForOverlayToDisappear();
            List<WebElement> homeLinks = driver.findElements(homeLinkLocator);
            if (!homeLinks.isEmpty()) {
                WebElement homeLink = homeLinks.get(0);
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", homeLink);
                wait.until(ExpectedConditions.elementToBeClickable(homeLink)).click();
            }
        } catch (Exception e) {}
    }

    public void clickOnSortBy() {
        clickOnElement(sortBy);
    }

    public void applyFilter(By loca) {
        clickOnElement(loca);
    }

}
