package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class AccountPage extends BasePage {

    public final Actions actions = new Actions(driver);
    public final JavascriptExecutor js = (JavascriptExecutor) driver;
    private String selectedStore;
    private String selectedDateFormatted;
    private String selectedSlot;

    public AccountPage(WebDriver driver) {
        super(driver);
    }


    public final By profile = By.xpath("//div[contains(text(),'My Profile')]");
    public final By name = By.xpath("//input[@id='Name']");
    public final By mobNo = By.xpath("//input[@id='Mobile No.']");
    public final By pinCode = By.xpath("//input[@id='Pin Code']");
    public final By address = By.xpath("//textarea[@id='Address']");
    public final By addressType = By.xpath("(//button[@type='button'])[3]");
    public final By newAddAddress = By.xpath("//p[contains(text(),'ADD ADDRESS')]");
    private final By deleteAddress = By.xpath("(//div[contains(text(),'Delete')])[2]");
    private final By confirmDelete = By.xpath("//p[contains(text(),'Confirm')]");
    public final By bookAPersonalShopper = By.xpath("//div[contains(text(),'Book a Personal Shopper')]");
    private final By bookAnAppointment = By.xpath("//p[contains(text(),'BOOK AN APPOINTMENT')]");
    private final By accountComponent = By.xpath("(//div[@class=\"bg-transparent\"])[28]//div");
    private final By bookAService = By.xpath("//p[contains(text(), 'book a service')]");
    private final By proceedToBook = By.xpath("//p[contains(text(), 'Proceed To Book')]");
    private final By myOrders = By.xpath("//div[contains(text(), 'My Orders')]");
    private final By myWallet = By.xpath("//div[contains(text(), 'My Wallet')]");
    private final By egv = By.xpath("//div[contains(text(), 'Gift Card/EGV')]");




    public void clicksOnMyProfile() {
        clickOnElement(profile);
    }

    public void clicksOnDelete() {
        clickOnElement(deleteAddress);
    }

    public void clicksOnConfirmation() {
        clickOnElement(confirmDelete);
    }

    public void clicksOnBookAnAppointment() {
        clickOnElement(bookAnAppointment);
    }

    public void clicksOnBookAService() {
        clickOnElement(bookAService);
    }

    public void clicksOnProceedToBook() {
        clickOnElement(proceedToBook);
    }

    public void clicksOnMyOrders() {
        clickOnElement(myOrders);
    }

    public void clicksOnMyWallet() {
        clickOnElement(myWallet);
    }

    public void clicksOnGiftCard() {
        clickOnElement(egv);
    }

    public List<String> getAllComponentList() {
        waitForOverlayToDisappear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountComponent));
        List<WebElement> componentElements = driver.findElements
                (By.xpath("//div[@class=\"md:!leading-4, cursor-pointer px-5 py-2.5 text-sm hover:bg-neutral-100 hover:font-medium bg-white font-normal leading-4 text-ssBlackHeavy select-none md:select-text\"]"));

        List<String> componentTexts = new ArrayList<>();
        for (WebElement element : componentElements) {
            String text = element.getText().trim();
            if (!text.isEmpty()) {
                componentTexts.add(text);
            }
        }
        return componentTexts;
    }

    public void clickOnTheEdit(boolean condition) {

        WebElement editOne = driver.findElement(By.xpath("(//div[contains(text(),'Edit')])[1]"));
        WebElement editTwo = driver.findElement(By.xpath("(//div[contains(text(),'Edit')])[2]"));

        if (condition) {
            actions.moveToElement(editOne);
            editOne.click();
            System.out.println("Clicked on the first Edit button.");
        } else {
            actions.moveToElement(editTwo);
            editTwo.click();
            System.out.println("Clicked on the third Edit button.");
        }
    }

    public void toggleGenderSelection() {
        WebElement maleGender = driver.findElement(By.xpath("(//button[@type='button'])[1]"));
        WebElement femaleGender = driver.findElement(By.xpath("(//button[@type='button'])[2]"));

        boolean isMaleSelected = maleGender.getAttribute("aria-checked").equals("true");

        if (isMaleSelected) {
            femaleGender.click();
            System.out.println("Switched to Female gender option.");
        } else {
            maleGender.click();
            System.out.println("Switched to Male gender option.");
        }
    }

    public void clickOnTheUpdateChanges() {

        try {
            if (isElementPresent(By.xpath("//p[contains(text(),'update changes')]"))) {
                WebElement update = driver.findElement(By.xpath("//p[contains(text(),'update changes')]"));
                if (update.isDisplayed() && update.isEnabled()) {
                    update.click();
                    System.out.println("Clicked on 'update changes'.");
                    return;
                }
            }

            if (isElementPresent(By.xpath("//p[contains(text(),'UPDATE CHANGES')]"))) {
                WebElement updated = driver.findElement(By.xpath("//p[contains(text(),'UPDATE CHANGES')]"));
                if (updated.isDisplayed() && updated.isEnabled()) {
                    updated.click();
                    System.out.println("Clicked on 'UPDATE CHANGES'.");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public void clickOnTheAddAddress() {
        By addAddressBtn = By.xpath("//img[@alt=\"add_address\"]");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement freshButton = wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.elementToBeClickable(addAddressBtn)
        ));
        freshButton.click();
    }

    public String selectCity(String cityIdentifier) {
        String selectedCity = null;

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement dropdownBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@role, 'combobox')]")));
            dropdownBtn.click();
            js.executeScript("arguments[0].click();", dropdownBtn);

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@role='option']//span")));
            // Freeze dropdown
            js.executeScript(
                    "document.querySelector('[role=\"listbox\"]').addEventListener('mousedown', function(e) { e.stopPropagation(); }, true);");
            // Fetch and print cities with serial numbers
            List<WebElement> cityElements = driver.findElements(By.xpath("//div[@role='option']//span"));
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
            List<WebElement> allOptions = driver.findElements(By.xpath("//div[@role='option']"));

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

    public void fetchAllStoreDetails() {
        try {
            List<WebElement> elementsInside = driver.findElements(By.xpath("//label[@for='-1']//div"));
            System.out.println("Total elements found: " + elementsInside.size());
            selectedStore = elementsInside.get(0).getText().trim();

            for (int i = 0; i < elementsInside.size(); i++) {
                String text = elementsInside.get(i).getText().trim();
                System.out.println("Element " + (i + 1) + ": " + (text.isEmpty() ? "[No Text Found]" : text));
            }
            WebElement selectStore = driver.findElement(By.xpath("(//button[contains(@role, 'radio')])[1]"));
            selectStore.click();

        } catch (Exception e) {
            System.out.println("Exception while fetching elements inside label[@for='-1']: " + e.getMessage());
        }
    }

    public void selectTomorrowDate() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Locate the calendar icon
            WebElement calendarIcon = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//img[@alt='calendar icon']")));

            // Scroll to the icon to avoid footer overlaps
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", calendarIcon);
            Thread.sleep(300);

            js.executeScript("arguments[0].click();", calendarIcon);
            // tomorrow's date
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            int dayInt = tomorrow.getDayOfMonth();
            String day = String.valueOf(dayInt);

            // XPath for tomorrow's day in the calendar
            String dayXpath = "//tr[contains(@class, 'rdp-row')]//td[contains(@class,'rdp-cell')]//button[normalize-space(text())='" + day + "']";
            WebElement dateButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dayXpath)));
            // Scroll the popup panel (if any)
            try {
                WebElement modalPanel = driver.findElement(By.xpath("//div[contains(@class,'overflow-y-scroll') or contains(@class,'overflow-auto')]"));
                    js.executeScript("arguments[0].scrollTop = arguments[1].offsetTop;", modalPanel, dateButton);
            } catch (Exception e) {
                // skip if not found
            }
            // Scroll to the date button and click it
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", dateButton);
            wait.until(ExpectedConditions.elementToBeClickable(dateButton)).click();
            // readable format output
            String suffix = (dayInt >= 11 && dayInt <= 13) ? "th" : switch (dayInt % 10) {
                case 1 -> "st";
                case 2 -> "nd";
                case 3 -> "rd";
                default -> "th";
            };
            selectedDateFormatted = dayInt + suffix + " " + tomorrow.format(DateTimeFormatter.ofPattern("MMM, yyyy"));
            System.out.println("Selected tomorrow's date: " +   selectedDateFormatted);
        } catch (Exception e) {
            Assert.fail(" Error selecting tomorrow's date: " + e.getMessage());
        }
    }

    public void selectSlot() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Open the time slot dropdown
            WebElement timeIcon = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//button[@data-state=\"closed\"])[3]")));
            WebElement timeIcon1 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[@class=\"w-full font-normal text-neutral-500\"]")));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", timeIcon);
            Thread.sleep(300);
            if(timeIcon.isDisplayed()) {
                timeIcon.click();
            } else {
                timeIcon1.click();
            }
            // Preferred time slots
            List<String> preferredSlots = Arrays.asList(
                    "12:00 PM - 1:00 PM",
                    "1:00 PM - 2:00 PM",
                    "2:00 PM - 3:00 PM",
                    "3:00 PM - 4:00 PM",
                    "4:00 PM - 5:00 PM",
                    "5:00 PM - 6:00 PM"
            );
            // Attempt to click on the first available slot from the list
            for (String slot : preferredSlots) {
                try {
                    String slotXpath = "//span[normalize-space(text())='" + slot + "']";
                    WebElement slotElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(slotXpath)));

                    if (slotElement.isDisplayed() && slotElement.isEnabled()) {
                        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", slotElement);
                        wait.until(ExpectedConditions.elementToBeClickable(slotElement)).click();
                        selectedSlot = slot;
                        System.out.println("Selected Slot: " + selectedSlot);
                        return;
                    }
                } catch (Exception ignored) {}
            }
            System.out.println("No preferred slots were available.");
            selectedSlot = null;
        } catch (Exception e) {
            Assert.fail(" Error selecting slot: " + e.getMessage());
        }
    }

    public void verifyBookingSummary() {
        try {
            WebElement summary = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "//div[@class='text-xs font-medium leading-6 tracking-wide text-ssBlack md:text-base select-none md:select-text']"
                    )));

            String summaryText = summary.getText().trim();
            System.out.println("Booking Summary: " + summaryText);

            if (selectedStore == null || selectedDateFormatted == null || selectedSlot == null) {
                Assert.fail("One or more values are null:\n" +
                        "selectedStore = " + selectedStore + "\n" +
                        "selectedDateFormatted = " + selectedDateFormatted + "\n" +
                        "selectedSlot = " + selectedSlot);
            }
            Assert.assertTrue(summaryText.contains(selectedStore), "Store not matched");
            Assert.assertTrue(summaryText.contains(selectedDateFormatted), "Date not matched");
            Assert.assertTrue(summaryText.contains(selectedSlot), "Slot not matched");

            System.out.println("Booking Summary Validated Successfully ");

        } catch (Exception e) {
            Assert.fail("Error verifying booking summary: " + e.getMessage());
        }
    }

    public boolean isNoOrdersMessageVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement noOrdersMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'No recent orders')]")));

            return noOrdersMessage.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void printAllProductDetailsAcrossPages() {
        if (isNoOrdersMessageVisible()) {
            System.out.println("Looks like your bag is empty. Fill it with things that make you feel stylish.");
            return;
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        int totalProducts = 0;

        try {
            List<WebElement> pageButtons = driver.findElements(By.xpath("//li[@class='cursor-pointer']"));
            int totalPages = pageButtons.size();

            for (int pageIndex = 0; pageIndex < totalPages; pageIndex++) {
                try {
                    // Scroll to pagination and click the current page
                    WebElement page = driver.findElements(By.xpath("//li[@class='cursor-pointer']")).get(pageIndex);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", page);
                    wait.until(ExpectedConditions.elementToBeClickable(page)).click();
                    Thread.sleep(1500); // wait for content to load

                    // Now fetch orders on this page
                    List<WebElement> orderBlocks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.xpath("//div[contains(@class,'border border-slate-200')]")));

                    for (WebElement block : orderBlocks) {
                        try {
                            String status = "N/A";
                            String orderId = "N/A";
                            String productName = "N/A";
                            String price = "N/A";

                            List<WebElement> orderIdElem = block.findElements(By.xpath(".//div[contains(text(),'Order No.')]"));
                            if (!orderIdElem.isEmpty()) {
                                orderId = orderIdElem.get(0).getText().replace("Order No.", "").trim();
                            }

                            String[] lines = block.getText().split("\\r?\\n");
                            for (String line : lines) {
                                if (line.toLowerCase().matches
                                        (".*\\b(processing|delivered|shipped|cancelled|pending pickup|return initiated|rto|returned|booked|out for delivery|in-transit|undelivered)\\b.*")) {
                                    status = line.trim();
                                    break;
                                }
                            }

                            List<WebElement> nameElem = block.findElements(By.xpath(".//div[contains(@class,'text-neutral-800') and not(contains(text(),'₹'))]"));
                            if (!nameElem.isEmpty()) {
                                productName = nameElem.get(0).getText().trim();
                            }

                            List<WebElement> priceElem = block.findElements(By.xpath(".//div[contains(text(),'₹')]"));
                            if (!priceElem.isEmpty()) {
                                price = priceElem.get(0).getText().trim();
                            }

                            System.out.println("Order ID: " + orderId +
                                    " | Status: " + status +
                                    " | Product: " + productName +
                                    " | Price: " + price);
                            totalProducts++;
                        } catch (Exception innerEx) {
                            System.out.println("Error parsing order block: " + innerEx.getMessage());
                        }
                    }
                } catch (Exception pageEx) {
                    System.out.println("Could not click page index " + pageIndex + ": " + pageEx.getMessage());
                }
            }
            System.out.println("Total products found across all pages: " + totalProducts);
        } catch (Exception e) {
            Assert.fail("Error while paginating and printing product details: " + e.getMessage());
        }
    }

    public void clickOnTheFilter() {
        try {
            js.executeScript("window.scrollTo(0, 0);");
            Thread.sleep(1000);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@role='combobox' and @type='button']")));
            filterButton.click();
            System.out.println("Clicked on Filter button");
        } catch (Exception e) {
            Assert.fail("Error in clicking Filter button: " + e.getMessage());
        }
    }

    public void applyFilterOption(String filterOptionText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[@role='option']//span")));

            boolean optionFound = false;
            for (WebElement option : options) {
                if (option.getText().trim().equalsIgnoreCase(filterOptionText)) {
                    option.click();
                    System.out.println("Selected filter: " + filterOptionText);
                    optionFound = true;
                    break;
                }
            }
            if (!optionFound) {
                Assert.fail("Filter option not found: " + filterOptionText);
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            Assert.fail("Error applying filter: " + e.getMessage());
        }
    }

    public void clickFlexDivByIndex(int index) {
        try {

            List<WebElement> elements = driver.findElements
                    (By.xpath("//div[@class='flex flex-col']"));

            if (index >= 0 && index < elements.size()) {
                WebElement targetElement = elements.get(index);
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", targetElement);
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", targetElement);
                System.out.println("Clicked element at index: " + index);
            } else {
                Assert.fail("Invalid index provided: " + index + " | Total available: " + elements.size());
            }
        } catch (Exception e) {
            Assert.fail("Error while clicking div at index " + index + ": " + e.getMessage());
        }
    }

    public void verifyOrderedProductSummary() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // ----------- PART 1: Product Details -----------
            List<WebElement> productBlocks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[@class='flex flex-col gap-6']")
            ));
            for (int i = 0; i < productBlocks.size(); i++) {
                WebElement block = productBlocks.get(i);
                String blockText = block.getText().trim();
                String[] lines = blockText.split("\n");

                String brand = lines.length > 0 ? lines[0].trim() : "N/A";
                String size = "N/A", color = "N/A", qty = "N/A", amount = "N/A";

                for (String line : lines) {
                    if (line.startsWith("Size:")) size = line.replace("Size:", "").trim();
                    else if (line.startsWith("Color:")) color = line.replace("Color:", "").trim();
                    else if (line.startsWith("Ordered Qty")) qty = line.replace("Ordered Qty :", "").trim();
                    else if (line.startsWith("Ordered Amount")) amount = line.replace("Ordered Amount :", "").trim();
                }
                System.out.println("\n------ Product Details " + " ------");
                System.out.println("Brand: " + brand);
                System.out.println("Size: " + size);
                System.out.println("Color: " + color);
                System.out.println("Ordered Qty: " + qty);
                System.out.println("Ordered Amount: " + amount);

                Assert.assertNotEquals(brand, "N/A", "Brand not found");
                Assert.assertNotEquals(qty, "N/A", "Ordered Qty missing");
                Assert.assertNotEquals(amount, "N/A", "Ordered Amount missing");
            }
            // ----------- PART 2: Order Summary -----------
            List<WebElement> orderBlocks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[@class='mt-5 flex flex-col gap-5 md:flex-row md:justify-between md:gap-6 lg:mt-6 lg:gap-8 xl:gap-10']")
            ));
            for (int i = 0; i < orderBlocks.size(); i++) {
                WebElement block = orderBlocks.get(i);
                String text = block.getText().trim();

                String orderId = "N/A", placedOn = "N/A", consignmentId = "N/A", consignmentStatus = "N/A";
                String[] lines = text.split("\n");

                for (int j = 0; j < lines.length; j++) {
                    String line = lines[j].trim().toLowerCase();

                    if (line.equals("order id") && j + 1 < lines.length)
                        orderId = lines[j + 1].trim();
                    else if (line.equals("order placed") && j + 1 < lines.length)
                        placedOn = lines[j + 1].trim();
                    else if (line.equals("consignment id") && j + 1 < lines.length)
                        consignmentId = lines[j + 1].trim();
                    else if (line.contains("consignment status") && j + 1 < lines.length) {
                        String mainStatus = lines[j].replace("Consignment Status", "").replace(":", "").trim();
                        consignmentStatus = mainStatus + " | " + lines[j + 1].trim();
                    }
                }
                System.out.println("\n------ Order & Consignment Details " + " ------");
                System.out.println("Order ID: " + orderId);
                System.out.println("Placed On: " + placedOn);
                System.out.println("Consignment ID: " + consignmentId);
                System.out.println("Consignment Status: " + consignmentStatus);

                Assert.assertNotEquals(orderId, "N/A", "Order ID missing");
                Assert.assertNotEquals(placedOn, "N/A", "Order Date missing");
            }
            // ----------- PART 3: Delivery Mode & Address -----------
            WebElement addressBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//div[@class='rounded border border-lightGray p-3 md:border-none'])[2]")
            ));
            String fullText = addressBlock.getText().trim();
            String[] lines = fullText.split("\n");

            String deliveryMode = "N/A", address = "N/A";

            for (int i = 0; i < lines.length; i++) {
                if (lines[i].equalsIgnoreCase("Delivery Mode") && i + 1 < lines.length) {
                    deliveryMode = lines[i + 1].trim();
                    if (i + 2 < lines.length) {
                        StringBuilder addrBuilder = new StringBuilder();
                        for (int j = i + 2; j < lines.length; j++) {
                            addrBuilder.append(lines[j].trim()).append(", ");
                        }
                        address = addrBuilder.toString().replaceAll(", $", "");
                    }
                    break;
                }
            }
            System.out.println("\n ------ Deliver Mode & Address "+ " ------");
            System.out.println("Delivery Mode: " + deliveryMode);
            System.out.println("Address: " + address);

        } catch (Exception e) {
            System.out.println("Error in Booking Summary Validation: " + e.getMessage());
            Assert.fail("Validation failed in verifyBookingSummary");
        }
    }

    public void printWalletSummaryData() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Scroll to the wallet container
            By containerLocator = By.xpath("//div[@class='relative mx-3 py-5 pb-2 md:mx-0 md:rounded-md md:border md:border-neutral-300 md:px-0 md:pb-0']");
            WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(containerLocator));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", container);
            System.out.println("----- WALLET SUMMARY -----");

            // Total Balance
            String totalBalance = container.findElement(By.xpath(".//div[contains(text(),'Total Balance')]/following-sibling::div")).getText();
            System.out.println("Total Balance: " + totalBalance);

            List<WebElement> walletTypes = container.findElements(By.xpath(".//div[contains(@class,'flex flex-col') and .//div[contains(@class,'text-neutral-800')]]"));

            for (WebElement walletType : walletTypes) {
                String label = walletType.findElement(By.xpath(".//div[1]")).getText();
                String amount = walletType.findElement(By.xpath(".//div[2]")).getText();
                System.out.println(label + ": " + amount);
            }
        } catch (Exception e) {
            Assert.fail("Failed to fetch wallet summary: " + e.getMessage());
        }
    }

    public void countGridItems() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Wait for the grid container
            By containerLocator = By.xpath("//div[contains(@class, 'grid justify-between') and contains(@class,'gap-')]");
            WebElement gridContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(containerLocator));

            List<WebElement> gridItems = gridContainer.findElements(By.xpath("./*"));
            int count = gridItems.size();

            System.out.println("Total items in the grid: " + count);
        } catch (Exception e) {
            Assert.fail("Failed to count grid items: " + e.getMessage());
        }
    }


}