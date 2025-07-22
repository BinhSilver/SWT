package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Practice Form Tests for demoqa.com")
class PracticeFormTest {
    static WebDriver driver;
    static WebDriverWait wait;

    @BeforeAll
    static void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        // Disable ads
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @Order(1)
    @DisplayName("Should submit form successfully with valid data")
    void testFormSubmissionSuccess() {
        driver.get("https://demoqa.com/automation-practice-form");
        removeAds();

        try {
            // Fill personal information
            driver.findElement(By.id("firstName")).sendKeys("John");
            driver.findElement(By.id("lastName")).sendKeys("Doe");
            driver.findElement(By.id("userEmail")).sendKeys("john.doe@example.com");
            driver.findElement(By.cssSelector("label[for='gender-radio-1']")).click();
            driver.findElement(By.id("userNumber")).sendKeys("1234567890");

            // Fill date of birth
            WebElement dateInput = driver.findElement(By.id("dateOfBirthInput"));
            dateInput.clear();
            dateInput.sendKeys("15 Aug 1990");

            // Fill subjects
            WebElement subjectsInput = driver.findElement(By.id("subjectsInput"));
            subjectsInput.sendKeys("Computer Science");
            subjectsInput.sendKeys(org.openqa.selenium.Keys.ENTER);

            // Select hobbies
            try {
                // Wait and scroll to hobby checkbox
                WebElement sportsHobby = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("label[for='hobbies-checkbox-1']")));
                WebElement readingHobby = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("label[for='hobbies-checkbox-2']")));

                // Scroll to element
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sportsHobby);
                // Wait for element to be clickable after scroll
                wait.until(ExpectedConditions.elementToBeClickable(sportsHobby));

                // Try clicking with Selenium first
                try {
                    sportsHobby.click();
                    readingHobby.click();
                } catch (Exception e) {
                    // If normal click fails, use JavaScript
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", sportsHobby);
                    js.executeScript("arguments[0].click();", readingHobby);
                }
            } catch (Exception e) {
                System.out.println("Error clicking hobbies: " + e.getMessage());
                throw e;
            }

            // Fill address
            driver.findElement(By.id("currentAddress")).sendKeys("123 Test Street");

            // Submit form
            WebElement submitButton = driver.findElement(By.id("submit"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

            // Verify submission
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
            assertTrue(modal.isDisplayed(), "Form should be submitted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Form submission failed due to an exception: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    @DisplayName("Should show validation errors with empty form")
    void testFormSubmissionWithEmptyData() {
        driver.get("https://demoqa.com/automation-practice-form");
        removeAds();

        // Submit empty form
        WebElement submitButton = driver.findElement(By.id("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        // Verify required field validations
        assertTrue(driver.findElements(By.cssSelector(".was-validated")).size() > 0,
                  "Form should show validation errors");
    }

    @ParameterizedTest
    @Order(3)
    @CsvSource({
        "John,Doe,john.doe@example.com,Male,1234567890,15 Aug 1990,success",
        ",,,,,15 Aug 1990,error",
        "Invalid,,invalid-email,Other,123,01 Jan 2000,error"
    })
    @DisplayName("Multiple form submissions using different data")
    void testFormWithMultipleData(String firstName, String lastName, String email,
                                String gender, String mobile, String dob, String expectedResult) {
        driver.get("https://demoqa.com/automation-practice-form");
        removeAds();

        fillAndSubmitForm(firstName, lastName, email, gender, mobile);
        verifyFormSubmissionResult(expectedResult);
    }

    private void removeAds() {
        // Remove ads and iframes that might interfere with the test
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("const elements = document.getElementsByTagName('iframe'); while (elements.length > 0) elements[0].remove();");
        js.executeScript("const adElements = document.getElementsByClassName('ad-banner'); while (adElements.length > 0) adElements[0].remove();");
    }

    private void fillAndSubmitForm(String firstName, String lastName, String email,
                                String gender, String mobile) {
        // Fill form based on parameters using explicit waits
        if (firstName != null) wait.until(ExpectedConditions.elementToBeClickable(By.id("firstName"))).sendKeys(firstName);
        if (lastName != null) wait.until(ExpectedConditions.elementToBeClickable(By.id("lastName"))).sendKeys(lastName);
        if (email != null) wait.until(ExpectedConditions.elementToBeClickable(By.id("userEmail"))).sendKeys(email);
        if (gender != null) {
            String genderSelector = String.format("label[for='gender-radio-%s']",
                gender.equals("Male") ? "1" : gender.equals("Female") ? "2" : "3");
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(genderSelector))).click();
        }
        if (mobile != null) wait.until(ExpectedConditions.elementToBeClickable(By.id("userNumber"))).sendKeys(mobile);

        // Submit form
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
    }

    private void verifyFormSubmissionResult(String expectedResult) {
        if (expectedResult.equals("success")) {
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
            assertTrue(modal.isDisplayed(), "Form should be submitted successfully");
        } else {
            WebElement form = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form")));
            assertTrue(form.getAttribute("class").contains("was-validated"),
                      "Form should show validation errors");
        }
    }

    @ParameterizedTest
    @Order(4)
    @CsvFileSource(resources = "/form-data.csv", numLinesToSkip = 1)
    @DisplayName("Form submissions using data from CSV file")
    void testFormWithCSV(String firstName, String lastName, String email,
                        String gender, String mobile, String dob, String expectedResult) {
        driver.get("https://demoqa.com/automation-practice-form");
        removeAds();

        try {
            // Clean up input data
            firstName = (firstName != null && !firstName.equals("\"\"")) ? firstName.trim() : "";
            lastName = (lastName != null && !lastName.equals("\"\"")) ? lastName.trim() : "";
            email = (email != null && !email.equals("\"\"")) ? email.trim() : "";
            gender = (gender != null && !gender.equals("\"\"")) ? gender.trim() : "";
            mobile = (mobile != null && !mobile.equals("\"\"")) ? mobile.trim() : "";
            dob = (dob != null && !dob.equals("\"\"")) ? dob.trim() : "";

            // Fill basic form fields
            if (!firstName.isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("firstName"))).sendKeys(firstName);
            }
            if (!lastName.isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("lastName"))).sendKeys(lastName);
            }
            if (!email.isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("userEmail"))).sendKeys(email);
            }
            if (!mobile.isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("userNumber"))).sendKeys(mobile);
            }

            // Handle gender selection
            if (!gender.isEmpty()) {
                String genderSelector = String.format("label[for='gender-radio-%s']",
                    gender.equals("Male") ? "1" : gender.equals("Female") ? "2" : "3");
                WebElement genderElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(genderSelector)));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", genderElement);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", genderElement);
            }

            // Handle date of birth
            if (!dob.isEmpty()) {
                WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("dateOfBirthInput")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateInput);
                dateInput.click();
                dateInput.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Clear existing text
                dateInput.sendKeys(dob);
                dateInput.sendKeys(Keys.ENTER);
            }

            // Submit form
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
            wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

            // Verify results
            if (expectedResult.equals("success")) {
                WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
                assertTrue(modal.isDisplayed(), "Form should be submitted successfully");

                // Verify submitted data in modal
                String modalText = modal.getText();
                if (!firstName.isEmpty()) assertTrue(modalText.contains(firstName), "First name not found in result");
                if (!lastName.isEmpty()) assertTrue(modalText.contains(lastName), "Last name not found in result");
                if (!email.isEmpty()) assertTrue(modalText.contains(email), "Email not found in result");
                if (!mobile.isEmpty()) assertTrue(modalText.contains(mobile), "Mobile not found in result");
                if (!gender.isEmpty()) assertTrue(modalText.contains(gender), "Gender not found in result");
            } else {
                // For error cases, verify form shows validation state
                WebElement form = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("form")));
                assertTrue(form.getAttribute("class").contains("was-validated") ||
                         !driver.findElements(By.cssSelector(".is-invalid")).isEmpty(),
                         "Form should show validation errors");
            }
        } catch (Exception e) {
            System.out.println("Test failed for data: " + firstName + ", " + lastName + ", " + email + ", " +
                             gender + ", " + mobile + ", " + dob + ", " + expectedResult);
            throw e;
        }
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

