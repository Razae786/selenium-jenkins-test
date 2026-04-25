package com.lab10;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    void test_login_with_incorrect_credetials() {
        driver.navigate().to("http://103.139.122.250:4000/");
        
        // Wait for page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        // Try to find email field - common selectors
        WebElement emailField = findElement(By.name("email"), 
                                            By.id("email"), 
                                            By.name("username"),
                                            By.id("username"),
                                            By.xpath("//input[@type='email']"),
                                            By.xpath("//input[contains(@placeholder, 'mail')]"));
        
        // Try to find password field
        WebElement passwordField = findElement(By.name("password"),
                                               By.id("password"),
                                               By.xpath("//input[@type='password']"));
        
        // Try to find submit button
        WebElement submitBtn = findElement(By.id("m_login_signin_submit"),
                                           By.xpath("//button[@type='submit']"),
                                           By.xpath("//input[@type='submit']"),
                                           By.cssSelector("button.btn-primary"));
        
        emailField.sendKeys("qasim@malik.com");
        passwordField.sendKeys("abcdefg");
        submitBtn.click();
        
        // Wait for error message
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Incorrect') or contains(text(), 'invalid') or contains(text(), 'wrong')]")));
        
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Incorrect") || pageSource.contains("invalid") || pageSource.contains("wrong"),
                   "Expected error message not found");
    }

    private WebElement findElement(By... selectors) {
        for (By selector : selectors) {
            try {
                return wait.until(ExpectedConditions.presenceOfElementLocated(selector));
            } catch (Exception e) {
                // Try next selector
            }
        }
        throw new RuntimeException("Could not find element with any of the provided selectors");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}