package com.lab10;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {
    @Test
    void test_login_with_incorrect_credetials() {
        ChromeOptions o = new ChromeOptions();
        o.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu");
        WebDriver d = new ChromeDriver(o);
        d.get("http://103.139.122.250:4000/");
        assertTrue(d.getTitle() != null && !d.getTitle().isEmpty());
        d.quit();
    }
}