package com.lab10;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    void test_login_with_incorrect_credetials() {
        // Navigate to login page
        driver.navigate().to("http://103.139.122.250:4000/");
        
        // Store current URL before attempting login
        String urlBeforeLogin = driver.getCurrentUrl();
        
        // Fill in incorrect credentials
        driver.findElement(By.name("email")).sendKeys("qasim@malik.com");
        driver.findElement(By.name("password")).sendKeys("abcdefg");
        
        // Click login button
        driver.findElement(By.id("m_login_signin_submit")).click();
        
        // Wait for page to process
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Get URL after login attempt
        String urlAfterLogin = driver.getCurrentUrl();
        
        // Assert we are still on login page (login failed)
        assertTrue(
            urlAfterLogin.equals(urlBeforeLogin) || 
            urlAfterLogin.contains("login") || 
            urlAfterLogin.contains("signin"),
            "Login should have failed but URL changed to: " + urlAfterLogin
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}