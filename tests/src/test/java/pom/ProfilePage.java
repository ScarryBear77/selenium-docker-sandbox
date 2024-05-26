package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Properties;

public class ProfilePage extends BasePage {

    private static final By SAVE_PROFILE_CHANGES_BY =
            By.id("profileSubmit-btn-top");
    private static final By IMAGE_UPLOAD_INPUT_BY =
            By.id("image-upload");
    private static final By PROFILE_SETTINGS_BUTTON_BY =
            By.xpath("//a[@href='https://www.nosalty.hu/profilom']");

    public ProfilePage(WebDriver driver, Properties properties) {
        super(driver, properties);
    }

    @Override
    protected String getPageBasePath() {
        return "https://www.nosalty.hu/profilom";
    }

    public ProfilePage openProfileSettings() {
        waitAndReturnElement(PROFILE_SETTINGS_BUTTON_BY).click();
        waitMillis(1000);
        return this;
    }

    public ProfilePage uploadProfileImage() {
        String profileImageFile = properties.getProperty("profile.image.path");
        File profileImage = new File(profileImageFile);
        waitMillis(1000);
        driver.findElement(IMAGE_UPLOAD_INPUT_BY).sendKeys(profileImage.getAbsolutePath());
        return this;
    }

    public ProfilePage saveProfileChanges() {
        waitAndReturnElement(SAVE_PROFILE_CHANGES_BY).click();
        waitMillis(1000);
        return this;
    }
}
