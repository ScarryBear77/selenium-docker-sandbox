package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.Instant;
import java.util.Date;
import java.util.Properties;

import static java.time.temporal.ChronoUnit.DAYS;

public class MainPage extends BasePage {

    private static final By CONSENT_INFO_DIV_BY =
            By.xpath("//div[@class='qc-cmp2-consent-info']");
    private static final By LOGIN_BUTTON_BY =
            By.xpath("//a[@href='https://www.nosalty.hu/login']");
    private static final By LOGOUT_BUTTON_BY =
            By.xpath("//a[@href='https://www.nosalty.hu/logout']");
    private static final By RECIPES_BUTTON_BY =
            By.xpath("//nav//ul/li/a[@href='https://www.nosalty.hu/receptek'][contains(text(),'Receptek')]");
    private static final By BREAKFAST_RECIPES_BUTTON_BY =
            By.xpath("//a[@href='https://www.nosalty.hu/receptek/mikor/reggeli'][contains(text(),'Reggeli')]");
    private static final By PROFILE_PAGE_BUTTON_BY =
            By.xpath("//a[@class='a-link -fontColorPrimary -headerProfileImage']");

    public MainPage(WebDriver driver, Properties properties) {
        super(driver, properties);
    }

    @Override
    protected String getPageBasePath() {
        return "https://www.nosalty.hu";
    }

    public void manageConsentCookiesInternally() {
        Date expiry = Date.from(Instant.now().plus(365, DAYS));
        driver.manage().deleteAllCookies();
        driver.manage().addCookie(new Cookie(
                "addtl_consent",
                "1~",
                ".www.nosalty.hu",
                "",
                expiry,
                true));
        driver.manage().addCookie(new Cookie(
                "euconsent-v2",
                "CP_ONMAP_ONMAAKA1BHUA2EgAAAAAEPgAA6gAAAUcALMNCogDrIk" +
                        "JCDQMIIEAKgrCAigQAAAAkDRAQAmDAp2BgEusJEAIAUAAwQAgABRk" +
                        "ACAAASABCIAIACgQAAQCBQAAgAQDAQAMDAAGACwEAgABAdAhTAggU" +
                        "CwASMyIhTAhCgSCAlsqEEgCBBXCEIs8CCAREwUAAAJABWAAICwWBx" +
                        "JICViQQJcQbQAAEACAQQAVCKTswBBAGbLVXiybRlaQFo-cL3gAAAA.YAAACCAAAAAA",
                ".www.nosalty.hu",
                "",
                expiry,
                true));
        while (isCookieContextShown()) {
            this.driver.navigate().refresh();
        }
    }

    public boolean isCookieContextShown() {
        return elementCanBeFound(CONSENT_INFO_DIV_BY);
    }

    public boolean isUserLoggedOut() {
        return !elementCanBeFound(LOGOUT_BUTTON_BY);
    }

    public LoginPage navigateToLoginPage() {
        waitAndReturnElement(LOGIN_BUTTON_BY).click();
        waitMillis(2000);
        return new LoginPage(driver, properties);
    }

    public ProfilePage navigateToProfilePage() {
        waitAndReturnElement(PROFILE_PAGE_BUTTON_BY).click();
        waitMillis(1000);
        return new ProfilePage(driver, properties);
    }

    public MainPage clickOnLogoutButton() {
        waitAndReturnElement(LOGOUT_BUTTON_BY).click();
        waitMillis(1000);
        return this;
    }

    public SearchResultsPage search(String name, String type) {
        waitAndReturnElement(By.id("search")).sendKeys(name);
        waitMillis(200);
        WebElement searchType = waitAndReturnElement(By.id("search-type"));
        searchType.click();
        waitMillis(1000);
        Select dropdown = new Select(searchType);
        waitMillis(200);
        dropdown.selectByValue(type);
        waitMillis(200);
        waitAndReturnElement(By.id("search-btn")).click();
        waitMillis(1000);
        return new SearchResultsPage(driver, properties);
    }

    public SearchResultsPage navigateToBreakfastRecipes() {
        hoverOver(RECIPES_BUTTON_BY);
        waitAndReturnElement(BREAKFAST_RECIPES_BUTTON_BY).click();
        waitMillis(1000);
        return new SearchResultsPage(driver, properties);
    }

}
