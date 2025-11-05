package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;


public class AccountPage extends BasePage {

    public final JavascriptExecutor js = (JavascriptExecutor) driver;
    private String selectedStore;
    private String selectedDateFormatted;
    private String selectedSlot;

    // ---------- Locators ----------
    private final By profile = By.xpath("//div[contains(text(),'My Profile')]");
    private final By name = By.xpath("//input[@id='Name']");
    private final By mobNo = By.xpath("//input[@id='Mobile No.']");
    private final By pinCode = By.xpath("//input[@id='Pin Code']");
    private final By address = By.xpath("//textarea[@id='Address']");
    private final By addressType = By.xpath("(//button[@type='button'])[3]");
    private final By addressType2 = By.xpath("(//div[@class=\"flex size-[18px] h-full items-center justify-center\"])[4]");
    private final By newAddAddress = By.xpath("//p[contains(text(),'ADD ADDRESS')]");
    private final By deleteAddress = By.xpath("(//div[contains(text(),'Delete')])[2]");
    private final By confirmDelete = By.xpath("//p[contains(text(),'Confirm')]");
    private final By bookAPersonalShopper = By.xpath("//div[contains(text(),'Book a Personal Shopper')]");
    private final By bookAnAppointment = By.xpath("//p[contains(text(),'BOOK AN APPOINTMENT')]");
    private final By bookAService = By.xpath("//p[contains(text(), 'book a service')]");
    private final By proceedToBook = By.xpath("//p[contains(text(), 'Proceed To Book')]");
    private final By myOrders = By.xpath("//div[contains(text(), 'My Orders')]");
    private final By myWallet = By.xpath("//div[contains(text(), 'My Wallet')]");
    private final By egv = By.xpath("//div[contains(text(), 'Gift Card/EGV')]");
    private final By comElement = By.xpath("//div[@class=\"md:!leading-4, cursor-pointer px-5 py-2.5 text-sm hover:bg-neutral-100 hover:font-medium bg-white font-normal leading-4 text-ssBlackHeavy select-none md:select-text\"]");
    private final By editOption = By.xpath("//div[contains(text(),'Edit')]");
    private final By editOption2 = By.xpath("(//div[contains(text(),'Edit')])[2]");
    private final By gender = By.xpath("(//button[@type='button'])");
    private final By updateChangesBtn = By.xpath("//p[translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')='UPDATE CHANGES']");
    private final By addAddressBtn = By.xpath("//img[@alt=\"add_address\"]");
    private final By dropDownBtn = By.xpath("(//button[contains(@role, 'combobox') and @type='button'])[1]");
    private final By cityOptions = By.xpath("//div[@role='option']//span");
    private final By allOptionDivs = By.xpath("//div[@role='option']");
    private final By insideStoreEle = By.xpath("//label[@for='-1']//div");
    private final By selectStore = By.xpath("(//button[contains(@role, 'radio')])[1]");
    private final By calendarIcon = By.xpath("//img[@alt='calendar icon']");
    private final By timeIcon = By.xpath("(//button[@data-state='closed'])[3] | //span[@class='w-full font-normal text-neutral-500']");
    private final By summary = By.xpath("//div[@class='text-xs font-medium leading-6 tracking-wide text-ssBlack md:text-base select-none md:select-text']");
    private final By noOrderMessage = By.xpath("//div[contains(text(), 'No recent orders')]");
    private final By paginationBtn = By.xpath("//li[@class='cursor-pointer']");
    private final By orderBlocks1 = By.xpath("//div[contains(@class,'border border-slate-200')]");
    private final By orderID = By.xpath(".//div[contains(text(),'Order No.')]");
    private final By productName1 = By.xpath(".//div[contains(@class,'text-neutral-800') and not(contains(text(),'₹'))]");
    private final By price1 = By.xpath(".//div[contains(text(),'₹')]");
    private final By filterBtn = By.xpath("//button[@role='combobox' and @type='button']");
    private final By option1 = By.xpath("//div[@role='option']//span");
    private final By ele = By.xpath("//div[@class='flex flex-col']");
    private final By productBlock = By.xpath("//div[@class='flex flex-col gap-6']");
    private final By orderBlock = By.xpath("//div[@class='mt-5 flex flex-col gap-5 md:flex-row md:justify-between md:gap-6 lg:mt-6 lg:gap-8 xl:gap-10']");
    private final By addressBlock = By.xpath("(//div[@class='rounded border border-lightGray p-3 md:border-none'])[2]");
    private final By walletContainer = By.xpath("//div[@class='relative mx-3 py-5 pb-2 md:mx-0 md:rounded-md md:border md:border-neutral-300 md:px-0 md:pb-0']");
    private final By typesWallet = By.xpath(".//div[contains(@class,'flex flex-col') and .//div[contains(@class,'text-neutral-800')]]");
    private final By walletTotal = By.xpath(".//div[contains(text(),'Total Balance')]/following-sibling::div");
    private final By labelWallet = By.xpath(".//div[1]");
    private final By amountWallet = By.xpath(".//div[2]");
    private final By gridContainerLocator = By.xpath("//div[contains(@class, 'grid justify-between') and contains(@class,'gap-')]");
    private final By firstCitizenClub = By.xpath("(//div[contains(text(),'First Citizen Club')])[2]");
    private final By silver = By.xpath("//div[contains(text(), 'SILVER EDGE')]");
    private final By golden = By.xpath("//div[contains(text(), 'GOLDEN GLOW')]");
    private final By platinum = By.xpath("//div[contains(text(), 'PLATINUM AURA')]");
    private final By black = By.xpath("//div[contains(text(), 'BLACK TIER')]");
    private final By membership = By.xpath("//div[contains(text(), 'Membership Details & Benefits')]");


