package com.webtest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Helpers {
    public static final String URL_INDEX = "https://demo.automationtesting.in/Index.html";
    public static final String URL_REGISTER = "https://demo.automationtesting.in/Register.html";
    WebDriver driver;

    public Helpers(WebDriver dr) {
        this.driver = dr;
    }

    public void fillInput(By selector, String value) {
        WebElement element = driver.findElement(selector);
        element.sendKeys(value);
    }

    public void selectRadioButton(By selector) {
        WebElement radioButton = driver.findElement(selector);
        if (!radioButton.isSelected()) {
            radioButton.click();
        }
    }

    public void clearRadioButton(By locator) {
        WebElement radioButton = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].checked = false;", radioButton);
    }

    public void clickElement(By selector) {
        WebElement click = driver.findElement(selector);
        if (click.isDisplayed()) {
            click.click();
        }
    }

    public void selectElement(By selector, String value) {
        WebElement dropdown = driver.findElement(selector);
        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
    }

    public void uploadImage(String path, By selector) {
        String filePath = Paths.get(path).toAbsolutePath().toString();
        WebElement uploadElement = driver.findElement(selector);
        uploadElement.sendKeys(filePath);
    }

    public void selectMultiOptions(By containerSelector, String... optionsToSelect) {
        WebElement container = driver.findElement(containerSelector);
        container.click();

        List<WebElement> options = driver.findElements(By.cssSelector(".ui-autocomplete li a"));
        for (WebElement option : options) {
            String optionText = option.getText();
            for (String optionToSelect : optionsToSelect) {
                if (optionText.equals(optionToSelect)) {
                    option.click();
                }
            }
        }
    }

    public void selectAllCheckboxes(By selector) {
        List<WebElement> checkboxes = driver.findElements(selector);
        for (WebElement checkbox : checkboxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    public void assertFieldIsEmpty(By locator, String errorMessage) {
        WebElement element = driver.findElement(locator);
        String value = element.getAttribute("value");
        assertTrue(value == null || value.isEmpty(), errorMessage);
    }

    public void assertFieldContainsText(By locator, String expectedText, String errorMessage) {
        WebElement element = driver.findElement(locator);
        String actualText = element.getText();
        assertTrue(actualText.contains(expectedText), errorMessage);
    }

    public void assertUncheckAllCheckboxes(By selector) {
        List<WebElement> checkboxes = driver.findElements(selector);
        for (WebElement checkbox : checkboxes) {
            if (checkbox.isSelected()) {
                checkbox.click();
                assertFalse(checkbox.isSelected(), "Checkbox está selecionado.");
            }
        }
    }

    public void assertMultiselectFieldIsEmpty(By selector) {
        WebElement container = driver.findElement(selector);
        String divContent = container.getText().trim();
        assertTrue(divContent.isEmpty(), "A div contém conteúdo");
    }

    public void resetForm() {
        WebElement refreshButton = driver.findElement(By.cssSelector("button[value='Refresh']"));
        refreshButton.click();
    }

    public void fillForm() {
        driver.get(URL_REGISTER);

        String firstName = "Silva";
        String lastName = "Luís";
        String address = "Avenida";
        String email = "silva@mail.com";
        String phone = "3293293293";
        String pass = "Teste123";

        fillInput(By.cssSelector(Selectors.INPUT_FIRST_NAME), firstName);
        fillInput(By.cssSelector(Selectors.INPUT_LAST_NAME), lastName);
        fillInput(By.cssSelector(Selectors.INPUT_ADDRESS), address);
        fillInput(By.cssSelector(Selectors.INPUT_EMAIL), email);
        fillInput(By.cssSelector(Selectors.INPUT_PHONE), phone);

        selectRadioButton(By.cssSelector(Selectors.RADIO_GENDER_MALE));

        selectAllCheckboxes(By.cssSelector(Selectors.CHECKBOXES));

        selectMultiOptions(By.id(Selectors.MULTISELECT_LANGUAGES), "English", "Portuguese");

        selectElement(By.id(Selectors.SELECT_SKILLS), "Java");

        selectElement(By.id(Selectors.SELECT_COUNTRY), "United States of America");

        selectElement(By.id(Selectors.SELECT_YEARBOX), "1995");
        selectElement(By.cssSelector(Selectors.SELECT_MONTH), "September");
        selectElement(By.id(Selectors.SELECT_DAYBOX), "23");

        fillInput(By.id(Selectors.INPUT_FIRST_PASSWORD), pass);
        fillInput(By.id(Selectors.INPUT_SECOND_PASSWORD), pass);

        uploadImage("src/test/resources/test-img.jpg", By.id(Selectors.INPUT_IMAGE));
    }

    public void validatesIfFormCamposWereCleaned() {
        assertFieldContainsText(By.id(Selectors.SELECT_SKILLS), "Select Skills", "O campo Skills não foi resetado");

        assertFieldIsEmpty(By.cssSelector(Selectors.INPUT_FIRST_NAME), "O campo First Name não foi resetado");
        assertFieldIsEmpty(By.cssSelector(Selectors.INPUT_LAST_NAME), "O campo Last Name não foi resetado");
        assertFieldIsEmpty(By.cssSelector(Selectors.INPUT_ADDRESS), "O campo Address não foi resetado");
        assertFieldIsEmpty(By.cssSelector(Selectors.INPUT_EMAIL), "O campo Email não foi resetado");
        assertFieldIsEmpty(By.cssSelector(Selectors.INPUT_PHONE), "O campo Tel não foi resetado");

        clearRadioButton(By.cssSelector(Selectors.RADIO_GENDER_MALE));

        assertUncheckAllCheckboxes(By.cssSelector(Selectors.CHECKBOXES));

        assertMultiselectFieldIsEmpty(By.id(Selectors.MULTISELECT_LANGUAGES));

        assertFieldContainsText(By.id(Selectors.SELECT_COUNTRY), "", "O campo Country não foi resetado");

        assertFieldContainsText(By.id(Selectors.SELECT_YEARBOX), "", "O campo Year não foi resetado");
        assertFieldContainsText(By.cssSelector(Selectors.SELECT_MONTH), "", "O campo Month não foi resetado");
        assertFieldContainsText(By.id(Selectors.SELECT_DAYBOX), "", "O campo Day não foi resetado");
    }
}
