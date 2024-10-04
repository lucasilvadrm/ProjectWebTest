package com.webtest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@DisplayName("testes do sign up")
public class WebTest {
    private WebDriver driver;
    private Helpers hlp;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        hlp = new Helpers(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Inserir email e Redirecionar para url de Registrar no site")
    public void testSignup() {
        driver.get(Helpers.URL_INDEX);

        String email = "silva@mail.com";
        hlp.fillInput(By.id("email"), email);

        hlp.clickElement(By.id("enterimg"));

        wait.until(ExpectedConditions.urlToBe(Helpers.URL_REGISTER));
    }

    @Test
    @DisplayName("Preencher campos na tela de Registro")
    public void testRegisterPageFormSubmission() {
        hlp.fillForm();
    }

    @Test
    @DisplayName("Limpar campos do formulário ao clicar no botão register")
    public void testClearFieldsForm() {
        hlp.fillForm();

        hlp.resetForm();

        hlp.validatesIfFormCamposWereCleaned();
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}
