package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

public class LoginPage extends BasePage {

    private static final By LOGIN_BY_EMAIL_BUTTON_BY =
            By.xpath("//div[@class='login__btn']/button");
    private static final By USERNAME_FIELD_BY =
            By.id("username");
    private static final By PASSWORD_FIELD_BY =
            By.id("password");
    private static final By LOGIN_BUTTON_BY =
            By.id("kc-login");

    public LoginPage(WebDriver driver, Properties properties) {
        super(driver, properties);
    }

    @Override
    protected String getPageBasePath() {
        return "https://www.nosalty.hu/login";
    }

    public LoginPage clickOnLoginByEmailButton() {
        waitAndReturnElement(LOGIN_BY_EMAIL_BUTTON_BY).click();
        waitMillis(500);
        return this;
    }

    public LoginPage fillInLoginDetails() {
        waitAndReturnElement(USERNAME_FIELD_BY).sendKeys(properties.getProperty("username"));
        waitAndReturnElement(PASSWORD_FIELD_BY).sendKeys(properties.getProperty("password"));
        waitMillis(1000);
        return this;
    }

    public MainPage clickOnLoginButton() {
        waitAndReturnElement(LOGIN_BUTTON_BY).click();
        waitMillis(2000);
        return new MainPage(driver, properties);
    }
}
