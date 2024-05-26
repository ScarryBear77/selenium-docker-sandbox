package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Properties;

public class IngredientPage extends BasePage {

    private static final By NUTRITION_EXPAND_LABEL_BY =
            By.xpath("//label[@for='nutritionExpand']");
    private static final By CALCULATE_CALORIE_RESULT_BY =
            By.xpath("//div[@class='p-article__ingredientCalculatorCalorieResult text-center']/p");

    public IngredientPage(WebDriver driver, Properties properties) {
        super(driver, properties);
    }

    @Override
    protected String getPageBasePath() {
        return "https://www.nosalty.hu/alapanyag/alma";
    }

    public IngredientPage openNutritionAndCalories() {
        waitAndReturnElement(NUTRITION_EXPAND_LABEL_BY).click();
        waitMillis(500);
        return this;
    }

    public IngredientPage setIngredientMassTo(Integer massInGrams) {
        WebElement ingredientCalculatorGramInput = waitAndReturnElement(By.id("ingredientCalculatorGramInput"));
        ingredientCalculatorGramInput.clear();
        ingredientCalculatorGramInput.sendKeys(massInGrams.toString());
        waitMillis(500);
        return this;
    }

    public IngredientPage calculateCalories() {
        waitAndReturnElement(By.id("ingredientCalculatorBtn")).click();
        waitMillis(500);
        return this;
    }

    public String getCalculateCaloriesResult() {
        return waitAndReturnElement(CALCULATE_CALORIE_RESULT_BY).getText().trim();
    }
}
