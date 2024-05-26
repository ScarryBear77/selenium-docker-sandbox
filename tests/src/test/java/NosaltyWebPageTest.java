import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pom.ArticlePage;
import pom.BasePage;
import pom.IngredientPage;
import pom.LoginPage;
import pom.MainPage;
import pom.ProfilePage;
import pom.RecipePage;
import pom.SearchResultsPage;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NosaltyWebPageTest {

    private static final String PROPERTIES_FILE_ABSOLUTE_PATH =
            "/home/selenium/tests/src/test/resources/config.properties";

    public WebDriver driver;
    public Properties properties;
    public MainPage mainPage;

    @Before
    public void setup() throws IOException {
        initDriver();
        initProperties();
        mainPage = openMainPageAndSetCookies();
    }

    private void initDriver() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
    }

    private void initProperties() throws IOException {
        FileReader fileReader = new FileReader(PROPERTIES_FILE_ABSOLUTE_PATH);
        properties = new Properties();
        properties.load(fileReader);
    }

    @Test
    public void testStaticPages() {
        Stream.of(
                new MainPage(driver, properties),
                new LoginPage(driver, properties),
                new ProfilePage(driver, properties),
                new SearchResultsPage(driver, properties),
                new IngredientPage(driver, properties),
                new RecipePage(driver, properties),
                new ArticlePage(driver, properties)
        ).forEach(page -> {
            page.loadPage();
            assertFalse(page.getTitleText().isEmpty());
        });
    }

    @Test
    public void testLoginLogoutAndProfilePageUpload() {
        assertTrue(mainPage.isUserLoggedOut());

        mainPage = mainPage.navigateToLoginPage()
                .clickOnLoginByEmailButton()
                .fillInLoginDetails()
                .clickOnLoginButton();
        assertFalse(mainPage.isUserLoggedOut());

        mainPage = mainPage.navigateToProfilePage()
                .openProfileSettings()
                .uploadProfileImage()
                .saveProfileChanges()
                .goToMainPage()
                .clickOnLogoutButton();
        assertTrue(mainPage.isUserLoggedOut());
    }

    @Test
    public void testNavigatingToBreakfastRecipesAndGoingBack() {
        SearchResultsPage recipesPage = mainPage.navigateToBreakfastRecipes();
        String recipesPageHeaderTitle = recipesPage.getHeaderTitleText().toLowerCase();

        assertEquals("reggeli", recipesPageHeaderTitle);
        assertTrue(recipesPage.getTitleText().contains("Reggeli"));

        BasePage previousPage = recipesPage.goBack();
        assertFalse(previousPage.getTitleText().contains("Reggeli"));
    }

    @Test
    public void testSearchForIngredients() {
        String calculatedCalories = searchAndCheckResults("alma", "alapanyag")
                .openFirstIngredientLink()
                .openNutritionAndCalories()
                .setIngredientMassTo(200)
                .calculateCalories()
                .getCalculateCaloriesResult();
        assertFalse(calculatedCalories.isEmpty());
    }

    @Test
    public void testSearchForRecipes() {
        RecipePage recipePage = searchAndCheckResults("barack", "recept").openFirstRecipeLink();
        int preparationTime = recipePage.getPreparationTime();
        int cookingTime = recipePage.getCookingTime();
        int allTime = recipePage.getAllTime();
        assertEquals(allTime, preparationTime + cookingTime);
    }

    @Test
    public void testSearchForArticles() {
        String authorName = searchAndCheckResults("citrom", "cikk").openFirstArticleLink().getAuthorName();
        assertFalse(authorName.isEmpty());
    }

    private MainPage openMainPageAndSetCookies() {
        MainPage mainPage = new MainPage(driver, properties);
        mainPage.loadPage();
        mainPage.manageConsentCookiesInternally();
        assertFalse(mainPage.isCookieContextShown());
        return mainPage;
    }

    private SearchResultsPage searchAndCheckResults(String name, String type) {
        SearchResultsPage searchResultsPage = mainPage.search(name, type);

        String recipesPageHeaderTitle = searchResultsPage.getHeaderTitleText().toLowerCase();
        assertTrue(recipesPageHeaderTitle.contains("lista"));

        List<WebElement> resultLinks = searchResultsPage.getResultLinks();
        assertFalse(resultLinks.isEmpty());

        return searchResultsPage;
    }

    @After
    public void close() {
        Optional.ofNullable(driver).ifPresent(WebDriver::close);
    }
}
