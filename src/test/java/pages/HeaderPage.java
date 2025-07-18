package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;

public class HeaderPage extends BasePage {

    public final JavascriptExecutor js = (JavascriptExecutor) driver;

    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    private final By fashionStylist = By.xpath("//img[@alt=\"fashion_stylist_logo\"]");
    private final By searchEditBox = By.xpath("//input[@type=\"text\"]");
    public final By trendingSearch = By.xpath("//div[@class=\"xs:text-sm select-none md:select-text\"]");
    public final By trendingProduct = By.xpath("//div[@class=\"text-[10px] md:text-xs lg:text-sm xl:text-base font-medium !leading-[18px] text-neutral-900 select-none md:select-text\"]");
    public final By popularBrands = By.xpath("//div[@class=\"bg-transparent flex flex-col flex-wrap\"]");
    private final By storeIcon = By.xpath("//img[@alt=\"near-by-store\"]");
    private final By storeCity = By.id("Select City");



    public List<String> getAllComponentList() {
        List<String> componentTexts = new ArrayList<>();

        try {
            // Top category section
            By firstLocator = By.xpath("//div[@class=\"bg-transparent\"]");
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(firstLocator));
            List<WebElement> firstElements = driver.findElements(firstLocator);

            for (WebElement element : firstElements) {
                String text = element.getText().trim();
                if (!text.isEmpty()) {
                    componentTexts.add(text);
                }
            }
        } catch (Exception e) {
            System.out.println("Error while fetching component list: " + e.getMessage());
        }
        return componentTexts;
    }

    public void navigateThroughTopCategoriesWithUrlMapping() {
        // Use LinkedHashMap to maintain insertion order
        Map<String, String> urlMap = new LinkedHashMap<>();
        urlMap.put("Men", "men");
        urlMap.put("Women", "women");
        urlMap.put("Kids", "kids");
        urlMap.put("Brands", "home");
        urlMap.put("Beauty", "beauty");
        urlMap.put("Watches", "watches");
        urlMap.put("Gifts", "gifts");
        urlMap.put("Homestop", "Homestop");

        By categoryLocator = By.xpath("//div[@class='bg-transparent']//a");

        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            String categoryName = entry.getKey();
            String expectedUrlPart = entry.getValue();

            try {
                // Re-fetch list after every navigation
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(categoryLocator));
                List<WebElement> categoryLinks = driver.findElements(categoryLocator);

                WebElement target = categoryLinks.stream()
                        .filter(el -> el.getText().trim().equalsIgnoreCase(categoryName))
                        .findFirst()
                        .orElse(null);

                if (target == null) {
                    System.out.println("Category not found: " + categoryName);
                    continue;
                }
                wait.until(ExpectedConditions.elementToBeClickable(target)).click();
                System.out.println("Clicked on category: " + categoryName);

                wait.until(ExpectedConditions.urlContains(expectedUrlPart));
                String currentUrl = driver.getCurrentUrl();

                if (currentUrl.contains(expectedUrlPart)) {
                    System.out.println("Navigation successful --> " + currentUrl);
                } else {
                    System.out.println("URL mismatch. Expected to contain '" + expectedUrlPart + "' but got: " + currentUrl);
                }
                driver.navigate().back();
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(categoryLocator));
            } catch (Exception e) {
                System.out.println("Failed navigating category: " + categoryName);
                e.printStackTrace();
            }
        }
    }

    public void clicksOnTheFashionStylist() {
        wait.until(ExpectedConditions.elementToBeClickable(fashionStylist)).click();
    }

    public void entersTheQuery(String str) {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@class=\"border-0 opacity-100\"]")));
        System.out.println("Switched to Fashion Stylist iframe");
        // Click on Edit Box
        WebElement edit = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("mui-2")));
        edit.click();
        edit.sendKeys(str);
        edit.sendKeys(Keys.ENTER);
    }

    public void printSuggestedProductDetails() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            List<WebElement> productCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[@class='content ']")));

            System.out.println("Total Products Found: " + productCards.size());
            System.out.println("-----------------");

            for (WebElement card : productCards) {
                String brand = "";
                String title = "";
                String discountedPrice = "";
                String discount = "";

                try {
                    // Exact targeted XPaths inside each card
                    WebElement brandEl = card.findElement(By.xpath("//div[@class=\"ff-chatbot-caption brand\"]"));
                    WebElement titleEl = card.findElement(By.xpath("//div[@class=\"title  \"]"));
                    WebElement discountPriceEl = card.findElement(By.xpath("//div[@class=\"ff-chatbot-body2 sale-price\"]"));
                    WebElement discountPercentEl = card.findElement(By.xpath("//div[@class=\"ff-chatbot-caption discount \"]"));

                    brand = brandEl.getText().trim();
                    title = titleEl.getText().trim();
                    discountedPrice = discountPriceEl.getText().trim();
                    discount = discountPercentEl.getText().trim();

                } catch (Exception e) {
                    continue;
                }
                // Final print
                if (!brand.isEmpty() && !title.isEmpty() && !discountedPrice.isEmpty() && !discount.isEmpty()) {
                    System.out.println(brand + " - " + title + " - " + discountedPrice + " - " + discount);
                }
            }
        } catch (Exception e) {
            System.out.println("Error while fetching cleaned product details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void clicksOnTheSearEditBox() {
        wait.until(ExpectedConditions.elementToBeClickable(searchEditBox)).click();
    }

    public List<String> extractProductNames(By locator, String sectionTitle) {
        List<String> productNames = new ArrayList<>();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            List<WebElement> productElements = driver.findElements(locator);

            Set<String> uniqueNames = new LinkedHashSet<>();
            for (WebElement product : productElements) {
                String name = product.getText().trim();
                if (!name.isEmpty()) {
                    uniqueNames.add(name);
                }
            }

            int index = 1;
            System.out.println("----- " + sectionTitle + " -----");
            for (String name : uniqueNames) {
                String formatted = index + ". " + name;
                productNames.add(formatted);
                System.out.println(" " + formatted);
                index++;
            }

            if (productNames.isEmpty()) {
                System.out.println("No unique product names found in section: " + sectionTitle);
            }

        } catch (Exception e) {
            Assert.fail("Exception in section '" + sectionTitle + "': " + e.getMessage());
        }

        return productNames;
    }

    public void clickProductByIndex(By locator, int indexToClick, String sectionTitle) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            List<WebElement> productElements = driver.findElements(locator);

            Map<String, WebElement> uniqueProducts = new LinkedHashMap<>();
            for (WebElement product : productElements) {
                String name = product.getText().trim();
                if (!name.isEmpty() && !uniqueProducts.containsKey(name)) {
                    uniqueProducts.put(name, product);
                }
            }

            List<Map.Entry<String, WebElement>> entries = new ArrayList<>(uniqueProducts.entrySet());

            if (indexToClick <= 0 || indexToClick > entries.size()) {
                Assert.fail("Invalid index '" + indexToClick + "' for section '" + sectionTitle + "'. Total products: " + entries.size());
            }

            Map.Entry<String, WebElement> selected = entries.get(indexToClick - 1);
            WebElement elementToClick = selected.getValue();
            String expectedBrandName = selected.getKey();

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", elementToClick);
            wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).click();

            System.out.println("Clicked product at index " + indexToClick + ": " + expectedBrandName);

            String actualBrandName = extractBrandNameFromPage();
            Assert.assertTrue(
                    actualBrandName.equalsIgnoreCase(expectedBrandName),
                    "Brand name mismatch! Expected: '" + expectedBrandName + "' but found: '" + actualBrandName + "'"
            );


            System.out.println("Brand assertion passed for: " + actualBrandName);

        } catch (Exception e) {
            Assert.fail("Failed to click product at index " + indexToClick + " in section '" + sectionTitle + "': " + e.getMessage());
        }
    }

    public void entersTheDynamicSearchKeyword(String str) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement ele = driver.findElement(searchEditBox);
        ele.sendKeys(str);
    }

    public String extractBrandNameFromPage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement brandTitleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='mb-6 text-lg font-normal uppercase leading-[21.47px] tracking-[1.8px] text-blackSS']")
            ));
            String fullText = brandTitleElement.getText().trim();

            String brandName = fullText.replaceAll("\\(.*\\)", "").trim();

            System.out.println("Brand Name Found: " + brandName);
            return brandName;
        } catch (Exception e) {
            Assert.fail("Error extracting brand name from page: " + e.getMessage());
            return null; // To satisfy compiler, won't reach here
        }
    }

    public void clicksOnTheStoreIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(storeIcon)).click();
    }

    public void clicksOnTheDropDown() {
        wait.until(ExpectedConditions.elementToBeClickable(storeCity)).click();
    }

    public String selectCity(String cityIdentifier) {
        String selectedCity = null;

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Fetch and print cities with serial numbers
            List<WebElement> cityElements = driver.findElements(By.xpath
                    ("//ul[@class=\"options-container countryDropdown absolute left-0 top-full z-[51] m-0 my-1 max-h-56 w-full list-none overflow-y-auto border border-gray-300 bg-white p-0 shadow-xl\"]/li"));
            List<String> allCities = new ArrayList<>();

            int index = 1;
            for (WebElement city : cityElements) {
                try {
                    String cityText = city.getText().trim();
                    if (!cityText.isEmpty() && !allCities.contains(cityText)) {
                        allCities.add(cityText);
                        System.out.println(index + ". " + cityText);
                        index++;
                    }
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element skipped.");
                }
            }
            if (allCities.isEmpty()) {
                throw new RuntimeException("No cities found in dropdown.");
            }
            // Handle city selection based on input
            if (cityIdentifier == null || cityIdentifier.equalsIgnoreCase("random")) {
                cityIdentifier = allCities.get(new Random().nextInt(allCities.size()));
                System.out.println("Randomly selected city: " + cityIdentifier);
            } else if (cityIdentifier.matches("\\d+")) {
                int serial = Integer.parseInt(cityIdentifier);
                if (serial < 1 || serial > allCities.size()) {
                    throw new RuntimeException("Invalid serial number: " + serial);
                }
                cityIdentifier = allCities.get(serial - 1);
                System.out.println("Selected city by serial number: " + cityIdentifier);
            } else if (!allCities.contains(cityIdentifier)) {
                throw new RuntimeException("City '" + cityIdentifier + "' not found in dropdown.");
            }
            // Click the selected city
            List<WebElement> allOptions = driver.findElements(By.xpath
                    ("//li[@class=\"option cursor-pointer p-2.5 text-xs capitalize text-ssBlack focus-within:bg-red-300 hover:bg-[#f0f0f0] md:text-sm\"]"));

            for (WebElement option : allOptions) {
                try {
                    String cityText = option.getText().trim();
                    if (cityText.equalsIgnoreCase(cityIdentifier)) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                        selectedCity = cityIdentifier;
                        System.out.println("Selected city: " + cityIdentifier);
                        break;
                    }
                } catch (StaleElementReferenceException e) {
                    System.out.println("Retrying stale element...");
                }
            }
            if (selectedCity == null) {
                throw new RuntimeException(" Could not click on selected city.");
            }
        } catch (Exception e) {
            Assert.fail("Exception in selectCity(): " + e.getMessage());
        } finally {
            driver.switchTo().defaultContent();
        }
        return selectedCity;
    }

    }

