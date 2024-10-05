package com.webtest;

import io.qameta.allure.Description;
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

@DisplayName("Testes do sign up")
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
    @Description("Realiza o teste no signup")
    public void testSignup() {
        driver.get(Helpers.URL_INDEX);

        String email = "silva@mail.com";
        hlp.fillInput(By.id("email"), email);

        hlp.clickElement(By.id("enterimg"));

        wait.until(ExpectedConditions.urlToBe(Helpers.URL_REGISTER));
    }

    @Test
    @DisplayName("Preencher campos na tela de Registro")
    @Description("Preenche todo formulário")
    public void testRegisterPageFormSubmission() {
        hlp.fillForm();
    }

    @Test
    @DisplayName("Limpar campos do formulário ao clicar no botão register")
    @Description("Realiza o teste que verifica se após clicar no botão register, os campos são limpos")
    public void testClearFieldsForm() throws InterruptedException {
        hlp.fillForm();

        hlp.resetForm();

        hlp.validatesIfFormFieldsWereCleaned();

        Thread.sleep(2000);
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}
