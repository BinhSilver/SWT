package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class PracticeFormPage extends BasePage {
    // Form fields locators
    private final By firstName = By.id("firstName");
    private final By lastName = By.id("lastName");
    private final By userEmail = By.id("userEmail");
    private final By genderMale = By.cssSelector("label[for='gender-radio-1']");
    private final By mobile = By.id("userNumber");
    private final By dateOfBirth = By.id("dateOfBirthInput");
    private final By subjects = By.id("subjectsInput");
    private final By hobbySports = By.cssSelector("label[for='hobbies-checkbox-1']");
    private final By hobbyReading = By.cssSelector("label[for='hobbies-checkbox-2']");
    private final By hobbyMusic = By.cssSelector("label[for='hobbies-checkbox-3']");
    private final By uploadPicture = By.id("uploadPicture");
    private final By currentAddress = By.id("currentAddress");
    private final By state = By.id("react-select-3-input");
    private final By city = By.id("react-select-4-input");
    private final By submitBtn = By.id("submit");

    // Validation elements
    private final By modalDialog = By.className("modal-content");
    private final By modalTitle = By.className("modal-title");
    private final By requiredFields = By.cssSelector(".form-control:required");
    private final By fieldValidationErrors = By.cssSelector(".form-control.is-invalid");

    public PracticeFormPage(WebDriver driver) {
        super(driver);
    }

    public void navigate() {
        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
        // Wait for the form to be fully loaded
        waitForVisibility(firstName);
    }

    public void fillForm() {
        // Personal Information
        type(firstName, "John");
        type(lastName, "Doe");
        type(userEmail, "john.doe@example.com");
        click(genderMale);
        type(mobile, "1234567890");

        // Date of Birth
        WebElement dateElement = waitForVisibility(dateOfBirth);
        dateElement.sendKeys(Keys.CONTROL + "a");
        dateElement.sendKeys("15 Aug 1990");
        dateElement.sendKeys(Keys.ENTER);

        // Subjects
        WebElement subjectsInput = waitForVisibility(subjects);
        typeAndEnter(subjectsInput, "Computer Science");
        typeAndEnter(subjectsInput, "English");

        // Hobbies
        click(hobbySports);
        click(hobbyReading);

        // Picture Upload
        String picturePath = System.getProperty("user.dir") + "/src/test/resources/mizu.png";
        if (new java.io.File(picturePath).exists()) {
            type(uploadPicture, picturePath);
        }

        // Address Information
        type(currentAddress, "123 Test Street");

        // State and City
        typeAndEnter(waitForVisibility(state), "NCR");
        typeAndEnter(waitForVisibility(city), "Delhi");

        submitForm();
    }

    public void submitEmptyForm() {
        click(submitBtn);
    }

    private void submitForm() {
        scrollTo(submitBtn);
        try {
            click(submitBtn);
        } catch (ElementClickInterceptedException e) {
            // If normal click fails, try JavaScript click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(submitBtn));
        }
    }

    private void typeAndEnter(WebElement element, String text) {
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
    }

    public boolean isFormSubmitted() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(modalDialog)).isDisplayed() &&
                   wait.until(ExpectedConditions.visibilityOfElementLocated(modalTitle)).getText().contains("Thanks for submitting");
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean hasValidationErrors() {
        try {
            // Check if any required fields are empty
            List<WebElement> required = driver.findElements(requiredFields);
            List<WebElement> invalid = driver.findElements(fieldValidationErrors);
            return !required.isEmpty() || !invalid.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}