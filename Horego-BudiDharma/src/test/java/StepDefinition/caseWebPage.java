package StepDefinition;

import com.github.javafaker.Faker;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;

public class caseWebPage {

    WebDriverWait wait;
    Faker faker = new Faker();
    String name =faker.name().username();

    WebDriver driver;
    By username_login = By.xpath("//input[@placeholder='Username']");
    By password = By.xpath("//input[@placeholder='Password']");
    By submit_button = By.xpath("//button[@type='submit']");
    By profile_icon = By.xpath("//span[@class='oxd-userdropdown-tab']");
    By dropdown_user_role = By.xpath("//label[text()='User Role']/following::div[@class='oxd-select-text-input'][1]");
    By option_admin = By.xpath("//span[contains(text(),'Admin')]");
    By employee_name = By.xpath("//input[@placeholder='Type for hints...']");
    By autocomplete_option = By.xpath("//div[@class='oxd-autocomplete-option']");
    By status_option = By.xpath("//label[text()='Status']/following::div[@class='oxd-select-text-input'][1]");
    By enable_option = By.xpath("//span[contains(text(),'Enabled')]");
    By username_user = By.xpath("//label[text()='Username']/following::input[@class='oxd-input oxd-input--active'][1]");
    By password_user = By.xpath("//label[text()='Password']/following::input[@class='oxd-input oxd-input--active'][1]");
    By confirm_password_user = By.xpath("//label[text()='Confirm Password']/following::input[@class='oxd-input oxd-input--active'][1]");
    By label_system_user = By.xpath("//h5[text()='System Users']");

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Given("User do login step")
    public void userDoLoginStep() {
        driver.findElement(username_login).sendKeys("Admin");
        driver.findElement(password).sendKeys("admin123");
        driver.findElement(submit_button).click();
    }

    @Then("User already in login stage")
    public void userAlreadyInLoginStage() {
        WebElement cartIcon = driver.findElement(profile_icon);
        wait.until(ExpectedConditions.visibilityOf(cartIcon));
        Assert.assertEquals(true,cartIcon.isDisplayed());
    }

    @And("User go to admin add user page")
    public void userGoToAdminPage() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/saveSystemUser");
    }

    @And("User create new user")
    public void userCreateNewUser() throws InterruptedException {

        driver.findElement(dropdown_user_role).click();
        driver.findElement(option_admin).click();

        driver.findElement(employee_name).sendKeys("Orange  Test");
        Thread.sleep(3000);
        driver.findElement(autocomplete_option).click();

        driver.findElement(status_option).click();
        driver.findElement(enable_option).click();

        driver.findElement(username_user).sendKeys(name);
        driver.findElement(password_user).sendKeys("Testing!2");
        driver.findElement(confirm_password_user).sendKeys("Testing!2");

        driver.findElement(submit_button).click();
    }

    @Then("Search user added")
    public void searchUserAdded() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(label_system_user));
        driver.findElement(username_user).sendKeys(name);
        driver.findElement(submit_button).click();
        WebElement checkData = driver.findElement(By.xpath("//div[text()='"+name+"']"));
        Assert.assertTrue(checkData.isDisplayed());
    }
}

