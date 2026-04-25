package com.lab10;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    @Test
    void test_login_with_incorrect_credentials() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu");
        WebDriver driver = new ChromeDriver(options);

        driver.get("http://103.139.122.250:4000/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.findElement(By.name("email")).sendKeys("wrong@test.com");
        driver.findElement(By.name("password")).sendKeys("wrongpassword");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        String pageSource = driver.getPageSource();
        assertTrue(
            pageSource.contains("Invalid") ||
            pageSource.contains("incorrect") ||
            pageSource.contains("error") ||
            pageSource.contains("wrong") ||
            pageSource.contains("failed"),
            "Expected login error message not found"
        );

        driver.quit();
    }
}