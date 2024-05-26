package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Properties;

public class SearchResultsPage extends BasePage {

    private static final By HEADER_BY =
            By.tagName("h1");
    private static final By RESULT_ELEMENT_BY =
            By.xpath("//ul[@class='m-searchResult__items m-grid -searchResult px-4']//h2/a");

    public SearchResultsPage(WebDriver driver, Properties properties) {
        super(driver, properties);
    }

    @Override
    protected String getPageBasePath() {
        return "https://www.nosalty.hu/kereses/recept";
    }

    public String getHeaderTitleText() {
        return waitAndReturnElement(HEADER_BY).getText();
    }

    public List<WebElement> getResultLinks() {
        return waitAndReturnElements(RESULT_ELEMENT_BY);
    }

    public IngredientPage openFirstIngredientLink() {
        getFirstResult().click();
        waitMillis(2000);
        return new IngredientPage(driver, properties);
    }

    public RecipePage openFirstRecipeLink() {
        getFirstResult().click();
        waitMillis(2000);
        return new RecipePage(driver, properties);
    }

    public ArticlePage openFirstArticleLink() {
        getFirstResult().click();
        waitMillis(2000);
        return new ArticlePage(driver, properties);
    }

    private WebElement getFirstResult() {
        return getResultLinks().stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Result links are empty"));
    }
}
