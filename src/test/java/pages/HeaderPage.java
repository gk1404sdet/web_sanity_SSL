package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class HeaderPage extends BasePage {

    public final JavascriptExecutor js = (JavascriptExecutor) driver;



    // ---------- Locators ----------
    private final By fashionStylist = By.xpath("//img[@alt=\"fashion_stylist_logo\"]");
    private final By searchEditBox = By.xpath("//input[@type=\"text\"]");
    public final By trendingSearch = By.xpath("//div[@class=\"xs:text-sm select-none md:select-text\"]");
    public final By popularBrands = By.xpath("//div[@class=\"bg-transparent flex flex-col flex-wrap\"]");
    private final By storeIcon = By.xpath("//img[@alt=\"near-by-store\"]");
    private final By storeCity = By.id("Select City");
    private final By topCategory = By.xpath("//div[@class=\"bg-transparent\"]");
    private final By categoryLocator = By.xpath("//div[@class='bg-transparent']//a");
    private final By iFrameFashion = By.xpath("//iframe[@class=\"border-0 opacity-100\"]");
    private final By edit = By.id("mui-2");
    private final By productCard = By.xpath("//div[@class='content ']");
    private final By brandLocator = By.xpath(".//div[@class='ff-chatbot-caption brand']");
    private final By titleLocator = By.xpath(".//div[@class='title  ']");
    private final By discountPriceLocator = By.xpath(".//div[@class='ff-chatbot-body2 sale-price']");
    private final By discountPercentLocator = By.xpath(".//div[@class='ff-chatbot-caption discount ']");
    private final By brandTitle = By.xpath("//div[@class='mb-6 text-lg font-normal uppercase leading-[21.47px] tracking-[1.8px] text-blackSS']");
    private final By cityDropdownList = By.xpath("//ul[@class=\"options-container countryDropdown absolute left-0 top-full z-[51] m-0 my-1 max-h-56 w-full list-none overflow-y-auto border border-gray-300 bg-white p-0 shadow-xl\"]/li");
    private final By cityOptionList = By.xpath("//li[@class=\"option cursor-pointer p-2.5 text-xs capitalize text-ssBlack focus-within:bg-red-300 hover:bg-[#f0f0f0] md:text-sm\"]");


    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    // ---------- Common Actions ----------
    public List<String> getAllComponentList() {
        List<String> componentTexts = new ArrayList<>();

        try {
            // Top category section
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(topCategory));
            List<WebElement> firstElements = driver.findElements(topCategory);

            for (WebElement element : firstElements) {
                String text = element.getText().trim();
                if (!text.isEmpty()) {
                    componentTexts.add(text);
                }
            }
        } catch (Exception e) {}
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
                    continue;
                }
                wait.until(ExpectedConditions.elementToBeClickable(target)).click();
                wait.until(ExpectedConditions.urlContains(expectedUrlPart));

                driver.navigate().back();
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(categoryLocator));
            } catch (Exception ignored) {}
        }
    }

    public void clicksOnTheFashionStylist() {
        wait.until(ExpectedConditions.elementToBeClickable(fashionStylist)).click();
    }

    public void entersTheQuery(String str) {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrameFashion));
        // Click on Edit Box
        wait.until(ExpectedConditions.elementToBeClickable(edit)).click();
        wait.until(ExpectedConditions.elementToBeClickable(edit)).sendKeys(str);
        wait.until(ExpectedConditions.elementToBeClickable(edit)).sendKeys(Keys.ENTER);
    }

    public void printSuggestedProductDetails() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            List<WebElement> productCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCard));

            for (WebElement card : productCards) {
                String brand = "";
                String title = "";
                String discountedPrice = "";
                String discount = "";

                try {
                    // Use relative locators inside each card
                    WebElement brandEl = card.findElement(brandLocator);
                    WebElement titleEl = card.findElement(titleLocator);
                    WebElement discountPriceEl = card.findElement(discountPriceLocator);
                    WebElement discountPercentEl = card.findElement(discountPercentLocator);

                    brand = brandEl.getText().trim();
                    title = titleEl.getText().trim();
                    discountedPrice = discountPriceEl.getText().trim();
                    discount = discountPercentEl.getText().trim();

                } catch (Exception ignored) {
                    continue;
                }

                // Validation check (instead of printing)
                if (brand.isEmpty() || title.isEmpty() || discountedPrice.isEmpty() || discount.isEmpty()) {
                    throw new RuntimeException("Incomplete product details found in one of the cards.");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while fetching cleaned product details: " + e.getMessage(), e);
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
            for (String name : uniqueNames) {
                String formatted = index + ". " + name;
                productNames.add(formatted);
                index++;
            }

            if (productNames.isEmpty()) {
                throw new RuntimeException("No unique product names found in section: " + sectionTitle);
            }

        } catch (Exception e) {
            throw new RuntimeException("Exception in section '" + sectionTitle + "': " + e.getMessage(), e);
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
                throw new RuntimeException("Invalid index '" + indexToClick + "' for section '"
                        + sectionTitle + "'. Total products: " + entries.size());
            }

            Map.Entry<String, WebElement> selected = entries.get(indexToClick - 1);
            WebElement elementToClick = selected.getValue();
            String expectedBrandName = selected.getKey();

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", elementToClick);
            wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).click();

            String actualBrandName = extractBrandNameFromPage();

            if (!actualBrandName.equalsIgnoreCase(expectedBrandName)) {
                throw new AssertionError("Brand name mismatch! Expected: '"
                        + expectedBrandName + "' but found: '" + actualBrandName + "'");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to click product at index " + indexToClick
                    + " in section '" + sectionTitle + "': " + e.getMessage(), e);
        }
    }

    public void entersTheDynamicSearchKeyword(String str) {
        enterTextOnElement(searchEditBox, str);
    }

    public String extractBrandNameFromPage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement brandTitleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(brandTitle));

            String fullText = brandTitleElement.getText().trim();
            String brandName = fullText.replaceAll("\\(.*\\)", "").trim();

            return brandName;

        } catch (Exception e) {
            throw new RuntimeException("Error extracting brand name from page: " + e.getMessage(), e);
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

            // Fetch cities
            List<WebElement> cityElements = driver.findElements(cityDropdownList);
            List<String> allCities = new ArrayList<>();

            for (WebElement city : cityElements) {
                try {
                    String cityText = city.getText().trim();
                    if (!cityText.isEmpty() && !allCities.contains(cityText)) {
                        allCities.add(cityText);
                    }
                } catch (StaleElementReferenceException ignored) {}
            }

            if (allCities.isEmpty()) {
                throw new RuntimeException("No cities found in dropdown.");
            }

            // Handle city selection based on input
            if (cityIdentifier == null || cityIdentifier.equalsIgnoreCase("random")) {
                cityIdentifier = allCities.get(new Random().nextInt(allCities.size()));
            } else if (cityIdentifier.matches("\\d+")) {
                int serial = Integer.parseInt(cityIdentifier);
                if (serial < 1 || serial > allCities.size()) {
                    throw new RuntimeException("Invalid serial number: " + serial);
                }
                cityIdentifier = allCities.get(serial - 1);
            } else if (!allCities.contains(cityIdentifier)) {
                throw new RuntimeException("City '" + cityIdentifier + "' not found in dropdown.");
            }

            // Click the selected city
            List<WebElement> allOptions = driver.findElements(cityOptionList);

            for (WebElement option : allOptions) {
                try {
                    String cityText = option.getText().trim();
                    if (cityText.equalsIgnoreCase(cityIdentifier)) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                        selectedCity = cityIdentifier;
                        break;
                    }
                } catch (StaleElementReferenceException ignored) {}
            }

            if (selectedCity == null) {
                throw new RuntimeException("Could not click on selected city.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception in selectCity(): " + e.getMessage());
        } finally {
            driver.switchTo().defaultContent();
        }

        return selectedCity;
    }



    }

