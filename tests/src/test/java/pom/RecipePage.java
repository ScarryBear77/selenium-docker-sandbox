package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

public class RecipePage extends BasePage {

    private static final By PREPARATION_TIME_BY =
            By.xpath("(//div[@class='p-recipe__details d-flex justify-content-around p-3 mb-6']//time)[2]");
    private static final By COOKING_TIME_BY =
            By.xpath("(//div[@class='p-recipe__details d-flex justify-content-around p-3 mb-6']//time)[3]");
    private static final By ALL_TIME_BY =
            By.xpath("(//div[@class='p-recipe__details d-flex justify-content-around p-3 mb-6']//time)[4]");

    public RecipePage(WebDriver driver, Properties properties) {
        super(driver, properties);
    }

    @Override
    protected String getPageBasePath() {
        return "https://www.nosalty.hu/recept/sargabarack-chutney-mangoval";
    }

    public Integer getPreparationTime() {
        return Integer.valueOf(waitAndReturnElement(PREPARATION_TIME_BY).getText());
    }

    public Integer getCookingTime() {
        return Integer.valueOf(waitAndReturnElement(COOKING_TIME_BY).getText());
    }

    public Integer getAllTime() {
        return Integer.valueOf(waitAndReturnElement(ALL_TIME_BY).getText());
    }
}
