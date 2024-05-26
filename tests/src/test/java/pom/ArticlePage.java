package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

public class ArticlePage extends BasePage {

    private static final By ARTICLE_AUTHOR_NAME_BY =
            By.xpath("//div[@class='m-article__author d-flex align-items-center mb-5 _ce_measure_widget']/a");

    public ArticlePage(WebDriver driver, Properties properties) {
        super(driver, properties);
    }

    @Override
    protected String getPageBasePath() {
        return "https://www.nosalty.hu/ajanlo/citromra-fel";
    }

    public String getAuthorName() {
        return waitAndReturnElement(ARTICLE_AUTHOR_NAME_BY).getText();
    }
}