    public AccountPage(WebDriver driver) {
        super(driver);
    }

    // ---------- Common Actions ----------
    public void clicksOnMyProfile() {
        waitForOverlayToDisappear();
        clickOnElement(profile);
    }

    public void moveToProfile() {
        moveToElement(profile);
    }

    public void clickOnAddressType() {
        clickJsUsingBy(addressType);
    }

    public void clickOnAddressType2() {
        clickJsUsingBy(addressType2);
    }

    public String getEnteredName() {
        waitForOverlayToDisappear();
        return driver.findElement(name).getAttribute("value");
    }

    public void enterTheName(String str) {
        waitForOverlayToDisappear();
        enterTextOnElement(name, str);
    }

    public void enterTheMobile(String no) {
        enterTextOnElement(mobNo, no);
    }

    public void enterThePinCode(String pin) {
        enterTextOnElement(pinCode, pin);
    }

    public void enterTheAddress(String adr) {
        enterTextOnElement(address, adr);
    }

    public void clicksOnNewAddress() {
        clickOnElement(newAddAddress);
    }

    public void clicksOnBookAPersonal() {
        clickOnElement(bookAPersonalShopper);
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

    public void clicksOnFirstCitizenClub() {
        clickOnElement(firstCitizenClub);
    }

    public void clicksOnGiftCard() {
        waitForOverlayToDisappear();
        clickOnElement(egv);
    }

    public List<String> getAllComponentList() {
        waitForOverlayToDisappear();
        moveToProfile();
        List<WebElement> componentElements = driver.findElements(comElement);

        List<String> componentTexts = new ArrayList<>();
        for (WebElement element : componentElements) {
            String text = element.getText().trim();
            if (!text.isEmpty()) {
                componentTexts.add(text);
            }
        }
        return componentTexts;
    }

    public void clickOnEdit() {
        waitForOverlayToDisappear();
        clickOnElement(editOption);
    }

    public void clickOnEdit2() {
        waitForOverlayToDisappear();
        moveToElement(editOption2);
        clickOnElement(editOption2);
    }

    public void toggleGenderSelection() {
        try {
            List<WebElement> genders = driver.findElements(gender);

            if (genders.size() >= 2) {
                WebElement maleGender = genders.get(0);
                WebElement femaleGender = genders.get(1);
                boolean isMaleSelected = "true".equals(maleGender.getAttribute("aria-checked"));

                if (isMaleSelected) {
                    femaleGender.click();
                } else {
                    maleGender.click();
                }
            }
        } catch (Exception e) {
        }
    }

    public void clickOnTheUpdateChanges() {

        try {
            if (isElementPresent(updateChangesBtn)) {
                WebElement update = driver.findElement(updateChangesBtn);
                if (update.isDisplayed() && update.isEnabled()) {
                    update.click();
                }
            }
        } catch (Exception e) {
        }
    }

    public void clickOnTheAddAddress() {

        waitForOverlayToDisappear();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement freshButton = wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.elementToBeClickable(addAddressBtn)
        ));
        freshButton.click();
    }

    public String selectCity(String cityIdentifier) {
        waitForOverlayToDisappear();
        String selectedCity = null;

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropDownBtn));
            dropdown.click();
            js.executeScript("arguments[0].click();", dropdown);

            wait.until(ExpectedConditions.visibilityOfElementLocated(cityOptions));

            // Freeze dropdown
            js.executeScript(
                    "document.querySelector('[role=\"listbox\"]').addEventListener('mousedown', function(e) { e.stopPropagation(); }, true);");

            // Fetch and collect cities
            List<WebElement> cityElements = driver.findElements(cityOptions);
            List<String> allCities = new ArrayList<>();

            for (WebElement city : cityElements) {
                try {
                    String cityText = city.getText().trim();
                    if (!cityText.isEmpty() && !allCities.contains(cityText)) {
                        allCities.add(cityText);
                    }
                } catch (StaleElementReferenceException e) {}
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
            List<WebElement> allOptions = driver.findElements(allOptionDivs);

            for (WebElement option : allOptions) {
                try {
                    String cityText = option.getText().trim();
                    if (cityText.equalsIgnoreCase(cityIdentifier)) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                        selectedCity = cityIdentifier;
                        break;
                    }
                } catch (StaleElementReferenceException e) {}
            }

            if (selectedCity == null) {
                throw new RuntimeException("Could not click on selected city.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception in selectCity(): " + e.getMessage(), e);
        } finally {
            driver.switchTo().defaultContent();
        }
        return selectedCity;
    }

    public void fetchAllStoreDetails(int index) {
        try {
            List<WebElement> elementsInside = driver.findElements(insideStoreEle);

            if (!elementsInside.isEmpty()) {
                selectedStore = elementsInside.get(index).getText().trim();
            }
            wait.until(ExpectedConditions.elementToBeClickable(selectStore)).click();
        } catch (Exception ignored) {
        }
    }

    public void selectTomorrowDate() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // :one: Open the calendar
            WebElement calendarIconElement = wait.until(ExpectedConditions.elementToBeClickable(calendarIcon));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", calendarIconElement);
            js.executeScript("arguments[0].click();", calendarIconElement);
            System.out.println(":white_check_mark: Calendar opened");
            // :two: Compute tomorrow’s date
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.plusDays(1);
            // :three: If tomorrow is in the next month, go to next month
            if (tomorrow.getMonthValue() != today.getMonthValue()) {
                System.out.println(":information_source: Tomorrow is in the next month. Clicking next...");
                By nextMonthArrow = By.xpath("//button[contains(@aria-label,'Next') or contains(@class,'rdp-nav_button')]");
                WebElement nextMonthButton = wait.until(ExpectedConditions.elementToBeClickable(nextMonthArrow));
                js.executeScript("arguments[0].click();", nextMonthButton);
                // Wait for header to change to next month
                String nextMonthFullName = tomorrow.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                wait.until(ExpectedConditions.textToBePresentInElementLocated(
                        By.xpath("//h2[contains(@class,'rdp-caption_label')]"), nextMonthFullName
                ));
                System.out.println(":white_check_mark: Switched to " + nextMonthFullName);
            }
            // :four: Use your specific working XPath pattern
            String dayXpath = "//*[contains(@class, 'rdp-day')]" + "[not(contains(@class, 'rdp-day_outside')) and not(@aria-disabled='true') and not(@disabled)]" + "[normalize-space()='" + tomorrow.getDayOfMonth() + "']";
            System.out.println(":mag: Using XPath: " + dayXpath);
            WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dayXpath)));
            // :five: Scroll into view and click safely
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", dateElement);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(dateElement)).click();
            } catch (Exception e) {
                System.out.println(":warning: Selenium click failed. Trying JS click...");
                js.executeScript("arguments[0].click();", dateElement);
            }
            System.out.println(":white_check_mark: Clicked date: " + tomorrow.getDayOfMonth());
            // :six: Format the selected date nicely
            int dayInt = tomorrow.getDayOfMonth();
            String suffix = (dayInt >= 11 && dayInt <= 13) ? "th" : switch (dayInt % 10) {
                case 1 -> "st";
                case 2 -> "nd";
                case 3 -> "rd";
                default -> "th";
            };
            selectedDateFormatted = dayInt + suffix + " " + tomorrow.format(DateTimeFormatter.ofPattern("MMM, yyyy"));
            System.out.println(":date: Selected date: " + selectedDateFormatted);
        } catch (Exception e) {
            System.out.println(":x: Error selecting tomorrow's date: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error selecting tomorrow's date: " + e.getMessage(), e);
        }
    }

    public void selectSlot() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Open the time slot dropdown
            WebElement timeDrop = wait.until(ExpectedConditions.elementToBeClickable(timeIcon));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", timeDrop);
            Thread.sleep(300);
            timeDrop.click();

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
                        return;
                    }
                } catch (Exception ignored) {
                }
            }
            selectedSlot = null;
        } catch (Exception e) {
            selectedSlot = null;
        }
    }

    public void verifyBookingSummary() {
        try {
            WebElement summaryT = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(summary));

            String summaryText = summaryT.getText().trim();

            if (selectedStore == null || selectedDateFormatted == null || selectedSlot == null) {
                throw new RuntimeException("One or more values are null:\n" +
                        "selectedStore = " + selectedStore + "\n" +
                        "selectedDateFormatted = " + selectedDateFormatted + "\n" +
                        "selectedSlot = " + selectedSlot);
            }

            if (!summaryText.contains(selectedStore)) {
                throw new RuntimeException("Store not matched. Expected: " + selectedStore + " | Actual Summary: " + summaryText);
            }
            if (!summaryText.contains(selectedSlot)) {
                throw new RuntimeException("Slot not matched. Expected: " + selectedSlot + " | Actual Summary: " + summaryText);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error verifying booking summary: " + e.getMessage(), e);
        }
    }

    public boolean isNoOrdersMessageVisible() {
        try {
            WebElement noOrdersMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(noOrderMessage));
            return noOrdersMessage.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void printOrderDetails(WebElement block) {
        String orderId = "N/A", status = "N/A", productName = "N/A", price = "N/A";

        List<WebElement> orderIdElem = block.findElements(orderID);
        if (!orderIdElem.isEmpty()) {
            orderId = orderIdElem.get(0).getText().replace("Order No.", "").trim();
        }

        String[] lines = block.getText().split("\\r?\\n");
        for (String line : lines) {
            if (line.toLowerCase().matches(".*\\b(processing|delivered|shipped|cancelled|pending pickup|return initiated|rto|returned|booked|out for delivery|in-transit|undelivered)\\b.*")) {
                status = line.trim();
                break;
            }
        }

        List<WebElement> nameElem = block.findElements(productName1);
        if (!nameElem.isEmpty()) productName = nameElem.get(0).getText().trim();

        List<WebElement> priceElem = block.findElements(price1);
        if (!priceElem.isEmpty()) price = priceElem.get(0).getText().trim();

    }

    public void printAllProductDetailsAcrossPages() {
        if (isNoOrdersMessageVisible()) {
            return;
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        int totalProducts = 0;

        try {
            List<WebElement> pageButtons = driver.findElements(paginationBtn);
            int totalPages = pageButtons.size();

            for (int pageIndex = 0; pageIndex < totalPages; pageIndex++) {
                try {
                    WebElement page = driver.findElements(paginationBtn).get(pageIndex);
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", page);
                    wait.until(ExpectedConditions.elementToBeClickable(page)).click();

                    // Wait for orders to load
                    List<WebElement> orderBlocks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(orderBlocks1));

                    for (WebElement block : orderBlocks) {
                        try {
                            printOrderDetails(block);
                            totalProducts++;
                        } catch (Exception innerEx) {
                        }
                    }
                } catch (Exception pageEx) {
                }
            }
        } catch (Exception e) {
        }
    }

    public void clickOnTheFilter() {
        try {
            js.executeScript("window.scrollTo(0, 0);");
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(filterBtn)).click();
        } catch (Exception e) {
        }
    }

    public void applyFilterOption(String filterOptionText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(option1));

            for (WebElement option : options) {
                if (option.getText().trim().equalsIgnoreCase(filterOptionText)) {
                    Thread.sleep(1000);
                    option.click();
                    return;
                }
            }
        } catch (Exception e) {
        }
    }

    public void clickFlexDivByIndex(int index) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ele));

            if (index >= 0 && index < elements.size()) {
                WebElement targetElement = elements.get(index);
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", targetElement);
                Thread.sleep(500);
                js.executeScript("arguments[0].click();", targetElement);
            } else {
            }
        } catch (Exception e) {
        }
    }

    public List<Map<String, String>> verifyOrderedProductSummary() {
        List<Map<String, String>> summaryList = new ArrayList<>();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // ----------- PART 1: Product Details -----------
            List<WebElement> productBlocks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productBlock));
            for (int i = 0; i < productBlocks.size(); i++) {
                WebElement block = productBlocks.get(i);
                String blockText = block.getText().trim();
                String[] lines = blockText.split("\n");

                Map<String, String> productMap = new HashMap<>();
                productMap.put("Brand", lines.length > 0 ? lines[0].trim() : "N/A");
                productMap.put("Size", "N/A");
                productMap.put("Color", "N/A");
                productMap.put("OrderedQty", "N/A");
                productMap.put("OrderedAmount", "N/A");

                for (String line : lines) {
                    if (line.startsWith("Size:")) productMap.put("Size", line.replace("Size:", "").trim());
                    else if (line.startsWith("Color:")) productMap.put("Color", line.replace("Color:", "").trim());
                    else if (line.startsWith("Ordered Qty"))
                        productMap.put("OrderedQty", line.replace("Ordered Qty :", "").trim());
                    else if (line.startsWith("Ordered Amount"))
                        productMap.put("OrderedAmount", line.replace("Ordered Amount :", "").trim());
                }
                summaryList.add(productMap);
            }

            // ----------- PART 2: Order Summary -----------
            List<WebElement> orderBlocks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(orderBlock));
            for (WebElement block : orderBlocks) {
                String text = block.getText().trim();
                String[] lines = text.split("\n");

                Map<String, String> orderMap = new HashMap<>();
                orderMap.put("OrderID", "N/A");
                orderMap.put("PlacedOn", "N/A");
                orderMap.put("ConsignmentID", "N/A");
                orderMap.put("ConsignmentStatus", "N/A");

                for (int j = 0; j < lines.length; j++) {
                    String line = lines[j].trim().toLowerCase();

                    if (line.equals("order id") && j + 1 < lines.length)
                        orderMap.put("OrderID", lines[j + 1].trim());
                    else if (line.equals("order placed") && j + 1 < lines.length)
                        orderMap.put("PlacedOn", lines[j + 1].trim());
                    else if (line.equals("consignment id") && j + 1 < lines.length)
                        orderMap.put("ConsignmentID", lines[j + 1].trim());
                    else if (line.contains("consignment status") && j + 1 < lines.length) {
                        String mainStatus = lines[j].replace("Consignment Status", "").replace(":", "").trim();
                        orderMap.put("ConsignmentStatus", mainStatus + " | " + lines[j + 1].trim());
                    }
                }
                summaryList.add(orderMap);
            }

            // ----------- PART 3: Delivery Mode & Address -----------
            WebElement addressBlocks = wait.until(ExpectedConditions.visibilityOfElementLocated(addressBlock));
            String fullText = addressBlocks.getText().trim();
            String[] lines = fullText.split("\n");

            Map<String, String> addressMap = new HashMap<>();
            addressMap.put("DeliveryMode", "N/A");
            addressMap.put("Address", "N/A");

            for (int i = 0; i < lines.length; i++) {
                if (lines[i].equalsIgnoreCase("Delivery Mode") && i + 1 < lines.length) {
                    addressMap.put("DeliveryMode", lines[i + 1].trim());
                    if (i + 2 < lines.length) {
                        StringBuilder addrBuilder = new StringBuilder();
                        for (int j = i + 2; j < lines.length; j++) {
                            addrBuilder.append(lines[j].trim()).append(", ");
                        }
                        addressMap.put("Address", addrBuilder.toString().replaceAll(", $", ""));
                    }
                    break;
                }
            }
            summaryList.add(addressMap);
        } catch (Exception e) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("Error", "Validation failed: " + e.getMessage());
            summaryList.add(errorMap);
        }
        return summaryList;
    }

    public Map<String, String> printWalletSummaryData() {
        Map<String, String> walletSummary = new LinkedHashMap<>();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Scroll to the wallet container
            WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(walletContainer));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", container);

            // Total Balance
            String totalBalance = container.findElement(walletTotal).getText();
            walletSummary.put("Total Balance", totalBalance);

            List<WebElement> walletTypes = container.findElements(typesWallet);

            for (WebElement walletType : walletTypes) {
                String label = walletType.findElement(labelWallet).getText();
                String amount = walletType.findElement(amountWallet).getText();
                walletSummary.put(label, amount);
            }
        } catch (Exception e) {}
        return walletSummary;
    }

    public int countGridItems() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait for the grid container
        WebElement gridContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(gridContainerLocator));

        List<WebElement> gridItems = gridContainer.findElements(By.xpath("./*"));
        return gridItems.size();
    }

    public boolean validateFirstCitizenClubSections(List<String> expectedSections) {
        List<By> expectedComponents = Arrays.asList(
                silver,
                golden,
                platinum,
                black,
                membership
        );
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean allFound = true;
        for (By locator : expectedComponents) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            } catch (TimeoutException e) {
                allFound = false;
            }
        }
        return allFound;
    }


}