package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;
import java.util.Properties;

public abstract class BasePage {

    protected static final By BODY_BY =
            By.tagName("body");
    protected static final By HOME_PAGE_BY =
            By.xpath("//header//a[@href='https://www.nosalty.hu']");

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Properties properties;

    public BasePage(WebDriver driver, Properties properties) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        this.properties = properties;
    }

    protected abstract String getPageBasePath();

    protected WebElement waitAndReturnElement(By locator) {
        waitUntilVisible(locator);
        return driver.findElement(locator);
    }

    protected List<WebElement> waitAndReturnElements(By locator) {
        waitUntilVisible(locator);
        return driver.findElements(locator);
    }

    protected void waitUntilVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitMillis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTitleText() {
        return driver.getTitle();
    }

    public BasePage goBack() {
        driver.navigate().back();
        waitUntilVisible(BODY_BY);
        return this;
    }

    public void loadPage() {
        driver.get(getPageBasePath());
    }

    public MainPage goToMainPage() {
        waitAndReturnElement(HOME_PAGE_BY).click();
        waitMillis(1000);
        return new MainPage(driver, properties);
    }

    protected boolean elementCanBeFound(By locator) {
        waitMillis(1000);
        try {
            WebElement element = driver.findElement(locator);
            return element != null;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void hoverOver(By locator) {
        Actions actions = new Actions(driver);
        WebElement recipesButton = waitAndReturnElement(locator);
        actions.moveToElement(recipesButton).build().perform();
        waitMillis(1000);
    }
}
